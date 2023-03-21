package ru.spb.taranenkoant.trick.track.client.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import ru.spb.taranenkoant.trick.track.client.exception.TracelessException;
import ru.spb.taranenkoant.trick.track.client.fx.alert.Alerts;
import ru.spb.taranenkoant.trick.track.client.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class AppResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppRestTemplate.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
        ErrorMessage message = getErrorMessage(response);

        if (Platform.isFxApplicationThread())
            Alerts.error(message.header, message.body);

        logger.error(message.header);
        logger.error(message.body);
        throw new TracelessException(message.body);
    }

    private ErrorMessage getErrorMessage(ClientHttpResponse response) {
        ErrorMessage m = new ErrorMessage();

        try {
            m.header = response.getStatusCode().toString();
        } catch (IOException e) {
            logger.error("Can not read response status code", e);
        }

        try {
            m.body = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Can not read response body", e);
        }

        if (!StringUtils.isNullOrEmpty(m.body)) {
            // пытаемся распарсить json
            try {
                Map map = mapper.readValue(m.body, Map.class);
                m.body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
                String header = StringUtils.toString(map.get("message"));
                if (!header.isEmpty() && !header.equals("No message available"))
                    m.header = header;
            } catch (IOException e) {
                //
            }
        }

        if (StringUtils.isNullOrEmpty(m.body))
            m.body = "Неизвестная ошибка";
        if (StringUtils.isNullOrEmpty(m.header))
            m.header = "Неизвестная ошибка";

        return m;
    }

    private static class ErrorMessage {
        private String header;
        private String body;
    }

}

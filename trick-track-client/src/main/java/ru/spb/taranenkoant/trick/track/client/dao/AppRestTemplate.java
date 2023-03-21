package ru.spb.taranenkoant.trick.track.client.dao;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.*;
import ru.spb.taranenkoant.trick.track.client.exception.TracelessException;
import ru.spb.taranenkoant.trick.track.client.fx.alert.Alerts;

import java.net.ConnectException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class AppRestTemplate extends RestTemplate {

    private static final Logger logger = LoggerFactory.getLogger(AppRestTemplate.class);

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor)
            throws RestClientException {
        try {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        } catch (ResourceAccessException e) {
            if (e.getRootCause() instanceof ConnectException) {
                if (Platform.isFxApplicationThread())
                    Alerts.error("Сервер пока недоступен :-(");
                logger.error("Server unavailable");
                throw new TracelessException("Server unavailable");
            }
            throw e;
        }
    }

    /**
     * Добавляет в restTemplate перехватчик,  которые вырезает заголовки
     * На некоторых версиях линукс попадаются слишком длинные заголовки,
     * которые невозможно прочитать или например Connection: close, что делает
     * невозможным чтение ответа
     * */
    private List<ClientHttpRequestInterceptor> setFilteredHeaderInterceptor() {
        List<ClientHttpRequestInterceptor> interceptors = super.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new FilteredHeaderInterceptor(Collections.singletonList("Connection")));
        return interceptors;
    }

}

package ru.spb.taranenkoant.trick.track.client.dao;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by taranenko on 21.03.2023
 * description: Класс вырезает из ответа заголовки переданные в List<String> headers
 */
public class FilteredHeaderInterceptor implements ClientHttpRequestInterceptor {

    private List<String> headers;

    public FilteredHeaderInterceptor(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        headers.forEach(header -> {
            if(response.getHeaders().containsKey(header))
                response.getHeaders().remove(header);
        });

        return response;
    }
}

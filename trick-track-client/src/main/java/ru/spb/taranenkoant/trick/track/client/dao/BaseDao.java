package ru.spb.taranenkoant.trick.track.client.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class BaseDao {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    public static final DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static final DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
    protected static final AppRestTemplate restTemplate = new AppRestTemplate();
    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
    private static String authToken;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected UriComponentsBuilder baseUriBuilder;

    static {
        restTemplate.setErrorHandler(new AppResponseErrorHandler());
    }

    BaseDao() {
        // for mocks only
    }

    public BaseDao(DaoParams params) {
        String url = params.getUrl();
        if (!url.endsWith("/"))
            url += '/';
        this.baseUriBuilder = UriComponentsBuilder.fromHttpUrl(url);
    }

    protected static URI buildUri(UriComponentsBuilder uriBuilder, String path, Map<String, Object> params) {
        UriComponentsBuilder url = uriBuilder.cloneBuilder().path(path);
        for (String key : params.keySet())
            url.query(String.format("%s={%s}", key, key));
        Map<String, Object> formattedParams = new HashMap<>();
        params.forEach((k, v) -> formattedParams.put(k, formatParamValue(v)));
        return url.build(formattedParams);
    }

    private static Object formatParamValue(Object value) {
        if (value instanceof Date)
            return dateFormat.format((Date) value);
        if (value instanceof LocalDate)
            return localDateFormat.format((LocalDate) value);
        if (value instanceof LocalDateTime)
            return localDateTimeFormat.format((LocalDateTime) value);
        return value;
    }

    protected URI buildUri(String method) {
        return buildUri(method, Collections.emptyMap());
    }

    protected URI buildUri(String method, Map<String, Object> params) {
        return buildUri(baseUriBuilder, method, params);
    }

    protected <T> T getForObject(String method, Class<T> responseType) {
        return getForObject(method, Collections.emptyMap(), responseType);
    }

    public  <T> T getForObject(String method, Map<String, Object> params, Class<T> responseType) {
        URI uri = buildUri(method, params);
        return getForObject(uri, responseType);
    }

    protected <T> T getForObject(URI uri, Class<T> responseType) {
        return exchange(HttpMethod.GET, uri, null, responseType);
    }

    protected <T> List<T> asList(T... array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    protected void postForObject(String method, Map<String, Object> params) {
        postForObject(method, params, null, Object.class);
    }

    protected void postForObject(String method, Object request) {
        postForObject(method, new HashMap<>(), request, Object.class);
    }

    protected void postForObject(String method, Map<String, Object> params, Object request) {
        postForObject(method, params, request, Object.class);
    }

    protected <T> T postForObject(String method, Map<String, Object> params, Class<T> responseType) {
        return postForObject(method, params, null, responseType);
    }

    protected <T> T postForObject(String method, Object request, Class<T> responseType) {
        return postForObject(method, new HashMap<>(), request, responseType);
    }

    protected <T> T postForObject(String method, Map<String, Object> params, Object request, Class<T> responseType) {
        URI uri = buildUri(method, params);
        return postForObject(uri, request, responseType);
    }

    protected <T> T postForObject(URI uri, Object request, Class<T> responseType) {
        return exchange(HttpMethod.POST, uri, request, responseType);
    }


    private <T> T exchange(HttpMethod method, URI uri, Object request, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_TOKEN_HEADER, authToken);
        HttpEntity entity = new HttpEntity<>(request, headers);
        ResponseEntity<T> response = restTemplate.exchange(uri, method, entity, responseType);
        return response.getBody();
    }

//    LoginStatus login(UserDTO user) {
//        URI url = buildUri("login");
//        ResponseEntity<LoginStatus> entity = restTemplate.postForEntity(url, user, LoginStatus.class);
//        List<String> authTokens = entity.getHeaders().get(AUTH_TOKEN_HEADER);
//        if (authTokens != null && authTokens.size() > 0)
//            authToken = authTokens.get(0);
//
//        return entity.getBody();
//    }
}

package org.example.devicesweb.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;

import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class InvocationServiceImpl implements InvocationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(InvocationServiceImpl.class);

    @Override
    public String execute(String method, String url, List<NameValuePair> parameters) {
        HttpUriRequestBase request = null;
        if (method.equals(HttpGet.METHOD_NAME)) {
            request = get(url, parameters);
        } else {
            request = post(url, parameters);
        }
        return send(request);
    }

    @Override
    public String execute(String method, String url, List<NameValuePair> parameters, List<NameValuePair> headers) {
        HttpUriRequestBase request = null;
        if (method.equals(HttpGet.METHOD_NAME)) {
            request = get(url, parameters);
        } else {
            request = post(url, parameters);
        }
        if (!headers.isEmpty()) {
            for (NameValuePair header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }
        }
        return send(request);
    }

    private String send(HttpUriRequestBase request) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            return httpClient.execute(request, response -> EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            LOGGER.error("Error occurs when executing request {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private HttpGet get(String url, List<NameValuePair> parameters) {
        try {
            URI uri = new URIBuilder(new URI(url)).addParameters(parameters).build();
            return new HttpGet(uri);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurs when attempt to build uri {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private HttpPost post(String url, List<NameValuePair> parameters) {
        HttpPost request = new HttpPost(url);
        if (Objects.nonNull(parameters)) {
            request.setEntity(new UrlEncodedFormEntity(parameters));
        }
        return request;
    }

}

package org.example.devicesweb.service;

import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;

public interface InvocationService {
    HttpResponse execute(String method, String url, List<NameValuePair> parameters);
    HttpResponse execute(String method, String url, List<NameValuePair> parameters, List<NameValuePair> headers);
}

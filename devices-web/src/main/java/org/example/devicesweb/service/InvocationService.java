package org.example.devicesweb.service;

import org.apache.hc.core5.http.NameValuePair;

import java.util.List;
import java.util.Map;

public interface InvocationService {
    String execute(String method, String url, List<NameValuePair> parameters);
}

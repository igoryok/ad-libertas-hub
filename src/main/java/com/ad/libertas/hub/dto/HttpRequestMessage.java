package com.ad.libertas.hub.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 502669748 on 14.10.2016.
 */
public class HttpRequestMessage implements Serializable {

    private static final long serialVersionUID = 538700449794575309L;
    private String method;
    private String uri;
    private Map<String, List<String>> headers;
    private Map<String, List<String>> params;
    private String body;

    public HttpRequestMessage(){

    }

    public HttpRequestMessage(String method, String uri, Map<String, List<String>> headers,
                              Map<String, List<String>> params, String body) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.params = params;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getParams() {
        return params;
    }

    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestMessage that = (HttpRequestMessage) o;
        return Objects.equals(getMethod(), that.getMethod()) &&
                Objects.equals(getUri(), that.getUri()) &&
                Objects.equals(getHeaders(), that.getHeaders()) &&
                Objects.equals(getParams(), that.getParams()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getUri(), getHeaders(), getParams(), getBody());
    }

    @Override
    public String toString() {
        return "HttpRequestMessage{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                ", body='" + body + '\'' +
                '}';
    }
}

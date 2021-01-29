package nettytomcat;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class DefaultNettyRequest implements INettyRequest{
    private HttpRequest httpRequest;

    DefaultNettyRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
    @Override
    public String getUri() {
        return httpRequest.uri();
    }

    @Override
    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.path();
    }

    @Override
    public String getMethod() {
        return httpRequest.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> parameters = getParameters(name);
        if (parameters.isEmpty() || parameters == null) {
            return null;
        }
        return parameters.get(0);
    }
}

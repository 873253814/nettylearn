package nettytomcat;

public class OneServerNet extends ServerNet {
    @Override
    public void doGet(INettyRequest request, INettyResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String method = request.getMethod();
        String name = request.getParameter("name");

        String content = "uri = " + uri + "\n" +
                "path = " + path + "\n" +
                "method = " + method + "\n" +
                "param = " + name;
        response.write(content);
    }

    @Override
    public void doPost(INettyRequest request, INettyResponse response) throws Exception {
        doGet(request, response);
    }
}

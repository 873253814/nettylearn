package nettytomcat;

public class DefaultServerNet extends ServerNet {
    @Override
    public void doGet(INettyRequest request, INettyResponse response) throws Exception {
        String serverNetName = request.getUri().split("/")[1];
        response.write("404 - no this serverNet : " + serverNetName);
    }

    @Override
    public void doPost(INettyRequest request, INettyResponse response) throws Exception{
        doGet(request, response);
    }
}

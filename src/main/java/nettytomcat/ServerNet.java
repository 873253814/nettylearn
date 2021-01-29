package nettytomcat;

public abstract class ServerNet {

    public abstract void doGet(INettyRequest request, INettyResponse response) throws Exception;

    public abstract void doPost(INettyRequest request, INettyResponse response) throws Exception;
}

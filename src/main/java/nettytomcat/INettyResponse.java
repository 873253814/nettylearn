package nettytomcat;

public interface INettyResponse {
    /**
     * 将相应写入channel
     * @param content
     * @throws Exception
     */
    void write(String content) throws Exception;
}

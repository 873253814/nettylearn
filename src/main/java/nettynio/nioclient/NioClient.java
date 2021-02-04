package nettynio.nioclient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel clientChannel = SocketChannel.open();
        //非阻塞模式
        clientChannel.configureBlocking(false);
        //服务器地址
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8888);
        if (!clientChannel.connect(serverAddress)) {
            while (!clientChannel.finishConnect()) {
                System.out.println("未连接上server");
                continue;
            }
        }
        clientChannel.write(ByteBuffer.wrap("hello".getBytes()));
        System.out.println("Client消息已发送");
        System.in.read();

    }
}

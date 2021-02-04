package nettynio.nioserver;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8888));
        Selector selector = Selector.open();
        //selector监听client连接事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("未找到就绪的channel");
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("接收到连接");
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }

                if (selectionKey.isReadable()) {
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        clientChannel.read(byteBuffer);
                        System.out.println(new String(byteBuffer.array()));
                    } catch (Exception e) {
                        selectionKey.cancel();
                    }
                }
                it.remove();
            }

        }

    }
}

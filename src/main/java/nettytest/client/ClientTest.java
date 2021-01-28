package nettytest.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import nettytest.client.handler.HttpClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class ClientTest {

    private String serverHost = "127.0.0.1";

    private Integer serverPort = 8888;


    /**
     * 线程组，用于客户端对服务端的链接、数据读写
     */
    private EventLoopGroup eventGroup = new NioEventLoopGroup();
    /**
     * Netty Client Channel
     */
    private volatile Channel channel;

    @Autowired
    private HttpClientHandler httpClientHandler;

    @PostConstruct
    public void start() throws InterruptedException {
        // 创建 Bootstrap 对象，用于 Netty Client 启动
        Bootstrap bootstrap = new Bootstrap();
        // 设置 Bootstrap 的各种属性。
        bootstrap.group(eventGroup) // 设置一个 EventLoopGroup 对象
                .channel(NioSocketChannel.class)  // 指定 Channel 为客户端 NioSocketChannel
                .remoteAddress(serverHost, serverPort) // 指定链接服务器的地址
                .option(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .option(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                .handler(httpClientHandler);
        // 链接服务器，并异步等待成功，即启动客户端
        bootstrap.connect().addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 连接失败
                if (!future.isSuccess()) {
                    reconnect();
                    return;
                }
                // 连接成功
                channel = future.channel();
            }

        });
    }

    public void reconnect() {
        eventGroup.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (InterruptedException e) {
                }
            }
        }, 60, TimeUnit.SECONDS);
    }

//    public static void main(String[] args) {
//        String host = "127.0.0.1";
//        int port = 8080;
//
//        EventLoopGroup group = new NioEventLoopGroup();
//
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast(new HttpClientCodec())
//                                    .addLast(new HttpObjectAggregator(65536))
//                                    .addLast(new HttpClientHandler());
//                        }
//                    });
//
//            ChannelFuture future = b.connect(host, port).sync();
//            channel = future.channel();
//            future.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
}

package nettytest.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettytest.server.handler.HttpServerInitializer;

import java.net.InetSocketAddress;

public class ServerMain {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建 ServerBootstrap 对象，用于 Netty Server 启动
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置 ServerBootstrap 的各种属性
        bootstrap.group(bossGroup, workerGroup) // 设置两个 EventLoopGroup 对象
                .channel(NioServerSocketChannel.class)  // 指定 Channel 为服务端 NioServerSocketChannel
                .localAddress(new InetSocketAddress(8080)) // 设置 Netty Server 的端口
                .option(ChannelOption.SO_BACKLOG, 1024) // 服务端 accept 队列的大小
                .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .childOption(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                .childHandler(new HttpServerInitializer());
        // 绑定端口，并同步等待成功，即启动服务端
        ChannelFuture future = bootstrap.bind().sync();
    }
}

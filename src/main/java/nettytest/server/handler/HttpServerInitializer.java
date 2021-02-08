package nettytest.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec())
                .addLast("httpServerHandler", new HttpServerHandler())
                .addLast("httpServerInfoHandler", new HttpServerInfoHandler())
                .addLast("httpServerPrintHandler", new HttpServerPrintHandler())
                .addLast("HttpOutBoundHandler", new HttpServerOutHandler())
                .addLast("HttpOutBoundInfoHandler", new HttpServerOutInfoHandler())
                .addLast("idleStateHandler", new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                .addLast("heartBeatHandler", new HeartBeatHandler())
                .addLast("exceptionCaughtHandler", new ExceptionCaughtHandler());
    }
}

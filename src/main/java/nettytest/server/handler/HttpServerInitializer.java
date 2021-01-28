package nettytest.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Component;

@Component
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpServerHandler", new HttpServerHandler())
                .addLast("httpServerInfoHandler", new HttpServerInfoHandler())
                .addLast("httpServerOutHandler", new HttpServerOutHandler())
                .addLast("httpServerOutInfoHandler", new HttpServerOutInfoHandler());
    }
}

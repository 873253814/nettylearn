package nettytest.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class HttpServerInfoHandler extends SimpleChannelInboundHandler<HttpObject> {
    private HttpRequest request;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("Hello,World");
    }
}

package nettytest.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private HttpRequest request;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            request.method();
            String uri = request.uri();
            System.out.println("Uri:" + uri);
            channelHandlerContext.fireChannelRead(msg);
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(StandardCharsets.UTF_8));

            ByteBuf byteBuf = Unpooled.copiedBuffer("lyh", StandardCharsets.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }
    }
}

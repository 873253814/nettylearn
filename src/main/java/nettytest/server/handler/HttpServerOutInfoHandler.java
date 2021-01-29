package nettytest.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.TimeUnit;

public class HttpServerOutInfoHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundInfo Write" + msg);
        ctx.write(msg, promise);
    }

//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        ctx.channel().write("Hello World 2222");
//    }

    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("第四次捕获异常");
        ctx.fireExceptionCaught(cause);

    }
}

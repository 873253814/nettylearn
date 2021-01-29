package nettytest.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ExceptionCaughtHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        String msg = "发生异常";

        if (cause instanceof ArrayIndexOutOfBoundsException) {
            msg = "发生边界索引溢出异常";
        }

        System.out.println(msg);
    }
}

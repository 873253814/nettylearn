package nettytest.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class HttpServerOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("OutBound");
    }
}

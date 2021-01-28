package nettytest.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class HttpServerOutInfoHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("OutBoundInfo");
    }
}

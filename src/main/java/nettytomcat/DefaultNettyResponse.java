package nettytomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;

public class DefaultNettyResponse implements INettyResponse{
    private HttpRequest httpRequest;
    private ChannelHandlerContext channelHandlerContext;

    DefaultNettyResponse(HttpRequest httpRequest, ChannelHandlerContext channelHandlerContext) {
        this.httpRequest = httpRequest;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void write(String content) throws Exception {
        if (StringUtils.isEmpty(content)) {
            return;
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                // 根据响应体内容大小为response对象分配存储空间
                Unpooled.wrappedBuffer(content.getBytes("UTF-8")));

        // 获取响应头
        HttpHeaders headers = response.headers();
        // 设置响应体类型
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
        // 设置响应体长度
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // 设置缓存过期时间
        headers.set(HttpHeaderNames.EXPIRES, 0);
        // 若HTTP请求是长连接，则响应也使用长连接
        if (HttpUtil.isKeepAlive(httpRequest)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        // 将响应写入到Channel
        channelHandlerContext.writeAndFlush(response);
    }
}

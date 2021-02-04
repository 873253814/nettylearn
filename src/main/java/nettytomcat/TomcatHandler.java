package nettytomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class TomcatHandler extends ChannelInboundHandlerAdapter {
    private Map<String, ServerNet> nameToServerNetMap;
    private Map<String, String> nameToClassNameMap;

    public TomcatHandler(Map<String, ServerNet> nameToServerNetMap, Map<String, String> nameToClassNameMap) {
        this.nameToServerNetMap = nameToServerNetMap;
        this.nameToClassNameMap = nameToClassNameMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            // 从请求中解析出要访问的ServerNet名称
            String serverNetName = request.uri().split("/")[1];
            // 直接给ServerNet赋值一个默认的
            ServerNet serverNet = new DefaultServerNet();
            if (nameToServerNetMap.containsKey(serverNetName)) {
                serverNet = nameToServerNetMap.get(serverNetName);
            } else if (nameToClassNameMap.containsKey(serverNetName)) {
                // double-check，双重检测锁
                // 当读到消息的时候根据请求获取对应的ServerNet
                // 如果不存在创建的时候用双重检查锁，避免线程安全问题
                if (nameToServerNetMap.get(serverNetName) == null) {
                    synchronized (this) {
                        if (nameToServerNetMap.get(serverNetName) == null) {
                            // 获取当前serverNet的全限定性类名
                            String className = nameToClassNameMap.get(serverNetName);
                            // 使用反射机制创建Servnet实例
                            serverNet = (ServerNet) Class.forName("nettytomcat." + className).newInstance();
                            // 将Servnet实例写入到nameToServnetMap
                            nameToServerNetMap.put(serverNetName, serverNet);
                        }
                    }
                }
            } //  end-else if

            // 代码走到这里，serverNet肯定不空
            INettyRequest req = new DefaultNettyRequest(request);
            INettyResponse res = new DefaultNettyResponse(request, ctx);
            // 根据不同的请求类型，调用serverNet实例的不同方法
            if (request.method().name().equalsIgnoreCase("GET")) {
                serverNet.doGet(req, res);
            } else if(request.method().name().equalsIgnoreCase("POST")) {
                serverNet.doPost(req, res);
            }
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

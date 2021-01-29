package nettytomcat;

import java.util.List;
import java.util.Map;

public interface INettyRequest {
    /**
     * 获取url?后的参数
     * @return
     */
    String getUri();

    /**
     * 获取路径
     * @return
     */
    String getPath();

    /**
     * 获取方法
     * @return
     */
    String getMethod();

    /**
     * 获取所有请求参数
     * @return
     */
    Map<String, List<String>> getParameters();

    /**
     * 根据名字获取请求参数
     * @param name
     * @return
     */
    List<String> getParameters(String name);

    /**
     * 获取指定名称的请求参数的第一个值
     * @param name
     * @return
     */
    String getParameter(String name);
}

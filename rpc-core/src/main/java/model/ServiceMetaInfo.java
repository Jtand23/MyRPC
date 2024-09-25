package model;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

/**
 * 服务元信息
 */
@Data
public class ServiceMetaInfo {

    //服务名称
    @Getter
    private String serviceName;

    //服务版本号
    @Getter
    private String serviceVersion = "2.0";

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    //服务域名
    @Getter
    private String serviceHost;

    //服务端口号
    @Getter
    private Integer servicePort;

    //服务分组
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     */
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点名
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整地址名
     */
    public String getServiceAddress() {
        if(!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }

    /**

     * 将对象转换为 JSON 字符串

     */

    public String toJsonString() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }

    }
}

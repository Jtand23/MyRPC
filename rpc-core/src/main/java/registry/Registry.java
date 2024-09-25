package registry;

import config.RegistryConfig;
import model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 */
public interface Registry {
    //初始化
    void init(RegistryConfig registryConfig);

    //注册服务
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    //注销服务
    void unregister(ServiceMetaInfo serviceMetaInfo);

    //服务发现
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    //服务销毁
    void destory();

    //心跳检测（服务端）
    void heartBeat();

    //监听服务
    void watch(String serviceNodeKey);
}

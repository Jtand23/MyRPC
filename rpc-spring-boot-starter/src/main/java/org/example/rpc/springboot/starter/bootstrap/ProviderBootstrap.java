package org.example.rpc.springboot.starter.bootstrap;

import Application.RpcApplication;
import config.RegistryConfig;
import config.RpcConfig;
import model.ServiceMetaInfo;
import model.ServiceRegisterInfo;
import registry.LocalRegistry;
import registry.Registry;
import registry.RegistryFactory;
import server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化类
 */
public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        //Rpc框架初始化（全局注册中心）
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for(ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServicePort(rpcConfig.getServerport());
            serviceMetaInfo.setServiceHost(rpcConfig.getHost());

            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败：", e);
            }
        }

        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerport());
    }
}

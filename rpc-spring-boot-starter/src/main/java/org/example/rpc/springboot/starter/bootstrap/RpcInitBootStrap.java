package org.example.rpc.springboot.starter.bootstrap;

import Application.RpcApplication;
import config.RpcConfig;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.springboot.starter.annotation.EnableRpc;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import server.tcp.VertxTcpServer;

/**
 * Rpc 框架启动
 */
@Slf4j
public class RpcInitBootStrap implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取EnableRpc注解的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        //RPC 框架初始化（配置和注册中心）
        RpcApplication.init();

        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //启动服务器
        if(needServer) {
            VertxTcpServer server = new VertxTcpServer();
            server.doStart(rpcConfig.getServerport());
        } else {
            log.info("不启动server");
        }
    }
}

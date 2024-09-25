package org.example.rpc.springboot.starter.bootstrap;

import Application.RpcApplication;
import cn.hutool.core.bean.BeanException;
import config.RegistryConfig;
import config.RpcConfig;
import lombok.extern.slf4j.Slf4j;
import model.ServiceMetaInfo;
import org.example.rpc.springboot.starter.annotation.RpcService;
import org.springframework.beans.factory.config.BeanPostProcessor;
import registry.LocalRegistry;
import registry.Registry;
import registry.RegistryFactory;

/**
 * Rpc服务提供者启动
 */
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {
    /**
     * bean 初始化后执行，注册服务
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if(rpcService != null) {
            //需要注册服务
            //1. 获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            //默认值处理
            if(interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();

            //2.注册本地服务
            //本地注册
            LocalRegistry.register(serviceName, beanClass);

            //全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServicePort(rpcConfig.getServerport());
            serviceMetaInfo.setServiceHost(rpcConfig.getHost());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);

    }


}

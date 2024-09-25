package org.example.rpc.springboot.starter.bootstrap;

import cn.hutool.core.bean.BeanException;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.springboot.starter.annotation.RpcReference;
import org.springframework.beans.factory.config.BeanPostProcessor;
import proxy.ServiceProxyFactory;

import java.lang.reflect.Field;

@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        Class<?> beanClass = bean.getClass();
        //遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for(Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if(rpcReference != null) {
                //为属性代理生成对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if(interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(true);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败" ,e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

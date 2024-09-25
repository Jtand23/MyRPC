package config;

import fault.retry.RetryStrategyKeys;
import fault.tolerant.TolerantStrategyKeys;
import loadbalancer.LoadBalancerKeys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import serializer.SerializerKeys;

/**
 * Rpc框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "MyRpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String host = "localhost";

    /**
     * 服务器端口
     */
    @Setter
    @Getter
    private int serverport = 8080;

    /**
     *模拟调用
     */
    public boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}

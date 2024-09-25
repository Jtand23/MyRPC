package Consumer;

import Application.RpcApplication;
import config.RegistryConfig;
import config.RpcConfig;
import loadbalancer.LoadBalancerKeys;
import loadbalancer.RoundRobinLoadBalancer;
import loadbalancer.LoadBalancer;
import model.ServiceMetaInfo;
import registry.EtcdRegistry;
import registry.Registry;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsumerBalanceApp {

    public static void main(String[] args) {

        // 创建注册中心实例
        Registry registry = new EtcdRegistry();

        // 初始化注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("http://localhost:2379");
        registry.init(registryConfig);

        // 注册服务
        try {
            registerServices(registry);
            refreshAndPrintCache(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 发现服务
        ServiceMetaInfo discoveryServiceMetaInfo = new ServiceMetaInfo();
        discoveryServiceMetaInfo.setServiceName("myService");
        discoveryServiceMetaInfo.setServiceVersion("1.0");
        String serviceKey = discoveryServiceMetaInfo.getServiceKey();

        try {
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
            if (serviceMetaInfoList != null && !serviceMetaInfoList.isEmpty()) {
                System.out.println("Found services:");

                // 创建负载均衡器实例
                LoadBalancer loadBalancer = new RoundRobinLoadBalancer();

                // 测试负载均衡效果
                for (int i = 0; i < 10; i++) {
                    ServiceMetaInfo selectedService = loadBalancer.select(null, serviceMetaInfoList);
                    System.out.println("Selected Service " + (i + 1) + ": " + selectedService.getServiceAddress());
                }
            } else {
                System.out.println("No services found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 在休眠60秒后关闭资源
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted, Failed to complete operation");
        }

        // 关闭资源
        ((EtcdRegistry) registry).destory();
    }

    /**
     * Registers multiple services with the registry.
     *
     * @param registry The registry instance to use for registration.
     * @throws Exception If there is an error during registration.
     */
    private static void registerServices(Registry registry) throws Exception {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.register(serviceMetaInfo);

        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1100);
        registry.register(serviceMetaInfo);

        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("2.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(80);
        registry.register(serviceMetaInfo);
    }

    /**
     * Refreshes and prints the cache of a specific service version.
     *
     * @param registry The registry instance to use for discovery.
     */
    private static void refreshAndPrintCache(Registry registry) {
        List<ServiceMetaInfo> serviceMetaInfoList = ((EtcdRegistry) registry).serviceDiscovery("myService:1.0");
        serviceMetaInfoList.forEach(serviceMetaInfo -> {
            System.out.println("Cached Service Key: " + serviceMetaInfo.getServiceKey());
        });
    }
}

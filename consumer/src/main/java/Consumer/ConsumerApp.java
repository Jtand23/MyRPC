package Consumer;

import config.RegistryConfig;
import model.ServiceMetaInfo;
import registry.EtcdRegistry;
import registry.Registry;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsumerApp {

    public static void main(String[] args) {
        // 创建注册中心实例
        Registry registry = new EtcdRegistry();

        // 初始化注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("http://localhost:2379");
        registry.init(registryConfig);

        // 注册服务
        try {
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
            refreshAndPrintCache(registry);

            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1235);
            registry.register(serviceMetaInfo);
            refreshAndPrintCache(registry);

            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("2.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
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
                for (ServiceMetaInfo info : serviceMetaInfoList) {
                    System.out.println("Service Name: " + info.getServiceName());
                    System.out.println("Service Version: " + info.getServiceVersion());
                    System.out.println("Service Host: " + info.getServiceHost());
                    System.out.println("Service Port: " + info.getServicePort());
                    System.out.println("Service Address: " + info.getServiceAddress());
                    System.out.println("-------------------------");
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

    private static void refreshAndPrintCache(Registry registry) {
        List<ServiceMetaInfo> serviceMetaInfoList = ((EtcdRegistry) registry).serviceDiscovery("myService:2.0");
        serviceMetaInfoList.forEach(serviceMetaInfo -> {
            System.out.println("Cached Service Key: " + serviceMetaInfo.getServiceKey());
        });
    }
}

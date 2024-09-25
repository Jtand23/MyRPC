package provider;

import common.service.UserService;
import model.ServiceRegisterInfo;
import org.example.rpc.springboot.starter.bootstrap.ProviderBootstrap;

import java.util.ArrayList;
import java.util.List;


/**
 * 简易服务提供者
 */
public class ProviderExample {
    public static void main(String[] args) {
       //要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<?> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName() , UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        //服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}

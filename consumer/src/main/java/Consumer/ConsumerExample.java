package Consumer;

import bootstrap.ConsumerBootstrap;
import common.model.User;
import common.service.UserService;
import proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        //服务消费者初始化
        ConsumerBootstrap.init();

        //获取代理
        UserService userService = ServiceProxyFactory.getMockServiceProxy(UserService.class);
        User user = new User();
        user.setName("kevin");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        } else {
           System.out.println("user为空！");
        }
    }
}

package Consumer;

import common.model.User;
import common.service.UserService;
import proxy.ServiceProxyFactory;

/**
 * @author russ2
 */
public class EasyConsumerExample {
    // todo 需要获取UserService的实现类对象
    public static void main(String[] args) {
        //动态代理
        UserService userService = ServiceProxyFactory.getServiceProxy(UserService.class);
        User user = new User();
        user.setName("张三");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("未找到用户");
        }
    }
}

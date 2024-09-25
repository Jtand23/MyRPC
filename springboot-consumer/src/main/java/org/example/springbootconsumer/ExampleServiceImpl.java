package org.example.springbootconsumer;

import common.model.User;
import common.service.UserService;
import org.example.rpc.springboot.starter.annotation.RpcReference;
import org.example.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class ExampleServiceImpl {

    @RpcReference
    UserService userService;

    public void test() {
        User user = new User();
        user.setName("kevin");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}

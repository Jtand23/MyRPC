package provider;

import common.model.User;
import common.service.UserService;

/**
 * @author russ2
 */
public class UserServiceImpl implements UserService {
    public User getUser(User user){
        System.out.println("用户名："+user.getName());
        return user;
    }
}

package common.service;

import common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 * @author russ2
 */
@Service
@Component
public interface UserService {
    /**
     * 获取用户
     *
     * @Param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法，获取数字
     */
    default short getNumber(){
        return 1;
    }
}

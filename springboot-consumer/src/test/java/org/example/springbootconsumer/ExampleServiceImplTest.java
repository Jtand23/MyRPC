package org.example.springbootconsumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringbootConsumerApplication.class)
@ActiveProfiles("test") // 使用测试环境配置
public class ExampleServiceImplTest {

    @Autowired
    private ExampleServiceImpl exampleService;

    @Test
    public void testRemoteServiceCall() {
        exampleService.test(); // 这里假设 test() 方法会触发远程调用
    }
}

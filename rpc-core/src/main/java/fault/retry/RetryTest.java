package fault.retry;

import model.RpcResponse;
import org.junit.Test;

public class RetryTest {

    RetryStrategy strategy = new FixedIntervalRetryStrategy();

    @Test
    public void test() {
        try {
            RpcResponse rpcResponse = strategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟测试失败");
            });
            System.out.println(rpcResponse.getMessage());
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}

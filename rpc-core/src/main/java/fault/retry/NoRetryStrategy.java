package fault.retry;

import model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试——重试策略
 */
public class NoRetryStrategy implements RetryStrategy{
    @Override
    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}

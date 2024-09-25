package fault.retry;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import model.RpcResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy{

    public RpcResponse doRetry(Callable<RpcResponse> callable) throws ExecutionException, RetryException {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3000, java.util.concurrent.TimeUnit.MILLISECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("第" + attempt.getAttemptNumber() + "次重试");
                    }
                })
                .build();
        return retryer.call(callable);

    }
}

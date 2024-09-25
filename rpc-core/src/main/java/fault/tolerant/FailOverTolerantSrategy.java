package fault.tolerant;

import model.RpcResponse;

import java.util.Map;

/**
 * 转移到其他服务节点 - 容错策略
 */
public class FailOverTolerantSrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //todo 扩展，获取其他服务节点并调用
        return null;
    }
}

package loadbalancer;

public interface LoadBalancerKeys {
    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";
    String RANDOM = "random";
    String CONSTANT_HASH = "constantHash";
}

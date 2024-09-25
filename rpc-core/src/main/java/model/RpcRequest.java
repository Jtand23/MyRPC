package model;


import constant.RpcConstant;
import lombok.*;

import java.io.Serializable;

/**
 * RPC请求
 * @author russ2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 服务版本
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数列表
     */
    private Object[] args;

//    private RpcRequest(Builder builder) {
//        this.serviceName = builder.serviceName;
//        this.methodName = builder.methodName;
//        this.parameterTypes = builder.parameterTypes;
//        this.args = builder.args;
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//        private String serviceName;
//        private String methodName;
//        private Class<?>[] parameterTypes;
//        private Object[] args;
//
//        public Builder serviceName(String serviceName) {
//            this.serviceName = serviceName;
//            return this;
//        }
//
//        public Builder methodName(String methodName) {
//            this.methodName = methodName;
//            return this;
//        }
//
//        public Builder parameterTypes(Class<?>[] parameterTypes) {
//            this.parameterTypes = parameterTypes;
//            return this;
//        }
//
//        public Builder args(Object[] args) {
//            this.args = args;
//            return this;
//        }
//
//        public RpcRequest build() {
//            return new RpcRequest(this);
//        }
//    }
//
//    public String getServiceName() {
//        return serviceName;
//    }
//
//    public String getMethodName() {
//        return methodName;
//    }
//
//    public String getServiceVersion() {
//        return RpcConstant.DEFAULT_SERVICE_VERSION;
//    }
//
//    public Class<?>[] getParameterTypes() {
//        return parameterTypes;
//    }
//
//    public Object[] getArgs() {
//        return args;
//    }
}

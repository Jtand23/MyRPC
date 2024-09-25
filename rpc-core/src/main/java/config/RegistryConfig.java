package config;

import lombok.Data;

/**
 * RPC框架注册中心
 */
@Data
public class RegistryConfig {
    private String registry = "etcd";

    //注册中心地址
    private String address = "http://localhost:2380";

    //用户名
    private String userName;

    //密码
    private String password;

    //超时时间
    private Long timeout = 30000L;

}

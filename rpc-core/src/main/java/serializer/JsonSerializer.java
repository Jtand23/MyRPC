package serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.RpcRequest;
import model.RpcResponse;

import java.io.IOException;

/**
 * Json序列化器，用于将Java对象转换为JSON字节数组，以及反之。
 * @author russ2
 */
public class JsonSerializer implements Serializer {
    /**
     * 静态的ObjectMapper实例，用于所有序列化和反序列化操作
      */

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将Java对象序列化为JSON字节数组。
     *
     * @param obj 要序列化的Java对象
     * @return 对象的JSON字节数组表示
     * @throws IOException 如果序列化过程中发生I/O错误
     */
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }

    /**
     * 将JSON字节数组反序列化为Java对象。
     *
     * @param bytes JSON字节数组
     * @param classType Java对象的目标类型
     * @return 反序列化后的Java对象
     * @throws IOException 如果反序列化过程中发生I/O错误
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> classType) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, classType);

        // 检查反序列化后的对象类型，如果是RpcRequest或RpcResponse，则进行额外处理
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, classType);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, classType);
        }

        // 如果不是上述两种类型，直接返回反序列化后的对象
        return obj;
    }

    /**
     * 处理RpcRequest对象，确保参数类型匹配。
     *
     * @param rpcRequest RpcRequest对象
     * @param type 目标类型
     * @return 处理后的RpcRequest对象
     * @throws IOException 如果处理过程中发生I/O错误
     */
    public <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        // 遍历参数列表，如果参数的实际类型与声明的类型不符，则重新反序列化为正确的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argsBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argsBytes, clazz);
            }
        }

        // 设置处理后的参数
        rpcRequest.setArgs(args);
        return type.cast(rpcRequest);
    }

    /**
     * 处理RpcResponse对象，确保响应数据的类型正确。
     *
     * @param rpcResponse RpcResponse对象
     * @param type 目标类型
     * @return 处理后的RpcResponse对象
     * @throws IOException 如果处理过程中发生I/O错误
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        // 将响应数据重新序列化并反序列化，确保其类型与预期相符
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}

package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC响应
 * @author russ2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    private Object data;
    private Class<?> dataType;
    private String message;
    private Exception exception;

    public Object getData() {
        return data;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }



}

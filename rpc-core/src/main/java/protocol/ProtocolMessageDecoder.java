package protocol;

import io.vertx.core.buffer.Buffer;
import model.RpcRequest;
import model.RpcResponse;
import serializer.Serializer;
import serializer.SerializerFactory;

import java.io.IOException;
import java.rmi.RemoteException;

public class ProtocolMessageDecoder {

    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException, ClassNotFoundException {
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        //校验魔数
        if(magic != ProtocolConstant.PROTOCOL_MAGIC) {
            throw new RuntimeException("Magic消息非法");
        }
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setType(buffer.getByte(2));
        header.setStatus(buffer.getByte(3));
        header.setSerializer(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5));
        header.setBodyLength(buffer.getInt(13));
        //解决粘包问题，只读指定长度的数据
        byte[] bodyBytes = buffer.getBytes(17, 17 + header.getBodyLength());
        //解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null) {
            throw new RuntimeException("序列化消息的协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum messageTypeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if(messageTypeEnum == null) {
            throw new RuntimeException("序列化消息的类型不存在");
        }
        switch (messageTypeEnum) {
            case REQUEST:
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                return new ProtocolMessage<>(header, request);
            case RESPONSE:
                RpcResponse response = serializer.deserialize(bodyBytes, RpcResponse.class);
                return new ProtocolMessage<>(header, response);
            case HEARTBEAT:
            case OTHERS:
            default:
                throw new RemoteException("暂不支持该消息类型");

        }

    }
}

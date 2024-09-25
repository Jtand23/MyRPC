package serializer;

import java.io.*;

/**
 * @author russ2
 * JDK序列化器
 */
public class JdkSerializer implements Serializer{

    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }

        /**
         * 反序列化
         *
         * @param bytes
         * @param type
         * @param <T>
         * @return
         * @throws IOException
         * @throws ClassNotFoundException
         */


        @Override
        public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException, ClassNotFoundException {

            if(bytes == null) {
                return null;
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                return (T) objectInputStream.readObject();
            }
        }
}

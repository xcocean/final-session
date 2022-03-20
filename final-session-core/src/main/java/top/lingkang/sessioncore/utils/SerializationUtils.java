package top.lingkang.sessioncore.utils;

import java.io.*;

/**
 * @author lingkang
 * Created by 2022/1/27
 * 序列化反序列化工具
 */
public class SerializationUtils {
    public static byte[] serialization(Object source) throws IOException {
        ByteArrayOutputStream bytes = null;
        try {
            bytes = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bytes);
            oos.writeObject(source);
            oos.close();
            return bytes.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (bytes != null)
                bytes.close();
        }
    }

    public static Object unSerialization(InputStream in) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            return objectInputStream.readObject();
        } catch (Exception e) {
            throw e;
        }
    }
}

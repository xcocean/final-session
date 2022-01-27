package top.lingkang.sessioncore.utils;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class Assert {
    public static void isTure(boolean obj,String message){
        if (!obj){
            throw new IllegalStateException(message);
        }
    }

    public static void notNull(Object obj,String message){
        if (obj==null){
            throw new IllegalStateException(message);
        }
    }
}

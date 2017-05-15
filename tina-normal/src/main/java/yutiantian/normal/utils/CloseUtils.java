package yutiantian.normal.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tina on 2017/4/26.
 * Description:
 */

public final class CloseUtils {
    private CloseUtils(){

    }

    /**
     * 关闭closeable对象
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

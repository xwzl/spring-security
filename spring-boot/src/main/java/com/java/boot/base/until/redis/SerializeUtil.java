package com.java.boot.base.until.redis;

import java.io.*;

/**
 * 序列化工具类
 *
 * @author Shuyu.Wang
 */
class SerializeUtil {
    /**
     * 序列化
     */
    static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * 反序列化
     */
    static Object unSerialize(byte[] bytes) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (bytes != null) {
                bis = new ByteArrayInputStream(bytes);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

}

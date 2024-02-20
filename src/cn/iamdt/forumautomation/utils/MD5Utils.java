package cn.iamdt.forumautomation.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class MD5Utils {
    // 生成MD5签名
    public static String generateMD5Sign(int catId, long time) {
        String source = "cat_id" + catId + "time" + time + "fa1c28a5b62e79c3e63d9030b6142e4b";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 获取MD5实例
            return byteArray2Hex(md.digest(source.getBytes())); // 返回MD5签名的十六进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将字节数组转换为十六进制字符串
    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
package cn.iamdt.HuluxiaTools.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpConnection {
    // TODO：准备整合图片上传HttpURLConnection至本类中
    public static HttpURLConnection getHttpURLConnection(String urlString, String params) throws IOException {
        URL url = new URL(urlString);
        java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
        // 设置请求方法为POST
        con.setRequestMethod("POST");
        // 设置请求头
        con.setRequestProperty("Connection", "close");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "305");
        con.setRequestProperty("Host", "floor.huluxia.com");
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setRequestProperty("User-Agent", "okhttp/3.8.1");
        // 允许输入输出流
        con.setDoOutput(true);

        // 发送POST请求参数
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = params.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return con;
    }


}

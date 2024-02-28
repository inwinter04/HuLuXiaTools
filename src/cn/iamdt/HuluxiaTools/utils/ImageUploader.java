package cn.iamdt.HuluxiaTools.utils;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ImageUploader {
    // 此方法用于上传图片并返回fid
    public static String uploadImage(String key, File imageFile) throws IOException {
        String boundary = UUID.randomUUID().toString();
        String charset = "UTF-8";
        String requestURL = "http://upload.huluxia.com/upload/v3/image";

        // 获取当前时间戳
        long timeMillis = System.currentTimeMillis();
        // 生成nonce_str
        String nonce_str = MD5Utils.generateNonceStrUsingSecureRandom();
        // 获得上传图片所需要的sign
        String sign = MD5Utils.generatePicSign(key, nonce_str, timeMillis);

        // 请求参数
        String parameters = "platform=2&gkey=000000&app_version=4.3.0.2&versioncode=20141492&market_id=floor_web&_key=" + key + "&device_code=%5Bd%5D1ed2762d-4019-4e37-8658-442c9467ec63&use_type=2&sign=" + sign + "&timestamp=" + timeMillis + "&nonce_str=" + nonce_str;

        // 创建一个URL对象
        URL url = new URL(requestURL + "?" + parameters);
        // 打开连接
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "okhttp/3.8.1");
        httpConn.setRequestProperty("Accept-Encoding", "gzip");
        httpConn.setRequestProperty("Connection", "close");
        httpConn.setRequestProperty("Host", "upload.huluxia.com");

        OutputStream outputStream = httpConn.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

        // 发送文件数据
        writer.append("--").append(boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(imageFile.getName()).append("\"\r\n");
        writer.append("Content-Type: ").append(HttpURLConnection.guessContentTypeFromName(imageFile.getName())).append("\r\n");
        writer.append("\r\n");
        writer.flush();

        FileInputStream inputStream = new FileInputStream(imageFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append("\r\n").flush();
        writer.append("--").append(boundary).append("--").append("\r\n");
        writer.close();

        // 检查服务器的响应代码
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            InputStream responseStream = new BufferedInputStream(httpConn.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseStreamReader.close();

            // 解析返回的内容
            JSONObject response = new JSONObject(stringBuilder.toString());
            String fid = response.getString("fid");

            httpConn.disconnect();
            return fid; // 返回fid
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
    }

    // 测试方法
    public static void main(String[] args) {
        try {
            // 假设有一个图片文件
            File imageFile = new File("E:\\Pictures\\其他\\IMG_20230731_211334.jpg");
            // 设置账户秘钥
            String key = "";
            // 调用上传方法并打印返回的fid
            String fid = uploadImage(key, imageFile);
            System.out.println("Image FID: " + fid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

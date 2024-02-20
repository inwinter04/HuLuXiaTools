package cn.iamdt.forumautomation;

import cn.iamdt.forumautomation.utils.MD5Utils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInUtility {

    // 用户密钥和API URL
    //    private final String userKey; // 定义用户密钥
    private static final String USER_KEY = "请输入你的Key";
    private static final String POST_URL = "http://floor.huluxia.com/user/signin/ANDROID/4.1.8";
    private static final String USER_AGENT = "okhttp/3.8.1"; // 用户代理

    // 构造函数
    //    public SignInUtility(String userKey) {
    //        this.userKey = userKey; // 从配置加载用户密钥
    //    }

    // 对所有分类板块执行签到
    public void executeSignInForAllCategories() throws InterruptedException, IOException {
        /*
            使用数组爬取出来的板块IP
            1,2,3,4,6,15,16,21,22,29,43,44,45,57,58,60,63,67,68,69,70,71,76,77,81,82,84,90,92,94,96,98,102,107,108,110,111,115,119,120,121,122
         */
        // 创建一个板块ID数组
        int[] catIds = {1,2,3,4,6,15,16,21,22,29,43,44,45,57,58,60,63,67,68,69,70,71,76,77,81,82,84,90,92,94,96,98,102,107,108,110,111,115,119,120,121,122};

        for (int catId : catIds) {
            long time = System.currentTimeMillis(); // 当前时间戳
            String sign = MD5Utils.generateMD5Sign(catId, time); // 生成MD5签名
            // 构建POST请求的参数字符串
            String params = String.format("platform=2&gkey=000000&app_version=4.3.0.2&versioncode=20141492&market_id=floor_web&_key=%s&device_code=[d]1ed2762d-4019-4e37-8658-442c9467ec63&phone_brand_type=MI&hlx_imei=&hlx_android_id=effafaac8567c6d0&hlx_oaid=9d22479ec6e9f693&cat_id=%d&time=%d",
                    USER_KEY, catId, time);
            String postBody = "sign=" + sign; // 构建POST请求体
            sendPostRequest(POST_URL, params, postBody, catId); // 发送POST请求
            Thread.sleep(1000); // 暂停1秒
        }
    }

    // 发送POST请求
    private void sendPostRequest(String postUrl, String getParams, String postBody, int catId) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(postUrl + "?" + getParams); // 构造完整的URL
            connection = (HttpURLConnection) url.openConnection(); // 打开连接
            connection.setRequestMethod("POST"); // 设置请求方法
            connection.setRequestProperty("User-Agent", USER_AGENT); // 设置用户代理
            connection.setDoOutput(true); // 允许输出

            try (OutputStream os = connection.getOutputStream()) {
                os.write(postBody.getBytes()); // 写入POST数据
                os.flush(); // 刷新输出流
            }

            int responseCode = connection.getResponseCode(); // 获取响应码
            if (responseCode == HttpURLConnection.HTTP_OK) { // 响应码为200表示成功
                printResponse(connection, catId); // 打印响应内容
            }
        } finally {
            if (connection != null) {
                connection.disconnect(); // 断开连接
            }
        }
    }

    // 打印响应内容
    private static void printResponse(HttpURLConnection connection, int catId) throws IOException {
        // 使用BufferedReader读取响应
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            // 一行行读取并追加到StringBuilder
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // 解析返回的JSON数据
            JSONObject jsonResponse = new JSONObject(response.toString());
            int status = jsonResponse.getInt("status");
            if (status == 1) { // 状态码为1表示签到成功
                int continueDays = jsonResponse.getInt("continueDays");
                int experienceVal = jsonResponse.getInt("experienceVal");
                // 输出签到成功信息
                System.out.println("板块ID为：" + catId +
                        " 签到状态：成功" +
                        " 获得经验：" + experienceVal +
                        " 连续签到天数：" + continueDays);

            } else {
                // 如果状态码不是1，表示签到失败
                System.out.println("签到失败，请检查原因。");
            }
        }
    }

}
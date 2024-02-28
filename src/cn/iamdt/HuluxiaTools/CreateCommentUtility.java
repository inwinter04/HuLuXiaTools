package cn.iamdt.HuluxiaTools;

import cn.iamdt.HuluxiaTools.utils.AtUserData;
import cn.iamdt.HuluxiaTools.utils.HttpConnection;
import cn.iamdt.HuluxiaTools.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CreateCommentUtility {
    /**
     * 创建评论的静态方法
     *
     * @param userKey   用户登录秘钥
     * @param postId    帖子ID
     * @param commentId 评论ID，回复评论时使用，新评论则为0
     * @param text      评论文本内容
     * @param images    评论图片，没有则为空字符串
     * @param atUsers   评论中艾特用户的JSON数据(可艾特多个用户)
     */
    public static void createComment(String userKey, long postId, long commentId, String text, String images, String atUsers) {
        // 对评论内容进行URL编码
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        // 生成数据验证Sign
        String sign = MD5Utils.generateSign(userKey, String.valueOf(postId), text);

        // 构建请求的URL
        String urlString = "http://floor.huluxia.com/comment/create/ANDROID/4.2?platform=2&gkey=000000&app_version=4.3.0.2&versioncode=20141492&market_id=floor_web&_key=" + userKey;

        // 构建请求参数，使用已编码的评论内容
        String params = "post_id=" + postId + "&comment_id=" + commentId + "&text=" + encodedText + "&patcha=&images=" + images + "&remindUsers=" + atUsers + "&sign=" + sign;

        try {
            HttpURLConnection con = HttpConnection.getHttpURLConnection(urlString, params);

            // 获取响应码
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 请求成功，读取响应内容
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // 打印成功日志和响应内容
                    System.out.println("评论创建成功，响应内容：" + response);
                }
            } else {
                // 请求失败，读取错误流内容
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        errorResponse.append(responseLine.trim());
                    }
                    // 打印失败日志和错误响应内容
                    System.out.println("评论创建失败，错误响应内容：" + errorResponse);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 替换为实际的用户登录秘钥
        String userKey = "";
        // 替换为实际的帖子ID和评论ID（如果是新评论则为0），以及评论文本内容
        long postId = 53365120;
        long commentId = 0; // 新评论
        String text = "我来测试一下 @伊泽瑞尔";
        String images = ""; // 没有图片

        // 创建艾特用户数据
        AtUserData atUserData = new AtUserData();
        atUserData.addUser("伊泽瑞尔", 10485193);

        // 获取艾特用户的JSON数据
        String atUsers = atUserData.getAtUsersJSON();

        createComment(userKey, postId, commentId, text, images, atUsers);
    }
}

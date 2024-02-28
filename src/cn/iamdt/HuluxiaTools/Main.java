package cn.iamdt.HuluxiaTools;

import cn.iamdt.HuluxiaTools.utils.ConfigLoader;

import java.io.IOException;

import static cn.iamdt.HuluxiaTools.SignInUtility.SignInForAllCategories;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        // 读取配置文件
        ConfigLoader configLoader = new ConfigLoader();
        String userKey = configLoader.getUserKey();

        // 执行用户签到操作
        SignInForAllCategories(userKey);
    }
}

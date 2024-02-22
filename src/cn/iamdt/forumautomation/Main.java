package cn.iamdt.forumautomation;

import java.io.IOException;

import static cn.iamdt.forumautomation.SignInUtility.SignInForAllCategories;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        //        ConfigLoader configLoader = new ConfigLoader();
        //        String userKey = configLoader.getUserKey();

        // 设置用户秘钥
        String userKey = "C735451576B94B492652EFC27D549B931A47E7D8C97CD223A9CD465843953137F28AA8328697373EF08FD3549D410F41C84AF1780AC3B564";
        SignInForAllCategories(userKey);
    }
}

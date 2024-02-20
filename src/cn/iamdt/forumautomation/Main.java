package cn.iamdt.forumautomation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        //        ConfigLoader configLoader = new ConfigLoader();
        //        String userKey = configLoader.getUserKey();

        SignInUtility signInUtility = new SignInUtility();
        signInUtility.executeSignInForAllCategories();
    }
}

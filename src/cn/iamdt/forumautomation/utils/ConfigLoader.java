package cn.iamdt.forumautomation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        loadProperties();
    }

    // 从配置文件加载属性
    private void loadProperties() {
        // 注意：路径是相对于类路径的ConfigLoader类的位置
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("无法找到配置文件！");
                return;
            }

            // 加载属性文件
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 获取用户密钥
    public String getUserKey() {
        return properties.getProperty("userKey");
    }
}
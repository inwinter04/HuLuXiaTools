package cn.iamdt.HuluxiaTools.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class AtUserData {
    private final JSONArray atUsersArray;

    public AtUserData() {
        atUsersArray = new JSONArray();
    }

    public void addUser(String nick, long userID) {
        JSONObject userObject = new JSONObject();
        userObject.put("nick", nick);
        userObject.put("userID", userID);
        atUsersArray.put(userObject);
    }

    public String getAtUsersJSON() {
        return atUsersArray.toString();
    }
}
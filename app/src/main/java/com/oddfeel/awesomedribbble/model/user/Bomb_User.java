
package com.oddfeel.awesomedribbble.model.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/7/10 0010.
 * email:970196066@qq.com
 */
public class Bomb_User extends BmobObject {
    private String accesstoken;
    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}

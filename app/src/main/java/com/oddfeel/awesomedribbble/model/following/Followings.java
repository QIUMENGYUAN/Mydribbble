
package com.oddfeel.awesomedribbble.model.following;

/**
 * Created by Administrator on 2016/7/16 0016.
 * email:970196066@qq.com
 */
public class Followings {
private long id;
private String created_at;
private Followee followee;

public long getId() {
        return id;
        }

public void setId(long id) {
        this.id = id;
        }

public String getCreated_at() {
        return created_at;
        }

public void setCreated_at(String created_at) {
        this.created_at = created_at;
        }

public Followee getFollowee() {
        return followee;
        }

public void setFollowee(Followee followee) {
        this.followee = followee;
        }

}

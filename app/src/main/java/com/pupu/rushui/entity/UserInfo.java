package com.pupu.rushui.entity;

import java.util.Date;

/**
 * Created by pupu on 2018/3/25.
 * 用户数据
 */

public class UserInfo extends BaseDO {
    /**
     * 用户id
     */
    long userId;

    /**
     * 电话号码（唯一标识）
     */
    String phoneNum;

    /**
     * 用户昵称
     */
    String userName;

    /**
     * 性别
     */
    String sex;

    /**
     * 出生日期
     */
    Date birth;

    /**
     * 海拔cm
     */
    int height;

    /**
     * 吨位kg
     */
    int weight;

    /**
     * 请求授权token
     */
    String userToken;

    /**
     * 密码
     */
    String password;

    /**
     * 创建日期
     */
    Date createDate;

    /**
     * 更新日期
     */
    Date updateDate;

    /**
     * 用户头像
     */
    String avatarUrl;

    /**
     * 闹钟时间
     */
    Date alarmTime;

    public UserInfo() {
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", phoneNum='" + phoneNum + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                ", height=" + height +
                ", weight=" + weight +
                ", userToken='" + userToken + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", alarmTime=" + alarmTime +
                '}';
    }
}

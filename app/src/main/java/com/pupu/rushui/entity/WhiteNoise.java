package com.pupu.rushui.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pupu on 2018/5/13.
 */

public class WhiteNoise extends BaseDO {
    /**
     * 已选中
     */
    public static final int STATE_CHECKED = 0x001;

    /**
     * 未选中
     */
    public static final int STATE_NO_CHECKED = 0x002;

    /**
     * 正在下载标志
     */
    public static final int STATE_DOWNLOADING = 0x003;

    /**
     * 未下载，显示下载按钮
     */
    public static final int STATE_NO_DOWNLOADED = 0x004;

    /**
     * 键值
     */
    int id;

    /**
     * 下载地址
     */
    String url;

    /**
     * 本机地址
     */
    String localUrl;

    /**
     * 白噪声名称
     */
    String name;

    /**
     * 当前状态
     */
    int state = STATE_NO_CHECKED;

    /**
     * 下载进度：0~100
     */
    int progress;

    public WhiteNoise() {
    }

    public WhiteNoise(String whiteNoiseName) {
        this.name = whiteNoiseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "WhiteNoise{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", progress=" + progress +
                '}';
    }
}

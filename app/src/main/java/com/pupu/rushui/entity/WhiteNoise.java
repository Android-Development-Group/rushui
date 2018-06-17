package com.pupu.rushui.entity;

/**
 * Created by pupu on 2018/5/13.
 */

public class WhiteNoise extends BaseDO {

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

    public WhiteNoise() {
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

    @Override
    public String toString() {
        return "WhiteNoise{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.pupu.rushui.entity;

import java.io.Serializable;

/**
 * Created by pupu on 2018/3/25.
 */
public class PhoneInfo implements Serializable {


    /**
     * 是否是第一次打开app
     */
    boolean isFisrtOpen = true;


    public PhoneInfo() {
        this.isFisrtOpen = true;
    }

    public boolean isFisrtOpen() {
        return isFisrtOpen;
    }

    public void setFisrtOpen(boolean fisrtOpen) {
        isFisrtOpen = fisrtOpen;
    }

}
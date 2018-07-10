package com.pupu.rushui.net.bean;

import com.pupu.rushui.entity.BaseDO;
import com.pupu.rushui.entity.SleepData;

import java.util.List;

/**
 * Created by pupu on 2018/7/10.
 * 上传睡眠数据请求
 */

public class UploadSleepDataRequest extends BaseDO {

    /**
     * 用户id
     */
    Long userid;

    /**
     * 睡眠数据列表
     */
    List<SleepData> sleepDatas;

    public UploadSleepDataRequest() {
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public List<SleepData> getSleepDatas() {
        return sleepDatas;
    }

    public void setSleepDatas(List<SleepData> sleepDatas) {
        this.sleepDatas = sleepDatas;
    }

    @Override
    public String toString() {
        return "UploadSleepDataRequest{" +
                "userid=" + userid +
                ", sleepDatas=" + sleepDatas +
                '}';
    }
}

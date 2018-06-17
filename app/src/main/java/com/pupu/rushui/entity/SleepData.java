package com.pupu.rushui.entity;

import java.util.Date;

/**
 * Created by pupu on 2018/6/11.
 * 睡眠数据
 */

public class SleepData extends BaseDO {

    /**
     * 键值
     */
    int id;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 更新时间
     */
    Date updateTime;

    /**
     * 开始睡眠时间
     */
    Date startTime;

    /**
     * 结束睡眠时间
     */
    Date endTime;


    public SleepData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SleepData{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

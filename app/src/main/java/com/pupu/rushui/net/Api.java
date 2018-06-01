package com.pupu.rushui.net;

import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.UserInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pupu on 2018/5/10.
 * 网络请求接口
 */

public interface Api {

    /**
     * 手机号登录
     *
     * @param userName 用户名（手机号）
     * @return
     */
    @POST(NetAction.LOGIN_REGISTER)
    Observable<UserInfo> loginByPhoneNum(String userName);

    /**
     * 手机号请求注册，服务器发送验证码
     *
     * @param phoneNum 手机号
     * @return
     */
    Observable<ResponseBody> registerByPhoneNum(String phoneNum);

    /**
     * 请求校验短信验证码
     *
     * @param phoneNum 手机号
     * @param smsCode  短信验证码
     * @return
     */
    @POST(NetAction.VERIFY_CODE)
    Observable<UserInfo> verifySMSCode(String phoneNum, String smsCode);

    /**
     * 上传&更新用户信息
     *
     * @param userInfo
     * @return
     */
    Observable<ResponseBody> uploadUserInfo(UserInfo userInfo);

    /**
     * 上传用户闹钟列表
     *
     * @param userID        用户id
     * @param userToken     登录的token
     * @param alarmTimeList 闹钟列表
     * @return
     */
    Observable<ResponseBody> uploadAlarmList(int userID, String userToken, List<AlarmTime> alarmTimeList);

    /**
     * 获取闹钟列表
     *
     * @return
     */
    Observable<ResponseBody> getAlarmList();
}

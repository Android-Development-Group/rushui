package com.pupu.rushui.net;

import com.pupu.rushui.entity.SleepData;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.entity.WhiteNoise;
import com.pupu.rushui.net.bean.BaseResponse;
import com.pupu.rushui.net.bean.UploadSleepDataRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pupu on 2018/5/10.
 * 网络请求接口
 */

public interface Api {


    /**
     * 请求验证码
     *
     * @param phoneNum 手机号
     * @return
     */
    //    @GET("http://rap2api.taobao.org/app/mock/14802//rushui/requestSMSCode")
    @GET(NetAction.REQUEST_SMS_CODE)
    Observable<BaseResponse<String>> requestSMSCode(@Query("phoneNum") String phoneNum);

    /**
     * 验证验证码
     *
     * @param phoneNum 手机号
     * @param smsCode  验证码
     * @return
     */
    @GET(NetAction.VERIFY_SMS_CODE)
    Observable<BaseResponse<String>> verifySMSCode(@Query("phoneNum") String phoneNum, @Query("smsCode") String smsCode);

    /**
     * 请求广告
     *
     * @param userid 用户id
     * @return
     */
    @GET(NetAction.REQUEST_AD)
    Observable<BaseResponse<String>> requestAD(@Query("userid") Long userid);

    /**
     * 获取用户信息
     *
     * @param userid 用户id
     * @return
     */
    @GET(NetAction.REQUEST_USER)
    Observable<BaseResponse<UserInfo>> requestUser(@Query("userid") Long userid);

    /**
     * 获取白噪声列表
     *
     * @param userid 用户id
     * @return
     */
    @GET(NetAction.REQUEST_WHITE_NOISE_LIST)
    Observable<BaseResponse<List<WhiteNoise>>> requestWhiteNoiseList(@Query("userid") Long userid);

    /**
     * 反馈
     *
     * @param userid  用户id
     * @param device  用户设备名称
     * @param content 反馈内容
     * @return
     */
    @GET(NetAction.REQUEST_FEED_BACK)
    Observable<BaseResponse<String>> requestFeedBack(@Query("userid") Long userid, @Query("device") String device, @Query("content") String content);

    /**
     * 获取睡眠数据
     *
     * @param userid 用户id
     * @param dataid 睡眠数据id
     * @param size   指定拉取条数
     * @return
     */
    @GET(NetAction.REQUEST_SLEEP_DATA_LIST)
    Observable<BaseResponse<List<SleepData>>> requestSleepDataList(@Query("userid") Long userid, @Query("dataid") String dataid, @Query("size") int size);

    /**
     * 上传睡眠数据列表
     *
     * @param request 上传请求体
     * @return
     */
    @POST(NetAction.UPLOAD_SLEEP_DATA_LIST)
    Observable<BaseResponse<String>> uploadSleepDataList(@Body UploadSleepDataRequest request);
}

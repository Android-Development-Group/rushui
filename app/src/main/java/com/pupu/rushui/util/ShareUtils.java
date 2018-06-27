package com.pupu.rushui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by pupu on 2018/6/27.
 * 分享工具类
 */

public class ShareUtils {

    /**
     * 分享
     *
     * @param context
     * @param shareTitle   标题
     * @param shareContent 内容
     * @param shareUrl     url地址
     */
    public void share(Activity context, String shareTitle, String shareContent, String shareUrl) {
        Intent it = new Intent();
        it.setAction(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareContent + "\n" + shareUrl);
        it.setType("text/plain");
//			sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//			sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//			sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//			sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
        it.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
//			sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
        context.startActivityForResult(it, 0x110);
    }
}

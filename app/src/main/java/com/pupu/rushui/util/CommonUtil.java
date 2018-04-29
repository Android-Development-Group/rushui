package com.pupu.rushui.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.widget.CommonDialog;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by pulan on 2017/8/18.
 */
public class CommonUtil {

    private static final String TAG = "CommonUtil";
    public static String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 判断year是否为闰年
     *
     * @param year
     * @return true==>闰年;false==平年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            //是闰年
            return true;
        }
        return false;
    }

    /**
     * 强制弹出软键盘
     *
     * @param view
     */
    public static void showSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen == true) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity    目标页面
     * @param windowToken 页面上的任意view的windowToken
     * @return
     */
    public static void hideSoftInputFromWindow(Activity activity, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 获取android系统版本号
     */
    public static int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 显示提示
     */
    public static void showToast(String msg, boolean timeLong) {
        ToastUtils.getInstance().showToast(msg, timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示
     */
    public static void showToast(String msg) {
        ToastUtils.getInstance().showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示
     */
    public static void showToast(int resId, boolean timeLong) {
        showToast(getString(resId), timeLong);
    }

    /**
     * 显示提示
     */
    public static void showToast(int resId) {
        showToast(getString(resId), false);
    }

    public static String getString(int resId) {
        return MyApplication.getInstance().getString(resId);
    }

    /**
     * 判断字符串是否为电话号码
     *
     * @param str 待验证的字符串
     * @return 如果是符合邮箱格式的字符串, 返回true, 否则为false
     */
    public static boolean isPhoneNum(String str) {
        String regex = "^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$";
        return Pattern.matches(regex, str);
    }

    /**
     * 计算渐变颜色中间色值
     *
     * @param startColor 起始颜色
     * @param endColor   结束颜色
     * @param radio      百分比，取值范围【0~1】
     * @return 颜色值
     */
    public static int getColor(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }

    /**
     * 字符转日期date类型
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseStr2Date(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            Log.i(TAG, "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }


    /**
     * 检查当前网络是否可用
     *
     * @return true==>可用;false==>不可用
     */

    public static boolean isNetworkAvailable() {
        Context context = MyApplication.getInstance().getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 震动milliseconds毫秒
     *
     * @param activity
     * @param milliseconds
     */
    public static void vibrateStart(Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 以pattern[]方式震动
     *
     * @param ctx
     * @param pattern
     * @param repeat
     */
    public static void vibrateStart(Context ctx, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) ctx.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, repeat);
    }

    /**
     * 取消震动
     *
     * @param ctx
     */
    public static void virateCancel(Context ctx) {
        Vibrator vib = (Vibrator) ctx.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static final int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static final int px2dip(Resources res, float pxValue) {
        float scale = res.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static final int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    /**
     * 将dimen中的值转换为px值，保证尺寸大小不变
     */
    public static final int dimenToPx(Context context, int rDimen) {
        Resources r = context.getResources();
        return r.getDimensionPixelSize(rDimen);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static final int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static final int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽、高；返回一个数组，[0] = width, [1] = height
     */
    public static final int[] getDisplayPxArray(Context context) {
        int displays[] = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        displays[0] = dm.widthPixels;
        displays[1] = dm.heightPixels;
        return displays;
    }

    /**
     * 获取屏幕宽；返回int
     */
    public static final int getDisplayPxHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽；返回int
     */
    public static final int getDisplayPxWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取状态栏高度px
     */
    public static final int getStatusBarHigh(Resources res) {
        int statusBar = 0;
        try {
            Class<?> c;
            c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBar = res.getDimensionPixelSize(x);
        } catch (Exception e) {
            statusBar = 0;
        }
        return statusBar;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据分辨率获取大小
     *
     * @param mActivity
     * @return 这个方法主要是给引导页用的
     */
    public static int getSize(Activity mActivity) {
        DisplayMetrics dm = getDm(mActivity);
        int height = 0;
        if (dm.widthPixels <= 480) {// 分辨率
            height = 16;
        } else if (dm.widthPixels <= 720) {// 分辨率
            height = 18;
        } else if (dm.widthPixels <= 1080) {// 分辨率
            height = 28;
        } else if (dm.widthPixels <= 1440) {// 分辨率
            height = 28;
        } else if (dm.widthPixels <= 1920) {
            height = 32;
        }
        return height;
    }


    /**
     * 根据分辨率获取大小
     *
     * @param mActivity
     * @return 这个方法主要是给页面中带广告用的viewpager
     */
    public static int getSize2(Activity mActivity) {
        DisplayMetrics dm = getDm(mActivity);
        int height = 0;
        if (dm.widthPixels <= 480) {// 分辨率
            height = 11;
        } else if (dm.widthPixels <= 720) {// 分辨率
            height = 13;
        } else if (dm.widthPixels <= 1080) {// 分辨率
            height = 16;
        } else if (dm.widthPixels <= 1440) {// 分辨率
            height = 18;
        } else if (dm.widthPixels <= 1920) {
            height = 18;
        }
        return height;
    }

    public static DisplayMetrics getDm(Activity mActivity) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static float getDensity(Context mActivity) {
        DisplayMetrics localDisplayMetrics = mActivity.getResources()
                .getDisplayMetrics();
        return localDisplayMetrics.density;
    }

    /**
     * 获取单个App图标
     **/
    public Drawable getAppIcon(String packageName) throws PackageManager.NameNotFoundException {
        Drawable icon = MyApplication.getInstance().getPackageManager().getApplicationIcon(packageName);
        return icon;
    }

    /**
     * 获取单个App名称
     **/
    public static String getAppName(String packageName) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = MyApplication.getInstance().getPackageManager().getApplicationInfo(packageName, 0);
        String appName = MyApplication.getInstance().getPackageManager().getApplicationLabel(appInfo).toString();
        return appName;
    }

    /**
     * 获取单个App版本号
     **/
    public static String getAppVersion(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = MyApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
        String appVersion = packageInfo.versionName;
        return appVersion;
    }

    /**
     * 获取单个App的所有权限
     **/
    public static String[] getAppPermission(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = MyApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
        String[] permission = packageInfo.requestedPermissions;
        return permission;
    }

    /**
     * 获取单个App的签名
     **/
    public static String getAppSignature(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = MyApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        String allSignature = packageInfo.signatures[0].toCharsString();
        return allSignature;
    }

    /**
     * 获取version code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 显示通用对话框
     *
     * @param content  对话框内容
     * @param okText   确定按钮文字
     * @param listener 按钮点击
     * @return
     */
    public static CommonDialog showCommonDialog(Context context, String content, String okText, DialogInterface.OnClickListener listener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        CommonDialog dialog = builder.setContent(content)
                .setOk(okText, listener)
                .setCancel(null, null)
                .create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
        dialog.show();
        return dialog;
    }

    /**
     * 显示通用对话框
     *
     * @param context
     * @param content
     * @param okText
     * @param okListener
     * @param cancelText
     * @param cancelListener
     * @return
     */
    public static CommonDialog showCommonDialog(Context context, String content, String okText, DialogInterface.OnClickListener okListener,
                                                String cancelText, DialogInterface.OnClickListener cancelListener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        CommonDialog dialog = builder.setContent(content)
                .setOk(okText, okListener)
                .setCancel(cancelText, cancelListener)
                .create();
        dialog.show();
        return dialog;
    }

}

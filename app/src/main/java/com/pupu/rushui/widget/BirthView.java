package com.pupu.rushui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pupu.rushui.R;
import com.pupu.rushui.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulan on 17/11/22.
 * 出生日期组合控件
 */
public class BirthView extends RelativeLayout {

    //滚动控件
    WheelView wv_year, wv_month, wv_day;
    //选中的日期
    String yearSel, monthSel, daySel;

    List<String> years = new ArrayList<>();
    List<String> months = new ArrayList<>();
    List<String> days = new ArrayList<>();

    final static int MSG_CHANGE_BIRTH = 0x1001;//改变日期选择事件
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CHANGE_BIRTH:
                    List<String> days = new ArrayList<>();
                    //判断当前选择月份是否为2月
                    if (monthSel.equals("2")) {
                        //判断当前年份是否为闰年
                        if (CommonUtil.isLeapYear(Integer.parseInt(yearSel)) == true) {
                            //闰年2月29天
                            for (int i = 1; i <= 29; i++) {
                                days.add("" + i);
                            }
                            wv_day.setItems(days);
                            //设置成上一次选择的day
                            int tmp = Integer.parseInt(daySel);
                            if (tmp <= 29) {
                                wv_day.setSeletion(Integer.parseInt(daySel) - 1);
                            }
                        } else {
                            //2月28天
                            for (int i = 1; i <= 28; i++) {
                                days.add("" + i);
                            }
                            wv_day.setItems(days);
                            //设置成上一次选择的day
                            int tmp = Integer.parseInt(daySel);
                            if (tmp <= 28) {
                                wv_day.setSeletion(Integer.parseInt(daySel) - 1);
                            }
                        }
                    } else if (monthSel.equals("1") || monthSel.equals("3") || monthSel.equals("5") ||
                            monthSel.equals("7") || monthSel.equals("8") || monthSel.equals("10") ||
                            monthSel.equals("12")) {
                        //判断是否为大月
                        for (int i = 1; i <= 31; i++) {
                            days.add("" + i);
                        }
                        wv_day.setItems(days);
                        //设置成上一次选择的day
                        int tmp = Integer.parseInt(daySel);
                        wv_day.setSeletion(Integer.parseInt(daySel) - 1);
                    } else {
                        //如果是小月
                        for (int i = 1; i <= 30; i++) {
                            days.add("" + i);
                        }
                        wv_day.setItems(days);
                        //设置成上一次选择的day
                        int tmp = Integer.parseInt(daySel);
                        if (tmp <= 30) {
                            wv_day.setSeletion(Integer.parseInt(daySel) - 1);
                        }
                    }
                    if (listener != null) {
                        listener.onDateChanged(yearSel, monthSel, daySel);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public BirthView(Context context) {
        this(context, null);
    }

    public BirthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        //布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_birthview, null);
        //年
        wv_year = view.findViewById(R.id.wv_year);
        wv_month = view.findViewById(R.id.wv_month);
        wv_day = view.findViewById(R.id.wv_day);

        wv_year.setOffset(1);
        wv_month.setOffset(1);
        wv_day.setOffset(1);
        //范围1949.1.1~2016.1.1
        for (int i = 1949; i <= 2016; i++) {
            years.add("" + i);
        }
        for (int i = 1; i <= 12; i++) {
            months.add("" + i);
        }
        //天数要随选择变化,默认31天
        for (int i = 1; i <= 31; i++) {
            days.add("" + i);
        }
        wv_year.setItems(years);
        wv_month.setItems(months);
        wv_day.setItems(days);
        //1994
        wv_year.setSeletion(45);
        yearSel = "1994";
        //10
        wv_month.setSeletion(9);
        monthSel = "10";
        //17
        wv_day.setSeletion(16);
        daySel = "16";

        //设置监听，动态改变取值范围
        wv_year.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                yearSel = item;
                handler.sendEmptyMessage(MSG_CHANGE_BIRTH);
            }
        });
        wv_month.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                monthSel = item;
                handler.sendEmptyMessage(MSG_CHANGE_BIRTH);
            }
        });
        wv_day.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                daySel = item;
                handler.sendEmptyMessage(MSG_CHANGE_BIRTH);
            }
        });

        addView(view);
    }

    //暴露获取日期的接口
    public OnDateChangedListener listener;

    public interface OnDateChangedListener {
        void onDateChanged(String yearSel, String monthSel, String daySel);
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    public String getYearSel() {
        return yearSel;
    }

    public String getMonthSel() {
        return monthSel;
    }

    public String getDaySel() {
        return daySel;
    }
}

package com.pupu.rushui;

import com.pupu.rushui.net.ApiClient;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static void main(String[] args) {
        System.out.println("ok");
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        String s1 = "Programming";
//        String s2 = new String("Programming");
//        String s3 = "Program";
//        String s4 = "ming";
//        String s5 = "Program" + "ming";
//        String s6 = s3 + s4;
//        System.out.println(s1 == s2);
//        System.out.println(s1 == s5);
//        System.out.println(s1 == s6);
//        System.out.println(s1 == s6.intern());
//        System.out.println(s1 == s1.intern());
//        System.out.println(reverse("abc"));
//        try {
//            try {
//                throw new Sneeze();
//            } catch (Annoyance a) {
//                System.out.println("Caught Annoyance");
//                throw a;
//            }
//        } catch (Sneeze s) {
//            System.out.println("Caught Sneeze");
//            return;
//        } finally {
//            System.out.println("Hello World!");
//        }

        Integer[] list = {6, 1, 5, 7, 9, 10, 3, 13};
        bubbleSort(list);
        for (Integer i : list) {
            System.out.println(i + "、");
        }
    }

    public static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1)
            return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    class Annoyance extends Exception {
    }

    class Sneeze extends Annoyance {
    }

    public <T extends Comparable<T>> void bubbleSort(T[] list) {
        if (list == null) return;
        boolean isOk = false;
        for (int i = 1; i < list.length && !isOk; i++) {
            isOk = true;
            for (int j = 0; j < list.length - i; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                    isOk = false;
                }
            }
        }
    }

    @Test
    public void testNet() {
        ApiClient.getInstance().getApi().testNet("@ctitle")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            System.out.println(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println(throwable.toString());
                    }
                });
//        try {
//            Response<ResponseBody> response = call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
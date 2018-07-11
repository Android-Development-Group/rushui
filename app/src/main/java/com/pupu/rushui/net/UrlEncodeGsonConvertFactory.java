package com.pupu.rushui.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by pupu on 2018/7/11.
 * POST请求中对request和response做UrlEncode、UrlDecode处理
 */

public class UrlEncodeGsonConvertFactory extends Converter.Factory {

    private final Gson gson;

    public static UrlEncodeGsonConvertFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new UrlEncodeGsonConvertFactory(gson);
    }

    public static UrlEncodeGsonConvertFactory create() {
        return create(new Gson());
    }

    private UrlEncodeGsonConvertFactory(Gson gson) {
        this.gson = gson;
    }

    /**
     * 请求UrlEncode处理
     *
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new UrlEncodeGsonRequestConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new UrlEncodeGsonResponseConverter<>(gson, type);
    }
}

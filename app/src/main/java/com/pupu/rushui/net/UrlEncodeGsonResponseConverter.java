package com.pupu.rushui.net;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by pupu on 2018/7/11.
 * 响应转换器
 */

public class UrlEncodeGsonResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;
    private final Gson gson;

    UrlEncodeGsonResponseConverter(Gson gson, Type type) {
        this.type = type;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return gson.fromJson(URLDecoder.decode(tempStr, "utf-8"), type);
    }
}

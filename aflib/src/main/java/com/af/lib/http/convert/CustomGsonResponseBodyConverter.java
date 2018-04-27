package com.af.lib.http.convert;

import com.af.lib.http.exception.ApiException;
import com.af.lib.http.response.interfaces.IResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Class clazz;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter,Class clazz) {
        this.gson = gson;
        this.adapter = adapter;
        this.clazz = clazz;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        IResponse iResponse = (IResponse) gson.fromJson(response, clazz);
        if (!iResponse.isOk()) {
            value.close();
            //将所有的错误抛出到rxjava 的onError中
            throw new ApiException(iResponse.getCode(), iResponse.getMessage());
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
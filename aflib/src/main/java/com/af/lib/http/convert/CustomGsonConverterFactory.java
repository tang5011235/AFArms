package com.af.lib.http.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

//CustomGsonConverterFactory.java
public class CustomGsonConverterFactory extends Converter.Factory {

    private final Gson gson;
    private final Class clazz;

    private CustomGsonConverterFactory(Gson gson, Class clazz) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
        this.clazz = clazz;
    }

    public static CustomGsonConverterFactory create(Gson gson, Class clazz) {
        return new CustomGsonConverterFactory(gson, clazz);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(gson, adapter, clazz);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);
    }
}
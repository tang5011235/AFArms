package com.af.lib.app.component;

import retrofit2.Retrofit;

/**
 * 提供配置完成后的数据
 */
public class ConfiguratedList {
    public Retrofit retrofit;

    private ConfiguratedList(Builder builder) {
        retrofit = builder.retrofit;
    }

    public  Retrofit getRetrofit() {
        return this.retrofit;
    }

    public static final class Builder {
        private Retrofit retrofit;

        public Builder() {
        }

        public Builder setRetrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }

        public ConfiguratedList build() {
            return new ConfiguratedList(this);
        }
    }
}

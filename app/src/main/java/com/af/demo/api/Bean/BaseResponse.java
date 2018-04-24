package com.af.demo.api.Bean;

import com.af.lib.http.response.imp.HttpResponse;

/**
 * Created by Administrator on 2017/11/27.
 */

public class BaseResponse<T> extends HttpResponse{
    private int code = 101;
    private String message;
    private boolean erro;
    private T results;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isOk() {
        return code == 200;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

}

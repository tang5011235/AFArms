package com.af.demo.api.Bean;

import com.af.lib.http.response.interfaces.IResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class BaseResponse<T> implements IResponse{
    private List<String> category;
    private int code = 200;
    private String message;
    private boolean error;
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

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}

package com.af.lib.http.response.imp;

import com.af.lib.http.response.interfaces.IResponse;

public class HttpResponse implements IResponse{
    private int code;

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public boolean isOk() {
        return false;
    }
}

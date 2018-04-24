package com.af.lib.http.response.interfaces;

public interface IResponse {
    //返回状态码
   int getCode();
   String getMessage();
   boolean isOk();
}

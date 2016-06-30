package com.dh.superxz_bottom.framework.net.fgview;

import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;

/***
 * w.gs 对OnResponseListener做一定的简化
 */
public abstract class OnQueryListener<T> implements OnResponseListener<T> {

    public abstract void onSuccess(Response<T> response, T t);

    /**子类可重写处理错误 不处理则不重写**/
    public void onFailure(int code, String msg) {
    };

    public void onResponseFinished(Request<T> request, Response<T> response) {
        onSuccess(response, response.getT());
    }

    public void onResponseDataError(Request<T> request) {
        onFailure(900, "数据解析异常");
    }

    public void onResponseConnectionError(Request<T> request, int statusCode) {
        onFailure(statusCode, "连接异常" + request.getUrl());
    }

    public void onResponseFzzError(Request<T> request, ErrorMsg errorMsg) {
        onFailure(errorMsg.getCode(), errorMsg.getMessage());
    }

}

package com.miqtech.master.client.core;

/**
 * Created by xiaoyi on 2016/9/20.
 * 支付结果回调
 */
public interface PayCallback {
    void onCallback(int code);

    public static final int FLAG_SUCCESS = 0;  //成功
    public static final int FLAG_FAILD = -1;   //支付失败
    public static final int FLAG_CANCEL = -2;  // 用户取消
}

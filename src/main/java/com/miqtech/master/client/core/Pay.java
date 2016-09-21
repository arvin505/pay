package com.miqtech.master.client.core;

import android.app.Activity;

/**
 * Created by xiaoyi on 2016/9/20.
 * * 支付抽象类
 * 默认继承微信支付和支付宝支付
 * 可被扩展其他的支付方式
 */
public abstract class Pay {

    /**
     * 支付
     *
     * @param prepayID  后台生成的预付订单
     * @param shopName  商家名称
     * @param orderInfo 商品
     * @param price     支付金额
     */
    public abstract void pay(String prepayID, String shopName, String orderInfo, String price);

    /**
     * 初始化具体的Pay object
     *
     * @param activity 调起支付的activity
     */
    public abstract void init(Activity activity);
}

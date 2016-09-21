package com.miqtech.master.client.core;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyi on 2016/9/20.
 * 支付类 采用单例实现
 * 使用Pay抽象类，调用具象子类的pay方法
 * 目前集成了微信和阿里支付，可自行扩展其他的支付方式@see{Pay.java}
 * 使用时应注意：
 * 1. 需要设置调起支付的activity @see{setActivity}
 * 2. 在调起支付的activity 的 onDestory()中要释放掉activity@see{releaseActivity}
 * 3. 需要回调结果确认@see{addCallback}
 */
public class PayHelper {
    private final static String TAG = "PayHelper";
    private List<PayCallback> mCallbacks = new ArrayList<>();
    private List<Pay> mPays = new ArrayList<>();
    private Activity mActivity;

    public enum PayType {
        WXPAY, ALIPAY
    }

    private static volatile PayHelper mInstance;
    private Map<PayType, Pay> mPayMaps;

    private PayHelper() {
    }

    public static PayHelper getInstance() {
        if (mInstance == null) {
            synchronized (PayHelper.class) {
                if (mInstance == null)
                    mInstance = new PayHelper();
            }
        }
        return mInstance;
    }

    /**
     * 添加支付结果回调
     *
     * @param callback
     */
    public void addCallback(PayCallback callback) {
        if (mCallbacks == null) return;
        if (mCallbacks.contains(callback)) return;
        mCallbacks.add(callback);
    }

    /**
     * 移除支付结果回调 在ondestory中释放资源
     *
     * @param callback
     */
    public void removeCallback(PayCallback callback) {
        if (mCallbacks == null) return;
        if (!mCallbacks.contains(callback)) return;
        mCallbacks.remove(callback);
    }

    /**
     * 清空回调
     */
    public void cleanCallback() {
        if (mCallbacks == null) return;
        mCallbacks.clear();
    }

    /**
     * 通知支付结果  0 成功 -1失败 -2取消
     *
     * @param code
     * @param str
     */
    public void notifyPayCallback(int code, String str) {
        if (mCallbacks == null) return;
        for (PayCallback callback : mCallbacks) {
            callback.onCallback(code);
        }
    }

    /**
     * 支付（支付宝支付需要传入的信息较多，
     * 如果使用微信支付调可用两个参数的方法）
     *
     * @param prepayId
     * @param shopName
     * @param orderInfo
     * @param price
     */
    public void pay(Class clazz, String prepayId, String shopName, String orderInfo, String price) {
        try {
            Object obj = clazz.newInstance();
            if (!(obj instanceof Pay)) {
                throw new IllegalStateException("params wrong , not pay subclass");
            }
            if (mActivity == null) {
                throw new IllegalStateException("pay activity is empty");
            }
            ((Pay) obj).init(mActivity);
            ((Pay) obj).pay(prepayId, shopName, orderInfo, price);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付需要传入其他参数
     *
     * @param prepayId
     */
    public void pay(Class clazz, String prepayId) {
        pay(clazz, prepayId, null, null, null);
    }

    /**
     * 设置调起支付的activity
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        mActivity = null;
        mActivity = activity;
    }

    public void releaseActivity() {
        mActivity = null;
    }
}

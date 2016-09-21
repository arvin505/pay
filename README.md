# pay
    一个简单的支付库
    集成了微信支付和支付宝支付
    可自行扩展其他的支付方式
    
    扩展只需继承Pay，然后实现具体的支付即可
    
    使用方式：
    在需要调起支付的activity(fragment也可)中
    
    //设置调起activity
    PayHelper.getInstance().setActivity(this)；
    
    //加入支付结果回调
    PayHelper.getInstance().addCallback(this);
    
    //实现回调接口方法 0成功 -1失败 -2取消  （支付宝的如果取消了也是支付失败）
    @Override
    public void onCallback(int code) {
        switch (code){
            case 0:
                Toast.makeText(this, "支付成功", 0).show();
                break;
            case -1:
                Toast.makeText(this, "支付失败  " , 0).show();
                break;
            case -2:
                Toast.makeText(this, "他取消啦", 0).show();
                break;
        }
    }
    
    最后注意在onDestory()中去释放掉activity，移除回调
    @Override
    protected void onDestroy() {
        PayHelper.getInstance().releaseActivity();
        PayHelper.getInstance().removeCallback(this);
        super.onDestroy();
    }

package com.tencent.tpush;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.tpush.bean.XGPushResultConvert;

public class XGPushModule extends ReactContextBaseJavaModule implements LifecycleEventListener,
        ActivityEventListener {

    public static final String NAME = "XGPush";
    private final ReactApplicationContext reactContext;
    private DeviceEventManagerModule.RCTDeviceEventEmitter mDeviceEventEmitter;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NAME.equals(intent.getAction()) && intent.getExtras() != null) {
                String eventName = intent.getExtras().getString("eventName", "");
                mDeviceEventEmitter.emit(eventName,
                        Arguments.fromBundle(intent.getExtras()));
            }
        }
    };


    public XGPushModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize() {
        super.initialize();
        this.mDeviceEventEmitter
                = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        LocalBroadcastManager.getInstance(getReactApplicationContext())
                .registerReceiver(mBroadcastReceiver, new IntentFilter(NAME));
    }

    @Override
    public void onCatalystInstanceDestroy() {
        LocalBroadcastManager.getInstance(getReactApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
        super.onCatalystInstanceDestroy();
    }

    //注册信鸽服务的接口
    @ReactMethod
    public void registerPush(Promise promise) {
        XGPushManager.registerPush(getReactApplicationContext(),
                new XGIOperateCallback() {

                    @Override
                    public void onSuccess(Object data, int flag) {
                        promise.resolve(createResult(data));
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        WritableMap result = createResult(data);
                        result.putInt("errCode", errCode);
                        result.putString("msg", msg);
                        promise.resolve(result);
                    }

                    private WritableMap createResult(Object token) {
                        WritableMap result = Arguments.createMap();
                        result.putString("token", "" + token);
                        return result;
                    }
                });
    }

    //设置标签
    @ReactMethod
    public void setTag(String tagName) {
        XGPushManager.setTag(getReactApplicationContext(), tagName);
    }

    //删除标签
    @ReactMethod
    public void deleteTag(String tagName) {
        XGPushManager.deleteTag(getReactApplicationContext(), tagName);
    }

    //检测通知栏是否关闭
    @ReactMethod
    public void isNotificationOpened(Promise promise) {
        promise.resolve(XGPushManager.isNotificationOpened(getReactApplicationContext()));
    }

    @ReactMethod
    public void enableDebug(boolean debugMode) {
        XGPushConfig.enableDebug(getReactApplicationContext(), debugMode);
    }


    @Override
    public void onHostResume() {
        XGPushClickedResult click = XGPushManager.onActivityStarted(getCurrentActivity());
        if (click != null) {
            mDeviceEventEmitter.emit("onNotifactionClick",
                    Arguments.fromBundle(XGPushResultConvert.fromXGPushClickedResult(click)));
        }
    }

    @Override
    public void onHostPause() {
        XGPushManager.onActivityStoped(getCurrentActivity());
    }

    @Override
    public void onHostDestroy() {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        if (getCurrentActivity() != null) {
            getCurrentActivity().setIntent(intent);
        }
    }
}

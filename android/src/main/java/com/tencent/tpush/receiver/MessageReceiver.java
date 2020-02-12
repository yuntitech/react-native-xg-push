package com.tencent.tpush.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.tencent.tpush.XGPushModule;

public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult pushShowedResult) {
        Bundle data = new Bundle();
        data.putLong("msgId", pushShowedResult.getMsgId());
        data.putString("activity", pushShowedResult.getActivity());
        data.putString("title", pushShowedResult.getTitle());
        data.putString("content", pushShowedResult.getContent());
        data.putInt("notificationActionType", pushShowedResult.getNotificationActionType());
        data.putString("custom_content", pushShowedResult.getCustomContent());
        data.putInt("notifaction_id", pushShowedResult.getNotifactionId());
        sendBroadcast(context, "onNotifactionShowedResult", data);
    }

    //反注册的回调
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        Bundle data = new Bundle();
        data.putInt("errorCode", errorCode);
        sendBroadcast(context, "onUnregisterResult", data);
    }

    //设置tag的回调
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        Bundle data = new Bundle();
        data.putInt("errorCode", errorCode);
        data.putString("tagName", tagName);
        sendBroadcast(context, "onSetTagResult", data);
    }

    //删除tag的回调
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        Bundle data = new Bundle();
        data.putInt("errorCode", errorCode);
        data.putString("tagName", tagName);
        sendBroadcast(context, "onDeleteTagResult", data);
    }

    /**
     * 通知点击回调
     * actionType=1为该消息被清除
     * actionType=0为该消息被点击
     * 此处不能做点击消息跳转
     * 详细方法请参照官网的Android常见问题文档(https://xg.qq.com/docs/android_access/android_faq.html)
     *
     * @param context           app上下文
     * @param pushClickedResult 推送点击对象
     */
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult pushClickedResult) {
        Bundle data = new Bundle();
        data.putLong("msgId", pushClickedResult.getMsgId());
        data.putString("title", pushClickedResult.getTitle());
        data.putString("content", pushClickedResult.getContent());
        data.putString("activityName", pushClickedResult.getActivityName());
        data.putInt("notificationActionType", pushClickedResult.getNotificationActionType());
        data.putString("customContent", pushClickedResult.getCustomContent());
        data.putLong("actionType", pushClickedResult.getActionType());
        sendBroadcast(context, "onNotifactionShowedResult", data);
    }

    //注册的回调
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败错误码：" + errorCode;
        }
        Log.d(LogTag, text);
    }

    // 消息透传的回调
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {

    }

    private void sendBroadcast(Context context, String eventName, Bundle data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(XGPushModule.NAME);
        intent.putExtras(data);
        intent.putExtra("eventName", eventName);
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }
}

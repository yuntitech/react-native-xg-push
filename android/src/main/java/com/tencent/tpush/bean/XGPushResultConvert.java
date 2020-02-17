package com.tencent.tpush.bean;

import android.os.Bundle;

import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class XGPushResultConvert {

    public static Bundle fromXGPushClickedResult(XGPushClickedResult pushClickedResult) {
        Bundle result = new Bundle();
        result.putLong("msgId", pushClickedResult.getMsgId());
        result.putString("title", pushClickedResult.getTitle());
        result.putString("content", pushClickedResult.getContent());
        result.putString("activityName", pushClickedResult.getActivityName());
        result.putInt("notificationActionType", pushClickedResult.getNotificationActionType());
        result.putString("customContent", pushClickedResult.getCustomContent());
        result.putLong("actionType", pushClickedResult.getActionType());
        return result;
    }

    public static Bundle fromXGPushTextMessage(XGPushTextMessage pushTextMessage) {
        Bundle result = new Bundle();
        result.putString("title", pushTextMessage.getTitle());
        result.putString("content", pushTextMessage.getContent());
        result.putString("customContent", pushTextMessage.getCustomContent());
        return result;
    }
}

package com.tencent.tpush.bean;

import android.os.Bundle;

import com.tencent.android.tpush.XGPushClickedResult;

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
}

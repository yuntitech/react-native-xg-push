export interface RegisterResult {
  //设备token
  token: string
  //失败错误码
  errCode?: number
  //失败错误信息
  msg?: string
}

export interface XGPushClickedResult extends XGPushCommonResult {
  //消息id
  msgId: number
  //被打开的页面名称
  activityName: string
  //actionType=1为该消息被清除 actionType=0为该消息被点击
  actionType: number
}

export interface XGPushShowedResult extends XGPushCommonResult {
  //消息id
  msgId: number
  notificationActionType: number
}

export interface XGPushTextMessage extends XGPushCommonResult {}

interface XGPushCommonResult {
  //通知标题
  title: string
  //通知正文内容
  content: string
  //自定义key-value，json字符串
  customContent: string
}

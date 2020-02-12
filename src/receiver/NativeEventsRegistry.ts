/*
 * @Author: kangqiang
 * @Date: 2020/2/12 6:11 下午
 * @Last Modified by: kangqiang
 * @Last Modified time: 2020/2/12 6:11 下午
 */
import { NativeModules, NativeEventEmitter, EventEmitter, EmitterSubscription } from 'react-native'
import { XGPushShowedResult, XGPushClickedResult, XGPushTextMessage } from '../interfaces/Results'

export class NativeEventsRegistry {
  private emitter: EventEmitter

  constructor() {
    try {
      // TODO IOS 需要 this.emitter = new NativeEventEmitter(NativeModules.XGPushEventEmitter)
      this.emitter = new NativeEventEmitter()
    } catch (e) {
      this.emitter = ({
        addListener: () => {
          return {
            remove: () => undefined,
          }
        },
      } as any) as EventEmitter
    }
  }

  // 通知展示
  public addNotifactionShowedResultListener(
    callback: (data: XGPushShowedResult) => void,
  ): EmitterSubscription {
    return this.emitter.addListener('onNotifactionShowedResult', callback)
  }

  /**
   * 通知点击回调
   * actionType=1为该消息被清除
   * actionType=0为该消息被点击
   * 此处不能做点击消息跳转
   * 详细方法请参照官网的Android常见问题文档(https://xg.qq.com/docs/android_access/android_faq.html)
   */
  public addNotifactionClickedResultListener(
    callback: (data: XGPushClickedResult) => void,
  ): EmitterSubscription {
    return this.emitter.addListener('onNotifactionClickedResult', callback)
  }

  // 消息透传的回调
  public addTextMessageListener(callback: (data: XGPushTextMessage) => void): EmitterSubscription {
    return this.emitter.addListener('onTextMessage', callback)
  }
}

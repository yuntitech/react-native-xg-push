import { NativeModules } from 'react-native'
const XGPushModule = NativeModules.XGPush
import { RegisterResult } from './interfaces/Results'
import { NativeEventsRegistry } from './receiver/NativeEventsRegistry'
class XGTencentPush {
  private readonly nativeEventsRegistry: NativeEventsRegistry

  constructor() {
    this.nativeEventsRegistry = new NativeEventsRegistry()
  }

  /**
   * 启动并注册
   */
  public registerPush(): Promise<RegisterResult> {
    return XGPushModule.registerPush()
  }

  /**
   * 设置标签
   * @param tagName 标签名
   */
  public setTag(tagName: string) {
    XGPushModule.setTag(tagName)
  }

  /**
   * 删除标签
   * @param tagName 标签名
   */
  public deleteTag(tagName: string) {
    XGPushModule.deleteTag(tagName)
  }

  /**
   * 检测通知栏是否关闭
   */
  public isNotificationOpened(): Promise<boolean> {
    return XGPushModule.isNotificationOpened()
  }

  /**
   * 原生事件处理注册
   */
  public nativeEvents(): NativeEventsRegistry {
    return this.nativeEventsRegistry
  }
}
export const XGPush = new XGTencentPush()
export * from './interfaces/Results'

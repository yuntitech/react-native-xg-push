import { NativeModules } from 'react-native'
const XGPushModule = NativeModules.XGPush
import { NativeEventsRegistry } from './receiver/NativeEventsRegistry'
import { retry } from './utils/utils'

class XGTencentPush {
  private readonly nativeEventsRegistry: NativeEventsRegistry

  constructor() {
    this.nativeEventsRegistry = new NativeEventsRegistry()
  }

  /**
   * 启动并注册
   */
  public registerPush(): Promise<string> {
    return retry(XGPushModule.registerPush)
  }

  /**
   * 启动并注册APP，同时绑定账号,
   推荐有帐号体系的APP使用
   （3.2.2以及3.2.2之后的版本使用，
   此接口会覆盖设备之前绑定过的账号，仅当前注册的账号生效）
   */
  public bindAccount(account: string): Promise<string> {
    return retry(XGPushModule.bindAccount, account)
  }

  /**
   * 解绑指定账号（3.2.2以及3.2.2之后的版本使用，有注册回调）
   *
   * @param account 账号
   */
  public delAccount(account: string): Promise<string> {
    return retry(XGPushModule.delAccount, account)
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
   * 是否开启debug模式，即输出logcat日志重要：为保证数据的安全性，发布前必须设置为false）
   * @param debugMode 调试模式
   */
  public enableDebug(debugMode: boolean) {
    XGPushModule.enableDebug(debugMode)
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

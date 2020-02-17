/*
 * @Author: kangqiang
 * @Date: 2020/2/14 5:10 下午
 * @Last Modified by: kangqiang
 * @Last Modified time: 2020/2/14 5:10 下午
 */

export async function retry<T>(
  fn: (args?: string | any) => Promise<T>,
  args: string | any = null,
  retriesLeft: number = 5,
  interval: number = 1000,
): Promise<T> {
  try {
    return args ? await fn(args) : await fn()
  } catch (error) {
    if (retriesLeft) {
      await new Promise(r => setTimeout(r, interval))
      return retry(fn, args, retriesLeft - 1, interval)
    } else {
      throw error
    }
  }
}

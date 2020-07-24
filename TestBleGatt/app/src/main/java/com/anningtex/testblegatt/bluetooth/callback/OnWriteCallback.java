package com.anningtex.testblegatt.bluetooth.callback;

/**
 * @ClassName: OnWriteCallback
 * @Description: 写操作回调接口
 * @Author: alvis
 * @CreateDate: 2020/7/24 10:49
 */
public interface OnWriteCallback {

    /**
     * 蓝牙未开启
     */
    int FAILED_BLUETOOTH_DISABLE = 1;

    /**
     * 特征无效
     */
    int FAILED_INVALID_CHARACTER = 2;

    /**
     * 写入成功
     */
    void onSuccess();

    /**
     * 写入失败
     *
     * @param state
     */
    void onFailed(int state);
}

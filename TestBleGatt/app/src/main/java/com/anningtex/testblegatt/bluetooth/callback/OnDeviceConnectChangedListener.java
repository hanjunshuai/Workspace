package com.anningtex.testblegatt.bluetooth.callback;

/**
 * @ClassName: OnDeviceConnectChangedListener
 * @Description: 蓝牙设备连接发生变化回调
 * @Author: alvis
 * @CreateDate: 2020/7/24 10:51
 */
public interface OnDeviceConnectChangedListener {
    void onConnected();

    void onDisconnected();
}

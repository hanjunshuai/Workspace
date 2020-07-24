package com.anningtex.testblegatt.bluetooth.callback;

import android.bluetooth.BluetoothDevice;

/**
 * @ClassName: OnScanCallback
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/24 10:49
 */
public interface OnScanCallback {
    /**
     * 扫描完成回调
     */
    void onFinish();

    /**
     * 扫描过程中,每扫描到一个设备回调一次
     *
     * @param device     扫描到的设备
     * @param rssi       设备的信息强度
     * @param scanRecord
     */
    void onScanning(final BluetoothDevice device, int rssi, byte[] scanRecord);
}

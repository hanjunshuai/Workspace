package com.anningtex.testblegatt.bluetooth.callback;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * @ClassName: BleDevceScanCallback
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/24 10:50
 */
public class BleDevceScanCallback implements BluetoothAdapter.LeScanCallback {
    private OnScanCallback mScanCallback;

    public BleDevceScanCallback(OnScanCallback scanCallback) {
        this.mScanCallback = scanCallback;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (null != mScanCallback) {
            //每次扫描到设备会回调此方法,这里一般做些过滤在添加进list列表
            mScanCallback.onScanning(device, rssi, scanRecord);
        }
    }
}

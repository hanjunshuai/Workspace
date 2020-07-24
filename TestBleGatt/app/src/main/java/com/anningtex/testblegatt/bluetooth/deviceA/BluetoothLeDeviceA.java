package com.anningtex.testblegatt.bluetooth.deviceA;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;

import com.anningtex.testblegatt.bluetooth.BluetoothLeDeviceBase;
import com.anningtex.testblegatt.bluetooth.util.HexUtil;

import java.text.DecimalFormat;

/**
 * @ClassName: deviceA
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/24 10:52
 */
public class BluetoothLeDeviceA extends BluetoothLeDeviceBase {

    private static final String TAG = "BluetoothLeDeviceA";
    //根据具体硬件进行设置
    public static String DEVICEA_UUID_SERVICE = "0000ffb0-0000-1000-8000-00805f9b34fb";
    public static String DEVICEA_UUID_CHARACTERISTIC = "0000ffb2-0000-1000-8000-00805f9b34fb";

    //一般不用修改
    public static String DEVICEA_UUID_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";

    public BluetoothLeDeviceA(Context context) {
        super(context);
        UUID_SERVICE = DEVICEA_UUID_SERVICE;
        UUID_NOTIFY = DEVICEA_UUID_CHARACTERISTIC;

        UUID_DESCRIPTOR = DEVICEA_UUID_DESCRIPTOR;
    }


    @Override
    public void parseData(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        String toHexString = HexUtil.bytesToHexString(characteristic.getValue());
        String hexArray[] = toHexString.split(" ");
        byte[] b = new byte[hexArray.length];
        for (int i = 0; i < hexArray.length; i++) {
            b[i] = HexUtil.hexStringToByte(hexArray[i])[0];
        }
        String sixteen = hexArray[3] + hexArray[4];
        int ten = Integer.parseInt(sixteen, 16);
        Log.e(TAG, "BluetoothLeDeviceA-parseData: -------解析数据" + toHexString + toFloat(ten, 100));
    }

    /**
     * TODO 除法运算，保留小数
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    public static String toFloat(int a, int b) {
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((float) a / b);
    }
}

package com.anningtex.testbluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @Description: des
 * @ClassName: MainActivity
 * @Author: alvis
 * @CreateDate:2020-6-11 15:24:41
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENBLE_BT = 1 << 2;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothManager bluetoothManger = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if (bluetoothManger != null) {
            mBluetoothAdapter = bluetoothManger.getAdapter();
        }
        if (mBluetoothAdapter == null) {
            Log.e("TAG", "当前设备不支持蓝牙");
        } else {
            // 判断蓝牙是否打开
            if (!mBluetoothAdapter.isEnabled()) {
                //蓝牙设备没有打开
                //打开蓝牙设备，这一步应该放到线程里边操作
                //mBluetoothAdapter.enable();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENBLE_BT);
            } else {
                //蓝牙设备已经打开，需要关闭蓝牙
                //这一步也需要放到线程中操作
                // mBluetoothAdapter.disable();
            }
        }

        mBroadcastReceiver = new BluetoothReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        registerReceiver(mBroadcastReceiver, intentFilter);

        // 开始进行蓝牙扫描
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENBLE_BT) {
            if (resultCode == RESULT_OK) {
                mBluetoothAdapter.startDiscovery();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    public class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device;
            // 搜索发现设备时,取得设备的信息;注意,这里有可能重复搜索同一设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 这里就是我们扫描到的蓝牙设备
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            }
            // 状态改变
            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    // 正在配对
                    case BluetoothDevice.BOND_BONDING:
                        Log.e("TAG", "正在配对......");
                        break;
                    // 配对结束
                    case BluetoothDevice.BOND_BONDED:
                        Log.e("TAG", "配对结束......");
                        break;
                    // 取消配对 未配对
                    case BluetoothDevice.BOND_NONE:
                        Log.e("TAG", "取消配对......");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + device.getBondState());
                }
            }
        }
    }
}

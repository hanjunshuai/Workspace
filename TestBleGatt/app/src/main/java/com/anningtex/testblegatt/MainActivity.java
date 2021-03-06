package com.anningtex.testblegatt;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.BluetoothLeScanner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anningtex.testblegatt.bluetooth.callback.OnDeviceConnectChangedListener;
import com.anningtex.testblegatt.bluetooth.callback.OnScanCallback;
import com.anningtex.testblegatt.bluetooth.callback.OnWriteCallback;
import com.anningtex.testblegatt.bluetooth.deviceA.BluetoothLeDeviceA;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: des
 * @ClassName: MainActivity
 * @Author: alvis
 * @CreateDate:2020-7-24 10:36:15
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UaTestActivity";

    private BluetoothAdapter mBluetoothAdapter;

    private TextView mConnectionState1;
    private TextView mDataField;
    private TextView scanResult;

    private TextView send;
    private EditText sendCommand;
    private BluetoothLeDeviceA bluetoothLeDeviceA;

//    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rxPermissions = new RxPermissions(this);
        bluetoothLeDeviceA = new BluetoothLeDeviceA(this);
        bluetoothLeDeviceA.setConnectChangedListener(new OnDeviceConnectChangedListener() {
            @Override
            public void onConnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnectionState1.setText("onConnected");
                    }
                });
            }

            @Override
            public void onDisconnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnectionState1.setText("onDisconnected");
                    }
                });
            }
        });

        mBluetoothAdapter = bluetoothLeDeviceA.isDeviceSupport();
        if (mBluetoothAdapter == null) {
            finish();
            return;
        }

        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLeDevice(true);
            }
        });
        mConnectionState1 = findViewById(R.id.connection_state1);
        mDataField = findViewById(R.id.data_value);
        scanResult = findViewById(R.id.scan_result);
        sendCommand = findViewById(R.id.send_command);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendContent = sendCommand.getText().toString();
                if (!TextUtils.isEmpty(sendContent)) {
                    bluetoothLeDeviceA.writeBuffer(sendContent, new OnWriteCallback() {
                        @Override
                        public void onSuccess() {
                            Log.e(TAG, "write data success:");
                        }

                        @Override
                        public void onFailed(int state) {
                            Log.e(TAG, "write data faile-----" + state);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "no content", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanLeDevice(true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothLeDeviceA.close();
    }

    ArrayList<BluetoothDevice> devices = new ArrayList<>();

    private void scanLeDevice(final boolean enable) {
        startScan(enable);
//        rxPermissions.request(Manifest.permission.BLUETOOTH,
//                Manifest.permission.BLUETOOTH_ADMIN,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//        ;
//                .subscribe(granted -> {
//                    if (granted) {
//
//                    }
//                });

    }

    private void startScan(boolean enable) {
        bluetoothLeDeviceA.scanBleDevice(enable, new OnScanCallback() {
            @Override
            public void onFinish() {
                //这里可以用ListView将扫描到的设备展示出来，然后选择某一个进行连接
                // bluetoothLeDeviceA.connect(devices.get(0).getAddress())
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("扫描成功：\n");
                for (BluetoothDevice device : devices) {
                    if (device.getName() != null) {
                        if (device.getName().equals("SWAN")) {
                            stringBuilder.append(device.getName());
                            stringBuilder.append(",");
                            stringBuilder.append(device.getAddress());
                            stringBuilder.append("\n");
                            bluetoothLeDeviceA.connect(device.getAddress());
                        }
                    }
                }
                scanResult.setText(stringBuilder.toString());
            }

            @Override
            public void onScanning(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                if (!devices.contains(device)) {
                    devices.add(device);
                    scanResult.setText("扫描中........");
                    Log.e(TAG, "onScanning: " + device.getAddress());
                }
            }
        });
    }
}

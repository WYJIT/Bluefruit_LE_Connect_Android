// Original source code: https://github.com/StevenRudenko/BleSensorTag. MIT License (Steven Rudenko)

package com.adafruit.bluefruit.le.connect.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class BleManager implements BleExecutorListener {
    // Log
    private final static String TAG = BleManager.class.getSimpleName();

    // Enumerations
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;

    // Singleton
    private static BleManager mInstance = null;

    // Data
    private final BleGattExecutor mExecutor = BleGattExecutor.createExecutor(this);
    private BluetoothAdapter mAdapter;
    private BluetoothGatt mGatt;
    private Context mContext;

    private BluetoothDevice mDevice;
    private String mDeviceAddress;
    private int mConnectionState = STATE_DISCONNECTED;

    private BleServiceListener mBleListener;

    public static BleManager getInstance(Context context) {
        if(mInstance == null)
        {
            mInstance = new BleManager(context);
        }
        return mInstance;
    }

    public int getState() {
        return mConnectionState;
    }

    public BluetoothDevice getConnectedDevice() {return mDevice;}

    public String getConnectedDeviceAddress() {
        return mDeviceAddress;
    }

    public void setBleListener(BleServiceListener listener) {
        mBleListener = listener;
    }

    public BluetoothAdapter getAdapter() {
        return mAdapter;
    }

    public BleManager(Context context) {
        // Init Adapter
        mContext = context.getApplicationContext();
        if (mAdapter == null) {
            mAdapter = BleUtils.getBluetoothAdapter(mContext);
        }

        if (mAdapter == null || !mAdapter.isEnabled()) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
        }
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result is reported asynchronously through the {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)} callback.
     */
    public boolean connect(Context context, String address) {
        if (mAdapter == null || address == null) {
            Log.w(TAG, "connect: BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Get preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean reuseExistingConnection = sharedPreferences.getBoolean("pref_recycleconnection", false);

        if (reuseExistingConnection) {
            // Previously connected device.  Try to reconnect.
            if (mDeviceAddress != null && address.equalsIgnoreCase(mDeviceAddress) && mGatt != null) {
                Log.d(TAG, "Trying to use an existing BluetoothGatt for connection.");
                if (mGatt.connect()) {
                    mConnectionState = STATE_CONNECTING;
                    if (mBleListener != null)
                        mBleListener.onConnecting();
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            final boolean forceCloseBeforeNewConnection = sharedPreferences.getBoolean("pref_forcecloseconnection", true);

            if (forceCloseBeforeNewConnection) {
                close();
            }
        }

        mDevice = mAdapter.getRemoteDevice(address);
        if (mDevice == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        final boolean gattAutoconnect = sharedPreferences.getBoolean("pref_gattautoconnect", true);

        mGatt = mDevice.connectGatt(mContext, gattAutoconnect, mExecutor);
        Log.d(TAG, "Trying to create a new connection.");
        mDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        if (mBleListener != null) {
            mBleListener.onConnecting();
        }
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)} callback.
     */
    public void disconnect() {
        mDevice = null;

        if (mAdapter == null || mGatt == null) {
            Log.w(TAG, "disconnect: BluetoothAdapter not initialized");
            return;
        }
        mGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are  released properly.
     */
    public void close() {
        if (mGatt != null) {
            mGatt.close();
            mGatt = null;
            mDeviceAddress = null;
            mDevice = null;
        }
    }

    public void readCharacteristic(BluetoothGattService service, String characteristicUUID) {
        readService(service, characteristicUUID, null);
    }

    public void readDescriptor(BluetoothGattService service, String characteristicUUID, String descriptorUUID) {
        readService(service, characteristicUUID, descriptorUUID);
    }

    private void readService(BluetoothGattService service, String characteristicUUID, String descriptorUUID) {
        if (service != null) {
            if (mAdapter == null || mGatt == null) {
                Log.w(TAG, "readService: BluetoothAdapter not initialized");
                return;
            }

            mExecutor.read(service, characteristicUUID, descriptorUUID);
            mExecutor.execute(mGatt);
        }
    }

    public void writeService(BluetoothGattService service, String uuid, byte[] value)
    {
        if (service != null) {
            if (mAdapter == null || mGatt == null) {
                Log.w(TAG, "writeService: BluetoothAdapter not initialized");
                return;
            }

            mExecutor.write(service, uuid, value);
            mExecutor.execute(mGatt);
        }
    }


    public void enableService(BluetoothGattService service, String uuid, boolean enabled) {
        if (service != null) {

            if (mAdapter == null || mGatt == null) {
                Log.w(TAG, "enableService: BluetoothAdapter not initialized");
                return;
            }

            mExecutor.enable(service, uuid, enabled);
            mExecutor.execute(mGatt);
        }
    }


    // Properties
    public int getCharacteristicProperties(BluetoothGattService service, String characteristicUUIDString) {
        final UUID characteristicUuid = UUID.fromString(characteristicUUIDString);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUuid);
        int properties = 0;
        if (characteristic != null) {
            properties = characteristic.getProperties();
        }

        return properties;
    }

    public boolean isCharacteristicReadable(BluetoothGattService service, String characteristicUUIDString) {
        final int properties = getCharacteristicProperties(service, characteristicUUIDString);
        final boolean isReadable = (properties & BluetoothGattCharacteristic.PROPERTY_READ) != 0;
        return isReadable;
    }

    public boolean isCharacteristicNotifiable(BluetoothGattService service, String characteristicUUIDString) {
        final int properties = getCharacteristicProperties(service, characteristicUUIDString);
        final boolean isNotifiable = (properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0;
        return isNotifiable;
    }


    // Permissions
    public int getDescriptorPermissions(BluetoothGattService service, String characteristicUUIDString, String descriptorUUIDString) {
        final UUID characteristicUuid = UUID.fromString(characteristicUUIDString);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUuid);

        int permissions = 0;
        if (characteristic != null) {
            final UUID descriptorUuid = UUID.fromString(descriptorUUIDString);
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUuid);
            if (descriptor != null) {
                permissions = descriptor.getPermissions();
            }
        }

        return permissions;
    }

    public boolean isDescriptorReadable(BluetoothGattService service, String characteristicUUIDString, String descriptorUUIDString) {
        final int permissions = getDescriptorPermissions(service, characteristicUUIDString, descriptorUUIDString);
        final boolean isReadable = (permissions & BluetoothGattCharacteristic.PERMISSION_READ) != 0;
        return isReadable;
    }


    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mGatt != null) {
            return mGatt.getServices();
        } else {
            return null;
        }
    }

    public BluetoothGattService getGattService(String uuid) {
        if (mGatt != null) {
            final UUID serviceUuid = UUID.fromString(uuid);
            return mGatt.getService(serviceUuid);
        } else {
            return null;
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            mConnectionState = STATE_CONNECTED;

            if (mBleListener != null) {
                mBleListener.onConnected();
            }

            // Attempts to discover services after successful connection.
            gatt.discoverServices();

        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            mConnectionState = STATE_DISCONNECTED;

            if (mBleListener != null) {
                mBleListener.onDisconnected();
            }
        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            mConnectionState = STATE_CONNECTING;

            if (mBleListener != null) {
                mBleListener.onConnecting();
            }
        }
    }

    // region BleExecutorListener
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            // Call listener
            if (mBleListener != null)
                mBleListener.onServicesDiscovered();
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (mBleListener != null) {
                mBleListener.onDataAvailable(characteristic);
            }
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        if (mBleListener != null) {
            mBleListener.onDataAvailable(characteristic);
        }
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (mBleListener != null) {
                mBleListener.onDataAvailable(descriptor);
            }
        }
    }
    //endregion
}
package mms.bluetooth;

import java.io.IOException;
import java.util.UUID;

public class BluetoothDevice {
	private android.bluetooth.BluetoothDevice devAndroid;
	
	protected BluetoothDevice(android.bluetooth.BluetoothDevice dev) {
		devAndroid = dev;
	}
	
	public String getName() {
		return devAndroid.getName();
	}

	public String getAddress() {
		return devAndroid.getAddress();
	}

	public BluetoothSocket createRfcommSocketToServiceRecord(UUID uuid) throws IOException {
		return new BluetoothSocket(devAndroid.createRfcommSocketToServiceRecord(uuid));
	}
}

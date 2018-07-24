package mms.bluetooth;

import java.util.HashSet;
import java.util.Set;

public class BluetoothAdapter {

	private android.bluetooth.BluetoothAdapter btAndroid;
	
	public BluetoothAdapter(android.bluetooth.BluetoothAdapter btA) {
		btAndroid = btA;
	}
	
	public static BluetoothAdapter getDefaultAdapter() { 
		return new BluetoothAdapter(android.bluetooth.BluetoothAdapter.getDefaultAdapter());
	}
	
	public static boolean enableBluetooth() {
		//TODO: Implementar activación del bluetooth en Android
		return true;
	}
	
	public Set<BluetoothDevice> getBondedDevices() {
		Set<BluetoothDevice> retD = new HashSet<BluetoothDevice>();
		
		try {
			Set<android.bluetooth.BluetoothDevice> rds = btAndroid.getBondedDevices();
			
			for(android.bluetooth.BluetoothDevice r: rds) {
				retD.add(new BluetoothDevice(r));
			}
		}catch(Exception e) {
			retD = null;
			e.printStackTrace();
		}
		
		return retD;
	}
}

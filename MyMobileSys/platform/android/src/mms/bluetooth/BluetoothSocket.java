package mms.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothSocket {

	private android.bluetooth.BluetoothSocket bsAndroid;
	
	public BluetoothSocket(android.bluetooth.BluetoothSocket bs) {
		bsAndroid = bs;
	}
	
	public void connect() throws IOException {
		bsAndroid.connect();
	}
	
	public InputStream getInputStream() throws IOException{
		return bsAndroid.getInputStream();
	}
	
	public OutputStream getOutputStream() throws IOException{
		return bsAndroid.getOutputStream();
	}
	
	public void close() throws IOException{
		bsAndroid.close();
	}
}

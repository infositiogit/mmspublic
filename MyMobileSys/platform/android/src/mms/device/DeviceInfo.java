package mms.device;

import mms.app.MMSApp;
import android.content.Context;
import android.telephony.TelephonyManager;

//Clase que permite obtener información del dispositivo.
public class DeviceInfo {
	public static final int DESKTOP = 1;
	public static final int ANDROID = 2;
	public static final int IOS = 3;
	
	public static String getID() {
		TelephonyManager telephonyManager = (TelephonyManager)MMSApp.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return(telephonyManager.getDeviceId());	
	}

	public static int getPlatform() {
		return ANDROID;
	}
}

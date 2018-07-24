package mms.location;

import mms.app.MMSActivity;
import mms.app.MMSApp;

public class Location {
	
	public static void initUpdates(long interval, LocationUpdatesListener listener) {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		mmsAct.initLocationUpdates(interval, listener);
	}
	
	public static void startUpdates() {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		mmsAct.startLocationUpdates();
	}
	
	public static void stopUpdates() {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		mmsAct.stopLocationUpdates();
	}
}

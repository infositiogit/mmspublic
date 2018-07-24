package mms.location.gps;

import mms.app.MMSApp;
import mms.location.LocationData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class GPS {
	
	private int interval;
	private GPSListener gpsListener;
	private GPSLocationListener locListener;
	private boolean updating = false;
	
	public GPS(int ival, GPSListener gpsl) {
		interval = ival;
		gpsListener = gpsl;
		updating = false;
	}
	
	public boolean start() {
		boolean ret = true;
		
		if(gpsListener != null) {
			Context ctx = MMSApp.getContext();
			
			final LocationManager locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
			
			locListener = new GPSLocationListener();
			locListener.setGpsListener(gpsListener);
			
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {
			     public void run() {
			    	 locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, interval, 0, locListener);
			     }
			});
			
			updating = true;
		} else {
			ret = false; //Sin listener no se inician los updates
		}
		
		return ret;
	}
	
	public boolean stop() {
		boolean ret = true;
		
		if(updating) {
			Context ctx = MMSApp.getContext();
			LocationManager locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
			locManager.removeUpdates(locListener);
			updating = false;
		}
		
		return ret;
	}
	
	public static boolean isEnabled() {
		Context ctx = MMSApp.getContext();
		LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

	    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	private class GPSLocationListener implements LocationListener{

		private GPSListener gpsListener;
		
		@SuppressWarnings("unused")
		public GPSListener getGpsListener() {
			return gpsListener;
		}

		public void setGpsListener(GPSListener gpsListener) {
			this.gpsListener = gpsListener;
		}

		@Override
		public void onLocationChanged(Location l) {
			// TODO Auto-generated method stub
			if(gpsListener != null) {
				LocationData ld = new LocationData();
				
				ld.setLatitude(l.getLatitude());
				ld.setLongitude(l.getLongitude());
				ld.setAccuracy(l.getAccuracy());
				ld.setTime(l.getTime());
				
				gpsListener.locationGPSUpdate(ld);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			if(gpsListener != null) gpsListener.disabled();
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			if(gpsListener != null) gpsListener.enabled();
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

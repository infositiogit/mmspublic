package mms.location.gps;

import mms.location.LocationData;

public interface GPSListener {
	
	public void locationGPSUpdate(LocationData ld);
	public void disabled();
	public void enabled();
}

package mms.google.drive;

public interface GoogleDriveResultListener {
	public void success(GoogleDrive gd);
	public void error(long errorcode);
}

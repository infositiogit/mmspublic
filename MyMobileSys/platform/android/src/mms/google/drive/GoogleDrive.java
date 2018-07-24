package mms.google.drive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mms.app.MMSActivity;
import mms.app.MMSApp;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Export;
import com.google.api.services.drive.Drive.Files.Get;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GoogleDrive {
	/**
	 * 
	 */
	private final int ERROR_AUTH_PERMISSION = 1;
	
	public static final int REQUEST_CODE_RESOLUTION = 8000; //OJO
	public static final int REQUEST_AUTHORIZATION = 8001;//OJO
	public static final int REQUEST_ACCOUNT_PICKER = 8002;//OJO
	
	private Drive drive;
	private GoogleDriveResultListener googleDriveResultListener;
	private boolean connected = false;
	private int lastError;
	private String client_id; 
	
	public void connect(GoogleDriveResultListener gdr) {
		googleDriveResultListener = gdr;

		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		if(mmsAct != null) {
			mmsAct.startGoogleDriveConnection(this, gdr);
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public ArrayList<GoogleFile> list(String fileid) {
		ArrayList<GoogleFile> arrgf = null;

		FileList result;
		try {

			result = drive.files().list()
					.setFields("nextPageToken, files(id, name, mimeType, webContentLink)")
					.setQ("'" + fileid + "' in parents and trashed=false")
					.execute();

			List<File> files = result.getFiles();
			if (files != null) {
				arrgf = new ArrayList<GoogleFile>();
				for ( com.google.api.services.drive.model.File file: files) {
					GoogleFile gf = new GoogleFile();
					gf.setName(file.getName());
					gf.setId(file.getId());
					gf.setMimeType(file.getMimeType());
					gf.setDownloadUrl(file.getWebContentLink());
					arrgf.add(gf);
					System.out.println("File: " + file.getId() + " " + file.getName() + " " + file.getMimeType());
				}
			}
		} catch (UserRecoverableAuthIOException e) {
			lastError = ERROR_AUTH_PERMISSION;
			MMSActivity mmsAct = MMSApp.getMMSActivity();
			
			if(mmsAct != null) {
				mmsAct.startRecoverableAuth(e);
			}
		} catch (Exception e) {
			arrgf = null;
			e.printStackTrace();
		}
		return arrgf;
	}

	public boolean downloadFile(String fileid, mms.io.File f) {
		Get gf;
		boolean ret = true;
		try {
			gf = drive.files().get(fileid);
			FileOutputStream fo = new FileOutputStream(f.getAbsolutePath());
			gf.executeMediaAndDownloadTo(fo);
			fo.flush();
			fo.close();
		} catch (IOException e) {
			ret = false;
		}
		return ret;
	}
	
	public boolean export(String fileid, String mimeType, mms.io.File f) {
		Export ge;
		boolean ret = true;
		try {
			ge = drive.files().export(fileid, mimeType);
			FileOutputStream fo = new FileOutputStream(f.getAbsolutePath());
			ge.executeMediaAndDownloadTo(fo);
			fo.flush();
			fo.close();
		} catch (IOException e) {
			ret = false;
		}
		return ret;
	}
	
	public Drive getDrive() {
		return drive;
	}

	public void setDrive(Drive drive) {
		this.drive = drive;
	}
	
	//Solo necesario invocarlo en Desktop
	public void setClientId(String cid) {
		client_id = cid;
	}
}

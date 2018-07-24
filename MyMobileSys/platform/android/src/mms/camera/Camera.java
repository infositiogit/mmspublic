package mms.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mms.app.MMSActivity;
import mms.app.MMSApp;
import mms.lib.ImagesUtil;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class Camera {
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 101;
	private Uri fileUri;
	private mms.io.File filePicture;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private boolean compressPicture = false; 
	private int changeQuality = 100;

	private final int C_MAXWIDTH = 1024;
	private final int C_MAXHEIGHT = 768;

	public static final boolean checkCameraHardware() {
		Context context = MMSApp.getContext();
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	public void capture(mms.io.File f, CameraListener lstCamera) {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		if(mmsAct != null) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			filePicture = f;
			if(f == null) 
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			else
				fileUri = Uri.fromFile(f._getFileAndroid());

			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			mmsAct.setCameraPicture(this);
			// start the image capture Intent
			mmsAct.startCameraForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, lstCamera);
		}
	}

	public void captureVideo(mms.io.File f, CameraListener lstCamera) {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		if(mmsAct != null) {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

			filePicture = f;
			if(f == null) 
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO); // create a file to save the image
			else
				fileUri = Uri.fromFile(f._getFileAndroid());

			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			mmsAct.setCameraPicture(this);
			// start the image capture Intent
			mmsAct.startCameraForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE, lstCamera);
		}
	}
	
	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), "MMS");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("MMS", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");
		} else if(type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"VID_"+ timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	public boolean isCompressPicture() {
		return compressPicture;
	}

	//Cambia la resolucion de la imagen a un tamaño menor a 1024x768
	public void setCompressPicture(boolean compressPicture) {
		this.compressPicture = compressPicture;
	}

	public void setPictureOptions(int quality) {
		changeQuality = quality;
	}
	
	public mms.io.File _getFile() {
		return(filePicture);
	}
	
	public void _compressPicture() {
		if(this.isCompressPicture()) {
			ImagesUtil iu = new ImagesUtil();

			Bitmap foto = iu.obtenerBitmapEscaladoRotado(filePicture.getAbsolutePath(), C_MAXWIDTH, C_MAXHEIGHT, 0);
			FileOutputStream out;
			try {
				out = new FileOutputStream(filePicture._getFileAndroid());
				foto.compress(Bitmap.CompressFormat.JPEG, changeQuality, out);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

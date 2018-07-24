package mms.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mms.app.MMSApp;
import mms.io.File;
import mms.io.FileFilter;
import android.os.Environment;

public class File {
	protected java.io.File fileAndroid;
	private String path;
	
	public File(String p) {
		path = p;
		fileAndroid = new java.io.File(path);
	}
	
	public File(java.io.File f) {
		fileAndroid = f;
		path = f.getAbsolutePath();
	}
	
	public File(File dir, String f) {
		fileAndroid = new java.io.File(dir._getFileAndroid(), f);
		path = fileAndroid.getAbsolutePath();
	}
	
	public boolean createNewFile() throws IOException {
		return fileAndroid.createNewFile();
	}
	
	public boolean mkdirs() {
		return(fileAndroid.mkdirs());
	}
	
	public java.io.File _getFileAndroid() {
		return(fileAndroid);
	}
	
	public FileInputStream openFileInput() throws FileNotFoundException {
		FileInputStream is = new FileInputStream(fileAndroid);
		return(is);
	}
	
	public FileOutputStream openFileOutput() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream(fileAndroid);
		return(os);
	}

	public String getAbsolutePath() {
		return(fileAndroid.getAbsolutePath());
	}

	public boolean exists() {
		return(fileAndroid.exists());
	}
	
	/* Checks if external storage is available for read and write */
	public static final boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	/* Checks if external storage is available to at least read */
	public static final boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public boolean delete() {
		boolean ret = true;
		
		try {
			fileAndroid.delete();
		}catch(Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static final File getInternalDataDir() {
		java.io.File f = MMSApp.getContext().getFilesDir();
		return(new File(f));
	}
	
	public static final File getExternalDataDir(boolean pub) {
		java.io.File f;
		if(pub) {
			java.io.File fdata = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
			f = new java.io.File(fdata, "DATA");
			if(!f.exists()) {
				f.mkdirs();
			}
		}
		else
			f = MMSApp.getContext().getExternalFilesDir(null);
		
		return(new File(f));
	}	
	
	public String getName() {
		return fileAndroid.getName();
	}

	public String getPath() {
		return fileAndroid.getPath();
	}

	public String[] list() {
		return fileAndroid.list();
	}

	public File[] listFiles() {
		java.io.File[] filesA = fileAndroid.listFiles();

		if(filesA != null) {
			ArrayList<File> af = new ArrayList<File>();

			for(java.io.File f: filesA) {
				af.add(new File(f));
			}
			
			return (File[])af.toArray(new File[0]);
		} else {
			return null;
		}
		
	}
	
	public File[] listFiles(FileFilter filter) {
		java.io.File[] filesA = fileAndroid.listFiles();

		if(filesA != null) {
			ArrayList<File> af = new ArrayList<File>();

			for(java.io.File f: filesA) {
				File mmsf = new File(f);
				if(filter.accept(mmsf))
					af.add(mmsf);
			}
			
			return (File[])af.toArray(new File[0]);
		} else {
			return null;
		}
		
	}
	
	public static final File[] getStoragePaths() {
		StoragePath storagePath;
        storagePath = new StoragePath(MMSApp.getContext().getExternalFilesDirs(null));

		String[] storages;
		storages = storagePath.getDeviceStorages();

		if(storages != null) {
			ArrayList<File> af = new ArrayList<File>();

			for(String f: storages) {
				af.add(new File(f));
			}
			
			return (File[])af.toArray(new File[0]);
		} else {
			return null;
		}
	}
	
	public FileReader getFileReader() throws FileNotFoundException {
		return new FileReader(fileAndroid);
	}

	public FileWriter getFileWriter() throws IOException {
		return new FileWriter(fileAndroid);
	}
	
	public static final File getTmpDir() {
		return(getExternalDataDir(false));
	}
	
	public static void copyFile(File fromFile, File toFile, boolean overwrite) {

	}
	
	public boolean isDirectory() {
		return fileAndroid.isDirectory();
	}
	
	public boolean isFile() {
		return fileAndroid.isFile();
	}
	
	public long length() {
		return fileAndroid.length();
	}
}

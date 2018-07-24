/*
 * 
 */
package mms.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class MMSApp.
 */
public class MMSApp extends Application {
	
	/** The context. */
	private static Context context;
	private static MMSActivity mmsActivity = null;
	
	private static HashMap<String, Object> htData = new HashMap<String,Object>(10);

	private static ThreadPoolExecutor executorEvents;  
	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public static Context getContext() {
		return context;
	}

	public static MMSActivity getMMSActivity() {
		return mmsActivity;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		refreshContext();
		//Log.i("MMS", "App Create");
	}
	
	public void refreshContext() {
		MMSApp.context = getApplicationContext();
	}
	
	public static void refreshActivity(MMSActivity a) {
		MMSApp.mmsActivity = a;
	}

	public static void setData(String key, String value) {
		if(value == null)
			htData.remove(key);
		else
			htData.put(key,  value);
	}

	public static void setData(String key, boolean value) {
		htData.put(key, value);
	}
	
	public static void setData(String key, int value) {
		htData.put(key, value);
	}
	
	public static void setData(String key, long value) {
		htData.put(key, value);
	}
	/*
	public static void setData(String key, String value) {
		setData(key, value);
	}
	*/
	public static void setData(String key, double value) {
		htData.put(key, value);
	}
	
	public static void setData(String key, float value) {
		htData.put(key, value);
	}
	
	public static boolean containsKey(String key) {
		return(htData.containsKey(key));
	}
	
	public static Object getData(String key) {
		return(htData.get(key));
	}

	public static boolean getDataBoolean(String key) {
		Object v = htData.get(key);
		boolean ret = false;
		
		if(v instanceof Boolean)
			ret = ((Boolean)v).booleanValue();
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	public static int getDataInteger(String key) {
		Object v = htData.get(key);
		int ret = 0;
		
		if(v instanceof Integer)
			ret = ((Integer)v).intValue();
		else if(v instanceof Double)
			ret = ((Double)v).intValue();
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	public static long getDataLong(String key) {
		Object v = htData.get(key);
		long ret = 0;
		
		if(v instanceof Long)
			ret = ((Long)v).longValue();
		else if(v instanceof Double)
			ret = ((Double)v).longValue();
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	public static double getDataDouble(String key) {
		Object v = htData.get(key);
		double ret = 0;
		
		if(v instanceof Double)
			ret = ((Double)v).doubleValue();
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	
	public static float getDataFloat(String key) {
		Object v = htData.get(key);
		float ret = 0;
		
		if(v instanceof Float)
			ret = ((Float)v).floatValue();
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	public static String getDataString(String key) {
		Object v = htData.get(key);
		String ret = null;
		
		if(v instanceof String)
			ret = (String)v;
		else
			throw new ClassCastException();
		
		return ret;
	}
	
	public static void exit() {
		MMSApp.getMMSActivity().finish();
	}
	
	public static String getAssetTextFile(String file, String charset) {
        String tContents = "";
        
	    try {
	        InputStream stream = getContext().getAssets().open(file);
	
	        int size = stream.available();
	        byte[] buffer = new byte[size];
	        stream.read(buffer);
	        stream.close();
	        if(charset != null)
	        	tContents = new String(buffer, charset);
	        else
	        	tContents = new String(buffer);
	    } catch (IOException e) {
	        // Handle exceptions here
	    }
	
	    return tContents;
	}
	
	public static String getAssetTextFile(String file) {
		return(getAssetTextFile(file, null));
	}
	
	public static ThreadPoolExecutor getExecutorEvent() {
		if(executorEvents == null) {
			 executorEvents = (ThreadPoolExecutor)Executors.newCachedThreadPool();//Crea el objeto executor o un pool de threads
		}
		
		return executorEvents;
	}

	/*
	public static void setDataFromBundle(Bundle bundle) {
		htData = (HashMap<String,Object>)bundle.getSerializable("__mmsappdata__");
	}
	*/
	public static void saveData() {
		SharedPreferences sp = MMSApp.getContext().getSharedPreferences("mms.app.MMSApp.data", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		String j = gson.toJson(htData);
		
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("htData", j);
		editor.commit();

		return;
	}
	
	public static void restoreData() {
		SharedPreferences sp = MMSApp.getContext().getSharedPreferences("mms.app.MMSApp.data", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		
		String d = sp.getString("htData", "");
		
		if(d.length() > 0)
			htData = gson.fromJson(sp.getString("htData", ""), HashMap.class);
		
	}
	
	public static void clearSavedData() {
		SharedPreferences sp = MMSApp.getContext().getSharedPreferences("mms.app.MMSApp.data", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		editor.clear();
		editor.commit();
	}
	
	public static boolean existSavedData() {
		SharedPreferences sp = MMSApp.getContext().getSharedPreferences("mms.app.MMSApp.data", Context.MODE_PRIVATE);
		return sp.contains("htData");
	}
	
	public static boolean openUrl(String url) {
		boolean ret = true;
		try
		{
			if(mmsActivity != null) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
				mmsActivity.startActivity( intent );
			} else {
				ret = false;
			}
		}
		catch ( ActivityNotFoundException ex  )
		{
			ret = false;
		}
		return ret;
	}
} 
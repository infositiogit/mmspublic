package mms.database.sqlite;

import java.io.File;
import java.util.Arrays;

import mms.app.MMSApp;
import android.app.Activity;
import android.content.Context;


// TODO: Auto-generated Javadoc
/**
 * The Class SQLiteDatabase.
 */
/**
 * @author Claudio
 *
 */
public class SQLiteDatabase {
	
	/** The android db. */
	protected android.database.sqlite.SQLiteDatabase androidDB; 
	
	/** The path name. */
	protected String pathName;
	
	/**
	 * Instantiates a new SQL lite database.
	 *
	 * @param pName the name
	 * @throws SQLiteException the SQ lite exception
	 */
	public SQLiteDatabase(String pName) throws SQLiteException{
		try {
			pathName = pName;
			File fname = new File(pName);
			
			if(fname.getParent() == null) {
				fname = MMSApp.getContext().getDatabasePath(pName);
				pathName = fname.getPath();
			}
			
			//androidDB = android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(fname, null);
			androidDB = MMSApp.getContext().openOrCreateDatabase(pName, Activity.MODE_PRIVATE, null);
			
		}catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}

	public static boolean exist(String dbName) {
		boolean ret = false;
		Context ctx = MMSApp.getContext();
		
		String[] list = ctx.databaseList();
		ret = Arrays.asList(list).contains(dbName);
		
		return(ret);
	}
	
	/**
	 * Checks if is open.
	 *
	 * @return true, if is open
	 */
	public boolean isOpen() {
		boolean ret = false;
		
		if(androidDB != null) {
			ret = androidDB.isOpen();
		}
		
		return(ret);
	}
	
	/**
	 * Close.
	 */
	public void close() {
		if(isOpen()) {
			androidDB.close();
			androidDB = null;
		}
	}
	
	/**
	 * R query.
	 *
	 * @param sql the sql
	 * @param args the args
	 * @return the SQ lite cursor
	 */
	public SQLiteCursor rQuery(String sql, String[] args) {
		SQLiteCursor ret = new SQLiteCursor();
		
		ret.setAndroidCursor(androidDB.rawQuery(sql, args));
		
		return(ret);
	}
	
	/**
	 * Exec sql.
	 *
	 * @param sql the sql
	 */
	public void execSQL(String sql) {
		try {
			androidDB.execSQL(sql);
		}catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}
	
	/**
	 * Compile statement.
	 *
	 * @param sql the sql
	 * @return the SQ lite statement
	 */
	public SQLiteStatement compileStatement(String sql) {
		SQLiteStatement st = null;
	
		try {
			st = new SQLiteStatement();
			st.setAndroidSQLiteStatement(androidDB.compileStatement(sql));
			
			return(st);
		} catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
		
	}
	
	public void beginTransaction() {
		try {
			androidDB.beginTransaction();
		} catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}
	
	public void endTransaction() {
		try {
			androidDB.endTransaction();
		} catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}
	
	public void setTransactionSuccessful() {
		try {
			androidDB.setTransactionSuccessful();
		} catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}
	
	public static String getDatabasePath(String dbname) {
		return MMSApp.getContext().getDatabasePath(dbname).getAbsolutePath();
	}
}

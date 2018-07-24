package mms.database.sqlite;

import android.database.Cursor;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLiteCursor.
 */
public class SQLiteCursor {

	/** The cursor. */
	private Cursor cursor;
	
	/**
	 * Sets the android cursor.
	 *
	 * @param c the new android cursor
	 */
	protected void setAndroidCursor(Cursor c) {
		cursor = c;
	}
	
	public boolean moveToNext() {
		return(cursor.moveToNext());
	}
	
	public boolean moveToFirst() {
		return(cursor.moveToFirst());
	}
	
	public boolean moveToLast() {
		return(cursor.moveToLast());
	}
	
	public boolean moveToPrevious() {
		return(cursor.moveToPrevious());
	}
	
	public void close() {
		cursor.close();
	}
	
	public int getColumnCount() {
		return(cursor.getColumnCount());
	}
	
	/*
	public int getCount() {
		return(cursor.getCount());
	}
	*/
	
	public boolean move(int offset) {
		return(cursor.move(offset));
	}
	
	public int getColumnIndex(String columnName) {
		return(cursor.getColumnIndex(columnName));
	}
	
	public int getInt(int columnIndex) {
		return(cursor.getInt(columnIndex));
	}
	
	public long getLong(int columnIndex) {
		return(cursor.getLong(columnIndex));
	}
	
	public String getString(int columnIndex) {
		return(cursor.getString(columnIndex));
	}

	public double getDouble(int columnIndex) {
		return(cursor.getDouble(columnIndex));
	}

	public float getFloat(int columnIndex) {
		return(cursor.getFloat(columnIndex));
	}
	
	public int getPosition() {
		return(cursor.getPosition());
	}
}

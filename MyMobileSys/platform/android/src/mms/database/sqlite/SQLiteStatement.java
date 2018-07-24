package mms.database.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLiteStatement.
 */
public class SQLiteStatement {
	
	/** The stmt. */
	private android.database.sqlite.SQLiteStatement stmt;
	
	/**
	 * Sets the android sq lite statement.
	 *
	 * @param s the new android sq lite statement
	 */
	protected void setAndroidSQLiteStatement(android.database.sqlite.SQLiteStatement s) {
		stmt = s;
	}
	
	/**
	 * Close.
	 */
	public void close() {
		try {
			stmt.close();
		}catch(Exception e) {
			throw new SQLiteException(e.toString());
		}
	}
	
	/**
	 * Bind long.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public void bindLong(int index, long value) {
		try {
			stmt.bindLong(index, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	
	/**
	 * Bind double.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public void bindDouble(int index, double value) {
		try {
			stmt.bindDouble(index, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	
	
	/**
	 * Bind string.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public void bindString(int index, String value) {
		try {
			if(value == null)
				stmt.bindNull(index);
			else
				stmt.bindString(index, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}

	/**
	 * Bind int.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public void bindInt(int index, int value) {
		try {
			stmt.bindLong(index, (long)value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	/**
	 * Bind blob.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public void bindBlob(int index, byte[] value) {
		try {
			if(value == null)
				stmt.bindNull(index);
			else
				stmt.bindBlob(index, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		try {
			stmt.clearBindings();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	
	
	/**
	 * Execute this SQL statement, if it is not a SELECT / INSERT / DELETE / UPDATE, for example CREATE / DROP table, view, trigger, index etc.
	 */
	public void execute() {
		try {
			stmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	

	public long executeInsert() {
		try {
			return(stmt.executeInsert() > 0 ? 1: 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	
	public int executeUpdateDelete() {
		try {
			return(stmt.executeUpdateDelete() > 0 ? 1: 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SQLiteException(e.toString());
		}
	}
	
}

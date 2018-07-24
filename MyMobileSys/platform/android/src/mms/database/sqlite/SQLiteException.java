package mms.database.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLiteException.
 */
public class SQLiteException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1444899121602782391L;

    /**
     * Instantiates a new SQ lite exception.
     */
    public SQLiteException() {
    }

    /**
     * Instantiates a new SQ lite exception.
     *
     * @param error the error
     */
    public SQLiteException(String error) {
        super(error);
    }

    /**
     * Instantiates a new SQ lite exception.
     *
     * @param error the error
     * @param cause the cause
     */
    public SQLiteException(String error, Throwable cause) {
        super(error, cause);
    }
}

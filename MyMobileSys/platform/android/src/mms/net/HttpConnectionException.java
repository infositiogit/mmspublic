package mms.net;

import java.io.IOException;

public class HttpConnectionException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -18324795886654599L;

	/**
     * Constructs a new instance of this class with its walkback filled in.
     */
    public HttpConnectionException() {
        super();
    }

    /**
     * Constructs a new instance of this class with its walkback and message
     * filled in.
     * 
     * @param detailMessage
     *            the detail message for this exception instance.
     */
    public HttpConnectionException(String detailMessage) {
        super(detailMessage);
    }
}

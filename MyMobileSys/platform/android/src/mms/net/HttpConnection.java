package mms.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HttpConnection {

	protected URL url;
	protected HttpURLConnection http = null;
	
    public final static int HTTP_OK = 200;
    
	public HttpConnection(URL u) throws HttpConnectionException {
		url = u;
		
		if(url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
			try {
				http = (HttpURLConnection)url.urlN.openConnection();
			} catch (IOException e) {
				throw new HttpConnectionException(e.getMessage());
			}
		} else {
			throw new HttpConnectionException("Protocol error: " + url.getProtocol());
		} 
	}
	
	public void setReadTimeout(int t) {
		if(http != null) http.setReadTimeout(t);
	}
	
	public void setConnectTimeout(int t) {
		if(http != null) http.setConnectTimeout(t);
	}
	
	public void setRequestMethod(String method) throws HttpConnectionException{
		try {
			http.setRequestMethod(method);
		} catch(Exception e) {
			throw new HttpConnectionException(e.toString());
		}
	}
	
	public void setRequestProperty(String field, String newValue) throws HttpConnectionException {
		if(http == null) {
			throw new HttpConnectionException("Not connected");
		} else {
			http.setRequestProperty(field, newValue);
		}
	}
	
	public OutputStream getOutputStream() throws IOException, HttpConnectionException {
		if(http == null) {
			throw new HttpConnectionException("Not connected");
		}
		
		http.setDoOutput(true);
		return(http.getOutputStream());
	}
	
	
	public InputStream getInputStream() throws IOException, HttpConnectionException {
		if(http == null) {
			throw new HttpConnectionException("Not connected");
		}
		
		return(http.getInputStream());
	}
	
	public int getResponseCode() throws IOException, HttpConnectionException {
		if(http == null) {
			throw new HttpConnectionException("Not connected");
		}
		
		return(http.getResponseCode());
	}
}

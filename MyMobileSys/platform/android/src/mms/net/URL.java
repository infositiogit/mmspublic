package mms.net;

import java.net.MalformedURLException;

public class URL {

	protected java.net.URL urlN;
	
	public URL(String url) throws MalformedURLException{
		urlN = new java.net.URL(url);
	}
	
	public String getProtocol() {
		return(urlN.getProtocol());
	}
	
}

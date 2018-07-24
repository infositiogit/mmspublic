package mms.util;

public class Base64 {
	
	public static byte[] decode(String data) {
		return android.util.Base64.decode(data, android.util.Base64.DEFAULT);
	}
	
	public static byte[] encode(String data) {
		return android.util.Base64.encode(data.getBytes(), android.util.Base64.DEFAULT);
	}
	
	public static byte[] encode(byte[] data) {
		return android.util.Base64.encode(data, android.util.Base64.DEFAULT);
	}
}

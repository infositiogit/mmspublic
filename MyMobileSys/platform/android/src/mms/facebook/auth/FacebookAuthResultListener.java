package mms.facebook.auth;

public interface FacebookAuthResultListener {
	public void success(AccountDataFacebook ac);
	public void cancel();
	public void error(String errormessage);
}

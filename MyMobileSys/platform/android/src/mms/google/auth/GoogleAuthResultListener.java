package mms.google.auth;

public interface GoogleAuthResultListener {
	public void success(AccountData ac);
	public void error(long errorcode);
}

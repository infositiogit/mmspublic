package mms.google.auth;

import mms.app.MMSActivity;
import mms.app.MMSApp;

public class GoogleAuthSign {
	public static final int RESULT_SIGN_IN = 9001; 
	private String client_id = null;

	public GoogleAuthSign(String cid) {
		client_id = cid;
	}
	
	public void executeSign(GoogleAuthResultListener googleAuthResultListener) {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		if(mmsAct != null) {
			mmsAct.startGoogleSignInForResult(this, googleAuthResultListener);
		}
	}
	
	public String getClientId() {
		return client_id;
	}
}

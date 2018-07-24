package mms.facebook.auth;

import mms.app.MMSActivity;
import mms.app.MMSApp;

public class FacebookAuthSign {
	public static final int RESULT_SIGN_IN_FACEBOOK = 9200; 

	public FacebookAuthSign() {
	}
	
	public void executeSign(FacebookAuthResultListener faceAuthResultListener) {
		MMSActivity mmsAct = MMSApp.getMMSActivity();
		
		if(mmsAct != null) {
			mmsAct.startFacebookSignIn(this, faceAuthResultListener);
		}
	}
	
}

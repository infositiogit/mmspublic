package mms.util;

import android.text.TextUtils;

public class Html {
	
	public static String escape(String html) {
		return(TextUtils.htmlEncode(html));
	}
	
}

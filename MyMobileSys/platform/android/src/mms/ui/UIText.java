package mms.ui;

import android.util.Log;

public class UIText extends UIControl{

	private String text = "";
	
	public UIText(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return(text);
	}
	
	public void setText(String t) {
		text = t;
		String textSpecial = t.replace("'", "\\'");

		uiScreen.executeJSView("setTextControl('" + getId() + "','" + textSpecial + "')");
	}

	public void setLabel(String l) {
		String textSpecial = l.replace("'", "\\'");
		
		uiScreen.executeJSView("setLabelControl('label_" + getId() + "','" + textSpecial + "')");
	}
	
	public void setTextView(String t) {
		text = t;
	}

	@Override
	public void setEnable(boolean enable) {
		super.setEnable(enable);
		if(enable)
			uiScreen.executeJSView("enableText('" + getId() + "','" + getParentPanel().getId() + "')");
		else
			uiScreen.executeJSView("disableText('" + getId() +  "','" + getParentPanel().getId() + "')");
	}
}

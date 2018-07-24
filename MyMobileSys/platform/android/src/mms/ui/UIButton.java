package mms.ui;

public class UIButton extends UIControl {

	protected String text = "";
	
	public UIButton(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public UIButton() {
		super();
	}

	public void setText(String txt) {
		text = txt;
		
		if(uiScreen != null) { //Si esta uiScreen está en nulo significa que aun no se agrega al screen web
			String textSpecial = txt.replace("'", "\\'");
			uiScreen.executeJSView("setTextButton('" + getId() + "','" + textSpecial + "')");
		}
	}
	
	@Override
	public void setEnable(boolean enable) {
		super.setEnable(enable);
		if(enable)
			uiScreen.executeJSView("enableButton('" + getId() + "')");
		else
			uiScreen.executeJSView("disableButton('" + getId() + "')");
	}
	
	@Override
	protected void createUI() {
		String shtml = "<input type=\"button\" id=\"" + getId() + "\" value=\"" + text + "\">";
		uiScreen.executeJSView("createButton('" + shtml + "','" + getId() + "','" + getIdParent() + "')");
		super.createUI();
	}
	
}

package mms.ui;

public class UIHtml extends UIControl {

	public UIHtml(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public UIHtml(){
		super();
	}
	
	public void setHtml(String shtml) {
		shtml = shtml.replace("'", "\\'").replace("\r", "").replace("\n", "").replace("\t", "");
		uiScreen.executeJSView("setHtmlControl('" + getId() + "','" + shtml + "')");
	}
	
	@Override
	protected void createUI() {
		uiScreen.executeJSView("createHtml2('" + getId() + "','" + getIdParent() + "')");
		super.createUI();
	}
	
	@Override
	protected void removeUI() {
		uiScreen.executeJSView("removeHtml('" + getId() + "')");
	}
}

package mms.ui;


public class UIGridCol extends UIContainer {

	protected String block = "";
	
	public UIGridCol(String nid) {
		super(nid);
	}

	public UIGridCol() {
		super();
	}
	
	@Override
	public void createUI() {
		String shtml = "<div class=\"ui-block-" +  block + "\" id=\"" + getId() + "\"></div>";
		uiScreen.executeJSView("createGridCol('" + shtml + "','" + getId() + "','" + getIdParent() + "')");
		super.createUI();
	}
	
}

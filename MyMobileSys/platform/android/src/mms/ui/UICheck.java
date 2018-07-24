package mms.ui;


public class UICheck extends UIControl{

	private boolean checked = false;
	protected String text = "";
	
	public UICheck(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public UICheck() {
		super();
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked, boolean changeview) {
		this.checked = checked;
	
		if(changeview)
			uiScreen.executeJSView("setCheckControl('" + getId() + "'," + checked + ")");
	}

	public void setChecked(boolean checked) {
		setChecked(checked, true);
	}
	
	@Override
	protected void createUI() {
		String shtml = "<input type=\"checkbox\" name=\"" + getId() + "\" id=\"" + getId() + "\">";
		shtml += "<label for=\"" + getId() + "\">" + getText() + "</label>";
		uiScreen.executeJSView("createCheck('" + shtml + "','" + getId() + "','" + getIdParent() + "')");
		setChecked(false);
		super.createUI();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void setEnable(boolean enable) {
		super.setEnable(enable);
		if(enable)
			uiScreen.executeJSView("enableCheck('" + getId() + "')");
		else
			uiScreen.executeJSView("disableCheck('" + getId() + "')");
	}

	@Override
	protected void removeUI() {
		uiScreen.executeJSView("removeCheck('" + getId() + "')");
	}
	
	
}

package mms.ui;


public class UIRadio extends UIControl{

	protected boolean checked = false;
	protected UIRadioGroup rgParent = null; 
	protected String text = "";
	
	protected int indexRG = 0;
	
	public UIRadio(String nid) {
		super(nid);
	}

	public UIRadio() {
		super();
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked, boolean changeview) {
		this.checked = checked;
	
		if(checked) {
			rgParent.setCheckedRadio(this);
		}
		if(changeview)
			uiScreen.executeJSView("setCheckRadioControl('" + getId() + "'," + checked + ")");
	}

	public void setChecked(boolean checked) {
		setChecked(checked, true);
	}

	public UIRadioGroup getRgParent() {
		return rgParent;
	}

	public void setRgParent(UIRadioGroup rgParent) {
		this.rgParent = rgParent;
	}
	
	@Override
	protected void createUI() {
		String namerg = "";
		
		if(rgParent != null)
			namerg = rgParent.getId();
		
 		String sHtml = "<input type=\"radio\" name=\"" + namerg + "\" id=\"" + getId() + "\" value=\"choice-" + getIndexRG() + "\" " + (checked ? "checked=\"checked\"": "") + ">" +
 					   "<label for=\"" + getId() + "\">" + getText() + "</label>";
 		uiScreen.executeJSView("createRadio('" + sHtml + "','" + getId() + "','" + namerg + "')");
 		super.createUI();
	}

	public int getIndexRG() {
		return indexRG;
	}

	public void setIndexRG(int indexRG) {
		this.indexRG = indexRG;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

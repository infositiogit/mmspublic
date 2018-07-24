package mms.ui;

import java.util.Enumeration;
import java.util.Hashtable;
/*
 * Created on 13-02-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UIRadioGroup extends UIControl{
	protected Hashtable<String, UIRadio> htRadios;
	protected UIRadio checkedRadio = null; 
	protected String label = "";
	
	public UIRadioGroup(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
		htRadios = new Hashtable<String, UIRadio>();
	}
	
	public UIRadioGroup() {
		super();
		htRadios = new Hashtable<String, UIRadio>();
	}
	
	public void add(UIRadio rd) {
		htRadios.put(rd.getId(), rd);
		rd.setIndexRG(htRadios.size() - 1);
		rd.setRgParent(this);
		registerControl(rd);
	}

	protected void setCheckedRadio(UIRadio radio) {
		UIRadio rd = null;
		
		checkedRadio = radio;
		
		Enumeration<UIRadio> enumR = htRadios.elements();
		while(enumR.hasMoreElements()) {
			rd = enumR.nextElement();
			if(rd != radio)
				rd.checked = false;
		}
	}
	
	public UIRadio getChecked() {
		return(checkedRadio);
	}

	@Override
	protected void createUI() {
		super.createUI();
		String sHtml = "<fieldset data-role=\"controlgroup\" id=\"" + getId() + "\">" +
					   "<legend>" + getLabel() + "</legend></fieldset>";
		uiScreen.executeJSView("createRadioGroup('" + sHtml + "','" + getId() + "','" + getIdParent() + "')");
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}

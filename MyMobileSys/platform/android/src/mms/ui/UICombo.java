package mms.ui;

import java.util.Vector;
/*
 * Created on 13-02-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UICombo extends UIControl{
	protected Vector<Object> vItems;
	protected int selectedIndex = -1;
	
	public UICombo(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
		vItems = new Vector<Object>();
	}
	
	public UICombo(){
		super();
	}
	
	public void add(Object item) {
		vItems.add(item);
		String text = item.toString().replace("'", "\\'");
		String value = String.valueOf(item.hashCode());
		String option = "<option value=\"" + value + "\">" + text + "</option>";
		uiScreen.executeJSView("addItemSelectMenu('" + getId() + "','" + option + "')");
	}

	public void add(Object[] items) {
		int l = items.length;
		StringBuffer sb = new StringBuffer();
		String text, value;
		Object item;
		
		for(int i = 0; i < l; i++) {
			vItems.add(items[i]);
			item = items[i];
			text = item.toString().replace("'", "\\'");
			value = String.valueOf(item.hashCode());
			
			sb.append("<option value=\"").append(value).append("\">").append(text).append("</option>");
		}
		uiScreen.executeJSView("addItemSelectMenu('" + getId() + "','" + sb.toString() + "')");
	}
	
	@Override
	public void refresh() {
		uiScreen.executeJSView("try { $( '#" + getId() + "').selectmenu( 'refresh' ) } catch(e) {}");
	}
	
	public void selectIndex(int index, boolean changeView) {
		if(index >= -1 && index < vItems.size()) {
			selectedIndex = index;
			
			if(changeView) {
				uiScreen.executeJSView("setComboSelectedIndex('" + getId() + "'," + (index+1) + ")");
			}
		}
	}
	
	public void selectIndex(int index) {
		selectIndex(index, true);
	}
	
	public void removeAll() {
		vItems.clear();
		uiScreen.executeJSView("removeAllListview('" + getId() + "')");
		selectedIndex = -1;
		addPlaceHolder();
	}
	
	public void addPlaceHolder() {
		String option = "<option value=\"-1\" data-placeholder=\"true\">Seleccionar...</option>";
		uiScreen.executeJSView("addItemSelectMenu('" + getId() + "','" + option + "')");
		refresh();
	}
	
	public int getSelectedIndex() {
		return(selectedIndex);
	}
	
	public Object getItem(int index) {
		Object item = null;
		if(index >= 0 && index < vItems.size()) {
			item = vItems.get(index);
		}
		return(item);
	}
	
	@Override
	public void setEnable(boolean enable) {
		super.setEnable(enable);
		if(enable)
			uiScreen.executeJSView("enableSelect('" + getId() + "','" + getParentPanel().getId() + "')");
		else
			uiScreen.executeJSView("disableSelect('" + getId() + "','" + getParentPanel().getId() + "')");
	}
	
	public int size() {
		return vItems.size();
	}
}

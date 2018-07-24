package mms.ui;

import java.util.Vector;
/*
 * Created on 13-02-2014 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UIList extends UIControl{
	protected Vector<UIListItem> vItems;
	protected int selectedIndex = -1;
	
	public UIList(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
		vItems = new Vector<UIListItem>();
	}
	
	public UIList() {
		super();
	}
	
	public void add(Object item) {
		UIListItem uit = new UIListItem(item.toString());
		add(uit);
	}
	
	//Solo para pocas llamadas ya que tiene rendimiento pobre en multiples items
	public void add(UIListItem item) {
		item.setId(getId() + "_" + vItems.size());
		vItems.add(item);
		String li = item.getHTMLItem();
		uiScreen.executeJSView("addItemListView('" + getId() + "','" + li + "')");
	}
	
	//Para un mejor rendimiento al agregar varios items a la vez
	public void add(UIListItem[] items) {
		StringBuffer sbLi = new StringBuffer();
		
		for(UIListItem item: items) {
			item.setId(getId() + "_" + vItems.size());
			sbLi.append(item.getHTMLItem());
			vItems.add(item);
		}
		
		uiScreen.executeJSView("addItemListView('" + getId() + "','" + sbLi.toString() + "')");
	}
	
	//Para reemplazar un item
	public void replace(UIListItem itemrep, int index) {
		if(index < vItems.size()) {
			UIListItem item = vItems.get(index);
			itemrep.setId(item.getId());
			uiScreen.executeJSView("$('#" + item.id + "').replaceWith('" + itemrep.getHTMLItem() + "')");
			uiScreen.executeJSView("$('#" + getId() + "').listview('refresh')");
			vItems.setElementAt(itemrep, index);
		}
	}
	/*
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
*/
	@Override
	public void refresh() {
		uiScreen.executeJSView("$( '#" + getId() + "').listview( 'refresh' )");
	}
	
	public void selectIndex(int index) {
		if(index >= 0 && index < vItems.size()) {
			selectedIndex = index;
		}
	}

	public int getSelectedIndex() {
		return(selectedIndex);
	}
	
	public UIListItem getItem(int index) {
		UIListItem item = null;
		if(index >= 0 && index < vItems.size()) {
			item = vItems.get(index);
		}
		return(item);
	}
	
	public void removeAll() {
		vItems.clear();
		uiScreen.executeJSView("removeAllListview('" + getId() + "')");
	}
}
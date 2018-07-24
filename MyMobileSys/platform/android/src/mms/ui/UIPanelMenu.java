package mms.ui;
/*
 * Created on 28-01-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UIPanelMenu extends UIContainer{

	public UIPanelMenu(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public UIPanelMenu() {
		super();
	}

	@Override
	public void registerControl(UIControl c) {
		if(uiScreen != null) {
			uiScreen.registerControl(c);
		}
	}

	@Override
	public void unregisterControl(UIControl c) {
		if(uiScreen != null) {
			uiScreen.unregisterControl(c);
		}
	}

	@Override
	public void add(UIControl ctl) {
		// TODO Auto-generated method stub
		super.add(ctl);
	}

	public void close() {
		uiScreen.executeJSView("closePanelMenu('" + getId() + "')");
	}
	
	public void open() {
		uiScreen.executeJSView("openPanelMenu('" + getId() + "')");
	}
	
	public void toggle() {
		uiScreen.executeJSView("togglePanelMenu('" + getId() + "')");
	} 
}

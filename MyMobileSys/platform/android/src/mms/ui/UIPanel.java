package mms.ui;

import mms.app.MMSApp;
/*
 * Created on 28-01-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UIPanel extends UIContainer{
	
	protected OnBeforeShowListener mOnBeforeShowListener = null;
	private UIHeader header = null; 
	
	public UIPanel(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}
	
	public UIPanel() {
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
	
	public boolean performBeforeShow() {
		boolean ret = true;
		if(mOnBeforeShowListener != null) 
			MMSApp.getExecutorEvent().execute(new Runnable() {
				public void run() {
					mOnBeforeShowListener.onBeforeShow(UIPanel.this);
				}
			});
			
		else
			ret = false;
		return ret;
	}
	
	public void setOnBeforeShowListener(OnBeforeShowListener l) {
		mOnBeforeShowListener = l;
	}

	public UIHeader getHeader() {
		return header;
	}

	@Override
	public void add(UIControl ctl) {
		// TODO Auto-generated method stub
		super.add(ctl);
		if(ctl instanceof UIHeader) {
			header = (UIHeader)ctl;
		}
	}
}

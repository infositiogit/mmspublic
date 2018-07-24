package mms.ui;

import java.util.ArrayList;
import java.util.Vector;

public class UIContainer extends UIControl{

	protected Vector<UIControl> controls = new Vector<UIControl>();
	private boolean visible = true;
	
	public UIContainer(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}
	
	public UIContainer() {
		super();
	}

	public void add(UIControl ctl) {
		controls.add(ctl);
		ctl.setParent(this);
		registerControl(ctl);
	}
	
	public void remove(UIControl ctl) {
		controls.remove(ctl);
		ctl.setParent(null);
		unregisterControl(ctl);
	}
	
	public int getCount() {
		return(controls.size());
	}
	
	//27/01/2015 cperez
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		uiScreen.executeJSView("$('#" + getId() + "').toggle(" + visible + ")");
	}
	
	public UIControl[] getListControls() {
		ArrayList<UIControl> al = new ArrayList<UIControl>();
		for(UIControl ctl: controls) {
			al.add(ctl);
			if(ctl instanceof UIContainer) {
				UIContainer cnt = (UIContainer)ctl;
				
				UIControl[] lsub = cnt.getListControls();
				for(UIControl subctl: lsub) {
					al.add(subctl);
				}
			}
		}
		return al.toArray(new UIControl[al.size()]);
	}
}

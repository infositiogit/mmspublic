package mms.ui;

public class UIControllerEvent {
	protected UIScreen screen;
	protected String name;
	
	public void setName(String n) {
		name = n;
	}
	
	public void setScreen(UIScreen v) {
		screen = v;
	}
	
	public UIScreen getScreen() {
		return(screen);
	}
	
	public String getName() {
		return(name);
	}
}

package mms.ui;

import java.util.UUID;

import mms.app.MMSApp;

public class UIControl {

	private String id = "";
	protected UIScreen uiScreen = null;
	protected UIContainer parent = null;
	protected boolean isUICreated = false;
	boolean isenabled;
	private Object data = null;
	private Object eventData = null;
	//Listeners
	protected OnClickListener mOnClickListener = null;
	protected OnFocusListener mOnFocusListener = null;
	protected OnFocusOutListener mOnFocusOutListener = null;

	public UIControl(String nid) {
		id = nid;
		isenabled = true;
	}

	//Si no necesitamos establecer un nombre al control en la capa grafica de UI entonces 
	//la creamos automáticamente
	public UIControl() {
		this(UUID.randomUUID().toString());
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return(id);
	}

	public void setView(UIScreen v) {
		uiScreen = v;
	}

	protected void setParent(UIContainer c) {
		parent = c;
	}

	public void registerControl(UIControl c) {
		if(parent != null) {
			parent.registerControl(c);
		}
	}

	public void unregisterControl(UIControl c) {
		if(parent != null) {
			parent.unregisterControl(c);
		}
	}

	public void refresh() {
	}

	public void setEnable(boolean enable) {
		isenabled = enable;
	}

	public boolean isEnabled() {
		return(isenabled);
	}

	public void setOnClickListener(OnClickListener l) {
		mOnClickListener = l;
	}

	public boolean performClick() {
		boolean ret = true;
		if(mOnClickListener != null) {
			/*
			new Thread(new Runnable() {
				public void run() {
					mOnClickListener.onClick(UIControl.this);
				}
			}).start();
			*/
			MMSApp.getExecutorEvent().execute(new Runnable() {
				public void run() {
					mOnClickListener.onClick(UIControl.this);
				}
			});
		} else
			ret = false;
		return ret;
	}

	protected void createUI() {
		isUICreated = true;
	}

	protected void removeUI() {
		uiScreen.executeJSView("removeControl('" + getId() + "')");
	}

	protected String getIdParent() {
		String ret = "";

		if(this.parent != null) {
			ret = this.parent.getId();

			if(this.parent instanceof UIPanel) {
				ret += "_panelcnt";
			}
		}
		return(ret);
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setOnFocusListener(OnFocusListener l) {
		mOnFocusListener = l;
	}

	public void setOnFocusOutListener(OnFocusOutListener l) {
		mOnFocusOutListener = l;
	}

	public boolean performFocus() {
		boolean ret = true;
		if(mOnFocusListener != null) 
			mOnFocusListener.onFocus(this);
		else
			ret = false;
		return ret;
	}

	public boolean performFocusOut() {
		boolean ret = true;
		if(mOnFocusOutListener != null) 
			mOnFocusOutListener.onFocusOut(this);
		else
			ret = false;
		return ret;
	}

	public UIPanel getParentPanel() {
		UIPanel p = null;

		if(this.parent != null) {
			if(this.parent instanceof UIPanel) {
				p = (UIPanel)this.parent;
			} else {
				p = this.parent.getParentPanel();
			}
		}

		return(p);
	}
	
	public void scrollToControl() {
		if(uiScreen != null) {
			UIHeader header = getParentPanel().getHeader();
			String nheader = "";
			
			//Si el Panel contenedor tiene creado un header el scroll debe descontar el ancho de este
			if(header != null) nheader = header.getId();
			
			uiScreen.executeJSView("scrollToControl('" + getId() + "','" + nheader + "')");
		}
	}
	
	public void focus() {
		uiScreen.executeJSView("$('#" + getId() + "').focus()");
	}
	
	public Object getEventData() {
		return eventData;
	}

	public void setEventData(Object eventData) {
		this.eventData = eventData;
	}
}

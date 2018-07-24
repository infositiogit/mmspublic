package mms.ui;

import mms.app.MMSApp;

public class UIModalDialog extends UIPanel {

	protected String title = "";
	protected String message = "";


	protected OnDialogOptionListener mOnDialogListener = null;


	public UIModalDialog(String nid) {
		super(nid);
		// TODO Auto-generated constructor stub
	}

	public UIModalDialog() {
		super();
	}

	public void setTitle(String t) {
		title = t;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	protected void createUI() {
		String sd = String.format("<div data-role=\"popup\" data-dismissible=\"false\" id=\"%1$s\"><div data-role=\"header\" id=\"%1$s_headerdialog\"><h1>%2$s</h1></div><div role=\"main\" class=\"ui-content\" id=\"%1$s_panelcnt\">%3$s</div></div>",
				getId(), title, message);
		
		//String sd = String.format("<div data-role=\"popup\" id=\"%1$s\" data-dismissible=\"false\" style=\"max-width:400px;\"><div data-role=\"header\" id=\"%1$s_headerdialog\"><h1>%2$s</h1></div><div role=\"main\" class=\"ui-content\" id=\"%1$s_panelcnt\"><h3 class=\"ui-title\">%3$s</h3></div></div>",
		//		getId(), title, message);
		
		//String sd = String.format("<div data-role=\"popup\" data-dismissible=\"false\" id=\"%1$s\"><div role=\"main\" class=\"ui-content\" id=\"%1$s_panelcnt\"><div style=\"text-align:center;margin-top:0px\"><h3>%2$s</h3></div>%3$s</div></div>",
        //        getId(), title, message);
		sd = sd.replace("\r", "");
		sd = sd.replace("\n", "");
		sd = sd.replace("\t", ""); 
		uiScreen.executeJSView("createDialog('" + sd + "','" + getId() + "')");
		super.createUI();
	}

	public void show() {
		uiScreen.executeJSView("showDialog('" + getId() + "')");
	}

	public OnDialogOptionListener getOnDialogListener() {
		return mOnDialogListener;
	}

	public void setOnDialogListener(OnDialogOptionListener mOnDialogListener) {
		this.mOnDialogListener = mOnDialogListener;
	}

	public void close() {
		uiScreen.unregisterControl(this);
		
		//Generamos una espera al momento de cerrar un popup
		//ya que en JQuery se genera un extraño error al cerrar rápidamente un popup y abrir otro.
		
		/*
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String mdid = "___MMSDialogOpen__" + this.getId();
		if(MMSApp.containsKey(mdid)) {
			if(MMSApp.getDataBoolean(mdid)) {
				MMSApp.setData(mdid, false);
			}
		}
		*/
		
		String mdid = "___MMSDialogOpen__" + this.getId();
		if(MMSApp.containsKey(mdid)) {
			while(MMSApp.getDataBoolean(mdid)) {    
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	public static UIModalDialog showDialog(UIScreen scr, String title, String message, String[] options, OnDialogOptionListener l) {
		UIModalDialog md = new UIModalDialog();
		md.setTitle(title);
		message = message.replace("\r\n", "<br>");
		md.setMessage(message);
		scr.add(md);

		DialogButtonsListener buttonList;
		if(l == null)
			buttonList = new DialogButtonsListener(new OnDialogOptionListener() {
				public void onSelect(UIModalDialog d, int option) {
					d.close();
				}
			});
		else
			buttonList = new DialogButtonsListener(l);
		
		//Agregamos los botones al diálogo

		if(options == null) {
			UIButton btnOpt = new UIButton();
			btnOpt.setData(new Integer(1));
			btnOpt.setText("OK");

			btnOpt.setOnClickListener(buttonList);
			md.add(btnOpt);		
		} else {
			for(int i = 0; i < options.length; i++) {
				UIButton btnOpt = new UIButton();
				btnOpt.setData(new Integer(i));
				btnOpt.setText(options[i]);
				btnOpt.setOnClickListener(buttonList);
				md.add(btnOpt);
			}
		}

		md.show();
		return md;
	}

	public static void showDialog(UIScreen scr, String title, String message, String[] options) {
		showDialog(scr, title, message, options, null);
	}

	public static void showDialog(UIScreen scr, String title, String message) {
		showDialog(scr, title, message, null, null);
	}

	public static int showModalDialog(UIScreen scr, String title, String message, String[] options) {
		UIModalDialog md = new UIModalDialog();
		md.setTitle(title);
		message = message.replace("\r\n", "<br>");
		md.setMessage(message);
		scr.add(md);
		final int lastOption = -1;
		
		DialogButtonsListener buttonList;
		buttonList = new DialogButtonsListener(new OnDialogOptionListener() {
			public void onSelect(UIModalDialog d, int option) {
				d.close();
			}
		});
		
		//Agregamos los botones al diálogo

		if(options == null) {
			UIButton btnOpt = new UIButton();
			btnOpt.setData(new Integer(1));
			btnOpt.setText("OK");

			btnOpt.setOnClickListener(buttonList);
			md.add(btnOpt);		
		} else {
			for(int i = 0; i < options.length; i++) {
				UIButton btnOpt = new UIButton();
				btnOpt.setData(new Integer(i));
				btnOpt.setText(options[i]);
				btnOpt.setOnClickListener(buttonList);
				md.add(btnOpt);
			}
		}

		md.show();
		String mdid = "___MMSDialogOpen__" + md.getId();
		MMSApp.setData(mdid, true);
		
		while(MMSApp.getDataBoolean(mdid)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buttonList.getLastOption();
	}
	
	public static int showModalDialog(UIScreen scr, String title, String message) {
		return showModalDialog(scr, title, message, null);
	}	
	
	@Override
	protected void removeUI() {
		uiScreen.executeJSView("closeDialog('" + getId() + "')");
	}
}

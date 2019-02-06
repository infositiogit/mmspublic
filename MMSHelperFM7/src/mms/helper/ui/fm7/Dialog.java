package mms.helper.ui.fm7;

import java.util.ArrayList;
import mms.ui.UIScreen;

//TODO: Se debe agregar
public class Dialog {
	protected String title = "";
	protected String message = "";
	
	protected OnDialogListener mOnDialogListener = null;
	protected ArrayList<String> alButtons = new ArrayList<String>();
	
	private String idDialog = null;
	private UIScreen screen;
	
	private String nameVarAppFM7 = "";
	
	public Dialog(UIScreen s, String nameApp) {
		super();
		screen = s;
		idDialog = "d_" + String.valueOf(mms.helper.ui.UniqueID.get()); //Nombre del dialog(Variable JS)
		nameVarAppFM7 = nameApp;
	}

	public Dialog setTitle(String t) {
		title = mms.helper.ui.HelperUI.jsSingleQuote(t);
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Dialog setMessage(String message) {
		this.message = mms.helper.ui.HelperUI.jsSingleQuote(message);
		return this;
	}
	
	public Dialog addButton(String button) {
		alButtons.add(button);
		return this;
	}
	
	public OnDialogListener getOnDialogListener() {
		return mOnDialogListener;
	}

	public Dialog setOnDialogListener(OnDialogListener mOnDialogListener) {
		this.mOnDialogListener = mOnDialogListener;
		return this;
	}
	
	protected void createDialog() {
		String abuttons = "[";
		String onclick = "function(dialog, index) { MMSNative(F_CONTROLLER, 'mms.helper.ui.fm7.ContDialog_" + idDialog + ".onButtonClick',JSON.stringify(index)) }";
		for(String b: alButtons) {
			abuttons += "{ text: '" + b + "', close: false},";
		}
		
		abuttons += "]";
		String sd = String.format("var %1$s = %2$s.dialog.create({title: '%3$s', text: '%4$s', buttons: %5$s, onClick: %6$s, destroyOnClose: true});",
				idDialog, nameVarAppFM7, title, message, abuttons, onclick);
		
		ContDialog cDialog = new ContDialog(mOnDialogListener);
		screen.addController(cDialog, "mms.helper.ui.fm7.ContDialog_" + idDialog);
		
		sd = sd.replace("\r", "");
		sd = sd.replace("\n", "");
		sd = sd.replace("\t", "");
		
		screen.executeJSView(sd);
	}
	
	public Dialog open() {
		createDialog();
		String sd = String.format("%1$s.open();", idDialog);
		screen.executeJSView(sd);
		return this;
	}
	
	public Dialog close() {
		if(idDialog != null) {
			String sd = String.format("%1$s.close();", idDialog);
			screen.executeJSView(sd);
		}
		return this;
	}
	
	public static Dialog showDialog(UIScreen scr, String nameApp, String title, String message, String[] buttons, OnDialogListener l) {
		Dialog d = new Dialog(scr, nameApp).
				             setTitle(title).
				             setMessage(message).
				             setOnDialogListener(l);
		
		for(String b: buttons) {
			d.addButton(b);
		}

		d.open();
		
		return d;
	}
	
	public static void showDialog(UIScreen scr, String nameApp, String title, String message, String[] options) {
		showDialog(scr, nameApp, title, message, options, null);
	}

	public static void showDialog(UIScreen scr, String nameApp, String title, String message) {
		showDialog(scr, nameApp, title, message, null, null);
	}
}

package mms.helper.ui.fm7;

import java.util.ArrayList;

import mms.app.MMSApp;
import mms.ui.UIScreen;

public class Dialog {
	protected String title = "";
	protected String message = "";
	
	protected OnDialogListener mOnDialogListener = null;
	protected ArrayList<String> alButtons = new ArrayList<String>();
	
	private String idDialog = null;
	private UIScreen screen;
	
	private String nameVarAppFM7 = "";
	private ContDialog cDialog = null;
	
	private int lastButtonIndex = -1;
	
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
	
	public String getIdDialog() {
		return idDialog;
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
		
		cDialog = new ContDialog(mOnDialogListener, this);
		cDialog.setName("mms.helper.ui.fm7.ContDialog_" + idDialog);
		screen.addController(cDialog, cDialog.getName());
		
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
			screen.removeController(cDialog);
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
	
	public static int showDialogBlock(UIScreen scr, String nameApp, String title, String message, String[] buttons) {
		final int indexB = -1;
		
		Dialog d = showDialog(scr, nameApp, title, message, buttons, new OnDialogListener() {

			@Override
			public void onButtonClick(int index, Dialog d) {
				d.close();
				d.setLastButtonIndex(index);
				String mdid = "___MMSDialogOpen__" + d.getIdDialog();
				if(MMSApp.containsKey(mdid)) {
					if(MMSApp.getDataBoolean(mdid)) {
						MMSApp.setData(mdid, false);
					}
				}
				
			}

		});
		
		String mdid = "___MMSDialogOpen__" + d.getIdDialog();
		MMSApp.setData(mdid, true);
		
		while(MMSApp.getDataBoolean(mdid)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//TODO: Falta agregar a MMS soporte para eliminar elementos de la colección MMSApp
		//TODO: Falta eliminar el elemento ___MMSDialogOpen__ desde la colección MMSApp
		return d.getLastButtonIndex();
	}

	protected int getLastButtonIndex() {
		return lastButtonIndex;
	}

	protected void setLastButtonIndex(int lastButtonIndex) {
		this.lastButtonIndex = lastButtonIndex;
	}
}

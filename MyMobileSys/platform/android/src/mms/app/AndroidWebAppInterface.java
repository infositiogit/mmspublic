package mms.app;

import mms.ui.UICheck;
import mms.ui.UICombo;
import mms.ui.UIControl;
import mms.ui.UIList;
import mms.ui.UIPanel;
import mms.ui.UIRadio;
import mms.ui.UIScreen;
import mms.ui.UIText;
import android.webkit.JavascriptInterface;

public class AndroidWebAppInterface {
	private MMSActivity activity;
	public String m_error_message="";

	private UIScreen screen;

	//17-04
	private boolean isComboOpen = false;
	private String comboOpen = null;
	//

	private final int TE_CLICK = 1;
	private final int TE_LOAD = 2;
	private final int TE_BEFORESHOW = 3;
	private final int TE_FOCUS = 4;
	private final int TE_FOCUSOUT = 5;

	//isExec se encuentra en true mientras se ejecuta una sentencia javascript
	//y queda en falso una vez termina la ejecución
	private static boolean isExec = false;
	
	public AndroidWebAppInterface(MMSActivity c) {
		activity = c;
	}

	public void setScreen(UIScreen sc) {
		screen = sc;
	}
	/** Show a toast from the web page */

	@JavascriptInterface
	public void controllerEvent(String idControl, String tevent, String data) {
		//Agregamos los llamados a los metodos controladores del UIScreen
		int tev = Integer.parseInt(tevent);

		if(tev == TE_LOAD) {
			screen.performLoad();
		} else {
			UIControl cnt = screen.getControl(idControl);
			
			if(cnt != null) //En algunos eventos se gatillan eventos sobre controles que no existen en la parte nativa, por eso es necesario validar
				cnt.setEventData(data);
			
			if(tev == TE_CLICK) {
				if(cnt instanceof UIList) {
					UIList lst = (UIList)cnt;
					int index = Integer.parseInt(data);
					lst.selectIndex(index);
				}
				cnt.performClick();
			} else if(tev == TE_BEFORESHOW) {
				if(cnt instanceof UIPanel) {
					UIPanel p = (UIPanel)cnt;
					p.performBeforeShow();
				}
			} else if(tev == TE_FOCUS) {
				cnt.performFocus();
			} else if(tev == TE_FOCUSOUT) {
				cnt.performFocusOut();
			}
		}
	}

	@JavascriptInterface
	public void updateTextControl(String idText, String text) {
		UIText textI = (UIText)screen.getControl(idText);
		textI.setTextView(text == null ? "": text);
	}

	@JavascriptInterface
	public void updateCheckControl(String idCheck, String flag) {
		UICheck check = (UICheck)screen.getControl(idCheck);
		check.setChecked(flag.equals("true") ? true: false, false);
	}

	@JavascriptInterface
	public void updateRadioControl(String idCheck, String flag) {
		UIRadio radio = (UIRadio)screen.getControl(idCheck);
		radio.setChecked(flag.equals("true") ? true: false, false);
	}

	@JavascriptInterface
	public void updateComboControl(String idCombo, String index) {
		UICombo combo = (UICombo)screen.getControl(idCombo);
		combo.selectIndex(Integer.parseInt(index), false);
	}

	@JavascriptInterface
	public void initScreen() {
		activity.initScreen();
	}


	//17-04
	@JavascriptInterface
	public void setIsComboOpen(String isOpen) {
		this.isComboOpen = isOpen.equals("true");
	}

	@JavascriptInterface
	public void setComboOpen(String comboId) {
		this.comboOpen = comboId;
	}
	//21-04
	@JavascriptInterface
	public String getComboOpen() {
		return(this.comboOpen);
	}

	@JavascriptInterface
	public boolean isComboOpen() {
		return(this.isComboOpen);
	}

	@JavascriptInterface
	public synchronized void setIsExec(boolean e) {
		screen.setIsExecJavascript(e);
		//System.out.println("SET isExec: " + isExec);
	}
	
	@JavascriptInterface
	public void hideProgressScreen() {
		screen.hideProgress();
	}
	
	@JavascriptInterface
	public void closeDialog(String id) {
		String mdid = "___MMSDialogOpen__" + id;
		if(MMSApp.containsKey(mdid)) {
			if(MMSApp.getDataBoolean(mdid)) {
				MMSApp.setData(mdid, false);
			}
		}
	}
}
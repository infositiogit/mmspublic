package mms.ui;

import java.util.HashMap;
import java.util.Hashtable;

import mms.app.AndroidWebAppInterface;
import mms.ui.OnPauseListener;
import mms.ui.OnRestoreStateListener;
import mms.ui.OnResumeListener;
import mms.ui.OnSaveStateListener;
import mms.ui.UIPanelMenu;
import mms.ui.UIScreen;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

public class UIScreen {
	private Hashtable<String, UIControl> htControls;
	private Hashtable<String, UIControllerEvent> htControllers;
	private HashMap<String, UIControl> hmControls; //Almacena referencias de todos los controles del Screen y todos sus hijos, permite buscar los controles de manera mas rapida
	private WebView webView;
	
	private ProgressDialog progressD;
	
	private UIPanel panelSelected = null;
	private UIPanelMenu panelMenuSelected = null;
	protected OnLoadListener mOnLoadListener = null;
	
	//24/10/2016
	protected OnSaveStateListener mOnSaveStateListener = null;
	protected OnRestoreStateListener mOnRestoreStateListener = null;
	
	protected OnPauseListener mOnPauseListener = null;
	protected OnResumeListener mOnResumeListener = null;
	
	//15/01/2015 cperez
	private boolean updateScreen = false;
	private StringBuffer bufferScript;
	private int updateProf = 0;
	
	//01/12/2014 cperez
	private boolean updateUIGraphicLayer = true;
	
	//10/02/2015 cperez
	private boolean isExecJavascript = false;
	
	public UIScreen(WebView wv) {
		webView = wv;
		htControls = new Hashtable<String, UIControl>();
		htControllers = new Hashtable<String, UIControllerEvent>(3);
		hmControls = new HashMap<String, UIControl>(20);
		bufferScript = new StringBuffer();
	}
	
	public void add(UIControl c, boolean createui) {
		htControls.put(c.getId(), c);
		registerControl(c);
		if(createui) {
			c.createUI();
		}
	}

	public void add(UIControl c) {
		add(c, false);
	}
	
	
	public void registerControl(UIControl c) {
		hmControls.put(c.getId(), c);
		c.setView(this);
		if(isUpdateUIGraphicLayer()) {
			c.createUI();
		}
	}
	
	public void unregisterControl(UIControl c) {
		hmControls.remove(c.getId());
		if(isUpdateUIGraphicLayer()) {
			c.removeUI();
		}
	}
	
	public void addController(UIControllerEvent cont) {
		//new
		cont.setScreen(this);
		htControllers.put(cont.getName(), cont);
	}
	
	public UIControllerEvent getController(String nameCont) {
		return(htControllers.get(nameCont));
	}
	
	public UIControl getControl(String id) {
		return(hmControls.get(id));
	}
	
	public synchronized void executeJSView(String js) {
		executeJSView(js, false);
	}
	
	public synchronized void executeJSView(String js, boolean hideProgress) {
		if(updateScreen) {
			if(!js.endsWith(";")) js += ";";
			
			bufferScript.append(js);
		} else {
			/*
			if(Thread.currentThread() == Looper.getMainLooper().getThread()) { //Se está ejecutando este código en el UI Thread
				Log.e("MMS", "La ejecución de Javascript debe ser en un hilo aparte del hilo UI Principal: " + js);
				return;
			}
			*/
			Activity parent = (Activity)webView.getContext();
			final String jscript = "try {" + js + ";} catch(e) { console.log(e.message) } finally { Android.setIsExec(false);" + (hideProgress ? "Android.hideProgressScreen();": "") + " }";

			/*
			try {
				synchronized(this) {
					if(isExecJavascript)
						this.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			setIsExecJavascript(true);
			
			parent.runOnUiThread(new Runnable() {
			    public void run() {
			    	webView.loadUrl("javascript:" + jscript);
			    }
			});
			
		}
	}
	
	public void setSelectedPanel(UIPanel panel) {
		this.executeJSView("changePage('#" + panel.getId() + "')");
		panelSelected = panel;
	}
	
	public UIPanel getSelectedPanel() {
		return panelSelected;
	}
	
	public void setSelectedPanelMenu(String panel) {
		UIPanelMenu p = (UIPanelMenu)getControl(panel);
		
		if(p != null) {
			setSelectedPanelMenu(p);
		}
	}	
	
	public void setSelectedPanelMenu(UIPanelMenu panel) {
		this.executeJSView("openPanelMenu('" + panel.getId() + "')");
		panelMenuSelected = panel;
	}
	
	public void beginUpdateScreen() {
		if(!updateScreen) {
			updateScreen = true;
			bufferScript.setLength(0);
		}
		updateProf++;
	}
	
	public void endUpdateScreen() {
		endUpdateScreen(false);
	}
	
	public void endUpdateScreen(boolean hideProgress) {
		if(updateScreen && updateProf == 1) {
			updateScreen = false;
			if(bufferScript.length() > 0) {
				executeJSView(bufferScript.toString(), hideProgress);
			}
		}
		updateProf--;
	}
	
	public void setSelectedPanel(String panel) {
		UIPanel p = (UIPanel)getControl(panel);
		
		if(p != null) {
			setSelectedPanel(p);
		}
	}
	
	public void setOnLoadListener(OnLoadListener l) {
		mOnLoadListener = l;
	}
	
	public void setOnSaveStateListener(OnSaveStateListener l) {
		mOnSaveStateListener = l;
	}
	
	public void setOnRestoreStateListener(OnRestoreStateListener l) {
		mOnRestoreStateListener = l;
	}
	
	public void setOnPauseListener(OnPauseListener l) {
		mOnPauseListener = l;
	}
	
	public void setOnResumeListener(OnResumeListener l) {
		mOnResumeListener = l;
	}
	
	public boolean performLoad() {
		boolean ret = true;
		if(mOnLoadListener != null) 
			new Thread(new Runnable() {
				public void run() {
					mOnLoadListener.onLoad(UIScreen.this);
				}
			}).start();
		else
			ret = false;
		
		return ret;
	}
	
	//Este evento no se ejecuta en otro hilo ya que no tiene que ver con la ejecución de la UI
	public boolean performSaveState() {
		boolean ret = true;
		if(mOnSaveStateListener != null) 
			mOnSaveStateListener.onSaveState(UIScreen.this);
		else
			ret = false;
		
		return ret;
	}
	
	//Este evento no se ejecuta en otro hilo ya que no tiene que ver con la ejecución de la UI
	public boolean performRestoreState() {
		boolean ret = true;
		if(mOnRestoreStateListener != null) 
			mOnRestoreStateListener.onRestoreState(UIScreen.this);
		else
			ret = false;
		
		return ret;
	}
	
	//Este evento no se ejecuta en otro hilo ya que no tiene que ver con la ejecución de la UI
	public boolean performPause() {
		boolean ret = true;
		if(mOnPauseListener != null) 
			mOnPauseListener.onPause(UIScreen.this);
		else
			ret = false;
		
		return ret;
	}
	
	//Este evento no se ejecuta en otro hilo ya que no tiene que ver con la ejecución de la UI
	public boolean performResume() {
		boolean ret = true;
		if(mOnResumeListener != null) 
			mOnResumeListener.onResume(UIScreen.this);
		else
			ret = false;
		
		return ret;
	}
	
	public void closeCombo(String idCombo) {
		this.executeJSView("$('#" + idCombo + "').selectmenu('close')");
	}
	
	public boolean isUpdateUIGraphicLayer() {
		return updateUIGraphicLayer;
	}

	public void setUpdateUIGraphicLayer(boolean updateUIGraphicLayer) {
		this.updateUIGraphicLayer = updateUIGraphicLayer;
	}
	
	public synchronized void setIsExecJavascript(boolean e) {
		isExecJavascript = e;
		if(!isExecJavascript) {
			synchronized(this) {
				this.notify();
			}
		}
		//System.out.println("SET isExec: " + isExec);
	}
	
	public synchronized boolean isExecJavascript() {
		//System.out.println("GET isExec: " + isExec);
		return(isExecJavascript);
	}
	
	public String[] getControlsId() {
		String[] keys = new String[hmControls.size()];
		keys = (String[])( hmControls.keySet().toArray( keys ) );
		return(keys);
	}
	
	public void showProgress(final String title, final String message) {
		final Activity parent = (Activity)webView.getContext();
		
		parent.runOnUiThread(new Runnable() {
		    public void run() {
		    	progressD = new ProgressDialog(parent);
				progressD.setTitle(title);
				progressD.setMessage(message);
				progressD.setCancelable(false);
				progressD.setCanceledOnTouchOutside(false);
				final Window window = progressD.getWindow();
			    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
			    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			    ColorDrawable color = new ColorDrawable(Color.BLACK);
			    color.setAlpha(200);
			    window.setBackgroundDrawable(color);
				progressD.show();
		    }
		});
		
	}
	
	public void hideProgress() {
		final Activity parent = (Activity)webView.getContext();
		
		parent.runOnUiThread(new Runnable() {
		    public void run() {
		    	progressD.dismiss();
		    }
		});
	}
	
}

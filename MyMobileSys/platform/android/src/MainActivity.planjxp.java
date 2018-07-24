<%
import mymobilesys.xml.ui.Text;

function void addControlsForm(Object form) {
	for(Object control: form.getControls() ) { 
		if(!control.getType().equals("Custom") || (control.getType().equals("Custom") && control.isNativeside())) {
			Object parent = control.getParent();
			String nameparent = parent.getName();
			if( control.getType().equals("Grid") ) {
				println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\"," + control.getCols() + ");");
			} else {
				println("UI" + control.getType() + " ctl" + control.getName() + "= new UI" + control.getType() + "(\"" + control.getName() + "\");");
			}
			
			println("ctl" + nameparent + ".add(ctl" + control.getName() + ");");
			genCallController(control);
			
			if( control.getType().equals("Grid") ) { 
				addControlsGrid(control);
			} else if( control.getType().equals("Container") ) {
				addControlsForm(control);
			}
		}
	}
}

function void addControlsGrid(Object grid) {
	int blocki = 1;
	for(Object b: grid.getBlocks() ) { 
		String blockName = "ctl" + grid.getName() + "_block" + blocki;
		println("UIGridCol "+ blockName + " = new UIGridCol(\"" + grid.getName() + "_block" + blocki  + "\");");
		println("ctl" + grid.getName() + ".add(" + blockName + ");");
		for(Object control: b.getControls()) {
			if(!control.getType().equals("Custom") || (control.getType().equals("Custom") && control.isNativeside())) {
				Object parent = control.getParent();
	
				if( control.getType().equals("Grid") ) {
					println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\"," + control.getCols() + ");");
				} else {
					println("UI" + control.getType() + " ctl" + control.getName() + "= new UI" + control.getType() + "(\"" + control.getName() + "\");");
				}
				
				//println("UI" + control.getType() + " ctl" + control.getName() + "= new UI" + control.getType() + "(\"" + control.getName() + "\");");
				println(blockName + ".add(ctl" + control.getName() + ");");
				genCallController(control);
				
				if( control.getType().equals("Grid") ) { 
					addControlsGrid(control);
				}
			}
		}
		blocki++;
	}
}

function void genCallController(Object control) {
	for(Object controller: control.getControllers()) {
		if( controller.getType().equals("ONCLICK")) {
			println("ctl" + control.getName() + ".setOnClickListener(new OnClickListener() {");
			println("public void onClick(UIControl ctl) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(ctl);");
			println("}");
			println("});");
		} else if( controller.getType().equals("ONLOAD")) {
			println("view.setOnLoadListener(new OnLoadListener() {");
			println("public void onLoad(UIScreen scr) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(scr);");
			println("}");
			println("});");
		} else if( controller.getType().equals("ONBEFORESHOW")) {
			println("p" + control.getName() + ".setOnBeforeShowListener(new OnBeforeShowListener() {");
			println("public void onBeforeShow(UIPanel panel) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(panel);");
			println("}");
			println("});");
		} else if( controller.getType().equals("ONFOCUS")) {
			println("ctl" + control.getName() + ".setOnFocusListener(new OnFocusListener() {");
			println("public void onFocus(UIControl ctl) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(ctl);");
			println("}");
			println("});");
		} else if( controller.getType().equals("ONFOCUSOUT")) {
			println("ctl" + control.getName() + ".setOnFocusOutListener(new OnFocusOutListener() {");
			println("public void onFocusOut(UIControl ctl) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(ctl);");
			println("}");
			println("});");	
		} else if( controller.getType().equals("ONSAVESTATE")) {
			println("view.setOnSaveStateListener(new OnSaveStateListener() {");
			println("public void onSaveState(UIScreen scr) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(scr);");
			println("}");
			println("});");			
		} else if( controller.getType().equals("ONRESTORESTATE")) {
			println("view.setOnRestoreStateListener(new OnRestoreStateListener() {");
			println("public void onRestoreState(UIScreen scr) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(scr);");
			println("}");
			println("});");	
		} else if( controller.getType().equals("ONPAUSE")) {
			println("view.setOnPauseListener(new OnPauseListener() {");
			println("public void onPause(UIScreen scr) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(scr);");
			println("}");
			println("});");			
		} else if( controller.getType().equals("ONRESUME")) {
			println("view.setOnResumeListener(new OnResumeListener() {");
			println("public void onResume(UIScreen scr) {");
			println(controller.getClassController() + " cont = (" + controller.getClassController() + ")uiScreen.getController(\"" + controller.getClassController() + "\");");
			println("cont." + controller.getMethod() + "(scr);");
			println("}");
			println("});");	
		}
	
				
	}
}

function void genInitControls(Object control) {
	if( !control.getType().equals("Panel") ) {
		Object parent = control.getParent();
		String nameparent = parent.getName();
		
		if( control.getType().equals("Grid") ) {
			println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\"," + control.getCols() + ");");
		} else {
			println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\");");
		}
		println("p" + nameparent + ".add(ctl" + control.getName() + ");");

		genCallController(control);
		
		if( control.getType().equals("Check") && control.isChecked() == true) { 
			println("ctl" + control.getName() + ".setChecked(" + control.isChecked() + ");");
		} else if( control.getType().equals("RadioGroup")) { 
			for(Object radio: control.getRadios()) { 
				println("UIRadio ctl" + radio.getName() + " = new UIRadio(\"" + radio.getName() + "\");");
				println("ctl" + control.getName() + ".add(ctl" + radio.getName() + ");");
				if(radio.isChecked() == true) {
					println("ctl" + radio.getName() + ".setChecked(true, false);");
				}
				genCallController(radio);
			}
		} else if( control.getType().equals("Form") || control.getType().equals("Container") ) { 
			addControlsForm(control);
		} else if( control.getType().equals("Grid") ) { 
			addControlsGrid(control);
		}
	}
}

function void genInitFooter(Object panel) {
	String nameParent = panel.getName();
	Object footer = panel.getFooter();
	String footerName = footer.getName();
	
	println("UIFooter p" + footerName + " = new UIFooter(\"" + footerName + "\");");
	println("p" + nameParent + ".add(p" + footerName + ");");
	
	for(Object control: footer.getControls()) {
		//println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\");");
		//println(footerName + ".add(ctl" + control.getName() + ");");
		//genCallController(control);
		genInitControls(control);
	}
	
}

function void genInitHeader(Object panel) {
	String nameParent = panel.getName();
	Object header = panel.getHeader();
	String headerName = header.getName();
	
	println("UIHeader p" + headerName + " = new UIHeader(\"" + headerName + "\");");
	println("p" + nameParent + ".add(p" + headerName + ");");
	
	for(Object control: header.getControls()) {
		//println("UI" + control.getType() + " ctl" + control.getName() + " = new UI" + control.getType() + "(\"" + control.getName() + "\");");
		//println(headerName + ".add(ctl" + control.getName() + ");");
		genInitControls(control);
	}
	
}
%>
package <%=configproject.getString("mms.package").toLowerCase()%>;

<% for(String cl: classes) { %>
	import <%=cl%>;
	
<% } %>


import java.util.ArrayList;

import mms.app.MMSActivity;
import mms.app.MMSApp;
import mms.app.AndroidWebAppInterface;
import mms.ui.OnClickListener;
import mms.ui.OnLoadListener;
import mms.camera.Camera;
import mms.facebook.auth.FacebookAuthSign;
import mms.google.auth.AccountData;
import mms.google.auth.GoogleAuthSign;
import mms.google.drive.GoogleDrive;
import mms.lib.GingerbreadJSFixExample;
import mms.ui.*;
import android.accounts.AccountManager;
import mms.barcode.BarcodeConstants;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
<% String usezxing = configProject.getString("mms.usezxing");
if(usezxing != null && usezxing.equals("1")) { %>
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
<% } %>
<% String usefacebooklogin = configProject.getString("mms.usefacebooklogin");
if(usefacebooklogin != null && usefacebooklogin.equals("1")) { %>
import com.facebook.FacebookSdk;
<% } %>

@SuppressLint("SetJavaScriptEnabled")
public class <%=configproject.getString("mms.name")%>Activity extends MMSActivity {

	protected FrameLayout webViewPlaceholder; 
	protected WebView webView;
	protected AndroidWebAppInterface api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initApp();
		initUI();
	}

	protected void initApp() {
		MMSApp.setData("__mmsversioncode__", "<%=configproject.getString("mms.versioncode")%>");
		MMSApp.setData("__mmsversionname__", "<%=configproject.getString("mms.versionname")%>");
	}
	
	protected void initUI()
	{
		// Retrieve UI elements
		webViewPlaceholder = ((FrameLayout)findViewById(R.id.webViewPlaceholder));

		// Initialize the WebView if necessary
		if (webView == null)
		{
			// Create the webview
			webView = new WebView(this);
			webView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			// Load the URLs inside the WebView, not in the external web browser
			webView.setWebViewClient(new WebViewClient());
			
			WebSettings webSettings = webView.getSettings();

			webSettings.setLoadWithOverviewMode(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadsImagesAutomatically(true);
			webSettings.setJavaScriptEnabled(true);
			webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
			//webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			
			api = new AndroidWebAppInterface(this);

			GingerbreadJSFixExample gbfix = new GingerbreadJSFixExample();

			gbfix.fixWebViewJSInterface(webView, api, "Android", "_gbjsfix:", this);

			loadScreen();
		}

		// Attach the WebView to its placeholder
		webViewPlaceholder.addView(webView);
	}

	@Override 
	protected void loadScreen() {
		// Cargamos el screen inicial solo si el activity no est� verificando el Google Play Services
		if(this.stateActivity != MMSActivity.STATE_CHECKGOOGLEPLAYSERVICES)
			webView.loadUrl("file:///android_asset/MMS_<%=screen.getName()%>.html");
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		if (webView != null)
		{
			// Remove the WebView from the old placeholder
			webViewPlaceholder.removeView(webView);
		}

		super.onConfigurationChanged(newConfig);

		// Load the layout resource for the new configuration
		setContentView(R.layout.activity_main);

		// Reinitialize the UI
		initUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		try {
			super.onSaveInstanceState(outState);
	
			// Save the state of the WebView
			webView.saveState(outState);
			
			if(uiScreen != null) uiScreen.performSaveState();
		} catch(Exception e) {
			Log.e("MMS", "ONSAVEINSTANCE: " + e.toString());
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		try {
			super.onRestoreInstanceState(savedInstanceState);
	
			// Restore the state of the WebView
			webView.restoreState(savedInstanceState);
			
			if(api != null) {
				GingerbreadJSFixExample gbfix = new GingerbreadJSFixExample();
	
				gbfix.fixWebViewJSInterface(webView, api, "Android", "_gbjsfix:", this);
			} else {
				Log.i("MMSDemo", "API es NULL en onRestoreInstanceState");
			}
			
			if(uiScreen != null) uiScreen.performRestoreState();
		} catch(Exception e) {
			Log.e("MMS", "ONRESTOREINSTANCE: " + e.toString());
		}
	}
	
	
	@Override
	public void onBackPressed() {
		//Si se presiona el boton BACK verificamos primero si hay abierto un popup combo y si lo esta lo cerramos
		if(api != null) {
			if(api.isComboOpen()) {
				uiScreen.closeCombo(api.getComboOpen());
				api.setIsComboOpen("false");
			} else {
				//Por el momento no hacemos nada sin embargo
				//TODO: Implementar este evento para poder manejarlo desde los controladores
				//super.onBackPressed();
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Camera.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				if(cameraPicture != null) {
					cameraPicture._compressPicture();
					listenerCam.onCapture(cameraPicture._getFile());
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		} else if(requestCode == Camera.CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				if(cameraPicture != null) {
					listenerCam.onCapture(cameraPicture._getFile());
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
			}
		<%  if(usezxing != null && usezxing.equals("1")) { %>
		} else if(requestCode == IntentIntegrator.REQUEST_CODE) { //BARCCODE RESULT
			if (resultCode == RESULT_OK) {
				IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
				if(result != null) {
					if(listenerBarcode != null) {
						listenerBarcode.onBarcode(result.getContents());
					}
				} else {
					listenerBarcode.onCancel();
				}
			} else if (resultCode == RESULT_CANCELED) {
				if(listenerBarcode != null) {
					listenerBarcode.onCancel();
				}
			}
		<% } %>
		} else if (requestCode == GoogleAuthSign.RESULT_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                
                final AccountData account = new AccountData(acct.getIdToken(), acct.getEmail(), acct.getId(), acct.getDisplayName(), acct.getGivenName(), acct.getFamilyName());
				new Thread(new Runnable() {

					@Override
					public void run() {
						listenerSignIn.success(account);
					}
					
				}).start();
                
                
                //Toast.makeText(this,"OK",Toast.LENGTH_LONG).show();
            } else {
                final Integer result_code = result.getStatus().getStatusCode();
				new Thread(new Runnable() {

					@Override
					public void run() {
						listenerSignIn.error(result_code);
					}
					
				}).start();
                
                
                //Toast.makeText(this,"KO",Toast.LENGTH_LONG).show();
            }
            
        <% usefacebooklogin = configProject.getString("mms.usefacebooklogin");
 		   if(usefacebooklogin != null && usefacebooklogin.equals("1")) { %>
		} else if( requestCode == FacebookSdk.getCallbackRequestCodeOffset()){ //Facebook
        	mCallbackManagerFace.onActivityResult(requestCode, resultCode, data);
        <% } %>
        
        } else if ((requestCode == GoogleDrive.REQUEST_ACCOUNT_PICKER || requestCode == GoogleDrive.REQUEST_CODE_RESOLUTION)) {
        	if(resultCode == RESULT_OK) {
				if(data != null && data.getExtras() != null) {
					String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
					if (accountName != null) {
						m_credential.setSelectedAccountName(accountName);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								listenerDrive.success(googleDrive);
							}
							
						}).start();
						
					}
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							listenerDrive.error(-1);
						}
						
					}).start();
					
				}
        	} else {
        		
        		new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listenerDrive.error(-1);
					}
					
				}).start();
        	}
        }

	}
	
	@Override
	public void initScreen() {
		uiScreen = createScreen(webView);
		
		//Instanciamos los controaldores del Screen

		api.setScreen(uiScreen);
		<%int count = 1;
		  for(String cl: classes) { %>
		  	<%=cl%> awc<%=count%> = new <%=cl%>();
		  	awc<%=count%>.setName("<%=cl%>");
		  	uiScreen.addController(awc<%=count%>);
		  	<% count++;
		  } %>
	}
	
	@Override
	public UIScreen createScreen(WebView webView) { 
		UIScreen view = new UIScreen(webView);
		
		view.setUpdateUIGraphicLayer(false);
		
		<% genCallController(screen); %>
		
		<% for(Object panel: screen.getPanels() ) { %>
			UIPanel p<%=panel.getName()%> = new UIPanel("<%=panel.getName()%>");
			view.add(p<%=panel.getName()%>);
			<% genCallController(panel); %>
			<% if(panel.getHeader() != null) {
			       genInitHeader(panel);
			   }

			   for( Object control: panel.getControls() ) {
				   if(!control.getType().equals("Custom") || (control.getType().equals("Custom") && control.isNativeside())) {
					   genInitControls(control);
			   	   }
			   } 
			   if(panel.getFooter() != null) {
				  genInitFooter(panel);
			   }
		   } %>

		<% for(Object panel: screen.getPanelsMenu() ) { %>
			UIPanelMenu p<%=panel.getName()%> = new UIPanelMenu("<%=panel.getName()%>");
			view.add(p<%=panel.getName()%>);
			<%-- genCallController(panel); --%>
			<% for( Object control: panel.getControls() ) {
			       genInitControls(control);
			   } 
		   } %>
		   
		view.setUpdateUIGraphicLayer(true);
    	view.setSelectedPanel(p<%=screen.getPanels().get(0).getName()%>);
		return(view);
	}
}

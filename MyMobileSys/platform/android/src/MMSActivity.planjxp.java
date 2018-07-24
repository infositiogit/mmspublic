package mms.app;

import <%=configproject.getString("mms.package").toLowerCase()%>.R;
import java.util.Arrays;
import java.util.Collections;

import mms.barcode.BarcodeListener;
import mms.camera.Camera;
import mms.camera.CameraListener;
import mms.google.auth.AccountData;
import mms.google.auth.GoogleAuthResultListener;
import mms.google.auth.GoogleAuthSign;
import mms.google.drive.GoogleDrive;
import mms.google.drive.GoogleDriveResultListener;
import mms.location.LocationData;
import mms.location.LocationUpdatesListener;
import org.json.JSONException;
import org.json.JSONObject;
import mms.ui.UIScreen;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;
import mms.facebook.auth.AccountDataFacebook;
import mms.facebook.auth.FacebookAuthResultListener;
import mms.facebook.auth.FacebookAuthSign;

<% String usefacebooklogin = configProject.getString("mms.usefacebooklogin");
if(usefacebooklogin != null && usefacebooklogin.equals("1")) { %>
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
<% } %>
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
<% String usezxing = configProject.getString("mms.usezxing");
if(usezxing != null && usezxing.equals("1")) { %>
import com.google.zxing.integration.android.IntentIntegrator;
<% } %>

public class MMSActivity extends FragmentActivity implements LocationListener,
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{

	protected Camera cameraPicture;

	protected CameraListener listenerCam;
	protected BarcodeListener listenerBarcode;
	protected GoogleAuthResultListener listenerSignIn;
	
    <% usefacebooklogin = configProject.getString("mms.usefacebooklogin");
	   if(usefacebooklogin != null && usefacebooklogin.equals("1")) { %>
	protected FacebookAuthResultListener listenerSignInFace;
	protected CallbackManager mCallbackManagerFace;
	<% } %>
	
	protected GoogleDriveResultListener listenerDrive; 
	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_CHECKGOOGLEPLAYSERVICES = 1; 
	public static final int CHECK_GOOGLEPLAY_ERROR = 9000; 

	protected int stateActivity = STATE_NORMAL;
	protected boolean isScreenLoaded = false;

	// Stores the current instantiation of the location client in this object
	private GoogleApiClient mGoogleApiClient;
	
	//GoogleApiClient para realizar autenticaci�n en Google
	private GoogleApiClient mGoogleApiClientSign = null;
		
	private LocationRequest mLocationRequest;
	private LocationUpdatesListener mLocationUpdListener;

	private boolean locationUpdates = false;

	protected UIScreen uiScreen;
	
	//Para Google Drive
	private final HttpTransport m_transport = AndroidHttp.newCompatibleTransport();//OJO
	private final JsonFactory m_jsonFactory = GsonFactory.getDefaultInstance();//OJO
	protected GoogleAccountCredential m_credential;//OJO
	protected Drive m_client;//OJO
	protected GoogleDrive googleDrive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		//Las aplicaciones MMS requieren de Google Play Services instalado en el equipo
		checkGooglePlayServices(true); 

		mGoogleApiClient = null;
		mLocationRequest = null;
	}

	protected void loadScreen() {

	}

	public void startCameraForResult(Intent intent, int code, CameraListener lst) {
		listenerCam = lst;
		startActivityForResult(intent, code);
	}

	public void setCameraPicture(Camera camera) {
		cameraPicture = camera;
	}

	public void startBarcodeZXingForResult(BarcodeListener lst, String barcodes) {
		listenerBarcode = lst;
		<% if(usezxing != null && usezxing.equals("1")) { %>
		new IntentIntegrator(this)
		.setDesiredBarcodeFormats(Collections.unmodifiableList(Arrays.asList(barcodes.split(","))))
		.initiateScan();
		<% } %>
	}
	
	public void startGoogleSignInForResult(GoogleAuthSign gas, GoogleAuthResultListener lst) {
		listenerSignIn = lst;
		
        if(mGoogleApiClientSign != null) {
            mGoogleApiClientSign.disconnect();
        }
 
        String c_id = gas.getClientId();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(c_id).requestEmail().build();
        mGoogleApiClientSign = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClientSign);
        startActivityForResult(signInIntent, GoogleAuthSign.RESULT_SIGN_IN);
	}
	
	public void startFacebookSignIn(FacebookAuthSign gas, FacebookAuthResultListener lst) {
	    <% if(usefacebooklogin != null && usefacebooklogin.equals("1")) { %>
		this.listenerSignInFace = lst;
		
    	//FacebookSdk.sdkInitialize(this.getApplicationContext(), FacebookAuthSign.RESULT_SIGN_IN_FACEBOOK);

        mCallbackManagerFace = CallbackManager.Factory.create();
       
        if(AccessToken.getCurrentAccessToken() != null) {
        	LoginManager.getInstance().logOut() ;
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile,email"));
        
        LoginManager.getInstance().registerCallback(mCallbackManagerFace,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                    	Profile profile = Profile.getCurrentProfile();
                    	final LoginResult lr = loginResult;
                    	GraphRequest request = GraphRequest.newMeRequest(
                    			loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            						@Override
            						public void onCompleted(JSONObject object,
            								GraphResponse response) {
            							try {
            								String email = object.has("email") ? object.getString("email"): "";
											String middle_name = object.has("middle_name") ? object.getString("middle_name"): "";

            								final AccountDataFacebook ac = new AccountDataFacebook(lr.getAccessToken().getToken(), email, object.getString("name"), object.getString("first_name"), middle_name, object.getString("last_name"));

					        				new Thread(new Runnable() {

					        					@Override
					        					public void run() {
					        						listenerSignInFace.success(ac);
					        					}
					        					
					        				}).start();
					                        
										} catch (final JSONException e) {
											// TODO Auto-generated catch block
					        				new Thread(new Runnable() {

					        					@Override
					        					public void run() {
					        						listenerSignInFace.error(e.getMessage());
					        					}
					        					
					        				}).start();
											
										}
            						}

            					});
            			Bundle parameters = new Bundle();
            			parameters.putString("fields", "email,name,first_name,middle_name,last_name");
            			request.setParameters(parameters);
            			GraphRequest.executeBatchAsync(request);
                    }

                    @Override
                    public void onCancel() {
                    	listenerSignInFace.cancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    	listenerSignInFace.error(exception.getMessage());
                    }
                });
    	<% } %>
	}
	
	public void startGoogleDriveConnection(GoogleDrive gd, GoogleDriveResultListener gdr) {
		this.listenerDrive = gdr;
		m_credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(DriveScopes.DRIVE));

		m_client = new com.google.api.services.drive.Drive.Builder(
				   m_transport, m_jsonFactory, m_credential).setApplicationName("MMSApp")
				   .build();
		
		gd.setDrive(m_client);
		googleDrive = gd;
		
		startActivityForResult(m_credential.newChooseAccountIntent(), GoogleDrive.REQUEST_ACCOUNT_PICKER);
	}
	
	public void startRecoverableAuth(UserRecoverableAuthIOException e) {
		startActivityForResult(e.getIntent(),GoogleDrive.REQUEST_AUTHORIZATION);
	}

	
	/**
	 * Verify that Google Play services is available before making a request.
	 *
	 * @return true if Google Play services is available, otherwise false
	 */
	private int checkGooglePlayServices(boolean showDialog) {


		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// Continue
			// Google Play services was not available for some reason
		} else if(showDialog) {
			// Display an error dialog
			stateActivity = STATE_CHECKGOOGLEPLAYSERVICES;
			//Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, CHECK_GOOGLEPLAY_ERROR);
			GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
			if (apiAvailability != null) {

                apiAvailability.getErrorDialog(this, resultCode, CHECK_GOOGLEPLAY_ERROR).show();

			}
		}

		return resultCode;

	}

	public void initLocationUpdates(long intervalMillis, LocationUpdatesListener l) {
		if(mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
		}

		if(mLocationRequest == null) {
			mLocationRequest = LocationRequest.create();
		}
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(intervalMillis);
		mLocationRequest.setFastestInterval(5000);

		if(l != null) mLocationUpdListener = l;

		if(mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
			//mGoogleApiClient.connect();
		}
	}

	public void startLocationUpdates() {
		mGoogleApiClient.connect();
	}

	public void stopLocationUpdates() {
		if(locationUpdates) {
			if(mGoogleApiClient.isConnected())
				LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
			mGoogleApiClient.disconnect();
			locationUpdates = false;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		MMSApp.refreshActivity(this);
		//Si ya estaba activa la actualizaci�n de localizaci�n entonces la reactivamos
		//if(MMSApp.containsKey("__mmsLocationUpdates__")) {
		//	locationUpdates = MMSApp.getDataBoolean("__mmsLocationUpdates__");
		//	if(locationUpdates) {
		//		initLocationUpdates(MMSApp.getDataLong("__mmsLocationInterval__"), (LocationUpdatesListener)MMSApp.getData("__mmsLocationListener__"));
		//		startLocationUpdates();
		//	}
		//}
	}

	@Override 
	protected void onPause() {
		try {
			super.onPause();


			//Si el activity se queda en pause grabamos 
			//if(locationUpdates) {
			//	MMSApp.setData("__mmsLocationUpdates__", true);
			//	MMSApp.setData("__mmsLocationInterval__", mLocationRequest.getInterval());

			//Se debe reinicar el listener al hacer el onRestoreState 
			//MMSApp.setData("__mmsLocationListener__", mLocationUpdListener);
			//}

			MMSApp.saveData();
			if(uiScreen != null) uiScreen.performPause();
		} catch(Exception e) {
			Log.e("MMS", "ONPAUSE: " + e.toString());
		}
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();

			if(MMSApp.existSavedData()) {
				MMSApp.restoreData();
			}

			if(uiScreen != null) uiScreen.performResume();
		} catch(Exception e) {
			Log.e("MMS", "ONRESUME: " + e.toString());
		}
	}

	@Override
	protected void onDestroy() {
		MMSApp.clearSavedData();
		stopLocationUpdates();
		super.onDestroy();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		//Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		// If already requested, start periodic updates
		locationUpdates = true;

		//Hacemos esto porque un extra�o problema que se ejecuta el onConnected sin que este conectado realmente el Google Play Services
		if(!mGoogleApiClient.isConnected()) {
			mGoogleApiClient.connect();
		}
		//*********************

		try {
			LocationServices.FusedLocationApi.requestLocationUpdates(
	                mGoogleApiClient, mLocationRequest, this); //OJO
		}catch(SecurityException e) {
			
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// Reporta al listener si la localizaci�n debe ser actualizada

		if(mLocationUpdListener != null) {
			LocationData ld = new LocationData();
			ld.setLatitude(location.getLatitude());
			ld.setLongitude(location.getLongitude());
			ld.setAccuracy(location.getAccuracy());
			ld.setTime(location.getTime());
			mLocationUpdListener.onLocationUpdate(ld);
		}

	}

	@SuppressLint("ValidFragment")
	public class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;
		private boolean canceled = false;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;

		}
		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;

		}
		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}

		@Override
		public void onCancel (DialogInterface dialog) {
			super.onCancel(dialog);

			canceled = true;

			//Verificamos si el activity est� en el estado "Revisando Google Play services)
			if(stateActivity == MMSActivity.STATE_CHECKGOOGLEPLAYSERVICES) {
				if(checkGooglePlayServices(false) != ConnectionResult.SUCCESS)
					//Si se est� cerrando el dialogo y aun no est� disponible el google play services entonces salimos de la app
					MMSApp.exit();
				else {
					//Si se cerr� el dialogo y ya est� disponible el Google Play Services entonces dejamos el
					//Activity en estado normal y cargamos el primer Screen
					stateActivity = MMSActivity.STATE_NORMAL;
					loadScreen();
				}

			}
		}


		@Override
		public void onDismiss (DialogInterface dialog) {
			super.onCancel(dialog);

		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	public void initScreen() {

	}

	protected UIScreen createScreen(WebView webView) {
		return(null);
	}


	public UIScreen getScreen() {
		return(uiScreen);
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
}

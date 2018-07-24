package mms.infosync;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mms.app.MMSApp;
import mms.infosync.InfoSyncAuthException;
import mms.infosync.InfoSyncCI;
import mms.infosync.InfoSyncException;
import mms.io.File;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class InfoSyncCI {

	private String className = "";
	private String methodName = "";
	
	public static int openTimeout = 60000;
	public static int readTimeout = 30000;

	private static Hashtable<String, String> hCookies;
	private boolean debug = false;
	
	public InfoSyncCI(String classMethod)
	{
		String[] res = classMethod.split("\\.");
		className = res[0];
		methodName = res[1];

		if (InfoSyncCI.hCookies == null)
		{
			InfoSyncCI.hCookies = new Hashtable<String, String>(5);
		}
	}

	public void setDebug(boolean d) {
		debug = d;
	}
	
	public static void setPathController(String pathC) {
		MMSApp.setData("__infosync_pathc", pathC);
	}

	public static void setPathControllerFile(String pathC) {
		MMSApp.setData("__infosync_pathc_file", pathC);
	}
	
	public static void setAuth(Object auth) {
		Gson gson = new Gson();
		MMSApp.setData("__infosync_auth", gson.toJson(auth));
		MMSApp.setData("__infosync_auth_class", auth == null ? null: auth.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public static Object getAuth() {
		Gson gson = new Gson();
		@SuppressWarnings("rawtypes")
		Class cls = null;
		try {
			cls = Class.forName(MMSApp.getDataString("__infosync_auth_class"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}
		return gson.fromJson(MMSApp.getDataString("__infosync_auth"),cls);
	}
		
	public Object[] execute(Object requestdata) throws InfoSyncException, InfoSyncAuthException
	{
		Object[] ret = null;
		try
		{
			ret = executeServer(requestdata);
		}
		catch (InfoSyncAuthException e)
		{
			//Si no se ha realizado autenticación llamamos al metodo 'autenticar.autentica' con los
			//parámetros almacenados en params_auth
			InfoSyncCI pauth = new InfoSyncCI("autenticar.autentica");
			ret = pauth.executeServer(getAuth());

			ret = executeServer(requestdata);
		}

		return (ret);
	}

	
	public Object[] execute() throws InfoSyncException, InfoSyncAuthException {
		return(execute(null));
	}
	
    public Object[] execute(Object requestdata, File fs)  throws InfoSyncException, InfoSyncAuthException
    {
        Object[] ret = null;
        if (fs != null && fs.exists())
        {
        	String pathController = MMSApp.getDataString("__infosync_pathc");
            ISHttpSendFile hs = new ISHttpSendFile(pathController, fs._getFileAndroid());
            hs.setCookies(hCookies);
            hs.request();

            if (hs.isOk())
            {
                ret = executeServer(requestdata);
            }
            else
            {
                if (hs.getCode() == 2)
                { //No hay autenticación
                    //Si no se ha realizado autenticación llamamos al metodo 'autenticar.autentica' con los
                    //parámetros almacenados en params_auth
        			InfoSyncCI pauth = new InfoSyncCI("autenticar.autentica");
        			ret = pauth.executeServer(MMSApp.getData("__infosync_auth"));

                    hs = new ISHttpSendFile(pathController, fs._getFileAndroid());
                    hs.setCookies(hCookies);
                    hs.request();

                    ret = executeServer(requestdata);
                }
                else
                {
                	throw new InfoSyncException("Error al transmitir archivo(Código: " + hs.getCode() + "): " + hs.getStatus());
                }
            }
        } else {
        	//ret = executeServer(requestdata, true);
    		try
    		{
    			ret = executeServer(requestdata, true);
    		}
    		catch (InfoSyncAuthException e)
    		{
    			//Si no se ha realizado autenticación llamamos al metodo 'autenticar.autentica' con los
    			//parámetros almacenados en params_auth
    			InfoSyncCI pauth = new InfoSyncCI("autenticar.autentica");
    			ret = pauth.executeServer(getAuth());

    			ret = executeServer(requestdata, true);
    		}
        }
        return (ret);
    }
    
	private Object[] executeServer(Object requestdata) throws InfoSyncException, InfoSyncAuthException {
		return(executeServer(requestdata, false));
	}
	
	private Object[] executeServer(Object requestdata, boolean emptyfile) throws InfoSyncException, InfoSyncAuthException {
		OutputStream requestStream = null;
		InputStream resp = null;
		Object[] ret = new Object[0];

		try
		{
			Gson gson = new Gson();

			URL url = new URL((String)MMSApp.getData("__infosync_pathc"));
			HttpURLConnection req = (HttpURLConnection) url.openConnection();  
			req.setConnectTimeout(openTimeout);
			req.setReadTimeout(readTimeout);
			req.setRequestMethod("POST");
			req.setInstanceFollowRedirects(false);
			req.setRequestProperty("Content-Type","application/json");
			req.setDoOutput(true);

			//Asignamos las cookies grabadas anteriormente
			if (InfoSyncCI.hCookies.size() > 0)
			{
				for(String cookie: InfoSyncCI.hCookies.values())
				{
					req.setRequestProperty("Cookie", cookie);
				}
			}


			ISSend send = new ISSend();
			send.classname = className;
			send.methodname = methodName;
			send.requestdata = requestdata;
			send.emptyfile = emptyfile;
			
			String reqJSON = gson.toJson(send);
			byte[] postBytes = reqJSON.getBytes("UTF-8");

			requestStream = req.getOutputStream();

			requestStream.write(postBytes, 0, postBytes.length);

			if(req.getResponseCode() == HttpURLConnection.HTTP_OK) {
				resp = req.getInputStream();

				String auxCookie = req.getHeaderField("Set-Cookie");

				if(auxCookie != null) {
					for(Entry<String, List<String>> vc: req.getHeaderFields().entrySet()) {
						if(vc.getKey() != null && vc.getKey().equals("Set-Cookie")) {
							for(String vvc: vc.getValue()) {
								InfoSyncCI.hCookies.put(vc.getKey(), vvc );
							}
						}
					}
				}

				//reader = new StreamReader(getStreamForResponse(resp, 30000), Encoding.UTF8);

				ret = getDataResponse(resp);
				//reader.Close();
				requestStream.close();
				resp.close();

			} else {
				throw new InfoSyncException("Error en respuesta del servidor: Código HTTP = " + req.getResponseCode());
			}
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new InfoSyncException("Error URL. URL de solicitud está mal formada:" + MMSApp.getData("__infosync_pathc"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new InfoSyncException("Error Codificación. La codificación UTF-8 no está soportada");
		} catch (SocketTimeoutException e) {
			throw new InfoSyncException("Error de comunicación. El servidor ha tardado mucho tiempo en responder.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InfoSyncException("Error de comunicación. Verifique estado de comunicación de datos en el dispositivo." + e.toString());
		}
		finally
		{
			if(requestStream != null)
				try {
					requestStream.close();
					if(resp != null) resp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return (ret);
	}

	private Object[] getDataResponse(InputStream is) throws IOException, InfoSyncAuthException, InfoSyncException {
		Object[] ret = null;
		JsonReader jsonreader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		String result = "", message = "";
		
		jsonreader.beginObject();
		
		while(jsonreader.hasNext()) {
			String name = jsonreader.nextName();
			
			if(name.equals("result")) {
				result = jsonreader.nextString();
			} else if(name.equals("resultdata")) {
				Gson gson = new Gson();
				ret = gson.fromJson(jsonreader,Object[].class);
			} else if(name.equals("message")) {
				message = jsonreader.nextString();
			}
		}
		
		jsonreader.endObject();
		jsonreader.close();
		
		if(!result.equals("OK")) {
			if(result.equals("ERROR_AUTH")) {
				throw new InfoSyncAuthException(message);
			} else {
				throw new InfoSyncException(message);
			}
		}

		return ret;
	}

    public static void clearCookies() {
    	if(hCookies != null) {
    		hCookies.clear();
    	}
    }
}

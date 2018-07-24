package mms.infosync;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import mms.app.MMSApp;

/**
 * 
 * @author Claudio Perez
 * 
 * HttpSendFile: Permite enviar archivos al servidor mediante Http
 * 
 */
class ISHttpSendFile {
	private String URL = null; 

	private boolean error;
	private String mensajeerror;

	private File archivo;

	private Socket sc;

	private String respuesta = "";
	private long tinicio;
	public int TIMEOUT_CONN = 30000;
	public int TIMEOUT_READ = 20000;
	public int TIMEOUT_WRITE = 10000;
	private static int PARTE_DEFAULT = 50000;

	private long largoarchivo;
	private int returnCode = 0;

	private static Hashtable<String, String> hCookies;

	private OutputStream os = null;
	private InputStream is = null;
	/**
	 * Constructor HttpSendFile 
	 * 
	 * @param url Define la URL del script donde se enviará el archivo
	 * @param sd Cadena de texto para enviar información al script
	 * @param fc Objeto FileConnection que contiene el archivo a enviar
	 */
	public ISHttpSendFile(String url, File fc) {
		URL = url;
		sc = null;
		archivo = fc;

		try {
			largoarchivo = fc.length();
			//Vm.debug("LARGO:" + largoarchivo + " TOTALPARTES:" + totalpartes);
		}catch(Exception e) {
			//Vm.debug(e.toString());
			largoarchivo = -1;
		}

	}

	/**
	 * Permite ejecutar el envío del archivo al script indicado anteriormente
	 * en el constructor de la clase
	 *
	 */
	public void request() {
		error = false;
		mensajeerror = "";

		//EnviarArchivo();
		EnviarArchivoHttpConn();
	}


	private void EnviarArchivo() {
		int totalenviados = 0;
		String path = "";
		URI uri;

		try {
			path = MMSApp.containsKey("__infosync_pathc_file") ? MMSApp.getDataString("__infosync_pathc_file") : null;

			if(path == null)
				uri = new URI(URL + "/uploadfile");
			else 
				uri = new URI(path);

			sc = new Socket(uri.getHost(), uri.getPort() == -1 ? 80: uri.getPort());
			sc.setSoTimeout(TIMEOUT_READ);
			//sc.writeTimeout = TIMEOUT_WRITE;
			os = sc.getOutputStream();
			is = sc.getInputStream();

			StringBuffer sbHeaderHttp = new StringBuffer();

			sbHeaderHttp.append("POST " + uri.getPath() + " HTTP/1.0\r\n");
			sbHeaderHttp.append("Host: " + uri.getHost().toString() + "\r\n");
			sbHeaderHttp.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36\r\n");
			sbHeaderHttp.append("Content-Type: multipart/form-data; boundary=---------------------------41184676334\r\n");

			//Asignamos las cookies grabadas anteriormente
			if (hCookies.size() > 0)
			{
				for(String cookie: hCookies.values())
				{
					sbHeaderHttp.append("Cookie: ").append(cookie).append("\r\n");
				}
			}
			StringBuffer sbHeaderPost = new StringBuffer();

			sbHeaderPost.append("-----------------------------41184676334\r\n");
			sbHeaderPost.append("Content-Disposition: form-data; name=\"userfile\"; filename=\"" + archivo.getName() + "\"\r\nContent-Type: \"image/jpeg\"\r\n");
			sbHeaderPost.append("\r\n");
			byte[] endHeaderPost = "\r\n-----------------------------41184676334--\r\n\r\n".getBytes();

			long Largo = sbHeaderPost.length() + largoarchivo + endHeaderPost.length;

			sbHeaderHttp.append("Content-Length: " + Largo + "\r\n\r\n");

			respuesta = "";

			os.write(sbHeaderHttp.toString().getBytes());
			os.write(sbHeaderPost.toString().getBytes());


			totalenviados = 0;
			int leidosArchivo;
			byte[] parte = new byte[10240];

			InputStream fis = new FileInputStream(archivo);
			while(totalenviados < largoarchivo) {
				leidosArchivo = fis.read(parte, 0, parte.length);
				if(leidosArchivo == -1) {
					throw new IOException("Error en lectura del archivo en el dispositivo.");
				}

				os.write(parte, 0, leidosArchivo);

				totalenviados += leidosArchivo;
			}
			fis.close();

			os.write(endHeaderPost);

			//Vm.debug(getFechaHora() + " Escritos: " + totalenviados + " de: " + r.length + " largo: " + Largo);

			respuesta = procesarRespuesta();
			int pos = -1;

			if ((pos = respuesta.indexOf("%%0%%")) != -1)
			{
				respuesta = respuesta.substring(pos + 5);
				returnCode = 0;
			}
			else if ((pos = respuesta.indexOf("%%")) == -1)
			{
				error = true;
				returnCode = -1;
				mensajeerror = "Respuesta inválida del servidor:" + respuesta;
			} else {
				int pos2;
				error = true;
				pos2 = respuesta.indexOf("%%", pos + 2);
				returnCode = Integer.parseInt(respuesta.substring(pos + 2, pos2));
				mensajeerror = "Error en servidor:" + respuesta.substring(pos2 + 2);
			}
			close();

		}catch(Exception e) {
			error = true;
			mensajeerror += e.toString();
			//Vm.debug(mensajeerror);
			close();  
		}
	}

	private void EnviarArchivoHttpConn() {
		String path = "";
		URI uri;

		try {
			path = MMSApp.containsKey("__infosync_pathc_file") ? MMSApp.getDataString("__infosync_pathc_file") : null;

			if(path == null)
				uri = new URI(URL + "/uploadfile");
			else 
				uri = new URI(path);

			URL url = new URL(uri.toString());
			MultipartUtility multipart = new MultipartUtility(uri.toString(), "UTF-8", hCookies);

			multipart.addFilePart("userfile",archivo);

			List<String> response = multipart.finish();
			for (String line : response) {
				System.out.println("Upload Files Response:::" + line);
				// get your server response here.
				respuesta += line;
			}

			int pos = -1;

			if ((pos = respuesta.indexOf("%%0%%")) != -1)
			{
				respuesta = respuesta.substring(pos + 5);
				returnCode = 0;
			}
			else if ((pos = respuesta.indexOf("%%")) == -1)
			{
				error = true;
				returnCode = -1;
				mensajeerror = "Respuesta inválida del servidor:" + respuesta;
			} else {
				int pos2;
				error = true;
				pos2 = respuesta.indexOf("%%", pos + 2);
				returnCode = Integer.parseInt(respuesta.substring(pos + 2, pos2));
				mensajeerror = "Error en servidor:" + respuesta.substring(pos2 + 2);
			}
			close();

		}catch(Exception e) {
			error = true;
			mensajeerror += e.toString();
			//Vm.debug(mensajeerror);
			close();  
		}
	}

	private String procesarRespuesta() throws IOException
	{
		String respuesta = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		int leidos;
		byte datos[] = new byte[1000];
		while ((leidos = is.read(datos, 0, 1000)) > 0) 
		{
			baos.write(datos, 0, leidos);
		}

		respuesta = new String(baos.toByteArray());

		baos.close();
		return(respuesta);
	}

	/**
	 * Permite saber si el método request se ejecutó con �xito
	 * 
	 * @return True: En caso de que no hay error. False: En caso de error
	 * 
	 */
	public boolean isOk() {
		return(!error);
	}

	public int getCode() {
		return(returnCode);
	}

	/**
	 * Permite cerrar la conexión Http.
	 * 
	 * Obligatorio llamar a este método luego de invocado el método request
	 *
	 */
	public void close() {
		try {
			if(sc != null) sc.close();
		}catch(Exception e) {

		}
	}

	/**
	 * Permite saber el mensaje de error retornado por la conexión http
	 * 
	 * @return Texto con el mensaje de error retornado luego del request
	 */
	public String getStatus() {
		return(mensajeerror);
	}

	/**
	 * Permite rescatar el texto de retorno del script invocado
	 * 
	 * @return Texto retornado por el script
	 */
	public String getResponse() {
		return(respuesta);
	}

	public void setCookies(Hashtable<String, String> ck) {
		hCookies = ck;
	}
}

/*
 * Created on 13-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class ParseTemplate {
	
	protected String pathTemplate = null; //Ruta del archivo template velocity
	protected String template = null; //nombre del archivo template velocity
	protected String parsedText = ""; //Texto resultante del procesar la plantilla velocity
	private VelocityEngine ve = null; 
	private VelocityContext context = null;
	
	public ParseTemplate(String fTemplate) {
		File file = new File(fTemplate);
		
		if(file.isFile() && file.exists()) {
			pathTemplate = file.getParent();
			template = file.getName();
			
			ve = new VelocityEngine();		
			context = new VelocityContext();
			ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, pathTemplate);
			
			ve.init();
		}
	}
	
	public void put(String key, Object value) {
		if(context != null) {
			context.put(key, value);
		}
	}
	
	public void parse() {
		if(template != null) {
			StringWriter sw = new StringWriter();
			
			Template t = ve.getTemplate(template);
			
			t.merge(context, sw);
			parsedText = sw.toString();
		}
	}
	
	public String getParsedText() {
		return(parsedText);
	}
	
	public boolean save(String filename, String encoding) {
		boolean ret = true;
		if(parsedText != null) {
			try {
				OutputStreamWriter fw;
				if(encoding == null)
					fw = new OutputStreamWriter(new FileOutputStream(filename));
				else
					fw = new OutputStreamWriter(new FileOutputStream(filename),encoding);
				
				//String parsedUTF = new String(parsedText.getBytes(), "UTF-8");
				
				fw.write(parsedText);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = false;
			}
			
		} else {
			ret = false;
		}
		return(ret);
	}
	
	public boolean save(String filename) {
		return(save(filename, null));
	}
}

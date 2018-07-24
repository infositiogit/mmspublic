/*
 * Created on 13-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.velocity.Template;
import org.onemind.jxp.FilePageSource;
import org.onemind.jxp.JxpContext;
import org.onemind.jxp.JxpProcessor;

public class ParseTemplateJXP {
	
	protected String pathTemplate = null; //Ruta del archivo template velocity
	protected String template = null; //nombre del archivo template velocity
	protected String parsedText = ""; //Texto resultante del procesar la plantilla velocity
	private JxpProcessor jxp = null; 
	private JxpContext jxpcontext = null;
	protected HashMap<String, Object> jxpenv = null;
	
	public ParseTemplateJXP(String fTemplate) {
		File file = new File(fTemplate);
		
		if(file.isFile() && file.exists()) {
			pathTemplate = file.getParent();
			template = file.getName();

			FilePageSource pageSource = new FilePageSource(pathTemplate);

			jxpcontext = new JxpContext(pageSource);

			//declare the processor and make it use the context
			jxp = new JxpProcessor(jxpcontext);
			
			jxpenv = new HashMap<String, Object>();
		}
	}
	
	public void put(String key, Object value) {
		jxpenv.put(key, value);
	}
	
	public void parse() {
		if(template != null) {
			StringWriter sw = new StringWriter();
			
			try {
				jxp.process(template, sw, jxpenv);
				parsedText = sw.toString();
			} catch (Exception e) {
				parsedText = "";
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
		return(save(filename, "UTF-8"));
	}
}

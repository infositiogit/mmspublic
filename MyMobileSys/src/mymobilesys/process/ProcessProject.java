/*
 * Created on 07-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.process;


import java.io.File;

import mymobilesys.Global;
import mymobilesys.lib.Library;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class ProcessProject {
	
	private String filenameProject = null;
	private String pathProject = null;
	private String frName = null;
	
	private CompositeConfiguration configProject = null;
	private boolean projectOk = false;
	
	private static final String P_ANDROID = "android";
	private static final String P_TOTALCROSS = "totalcross";
	
	public ProcessProject(String fp) {
		File f = new File(fp);
		
		filenameProject = f.getName();
		pathProject = f.getAbsolutePath();
		pathProject = pathProject.substring(0,pathProject.lastIndexOf(File.separator));
		
		configProject = new CompositeConfiguration();
		configProject.addConfiguration(new SystemConfiguration());
		try {
			configProject.addConfiguration(new PropertiesConfiguration(f.getAbsoluteFile()));
			projectOk = true;
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void process() {
		if(projectOk) {
			Library.print("MyMobileSys versión: " + Global.version + "(" + Global.versionnumber + ")" + " - " + Global.versionname);
			Library.print("Procesando archivo proyecto " + pathProject + File.separator + filenameProject);
			Library.print("Nombre: " + configProject.getString("mms.name"));
			
			String[] deploys = configProject.getString("mms.deploy").split(",");
			
			for(int i = 0; i < deploys.length; i++) {
				if(deploys[i].equals(P_ANDROID)) {
					ProcessAndroid pa = new ProcessAndroid(configProject, filenameProject, pathProject, getFrName());
					pa.process();
				}
			}
		} else {
			Library.printError("No se pudo procesar proyecto");
		}
	}
	
	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	
}


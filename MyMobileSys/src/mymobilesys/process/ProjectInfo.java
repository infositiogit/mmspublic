package mymobilesys.process;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ProjectInfo {
	public String projectpath = null;
	
	public String getCustomHtml(String name) {
		String ret = null;
		
		File f = new File(projectpath + "/xml/ui/custom/" + name + ".html");
		if(f.exists() && !f.isDirectory()) { 
		    try {
				ret = FileUtils.readFileToString(f, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ret = null;
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public String getReplaceHtml(String name) {
		String ret = null;
		
		File f = new File(projectpath + "/xml/ui/replace/" + name);
		if(f.exists() && !f.isDirectory()) { 
		    try {
				ret = FileUtils.readFileToString(f, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ret = null;
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public boolean existCustomScript(String name) {
		boolean ret = false;
		
		File f = new File(projectpath + "/xml/ui/custom/" + name + ".js");
		
		return f.exists() && !f.isDirectory();
	}
	
	public boolean existCustomCSS(String name) {
		boolean ret = false;
		
		File f = new File(projectpath + "/xml/ui/custom/" + name + ".css");
		
		return f.exists() && !f.isDirectory();
	}
}

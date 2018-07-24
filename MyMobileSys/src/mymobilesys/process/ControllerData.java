/*
 * Created on 03-01-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.process;

import mymobilesys.xml.ui.base.Controller;

public class ControllerData {
	
	private String packageName;
	private String className;
	private String packageClassName;
	private String method;
	private Controller controllerData;
	
	public ControllerData(Controller cdata) {
		controllerData = cdata;
		init();
	}
	
	private void init() {
		String cname = controllerData.getName();
		int pos = cname.lastIndexOf(".");
		
		if(pos != -1) {
			method = cname.substring(pos + 1);
			
			int pos2 = cname.lastIndexOf(".", pos - 1);
			
			if(pos2 != -1) {
				className = cname.substring(pos2 + 1, pos);
				packageClassName = cname.substring(0, pos);
				packageName = cname.substring(0, pos2);
			}
		}
		
	}

	public String getPackageClassName() {
		return packageClassName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getMethod() {
		return method;
	}

	public Controller getControllerData() {
		return controllerData;
	}
	
}

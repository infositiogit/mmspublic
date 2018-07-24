/*
 * Created on 22-04-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.xml.ui.base;

public class Controller {
	protected String name = null;
	protected String type = null;

	/**
	 * @param name
	 * @param type
	 */
	public Controller(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethod() {
		String ret = "";

		int pos = name.lastIndexOf(".");

		if(pos != -1) {
			ret = name.substring(pos + 1);
		}

		return(ret);
	}
	
	public String getClassController() {
		String ret = "";

		int pos = name.lastIndexOf(".");

		if(pos != -1) {
			ret = name.substring(0, pos);
		}

		return(ret);
	}
}

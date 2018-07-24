/*
 * Created on 02-01-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.xml.ui.base;

import java.lang.reflect.Method;
import java.util.Vector;

import mymobilesys.xml.ui.Control;


public class ControlBase {

	private Control parent = null;
	
	public String getType() {
		return(this.getClass().getSimpleName());
	}
	
	//Retorna el listado de controladores presentes en el control
	//IMPORTANTE: Todos los metodos que informan controladores de eventos deben comenzar con "getOn"
	public Vector<Controller> getControllers() {
		Vector<Controller> vConts = new Vector<Controller>();
		Method[] methods = this.getClass().getMethods();
		int l = methods.length;
		
		for(int m = 0; m < l; m++) {
			if(methods[m].getName().toUpperCase().startsWith("GETON")) {
				String cont;
				try {
					cont = (String) methods[m].invoke(this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					cont = null;
				}
				
				if( cont != null && !cont.isEmpty() ) {
					vConts.add(new Controller(cont, methods[m].getName().toUpperCase().substring(3)));
				}
			}
		}
		return(vConts);
	}
	
	public Control getParent() {
		return(parent);
	}
	


	public void setParent(Control parent) {
		this.parent = parent;
	}
}

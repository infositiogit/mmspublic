/*
 * Created on 31-01-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.lib;

import java.util.Arrays;
import java.util.List;

import mymobilesys.xml.ui.Text;
import mymobilesys.xml.ui.base.ControlBase;

public class VelocityLib {

	public boolean isNull(Object obj) {
		return(obj == null);
	}
	
	public List<String> split(String text, String sep) {
		return(Arrays.asList(text.split(sep)));
	}
	
	public Text getText(ControlBase ctl) {
		return((Text)ctl);
	}
}

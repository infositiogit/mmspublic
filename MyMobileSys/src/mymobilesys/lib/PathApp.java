/*
 * Created on 06-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.lib;

public class PathApp {
	public String getPath() {
		String sPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
		sPath = sPath.substring(0, sPath.lastIndexOf("/"));		
		return sPath;
	}
}

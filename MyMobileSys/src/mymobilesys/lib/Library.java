/*
 * Created on 13-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import mymobilesys.Global;

public class Library {

	public static String getContents(File aFile) {
		//...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				String line = null; //not declared within while loop
				/*
				 * readLine is a bit quirky :
				 * it returns the content of a line MINUS the newline.
				 * it returns null only for the END of the stream.
				 * it returns an empty String if two newlines appear in a row.
				 */
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return contents.toString();
	}
	
	public static String packageToFolder(String pack) {
		return(pack.replace(".", "/"));
		
	}
	
	public static boolean execCommand(String com) {
		boolean ret = true;
		return(ret);
	}
	
	public static String removeFileSeparator(String path) {
		String ret = path;
		if(path.startsWith("\\") || path.startsWith("/")) {
			ret = ret.substring(1);
		}
		if(path.endsWith("\\") || path.endsWith("/")) {
			ret = ret.substring(0,ret.length()-1);
		}
		return ret;
	}
	
	public static void printError(String error) {
		System.err.println(error);
		Global.errorLog += error + System.getProperty("line.separator");
	}
	
	public static void print(String msg) {
		System.out.println(msg);
	}
}

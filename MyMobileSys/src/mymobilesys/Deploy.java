/*
 * Created on 06-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys;


import mymobilesys.lib.Library;
import mymobilesys.lib.PathApp;
import mymobilesys.process.ProcessProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import argparser.ArgParser;
import argparser.StringHolder;

public class Deploy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// create holder objects for storing results ...

		StringHolder pathIC = new StringHolder();
		StringHolder fileProject = new StringHolder();
		StringHolder frName = new StringHolder();

		// create the parser and specify the allowed options ...

		ArgParser parser = new ArgParser("java mymobilesys.Deploy");
		
		parser.addOption ("-p %s #archivo de proyecto a procesar(Puede incluir ruta)", fileProject);
		parser.addOption ("-pic %s #indica carpeta raiz MyMobileSys(Si no se indica se asume la carpeta de instalación)", pathIC);
		parser.addOption ("-? %h #mostrar ayuda", null);
		parser.addOption ("-frname %s #cambia el nombre de la carpeta de compilación del proyecto", frName);
		parser.setHelpOptionsEnabled(true);

		// match the arguments ...
		parser.matchAllArgs (args);		
		
		//Si no se indica el proyecto a procesar entonces no se puede hacer nada
		if(fileProject.value == null) {
			Library.printError("Debe indicar el archivo de proyecto a procesar(-p)");
			System.exit(0);
		}
		
		if(pathIC.value != null) {
			Global.pathApp = pathIC.value;
		}
		
		if(readConfiguration()) {
			Global.errorLog = "";
			
			//Aqui se debe procesar el proyecto que llegue indicado en la linea de comandos
			ProcessProject proj = new ProcessProject(fileProject.value);
			proj.setFrName(frName.value);
			proj.process();
		} else {
			Library.printError("No se pudo leer configuración");
		}
		
		Library.print("Compilación finalizada.");
	}

	private static boolean readConfiguration() {
		boolean ret = true;

		if(Global.pathApp == null)
			Global.pathApp = new PathApp().getPath();

		//System.out.println("Path:" + Global.pathApp);

		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration(Global.pathApp + "/config/mms.properties"));
			Global.configuration = config;
		} catch (ConfigurationException e) {
			ret = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(ret);
	}
}

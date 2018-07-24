/*
 * Created on 12-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.process;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import mymobilesys.Global;
import mymobilesys.lib.CharsetDetector;
import mymobilesys.lib.Library;
import mymobilesys.lib.ParseTemplateJXP;
import mymobilesys.lib.VelocityLib;
import mymobilesys.xml.ui.Control;
import mymobilesys.xml.ui.Custom;
import mymobilesys.xml.ui.Screen;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class ProcessAndroid extends ProcessPlatform{

	protected String processFolder;
	private boolean appIconExist = false;
	
	public ProcessAndroid(CompositeConfiguration cfg, String fn, String path, String apath) {
		super(cfg, fn, path, apath);
	}

	public void process() {
		if(createFolder()) {
			compile();
			saveErrorLog();
		} else {
			Library.printError("No se pudo procesar carpeta: " + processFolder);
		}
	}

	private void saveErrorLog() {
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(processFolder + "/error.log"));
            out.write(Global.errorLog);
	        out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected boolean createFolder() {
		boolean ret = true;
		String frname = getFrName();
		
		processFolder = Global.configuration.getString("mms.projects.folder") + File.separator + "android" + File.separator + (frname == null ? config.getString("mms.name"): frname);
		File fc = new File(processFolder);

		if(fc.exists()) {
			try {
				FileUtils.deleteDirectory(fc);
				ret = fc.mkdirs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ret = false;
				e.printStackTrace();
			}
		}
			
		return(ret);
	}

	private void compile() {
		File fp = new File(processFolder);
		String[] lf = fp.list();
		boolean projectOK = true;
		
		if(lf == null || lf.length == 0) {
			//Creamos el proyecto Android
			if(!createAndroidProject()) {
				projectOK = false;
				Library.printError("No se pudo crear proyecto ANDROID");
			}
		}
		
		
		if(projectOK) {
			if(configureProject()) {
				if(generateCode()) {
					executeGradle();
				}
			}
		} else {
			Library.printError("No se pudo crear proyecto ANDROID");
		}
		
	}

	private boolean configureProject() {
		boolean ret = true;
		Library.print("Configurando proyecto...");
		//Procesamos el archivo keystore para que se firmen bien las aplicaciones
		ParseTemplateJXP pt = new ParseTemplateJXP(Global.pathApp + File.separator + "platform\\android\\project\\keystore.planjxp.properties");
		
		pt.put("keystore", Global.configuration.getString("android.keystore"));
		pt.put("alias", Global.configuration.getString("android.alias"));
		pt.put("passalias", Global.configuration.getString("android.alias.password"));
		pt.put("passkeystore", Global.configuration.getString("android.keystore.password"));
		pt.put("finalfile", (config.containsKey("mms.android.apkname") ? config.getString("mms.android.apkname") : config.getString("mms.name")) + ".apk");

		pt.parse();
		pt.save(processFolder + File.separator + "keystore.properties");
		
		
		//Procesamos el archivo AndroidMenifest.xml del proyecto ANDROID
		ParseTemplateJXP ptA = new ParseTemplateJXP(Global.pathApp + "/platform/android/project/AndroidManifest.planjxp.xml");
		ptA.put("configProject", this.config);
		ptA.put("appIconExist", appIconExist);
		ptA.parse();
		ptA.save(processFolder + "/app/src/main/AndroidManifest.xml");
		
		//Procesamos el archivo local.properties proyecto ANDROID
		ParseTemplateJXP ptL = new ParseTemplateJXP(Global.pathApp + "/platform/android/project/local.planjxp.properties");
		ptL.put("configMMS", Global.configuration);
		ptL.parse();
		ptL.save(processFolder + "/local.properties");
		
		//Procesamos el archivo build.gradle del proyecto ANDROID
		ParseTemplateJXP ptB = new ParseTemplateJXP(Global.pathApp + "/platform/android/project/build.planjxp.gradle");
		ptB.put("configProject", this.config);
		ptB.parse();
		ptB.save(processFolder + "/app/build.gradle");

		//Procesamos el archivo strings.xml del proyecto ANDROID
		ParseTemplateJXP pt1 = new ParseTemplateJXP(Global.pathApp + "/platform/android/res/values/strings.planjxp.xml");
		pt1.put("configProject", this.config);
		pt1.parse();
		pt1.save(processFolder + "/app/src/main/res/values/strings.xml");
		
		//Copiamos archivos necesarios para el proyecto
		try {
			//assets
			//librerias javascript
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/libs"), new File(processFolder));
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/assets/js/jquerymobile"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/assets/js/datebox"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/assets/js/numberformat"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/assets/css"), new File(processFolder + "/app/src/main/assets"));
			FileUtils.copyFileToDirectory(new File(Global.pathApp + "/platform/android/assets/js/androidInterface.js"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyFileToDirectory(new File(Global.pathApp + "/platform/android/assets/js/jquery-2.1.3.min.js"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyFileToDirectory(new File(Global.pathApp + "/platform/android/assets/js/constants.js"), new File(processFolder + "/app/src/main/assets/js"));
			FileUtils.copyFileToDirectory(new File(Global.pathApp + "/platform/android/assets/js/eventsfunctions.js"), new File(processFolder + "/app/src/main/assets/js"));
			//project.properties
			//FileUtils.copyFileToDirectory(new File(Global.pathApp + "/platform/android/project/project.properties"), new File(processFolder));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
		
		
		return(ret);
	}
	
	
	private boolean generateCode() {
		boolean ret = true;
		Vector<String> vConts = new Vector<String>();
		Vector<Screen> vScreens = new Vector<Screen>();
		
		Library.print("Generando código...");
		//Procesamos los XML UI
		File[] fl = this.getListXMLUI();
		Screen screen = null;

		if(fl.length == 1) { //Si solo hay un XML UI entonces asumimos que es el principal y lo procesamos
			screen = this.getScreenXML(fl[0]);
		} else if(fl.length > 1) { //Si hay mas de un XML UI quiere decir que se usará la característica de inclusión de otros archivos en el XML principal. 
			//Para determinar el XML principal se debe consultar la propiedad "mms.mainxmlui" en archivo properties
			//Por lo tanto debe existir esta propiedad para que podamos continuar

			if(config.containsKey("mms.mainxmlui")) {
				File fs = buscaXMLPrincipal(fl, config.getString("mms.mainxmlui"));

				if(fs != null) {
					screen = this.getScreenXML(fs);
				} else {
					Library.printError("No se pudo encontrar el XML: " + config.getString("mms.mainxmlui"));
					return false;
				}
			} else {
				Library.printError("No se encuentra la propiedad 'mms.mainxmlui' en archivo properties");
				return false;
			}
		}


		if(screen != null) {
			vScreens.add(screen);

			Vector<Control> htc = this.getScreenControls(screen);
			//Para cada XML generamos los JavaScript y HTML que corresponden
			
			if(!generateScreen(screen, htc)) {
				Library.printError("Error al procesar XML UI");
				return false;
			}
			
			//Obtenemos información de los controladores para generar las clases nativas posteriormente
			Vector<String> vScrConts = this.getPackageClassesControllers(this.getControllerData(htc, screen));

			Iterator<String> iter = vScrConts.iterator();
			while(iter.hasNext()) {
				String pc = iter.next();
				if(!vConts.contains(pc)) {
					vConts.add(pc);
				}
			}
		} else {
			Library.printError("No se encontró archivo XML UI principal");
			return false;
		}
		
		//Copiamos archivos necesarios para la aplicacion
		try {
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/src/mms"), new File(processFolder + "/app/src/main/java"));
			FileUtils.copyDirectory(new File(pathProject + "/src"), new File(processFolder + "/app/src/main/java"));
			
			//Revisamos los archivos java del proyecto y los transformamos a UTF-8 si aplica(Para que compile bien en gradle y tenga mejor soporte para diferentes lenguajes)
			Collection<File> cfiles = FileUtils.listFiles(new File(pathProject + "/src"), new String[] {"java"}, true);
			for(File f: cfiles) {
				String[] charsetsToBeTested = {"windows-1252"};
				 
		        CharsetDetector cd = new CharsetDetector();
		        Charset charset = cd.detectCharset(f, charsetsToBeTested);
				//System.out.println(f.getAbsolutePath() + " ==> " + charset.displayName());
				
				if(charset != null) {
			        String content = FileUtils.readFileToString(f, charset);
			        FileUtils.write(new File(processFolder + "/app/src/main/java" + f.getAbsolutePath().substring((pathProject + "/src").length())), content, "UTF-8");
				}
			}
			
			FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/res/layout"), new File(processFolder + "/app/src/main/res"));
			FileUtils.copyDirectoryToDirectory(new File(pathProject + "/assets"), new File(processFolder + "/app/src/main"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}	
		ParseTemplateJXP pt;

		/**TODO
		 * Por el momento solo procesaremos el primer Screen definido en los XML
		 * Se debe implementar el soporte para varios screen en el mismo Activity.
		 */
		//Ahora creamos las clases Java que permiten la ejecución nativa de los controladores
		pt = new ParseTemplateJXP(Global.pathApp + "/platform/android/src/MainActivity.planjxp.java");
		
		pt.put("configProject", this.config);
		pt.put("xmlControls", this.getScreenControls(vScreens.get(0)));
		pt.put("screen", vScreens.get(0));
		pt.put("configproject", config);
		pt.put("classes", vConts);
		pt.put("vlib", new VelocityLib());

		pt.parse();
		pt.save(processFolder + "/app/src/main/java/" + Library.packageToFolder(config.getString("mms.package").toLowerCase()) + "/" + config.getString("mms.name") + "Activity.java");
						
		pt = new ParseTemplateJXP(Global.pathApp + "/platform/android/src/MMSActivity.planjxp.java");
		
		pt.put("configProject", this.config);
		pt.put("xmlControls", this.getScreenControls(vScreens.get(0)));
		pt.put("screen", vScreens.get(0));
		pt.put("configproject", config);
		pt.put("classes", vConts);
		pt.put("vlib", new VelocityLib());

		pt.parse();
		pt.save(processFolder + "/app/src/main/java/mms/app/MMSActivity.java");
		
		//Si no se usa la libreria ZXing en el proyecto eliminamos el paquete para
		//disminuir clases en la aplicación
		if(config.getInt("mms.usezxing", 0) != 1) {
			try {
				FileUtils.deleteDirectory(new File(processFolder + "/app/src/main/java/mms/barcode/zxing"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return(ret);
	}
	
	private File buscaXMLPrincipal(File[] fl, String fname) {
		File ret = null;

		for(File f: fl) {
			if(f.getName().equals(fname)) {
				ret = f;
				break;
			}
		}

		return ret;
	}
	
	private boolean generateScreen(Screen screen, Vector<Control> htc) {
		boolean ret = true;
		//generamos javascript de puente entre eventos de jquerymobile y llamada a controladores nativos
		ParseTemplateJXP pt = new ParseTemplateJXP(Global.pathApp + "/platform/android/assets/js/mms.planjxp.js");
		pt.put("xmlControls", htc);
		pt.put("screen", screen);
		pt.parse();
		pt.save(processFolder + "/app/src/main/assets/js/MMS_" + screen.getName() + ".js", "UTF-8");

		//generamos javascript de puente entre eventos de jquerymobile y llamada a controladores nativos
		ParseTemplateJXP ptI = new ParseTemplateJXP(Global.pathApp + "/platform/android/assets/js/mobileinit.planjxp.js");
		ptI.put("xmlControls", htc);
		ptI.put("screen", screen);
		ptI.parse();
		ptI.save(processFolder + "/app/src/main/assets/js/mobileinit.js", "UTF-8");

		//Creamos el HTML que forma el screen en la aplicación
		ParseTemplateJXP ptH = new ParseTemplateJXP(Global.pathApp + "/platform/android/assets/screen.planjxp.html");
		ptH.put("xmlControls", htc);
		ptH.put("screen", screen);
		ptH.put("vlib", new VelocityLib());
		ptH.put("projectinfo", projectinfo);
		ptH.parse();
		ptH.save(processFolder + "/app/src/main/assets/MMS_" + screen.getName() + ".html", "UTF-8");
		
		Vector<ControllerData> vControllers = this.getControllerData(htc, screen);
		Vector<String> vPackages = this.getPackageClassesControllers(vControllers);
		
		try {
			//Se verifica si existe el directorio inludeui en el proyecto
			//si existe se copia todo el directorio a la carpeta assets en el proyecto Android
			File dirIncludeUI = new File(pathProject + "/includeui");
			boolean conI = dirIncludeUI.exists() && FileUtils.listFiles(dirIncludeUI, null, true).size() > 0;
				
			if(conI) {
				FileUtils.copyDirectory(new File(pathProject + "/includeui"), new File(processFolder + "/app/src/main/assets"));
				
			} else {
				FileUtils.copyDirectoryToDirectory(new File(Global.pathApp + "/platform/android/assets/themedefault"), new File(processFolder + "/app/src/main/assets"));
			}
		
			//Se verifica si se han creado script para Controles Custom y CSS y se copian a la carpeta de la aplicacion
			//Para que las encuentre el Browser de la aplicación
			for(Control ctl: htc) {
				if(ctl instanceof Custom) {
					try {
						FileUtils.copyFileToDirectory(new File(pathProject + "/xml/ui/custom/" + ctl.getName() + ".js"),
								                      new File(processFolder + "/app/src/main/assets/js/custom/"));
					}catch(FileNotFoundException e) {
						
					}
					
					try {
						FileUtils.copyFileToDirectory(new File(pathProject + "/xml/ui/custom/" + ctl.getName() + ".css"),
			                      new File(pathProject + "/app/src/main/assets/css/custom/"));
					}catch(FileNotFoundException e) {
						
					}
				}
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		}
		
		return(ret);
	}
		
	private boolean createAndroidProject() {
		boolean ret = true;
		try {
			String line;

			Library.print("Creando proyecto ANDROID");
			//Creamos el proyecto Android en la carpeta de compilación
			FileUtils.copyDirectory(new File(Global.pathApp + "/platform/android/project/MMSBase"), new File(processFolder));
			FileUtils.copyDirectory(new File(Global.pathApp + "/platform/android/libs"), new File(processFolder + "/app/libs"));
						
			//Cambiamos la configuración de los archivos del proyecto para que compile adecuadamente
			//Procesamos el archivo AndroidMenifest.xml del proyecto ANDROID
			ParseTemplateJXP ptA = new ParseTemplateJXP(Global.pathApp + "/platform/android/project/MMSBase.planjxp.iml");
			ptA.put("configProject", this.config);
			ptA.parse();
			ptA.save(processFolder + "/" + config.getString("mms.name") + ".iml");
			
			//Creamos la estructura de directorios de paquetes del código fuente
			new File(processFolder + "/app/src/main/java/" + Library.packageToFolder(config.getString("mms.package").toLowerCase())).mkdirs();
			
			//Modificamos archivos de Android Studio para poder cargar bien el proyecto en dicha herramienta si es que se necesita
			ptA = new ParseTemplateJXP(Global.pathApp + "/platform/android/project/modules.planjxp.xml");
			ptA.put("configProject", this.config);
			ptA.parse();
			ptA.save(processFolder + "/.idea/modules.xml");
			
			String appicon = pathProject + "/appicon.png";
			
			if(new File(appicon).exists()) {
				//Leemos el icono
				File fappicon = new File(appicon);
				
				Thumbnails.of(fappicon)
		        .size(192, 192)
		        .toFile(new File(processFolder + "/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png"));
				
				Thumbnails.of(fappicon)
		        .size(144, 144)
		        .toFile(new File(processFolder + "/app/src/main/res/mipmap-xxhdpi/ic_launcher.png"));

				Thumbnails.of(fappicon)
		        .size(96, 96)
		        .toFile(new File(processFolder + "/app/src/main/res/mipmap-xhdpi/ic_launcher.png"));			

				Thumbnails.of(fappicon)
		        .size(48, 48)
		        .toFile(new File(processFolder + "/app/src/main/res/mipmap-mdpi/ic_launcher.png"));	
				
				Thumbnails.of(fappicon)
		        .size(72, 72)
		        .toFile(new File(processFolder + "/app/src/main/res/mipmap-hdpi/ic_launcher.png"));	
				
				appIconExist = true;
			}

		}
		catch (Exception err) {
			err.printStackTrace();
			ret = false;
		}			
		return(ret);
	}
	
	private String getAndroidTarget() {
		String projectTarget = Global.configuration.getString("android.target");
		
		//Si se usa zxing se debe asegurar al menos la API 14 al compilar
		if(config.getInt("mms.usezxing", 0) == 1) {
			if(getAPILevel(projectTarget) < 14) {
				projectTarget = "android-14";
			}
		}
		
		return projectTarget;
	}
	
	private int getAPILevel(String target) {
		int apilevel = 0;
		
		if(target.startsWith("android")) {
			apilevel = Integer.parseInt(target.substring(target.indexOf("-")+1));
		} else {
			apilevel = Integer.parseInt(target);
		}
		
		return apilevel;
	}
	
	private boolean executeGradle() {
		boolean ret = true;
		try {
			String line;
			String pathTools = processFolder;
			String sexec =  pathTools + "\\gradlew.bat assemblerelease --console plain";

			Library.print("Compilando aplicación final:");
			
			Process p = Runtime.getRuntime().exec(sexec, null, new File(pathTools));

		    inheritIO(p.getInputStream(), System.out, false);
		    inheritIO(p.getErrorStream(), System.err, true);
		    
		    p.waitFor();
/*
			BufferedReader input =	new BufferedReader(new InputStreamReader(p.getErrorStream()));

			if((line = input.readLine()) != null) {
				do {
					System.out.println(line);
				}while ((line = input.readLine()) != null);
				input.close();
			} else {
				ret = false;
				input.close();

				input =	new BufferedReader(new InputStreamReader(p.getErrorStream()));
				if((line = input.readLine()) != null) {
					do {
						System.out.println(line);
					}while ((line = input.readLine()) != null);
				}
				input.close();
			}
*/
		}
		catch (Exception err) {
			err.printStackTrace();
			ret = false;
		}			
		return(ret);
	}
	
    private static void inheritIO(final InputStream src, final PrintStream dest, final boolean saveLog) {
        new Thread(new Runnable() {
            public void run() {
            	String line = "";
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    dest.println(line = sc.nextLine());
                    if(saveLog) 
                    	Global.errorLog += line + System.getProperty("line.separator");
                }
            }
        }).start();
    }
}

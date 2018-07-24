/*
 * Created on 12-02-2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mymobilesys.process;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import mymobilesys.xml.ui.Container;
import mymobilesys.xml.ui.Control;
import mymobilesys.xml.ui.Footer;
import mymobilesys.xml.ui.Grid;
import mymobilesys.xml.ui.GridCol;
import mymobilesys.xml.ui.Header;
import mymobilesys.xml.ui.Panel;
import mymobilesys.xml.ui.PanelMenu;
import mymobilesys.xml.ui.Radio;
import mymobilesys.xml.ui.RadioGroup;
import mymobilesys.xml.ui.Screen;
import mymobilesys.xml.ui.base.Controller;

import org.apache.commons.configuration.CompositeConfiguration;

public class ProcessPlatform {

	protected String filenameProject = null;
	protected String pathProject = null;
	protected String frName = null; //Permite indicar ruta adicional a agregar en la ruta de MMS en donde compila los proyectos

	protected CompositeConfiguration config;

	protected ProjectInfo projectinfo = null;

	public ProcessPlatform(CompositeConfiguration cfg, String fn, String path, String frname) {
		filenameProject = fn;
		pathProject = path;
		frName = frname;

		config = cfg;
		
		projectinfo = new ProjectInfo();
		projectinfo.projectpath = pathProject;
	}

	protected File[] getListXMLUI() {
		File f = new File(pathProject + "/xml/ui");
		File[] list = f.listFiles(new FilterXMLUI());
		return(list);
	}

	protected Screen getScreenXML(File xmlForm) {
		Screen screen = null;
		try {
			screen = (Screen)unmarshallObjectXMLForm(xmlForm);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		return(screen);
	}
	
	//Recorre la estructura de objetos XML UI y retorna una HasTable con los controles
	//El hashtable al ser lineal permite facilitar el trabajo en ciertas rutinas de las plantillas velocity
	//donde se requiere recorrer los objetos de manera lineal y no siguiendo la estructura del XML
	protected Vector<Control> getScreenControls(Screen xmlControls) {
		Vector<Control> htControls = new Vector<Control>();
		htControls.clear();

		List<Panel> lstP = xmlControls.getPanels();
		Iterator<Panel> iter = lstP.iterator();
		//Recorremos los paneles y los agregamos al vector de controles, luego por cada Panel agregamos los conroles que tenga adentro
		while(iter.hasNext()) {
			Panel panel = iter.next();
			htControls.add(panel);

			//Si el panel tiene un header tenemos que agregar los controles del header para que se genere el código correctamente
			Header hd = panel.getHeader();
			if(hd != null) {
				addControlsTable(htControls, hd.getControls());
			}
			
			htControls = addControlsTable(htControls, panel.getControls());
			
			//Si el panel tiene un footer tenemos que agregar los controles del footer para que se genere el código correctamente
			Footer ft = panel.getFooter();
			if(ft != null) {
				addControlsTable(htControls, (List<Control>) (List<? extends Control>)ft.getControls());
			}
		}
		
		List<PanelMenu> lstPM = xmlControls.getPanelsMenu();
		Iterator<PanelMenu> iterPM = lstPM.iterator();
		//Recorremos los paneles de tipo menú y los agregamos al vector de controles, luego por cada Panel agregamos los conroles que tenga adentro
		while(iterPM.hasNext()) {
			PanelMenu panel = iterPM.next();
			htControls.add(panel);

			htControls = addControlsTable(htControls, panel.getControls());

		}
		return(htControls);
	}

	private Vector<Control> addControlsTable(Vector<Control> htControls, List<Control> xmlControls) {
		Iterator<Control> iter = xmlControls.iterator();
		while (iter.hasNext()) {
			Control ctl = iter.next();
			htControls.add(ctl);

			if(ctl instanceof Container) {
				Container cont = (Container)ctl;
				addControlsTable(htControls, cont.getControls());
			} else if( ctl instanceof Grid) {
				Grid grd = (Grid)ctl;
				for(GridCol grdc: grd.getBlocks()) {
					addControlsTable(htControls, grdc.getControls());
				}
			} else if(ctl instanceof RadioGroup) {
				RadioGroup rg = (RadioGroup)ctl;
				for(Radio radio: rg.getRadios()) {
					htControls.add(radio);
				}
			}
			
			//System.out.println(iter.next());
		}
		return(htControls);
	}
	
	/*
	private void addControlsRadio(Vector<Control> htControls, List<Radio> radios) {
		// TODO Auto-generated method stub
		Iterator<Radio> iter = radios.iterator();
		while (iter.hasNext()) {
			Control ctl = iter.next();
			htControls.add(ctl);
		}
	}
	*/
	
	protected Vector<ControllerData> getControllerData(Vector<Control> htControls, Screen scr) {
		Vector<ControllerData> vcd = new Vector<ControllerData>();

		//Primero agregamos los controladores del Screen(Si scr viene en null no se busca en el screen)
		if(scr != null) {
			Iterator<Controller> iterS = scr.getControllers().iterator();
			while(iterS.hasNext()) {
				Controller cont = iterS.next();
				ControllerData cd = new ControllerData(cont);

				if(!vcd.contains(cd)) {
					vcd.add(new ControllerData(cont));
				}

			}
		}

		//Luego agregamos los controladores de todos los controles del Screen
		Iterator<Control> iter = htControls.iterator();
		while (iter.hasNext()) {
			Control ctl = iter.next();
			
			Iterator<Controller> iterM = ctl.getControllers().iterator();
			while(iterM.hasNext()) {
				Controller cont = iterM.next();
				ControllerData cd = new ControllerData(cont);
				
				if(!vcd.contains(cd)) {
					vcd.add(new ControllerData(cont));
				}
				
			}
			
			//Agregamos los controladores de los containers
			if(ctl instanceof Grid) {
				Iterator<GridCol> iterb = ((Grid)ctl).getBlocks().iterator();
				while(iterb.hasNext()) {
					Vector<ControllerData> vcdg = new Vector<ControllerData>();
					
					vcdg = getControllerData(new Vector<Control>(iterb.next().getControls()), null);
					vcd.addAll(vcdg);
					
				}
			}
		}
		
		return(vcd);
	}
	
	protected Vector<String> getPackageClassesControllers(Vector<ControllerData> vCont) {
		Vector<String> vClasses = new Vector<String>();
		
		Iterator<ControllerData> iter = vCont.iterator();
		
		while(iter.hasNext()) {
			ControllerData cd = iter.next();
			
			if(!vClasses.contains(cd.getPackageClassName())) {
				vClasses.add(cd.getPackageClassName());
			}
		}
		return(vClasses);
	}
	
	protected Vector<String> getPackagesControllers(Vector<ControllerData> vCont) {
		Vector<String> vPack = new Vector<String>();
		
		Iterator<ControllerData> iter = vCont.iterator();
		
		while(iter.hasNext()) {
			ControllerData cd = iter.next();
			
			if(!vPack.contains(cd.getPackageName())) {
				vPack.add(cd.getPackageName());
			}
		}
		return(vPack);
	}
	
	private Object unmarshallObjectXMLForm(File is) throws Exception {
		String packageName = "mymobilesys.xml.ui";
		
		JAXBContext context = JAXBContext.newInstance(packageName);
		
		System.setProperty("javax.xml.accessExternalDTD", "all"); 
		
		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
		 
	    XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(is.getAbsolutePath()));
	    
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		TreeListener tl = new TreeListener();
		unmarshaller.setListener(tl);
		
		Object jaxbObject = unmarshaller.unmarshal(is);
		return jaxbObject;
	}
	
	private class FilterXMLUI implements FileFilter {

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			String fname = f.getName();
			return fname.substring(fname.length() - 4).equals(".xml");
		}
		
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}
}

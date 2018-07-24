package mymobilesys.process;

import javax.xml.bind.Unmarshaller.Listener;

import mymobilesys.xml.ui.Control;
import mymobilesys.xml.ui.base.ControlBase;

public class TreeListener extends Listener {

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		ControlBase ctl = (ControlBase)target;

		if(parent != null) ctl.setParent((Control)parent);
	}

	

}
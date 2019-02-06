package mms.helper.ui.fm7;

import mms.ui.UIController;
/****
 * 
 * @author Claudio
 *
 * Controlador para el manejo de eventos de los Dialogos en FM7 gatillados desde librería nativa
 * 
 */
public class ContDialog extends UIController {
	
	private OnDialogListener onDialogListener = null;
	
	public ContDialog(OnDialogListener dl) {
		onDialogListener = dl;
	}
	
	public void onButtonClick(String data) {
		int index = Integer.parseInt(data);

		if(onDialogListener != null)
			onDialogListener.onButtonClick(index);
	}
	
	public void onDialogClose(String data) {
		
	}
}

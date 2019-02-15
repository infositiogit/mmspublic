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
	private Dialog dialog = null;
	
	public ContDialog(OnDialogListener dl, Dialog d) {
		onDialogListener = dl;
		dialog = d;
	}
	
	public void onButtonClick(String data) {
		int index = Integer.parseInt(data);

		if(onDialogListener != null)
			onDialogListener.onButtonClick(index, dialog);
	}
	
	public void onDialogClose(String data) {
		
	}
}

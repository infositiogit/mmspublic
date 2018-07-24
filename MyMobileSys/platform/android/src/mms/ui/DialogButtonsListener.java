package mms.ui;

public class DialogButtonsListener implements OnClickListener {
	protected OnDialogOptionListener mOnDialogListener = null;
	private int lastOption = -1;
	
	public DialogButtonsListener(OnDialogOptionListener l) {
		mOnDialogListener = l;
	}
	
	@Override
	public void onClick(UIControl c) {
		// TODO Auto-generated method stub
		int op = ((Integer)c.getData()).intValue();
		
		lastOption = op;
		
		if(mOnDialogListener != null)
			mOnDialogListener.onSelect((UIModalDialog)c.parent, op);
	}
	
	public int getLastOption() {
		return lastOption;
	}
}
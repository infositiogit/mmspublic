package mms.barcode.zxing;

import mms.app.MMSActivity;
import mms.app.MMSApp;
import mms.barcode.BarcodeListener;

public class BarcodeZXing {
	private BarcodeListener listenerBarcode = null;
	private String barcodeFormats = "";
	
	public BarcodeZXing(BarcodeListener l, String bcf) {
		listenerBarcode = l;
		barcodeFormats = bcf;
	}
	
	public void read(){
		MMSActivity mainAct = MMSApp.getMMSActivity();
		//Intent intentScan = new Intent("cl.infositio.barcode.SCAN");
		mainAct.startBarcodeZXingForResult(listenerBarcode, barcodeFormats);
	} 
}
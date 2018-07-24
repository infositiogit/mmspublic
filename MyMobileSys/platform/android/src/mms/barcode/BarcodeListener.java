package mms.barcode;

public interface BarcodeListener {
	public void onBarcode(String barcode);
	public void onCancel();
}
package mms.lib;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Conjunto de métodos que nos auxilian en el manejo de imágenes.
 * 
 * @author Juan Carlos (jsanmartin@infositio.cl) siendo 11-02-2014; 22:32:53
 */
public class ImagesUtil
{
	/**
	 * Ruta absoluta de la imagen.
	 */
	private String	rutaAbsoluta;



	/**
	 * Retorna un bitmap escalado (y potencialmente rotado) de la imagen obtenida por la cámara.
	 * 
	 * @param w - ancho del ImageView donde pondremos el Bitmap
	 * @param h - alto del ImageView donde pondremos el Bitmap
	 * @param grados - por ejemplo, 90 significa 90º rotado a la derecha.
	 * @return - referencia al Bitmap escalado; null si no encuentra la imagen obtenida por usuario.
	 */
	public Bitmap obtenerBitmapEscaladoRotado(int w, int h, int grados)
	{
		Bitmap bitmap= null;
		Matrix matrix;

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions= new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds= true;
		BitmapFactory.decodeFile(this.rutaAbsoluta, bmOptions);
		int photoW= bmOptions.outWidth;
		int photoH= bmOptions.outHeight;
		double ratio = 1;
		
		if(photoW > photoH) {
			if(photoW > w) {
				ratio = (double)w / (double)photoW;
			}
		} else {
			if(photoH > h) {
				ratio = (double)h / (double)photoH;
			}
		}
		
		w = (int) (photoW * ratio);
		h = (int) (photoH * ratio);
		
		// Determine how much to scale down the image
		int scaleFactor= (int) Math.max(1.0, Math.min((double) photoW / (double)w, (double)photoH / (double)h));	//1, 2, 3, 4, 5, 6, ...
		scaleFactor= (int) Math.pow(2.0, Math.floor(Math.log((double) scaleFactor) / Math.log(2.0)));				//1, 2, 4, 8, ...

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds= false;
		bmOptions.inSampleSize= scaleFactor;
		bmOptions.inPurgeable= true;
		bmOptions.outWidth = w;
		bmOptions.outHeight = h;
		
		do
		{
			try
			{
				scaleFactor*= 2;
				bitmap= BitmapFactory.decodeFile(this.rutaAbsoluta, bmOptions);
			}
			catch(OutOfMemoryError e)
			{
				bmOptions.inSampleSize= scaleFactor;
			}
		}
		while(bitmap == null && scaleFactor <= 256);

		if(bitmap == null)
			return null;

		// y rotamos
		matrix= new Matrix();
		matrix.postRotate(grados);
		bitmap= Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);		//FIXME puede lanzar un OutOfMemoryError

		// y nos vamos
		return bitmap;
	}



	/**
	 * Retorna un bitmap escalado (y potencialmente rotado) de una imagen cualquiera. Útil si por azar la app se destruyó contra la voluntad del usuario,
	 * perdiéndose entonces la ruta absoluta del objeto IO que también fue destruido.
	 * 
	 * @param rutaAbsoluta - ruta absoluta de la imagen. Ejemplo: "/mnt/sdcard/Pictures/Infocamara_android/miImagen.jpg"
	 * @param w - ancho del ImageView donde pondremos el Bitmap
	 * @param h - alto del ImageView donde pondremos el Bitmap
	 * @param grados - por ejemplo, 90 significa 90º rotado a la derecha.
	 * @return - referencia al Bitmap escalado; null si no encuentra la imagen obtenida por usuario.
	 */
	public Bitmap obtenerBitmapEscaladoRotado(String rutaAbsoluta, int w, int h, int grados)
	{
		this.rutaAbsoluta= rutaAbsoluta;
		return this.obtenerBitmapEscaladoRotado(w, h, grados);
	}



	/**
	 * Rota la imagen en 0º, 90º, 180º y 270º, conservando sus dimensiones originales (con la conveniente transposición). Básicamente es como transponer una
	 * matriz.
	 * 
	 * @param rutaAbsoluta - ruta absoluta de la imagen. Ejemplo: "/mnt/sdcard/Pictures/Infocamara_android/miImagen.jpg"
	 * @param grados - por ejemplo, 90 significa 90º rotado a la derecha.
	 * @return referencia al Bitmap rotado; null si no encuentra la imagen indicada por el usuario.
	 */
	public Bitmap obtenerBitmapRotado(String rutaAbsoluta, int grados)
	{
		int w, h;
		BitmapFactory.Options options;
		Bitmap bitmap;

		this.rutaAbsoluta= rutaAbsoluta;

		options= new BitmapFactory.Options();
		options.inJustDecodeBounds= true;
		BitmapFactory.decodeFile(this.rutaAbsoluta, options);

		if(grados == 0 || grados == 180)
		{
			w= options.outWidth;
			h= options.outHeight;
		}
		else
		{
			h= options.outWidth;
			w= options.outHeight;
		}

		bitmap= this.obtenerBitmapEscaladoRotado(w, h, grados);
		return bitmap;
	}



	/**
	 * Facilita la reducción de la calidad de una imagen jpg, a fin de que pese menos.
	 * 
	 * @param bitmap imagen original.
	 * @param nuevo_ancho ancho de la nueva imagen.
	 * @param nuevo_alto alto de la nueva imagen.
	 * @param nueva_calidad calidad de la nueva imagen.
	 * @return referencia a un ByteArrayOutputStream conteniendo un byte[] con los datos de la nueva imagen.
	 */
	public ByteArrayOutputStream modificarCaracteristicas(Bitmap bitmap, int nuevo_ancho, int nuevo_alto, int nueva_calidad)
	{
		ByteArrayOutputStream byteArrayOutputStream;
		// ByteArrayInputStream byteArrayInputStream;

		/*
		 * Modificamos dimensiones
		 */
		bitmap= Bitmap.createScaledBitmap(bitmap, nuevo_ancho, nuevo_alto, false);		//FIXME puede lanzar un OutOfMemoryError

		/*
		 * Modificamos calidad.
		 */
		byteArrayOutputStream= new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, nueva_calidad, byteArrayOutputStream);

		// byteArrayInputStream= new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		// bitmap= BitmapFactory.decodeStream(byteArrayInputStream);

		return byteArrayOutputStream;
	}
}

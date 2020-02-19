/**
 * Esta clase representa una imagen
 * @author francis
 */

package imagen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Imagen 
{
	/*
	 * Atributos de la clase
	 */
	
	private Context contexto;
	private Bitmap imagen;
	
	/*
	 * Métodos de la clase
	 */
	
	/**
	 * Constructor de la clase
	 * @param contexto Contexto
	 * La imagen por defecto está vacía
	 */
	public Imagen(Context contexto)
	{
		this.contexto = contexto;
		imagen = null;
	}
	
	/**
	 * Carga una imagen de un fichero de imagen
	 * @param rutaimagen Ruta de la imagen
	 */
	public void cargar(String rutaimagen)
	{
		imagen = BitmapFactory.decodeFile(rutaimagen);
	}
	
	/**
	 * Carga una imagen de un recurso del sistema
	 * @param recurso Recurso del sistema
	 */
	public void cargar(int recurso)
	{
		imagen = BitmapFactory.decodeResource(contexto.getResources(), recurso);
	}
	
	/**
	 * Devuelve la imagen
	 * @return Imagen
	 */
	public Bitmap getImagen()
	{
		return imagen;
	}
	
	/**
	 * Libera los recursos de la imagen
	 */
	public void liberar()
	{
		if( imagen != null )
			imagen.recycle();
	}
}

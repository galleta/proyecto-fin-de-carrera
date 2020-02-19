/**
 * @author francis
 * Esta clase representa un niño
 */

package ninio;

import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;
import android.content.Context;
import android.content.SharedPreferences;

public class Ninio 
{
	/*
	 * Atributos de la clase
	 */
	
	private String nombreninio;
	private Context contexto;
	
	/*
	 * Métodos de la clase
	 */
	
	/**
	 * Constructor de la clase
	 * @param nombre Nombre del niño
	 * @param c Contexto
	 */
	public Ninio(String nombre, Context c)
	{
		this.setNombre(nombre);
		this.contexto = c;
	}

	/**
	 * Devuelve el nombre del niño
	 * @return Nombre del niño
	 */
	public String getNombre() 
	{
		return nombreninio;
	}

	/**
	 * Modifica el nombre del niño
	 * @param nombreninio Nuevo nombre
	 */
	private void setNombre(String nombreninio) 
	{
		this.nombreninio = nombreninio;
	}
	
	/**
	 * Almacena la configuración de un niño en un fichero del tipo ConfiguracionNinioXXX.xml 
	 */
	public void almacenar()
	{
		/*
		 * Para almacenar datos en ficheros xml utilizamos SharedPreferences, los modos
		 * para almacenar son los siguientes:
		 * 	1.- MODE_PRIVATE. Sólo nuestra aplicación tiene acceso a estas preferencias.
		 * 	2.- MODE_WORLD_READABLE. Todas las aplicaciones pueden leer estas preferencias, pero sólo la nuestra puede modificarlas.
		 * 	3.- MODE_WORLD_WRITABLE. Todas las aplicaciones pueden leer y modificar estas preferencias.
		 */
		
		SharedPreferences prefs = contexto.getSharedPreferences(UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_ninio) + getNombre(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("NOMBRE", getNombre());
		editor.commit();
		
		/*
		 * Una vez llamado commit() para almacenar se guardará en la ruta:
		 * 
		 * 	/data/data/paquete.java/shared_prefs/nombre_coleccion.xml
		 * 
		 * en este caso: /data/data/com.example.descubre_sonidos/shared_prefs/nombre_coleccion.xml
		 */
	}
	
	/**
	 * Obtiene el niño que se quiera de un fichero del tipo ConfiguracionNinioXXX.xml
	 * @param nombreni Nombre del niño a obtener
	 */
	public void obtener(String nombreni)
	{
		SharedPreferences prefs = contexto.getSharedPreferences(UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_ninio) + nombreni, Context.MODE_PRIVATE);
		this.setNombre(prefs.getString("NOMBRE", ""));
	}
}

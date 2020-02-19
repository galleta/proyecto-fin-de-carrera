/**
 * @author francis
 * Esta clase tiene los métodos de utilidad para las actividades
 */

package actividad;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.widget.Spinner;

import com.example.descubre_sonidos.R;

import cajon_desastre.UtilidadesVarias;

public final class UtilidadesActividades 
{
	/**
	 * Comprueba si ya está creada una actividad
	 * @param actividad Actividad que llama
	 * @param nombreactividad Actividad para ver si está creada o no
	 * @return Verdadero si la actividad ya existe, falso en caso contrario
	 */
	public static boolean existeActividad(Activity actividad, String nombreactividad)
	{
		return obtenerNombresActividades(actividad).contains(nombreactividad);
	}
	
	/**
	 * Obtiene una lista con los nombres de todas las actividades creadas
	 * @param actividad Actividad que llama (contexto)
	 * @return Lista con los nombres de todas las actividades que hay creadas en el juego
	 */
	public static ArrayList<String> obtenerNombresActividades(Activity actividad)
	{
		ArrayList<String> configuraciones = new ArrayList<String>();
		String ruta = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.ruta_ficheros_xml);
		String cabecera = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.configuracion_actividad);
		String tipofichero = ".xml";
		File f = new File(ruta);
		File[] ficherosconfiguracionesactividades = f.listFiles();
		
		if( ficherosconfiguracionesactividades != null )
		{
			for(int i = 0; i < ficherosconfiguracionesactividades.length; i++)
			{
				if( ficherosconfiguracionesactividades[i].isFile() && ficherosconfiguracionesactividades[i].getName().substring(0, cabecera.length()).compareTo(cabecera) == 0)
				{
					String nombre = ficherosconfiguracionesactividades[i].getName().substring(cabecera.length());
					nombre = nombre.substring(0, nombre.length() - tipofichero.length());
					
					configuraciones.add(nombre);
				}
			}
		}
		else
			configuraciones.add(UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_actividades_creadas));
	
		return configuraciones;
	}
	
	/**
	 * Muestra los nombres de las actividades que hay almacenados en el sistema
	 * @param actividad Actividad que llama
	 * @param spNombreActividad Spinner donde se va a mostrar la información
	 * @return Nombre de la primera actividad
	 */
	public static String mostrarConfiguracionesActividades(Activity actividad, Spinner spNombreActividad)
	{
		String primeraactividad = "";
		
		ArrayList<String> configuraciones = new ArrayList<String>();
		configuraciones = obtenerNombresActividades(actividad);		
		
		if( configuraciones.size() == 0 )
		{
			primeraactividad = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_actividad);
			configuraciones.add(UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_actividad));
		}
		else
			primeraactividad = configuraciones.get(0).toString();
		
		UtilidadesVarias.rellenarSpinner(actividad, spNombreActividad, configuraciones);
		
		return primeraactividad;
	}
}

/**
 * @author francis
 * Esta clase tiene los métodos de utilidad para los niños
 */

package ninio;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.widget.Spinner;

import com.example.debajodeunboton.R;

import cajon_desastre.UtilidadesVarias;

public final class UtilidadesNinios 
{
	/**
	 * Comprueba si ya está registrado un niño
	 * @param actividad Actividad que llama
	 * @param nombreninio Nombre del niño
	 * @return Verdadero si el niño ya está registrado, falso en caso contrario
	 */
	public static boolean existeNinio(Activity actividad, String nombreninio)
	{
		return obtenerNombresNinios(actividad).contains(nombreninio);
	}
	
	/**
	 * Obtiene una lista con los nombres todos los niños registrados
	 * @param actividad Actividad que llama (contexto)
	 * @return Lista con los nombres de todos los niños que hay registrados en el juego
	 */
	public static ArrayList<String> obtenerNombresNinios(Activity actividad)
	{
		ArrayList<String> ninios = new ArrayList<String>();
		String ruta = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.ruta_ficheros_xml);
		String cabecera = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.configuracion_ninio);
		String tipofichero = ".xml";
		File f = new File(ruta);
		File[] ficherosconfiguracionesninios = f.listFiles();
		
		if( ficherosconfiguracionesninios != null )
		{
			for(int i = 0; i < ficherosconfiguracionesninios.length; i++)
			{
				if( ficherosconfiguracionesninios[i].isFile() && ficherosconfiguracionesninios[i].getName().substring(0, cabecera.length()).compareTo(cabecera) == 0)
				{
					String nombre = ficherosconfiguracionesninios[i].getName().substring(cabecera.length());
					nombre = nombre.substring(0, nombre.length() - tipofichero.length());
					
					ninios.add(nombre);
				}
			}
		}
		else
			ninios.add(UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_ninios_creados));
		
		return ninios;
	}
	
	
	/**
	 * Muestra los nombres de los niños que hay almacenados en el sistema
	 * @param actividad Actividad que llama
	 * @param spNombreNinio Spinner donde se va a mostrar la información
	 * @return El nombre del primer niño
	 */
	public static String mostrarConfiguracionesNinios(Activity actividad, Spinner spNombreNinio)
	{
		String permininio = "";
		
		/*
		 * Recorro la ruta y añado los nombres de los niños
		 */
		
		ArrayList<String> ninios = new ArrayList<String>();
		ninios = obtenerNombresNinios(actividad);
		
		if( ninios.size() == 0 )
		{
			permininio = UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_ninio);
			ninios.add(UtilidadesVarias.obtenerStringXML(actividad.getBaseContext(), R.string.no_ninios_creados));
		}
		else
			permininio = ninios.get(0).toString();
		
		UtilidadesVarias.rellenarSpinner(actividad, spNombreNinio, ninios);
		
		return permininio;
	}
}


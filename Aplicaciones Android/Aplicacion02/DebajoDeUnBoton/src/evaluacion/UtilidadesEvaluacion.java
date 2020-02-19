/**
 * @author francis
 * Esta clase tiene los métodos de utilidad para las evaluaciones
 */


package evaluacion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import ninio.UtilidadesNinios;

import actividad.UtilidadesActividades;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;
import cajon_desastre.UtilidadesVarias;

import com.example.debajodeunboton.R;

public final class UtilidadesEvaluacion 
{
	/**
	 * Obtiene y muestra en un spinner las fechas de las realizaciones de una actividad por un niño
	 * @param actividad Contexto
	 * @param ninioseleccionado Niño que realiza la actividad
	 * @param actividadseleccionada Actividad que se realiza
	 * @param spinner Spinner donde se mostrará la información
	 * @param orden Indica si los elementos se mostraran en orden descendente o ascendente
	 * @return Devuelve la primera fecha del fichero
	 */
	public static String mostrarFechasEvaluaciones(Activity actividad, String ninioseleccionado, String actividadseleccionada, Spinner spinner, boolean orden)
	{
		String nombrefichero = UtilidadesVarias.obtenerStringXML(actividad, R.string.configuracion_evaluacion);
		nombrefichero += ninioseleccionado;
		nombrefichero += actividadseleccionada;
		ArrayList<String> fechas = new ArrayList<String>();
		SharedPreferences prefs = actividad.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		
		int totalfechas = prefs.getInt("TOTALEVALUACIONES", 0);
		if( totalfechas > 0 )
		{
			for(int i = 0; i < totalfechas; i++)
				fechas.add(prefs.getString("FECHA" + String.valueOf(i), UtilidadesVarias.obtenerStringXML(actividad, R.string.no_fechas)));
		}
		else
		{
			fechas.add(UtilidadesVarias.obtenerStringXML(actividad, R.string.no_fechas));
		}
		
		if(!orden)
			Collections.reverse(fechas);
		
		UtilidadesVarias.rellenarSpinner(actividad, spinner, fechas);
		
		return fechas.get(0);
	}
	
	/**
	 * Borra las evaluaciones de una actividad que ha sido eliminada
	 * @param actividad Contexto
	 * @param nombreactividad Nombre de la actividad para borrar sus evaluaciones
	 */
	public static void eliminarEvaluacionesActividad(Activity actividad, String nombreactividad)
	{
		ArrayList<String> ninios = new ArrayList<String>();
		ninios = UtilidadesNinios.obtenerNombresNinios(actividad);
		
		for(int i = 0; i < ninios.size(); i++)
		{
			String ruta = UtilidadesVarias.obtenerStringXML(actividad, R.string.configuracion_evaluacion);
			ruta += ninios.get(i);
			ruta += nombreactividad;
			ruta += ".xml";
			File f = new File(ruta);
			
			if( f != null )
				f.delete();
		}
	}
	
	/**
	 * Borra las evaluaciones de un niño que ha sido eliminado
	 * @param actividad Contexto
	 * @param nombreninio Nombre del niño para borrar sus evaluaciones
	 */
	public static void eliminarEvaluacionesNinio(Activity actividad, String nombreninio)
	{
		ArrayList<String> actividades = new ArrayList<String>();
		actividades = UtilidadesActividades.obtenerNombresActividades(actividad);
		
		for(int i = 0; i < actividades.size(); i++)
		{
			String ruta = UtilidadesVarias.obtenerStringXML(actividad, R.string.configuracion_evaluacion);
			ruta += nombreninio;
			ruta += actividades.get(i);
			ruta += ".xml";
			File f = new File(ruta);
			
			if( f != null )
				f.delete();
		}
	}
}


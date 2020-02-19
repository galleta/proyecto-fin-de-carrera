/**
 * Esta clase guardará los resultados de un niño en una actividad con una determinada
 * fecha, es decir, se guardará la evaluación del niño
 * @author francis
 */

package evaluacion;

import java.util.ArrayList;

import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class EvaluacionNinioActividad 
{
	/*
	 * Atributos de la clase
	 */
	private Context contexto;
	private String fecha;
	private String nombreactividad;
	private String nombreninio;
	private int aciertos, fallos;
	private int horastotal, minutostotal, segundostotal;
	private String comentario;
	private int rating;
	
	/*
	 * Métodos de la clase
	 */
	
	/**
	 * Constructor por defecto
	 * @param contexto Contexto
	 */
	public EvaluacionNinioActividad(Context contexto)
	{
		this.contexto = contexto;
	}
	
	/**
	 * Constructor con parámetros
	 * @param contexto Contexto
	 * @param fecha Fecha de realización de la actividad
	 * @param nombreactividad Nombre de la actividad
	 * @param nombreninio Nombre del niño
	 * @param aciertos Número de aciertos
	 * @param fallos Número de fallos
	 * @param horastotal Horas jugadas
	 * @param minutostotal Minutos jugados
	 * @param segundostotal Segundos jugados
	 */
	public EvaluacionNinioActividad(Context contexto, String fecha, String nombreactividad, String nombreninio,
			int aciertos, int fallos,
			int horastotal, int minutostotal, int segundostotal)
	{
		this.contexto = contexto;
		this.setNombreActividad(nombreactividad);
		this.setNombreNinio(nombreninio);
		this.setAciertos(aciertos);
		this.setFallos(fallos);
		this.setFecha(fecha);
		this.setHorasTotal(horastotal);
		this.setMinutosTotal(minutostotal);
		this.setSegundosTotal(segundostotal);
		comentario = "";
		rating = 0;
	}
	
	/**
	 * Completa el nombre del atributo según la fecha
	 * @param fech Fecha
	 * @param nombreatributo Nombre del atributo
	 * @return Nombre del atributo para guardarlo en la configuración con la fecha
	 * 	Si el atributo es horasfila01 y la fecha es 2014-03-28T20:03:07 el resultado será:
	 * 		horasfila01_2014-03-28T20:03:07
	 */
	private String completarAtributoConFecha(String fech, String nombreatributo)
	{
		String nombre = nombreatributo;
		nombre += "_";
		nombre += fech;
		
		return nombre;
	}
	
	/**
	 * Almacena los datos de la evaluación del niño con una actividad en una determinada fecha
	 */
	public void almacenar()
	{
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += getNombreNinio();
		nombrefichero += getNombreActividad();
		
		/*
		 * Para almacenar datos en ficheros xml utilizamos SharedPreferences, los modos
		 * para almacenar son los siguientes:
		 * 	1.- MODE_PRIVATE. Sólo nuestra aplicación tiene acceso a estas preferencias.
		 * 	2.- MODE_WORLD_READABLE. Todas las aplicaciones pueden leer estas preferencias, pero sólo la nuestra puede modificarlas.
		 * 	3.- MODE_WORLD_WRITABLE. Todas las aplicaciones pueden leer y modificar estas preferencias.
		 */
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		// Introduzco la fecha nueva de la evaluación
		editor.putInt("TOTALEVALUACIONES", prefs.getInt("TOTALEVALUACIONES", 0) + 1);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);
		editor.putString("FECHA" + String.valueOf(totalevaluaciones), fecha);
		
		// Introduzco los datos debidamente formateados
		editor.putInt(completarAtributoConFecha(this.getFecha(), "aciertos"), this.getAciertos());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "fallos"), this.getFallos());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "horastotal"), this.getHorasTotal());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "minutostotal"), this.getMinutosTotal());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "segundostotal"), this.getSegundosTotal());
		editor.putString(completarAtributoConFecha(this.getFecha(), "comentario"), this.getComentario());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "rating"), this.getRating());
		
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
	 * Obtiene la evaluación de un niño con una actividad en una determinada fecha
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @param fecha Fecha a consultar
	 */
	public void obtener(String nombreninio, String nombreactividad, String fecha)
	{
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		
		this.setAciertos(prefs.getInt(completarAtributoConFecha(fecha, "aciertos"), 0));
		this.setFallos(prefs.getInt(completarAtributoConFecha(fecha, "fallos"), 0));
		this.setHorasTotal(prefs.getInt(completarAtributoConFecha(fecha, "horastotal"), 0));
		this.setMinutosTotal(prefs.getInt(completarAtributoConFecha(fecha, "minutostotal"), 0));
		this.setSegundosTotal(prefs.getInt(completarAtributoConFecha(fecha, "segundostotal"), 0));
		this.setComentario(prefs.getString(completarAtributoConFecha(fecha, "comentario"), ""));
		this.setRating(prefs.getInt(completarAtributoConFecha(fecha, "rating"), 0));
	}
	
	/**
	 * Devuelve la fecha de la ejecución de la aplicación
	 * @return Fecha de cuando se ejecutó la aplicación
	 */
	public String getFecha() 
	{
		return fecha;
	}
	
	/**
	 * Modifica la fecha de realización de la aplicación
	 * @param fecha Nueva fecha de realización
	 */
	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}
	
	/**
	 * Devuelve el nombre de la actividad
	 * @return Nombre de la actividad que se jugó
	 */
	public String getNombreActividad() 
	{
		return nombreactividad;
	}
	
	/**
	 * Modifica el nombre de la actividad que se jugó
	 * @param nombreactividad Nueva actividad
	 */
	public void setNombreActividad(String nombreactividad) 
	{
		this.nombreactividad = nombreactividad;
	}
	
	/**
	 * Nombre del niño que jugó
	 * @return Nombre del niño que jugó
	 */
	public String getNombreNinio() 
	{
		return nombreninio;
	}
	
	/**
	 * Modifica el nombre del niño que jugó
	 * @param nombreninio Nuevo nombre del niño
	 */
	public void setNombreNinio(String nombreninio) 
	{
		this.nombreninio = nombreninio;
	}
	
	/**
	 * Aciertos del niño en la actividad
	 * @return Aciertos que tuvo el niño en la actividad
	 */
	public int getAciertos() 
	{
		return aciertos;
	}
	
	/**
	 * Modifica el número de aciertos que tuvo el niño en la actividad
	 * @param horasfila01 Nuevo número de aciertos
	 */
	private void setAciertos(int aciertos) 
	{
		this.aciertos = aciertos;
	}
	
	/**
	 * Fallos del niño en la actividad
	 * @return Fallos que tuvo el niño en la actividad
	 */
	public int getFallos() 
	{
		return fallos;
	}
	
	/**
	 * Modifica el número de fallos que tuvo el niño en la actividad
	 * @param horasfila01 Nuevo número de fallos
	 */
	private void setFallos(int fallos) 
	{
		this.fallos = fallos;
	}
	
	/**
	 * Horas que se jugó en total
	 * @return Horas que se jugó en total
	 */
	public int getHorasTotal() 
	{
		return horastotal;
	}
	
	/**
	 * Modifica el número de horas que se ha jugado en total
	 * @param horasTotal Nuevo número de horas
	 */
	private void setHorasTotal(int horastotal) 
	{
		this.horastotal = horastotal;
	}
	
	/**
	 * Minutos que se jugó en total
	 * @return Minutos que se jugó en total
	 */
	public int getMinutosTotal() 
	{
		return minutostotal;
	}
	
	/**
	 * Modifica el número de minutos que se ha jugado en total
	 * @param minutosTotal Nuevo número de minutos
	 */
	private void setMinutosTotal(int minutostotal) 
	{
		this.minutostotal = minutostotal;
	}
	
	/**
	 * Segundos que se jugó en total
	 * @return Segundos que se jugó en total
	 */
	public int getSegundosTotal() 
	{
		return segundostotal;
	}
	
	/**
	 * Modifica el número de segundos que se ha jugado en total
	 * @param minutosTotal Nuevo número de segundos
	 */
	public void setSegundosTotal(int segundostotal) 
	{
		this.segundostotal = segundostotal;
	}
	
	/**
	 * Obtiene todos los aciertos, fallos y tiempos de juego que ha obtenido el niño indicado en la actividad indicada
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @return Lista con todos los aciertos, todos los fallos y los tiempos de juego que ha ido obteniendo el niño en la actividad indicada
	 */
	public ArrayList<ArrayList<Integer>> obtenerTodosAciertosFallosTiemposEjecucion(String nombreninio, String nombreactividad)
	{
		ArrayList<ArrayList<Integer>> devolver = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> aciertos = new ArrayList<Integer>();
		ArrayList<Integer> fallos = new ArrayList<Integer>();
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);
	
		for(int i = 0; i < totalevaluaciones; i++)
		{
			String fechastring = "FECHA" + String.valueOf(i);
			String fecha = prefs.getString(fechastring, "");
			
			aciertos.add(prefs.getInt(completarAtributoConFecha(fecha, "aciertos"), 0));
			fallos.add(prefs.getInt(completarAtributoConFecha(fecha, "fallos"), 0));
		}
	
		devolver.add(aciertos);
		devolver.add(fallos);
		return devolver;
	}
	
	/**
	 * Pasa un tiempo a minutos
	 * @param horas Horas
	 * @param minutos Minutos
	 * @param segundos Segundos
	 * @return Convierte el tiempo del formato hh:mm:ss a minutos
	 */
	@SuppressLint("DefaultLocale")
	private double formatearTiempoAMinutos(int horas, int minutos, int segundos)
	{
		double tiempo = (1.0*horas*60) + (1.0*minutos) + (segundos/60.0);
		return Math.round(tiempo*100.0)/100.0;
	}
	
	/**
	 * Devuelve todos los tiempos de ejecución del niño en la actividad
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @return Array con todos los tiempos de ejecución de la actividad por parte del niño
	 */
	public ArrayList<Double> obtenerTodosTiemposEjecucion(String nombreninio, String nombreactividad)
	{
		ArrayList<Double> tiempos = new ArrayList<Double>();
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);
	
		for(int i = 0; i < totalevaluaciones; i++)
		{
			String fechastring = "FECHA" + String.valueOf(i);
			String fecha = prefs.getString(fechastring, "");
			
			int h = prefs.getInt(completarAtributoConFecha(fecha, "horastotal"), 0);
			int m = prefs.getInt(completarAtributoConFecha(fecha, "minutostotal"), 0);
			int s = prefs.getInt(completarAtributoConFecha(fecha, "segundostotal"), 0);
			tiempos.add(formatearTiempoAMinutos(h, m, s));
		}
	
		return tiempos;
	}
	
	/**
	 * Obtiene todas las fechas en las que el niño ha jugado a la actividad
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @return Array con todas las fechas en las que el niño ha jugado a la actividad
	 */
	public ArrayList<String> obtenerFechas(String nombreninio, String nombreactividad)
	{
		ArrayList<String> fechas = new ArrayList<String>();
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);
	
		for(int i = 0; i < totalevaluaciones; i++)
		{
			String fechastring = "FECHA" + String.valueOf(i);
			String fecha = prefs.getString(fechastring, "");
			
			fechas.add(fecha);
		}
		
		return fechas;
	}
	
	/**
	 * Modifica el comentario de la evaluación del niño con la actividad
	 * @param nuevocomentario Nuevo comentario
	 */
	public void setComentario(String nuevocomentario)
	{
		comentario = nuevocomentario;
	}
	
	/**
	 * Devuelve el comentario que hizo el tutor del niño con la actividad
	 * @return Comentario
	 */
	public String getComentario()
	{
		return comentario;
	}
	
	/**
	 * Modifica la nota del niño con la actividad
	 * @param nuevorating Nueva nota
	 */
	public void setRating(int nuevorating)
	{
		rating = nuevorating;
	}
	
	/**
	 * Devuelve la nota que puso el tutor al niño en la actividad
	 * @return Nota del niño en la actividad
	 */
	public int getRating()
	{
		return rating;
	}
	
}

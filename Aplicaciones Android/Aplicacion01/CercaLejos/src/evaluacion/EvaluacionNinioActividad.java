/**
 * Esta clase guardará los resultados de un niño en una actividad con una determinada
 * fecha, es decir, se guardará la evaluación del niño
 * @author francis
 */

package evaluacion;

import java.util.ArrayList;


import cajon_desastre.UtilidadesVarias;

import com.example.cerca_lejos.R;

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
	private int horasfila01, minutosfila01, segundosfila01;
	private int horasfila02, minutosfila02, segundosfila02;
	private int horasfila03, minutosfila03, segundosfila03;
	private int horastotal, minutostotal, segundostotal;
	private int desviosfila01, desviosfila02, desviosfila03;
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
	 * @param horasfila01 Horas empleadas en la fila 01
	 * @param minutosfila01 Minutos empleados en la fila 01
	 * @param segundosfila01 Segundos empleados en la fila 01
	 * @param horasfila02 Horas empleadas en la fila 02
	 * @param minutosfila02 Minutos empleados en la fila 02
	 * @param segundosfila02 Segundos empleados en la fila 02
	 * @param horasfila03 Horas empleadas en la fila 03
	 * @param minutosfila03 Minutos empleados en la fila 03
	 * @param segundosfila03 Segundos empleados en la fila 03
	 * @param horastotal Horas totales de ejecución de la actividad
	 * @param minutostotal Minutos totales de ejecución de la actividad
	 * @param segundostotal Segundos totales de ejecución de la actividad
	 * @param desviosfila01 Desvíos de la fila 01
	 * @param desviosfila02 Desvíos de la fila 02
	 * @param desviosfila03 Desvíos de la fila 03
	 */
	public EvaluacionNinioActividad(Context contexto, String fecha, String nombreactividad, String nombreninio,
			int horasfila01, int minutosfila01, int segundosfila01,
			int horasfila02, int minutosfila02, int segundosfila02,
			int horasfila03, int minutosfila03, int segundosfila03,
			int horastotal, int minutostotal, int segundostotal,
			int desviosfila01, int desviosfila02, int desviosfila03)
	{
		this.contexto = contexto;
		this.setNombreActividad(nombreactividad);
		this.setNombreNinio(nombreninio);
		this.setDesviosFila01(desviosfila01);
		this.setDesviosFila02(desviosfila02);
		this.setDesviosFila03(desviosfila03);
		this.setFecha(fecha);
		this.setHorasFila01(horasfila01);
		this.setHorasFila02(horasfila02);
		this.setHorasFila03(horasfila03);
		this.setHorasTotal(horastotal);
		this.setMinutosFila01(minutosfila01);
		this.setMinutosFila02(minutosfila02);
		this.setMinutosFila03(minutosfila03);
		this.setMinutosTotal(minutostotal);
		this.setSegundosFila01(segundosfila01);
		this.setSegundosFila02(segundosfila02);
		this.setSegundosFila03(segundosfila03);
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
		editor.putInt(completarAtributoConFecha(this.getFecha(), "horasfila01"), this.getHorasFila01());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "horasfila02"), this.getHorasFila02());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "horasfila03"), this.getHorasFila03());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "minutosfila01"), this.getMinutosFila01());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "minutosfila02"), this.getMinutosFila02());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "minutosfila03"), this.getMinutosFila03());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "segundosfila01"), this.getSegundosFila01());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "segundosfila02"), this.getSegundosFila02());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "segundosfila03"), this.getSegundosFila03());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "horastotal"), this.getHorasTotal());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "minutostotal"), this.getMinutosTotal());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "segundostotal"), this.getSegundosTotal());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "desviosfila01"), this.getDesviosFila01());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "desviosfila02"), this.getDesviosFila02());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "desviosfila03"), this.getDesviosFila03());
		editor.putString(completarAtributoConFecha(this.getFecha(), "comentario"), this.getComentario());
		editor.putInt(completarAtributoConFecha(this.getFecha(), "rating"), this.getRating());
		
		editor.commit();
		
		/*
		 * Una vez llamado commit() para almacenar se guardará en la ruta:
		 * 
		 * 	/data/data/paquete.java/shared_prefs/nombre_coleccion.xml
		 * 
		 * en este caso: /data/data/com.example.cerca_lejos/shared_prefs/nombre_coleccion.xml
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
		
		this.setHorasFila01(prefs.getInt(completarAtributoConFecha(fecha, "horasfila01"), 0));
		this.setHorasFila02(prefs.getInt(completarAtributoConFecha(fecha, "horasfila02"), 0));
		this.setHorasFila03(prefs.getInt(completarAtributoConFecha(fecha, "horasfila03"), 0));
		this.setMinutosFila01(prefs.getInt(completarAtributoConFecha(fecha, "minutosfila01"), 0));
		this.setMinutosFila02(prefs.getInt(completarAtributoConFecha(fecha, "minutosfila02"), 0));
		this.setMinutosFila03(prefs.getInt(completarAtributoConFecha(fecha, "minutosfila03"), 0));
		this.setSegundosFila01(prefs.getInt(completarAtributoConFecha(fecha, "segundosfila01"), 0));
		this.setSegundosFila02(prefs.getInt(completarAtributoConFecha(fecha, "segundosfila02"), 0));
		this.setSegundosFila03(prefs.getInt(completarAtributoConFecha(fecha, "segundosfila03"), 0));
		this.setHorasTotal(prefs.getInt(completarAtributoConFecha(fecha, "horastotal"), 0));
		this.setMinutosTotal(prefs.getInt(completarAtributoConFecha(fecha, "minutostotal"), 0));
		this.setSegundosTotal(prefs.getInt(completarAtributoConFecha(fecha, "segundostotal"), 0));
		this.setDesviosFila01(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila01"), 0));
		this.setDesviosFila02(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila02"), 0));
		this.setDesviosFila03(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila03"), 0));
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
	 * Horas que se jugó en la fila 01
	 * @return Horas que se jugó en la fila 01
	 */
	public int getHorasFila01() 
	{
		return horasfila01;
	}
	
	/**
	 * Modifica el número de horas que se ha jugado en la fila 01
	 * @param horasfila01 Nuevo número de horas
	 */
	private void setHorasFila01(int horasfila01) 
	{
		this.horasfila01 = horasfila01;
	}
	
	/**
	 * Minutos que se jugó en la fila 01
	 * @return Minutos que se jugó en la fila 01
	 */
	public int getMinutosFila01() 
	{
		return minutosfila01;
	}
	
	/**
	 * Modifica el número de minutos que se ha jugado en la fila 01
	 * @param minutosfila01 Nuevo número de minutos
	 */
	private void setMinutosFila01(int minutosfila01) 
	{
		this.minutosfila01 = minutosfila01;
	}
	
	/**
	 * Segundos que se jugó en la fila 01
	 * @return Segundos que se jugó en la fila 01
	 */
	public int getSegundosFila01() 
	{
		return segundosfila01;
	}
	
	/**
	 * Modifica el número de segundos que se ha jugado en la fila 01
	 * @param minutosfila01 Nuevo número de segundos
	 */
	public void setSegundosFila01(int segundosfila01) 
	{
		this.segundosfila01 = segundosfila01;
	}
	
	/**
	 * Horas que se jugó en la fila 02
	 * @return Horas que se jugó en la fila 02
	 */
	public int getHorasFila02() 
	{
		return horasfila02;
	}
	
	/**
	 * Modifica el número de horas que se ha jugado en la fila 02
	 * @param horasfila02 Nuevo número de horas
	 */
	private void setHorasFila02(int horasfila02) 
	{
		this.horasfila02 = horasfila02;
	}
	
	/**
	 * Minutos que se jugó en la fila 02
	 * @return Minutos que se jugó en la fila 02
	 */
	public int getMinutosFila02() 
	{
		return minutosfila02;
	}
	
	/**
	 * Modifica el número de minutos que se ha jugado en la fila 02
	 * @param minutosfila02 Nuevo número de minutos
	 */
	private void setMinutosFila02(int minutosfila02) 
	{
		this.minutosfila02 = minutosfila02;
	}
	
	/**
	 * Segundos que se jugó en la fila 02
	 * @return Segundos que se jugó en la fila 02
	 */
	public int getSegundosFila02() 
	{
		return segundosfila02;
	}
	
	/**
	 * Modifica el número de segundos que se ha jugado en la fila 02
	 * @param minutosfila02 Nuevo número de segundos
	 */
	public void setSegundosFila02(int segundosfila02) 
	{
		this.segundosfila02 = segundosfila02;
	}
	
	/**
	 * Horas que se jugó en la fila 03
	 * @return Horas que se jugó en la fila 03
	 */
	public int getHorasFila03() 
	{
		return horasfila03;
	}
	
	/**
	 * Modifica el número de horas que se ha jugado en la fila 03
	 * @param horasfila03 Nuevo número de horas
	 */
	private void setHorasFila03(int horasfila03) 
	{
		this.horasfila03 = horasfila03;
	}
	
	/**
	 * Minutos que se jugó en la fila 03
	 * @return Minutos que se jugó en la fila 03
	 */
	public int getMinutosFila03() 
	{
		return minutosfila03;
	}
	
	/**
	 * Modifica el número de minutos que se ha jugado en la fila 03
	 * @param minutosfila03 Nuevo número de minutos
	 */
	private void setMinutosFila03(int minutosfila03) 
	{
		this.minutosfila03 = minutosfila03;
	}
	
	/**
	 * Segundos que se jugó en la fila 03
	 * @return Segundos que se jugó en la fila 03
	 */
	public int getSegundosFila03() 
	{
		return segundosfila03;
	}
	
	/**
	 * Modifica el número de segundos que se ha jugado en la fila 03
	 * @param minutosfila03 Nuevo número de segundos
	 */
	public void setSegundosFila03(int segundosfila03) 
	{
		this.segundosfila03 = segundosfila03;
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
	 * Devuelve el número de desvíos en la fila 01
	 * @return Desviós de la fila 01
	 */
	public int getDesviosFila01() 
	{
		return desviosfila01;
	}

	/**
	 * Modifica el número de desvíos de la fila 01
	 * @param desviosfila01 Nuevo número de desvío de la fila 01
	 */
	private void setDesviosFila01(int desviosfila01) 
	{
		this.desviosfila01 = desviosfila01;
	}
	
	/**
	 * Devuelve el número de desvíos en la fila 02
	 * @return Desviós de la fila 02
	 */
	public int getDesviosFila02() 
	{
		return desviosfila02;
	}

	/**
	 * Modifica el número de desvíos de la fila 02
	 * @param desviosfila02 Nuevo número de desvío de la fila 02
	 */
	private void setDesviosFila02(int desviosfila02) 
	{
		this.desviosfila02 = desviosfila02;
	}
	
	/**
	 * Devuelve el número de desvíos en la fila 03
	 * @return Desviós de la fila 03
	 */
	public int getDesviosFila03() 
	{
		return desviosfila03;
	}

	/**
	 * Modifica el número de desvíos de la fila 03
	 * @param desviosfila03 Nuevo número de desvío de la fila 03
	 */
	private void setDesviosFila03(int desviosfila03) 
	{
		this.desviosfila03 = desviosfila03;
	}
	
	/**
	 * Obtiene todos los devíos de todas las filas de todas las ejecuciones de la actividad por el niño
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @return Array con todos los devíos de la actividad en todas sus ejecuciones
	 */
	public ArrayList<ArrayList<Integer>> obtenerTodosDesvios(String nombreninio, String nombreactividad)
	{
		ArrayList<ArrayList<Integer>> devolver = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> desvios01 = new ArrayList<Integer>();
		ArrayList<Integer> desvios02 = new ArrayList<Integer>();
		ArrayList<Integer> desvios03 = new ArrayList<Integer>();
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);;
	
		for(int i = 0; i < totalevaluaciones; i++)
		{
			String fechastring = "FECHA" + String.valueOf(i);
			String fecha = prefs.getString(fechastring, "");
			
			desvios01.add(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila01"), 0));
			desvios02.add(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila02"), 0));
			desvios03.add(prefs.getInt(completarAtributoConFecha(fecha, "desviosfila03"), 0));
		}
	
		devolver.add(desvios01);
		devolver.add(desvios02);
		devolver.add(desvios03);
		return devolver;
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
	 * Pasa un tiempo a minutos
	 * @param horas Horas
	 * @param minutos Minutos
	 * @param segundos Segundos
	 * @return Convierte el tiempo del formato hh:mm:ss a minutos
	 */
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
	public ArrayList<Double> obtenerTodosTiemposEjecucionTotales(String nombreninio, String nombreactividad)
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
	 * Obtiene todos los tiempos que el niño ha ejecutado cada fila en la actividad
	 * @param nombreninio Nombre del niño
	 * @param nombreactividad Nombre de la actividad
	 * @return Arrays de los tiempos de ejecución de las filas en todas las ejecuciones de la actividad por parte del niño
	 */
	public ArrayList<ArrayList<Double>> obtenerTodosTiemposFilas(String nombreninio, String nombreactividad)
	{
		ArrayList<ArrayList<Double>> devolver = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> tiemposfila01 = new ArrayList<Double>();
		ArrayList<Double> tiemposfila02 = new ArrayList<Double>();
		ArrayList<Double> tiemposfila03 = new ArrayList<Double>();
		String nombrefichero = UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_evaluacion);
		nombrefichero += nombreninio;
		nombrefichero += nombreactividad;
		
		SharedPreferences prefs = contexto.getSharedPreferences(nombrefichero, Context.MODE_PRIVATE);
		int totalevaluaciones = prefs.getInt("TOTALEVALUACIONES", 0);
	
		for(int i = 0; i < totalevaluaciones; i++)
		{
			String fechastring = "FECHA" + String.valueOf(i);
			String fecha = prefs.getString(fechastring, "");
			
			int h = prefs.getInt(completarAtributoConFecha(fecha, "horasfila01"), 0);
			int m = prefs.getInt(completarAtributoConFecha(fecha, "minutosfila01"), 0);
			int s = prefs.getInt(completarAtributoConFecha(fecha, "segundosfila01"), 0);
			tiemposfila01.add(formatearTiempoAMinutos(h, m, s));
			h = prefs.getInt(completarAtributoConFecha(fecha, "horasfila02"), 0);
			m = prefs.getInt(completarAtributoConFecha(fecha, "minutosfila02"), 0);
			s = prefs.getInt(completarAtributoConFecha(fecha, "segundosfila02"), 0);
			tiemposfila02.add(formatearTiempoAMinutos(h, m, s));
			h = prefs.getInt(completarAtributoConFecha(fecha, "horasfila03"), 0);
			m = prefs.getInt(completarAtributoConFecha(fecha, "minutosfila03"), 0);
			s = prefs.getInt(completarAtributoConFecha(fecha, "segundosfila03"), 0);
			tiemposfila03.add(formatearTiempoAMinutos(h, m, s));
		}
	
		devolver.add(tiemposfila01);
		devolver.add(tiemposfila02);
		devolver.add(tiemposfila03);
		return devolver;
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

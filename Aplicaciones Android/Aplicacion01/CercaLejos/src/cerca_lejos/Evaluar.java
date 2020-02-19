/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor calificar a un niño
 * Características:
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package cerca_lejos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ninio.UtilidadesNinios;

import cajon_desastre.DatosPrograma;
import cajon_desastre.UtilidadesVarias;

import com.androidplot.xy.XYPlot;
import com.example.cerca_lejos.R;

import evaluacion.EvaluacionNinioActividad;
import evaluacion.UtilidadesEvaluacion;
import grafica.UtilidadesGraficas;

import actividad.UtilidadesActividades;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressWarnings("unused")
public class Evaluar extends Activity 
{
	private DatosPrograma datos;
	private Activity actividadactual = this;
	private EditText edTiempoF01, edTiempoF02, edTiempoF03, edDesvios01, edDesvios02, edDesvios03, edTiempoRealizacion;
	private String ninioseleccionado, actividadseleccionada, fechaseleccionada;
	private Spinner spNinioEvaluacion, spActividadEvalucion, spFechaEvaluacion, spInicio, spHasta;
	private XYPlot layoutmostrargrafico;
	private RatingBar rbPuntuacionMostrar;
	private TextView tComentario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_calificar.xml.
		 */
		setContentView(R.layout.activity_evaluar);
		
		// Obtengo el bundle de los datos de las activitys
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
		
		// Obtengo los recursos de la actividad
		final Button bCancelarCalif = (Button)findViewById(R.id.bCancelarCalif);
		spNinioEvaluacion = (Spinner)findViewById(R.id.spNinioEvaluacion);
		spActividadEvalucion = (Spinner)findViewById(R.id.spActividadEvalucion);
		spFechaEvaluacion = (Spinner)findViewById(R.id.spFechaEvaluacion);
		spInicio = (Spinner)findViewById(R.id.spInicio);
		spHasta = (Spinner)findViewById(R.id.spHasta);
		layoutmostrargrafico = (XYPlot) findViewById(R.id.layoutmostrargrafico);
		rbPuntuacionMostrar = (RatingBar)findViewById(R.id.rbPuntuacionMostrar);
		tComentario = (TextView) findViewById(R.id.tComentarioMostrar);
		
		ninioseleccionado = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNinioEvaluacion);
		actividadseleccionada = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spActividadEvalucion);
		fechaseleccionada = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spFechaEvaluacion, Boolean.TRUE);
		String fechaseleccionada2 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spInicio, Boolean.TRUE);
		String fechaseleccionada3 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spHasta, Boolean.FALSE);
		
		edTiempoF01 = (EditText)findViewById(R.id.edTiempoF01);
		edTiempoF01.setEnabled(Boolean.FALSE);
		edTiempoF02 = (EditText)findViewById(R.id.edTiempoF02);
		edTiempoF02.setEnabled(Boolean.FALSE);
		edTiempoF03 = (EditText)findViewById(R.id.edTiempo03);
		edTiempoF03.setEnabled(Boolean.FALSE);
		edDesvios01 = (EditText)findViewById(R.id.edDesvios01);
		edDesvios01.setEnabled(Boolean.FALSE);
		edDesvios02 = (EditText)findViewById(R.id.edDesvios02);
		edDesvios02.setEnabled(Boolean.FALSE);
		edDesvios03 = (EditText)findViewById(R.id.edDesvios03);
		edDesvios03.setEnabled(Boolean.FALSE);
		edTiempoRealizacion = (EditText)findViewById(R.id.edTiempoRealizacion);
		edTiempoRealizacion.setEnabled(Boolean.FALSE);
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bCancelarCalif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bCancelarCalif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					finish();
				}
				catch (Exception e) 
				{
					Log.i("Calificacion", "Error al pulsar cancelar: " + e.toString());
				}
			}
		});
		
		// Sobrecargo los listener de los spinners
		
		spNinioEvaluacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					ninioseleccionado = spNinioEvaluacion.getSelectedItem().toString();
					fechaseleccionada = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spFechaEvaluacion, Boolean.TRUE);
					String fechaseleccionada2 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spInicio, Boolean.TRUE);
					String fechaseleccionada3 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spHasta, Boolean.FALSE);
					completarDatos();
				}
				catch (Exception e) 
				{
					Log.i("Evaluar", "Error al seleccionar un niño: " + e.toString());
				}
			}
			
			/**
			 * Se llamará cuando no haya ninguna opción seleccionada (esto 
			 * puede ocurrir por ejemplo si el adaptador no tiene datos)
			 */
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		spActividadEvalucion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					actividadseleccionada = spActividadEvalucion.getSelectedItem().toString();
					fechaseleccionada = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spFechaEvaluacion, Boolean.TRUE);
					String fechaseleccionada2 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spInicio, Boolean.TRUE);
					String fechaseleccionada3 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spHasta, Boolean.FALSE);
					completarDatos();
				}
				catch (Exception e) 
				{
					Log.i("Evaluar", "Error al seleccionar una actividad: " + e.toString());
				}
			}
			
			/**
			 * Se llamará cuando no haya ninguna opción seleccionada (esto 
			 * puede ocurrir por ejemplo si el adaptador no tiene datos)
			 */
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		spFechaEvaluacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					fechaseleccionada = spFechaEvaluacion.getSelectedItem().toString();
					completarDatos();
				}
				catch (Exception e) 
				{
					Log.i("Evaluar", "Error al seleccionar una fecha: " + e.toString());
				}
			}
			
			/**
			 * Se llamará cuando no haya ninguna opción seleccionada (esto 
			 * puede ocurrir por ejemplo si el adaptador no tiene datos)
			 */
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		spInicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					if( fechaseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_fechas)) != 0 )
						mostrarGraficoEvolucionDesvios();
				}
				catch (Exception e) 
				{
					Log.i("Evaluar", "Error al seleccionar una fecha de inicio: " + e.toString());
				}
			}
			
			/**
			 * Se llamará cuando no haya ninguna opción seleccionada (esto 
			 * puede ocurrir por ejemplo si el adaptador no tiene datos)
			 */
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		spHasta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					if( fechaseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_fechas)) != 0 )
						mostrarGraficoEvolucionDesvios();
				}
				catch (Exception e) 
				{
					Log.i("Evaluar", "Error al seleccionar una fecha de final: " + e.toString());
				}
			}
			
			/**
			 * Se llamará cuando no haya ninguna opción seleccionada (esto 
			 * puede ocurrir por ejemplo si el adaptador no tiene datos)
			 */
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calificar, menu);
		return true;
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	public void completarDatos()
	{
		/*
		 * Relleno los campos
		 */
		
		if( fechaseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_fechas)) != 0 )
		{
			// Obtengo la configuración del niño, con la actividad y la fecha
			EvaluacionNinioActividad datosevaluacion = new EvaluacionNinioActividad(getBaseContext());
			datosevaluacion.obtener(ninioseleccionado, actividadseleccionada, fechaseleccionada);
			
			// Desvíos
			
			edDesvios01.setText(Integer.toString(datosevaluacion.getDesviosFila01()));
			edDesvios02.setText(Integer.toString(datosevaluacion.getDesviosFila02()));
			edDesvios03.setText(Integer.toString(datosevaluacion.getDesviosFila03()));
			
			// Rating y comentario
			
			rbPuntuacionMostrar.setIsIndicator(Boolean.FALSE);
			rbPuntuacionMostrar.setRating(datosevaluacion.getRating());
			rbPuntuacionMostrar.setIsIndicator(Boolean.TRUE);
			tComentario.setText(datosevaluacion.getComentario());
			
			// Tiempo total jugado
			
			String cadena = "";
			int datotiempo = datosevaluacion.getHorasTotal();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getMinutosTotal();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getSegundosTotal();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			edTiempoRealizacion.setText(cadena);
			
			// Tiempo jugado en la fila 01
			
			cadena = "";
			datotiempo = datosevaluacion.getHorasFila01();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getMinutosFila01();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getSegundosFila01();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			edTiempoF01.setText(cadena);
			
			// Tiempo jugado en la fila 02
			
			cadena = "";
			datotiempo = datosevaluacion.getHorasFila02();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getMinutosFila02();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getSegundosFila02();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			edTiempoF02.setText(cadena);
			
			// Tiempo jugado en la fila 03
			
			cadena = "";
			datotiempo = datosevaluacion.getHorasFila03();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getMinutosFila03();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			cadena += ":";
			datotiempo = datosevaluacion.getSegundosFila03();
			if( datotiempo < 10 )
				cadena += "0";
			cadena += Integer.toString(datotiempo);
			edTiempoF03.setText(cadena);
			
			//mostrarGraficoEvolucionDesvios();
		}
		else
		{
			String mensaje = UtilidadesVarias.obtenerStringXML(actividadactual, R.string.no_datos);
			edDesvios01.setText(mensaje);
			edDesvios02.setText(mensaje);
			edDesvios03.setText(mensaje);
			edTiempoRealizacion.setText(mensaje);
			edTiempoF01.setText(mensaje);
			edTiempoF02.setText(mensaje);
			edTiempoF03.setText(mensaje);
			layoutmostrargrafico.clear();
			layoutmostrargrafico.setTitle(mensaje);
			layoutmostrargrafico.redraw();
			rbPuntuacionMostrar.setNumStars(0);
			tComentario.setText(mensaje);
		}
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Muestra gráficamente la evolución del niño en los desvíos en la actividad seleccionada entre las fechas seleccionadas
	 */
	public void mostrarGraficoEvolucionDesvios()
	{
		EvaluacionNinioActividad aux = new EvaluacionNinioActividad(getBaseContext());
		ArrayList<String> fechas = aux.obtenerFechas(ninioseleccionado, actividadseleccionada);
		int indicefinal = fechas.indexOf(spInicio.getSelectedItem().toString());
		int indiceprincipio = fechas.indexOf(spHasta.getSelectedItem().toString());
		
		if( indiceprincipio <= indicefinal )
		{
			String titulo = "Evolución de " + ninioseleccionado + " en " + actividadseleccionada;
			
			ArrayList<ArrayList<Integer>> totaldesvios = aux.obtenerTodosDesvios(ninioseleccionado, actividadseleccionada);
			ArrayList<Double> tiempostotales = aux.obtenerTodosTiemposEjecucionTotales(ninioseleccionado, actividadseleccionada);
			ArrayList<ArrayList<Double>> tiemposfilas = aux.obtenerTodosTiemposFilas(ninioseleccionado, actividadseleccionada);
			
			ArrayList<Integer> desvios1 = new ArrayList<Integer>();
			ArrayList<Integer> desvios2 = new ArrayList<Integer>();
			ArrayList<Integer> desvios3 = new ArrayList<Integer>();
			ArrayList<Double> tiempos1 = new ArrayList<Double>();
			ArrayList<Double> tiempos2 = new ArrayList<Double>();
			ArrayList<Double> tiempos3 = new ArrayList<Double>();
			ArrayList<Double> tiempostotal = new ArrayList<Double>();
			ArrayList<String> fechas2 = new ArrayList<String>();
			
			// Selecciono los valores entre las fechas elegidas
			
			for(int i = indiceprincipio; i <= indicefinal; i++)
			{
				desvios1.add(totaldesvios.get(0).get(i));
				desvios2.add(totaldesvios.get(1).get(i));
				desvios3.add(totaldesvios.get(2).get(i));
				fechas2.add(fechas.get(i));
				tiempos1.add(tiemposfilas.get(0).get(i));
				tiempos2.add(tiemposfilas.get(1).get(i));
				tiempos3.add(tiemposfilas.get(2).get(i));
				tiempostotal.add(tiempostotales.get(i));
			}
			
			UtilidadesGraficas.mostrarGrafico(titulo, desvios1, desvios2, desvios3,
					tiempos1, tiempos2, tiempos3, tiempostotal, fechas2, layoutmostrargrafico);
		}
		else
		{
			UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: las fechas se han seleccionado erróneamente");
			layoutmostrargrafico.setTitle("");
			layoutmostrargrafico.clear();
			layoutmostrargrafico.redraw();
		}
	}
	
	/**
	 * Resetea los datos de la pantalla de evaluación
	 */
	public void resetearDatos()
	{
		layoutmostrargrafico.setTitle("");
		layoutmostrargrafico.clear();
		layoutmostrargrafico.redraw();
		edDesvios01.setText("0");
		edDesvios02.setText("0");
		edDesvios03.setText("0");
		edTiempoRealizacion.setText("00:00:00");
		edTiempoF01.setText("00:00:00");
		edTiempoF02.setText("00:00:00");
		edTiempoF03.setText("00:00:00");
		rbPuntuacionMostrar.setNumStars(0);
		tComentario.setText("");
	}

}

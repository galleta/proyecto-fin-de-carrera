/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor calificar a un niño
 * Características:
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package hacer_musica;

import java.util.ArrayList;

import ninio.UtilidadesNinios;

import cajon_desastre.DatosPrograma;
import cajon_desastre.UtilidadesVarias;

import com.androidplot.xy.XYPlot;
import com.example.debajodeunboton.R;

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
	/*
	 * Atributos de la clase
	 */
	
	private DatosPrograma datos;
	private Activity actividadactual = this;
	private EditText edAciertosEvaluacion, edFallosEvaluacion, edTiempoRealizacion, edVecesEscuchado;
	private String ninioseleccionado, actividadseleccionada, fechaseleccionada;
	private Spinner spNinioEvaluacion, spActividadEvalucion, spFechaEvaluacion, spInicio, spHasta;
	private XYPlot layoutmostrargrafico;
	private RatingBar rbPuntuacionMostrar;
	private TextView tComentario, tModoEvaluacion, tOrdenes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluar);
		
		// Obtengo el bundle de los datos de las activitys
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
				
		// Obtengo los recursos de la actividad
		final Button bCancelarCalif = (Button)findViewById(R.id.bCancelarCalif);
		rbPuntuacionMostrar = (RatingBar)findViewById(R.id.rbPuntuacionMostrar);
		tComentario = (TextView) findViewById(R.id.tComentarioMostrar);
		tModoEvaluacion = (TextView) findViewById(R.id.tModo02);
		tOrdenes = (TextView) findViewById(R.id.tOrdenes02);
		spNinioEvaluacion = (Spinner)findViewById(R.id.spNinioEvaluacion);
		spActividadEvalucion = (Spinner)findViewById(R.id.spActividadEvalucion);
		spFechaEvaluacion = (Spinner)findViewById(R.id.spFechaEvaluacion);
		spInicio = (Spinner)findViewById(R.id.spInicio);
		spHasta = (Spinner)findViewById(R.id.spHasta);
		layoutmostrargrafico = (XYPlot) findViewById(R.id.layoutmostrargrafico);		
		edAciertosEvaluacion = (EditText)findViewById(R.id.edAciertosEvaluacion);
		edFallosEvaluacion = (EditText)findViewById(R.id.edFallosEvaluacion);
		edTiempoRealizacion = (EditText)findViewById(R.id.edTiempoRealizacion);	
		edVecesEscuchado = (EditText)findViewById(R.id.edVecesEscuchado);	
		
		edAciertosEvaluacion.setEnabled(Boolean.FALSE);
		edFallosEvaluacion.setEnabled(Boolean.FALSE);
		edFallosEvaluacion.setEnabled(Boolean.FALSE);
		edTiempoRealizacion.setEnabled(Boolean.FALSE);
		edVecesEscuchado.setEnabled(Boolean.FALSE);
		
		ninioseleccionado = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNinioEvaluacion);
		actividadseleccionada = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spActividadEvalucion);
		fechaseleccionada = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spFechaEvaluacion, Boolean.TRUE);
		String fechaseleccionada2 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spInicio, Boolean.TRUE);
		String fechaseleccionada3 = UtilidadesEvaluacion.mostrarFechasEvaluaciones(actividadactual, ninioseleccionado, actividadseleccionada, spHasta, Boolean.FALSE);
	
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
						mostrarGraficoEvolucionNinio();
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
						mostrarGraficoEvolucionNinio();
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
			
			// Aciertos / Fallos
			
			edAciertosEvaluacion.setText(Integer.toString(datosevaluacion.getAciertos()));
			edFallosEvaluacion.setText(Integer.toString(datosevaluacion.getFallos()));
			
			// Modo / Veces escuchado / Órdenes
			tModoEvaluacion.setText("Modo: " + datosevaluacion.getModo().toString());
			edVecesEscuchado.setText("" + datosevaluacion.getVecesEscuchado());
			String texto = "Orden: ";
			for(int i = 0; i < datosevaluacion.getOrdenes().size(); i++)
			{
				texto += String.valueOf(datosevaluacion.getOrdenes().get(i));
				if( i < datosevaluacion.getOrdenes().size()-1 )
					texto += ", ";
			}
			tOrdenes.setText(texto);
			
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
			
			//mostrarGraficoEvolucionNinio();
		}
		else
		{
			String mensaje = UtilidadesVarias.obtenerStringXML(actividadactual, R.string.no_datos);
			edAciertosEvaluacion.setText(mensaje);
			edFallosEvaluacion.setText(mensaje);
			edTiempoRealizacion.setText(mensaje);
			layoutmostrargrafico.clear();
			layoutmostrargrafico.setTitle(mensaje);
			layoutmostrargrafico.redraw();
			rbPuntuacionMostrar.setNumStars(0);
			tComentario.setText(mensaje);
			tModoEvaluacion.setText("Modo: " + mensaje);
			tOrdenes.setText("Orden: " + mensaje);
			edVecesEscuchado.setText(mensaje);
		}
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Muestra gráficamente la evolución del niño en los desvíos en la actividad seleccionada
	 */
	public void mostrarGraficoEvolucionNinio()
	{
		EvaluacionNinioActividad aux = new EvaluacionNinioActividad(getBaseContext());
		ArrayList<String> fechas = aux.obtenerFechas(ninioseleccionado, actividadseleccionada);
		int indicefinal = fechas.indexOf(spInicio.getSelectedItem().toString());
		int indiceprincipio = fechas.indexOf(spHasta.getSelectedItem().toString());
		
		if( indiceprincipio <= indicefinal )
		{
			String titulo = "Evolución de " + ninioseleccionado + " en " + actividadseleccionada;
			
			ArrayList<ArrayList<Integer>> totaldatos = aux.obtenerTodosAciertosVEscuchadoFallosEjecucion(ninioseleccionado, actividadseleccionada);		
			ArrayList<Double> tiempos = aux.obtenerTodosTiemposEjecucion(ninioseleccionado, actividadseleccionada);
			
			ArrayList<Integer> aciertos = new ArrayList<Integer>();
			ArrayList<Integer> fallos = new ArrayList<Integer>();
			ArrayList<Integer> vescuchado = new ArrayList<Integer>();
			ArrayList<String> fechas2 = new ArrayList<String>();
			ArrayList<Double> tiempos2 = new ArrayList<Double>();
			
			for(int i = indiceprincipio; i <= indicefinal; i++)
			{
				aciertos.add(totaldatos.get(0).get(i));
				fallos.add(totaldatos.get(1).get(i));
				vescuchado.add(totaldatos.get(2).get(i));
				fechas2.add(fechas.get(i));
				tiempos2.add(tiempos.get(i));
			}
			
			UtilidadesGraficas.mostrarGrafico(titulo, aciertos, fallos, vescuchado, tiempos2, fechas2, layoutmostrargrafico);
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
		edAciertosEvaluacion.setText("0");
		edFallosEvaluacion.setText("0");
		edTiempoRealizacion.setText("00:00:00");
		rbPuntuacionMostrar.setNumStars(0);
		tComentario.setText("");
		tModoEvaluacion.setText("Modo: ");
		//tOrdenes.setText("Orden: ");
		edVecesEscuchado.setText("0");
	}

}

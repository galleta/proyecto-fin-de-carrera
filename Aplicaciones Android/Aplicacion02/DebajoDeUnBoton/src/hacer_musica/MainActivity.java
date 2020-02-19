/**
 * @author francis
 * Clase que representa la actividad principal de la aplicación Debajo de un botón
 * Esta clase es general para las tres aplicaciones
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

import com.example.debajodeunboton.R;

import actividad.UtilidadesActividades;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class MainActivity extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private String nombreninio, nombreactividad, modojuego;
	private Spinner spNombreNinio, spNombreActividad, spElegirModo;
	private Activity actividadactual = this;
	private String basura;
	private DatosPrograma datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
		
		nombreninio = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_ninio);
		nombreactividad = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad);
		
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_main.xml.
		 */
		setContentView(R.layout.activity_main);
		
		/*
		 * Cambio la orientación de la pantalla:
		 * 	1.- SCREEN_ORIENTATION_LANDSCAPE -> horizontal
		 * 	2.-	SCREEN_ORIENTATION_PORTRAIT -> vertical
		 */
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// Obtengo los recursos de la actividad
		final Button botonniniciar = (Button)findViewById(R.id.bIniciar);
		final ImageButton botonmodoprofesor = (ImageButton)findViewById(R.id.bModoProfesor);
		final ImageButton bSalirAplicacion = (ImageButton)findViewById(R.id.bSalirAplicacion);
		spNombreNinio = (Spinner)findViewById(R.id.spNombreNinio);
		spNombreActividad = (Spinner)findViewById(R.id.spNombreActividad);
		spElegirModo = (Spinner)findViewById(R.id.spElegirModo);
		
		ArrayList<String> mods = new ArrayList<String>();
		mods.add("Recordar");
		mods.add("Componer");
		UtilidadesVarias.rellenarSpinner(actividadactual, spElegirModo, mods);
		modojuego = (String) spElegirModo.getItemAtPosition(0);
				
		basura = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNombreNinio);
		basura = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spNombreActividad);
		
		// Sobrecarlo el listener setOnItemSelectedListener para cuando se elija un elemento 
		// nuevo del spinner		
		
		spElegirModo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					modojuego = (String) spElegirModo.getItemAtPosition(position);
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al elegir un elemento del spinner de los modos: " + e.toString());
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
		
		spNombreNinio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String elemento = (String) spNombreNinio.getItemAtPosition(position);
					if( elemento.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_ninios_creados)) == 0 )
					{
						nombreninio = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_ninio);
					}
					else
					{
						nombreninio = elemento;
					}
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al elegir un elemento del spinner de los niños: " + e.toString());
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
		
		spNombreActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String elemento = (String) spNombreActividad.getItemAtPosition(position);
					if( elemento.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividades_creadas)) == 0 )
					{
						nombreactividad = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad);
					}
					else
					{
						nombreactividad = elemento;
					}
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al elegir un elemento del spinner de las actividades: " + e.toString());
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
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		botonniniciar.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón botonniniciar
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(MainActivity.this, JuegoDebajoBoton.class);
					datos.insertarString("NINIO", nombreninio);
					datos.insertarString("ACTIVIDAD", nombreactividad);
					datos.insertarString("MODOJUEGO", modojuego);
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al ir a Juego.class " + e.toString());
				}
			}
		});
		
		botonmodoprofesor.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón botonmodoprofesor
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(MainActivity.this, Configuracion.class);
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al ir a Configuracion.class " + e.toString());
				}
			}
		});
		
		bSalirAplicacion.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSalirAplicacion
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					UtilidadesVarias.cerrarApp(actividadactual);
				}
				catch (Exception e) 
				{
					Log.i("MainActivity", "Error al salir de la aplicación: " + e.toString());
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	/**
	 * Se ejecuta cuando la actividad se saca de la pila, es decir, vuelve
	 */
	protected void onResume() 
	{
	    super.onResume();
	    basura = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNombreNinio);
		basura = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spNombreActividad);
	}
	
}

/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor eliminar una actividad
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package cerca_lejos;

import java.io.File;

import cajon_desastre.UtilidadesVarias;

import com.example.cerca_lejos.R;

import evaluacion.UtilidadesEvaluacion;


import actividad.UtilidadesActividades;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class EliminarActividad extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private String nombreactividad;
	private Spinner spActividadesEliminar;
	private Activity actividadactual = this;
	private boolean BORRADO_CASCADA_EVALUACION_NINIO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_eliminar_actividad.xml.
		 */
		setContentView(R.layout.activity_eliminar_actividad);
								
		// Obtengo los recursos de la actividad
		BORRADO_CASCADA_EVALUACION_NINIO = Boolean.valueOf(UtilidadesVarias.obtenerStringXML(actividadactual, R.string.BORRADO_CASCADA_EVALUACION_NINIO).toString());
		final Button bEliminarActividad = (Button)findViewById(R.id.bEliminarActividadConf);
		final Button bVolverEliminarActividad = (Button)findViewById(R.id.bVolverEliminarActividad);
		spActividadesEliminar = (Spinner)findViewById(R.id.spActividadesEliminar);
		
		nombreactividad = UtilidadesActividades.mostrarConfiguracionesActividades(this, spActividadesEliminar);
		
		// Sobrecarlo el listener setOnItemSelectedListener para cuando se elija un elemento 
		// nuevo del spinner
										
		spActividadesEliminar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String elemento = (String) spActividadesEliminar.getItemAtPosition(position);
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
					Log.i("Eliminar actividad", "Error al elegir un elemento del spinner de los niños: " + e.toString());
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
		
		bEliminarActividad.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEliminarActividad
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					int error = comprobarCorrectitudDatosEliminarActividad();
					if( error == 0 )
					{
						AlertDialog.Builder dialogoconfirmar = new AlertDialog.Builder(actividadactual);
										
						dialogoconfirmar.setTitle("Información");
										
						TextView mensaje = new TextView(actividadactual);
						mensaje.setText("¿Eliminar la actividad " + nombreactividad + "?");
						mensaje.setPadding(10, 10, 10, 10);
						mensaje.setGravity(Gravity.CENTER);
						mensaje.setTextSize(23);
															
						dialogoconfirmar.setView(mensaje);
						dialogoconfirmar.setCancelable(Boolean.FALSE);
									
						// Creo el botón "Si" del diálogo
						dialogoconfirmar.setPositiveButton("Si", new DialogInterface.OnClickListener() 
						{
							@Override
							public void onClick(DialogInterface dialog, int which) 
							{
								try
								{			
									String ruta = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.ruta_ficheros_xml);
									ruta += UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.configuracion_actividad);
									ruta += nombreactividad;
									ruta += ".xml";
									File f = new File(ruta);
											
									if( f != null )
									{
										if( f.delete() )
										{
											UtilidadesVarias.mostrarToastText(actividadactual, "Actividad " + nombreactividad + " eliminada con éxito.");
											
											/*
											 * Borro todos los ficheros de evaluaciones de ese niño
											 */
											if( BORRADO_CASCADA_EVALUACION_NINIO )
												UtilidadesEvaluacion.eliminarEvaluacionesActividad(actividadactual, nombreactividad);
											
											nombreactividad = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spActividadesEliminar);
										}
										else
											UtilidadesVarias.mostrarToastText(actividadactual, "Error al eliminar la actividad " + nombreactividad);
									}
								}
								catch (Exception e) 
								{
									Log.i("Eliminar niño", "Error al pulsar eliminar: " + e.toString());
								}
							}
						});
										
						// Creo el botón "No" del diálogo
						dialogoconfirmar.setNegativeButton("No", new DialogInterface.OnClickListener() 
						{ 
							@Override
							public void onClick(DialogInterface dialog, int which) 
							{
								dialog.cancel();
							}
						});
									
						dialogoconfirmar.show();
					}
					else
					{
						String textoerror = "";
						switch(error)
						{
							case 1:
								textoerror =  "ERROR: No hay actividades registradas";
								break;
						}
						UtilidadesVarias.mostrarToastText(actividadactual, textoerror);
					}
				}
				catch (Exception e) 
				{
					Log.i("Eliminar actividad", "Error al pulsar eliminar: " + e.toString());
				}
			}
		});
				
		bVolverEliminarActividad.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bVolverEliminarActividad
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
					Log.i("Eliminar actividad", "Error al pulsar volver: " + e.toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eliminar_actividad, menu);
		return true;
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Comprueba que los datos son correctos
	 * @return Código de error
	 * 				1.-	No hay actividades registradas
	 */
	private int comprobarCorrectitudDatosEliminarActividad()
	{
		int error = 0;
		
		if( spActividadesEliminar.getSelectedItem().toString().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividades_creadas)) == 0 )
			error = 1;
		
		return error;
	}
}

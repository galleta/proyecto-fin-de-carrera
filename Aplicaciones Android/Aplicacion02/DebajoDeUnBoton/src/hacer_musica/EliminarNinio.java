/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor eliminar un niño
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package hacer_musica;

import java.io.File;

import ninio.UtilidadesNinios;

import cajon_desastre.UtilidadesVarias;

import com.example.debajodeunboton.R;

import evaluacion.UtilidadesEvaluacion;

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

public class EliminarNinio extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	private String nombreninio;
	private Spinner spNiniosEliminar;
	private Activity actividadactual = this;
	private boolean BORRADO_CASCADA_NINIO_EVALUACION;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_eliminar_ninio.xml.
		 */
		setContentView(R.layout.activity_eliminar_ninio);
		
		// Obtengo los recursos de la actividad
		BORRADO_CASCADA_NINIO_EVALUACION = Boolean.valueOf(UtilidadesVarias.obtenerStringXML(actividadactual, R.string.BORRADO_CASCADA_NINIO_EVALUACION).toString());
		final Button bEliminarNinio = (Button)findViewById(R.id.bEliminarActividadConf);
		final Button bVolverEliminar = (Button)findViewById(R.id.bVolverEliminarActividad);
		spNiniosEliminar = (Spinner)findViewById(R.id.spNiniosEliminar);
				
		nombreninio = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNiniosEliminar);
			
		// Sobrecarlo el listener setOnItemSelectedListener para cuando se elija un elemento 
		// nuevo del spinner
		
		spNiniosEliminar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String elemento = (String) spNiniosEliminar.getItemAtPosition(position);
					if( elemento.compareTo(UtilidadesVarias.obtenerStringXML(actividadactual, R.string.no_ninios_creados)) == 0 )
					{
						nombreninio = UtilidadesVarias.obtenerStringXML(actividadactual, R.string.no_ninio);
					}
					else
					{
						nombreninio = elemento;
					}
				}
				catch (Exception e) 
				{
					Log.i("Eliminar niño", "Error al elegir un elemento del spinner de los niños: " + e.toString());
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
		
		bEliminarNinio.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEliminarNinio
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					int error = comprobarCorrectitudDatosEliminarNinio();
					if( error == 0 )
					{
						AlertDialog.Builder dialogoconfirmar = new AlertDialog.Builder(actividadactual);
										
						dialogoconfirmar.setTitle("Información");
										
						TextView mensaje = new TextView(actividadactual);
						mensaje.setText("¿Eliminar a " + nombreninio + "?");
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
									String ruta = UtilidadesVarias.obtenerStringXML(actividadactual, R.string.ruta_ficheros_xml);
									ruta += UtilidadesVarias.obtenerStringXML(actividadactual, R.string.configuracion_ninio);
									ruta += nombreninio;
									ruta += ".xml";
									File f = new File(ruta);
												
									if( f != null )
									{
										if( f.delete() )
										{
											UtilidadesVarias.mostrarToastText(actividadactual, "Niño " + nombreninio + " eliminado con éxito.");
											/*
											 * Borro todos los ficheros de evaluaciones de ese niño
											*/
											if( BORRADO_CASCADA_NINIO_EVALUACION )
												UtilidadesEvaluacion.eliminarEvaluacionesNinio(actividadactual, nombreninio);
													
											nombreninio = UtilidadesNinios.mostrarConfiguracionesNinios(actividadactual, spNiniosEliminar);
										}
										else
											UtilidadesVarias.mostrarToastText(actividadactual, "Error al eliminar a " + nombreninio);
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
								textoerror =  "ERROR: No hay niños registrados";
								break;
						}
						UtilidadesVarias.mostrarToastText(actividadactual, textoerror);
					}
				}
				catch (Exception e) 
				{
					Log.i("Eliminar niño", "Error al pulsar eliminar: " + e.toString());
				}
			}
		});
		
		bVolverEliminar.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bVolverEliminar
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
					Log.i("Eliminar niño", "Error al pulsar volver: " + e.toString());
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eliminar_ninio, menu);
		return true;
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Comprueba que los datos son correctos
	 * @return Código de error
	 * 				1.-	No se ha elegido el nombre de la actividad
	 * 				2.-	Está activa la canción 01 pero no se ha elegido un fichero de audio
	 * 				3.- Está activa la canción 02 pero no se ha elegido un fichero de audio
	 * 				4.- Está activa la canción 03 pero no se ha elegido un fichero de audio
	 * 				5.- Está activo el estímulo negativo pero no se ha elegido un fichero de audio
	 */
	private int comprobarCorrectitudDatosEliminarNinio()
	{
		int error = 0;
		
		if( spNiniosEliminar.getSelectedItem().toString().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_ninios_creados)) == 0 )
			error = 1;
		
		return error;
	}

}

/**
 * @author francis
 * Esta clase nos permitirá modificar una actividad existente para la aplicación de cerca lejos
 */

package cerca_lejos;

import java.io.File;
import java.util.ArrayList;

import sonido.Sonido;

import cajon_desastre.TipoEstimulo;
import cajon_desastre.UtilidadesVarias;

import com.example.cerca_lejos.R;

import actividad.ConfiguracionActividad;
import actividad.UtilidadesActividades;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import ar.com.daidalos.afiledialog.FileChooserDialog;

public class Modificar_actividad extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private String nombreactividad;
	private Activity actividadactual = this;
	private Spinner cbTipoEstimuloModifAct;
	private CheckBox cbActivo01ModifAct, cbActivo02ModifAct, cbActivo03ModifAct;
	private CheckBox cbActivoDescripcionModif;
	private ConfiguracionActividad configuracion;
	private Spinner spNombresActividadesModifAct;
	private boolean grabando01 = Boolean.FALSE, escuchando01 = Boolean.FALSE;
	private boolean grabando02 = Boolean.FALSE, escuchando02 = Boolean.FALSE;
	private boolean grabando03 = Boolean.FALSE, escuchando03 = Boolean.FALSE;
	private boolean grabandoayuda = Boolean.FALSE, escuchandoayuda = Boolean.FALSE;
	private boolean grabandodescripcion = Boolean.FALSE, escuchandodescripcion = Boolean.FALSE;
	private Sonido s1, s2, s3, sayuda, sdescripcion;
	
	/*
	 * Con ficheroelegir indico qué ruta quiero seleccionar:
	 * 	Si vale 1 elijo la ruta de la canción 01
	 * 	Si vale 2 elijo la ruta de la canción 02
	 * 	Si vale 3 elijo la ruta de la canción 03
	 * 	Si vale 4 elijo la ruta de la ayuda
	 *  Si vale 5 elijo la ruta de la descripción
	 */
	private int ficheroelegir;
	private String rutacancion01 = "", rutacancion02 = "", rutacancion03 = "", rutadescripcion = "";
	private boolean activacancion01 = Boolean.TRUE, activacancion02 = Boolean.TRUE, activacancion03 = Boolean.TRUE;
	private boolean activadescripcion = Boolean.TRUE;
	private String rutaayuda = "";
	private TipoEstimulo estimulo = TipoEstimulo.VOLUMEN;
	private ConfiguracionActividad configuracionactividad;
	private final String FILTRO_AUDIO = ".*mp3|.*wav";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_modificar_actividad.xml.
		 */
		setContentView(R.layout.activity_modificar_actividad);
						
		// Obtengo los recursos de la actividad
		final Button bAceptarNuevoPerfilModifAct = (Button)findViewById(R.id.bAceptarNuevoPerfilModifAct);
		final Button bCancelarNuevoPerfilModifAct = (Button)findViewById(R.id.bCancelarNuevoPerfilModifAct);
		spNombresActividadesModifAct = (Spinner)findViewById(R.id.spNombresActividadesModifAct);
		final Button bCancion01ModifAct = (Button)findViewById(R.id.bCancion01ModifAct);
		final Button bCancion02ModifAct = (Button)findViewById(R.id.bCancion02ModifAct);
		final Button bCancion03ModifAct = (Button)findViewById(R.id.bCancion03ModifAct);
		final Button bAyudaModifAct = (Button)findViewById(R.id.bAyudaModifAct);
		final Button bDescripcionModif = (Button)findViewById(R.id.bDescripcionModif);
		cbTipoEstimuloModifAct = (Spinner)findViewById(R.id.cbTipoEstimuloModifAct);
		cbActivo01ModifAct = (CheckBox)findViewById(R.id.cbActivo01ModifAct);
		cbActivo02ModifAct = (CheckBox)findViewById(R.id.cbActivo02ModifAct);
		cbActivo03ModifAct = (CheckBox)findViewById(R.id.cbActivo03ModifAct);
		cbActivoDescripcionModif = (CheckBox)findViewById(R.id.cbActivoDescripcionModif);
		
		final ImageButton ibGrabarModif01 = (ImageButton)findViewById(R.id.ibGrabarModif01);
		final ImageButton ibEscucharModif01 = (ImageButton)findViewById(R.id.ibEscucharModif01);
		final ImageButton ibGrabarModif02 = (ImageButton)findViewById(R.id.ibGrabarModif02);
		final ImageButton ibEscucharModif02 = (ImageButton)findViewById(R.id.ibEscucharModif02);
		final ImageButton ibGrabarModif03 = (ImageButton)findViewById(R.id.ibGrabarModif03);
		final ImageButton ibEscucharModif03 = (ImageButton)findViewById(R.id.ibEscucharModif03);
		final ImageButton ibGrabarModifAyuda = (ImageButton)findViewById(R.id.ibGrabarModifAyuda);
		final ImageButton ibEscucharModifAyuda = (ImageButton)findViewById(R.id.ibEscucharModifAyuda);
		final ImageButton ibGrabarDescripcionModif = (ImageButton)findViewById(R.id.ibGrabarDescripcionModif);
		final ImageButton ibEscucharDescripcionModif = (ImageButton)findViewById(R.id.ibEscucharDescripcionModif);
		
		// Agrego datos a los elementos
		
		// Pongo los estímulos en el spinner
				
		ArrayList<String> estimulos = new ArrayList<String>();
		estimulos.add("Frecuencia");
		estimulos.add("Timbre");
		estimulos.add("Volumen");
		UtilidadesVarias.rellenarSpinner(actividadactual, cbTipoEstimuloModifAct, estimulos);
		
		/*
		 * Obtengo la actividad y la muestro
		 */
		nombreactividad = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spNombresActividadesModifAct);
		mostrarConfiguracionVentana(nombreactividad);
		
		
		// Sobrecarlo el listener setOnItemSelectedListener para cuando se elija un elemento 
		// nuevo del spinner
		
		cbActivoDescripcionModif.setOnClickListener(new CheckBox.OnClickListener()
		{
            @Override
            /**
             * Acción onClick del checkBox cbActivoDescripcionModif
             * @param evento Evento
             */
            public void onClick(View evento) 
            {
            	try
				{
            		activadescripcion = cbActivoDescripcionModif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoDescripcionModif: " + e.toString());
				}
            }
        });
		
		cbTipoEstimuloModifAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String estimuloseleccionado = (String) cbTipoEstimuloModifAct.getItemAtPosition(position);
					
					if( estimuloseleccionado.compareTo("Volumen") == 0 )
						estimulo = TipoEstimulo.VOLUMEN;
					else
						if( estimuloseleccionado.compareTo("Timbre") == 0 )
							estimulo = TipoEstimulo.TIMBRE;
						else
							if( estimuloseleccionado.compareTo("Frecuencia") == 0 )
								estimulo = TipoEstimulo.FRECUENCIA;
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error 02: " + e.toString());
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
		
		spNombresActividadesModifAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					nombreactividad = spNombresActividadesModifAct.getSelectedItem().toString();
					mostrarConfiguracionVentana(nombreactividad);
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error 03: " + e.toString());
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
				
		// Sobrecargo los listeners setOnClickListener para saber cuando se hace click en un checkbox
		
		cbActivo01ModifAct.setOnClickListener(new CheckBox.OnClickListener()
		{
		       @Override
		       /**
		        * Acción onClick del checkBox cbActivo01ModifAct
		        * @param evento Evento
		        */
		     public void onClick(View evento) 
		     {
		      	try
				{
					activacancion01 = cbActivo01ModifAct.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivo01ModifAct: " + e.toString());
				}
		    }
		});
				
		cbActivo02ModifAct.setOnClickListener(new CheckBox.OnClickListener()
		{
		          @Override
		          /**
		           * Acción onClick del checkBox cbActivo02ModifAct
		             * @param evento Evento
		             */
		            public void onClick(View evento) 
		            {
		            	try
						{
		            		activacancion02 = cbActivo02ModifAct.isChecked();
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar cbActivo02ModifAct: " + e.toString());
						}
		            }
		        });
				
				cbActivo03ModifAct.setOnClickListener(new CheckBox.OnClickListener()
				{
		            @Override
		            /**
		             * Acción onClick del checkBox cbActivo03ModifAct
		             * @param evento Evento
		             */
		            public void onClick(View evento) 
		            {
		            	try
						{
		            		activacancion03 = cbActivo03ModifAct.isChecked();
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar cbActivo03ModifAct: " + e.toString());
						}
		            }
		        });
						
				// Sobrecargo los listeners onClick de los botones para darles funcionalidad
				
				bAceptarNuevoPerfilModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bAceptarNuevoPerfilModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							int error = comprobarCorrectitudDatosModificarActividad();
							if( error == 0 )
							{
								ConfiguracionActividad actividadmodificada = new ConfiguracionActividad(getBaseContext());
								actividadmodificada.obtener(nombreactividad);
								actividadmodificada.actualizar(rutacancion01, rutacancion02, rutacancion03,
										rutadescripcion, activacancion01, activacancion02, activacancion03, 
										activadescripcion, rutaayuda, estimulo);
								actividadmodificada.almacenar();
								
								UtilidadesVarias.mostrarToastText(actividadactual, "Actividad " + actividadmodificada.getNombreactividad() + " modificada con éxito.");
							}
							else
							{
								String textoerror = "";
								switch(error)
								{
								case 1:
									textoerror =  "ERROR: No hay actividades creadas";
									break;
								case 2:
									textoerror =  "ERROR: Está activa la canción 01 y no se ha elegido fichero de audio";
									break;
								case 3:
									textoerror =  "ERROR: Está activa la canción 02 y no se ha elegido fichero de audio";
									break;
								case 4:
									textoerror =  "ERROR: Está activa la canción 03 y no se ha elegido fichero de audio";
									break;
								case 5:
									textoerror =  "ERROR: No se ha elegido fichero de audio para la ayuda";
									break;
								case 6:
									textoerror = "ERROR: Está activa la descripción y no se ha elegido fichero de audio";
									break;
								}
								UtilidadesVarias.mostrarToastText(actividadactual, textoerror);
							}
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar aceptar: " + e.toString());
						}
					}
				});
				
				bCancelarNuevoPerfilModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancelarNuevoPerfilModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( s1 != null )
							{
								s1.liberar();
								s1 = null;
							}
							if( s2 != null )
							{
								s2.liberar();
								s2 = null;
							}
							if( s3 != null )
							{
								s3.liberar();
								s3 = null;
							}
							if( sayuda != null )
							{
								sayuda.liberar();
								sayuda = null;
							}
							if( sdescripcion != null )
							{
								sdescripcion.liberar();
								sdescripcion = null;
							}
							finish();
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar cancelar: " + e.toString());
						}
					}
				});
				
				bDescripcionModif.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bDescripcionModif
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							ficheroelegir = 5;
							UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar bDescripcionModif: " + e.toString());
						}
					}
				});
				
				ibGrabarDescripcionModif.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón ibGrabarDescripcionModif
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
							{
								if( !grabandodescripcion )
								{
									rutadescripcion = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + nombreactividad + "_descripcion.wav";
									
									sdescripcion = new Sonido(actividadactual, "descripcion_actividad");
									sdescripcion.comenzarGrabacion(rutadescripcion);
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
									ibGrabarDescripcionModif.setImageBitmap(bmp);
									grabandodescripcion = Boolean.TRUE;
								}
								else
								{
									sdescripcion.finalizarGrabacion();
									sdescripcion.liberar();
									sdescripcion = null;
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
									ibGrabarDescripcionModif.setImageBitmap(bmp);
									grabandodescripcion = Boolean.FALSE;
									UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
								}
							}
							else
							{
								UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibGrabarDescripcionModif: " + e.toString());
						}
					}
				});
				
				ibEscucharDescripcionModif.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón ibEscucharDescripcionModif
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( rutadescripcion.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
							{
								if( !escuchandodescripcion )
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
									ibEscucharDescripcionModif.setImageBitmap(bmp);
									escuchandodescripcion = Boolean.TRUE;
									sdescripcion = new Sonido(actividadactual, "descripcion_actividad");
									sdescripcion.cargar(rutadescripcion, Boolean.FALSE);
									sdescripcion.reproducir();
								}
								else
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
									ibEscucharDescripcionModif.setImageBitmap(bmp);
									escuchandodescripcion = Boolean.FALSE;
									sdescripcion.stop();
									sdescripcion.liberar();
									sdescripcion = null;
								}
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibEscucharDescripcionModif: " + e.toString());
						}
					}
				});
				
				bCancion01ModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion01ModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							ficheroelegir = 1;
							UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar bCancion01ModifAct: " + e.toString());
						}
					}
				});
				
				ibGrabarModif01.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion01
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
							{
								if( !grabando01 )
								{
									rutacancion01 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + nombreactividad + "_cancion01.wav";
									
									s1 = new Sonido(actividadactual, "grabacion01");
									s1.comenzarGrabacion(rutacancion01);
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
									ibGrabarModif01.setImageBitmap(bmp);
									grabando01 = Boolean.TRUE;
								}
								else
								{
									s1.finalizarGrabacion();
									s1.liberar();
									s1 = null;
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
									ibGrabarModif01.setImageBitmap(bmp);
									grabando01 = Boolean.FALSE;
									UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
								}
							}
							else
							{
								UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibGrabarModif01: " + e.toString());
						}
					}
				});
				
				ibEscucharModif01.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion01
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( rutacancion01.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
							{
								if( !escuchando01 )
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
									ibEscucharModif01.setImageBitmap(bmp);
									escuchando01 = Boolean.TRUE;
									s1 = new Sonido(actividadactual, "grabacion01");
									s1.cargar(rutacancion01, Boolean.FALSE);
									s1.reproducir();
								}
								else
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
									ibEscucharModif01.setImageBitmap(bmp);
									escuchando01 = Boolean.FALSE;
									s1.stop();
									s1.liberar();
									s1 = null;
								}
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibEscucharModif01: " + e.toString());
						}
					}
				});
				
				bCancion02ModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion02ModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							ficheroelegir = 2;
							UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar bCancion02ModifAct: " + e.toString());
						}
					}
				});
				
				ibGrabarModif02.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion02
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
							{
								if( !grabando02 )
								{
									rutacancion02 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + nombreactividad + "_cancion02.wav";
									
									s2 = new Sonido(actividadactual, "grabacion02");
									s2.comenzarGrabacion(rutacancion02);
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
									ibGrabarModif02.setImageBitmap(bmp);
									grabando02 = Boolean.TRUE;
								}
								else
								{
									s2.finalizarGrabacion();
									s2.liberar();
									s2 = null;
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
									ibGrabarModif02.setImageBitmap(bmp);
									grabando02 = Boolean.FALSE;
									UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
								}
							}
							else
							{
								UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibGrabarModif02: " + e.toString());
						}
					}
				});
				
				ibEscucharModif02.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion02
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( rutacancion02.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
							{
								if( !escuchando02 )
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
									ibEscucharModif02.setImageBitmap(bmp);
									escuchando02 = Boolean.TRUE;
									s2 = new Sonido(actividadactual, "grabacion02");
									s2.cargar(rutacancion02, Boolean.FALSE);
									s2.reproducir();
								}
								else
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
									ibEscucharModif02.setImageBitmap(bmp);
									escuchando02 = Boolean.FALSE;
									s2.stop();
									s2.liberar();
									s2 = null;
								}
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibEscucharModif02: " + e.toString());
						}
					}
				});
				
				bCancion03ModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion03ModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							ficheroelegir = 3;
							UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar bCancion03ModifAct: " + e.toString());
						}
					}
				});
				
				ibGrabarModif03.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion03
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
							{
								if( !grabando03 )
								{
									rutacancion03 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + nombreactividad + "_cancion03.wav";
									
									s3 = new Sonido(actividadactual, "grabacion03");
									s3.comenzarGrabacion(rutacancion03);
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
									ibGrabarModif03.setImageBitmap(bmp);
									grabando03 = Boolean.TRUE;
								}
								else
								{
									s3.finalizarGrabacion();
									s3.liberar();
									s3 = null;
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
									ibGrabarModif03.setImageBitmap(bmp);
									grabando03 = Boolean.FALSE;
									UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
								}
							}
							else
							{
								UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibGrabarModif03: " + e.toString());
						}
					}
				});
				
				ibEscucharModif03.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancion03
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( rutacancion03.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
							{
								if( !escuchando03 )
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
									ibEscucharModif03.setImageBitmap(bmp);
									escuchando03 = Boolean.TRUE;
									s3 = new Sonido(actividadactual, "grabacion03");
									s3.cargar(rutacancion03, Boolean.FALSE);
									s3.reproducir();
								}
								else
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
									ibEscucharModif03.setImageBitmap(bmp);
									escuchando03 = Boolean.FALSE;
									s3.stop();
									s3.liberar();
									s3 = null;
								}
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibEscucharModif03: " + e.toString());
						}
					}
				});
				
				bAyudaModifAct.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bAyudaModifAct
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{/*
							 * Actualizo los elementos según la primera actividad que haya en el spinner
							 */
							actualizarVentanaConfiguracion(configuracion);
							ficheroelegir = 4;
							UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
						}
						catch (Exception e) 
						{
							Log.i("Modificar Actividad", "Error al pulsar bAyudaModifAct: " + e.toString());
						}
					}
				});
				
				ibGrabarModifAyuda.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancionayuda
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
							{
								if( !grabandoayuda )
								{
									rutaayuda = Environment.getExternalStorageDirectory() + "/cerca_lejos/" + nombreactividad + "_ayuda.wav";
									
									sayuda = new Sonido(actividadactual, "grabacionayuda");
									sayuda.comenzarGrabacion(rutaayuda);
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
									ibGrabarModifAyuda.setImageBitmap(bmp);
									grabandoayuda = Boolean.TRUE;
								}
								else
								{
									sayuda.finalizarGrabacion();
									sayuda.liberar();
									sayuda = null;
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
									ibGrabarModifAyuda.setImageBitmap(bmp);
									grabandoayuda = Boolean.FALSE;
									UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
								}
							}
							else
							{
								UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibGrabarModifayuda: " + e.toString());
						}
					}
				});
				
				ibEscucharModifAyuda.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					/**
					 * Acción onClick del botón bCancionayuda
					 * @param evento onClick
					 */
					public void onClick(View evento)
					{
						try
						{
							if( rutaayuda.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
							{
								if( !escuchandoayuda )
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
									ibEscucharModifAyuda.setImageBitmap(bmp);
									escuchandoayuda = Boolean.TRUE;
									sayuda = new Sonido(actividadactual, "grabacionayuda");
									sayuda.cargar(rutaayuda, Boolean.FALSE);
									sayuda.reproducir();
								}
								else
								{
									Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
									ibEscucharModifAyuda.setImageBitmap(bmp);
									escuchandoayuda = Boolean.FALSE;
									sayuda.stop();
									sayuda.liberar();
									sayuda = null;
								}
							}
						}
						catch (Exception e) 
						{
							Log.i("Crear Actividad", "Error al pulsar ibEscucharModifayuda: " + e.toString());
						}
					}
				});
		
		/*
		 * Actualizo los elementos según la primera actividad que haya en el spinner
		 */
		actualizarVentanaConfiguracion(configuracion);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_actividad, menu);
		return true;
	}

	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Listener para obtener los ficheros seleccionados del fileChooser
	 */
	private FileChooserDialog.OnFileSelectedListener onFileSelectedListener = new FileChooserDialog.OnFileSelectedListener() 
	{
		/**
		 * Se activa cuando se selecciona un fichero o carpeta
		 */
		public void onFileSelected(Dialog source, File file) 
		{
			switch( ficheroelegir )
			{
				case 1:
					rutacancion01 = file.getPath();
					break;
				case 2:
					rutacancion02 = file.getPath();
					break;
				case 3:
					rutacancion03 = file.getPath();
					break;
				case 4:
					rutaayuda = file.getPath();
					break;
			}
			
			source.hide();
			UtilidadesVarias.mostrarToastText(Modificar_actividad.this, "Seleccionado: " + file.getName());
		}
		
		/**
		 * Se activa cuando se crea un fichero o carpeta
		 */
		public void onFileSelected(Dialog source, File folder, String name) 
		{
			source.hide();
			UtilidadesVarias.mostrarToastText(Modificar_actividad.this, "Creado: " + folder.getName() + "/" + name);
		}
	};
	
	/**
	 * Actualiza la ventana de modificar actividad según la configuración  de la actividad seleccionada
	 * @param configuracionactividad Configuración de la actividad
	 */
	public void actualizarVentanaConfiguracion(ConfiguracionActividad configuracionactividad)
	{
		try
		{
			if( configuracionactividad != null )
			{
				rutacancion01 = configuracionactividad.getRutacancion01();
				activacancion01 = configuracionactividad.getEstaActivaCancion01();
				cbActivo01ModifAct.setChecked(activacancion01);
				rutacancion02 = configuracionactividad.getRutacancion02();
				activacancion02 = configuracionactividad.getEstaActiva02();
				cbActivo02ModifAct.setChecked(activacancion02);
				rutacancion03 = configuracionactividad.getRutacancion03();
				activacancion03 = configuracionactividad.getEstaActivaCancion03();
				cbActivo03ModifAct.setChecked(activacancion03);
				rutaayuda = configuracionactividad.getRutaAyuda();
				rutadescripcion = configuracionactividad.getRutaDescripcion();
				activadescripcion = configuracionactividad.getActivaDescripcion();
				cbActivoDescripcionModif.setChecked(configuracionactividad.getActivaDescripcion());
		
				String estimulocadena = configuracionactividad.getTipoestimulo();
				if( estimulocadena.compareTo("Volumen") == 0 )
				{
					estimulo = TipoEstimulo.VOLUMEN;
					cbTipoEstimuloModifAct.setSelection(0);
				}
				else
				{
					if( estimulocadena.compareTo("Timbre") == 0 )
					{
						estimulo = TipoEstimulo.TIMBRE;
						cbTipoEstimuloModifAct.setSelection(1);
					}
					else
					{
						estimulo = TipoEstimulo.FRECUENCIA;
						cbTipoEstimuloModifAct.setSelection(2);
					}
				}	
			}
		}
		catch (Exception e) 
		{
			Log.i("Modificar Actividad", "Error 111: " + e.toString());
		}
	}
	
	/**
	 * Muestra la configuración indicada en la pantalla
	 * @param nombreconfiguracion Configuración a mostrar
	 */
	public void mostrarConfiguracionVentana(String nombreconfiguracion)
	{
		if( nombreconfiguracion.compareTo( UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad) ) != 0 )
		{
			try
			{
				configuracionactividad = new ConfiguracionActividad(getBaseContext());
				configuracionactividad.obtener(nombreconfiguracion);
				actualizarVentanaConfiguracion(configuracionactividad);
			}
			catch (Exception e) 
			{
				Log.i("Modificar Actividad", "Error 01: " + e.toString());
			}
		}
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Comprueba que los datos son correctos
	 * @return Código de error
	 * 				1.-	No hay actividades creadas
	 * 				2.-	Está activa la canción 01 pero no se ha elegido un fichero de audio
	 * 				3.- Está activa la canción 02 pero no se ha elegido un fichero de audio
	 * 				4.- Está activa la canción 03 pero no se ha elegido un fichero de audio
	 * 				5.- No se ha elegido la ayuda
	 * 				6.- Está activa la descripción pero no se ha elegido un fichero de audio
	 */
	private int comprobarCorrectitudDatosModificarActividad()
	{
		int error = 0;
		
		if( spNombresActividadesModifAct.getSelectedItem().toString().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividades_creadas)) == 0 )
			error = 1;
		else
			if( activacancion01 && rutacancion01.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
				error = 2;
			else
				if( activacancion02 && rutacancion02.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
					error = 3;
				else
					if( activacancion03 && rutacancion03.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
						error = 4;
					else
						if( rutaayuda.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
							error = 5;
						else
							if( activadescripcion && rutadescripcion.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
								error = 6;
		
		return error;
	}
	
}

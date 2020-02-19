/**
 * @author francis
 * Esta clase nos permitirá crear una nueva actividad para la aplicación de cerca lejos
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import ar.com.daidalos.afiledialog.FileChooserDialog;

public class CrearActividad extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	/*
	 * Con ficheroelegir indico qué ruta quiero seleccionar:
	 * 	Si vale 1 elijo la ruta de la canción 01
	 * 	Si vale 2 elijo la ruta de la canción 02
	 * 	Si vale 3 elijo la ruta de la canción 03
	 * 	Si vale 4 elijo la ruta de la ayuda
	 *  Si vale 5 eligo la ruta de la descripción
	 */
	private int ficheroelegir;
	private String rutacancion01, rutacancion02, rutacancion03, rutadescripcion;
	private boolean activacancion01 = Boolean.TRUE, activacancion02 = Boolean.TRUE, activacancion03 = Boolean.TRUE;
	private boolean activadescripcion = Boolean.TRUE;
	private String rutaayuda;
	private TipoEstimulo estimulo = TipoEstimulo.VOLUMEN;
	private EditText edNombreActividad;
	private Activity actividadactual = this;
	private final String FILTRO_AUDIO = ".*mp3|.*wav";
	private boolean grabando01 = Boolean.FALSE, escuchando01 = Boolean.FALSE;
	private boolean grabando02 = Boolean.FALSE, escuchando02 = Boolean.FALSE;
	private boolean grabando03 = Boolean.FALSE, escuchando03 = Boolean.FALSE;
	private boolean grabandoayuda = Boolean.FALSE, escuchandoayuda = Boolean.FALSE;
	private boolean grabandodescripcion = Boolean.FALSE, escuchandodescripcion = Boolean.FALSE;
	private Sonido s1, s2, s3, sayuda, sdescripcion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_crear_perfil_cl.xml.
		 */
		setContentView(R.layout.activity_crear_actividad);
				
		// Obtengo los recursos de la actividad
		final Button bAceptarNuevoPerfil = (Button)findViewById(R.id.bAceptarNuevoPerfil);
		final Button bCancelarNuevoPerfil = (Button)findViewById(R.id.bCancelarNuevoPerfil);
		edNombreActividad = (EditText)findViewById(R.id.edNombreActividad);
		final Button bCancion01 = (Button)findViewById(R.id.bCancion01);
		final Button bDescripcion = (Button)findViewById(R.id.bDescripcion);
		
		final ImageButton ibGrabar01 = (ImageButton)findViewById(R.id.ibGrabar01);
		final ImageButton ibEscuchar01 = (ImageButton)findViewById(R.id.ibEscuchar01);
		final ImageButton ibGrabar02 = (ImageButton)findViewById(R.id.ibGrabar02);
		final ImageButton ibEscuchar02 = (ImageButton)findViewById(R.id.ibEscuchar02);
		final ImageButton ibGrabar03 = (ImageButton)findViewById(R.id.ibGrabar03);
		final ImageButton ibEscuchar03 = (ImageButton)findViewById(R.id.ibEscuchar03);
		final ImageButton ibGrabarAyuda = (ImageButton)findViewById(R.id.ibGrabarAyuda);
		final ImageButton ibEscucharAyuda = (ImageButton)findViewById(R.id.ibEscucharAyuda);
		final ImageButton ibGrabarDescripcion = (ImageButton)findViewById(R.id.ibGrabarDescripcion);
		final ImageButton ibEscucharDescripcion = (ImageButton)findViewById(R.id.ibEscucharDescripcion);
		
		final Button bCancion02 = (Button)findViewById(R.id.bCancion02);
		final Button bCancion03 = (Button)findViewById(R.id.bCancion03);
		final Button bAyuda = (Button)findViewById(R.id.bAyuda);
		final Spinner cbTipoEstimulo = (Spinner)findViewById(R.id.cbTipoEstimulo);
		final CheckBox cbActivo01 = (CheckBox)findViewById(R.id.cbActivo01);
		final CheckBox cbActivo02 = (CheckBox)findViewById(R.id.cbActivo02);
		final CheckBox cbActivo03 = (CheckBox)findViewById(R.id.cbActivo03);
		final CheckBox cbActivoDescripcion = (CheckBox)findViewById(R.id.cbActivoDescripcion);
		
		rutacancion01 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion02 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion03 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutaayuda = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutadescripcion = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		
		// Pongo los estímulos en el spinner
		
		ArrayList<String> estimulos = new ArrayList<String>();
		estimulos.add("Frecuencia");
		estimulos.add("Timbre");
		estimulos.add("Volumen");
		UtilidadesVarias.rellenarSpinner(actividadactual, cbTipoEstimulo, estimulos);
		
		// Sobrecarlo el listener setOnItemSelectedListener para cuando se elija un elemento 
		// nuevo del spinner
		
		cbTipoEstimulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					String estimuloseleccionado = (String) cbTipoEstimulo.getItemAtPosition(position);
					
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
					Log.i("Crear Actividad", "Error al elegir un elemento del spinner de estímulos: " + e.toString());
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
		
		cbActivoDescripcion.setOnClickListener(new CheckBox.OnClickListener()
		{
            @Override
            /**
             * Acción onClick del checkBox cbActivoDescripcion
             * @param evento Evento
             */
            public void onClick(View evento) 
            {
            	try
				{
            		activadescripcion = cbActivoDescripcion.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoDescripcion: " + e.toString());
				}
            }
        });
		
		cbActivo01.setOnClickListener(new CheckBox.OnClickListener()
		{
            @Override
            /**
             * Acción onClick del checkBox cbActivo01
             * @param evento Evento
             */
            public void onClick(View evento) 
            {
            	try
				{
					activacancion01 = cbActivo01.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivo01: " + e.toString());
				}
            }
        });
		
		cbActivo02.setOnClickListener(new CheckBox.OnClickListener()
		{
            @Override
            /**
             * Acción onClick del checkBox cbActivo02
             * @param evento Evento
             */
            public void onClick(View evento) 
            {
            	try
				{
            		activacancion02 = cbActivo02.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivo02: " + e.toString());
				}
            }
        });
		
		cbActivo03.setOnClickListener(new CheckBox.OnClickListener()
		{
            @Override
            /**
             * Acción onClick del checkBox cbActivo03
             * @param evento Evento
             */
            public void onClick(View evento) 
            {
            	try
				{
            		activacancion03 = cbActivo03.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivo03: " + e.toString());
				}
            }
        });
				
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bAceptarNuevoPerfil.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bAceptarNuevoPerfil
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					int error = comprobarCorrectitudDatosCrearActividad();
					if( error == 0 )
					{
						if( estimulo.toInt() == 0 )
						{
							ConfiguracionActividad actividad = new ConfiguracionActividad( getBaseContext(),
								edNombreActividad.getText().toString(), rutacancion01, rutacancion02, rutacancion03,
								rutadescripcion, activacancion01, activacancion02, activacancion03, activadescripcion, rutaayuda, estimulo);
							actividad.almacenar();
						
							UtilidadesVarias.mostrarToastText(actividadactual, "Actividad " + actividad.getNombreactividad() + " creada con éxito.");
						}
						else
							UtilidadesVarias.mostrarToastText(CrearActividad.this, "El estímulo " + estimulo.toString() + " no está implementado");
					}
					else
					{
						String textoerror = "";
						switch(error)
						{
							case 1:
								textoerror =  "ERROR: Falta el nombre de la actividad";
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
								textoerror = "ERROR: La actividad " + edNombreActividad.getText().toString() + " ya existe";
								break;
							case 7:
								textoerror = "ERROR: Está activa la descripción y no se ha elegido fichero de audio";
								break;
						}
						UtilidadesVarias.mostrarToastText(CrearActividad.this, textoerror);
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar aceptar: " + e.toString());
				}
			}
		});
		
		bCancelarNuevoPerfil.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bCancelarNuevoPerfil
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
					Log.i("Crear Actividad", "Error al pulsar cancelar: " + e.toString());
				}
			}
		});
		
		bDescripcion.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bDescripcion
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
					Log.i("Crear Actividad", "Error al pulsar bDescripcion: " + e.toString());
				}
			}
		});
		
		ibGrabarDescripcion.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabarDescripcion
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabandodescripcion )
						{
							rutadescripcion = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + edNombreActividad.getText().toString() + "_descripcion.wav";
							
							sdescripcion = new Sonido(actividadactual, "descripcion_actividad");
							sdescripcion.comenzarGrabacion(rutadescripcion);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabarDescripcion.setImageBitmap(bmp);
							grabandodescripcion = Boolean.TRUE;
						}
						else
						{
							sdescripcion.finalizarGrabacion();
							sdescripcion.liberar();
							sdescripcion = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabarDescripcion.setImageBitmap(bmp);
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabarDescripcion: " + e.toString());
				}
			}
		});
		
		ibEscucharDescripcion.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscucharDescripcion
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
							ibEscucharDescripcion.setImageBitmap(bmp);
							escuchandodescripcion = Boolean.TRUE;
							sdescripcion = new Sonido(actividadactual, "descripcion_actividad");
							sdescripcion.cargar(rutadescripcion, Boolean.FALSE);
							sdescripcion.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscucharDescripcion.setImageBitmap(bmp);
							escuchandodescripcion = Boolean.FALSE;
							sdescripcion.stop();
							sdescripcion.liberar();
							sdescripcion = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscucharDescripcion: " + e.toString());
				}
			}
		});
		
		bCancion01.setOnClickListener(new View.OnClickListener() 
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
					ficheroelegir = 1;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bCancion01: " + e.toString());
				}
			}
		});
		
		ibGrabar01.setOnClickListener(new View.OnClickListener() 
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
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando01 )
						{
							rutacancion01 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + edNombreActividad.getText().toString() + "_cancion01.wav";
							
							s1 = new Sonido(actividadactual, "grabacion01");
							s1.comenzarGrabacion(rutacancion01);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar01.setImageBitmap(bmp);
							grabando01 = Boolean.TRUE;
						}
						else
						{
							s1.finalizarGrabacion();
							s1.liberar();
							s1 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar01.setImageBitmap(bmp);
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar01: " + e.toString());
				}
			}
		});
		
		ibEscuchar01.setOnClickListener(new View.OnClickListener() 
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
							ibEscuchar01.setImageBitmap(bmp);
							escuchando01 = Boolean.TRUE;
							s1 = new Sonido(actividadactual, "grabacion01");
							s1.cargar(rutacancion01, Boolean.FALSE);
							s1.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar01.setImageBitmap(bmp);
							escuchando01 = Boolean.FALSE;
							s1.stop();
							s1.liberar();
							s1 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar01: " + e.toString());
				}
			}
		});
		
		bCancion02.setOnClickListener(new View.OnClickListener() 
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
					ficheroelegir = 2;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bCancion02: " + e.toString());
				}
			}
		});
		
		ibGrabar02.setOnClickListener(new View.OnClickListener() 
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
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando02 )
						{
							rutacancion02 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + edNombreActividad.getText().toString() + "_cancion02.wav";
							
							s2 = new Sonido(actividadactual, "grabacion02");
							s2.comenzarGrabacion(rutacancion02);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar02.setImageBitmap(bmp);
							grabando02 = Boolean.TRUE;
						}
						else
						{
							s2.finalizarGrabacion();
							s2.liberar();
							s2 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar02.setImageBitmap(bmp);
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar02: " + e.toString());
				}
			}
		});
		
		ibEscuchar02.setOnClickListener(new View.OnClickListener() 
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
							ibEscuchar02.setImageBitmap(bmp);
							escuchando02 = Boolean.TRUE;
							s2 = new Sonido(actividadactual, "grabacion02");
							s2.cargar(rutacancion02, Boolean.FALSE);
							s2.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar02.setImageBitmap(bmp);
							escuchando02 = Boolean.FALSE;
							s2.stop();
							s2.liberar();
							s2 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar02: " + e.toString());
				}
			}
		});
		
		bCancion03.setOnClickListener(new View.OnClickListener() 
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
					ficheroelegir = 3;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bCancion03: " + e.toString());
				}
			}
		});
		
		ibGrabar03.setOnClickListener(new View.OnClickListener() 
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
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando03 )
						{
							rutacancion03 = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + edNombreActividad.getText().toString() + "_cancion03.wav";
							
							s3 = new Sonido(actividadactual, "grabacion03");
							s3.comenzarGrabacion(rutacancion03);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar03.setImageBitmap(bmp);
							grabando03 = Boolean.TRUE;
						}
						else
						{
							s3.finalizarGrabacion();
							s3.liberar();
							s3 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar03.setImageBitmap(bmp);
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar03: " + e.toString());
				}
			}
		});
		
		ibEscuchar03.setOnClickListener(new View.OnClickListener() 
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
							ibEscuchar03.setImageBitmap(bmp);
							escuchando03 = Boolean.TRUE;
							s3 = new Sonido(actividadactual, "grabacion03");
							s3.cargar(rutacancion03, Boolean.FALSE);
							s3.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar03.setImageBitmap(bmp);
							escuchando03 = Boolean.FALSE;
							s3.stop();
							s3.liberar();
							s3 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar03: " + e.toString());
				}
			}
		});
		
		bAyuda.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bAyuda
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 4;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bAyuda: " + e.toString());
				}
			}
		});
		
		ibGrabarAyuda.setOnClickListener(new View.OnClickListener() 
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
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabandoayuda )
						{
							rutaayuda = Environment.getExternalStorageDirectory() + "/cerca_lejos_" + edNombreActividad.getText().toString() + "_ayuda.wav";
							
							sayuda = new Sonido(actividadactual, "grabacionayuda");
							sayuda.comenzarGrabacion(rutaayuda);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabarAyuda.setImageBitmap(bmp);
							grabandoayuda = Boolean.TRUE;
						}
						else
						{
							sayuda.finalizarGrabacion();
							sayuda.liberar();
							sayuda = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabarAyuda.setImageBitmap(bmp);
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabarayuda: " + e.toString());
				}
			}
		});
		
		ibEscucharAyuda.setOnClickListener(new View.OnClickListener() 
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
							ibEscucharAyuda.setImageBitmap(bmp);
							escuchandoayuda = Boolean.TRUE;
							sayuda = new Sonido(actividadactual, "grabacionayuda");
							sayuda.cargar(rutaayuda, Boolean.FALSE);
							sayuda.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscucharAyuda.setImageBitmap(bmp);
							escuchandoayuda = Boolean.FALSE;
							sayuda.stop();
							sayuda.liberar();
							sayuda = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscucharayuda: " + e.toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crear_perfil_cl, menu);
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
				case 5:
					rutadescripcion = file.getPath();
					break;
			}
			
			source.hide();
			UtilidadesVarias.mostrarToastText(CrearActividad.this, "Seleccionado: " + file.getName());
		}
		
		/**
		 * Se activa cuando se crea un fichero o carpeta
		 */
		public void onFileSelected(Dialog source, File folder, String name) 
		{
			/*source.hide();
			UtilidadesVarias.mostrarToastText(CrearActividad.this, "Creado: " + folder.getName() + "/" + name);
			*/
		}
	};
	
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
	 * 				5.- No se ha elegido ayuda
	 * 				6.- La actividad a crear ya existe
	 * 				7.- Está activa la descripción pero no se ha elegido un fichero de audio
	 */
	private int comprobarCorrectitudDatosCrearActividad()
	{
		int error = 0;
		
		if( UtilidadesVarias.estaEditTextVacio(edNombreActividad) )
			error = 1;
		else
			if( UtilidadesActividades.existeActividad(actividadactual, edNombreActividad.getText().toString()) )
				error = 6;
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
									error = 7;
		
		return error;
	}
}

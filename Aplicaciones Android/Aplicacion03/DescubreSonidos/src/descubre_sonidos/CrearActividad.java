/**
 * @author francis
 * Esta clase nos permitirá crear un nuevo perfil para la aplicación de descubre sonidos
 */

package descubre_sonidos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import sonido.Sonido;
import supertooltip.ToolTip;
import supertooltip.ToolTipRelativeLayout;
import supertooltip.ToolTipView;
import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;

import actividad.ConfiguracionActividad;
import actividad.UtilidadesActividades;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import ar.com.daidalos.afiledialog.FileChooserDialog;

public class CrearActividad extends Activity implements ToolTipView.OnToolTipViewClickedListener 
{
	/*
	 * Atributos de la clase
	 */
	
	/*
	 * Con ficheroelegir indico qué ruta quiero seleccionar:
	 * 	Si vale 1 elijo la ruta del sonido 01
	 * 	Si vale 2 elijo la ruta del sonido 02
	 * 	Si vale 3 elijo la ruta del sonido 03
	 * 	Si vale 4 elijo la ruta del sonido 04
	 * 	Si vale 5 elijo la ruta del sonido 05
	 * 	Si vale 6 elijo la ruta del sonido 06
	 *  Si vale 7 elijo la ruta de un estímulo positivo
	 *  Si vale 8 elijo la ruta del estímulo negativo
	 *  Si vale 9 elijo la ruta de la imagen 01
	 *  Si vale 10 elijo la ruta de la imagen 02
	 *  Si vale 11 elijo la ruta de la imagen 03
	 *  Si vale 12 elijo la ruta de la imagen 04
	 *  Si vale 13 elijo la ruta de la imagen 05
	 *  Si vale 14 elijo la ruta de la imagen 06
	 *  Si vale 15 elijo la ruta del sonido de la ayuda
	 *  Si vale 16 elijo la ruta de la descripción de la actividad
	 */
	private int ficheroelegir;
	private String rutacancion01, rutacancion02, rutacancion03, rutacancion04, rutacancion05, rutacancion06;
	private String rutaimagen01, rutaimagen02, rutaimagen03, rutaimagen04, rutaimagen05, rutaimagen06;
	private boolean estaactivacancion01 = Boolean.TRUE, estaactivacancion02 = Boolean.TRUE, estaactivacancion03 = Boolean.TRUE, estaactivacancion04 = Boolean.TRUE, estaactivacancion05 = Boolean.TRUE, estaactivacancion06 = Boolean.TRUE;
	private boolean estaactivaimagen01 = Boolean.TRUE, estaactivaimagen02 = Boolean.TRUE, estaactivaimagen03 = Boolean.TRUE, estaactivaimagen04 = Boolean.TRUE, estaactivaimagen05 = Boolean.TRUE, estaactivaimagen06 = Boolean.TRUE;
	private String rutaayuda;
	private ArrayList<String> rutasestimulospositivos = new ArrayList<String>();
	private String rutaestimulonegativo;
	private boolean estaactivoestimulonegativo = Boolean.TRUE;
	private Activity actividadactual = this;
	private final String FILTRO_AUDIO = ".*mp3|.*wav";
	private final String FILTRO_IMAGEN = ".*jpg|.*jpeg";
	private TextView tCantidadEstimulosPositivos;
	private EditText edNombreActividad;
	private String rutadescripcion;
	private boolean activadescripcion = Boolean.TRUE;
	private boolean grabando01 = Boolean.FALSE, escuchando01 = Boolean.FALSE;
	private boolean grabando02 = Boolean.FALSE, escuchando02 = Boolean.FALSE;
	private boolean grabando03 = Boolean.FALSE, escuchando03 = Boolean.FALSE;
	private boolean grabando04 = Boolean.FALSE, escuchando04 = Boolean.FALSE;
	private boolean grabando05 = Boolean.FALSE, escuchando05 = Boolean.FALSE;
	private boolean grabando06 = Boolean.FALSE, escuchando06 = Boolean.FALSE;
	private boolean grabandoayuda = Boolean.FALSE, escuchandoayuda = Boolean.FALSE;
	private boolean grabandodescripcion = Boolean.FALSE, escuchandodescripcion = Boolean.FALSE;
	private Sonido s1, s2, s3, s4, s5, s6, sayuda, sdescripcion;
	private ImageButton ibVerImagen01, ibVerImagen02, ibVerImagen03, ibVerImagen04, ibVerImagen05, ibVerImagen06;
	private CheckBox cbActivoImagen01, cbActivoImagen02, cbActivoImagen03, cbActivoImagen04, cbActivoImagen05, cbActivoImagen06;
	
	private int imagenprevisualizar = 0;
	private ToolTipView tooltipview;
	private ToolTipRelativeLayout layout_tooltip_crear;
	
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
		setContentView(R.layout.activity_crear_actividad);
	
		// Obtengo los recursos de la actividad
		layout_tooltip_crear = (ToolTipRelativeLayout) findViewById(R.id.layout_tooltip_crear);
		final Button bSonido01 = (Button)findViewById(R.id.bSonido01);
		final Button bSonido02 = (Button)findViewById(R.id.bSonido02);
		final Button bSonido03 = (Button)findViewById(R.id.bSonido03);
		final Button bSonido04 = (Button)findViewById(R.id.bSonido04);
		final Button bSonido05 = (Button)findViewById(R.id.bSonido05);
		final Button bSonido06 = (Button)findViewById(R.id.bSonido06);
		final Button bImagen01 = (Button)findViewById(R.id.bImagen01);
		final Button bImagen02 = (Button)findViewById(R.id.bImagen02);
		final Button bImagen03 = (Button)findViewById(R.id.bImagen03);
		final Button bImagen04 = (Button)findViewById(R.id.bImagen04);
		final Button bImagen05 = (Button)findViewById(R.id.bImagen05);
		final Button bImagen06 = (Button)findViewById(R.id.bImagen06);
		final Button bAyuda = (Button)findViewById(R.id.bAyuda);
		final Button bEstimuloPositivo = (Button)findViewById(R.id.bEstimuloPositivo);
		final Button bEstimuloNegativo = (Button)findViewById(R.id.bEstimuloNegativo);
		final Button bAceptarNuevoPerfil = (Button)findViewById(R.id.bAceptarNuevoPerfil);
		final Button bCancelarNuevoPerfil = (Button)findViewById(R.id.bCancelarNuevoPerfil);
		tCantidadEstimulosPositivos = (TextView)findViewById(R.id.tCantidadEstimulosPositivos);
		edNombreActividad = (EditText)findViewById(R.id.edNombreActividad);
		final CheckBox cbActivoSonido01 = (CheckBox)findViewById(R.id.cbActivoSonido01);
		final CheckBox cbActivoSonido02 = (CheckBox)findViewById(R.id.cbActivoSonido02);
		final CheckBox cbActivoSonido03 = (CheckBox)findViewById(R.id.cbActivoSonido03);
		final CheckBox cbActivoSonido04 = (CheckBox)findViewById(R.id.cbActivoSonido04);
		final CheckBox cbActivoSonido05 = (CheckBox)findViewById(R.id.cbActivoSonido05);
		final CheckBox cbActivoSonido06 = (CheckBox)findViewById(R.id.cbActivoSonido06);
		cbActivoImagen01 = (CheckBox)findViewById(R.id.cbActivoImagen01);
		cbActivoImagen02 = (CheckBox)findViewById(R.id.cbActivoImagen02);
		cbActivoImagen03 = (CheckBox)findViewById(R.id.cbActivoImagen03);
		cbActivoImagen04 = (CheckBox)findViewById(R.id.cbActivoImagen04);
		cbActivoImagen05 = (CheckBox)findViewById(R.id.cbActivoImagen05);
		cbActivoImagen06 = (CheckBox)findViewById(R.id.cbActivoImagen06);
		final CheckBox cbEstimuloNegativo = (CheckBox)findViewById(R.id.cbEstimuloNegativo);
		final CheckBox cbActivoDescripcion = (CheckBox)findViewById(R.id.cbActivoDescripcion);
		
		final Button bDescripcion = (Button)findViewById(R.id.bDescripcion);
		final ImageButton ibGrabar01 = (ImageButton)findViewById(R.id.ibGrabar01);
		final ImageButton ibEscuchar01 = (ImageButton)findViewById(R.id.ibEscuchar01);
		final ImageButton ibGrabar02 = (ImageButton)findViewById(R.id.ibGrabar02);
		final ImageButton ibEscuchar02 = (ImageButton)findViewById(R.id.ibEscuchar02);
		final ImageButton ibGrabar03 = (ImageButton)findViewById(R.id.ibGrabar03);
		final ImageButton ibEscuchar03 = (ImageButton)findViewById(R.id.ibEscuchar03);
		final ImageButton ibGrabar04 = (ImageButton)findViewById(R.id.ibGrabar04);
		final ImageButton ibEscuchar04 = (ImageButton)findViewById(R.id.ibEscuchar04);
		final ImageButton ibGrabar05 = (ImageButton)findViewById(R.id.ibGrabar05);
		final ImageButton ibEscuchar05 = (ImageButton)findViewById(R.id.ibEscuchar05);
		final ImageButton ibGrabar06 = (ImageButton)findViewById(R.id.ibGrabar06);
		final ImageButton ibEscuchar06 = (ImageButton)findViewById(R.id.ibEscuchar06);
		final ImageButton ibGrabarAyuda = (ImageButton)findViewById(R.id.ibGrabarAyuda);
		final ImageButton ibEscucharAyuda = (ImageButton)findViewById(R.id.ibEscucharAyuda);
		final ImageButton ibGrabarDescripcion = (ImageButton)findViewById(R.id.ibGrabarDescripcion);
		final ImageButton ibEscucharDescripcion = (ImageButton)findViewById(R.id.ibEscucharDescripcion);
		ibVerImagen01 = (ImageButton)findViewById(R.id.ibVerImagen01);
		ibVerImagen02 = (ImageButton)findViewById(R.id.ibVerImagen02);
		ibVerImagen03 = (ImageButton)findViewById(R.id.ibVerImagen03);
		ibVerImagen04 = (ImageButton)findViewById(R.id.ibVerImagen04);
		ibVerImagen05 = (ImageButton)findViewById(R.id.ibVerImagen05);
		ibVerImagen06 = (ImageButton)findViewById(R.id.ibVerImagen06);
		
		final ImageButton ibHacerFoto01 = (ImageButton)findViewById(R.id.ibHacerFoto01);
		final ImageButton ibHacerFoto02 = (ImageButton)findViewById(R.id.ibHacerFoto02);
		final ImageButton ibHacerFoto03 = (ImageButton)findViewById(R.id.ibHacerFoto03);
		final ImageButton ibHacerFoto04 = (ImageButton)findViewById(R.id.ibHacerFoto04);
		final ImageButton ibHacerFoto05 = (ImageButton)findViewById(R.id.ibHacerFoto05);
		final ImageButton ibHacerFoto06 = (ImageButton)findViewById(R.id.ibHacerFoto06);
		
		rutadescripcion = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion01 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion02 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion03 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion04 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion05 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutacancion06 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutaimagen01 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaimagen02 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaimagen03 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaimagen04 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaimagen05 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaimagen06 = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada);
		rutaayuda = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
		rutaestimulonegativo = UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado);
				
		// Sobrecargo los listeners setOnClickListener para saber cuando se hace click en un checkbox
		
		cbActivoSonido01.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido01
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion01 = cbActivoSonido01.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido01: " + e.toString());
				}
			}
		});
		
		cbActivoSonido02.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido02
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion02 = cbActivoSonido02.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido02: " + e.toString());
				}
			}
		});
		
		cbActivoSonido03.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido03
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion03 = cbActivoSonido03.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido03: " + e.toString());
				}
			}
		});
		
		cbActivoSonido04.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido04
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion04 = cbActivoSonido04.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido04: " + e.toString());
				}
			}
		});
		
		cbActivoSonido05.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido05
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion05 = cbActivoSonido05.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido05: " + e.toString());
				}
			}
		});
		
		cbActivoSonido06.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoSonido06
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivacancion06 = cbActivoSonido06.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoSonido06: " + e.toString());
				}
			}
		});
		
		cbActivoImagen01.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen01
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen01 = cbActivoImagen01.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen01: " + e.toString());
				}
			}
		});
		
		cbActivoImagen02.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen02
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen02 = cbActivoImagen02.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen02: " + e.toString());
				}
			}
		});
		
		cbActivoImagen03.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen03
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen03 = cbActivoImagen03.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen03: " + e.toString());
				}
			}
		});
		
		cbActivoImagen04.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen04
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen04 = cbActivoImagen04.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen04: " + e.toString());
				}
			}
		});
		
		cbActivoImagen05.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen05
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen05 = cbActivoImagen05.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen05: " + e.toString());
				}
			}
		});
		
		cbActivoImagen06.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbActivoImagen06
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivaimagen06 = cbActivoImagen06.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbActivoImagen06: " + e.toString());
				}
			}
		});
		
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
		
		cbEstimuloNegativo.setOnClickListener(new CheckBox.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del checkBox cbEstimuloNegativo
			 * @param evento Evento
			 */
			public void onClick(View evento) 
			{
				try
				{
					estaactivoestimulonegativo = cbEstimuloNegativo.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar cbEstimuloNegativo: " + e.toString());
				}
			}
		});
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
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
					ficheroelegir = 16;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bDescripcion: " + e.toString());
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
					if( s4 != null )
					{
						s4.liberar();
						s4 = null;
					}
					if( s5 != null )
					{
						s5.liberar();
						s5 = null;
					}
					if( s6 != null )
					{
						s6.liberar();
						s6 = null;
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
		
		ibVerImagen01.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen01
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 1;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen01: " + e.toString());
				}
			}
		});
		
		ibVerImagen02.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen02
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 2;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen02: " + e.toString());
				}
			}
		});
		
		ibVerImagen03.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen03
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 3;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen03: " + e.toString());
				}
			}
		});
		
		ibVerImagen04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen04
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 4;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen04: " + e.toString());
				}
			}
		});
		
		ibVerImagen05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen05
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 5;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen05: " + e.toString());
				}
			}
		});
		
		ibVerImagen06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					imagenprevisualizar = 6;
					mostrarImagenToolTip();
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen06: " + e.toString());
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
							rutadescripcion = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_descripcion.wav";
							
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
		
		ibHacerFoto01.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto01
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 9;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto01: " + e.toString());
				}
			}
		});
		
		ibHacerFoto02.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto02
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 10;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto02: " + e.toString());
				}
			}
		});
		
		ibHacerFoto03.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto03
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 11;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto03: " + e.toString());
				}
			}
		});
		
		ibHacerFoto04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto04
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 12;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto04: " + e.toString());
				}
			}
		});
		
		ibHacerFoto05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto05
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 13;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto05: " + e.toString());
				}
			}
		});
		
		ibHacerFoto06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						ficheroelegir = 14;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "ERROR: escriba el nombre de la actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto06: " + e.toString());
				}
			}
		});
		
		ibGrabar01.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar01
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
							rutacancion01 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion01.wav";
							
							s1 = new Sonido(actividadactual, "Cancion 01");
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
			 * Acción onClick del botón ibEscuchar01
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
							s1 = new Sonido(actividadactual, "Cancion 01");
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
		
		ibGrabar02.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar02
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
							rutacancion02 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion02.wav";
							
							s2 = new Sonido(actividadactual, "Cancion 02");
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
			 * Acción onClick del botón ibEscuchar02
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
							s2 = new Sonido(actividadactual, "Cancion 02");
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
		
		ibGrabar03.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar03
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
							rutacancion03 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion03.wav";
							
							s3 = new Sonido(actividadactual, "Cancion 03");
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
			 * Acción onClick del botón ibEscuchar03
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
							s3 = new Sonido(actividadactual, "Cancion 03");
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
		
		ibGrabar04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar04
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando04 )
						{
							rutacancion04 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion04.wav";
							
							s4 = new Sonido(actividadactual, "Cancion 04");
							s4.comenzarGrabacion(rutacancion04);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar04.setImageBitmap(bmp);
							grabando04 = Boolean.TRUE;
						}
						else
						{
							s4.finalizarGrabacion();
							s4.liberar();
							s4 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar04.setImageBitmap(bmp);
							grabando04 = Boolean.FALSE;
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar04: " + e.toString());
				}
			}
		});
		
		ibEscuchar04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar04
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( rutacancion04.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
					{
						if( !escuchando04 )
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
							ibEscuchar04.setImageBitmap(bmp);
							escuchando04 = Boolean.TRUE;
							s4 = new Sonido(actividadactual, "Cancion 04");
							s4.cargar(rutacancion04, Boolean.FALSE);
							s4.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar04.setImageBitmap(bmp);
							escuchando04 = Boolean.FALSE;
							s4.stop();
							s4.liberar();
							s4 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar04: " + e.toString());
				}
			}
		});
		
		ibGrabar05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar05
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando05 )
						{
							rutacancion05 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion05.wav";
							
							s5 = new Sonido(actividadactual, "Cancion 05");
							s5.comenzarGrabacion(rutacancion05);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar05.setImageBitmap(bmp);
							grabando05 = Boolean.TRUE;
						}
						else
						{
							s5.finalizarGrabacion();
							s5.liberar();
							s5 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar05.setImageBitmap(bmp);
							grabando05 = Boolean.FALSE;
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar05: " + e.toString());
				}
			}
		});
		
		ibEscuchar05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar05
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( rutacancion05.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
					{
						if( !escuchando05 )
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
							ibEscuchar05.setImageBitmap(bmp);
							escuchando05 = Boolean.TRUE;
							s5 = new Sonido(actividadactual, "Cancion 05");
							s5.cargar(rutacancion05, Boolean.FALSE);
							s5.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar05.setImageBitmap(bmp);
							escuchando05 = Boolean.FALSE;
							s5.stop();
							s5.liberar();
							s5 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar05: " + e.toString());
				}
			}
		});
		
		ibGrabar06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( edNombreActividad.getText().toString().compareTo("") != 0 )
					{
						if( !grabando06 )
						{
							rutacancion06 = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion06.wav";
							
							s6 = new Sonido(actividadactual, "Cancion 06");
							s6.comenzarGrabacion(rutacancion06);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar06.setImageBitmap(bmp);
							grabando06 = Boolean.TRUE;
						}
						else
						{
							s6.finalizarGrabacion();
							s6.liberar();
							s6 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar06.setImageBitmap(bmp);
							grabando06 = Boolean.FALSE;
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabar06: " + e.toString());
				}
			}
		});
		
		ibEscuchar06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( rutacancion06.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
					{
						if( !escuchando06 )
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note_reproduciendo);
							ibEscuchar06.setImageBitmap(bmp);
							escuchando06 = Boolean.TRUE;
							s6 = new Sonido(actividadactual, "Cancion 06");
							s6.cargar(rutacancion06, Boolean.FALSE);
							s6.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar06.setImageBitmap(bmp);
							escuchando06 = Boolean.FALSE;
							s6.stop();
							s6.liberar();
							s6 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar06: " + e.toString());
				}
			}
		});
		
		ibGrabarAyuda.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabarAyuda
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
							rutaayuda = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString() + "_cancion06.wav";
							
							sayuda = new Sonido(actividadactual, "Ayuda");
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
					Log.i("Crear Actividad", "Error al pulsar ibGrabarAyuda: " + e.toString());
				}
			}
		});
		
		ibEscucharAyuda.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscucharAyuda
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
							sayuda = new Sonido(actividadactual, "Ayuda");
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
					Log.i("Crear Actividad", "Error al pulsar ibEscucharAyuda: " + e.toString());
				}
			}
		});
		
		
		bAceptarNuevoPerfil.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bAceptarNuevoPerfil
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				
				//**********************Aqui agrego el tooltip*******************
				
				
				try
				{
					int error = comprobarCorrectitudDatosCrearActividad();
					if( error == 0 )
					{
						ConfiguracionActividad actividad = new ConfiguracionActividad( getBaseContext(), edNombreActividad.getText().toString(),
								rutacancion01, rutacancion02, rutacancion03, rutacancion04, rutacancion05, rutacancion06,
								rutaimagen01, rutaimagen02, rutaimagen03, rutaimagen04, rutaimagen05, rutaimagen06,
								estaactivacancion01, estaactivacancion02, estaactivacancion03, estaactivacancion04, estaactivacancion05, estaactivacancion06,
								estaactivaimagen01, estaactivaimagen02, estaactivaimagen03, estaactivaimagen04, estaactivaimagen05, estaactivaimagen06,
								rutaayuda, rutasestimulospositivos, 
								rutaestimulonegativo, estaactivoestimulonegativo,
								rutadescripcion, activadescripcion);
						actividad.almacenar();
						
						UtilidadesVarias.mostrarToastText(actividadactual, "Actividad " + actividad.getNombreactividad() + " creada con éxito.");
					}
					else
					{						
						String textoerror = "";
						switch(error)
						{
							case 1:
								textoerror =  "ERROR: No se ha elegido el nombre de la actividad";
								break;
							case 2:
								textoerror =  "ERROR: Está activo el sonido del elemento 01 pero no se ha elegido un fichero de audio";
								break;
							case 3:
								textoerror =  "ERROR: Está activo el sonido del elemento 02 pero no se ha elegido un fichero de audio";
								break;
							case 4:
								textoerror =  "ERROR: Está activo el sonido del elemento 03 pero no se ha elegido un fichero de audio";
								break;
							case 5:
								textoerror =  "ERROR: Está activo el sonido del elemento 04 pero no se ha elegido un fichero de audio";
								break;
							case 6:
								textoerror =  "ERROR: La actividad a crear ya existe";
								break;
							case 7:
								textoerror =  "ERROR: Está activo el sonido del elemento 05 pero no se ha elegido un fichero de audio";
								break;
							case 8:
								textoerror =  "ERROR: Está activo el sonido del elemento 06 pero no se ha elegido un fichero de audio";
								break;
							case 9:
								textoerror =  "ERROR: Está activa la imagen del elemento 01 pero no se ha elegido un fichero de imagen";
								break;
							case 10:
								textoerror =  "ERROR: Está activa la imagen del elemento 02 pero no se ha elegido un fichero de imagen";
								break;
							case 11:
								textoerror =  "ERROR: Está activa la imagen del elemento 03 pero no se ha elegido un fichero de imagen";
								break;
							case 12:
								textoerror =  "ERROR: Está activa la imagen del elemento 04 pero no se ha elegido un fichero de imagen";
								break;
							case 13:
								textoerror =  "ERROR: Está activa la imagen del elemento 05 pero no se ha elegido un fichero de imagen";
								break;
							case 14:
								textoerror =  "ERROR: Está activa la imagen del elemento 06 pero no se ha elegido un fichero de imagen";
								break;
							case 15:
								textoerror =  "ERROR: Está activo el estímulo negativo pero no se ha elegido un fichero de audio";
								break;
							case 16:
								textoerror =  "ERROR: No se ha seleccionado fichero de ayuda";
								break;
							case 17:
								textoerror =  "ERROR: Está activa la descripción de la actividad pero no se ha elegido un fichero de audio";
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
		
		bSonido01.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido01
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
		
		bSonido02.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido02
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
		
		bSonido03.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido03
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
		
		bSonido04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido04
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
					Log.i("Crear Actividad", "Error al pulsar bCancion04: " + e.toString());
				}
			}
		});
		
		bSonido05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido05
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
					Log.i("Crear Actividad", "Error al pulsar bCancion05: " + e.toString());
				}
			}
		});
		
		bSonido06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bSonido06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 6;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bCancion06: " + e.toString());
				}
			}
		});
		
		bEstimuloPositivo.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEstimuloPositivo
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 7;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bEstimuloPositivo: " + e.toString());
				}
			}
		});
		
		bEstimuloNegativo.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEstimuloNegativo
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 8;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bEstimuloNegativo: " + e.toString());
				}
			}
		});
		
		bImagen01.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen01
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 9;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen01: " + e.toString());
				}
			}
		});
		
		bImagen02.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen02
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 10;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen02: " + e.toString());
				}
			}
		});
		
		bImagen03.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen03
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 11;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen03: " + e.toString());
				}
			}
		});
		
		bImagen04.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen04
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 12;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen04: " + e.toString());
				}
			}
		});
		
		bImagen05.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen05
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 13;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen05: " + e.toString());
				}
			}
		});
		
		bImagen06.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bImagen06
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					ficheroelegir = 14;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_IMAGEN);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bImagen06: " + e.toString());
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
					ficheroelegir = 15;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bAyuda: " + e.toString());
				}
			}
		});	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crear_perfil_sonidos, menu);
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
					rutacancion04 = file.getPath();
					break;
				case 5:
					rutacancion05 = file.getPath();
					break;
				case 6:
					rutacancion06 = file.getPath();
					break;
				case 7:
					rutasestimulospositivos.add(file.getPath());
					tCantidadEstimulosPositivos.setText("Cantidad: " + rutasestimulospositivos.size() + " refuerzos");
					break;
				case 8:
					rutaestimulonegativo = file.getPath();
					break;
				case 9:
					rutaimagen01 = file.getPath();
					break;
				case 10:
					rutaimagen02 = file.getPath();
					break;
				case 11:
					rutaimagen03 = file.getPath();
					break;
				case 12:
					rutaimagen04 = file.getPath();
					break;
				case 13:
					rutaimagen05 = file.getPath();
					break;
				case 14:
					rutaimagen06 = file.getPath();
					break;
				case 15:
					rutaayuda = file.getPath();
					break;
				case 16:
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
	 * 				2.-	Está activo el sonido del elemento 01 pero no se ha elegido un fichero de audio
	 * 				3.- Está activo el sonido del elemento 02 pero no se ha elegido un fichero de audio
	 * 				4.- Está activo el sonido del elemento 03 pero no se ha elegido un fichero de audio
	 * 				5.- Está activo el sonido del elemento 04 pero no se ha elegido un fichero de audio
	 * 				6.- La actividad a crear ya existe
	 * 				7.-	Está activo el sonido del elemento 05 pero no se ha elegido un fichero de audio
	 * 				8.-	Está activo el sonido del elemento 06 pero no se ha elegido un fichero de audio
	 * 				9.-	Está activa la imagen del elemento 01 pero no se ha elegido un fichero de imagen
	 * 				10.- Está activa la imagen del elemento 02 pero no se ha elegido un fichero de imagen
	 * 				11.- Está activa la imagen del elemento 03 pero no se ha elegido un fichero de imagen
	 * 				12.- Está activa la imagen del elemento 04 pero no se ha elegido un fichero de imagen
	 * 				13.- Está activa la imagen del elemento 05 pero no se ha elegido un fichero de imagen
	 * 				14.- Está activa la imagen del elemento 06 pero no se ha elegido un fichero de imagen
	 * 				15.- Está activo el estímulo negativo pero no se ha elegido un fichero de audio
	 * 				16.- No se ha seleccionado fichero de ayuda
	 * 				15.- Está activa la descripción de la actividad pero no se ha elegido un fichero de audio
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
				if( estaactivacancion01 && rutacancion01.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
					error = 2;
				else
					if( estaactivacancion02 && rutacancion02.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
						error = 3;
					else
						if( estaactivacancion03 && rutacancion03.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
							error = 4;
						else
							if( estaactivacancion04 && rutacancion04.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
								error = 5;
							else
								if( estaactivacancion05 && rutacancion05.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
									error = 7;
								else
									if( estaactivacancion06 && rutacancion06.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
										error = 8;
									else
										if( estaactivaimagen01 && rutaimagen01.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
											error = 9;
										else
											if( estaactivaimagen02 && rutaimagen02.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
												error = 10;
											else
												if( estaactivaimagen03 && rutaimagen03.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
													error = 11;
												else
													if( estaactivaimagen04 && rutaimagen04.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
														error = 12;
													else
														if( estaactivaimagen05 && rutaimagen05.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
															error = 13;
														else
															if( estaactivaimagen06 && rutaimagen06.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) == 0 )
																error = 14;
															else
																if( estaactivoestimulonegativo && rutaestimulonegativo.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
																	error = 15;
																else
																	if( rutaayuda.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
																		error = 16;
																	else
																		if( activadescripcion && rutadescripcion.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) == 0 )
																			error = 17;
		return error;
	}

	/**
	 * Muestra la imagen deseada en un mensajito
	 */
	private void mostrarImagenToolTip() 
	{		
		ToolTip previsualizarimagen = new ToolTip();
		//previsualizarimagen.withText("Jaja,no puedes cancelar");
		previsualizarimagen.withAnimationType(ToolTip.ANIMATIONTYPE_FROMLEFT);
		previsualizarimagen.withColor(getResources().getColor(R.color.amarillo));
		previsualizarimagen.withShadow(Boolean.TRUE);

		switch( imagenprevisualizar )
		{
			case 1:
				if( cbActivoImagen01.isChecked() )
				{
					if( rutaimagen01.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
					{
						// cambiar imagen a la imagen 01
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen01);
						tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen01);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				}
				break;
			case 2:
				if( rutaimagen02.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
				{
					// cambiar imagen a la imagen 02
					//previsualizarimagen.withImage(R.drawable.musical_note);
					previsualizarimagen.withImage(rutaimagen02);
					tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen02);
				}
				else
					UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				break;
			case 3:
				if( rutaimagen03.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
				{
					// cambiar imagen a la imagen 03
					//previsualizarimagen.withImage(R.drawable.musical_note);
					previsualizarimagen.withImage(rutaimagen03);
					tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen03);
				}
				else
					UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				break;
			case 4:
				if( rutaimagen04.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
				{
					// cambiar imagen a la imagen 04
					//previsualizarimagen.withImage(R.drawable.musical_note);
					previsualizarimagen.withImage(rutaimagen04);
					tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen04);
				}
				else
					UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				break;
			case 5:
				if( rutaimagen05.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
				{
					// cambiar imagen a la imagen 05
					//previsualizarimagen.withImage(R.drawable.musical_note);
					previsualizarimagen.withImage(rutaimagen05);
					tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen05);
				}
				else
					UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				break;
			case 6:
				if( rutaimagen06.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_imagen_seleccionada)) != 0 )
				{
					// cambiar imagen a la imagen 06
					//previsualizarimagen.withImage(R.drawable.musical_note);
					previsualizarimagen.withImage(rutaimagen06);
					tooltipview = layout_tooltip_crear.showToolTipForView(previsualizarimagen, ibVerImagen06);
				}
				else
					UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna imagen");
				break;
		}
		
		tooltipview.setOnToolTipViewClickedListener(CrearActividad.this);
    }
	
	@Override
	/**
	 * Este método se ejecutará cuando se pulse el tooltip
	 */
    public void onToolTipViewClicked(ToolTipView toolTipView) 
	{
		// Oculto el tooltip
        if (tooltipview == toolTipView) 
        {
        	//mRedToolTipView.remove();
        	tooltipview = null;
        } 
    }
	
	/**
	 * Toma una foto de la cámara
	 */
	public void tomarFoto()
	{
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) 
		{
			UtilidadesVarias.mostrarToastText(actividadactual, "El dispositivo no posee cámara");
		}
		else
		{
			String rutaimagen = Environment.getExternalStorageDirectory() + "/descubre_sonidos_" + edNombreActividad.getText().toString();
			
			switch( ficheroelegir )
			{
				case 9:
					rutaimagen += "_imagen01.jpg";
					rutaimagen01 = rutaimagen;
					break;
				case 10:
					rutaimagen += "_imagen02.jpg";
					rutaimagen02 = rutaimagen;
					break;
				case 11:
					rutaimagen += "_imagen03.jpg";
					rutaimagen03 = rutaimagen;
					break;
				case 12:
					rutaimagen += "_imagen04.jpg";
					rutaimagen04 = rutaimagen;
					break;
				case 13:
					rutaimagen += "_imagen05.jpg";
					rutaimagen05 = rutaimagen;
					break;
				case 14:
					rutaimagen += "_imagen06.jpg";
					rutaimagen06 = rutaimagen;
					break;
			}
			
			File foto = new File( rutaimagen );
			try 
			{
				foto.createNewFile();
				Uri uri = Uri.fromFile( foto );
				// Abre la cámara para tomar la foto
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// Guarda la imagen
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				// Vuelve a la actividad
				startActivityForResult(cameraIntent, 0);
			} 
			catch (IOException ex) 
			{              
				Log.i("CrearActividad", "ERROR: " + ex.toString());
			}   
		}
	}
	
}

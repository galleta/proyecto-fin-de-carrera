/**
 * @author francis
 * Esta clase nos permitirá modificar un perfil para la aplicación de debajo de un botón
 */

package hacer_musica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import sonido.Sonido;
import supertooltip.ToolTip;
import supertooltip.ToolTipRelativeLayout;
import supertooltip.ToolTipView;

import cajon_desastre.UtilidadesVarias;

import com.example.debajodeunboton.R;


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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import ar.com.daidalos.afiledialog.FileChooserDialog;

public class Modificar_actividad extends Activity implements ToolTipView.OnToolTipViewClickedListener 
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
	Activity actividadactual = this;
	private final String FILTRO_AUDIO = ".*mp3|.*wav";
	private final String FILTRO_IMAGEN = ".*jpg|.*jpeg";
	private TextView tCantidadEstimulosPositivosModif;
	private String actividadseleccionada;
	private EditText edOrdenElemento01Modif, edOrdenElemento02Modif, edOrdenElemento03Modif, edOrdenElemento04Modif, edOrdenElemento05Modif, edOrdenElemento06Modif;
	private ConfiguracionActividad configuracion;
	private CheckBox cbActivoSonido01Modif, cbActivoSonido02Modif, cbActivoSonido03Modif, cbActivoSonido04Modif, cbActivoSonido05Modif, cbActivoSonido06Modif;
	private CheckBox cbEstimuloNegativoModif, cbActivoImagen01Modif, cbActivoImagen02Modif, cbActivoImagen03Modif, cbActivoImagen04Modif, cbActivoImagen05Modif, cbActivoImagen06Modif;
	
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
	private ImageButton ibVerImagen01Modif, ibVerImagen02Modif, ibVerImagen03Modif, ibVerImagen04Modif, ibVerImagen05Modif, ibVerImagen06Modif;
	private CheckBox cbActivoDescripcionModif;
	
	private int imagenprevisualizar = 0;
	private ToolTipView tooltipview;
	private ToolTipRelativeLayout layout_tooltip_modificar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_crear_perfil_debajo_boton.xml.
		 */
		setContentView(R.layout.activity_modificar_actividad);
		
		// Obtengo los recursos de la actividad
		layout_tooltip_modificar = (ToolTipRelativeLayout) findViewById(R.id.layout_tooltip_modificar);
		final Button bSonido01Modif = (Button)findViewById(R.id.bSonido01Modif);
		final Button bSonido02Modif = (Button)findViewById(R.id.bSonido02Modif);
		final Button bSonido03Modif = (Button)findViewById(R.id.bSonido03Modif);
		final Button bSonido04Modif = (Button)findViewById(R.id.bSonido04Modif);
		final Button bSonido05Modif = (Button)findViewById(R.id.bSonido05Modif);
		final Button bSonido06Modif = (Button)findViewById(R.id.bSonido06Modif);
		final Button bImagen01Modif = (Button)findViewById(R.id.bImagen01Modif);
		final Button bImagen02Modif = (Button)findViewById(R.id.bImagen02Modif);
		final Button bImagen03Modif = (Button)findViewById(R.id.bImagen03Modif);
		final Button bImagen04Modif = (Button)findViewById(R.id.bImagen04Modif);
		final Button bImagen05Modif = (Button)findViewById(R.id.bImagen05Modif);
		final Button bImagen06Modif = (Button)findViewById(R.id.bImagen06Modif);
		final Button bAyudaModif = (Button)findViewById(R.id.bAyudaModif);
		final Button bEstimuloPositivoModif = (Button)findViewById(R.id.bEstimuloPositivoModif);
		final Button bEstimuloNegativoModif = (Button)findViewById(R.id.bEstimuloNegativoModif);
		final Button bAceptarNuevoPerfilModif = (Button)findViewById(R.id.bAceptarNuevoPerfilModif);
		final Button bCancelarNuevoPerfilModif = (Button)findViewById(R.id.bCancelarNuevoPerfilModif);
		tCantidadEstimulosPositivosModif = (TextView)findViewById(R.id.tCantidadEstimulosPositivosModif);
		cbActivoSonido01Modif = (CheckBox)findViewById(R.id.cbActivoSonido01Modif);
		cbActivoSonido02Modif = (CheckBox)findViewById(R.id.cbActivoSonido02Modif);
		cbActivoSonido03Modif = (CheckBox)findViewById(R.id.cbActivoSonido03Modif);
		cbActivoSonido04Modif = (CheckBox)findViewById(R.id.cbActivoSonido04Modif);
		cbActivoSonido05Modif = (CheckBox)findViewById(R.id.cbActivoSonido05Modif);
		cbActivoSonido06Modif = (CheckBox)findViewById(R.id.cbActivoSonido06Modif);
		cbActivoImagen01Modif = (CheckBox)findViewById(R.id.cbActivoImagen01Modif);
		cbActivoImagen02Modif = (CheckBox)findViewById(R.id.cbActivoImagen02Modif);
		cbActivoImagen03Modif = (CheckBox)findViewById(R.id.cbActivoImagen03Modif);
		cbActivoImagen04Modif = (CheckBox)findViewById(R.id.cbActivoImagen04Modif);
		cbActivoImagen05Modif = (CheckBox)findViewById(R.id.cbActivoImagen05Modif);
		cbActivoImagen06Modif = (CheckBox)findViewById(R.id.cbActivoImagen06Modif);
		cbEstimuloNegativoModif = (CheckBox)findViewById(R.id.cbEstimuloNegativoModif);
		edOrdenElemento01Modif = (EditText)findViewById(R.id.edOrdenElemento01Modif);
		edOrdenElemento02Modif = (EditText)findViewById(R.id.edOrdenElemento02Modif);
		edOrdenElemento03Modif = (EditText)findViewById(R.id.edOrdenElemento03Modif);
		edOrdenElemento04Modif = (EditText)findViewById(R.id.edOrdenElemento04Modif);
		edOrdenElemento05Modif = (EditText)findViewById(R.id.edOrdenElemento05Modif);
		edOrdenElemento06Modif = (EditText)findViewById(R.id.edOrdenElemento06Modif);
		final Spinner spNombreActividadModif = (Spinner)findViewById(R.id.spNombreActividadModif);
		
		final ImageButton ibHacerFoto01Modif = (ImageButton)findViewById(R.id.ibHacerFoto01Modif);
		final ImageButton ibHacerFoto02Modif = (ImageButton)findViewById(R.id.ibHacerFoto02Modif);
		final ImageButton ibHacerFoto03Modif = (ImageButton)findViewById(R.id.ibHacerFoto03Modif);
		final ImageButton ibHacerFoto04Modif = (ImageButton)findViewById(R.id.ibHacerFoto04Modif);
		final ImageButton ibHacerFoto05Modif = (ImageButton)findViewById(R.id.ibHacerFoto05Modif);
		final ImageButton ibHacerFoto06Modif = (ImageButton)findViewById(R.id.ibHacerFoto06Modif);
		
		cbActivoDescripcionModif = (CheckBox)findViewById(R.id.cbActivoDescripcionModif);
		final Button bDescripcionModif = (Button)findViewById(R.id.bDescripcionModif);
		final ImageButton ibGrabar01Modif = (ImageButton)findViewById(R.id.ibGrabar01Modif);
		final ImageButton ibEscuchar01Modif = (ImageButton)findViewById(R.id.ibEscuchar01Modif);
		final ImageButton ibGrabar02Modif = (ImageButton)findViewById(R.id.ibGrabar02Modif);
		final ImageButton ibEscuchar02Modif = (ImageButton)findViewById(R.id.ibEscuchar02Modif);
		final ImageButton ibGrabar03Modif = (ImageButton)findViewById(R.id.ibGrabar03Modif);
		final ImageButton ibEscuchar03Modif = (ImageButton)findViewById(R.id.ibEscuchar03Modif);
		final ImageButton ibGrabar04Modif = (ImageButton)findViewById(R.id.ibGrabar04Modif);
		final ImageButton ibEscuchar04Modif = (ImageButton)findViewById(R.id.ibEscuchar04Modif);
		final ImageButton ibGrabar05Modif = (ImageButton)findViewById(R.id.ibGrabar05Modif);
		final ImageButton ibEscuchar05Modif = (ImageButton)findViewById(R.id.ibEscuchar05Modif);
		final ImageButton ibGrabar06Modif = (ImageButton)findViewById(R.id.ibGrabar06Modif);
		final ImageButton ibEscuchar06Modif = (ImageButton)findViewById(R.id.ibEscuchar06Modif);
		final ImageButton ibGrabarAyudaModif = (ImageButton)findViewById(R.id.ibGrabarAyudaModif);
		final ImageButton ibEscucharAyudaModif = (ImageButton)findViewById(R.id.ibEscucharAyudaModif);
		final ImageButton ibGrabarDescripcionModif = (ImageButton)findViewById(R.id.ibGrabarDescripcionModif);
		final ImageButton ibEscucharDescripcionModif = (ImageButton)findViewById(R.id.ibEscucharDescripcionModif);
		ibVerImagen01Modif = (ImageButton)findViewById(R.id.ibVerImagen01Modif);
		ibVerImagen02Modif = (ImageButton)findViewById(R.id.ibVerImagen02Modif);
		ibVerImagen03Modif = (ImageButton)findViewById(R.id.ibVerImagen03Modif);
		ibVerImagen04Modif = (ImageButton)findViewById(R.id.ibVerImagen04Modif);
		ibVerImagen05Modif = (ImageButton)findViewById(R.id.ibVerImagen05Modif);
		ibVerImagen06Modif = (ImageButton)findViewById(R.id.ibVerImagen06Modif);
		
		actividadseleccionada = UtilidadesActividades.mostrarConfiguracionesActividades(actividadactual, spNombreActividadModif);
		configuracion = new ConfiguracionActividad(actividadactual);
		configuracion.obtener(actividadseleccionada);
		
		actualizarDatosPantalla();
		
		// Doy utilidad a los spinners
		
		spNombreActividadModif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			/**
			 * Será llamado cada vez que se seleccione una opción en el spinner
			 */
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) 
			{
				try
				{
					actividadseleccionada = (String) spNombreActividadModif.getItemAtPosition(position);
					configuracion.obtener(actividadseleccionada);
					
					actualizarDatosPantalla();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al elegir un elemento del spinner de las actividades: " + e.toString());
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
		
		cbActivoSonido01Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion01 = cbActivoSonido01Modif.isChecked();
					if( !estaactivacancion01 )
					{
						edOrdenElemento01Modif.setText("0");
						edOrdenElemento01Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento01Modif.setText("1");
						edOrdenElemento01Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido01: " + e.toString());
				}
			}
		});
		
		cbActivoSonido02Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion02 = cbActivoSonido02Modif.isChecked();
					if( !estaactivacancion02 )
					{
						edOrdenElemento02Modif.setText("0");
						edOrdenElemento02Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento02Modif.setText("2");
						edOrdenElemento02Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido02: " + e.toString());
				}
			}
		});
		
		cbActivoSonido03Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion03 = cbActivoSonido03Modif.isChecked();
					if( !estaactivacancion03 )
					{
						edOrdenElemento03Modif.setText("0");
						edOrdenElemento03Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento03Modif.setText("3");
						edOrdenElemento03Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido03: " + e.toString());
				}
			}
		});
		
		cbActivoSonido04Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion04 = cbActivoSonido04Modif.isChecked();
					if( !estaactivacancion04 )
					{
						edOrdenElemento04Modif.setText("0");
						edOrdenElemento04Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento04Modif.setText("4");
						edOrdenElemento04Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido04: " + e.toString());
				}
			}
		});
		
		cbActivoSonido05Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion05 = cbActivoSonido05Modif.isChecked();
					if( !estaactivacancion05 )
					{
						edOrdenElemento05Modif.setText("0");
						edOrdenElemento05Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento05Modif.setText("5");
						edOrdenElemento05Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido05: " + e.toString());
				}
			}
		});
		
		cbActivoSonido06Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivacancion06 = cbActivoSonido06Modif.isChecked();
					if( !estaactivacancion06 )
					{
						edOrdenElemento06Modif.setText("0");
						edOrdenElemento06Modif.setEnabled(Boolean.FALSE);
					}
					else
					{
						edOrdenElemento06Modif.setText("6");
						edOrdenElemento06Modif.setEnabled(Boolean.TRUE);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoSonido06: " + e.toString());
				}
			}
		});
		
		cbActivoImagen01Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen01 = cbActivoImagen01Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen01: " + e.toString());
				}
			}
		});
		
		cbActivoImagen02Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen02 = cbActivoImagen02Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen02: " + e.toString());
				}
			}
		});
		
		cbActivoImagen03Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen03 = cbActivoImagen03Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen03: " + e.toString());
				}
			}
		});
		
		cbActivoImagen04Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen04 = cbActivoImagen04Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen04: " + e.toString());
				}
			}
		});
		
		cbActivoImagen05Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen05 = cbActivoImagen05Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen05: " + e.toString());
				}
			}
		});
		
		cbActivoImagen06Modif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivaimagen06 = cbActivoImagen06Modif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbActivoImagen06: " + e.toString());
				}
			}
		});
		
		cbEstimuloNegativoModif.setOnClickListener(new CheckBox.OnClickListener()
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
					estaactivoestimulonegativo = cbEstimuloNegativoModif.isChecked();
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar cbEstimuloNegativo: " + e.toString());
				}
			}
		});
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bCancelarNuevoPerfilModif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar cancelar: " + e.toString());
				}
			}
		});
		
		ibHacerFoto01Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto01Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 9;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto01Modif: " + e.toString());
				}
			}
		});
		
		ibHacerFoto02Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto02Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 10;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto02Modif: " + e.toString());
				}
			}
		});
		
		ibHacerFoto03Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto03Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 11;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto03Modif: " + e.toString());
				}
			}
		});
		
		ibHacerFoto04Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto04Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 12;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto04Modif: " + e.toString());
				}
			}
		});
		
		ibHacerFoto05Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto05Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 13;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto05Modif: " + e.toString());
				}
			}
		});
		
		ibHacerFoto06Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibHacerFoto06Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						ficheroelegir = 14;
						tomarFoto();
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibHacerFoto06Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar01Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar01Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando01 )
						{
							rutacancion01 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion01.wav";
							s1 = new Sonido(actividadactual, "Cancion 01");
							s1.comenzarGrabacion(rutacancion01);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar01Modif.setImageBitmap(bmp);
							grabando01 = Boolean.TRUE;
						}
						else
						{
							s1.finalizarGrabacion();
							s1.liberar();
							s1 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar01Modif.setImageBitmap(bmp);
							grabando01 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar01Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar01Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar01Modif
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
							ibEscuchar01Modif.setImageBitmap(bmp);
							escuchando01 = Boolean.TRUE;
							s1 = new Sonido(actividadactual, "Cancion 01");
							s1.cargar(rutacancion01, Boolean.FALSE);
							s1.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar01Modif.setImageBitmap(bmp);
							escuchando01 = Boolean.FALSE;
							s1.stop();
							s1.liberar();
							s1 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar01Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar02Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar02Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando02 )
						{
							rutacancion02 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion02.wav";
							
							s2 = new Sonido(actividadactual, "Cancion 02");
							s2.comenzarGrabacion(rutacancion02);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar02Modif.setImageBitmap(bmp);
							grabando02 = Boolean.TRUE;
						}
						else
						{
							s2.finalizarGrabacion();
							s2.liberar();
							s2 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar02Modif.setImageBitmap(bmp);
							grabando02 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar02Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar02Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar02Modif
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
							ibEscuchar02Modif.setImageBitmap(bmp);
							escuchando02 = Boolean.TRUE;
							s2 = new Sonido(actividadactual, "Cancion 02");
							s2.cargar(rutacancion02, Boolean.FALSE);
							s2.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar02Modif.setImageBitmap(bmp);
							escuchando02 = Boolean.FALSE;
							s2.stop();
							s2.liberar();
							s2 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar02Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar03Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar03Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando03 )
						{
							rutacancion03 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion03.wav";
							
							s3 = new Sonido(actividadactual, "Cancion 03");
							s3.comenzarGrabacion(rutacancion03);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar03Modif.setImageBitmap(bmp);
							grabando03 = Boolean.TRUE;
						}
						else
						{
							s3.finalizarGrabacion();
							s3.liberar();
							s3 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar03Modif.setImageBitmap(bmp);
							grabando03 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar03Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar03Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar03Modif
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
							ibEscuchar03Modif.setImageBitmap(bmp);
							escuchando03 = Boolean.TRUE;
							s3 = new Sonido(actividadactual, "Cancion 03");
							s3.cargar(rutacancion03, Boolean.FALSE);
							s3.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar03Modif.setImageBitmap(bmp);
							escuchando03 = Boolean.FALSE;
							s3.stop();
							s3.liberar();
							s3 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar03Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar04Modif.setOnClickListener(new View.OnClickListener() 
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
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando04 )
						{
							rutacancion04 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion04.wav";
							
							s4 = new Sonido(actividadactual, "Cancion 04");
							s4.comenzarGrabacion(rutacancion04);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar04Modif.setImageBitmap(bmp);
							grabando04 = Boolean.TRUE;
						}
						else
						{
							s4.finalizarGrabacion();
							s4.liberar();
							s4 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar04Modif.setImageBitmap(bmp);
							grabando04 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar04Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar04Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar04Modif
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
							ibEscuchar04Modif.setImageBitmap(bmp);
							escuchando04 = Boolean.TRUE;
							s4 = new Sonido(actividadactual, "Cancion 04");
							s4.cargar(rutacancion04, Boolean.FALSE);
							s4.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar04Modif.setImageBitmap(bmp);
							escuchando04 = Boolean.FALSE;
							s4.stop();
							s4.liberar();
							s4 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar04Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar05Modif.setOnClickListener(new View.OnClickListener() 
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
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando05 )
						{
							rutacancion05 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion05.wav";
							
							s5 = new Sonido(actividadactual, "Cancion 05");
							s5.comenzarGrabacion(rutacancion05);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar05Modif.setImageBitmap(bmp);
							grabando05 = Boolean.TRUE;
						}
						else
						{
							s5.finalizarGrabacion();
							s5.liberar();
							s5 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar05Modif.setImageBitmap(bmp);
							grabando05 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar05Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar05Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar05Modif
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
							ibEscuchar05Modif.setImageBitmap(bmp);
							escuchando05 = Boolean.TRUE;
							s5 = new Sonido(actividadactual, "Cancion 05");
							s5.cargar(rutacancion05, Boolean.FALSE);
							s5.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar05Modif.setImageBitmap(bmp);
							escuchando05 = Boolean.FALSE;
							s5.stop();
							s5.liberar();
							s5 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar05Modif: " + e.toString());
				}
			}
		});
		
		ibGrabar06Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabar06Modif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabando06 )
						{
							rutacancion06 = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion06.wav";
							
							s6 = new Sonido(actividadactual, "Cancion 06");
							s6.comenzarGrabacion(rutacancion06);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabar06Modif.setImageBitmap(bmp);
							grabando06 = Boolean.TRUE;
						}
						else
						{
							s6.finalizarGrabacion();
							s6.liberar();
							s6 = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabar06Modif.setImageBitmap(bmp);
							grabando06 = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabar06Modif: " + e.toString());
				}
			}
		});
		
		ibEscuchar06Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscuchar06Modif
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
							ibEscuchar06Modif.setImageBitmap(bmp);
							escuchando06 = Boolean.TRUE;
							s6 = new Sonido(actividadactual, "Cancion 06");
							s6.cargar(rutacancion06, Boolean.FALSE);
							s6.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscuchar06Modif.setImageBitmap(bmp);
							escuchando06 = Boolean.FALSE;
							s6.stop();
							s6.liberar();
							s6 = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscuchar06Modif: " + e.toString());
				}
			}
		});
		
		ibGrabarAyudaModif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibGrabarAyudaModif
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabandoayuda )
						{
							rutaayuda = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_cancion06.wav";
							
							sayuda = new Sonido(actividadactual, "Ayuda");
							sayuda.comenzarGrabacion(rutaayuda);
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono_grabando);
							ibGrabarAyudaModif.setImageBitmap(bmp);
							grabandoayuda = Boolean.TRUE;
						}
						else
						{
							sayuda.finalizarGrabacion();
							sayuda.liberar();
							sayuda = null;
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.microfono);
							ibGrabarAyudaModif.setImageBitmap(bmp);
							grabandoayuda = Boolean.FALSE;
							UtilidadesVarias.mostrarToastText(actividadactual, "El sonido se ha grabado correctamente");
						}
					}
					else
					{
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibGrabarAyudaModif: " + e.toString());
				}
			}
		});
		
		ibEscucharAyudaModif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibEscucharAyudaModif
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
							ibEscucharAyudaModif.setImageBitmap(bmp);
							escuchandoayuda = Boolean.TRUE;
							sayuda = new Sonido(actividadactual, "Ayuda");
							sayuda.cargar(rutaayuda, Boolean.FALSE);
							sayuda.reproducir();
						}
						else
						{
							Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.musical_note);
							ibEscucharAyudaModif.setImageBitmap(bmp);
							escuchandoayuda = Boolean.FALSE;
							sayuda.stop();
							sayuda.liberar();
							sayuda = null;
						}
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar ibEscucharAyudaModif: " + e.toString());
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
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						if( !grabandodescripcion )
						{
							rutadescripcion = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada + "_descripcion.wav";
							
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
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
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
		
		ibVerImagen01Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen01Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen01Modif: " + e.toString());
				}
			}
		});
		
		ibVerImagen02Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen02Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen02Modif: " + e.toString());
				}
			}
		});
		
		ibVerImagen03Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen03Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen03Modif: " + e.toString());
				}
			}
		});
		
		ibVerImagen04Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen04Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen04Modif: " + e.toString());
				}
			}
		});
		
		ibVerImagen05Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen05Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen05Modif: " + e.toString());
				}
			}
		});
		
		ibVerImagen06Modif.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón ibVerImagen06Modif
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
					Log.i("Crear Actividad", "Error al pulsar ibVerImagen06Modif: " + e.toString());
				}
			}
		});
		
		bAceptarNuevoPerfilModif.setOnClickListener(new View.OnClickListener() 
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
					int error = comprobarCorrectitudDatosModificarActividad();
					if( error == 0 )
					{						
						int elementostotales = 0, elemento;
						ArrayList<Integer> ordenaux = new ArrayList<Integer>();
						elemento = Integer.valueOf(edOrdenElemento01Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						elemento = Integer.valueOf(edOrdenElemento02Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						elemento = Integer.valueOf(edOrdenElemento03Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						elemento = Integer.valueOf(edOrdenElemento04Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						elemento = Integer.valueOf(edOrdenElemento05Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						elemento = Integer.valueOf(edOrdenElemento06Modif.getText().toString());
						if( elemento != 0 )
							elementostotales++;
						ordenaux.add(elemento);
						
						/*String eleccion = "";
						for(int i = 0; i < ordenaux.size(); i++)
						{
							eleccion += Integer.toString(ordenaux.get(i));
							eleccion += ", ";
						}
						Log.i("CrearActividad", "Ordenes: " + eleccion);*/
						
						ArrayList<Integer> ordenfinal = new ArrayList<Integer>();
						for(int i = 1; i <= elementostotales; i++)
						{
							ordenfinal.add(1 + ordenaux.indexOf(i));
							//Log.i("CrearActividad", "busco " + i + " -> " + ordenaux.indexOf(i));
						}
						for(int i = ordenfinal.size(); i < 6; i++)
							ordenfinal.add(0);
						
						if( !UtilidadesVarias.tieneRepetidos(ordenaux) )
						{
							configuracion.actualizar( getBaseContext(), actividadseleccionada,
									rutacancion01, rutacancion02, rutacancion03, rutacancion04, rutacancion05, rutacancion06,
									rutaimagen01, rutaimagen02, rutaimagen03, rutaimagen04, rutaimagen05, rutaimagen06,
									estaactivacancion01, estaactivacancion02, estaactivacancion03, estaactivacancion04, estaactivacancion05, estaactivacancion06,
									estaactivaimagen01, estaactivaimagen02, estaactivaimagen03, estaactivaimagen04, estaactivaimagen05, estaactivaimagen06,
									rutaayuda, rutasestimulospositivos, 
									rutaestimulonegativo, estaactivoestimulonegativo,
									ordenfinal, rutadescripcion, activadescripcion);
							configuracion.almacenar();
							UtilidadesVarias.mostrarToastText(actividadactual, "Actividad " + configuracion.getNombreactividad() + " modificada con éxito.");
						}
						else
						{
							UtilidadesVarias.mostrarToastText(Modificar_actividad.this, "ERROR: los órdenes de reproducción son incorrectos");
						}
					}
					else
					{						
						String textoerror = "";
						switch(error)
						{
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
								textoerror = "ERROR: Los ordenes de reproducción son incorrectos";
								break;
							case 18:
								textoerror =  "ERROR: Está activa la descripción de la actividad pero no se ha elegido un fichero de audio";
								break;
						}
						UtilidadesVarias.mostrarToastText(Modificar_actividad.this, textoerror);
					}
				}
				catch (Exception e) 
				{
					Log.i("Modificar Actividad", "Error al pulsar aceptar: " + e.toString());
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
					ficheroelegir = 16;
					UtilidadesVarias.mostrarFileChooser(actividadactual, onFileSelectedListener, FILTRO_AUDIO);
				}
				catch (Exception e) 
				{
					Log.i("Crear Actividad", "Error al pulsar bDescripcionModif: " + e.toString());
				}
			}
		});
		
		bSonido01Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion01: " + e.toString());
				}
			}
		});
		
		bSonido02Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion02: " + e.toString());
				}
			}
		});
		
		bSonido03Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion03: " + e.toString());
				}
			}
		});
		
		bSonido04Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion04: " + e.toString());
				}
			}
		});
		
		bSonido05Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion05: " + e.toString());
				}
			}
		});
		
		bSonido06Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bCancion06: " + e.toString());
				}
			}
		});
		
		bEstimuloPositivoModif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bEstimuloPositivo: " + e.toString());
				}
			}
		});
		
		bEstimuloNegativoModif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bEstimuloNegativo: " + e.toString());
				}
			}
		});
		
		bImagen01Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen01: " + e.toString());
				}
			}
		});
		
		bImagen02Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen02: " + e.toString());
				}
			}
		});
		
		bImagen03Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen03: " + e.toString());
				}
			}
		});
		
		bImagen04Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen04: " + e.toString());
				}
			}
		});
		
		bImagen05Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen05: " + e.toString());
				}
			}
		});
		
		bImagen06Modif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bImagen06: " + e.toString());
				}
			}
		});	
		
		bAyudaModif.setOnClickListener(new View.OnClickListener() 
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
					Log.i("Modificar Actividad", "Error al pulsar bAyuda: " + e.toString());
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crear_perfil_debajo_boton, menu);
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
					tCantidadEstimulosPositivosModif.setText("" + rutasestimulospositivos.size() + " refuerzos");
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
			}
			
			source.hide();
			UtilidadesVarias.mostrarToastText(Modificar_actividad.this, "Seleccionado: " + file.getName());
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
	 * 				2.-	Está activo el sonido del elemento 01 pero no se ha elegido un fichero de audio
	 * 				3.- Está activo el sonido del elemento 02 pero no se ha elegido un fichero de audio
	 * 				4.- Está activo el sonido del elemento 03 pero no se ha elegido un fichero de audio
	 * 				5.- Está activo el sonido del elemento 04 pero no se ha elegido un fichero de audio
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
	 * 				17.- Los órdenes de reproducción de los trozos de la canción están mal
	 */
	private int comprobarCorrectitudDatosModificarActividad()
	{
		int error = 0;
		
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
																		if( (Integer.valueOf(edOrdenElemento01Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento01Modif.getText().toString()) < 0 ||
																				Integer.valueOf(edOrdenElemento02Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento02Modif.getText().toString()) < 0 ||
																				Integer.valueOf(edOrdenElemento03Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento03Modif.getText().toString()) < 0 ||
																				Integer.valueOf(edOrdenElemento04Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento04Modif.getText().toString()) < 0 ||
																				Integer.valueOf(edOrdenElemento05Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento05Modif.getText().toString()) < 0 ||
																				Integer.valueOf(edOrdenElemento06Modif.getText().toString()) > 6 || Integer.valueOf(edOrdenElemento06Modif.getText().toString()) < 0) )
																			error = 17;
		
		return error;
	}

	/**
	 * Actualiza los datos de la pantalla con los de una actividad elegida
	 */
	private void actualizarDatosPantalla()
	{
		rutadescripcion = configuracion.getRutaDescripcion();
		rutacancion01 = configuracion.getRutacancion01();
		rutacancion02 = configuracion.getRutacancion02();
		rutacancion03 = configuracion.getRutacancion03();
		rutacancion04 = configuracion.getRutacancion04();
		rutacancion05 = configuracion.getRutacancion05();
		rutacancion06 = configuracion.getRutacancion06();
		rutaimagen01 = configuracion.getRutaImagen01();
		rutaimagen02 = configuracion.getRutaImagen02();
		rutaimagen03 = configuracion.getRutaImagen03();
		rutaimagen04 = configuracion.getRutaImagen04();
		rutaimagen05 = configuracion.getRutaImagen05();
		rutaimagen06 = configuracion.getRutaImagen06();
		rutaayuda = configuracion.getRutaAyuda();
		rutaestimulonegativo = configuracion.getRutaEstimuloNegativo();
		rutasestimulospositivos = configuracion.getRutasEstimulosPositivos();
		activadescripcion = configuracion.getActivaDescripcion();
		estaactivacancion01 = configuracion.getEstaActivaCancion01();
		estaactivacancion02 = configuracion.getEstaActivaCancion02();
		estaactivacancion03 = configuracion.getEstaActivaCancion03();
		estaactivacancion04 = configuracion.getEstaActivaCancion04();
		estaactivacancion05 = configuracion.getEstaActivaCancion05();
		estaactivacancion06 = configuracion.getEstaActivaCancion06();
		estaactivaimagen01 = configuracion.getEstaActivaImagen01();
		estaactivaimagen02 = configuracion.getEstaActivaImagen02();
		estaactivaimagen03 = configuracion.getEstaActivaImagen03();
		estaactivaimagen04 = configuracion.getEstaActivaImagen04();
		estaactivaimagen05 = configuracion.getEstaActivaImagen05();
		estaactivaimagen06 = configuracion.getEstaActivaImagen06();
		estaactivoestimulonegativo = configuracion.estaActivoEstimuloNegativo();
		cbActivoSonido01Modif.setChecked(estaactivacancion01);
		if( estaactivacancion01 )
			edOrdenElemento01Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento01Modif.setEnabled(Boolean.FALSE);
		cbActivoSonido02Modif.setChecked(estaactivacancion02);
		if( estaactivacancion02 )
			edOrdenElemento02Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento02Modif.setEnabled(Boolean.FALSE);
		cbActivoSonido03Modif.setChecked(estaactivacancion03);
		if( estaactivacancion03 )
			edOrdenElemento03Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento03Modif.setEnabled(Boolean.FALSE);
		cbActivoSonido04Modif.setChecked(estaactivacancion04);
		if( estaactivacancion04 )
			edOrdenElemento04Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento04Modif.setEnabled(Boolean.FALSE);
		cbActivoSonido05Modif.setChecked(estaactivacancion05);
		if( estaactivacancion05 )
			edOrdenElemento05Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento05Modif.setEnabled(Boolean.FALSE);
		cbActivoSonido06Modif.setChecked(estaactivacancion06);
		if( estaactivacancion06 )
			edOrdenElemento06Modif.setEnabled(Boolean.TRUE);
		else
			edOrdenElemento06Modif.setEnabled(Boolean.FALSE);
		cbActivoImagen01Modif.setChecked(estaactivaimagen01);
		cbActivoImagen02Modif.setChecked(estaactivaimagen02);
		cbActivoImagen03Modif.setChecked(estaactivaimagen03);
		cbActivoImagen04Modif.setChecked(estaactivaimagen04);
		cbActivoImagen05Modif.setChecked(estaactivaimagen05);
		cbActivoImagen06Modif.setChecked(estaactivaimagen06);
		cbEstimuloNegativoModif.setChecked(estaactivoestimulonegativo);
		rutasestimulospositivos = configuracion.getRutasEstimulosPositivos();
		tCantidadEstimulosPositivosModif.setText("" + rutasestimulospositivos.size() + " refuerzos");
		
		if( configuracion.getOrdenReproduccion().size() > 0 )
		{
			ArrayList<Integer> ordenes = configuracion.getOrdenReproduccion();
			int posicionelemento = ordenes.indexOf(1);
			if( posicionelemento == -1 )
				edOrdenElemento01Modif.setText("0");
			else
				edOrdenElemento01Modif.setText(String.valueOf(posicionelemento+1));
			
			posicionelemento = ordenes.indexOf(2);
			if( posicionelemento == -1 )
				edOrdenElemento02Modif.setText("0");
			else
				edOrdenElemento02Modif.setText(String.valueOf(posicionelemento+1));
			
			posicionelemento = ordenes.indexOf(3);
			if( posicionelemento == -1 )
				edOrdenElemento03Modif.setText("0");
			else
				edOrdenElemento03Modif.setText(String.valueOf(posicionelemento+1));
			
			posicionelemento = ordenes.indexOf(4);
			if( posicionelemento == -1 )
				edOrdenElemento04Modif.setText("0");
			else
				edOrdenElemento04Modif.setText(String.valueOf(posicionelemento+1));
			
			posicionelemento = ordenes.indexOf(5);
			if( posicionelemento == -1 )
				edOrdenElemento05Modif.setText("0");
			else
				edOrdenElemento05Modif.setText(String.valueOf(posicionelemento+1));
			
			posicionelemento = ordenes.indexOf(6);
			if( posicionelemento == -1 )
				edOrdenElemento06Modif.setText("0");
			else
				edOrdenElemento06Modif.setText(String.valueOf(posicionelemento+1));
		}
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
				if( cbActivoImagen01Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 01
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen01);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen01Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
			case 2:
				if( cbActivoImagen02Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 02
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen02);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen02Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
			case 3:
				if( cbActivoImagen03Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 03
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen03);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen03Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
			case 4:
				if( cbActivoImagen04Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 04
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen04);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen04Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
			case 5:
				if( cbActivoImagen05Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 05
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen05);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen05Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
			case 6:
				if( cbActivoImagen06Modif.isChecked() )
				{
					if( actividadseleccionada.compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0 )
					{
						// cambiar imagen a la imagen 06
						//previsualizarimagen.withImage(R.drawable.musical_note);
						previsualizarimagen.withImage(rutaimagen06);
						tooltipview = layout_tooltip_modificar.showToolTipForView(previsualizarimagen, ibVerImagen06Modif);
					}
					else
						UtilidadesVarias.mostrarToastText(actividadactual, "No se ha seleccionado ninguna actividad");
				}
				break;
		}
		
		tooltipview.setOnToolTipViewClickedListener(Modificar_actividad.this);
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
			String rutaimagen = Environment.getExternalStorageDirectory() + "/hacer_musica_" + actividadseleccionada;
			
			switch( ficheroelegir )
			{
				case 9:
					rutaimagen += "_imagen01Modif.jpg";
					rutaimagen01 = rutaimagen;
					break;
				case 10:
					rutaimagen += "_imagen02Modif.jpg";
					rutaimagen02 = rutaimagen;
					break;
				case 11:
					rutaimagen += "_imagen03Modif.jpg";
					rutaimagen03 = rutaimagen;
					break;
				case 12:
					rutaimagen += "_imagen04Modif.jpg";
					rutaimagen04 = rutaimagen;
					break;
				case 13:
					rutaimagen += "_imagen05Modif.jpg";
					rutaimagen05 = rutaimagen;
					break;
				case 14:
					rutaimagen += "_imagen06Modif.jpg";
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

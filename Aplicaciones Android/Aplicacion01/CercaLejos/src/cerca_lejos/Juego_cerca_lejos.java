/**
 * @author francis
 * Esta clase es la del juego cerca lejos
 */

package cerca_lejos;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sonido.Sonido;

import cajon_desastre.Cronometro;
import cajon_desastre.DatosPrograma;
import cajon_desastre.DetectorGestos;
import cajon_desastre.DetectorGestosAyuda;
import cajon_desastre.DetectorGestosCerrar;
import cajon_desastre.TipoEstimulo;
import cajon_desastre.UtilidadesVarias;

import com.example.cerca_lejos.R;

import actividad.ConfiguracionActividad;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint("SimpleDateFormat")
public class Juego_cerca_lejos extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private ConfiguracionActividad configuracionactividad;
	private Sonido cancion01, cancion02, cancion03, ayuda;
	private Sonido descripcion;
	private TipoEstimulo estimulo;
	private Cronometro totaljugado, jugadofila01, jugadofila02, jugadofila03;
	private boolean cronometro01 = Boolean.FALSE, cronometro02 = Boolean.FALSE, cronometro03 = Boolean.FALSE;
	private int desviosfila01, desviosfila02, desviosfila03;
	private DatosPrograma datos;
	private GestureDetector detectorcalificaciones;
	private GestureDetector detectorayuda;
	private GestureDetector detectorcerrar;
	private boolean todocorrecto = Boolean.TRUE;
	private long lastClick;
	private final long TIEMPO_ESPERA_CLICK = 1000;	// 1000 milisegundos = 1 segundo
	//private int idseekbaranterior = -1, idseekbaractual = -1;
	//private int idseekbar1, idseekbar2, idseekbar3;
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_juego_cerca_lejos.xml.
		 */
		setContentView(R.layout.activity_juego_cerca_lejos);
		
		// Obtengo los recursos de la actividad
		
		final RelativeLayout layoutCalificaciones = (RelativeLayout)findViewById(R.id.layoutCalificaciones);
		final RelativeLayout layoutAyuda = (RelativeLayout)findViewById(R.id.layoutAyuda);
		final RelativeLayout layoutCerrar = (RelativeLayout)findViewById(R.id.layoutCerrar);
		final SeekBar sbCancion1 = (SeekBar)findViewById(R.id.sbCancion1);
		final SeekBar sbCancion2 = (SeekBar)findViewById(R.id.sbCancion2);
		final SeekBar sbCancion3 = (SeekBar)findViewById(R.id.sbCancion3);
		
		// Cambio el color de los seekBar
		
		sbCancion1.setProgressDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
		sbCancion2.setProgressDrawable(new ColorDrawable(Color.rgb(0, 255, 0)));
		sbCancion3.setProgressDrawable(new ColorDrawable(Color.rgb(0, 0, 255)));
		
		/*idseekbar1 = sbCancion1.getId();
		idseekbar2 = sbCancion2.getId();
		idseekbar3 = sbCancion3.getId();*/
		
		// Obtengo el bundle de la pantalla anterior para ir pasando los datos
		
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
		
		configuracionactividad = new ConfiguracionActividad(getBaseContext());
		
		if( datos.obtenerString("ACTIVIDAD").compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_actividad)) != 0
			&& datos.obtenerString("NINIO").compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_ninio)) != 0 )
			configuracionactividad.obtener(datos.obtenerString("ACTIVIDAD"));
		else
			todocorrecto = Boolean.FALSE;
		
		// Doy la funcionalidad para cerrar la pantalla
		
		detectorcerrar = new GestureDetector(new DetectorGestosCerrar(this));
		layoutCerrar.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent evento) 
			{
				detectorcerrar.onTouchEvent(evento);
				return Boolean.TRUE;
			}
        });
		
		if( todocorrecto )
		{
			lastClick = System.currentTimeMillis();
			
			// Me creo los detectores de gestos
			detectorcalificaciones = new GestureDetector(new DetectorGestos(this));
			detectorayuda = new GestureDetector(new DetectorGestosAyuda(this));
			
			desviosfila01 = 0;
			desviosfila02 = 0;
			desviosfila03 = 0;
			
			totaljugado = new Cronometro("Cronometro total jugado");
			totaljugado.iniciar();
			jugadofila01 = new Cronometro("Cronometro jugado fila 01");
			jugadofila02 = new Cronometro("Cronometro jugado fila 02");
			jugadofila03 = new Cronometro("Cronometro jugado fila 03");
			
			if( configuracionactividad.getTipoestimulo().compareTo("Volumen") == 0 )
				estimulo = TipoEstimulo.VOLUMEN;
			else
				if( configuracionactividad.getTipoestimulo().compareTo("Timbre") == 0 )
					estimulo = TipoEstimulo.TIMBRE;
				else
					estimulo = TipoEstimulo.FRECUENCIA;
			
			/*
			 * Creo los sonidos
			 */
			
			if( configuracionactividad.getActivaDescripcion() )
			{
				descripcion = new Sonido(this, "Descripción actividad");
				descripcion.cargar(configuracionactividad.getRutaDescripcion(), Boolean.FALSE);
				descripcion.reproducir();
			}
			if( configuracionactividad.getEstaActivaCancion01() )
			{
				cancion01 = new Sonido(this, "Canción 01");
				cancion01.cargar(configuracionactividad.getRutacancion01(), Boolean.FALSE);
				sbCancion1.setMax(cancion01.getDuracion());
			}
			if( configuracionactividad.getEstaActiva02() )
			{
				cancion02 = new Sonido(this, "Canción 02");
				cancion02.cargar(configuracionactividad.getRutacancion02(), Boolean.FALSE);
				sbCancion2.setMax(cancion02.getDuracion());
			}
			if( configuracionactividad.getEstaActivaCancion03() )
			{
				cancion03 = new Sonido(this, "Canción 03");
				cancion03.cargar(configuracionactividad.getRutacancion03(), Boolean.FALSE);
				sbCancion3.setMax(cancion03.getDuracion());
			}
			
			if( configuracionactividad.getRutaAyuda().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
			{
				ayuda = new Sonido(this, "Ayuda");
				ayuda.cargar(configuracionactividad.getRutaAyuda(), Boolean.FALSE);
			}
		}
		
		// Sobrecargo el listener TouchListener del layout de las calificaciones para obtener 
		// los gestos que se hacen sobre él
		
		/*
		 * Modifico el TouchListener del layout que he creado en la actividad
		 * para que sea el que tiene DetectorGestos (onFling()) 
		 */
		layoutCalificaciones.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent evento) 
			{
				if( todocorrecto )
					detectorcalificaciones.onTouchEvent(evento);
				return Boolean.TRUE;
			}
        });
		
		layoutAyuda.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent evento) 
			{
				if( todocorrecto )
					detectorayuda.onTouchEvent(evento);
				return Boolean.TRUE;
			}
        });
		
		// Sobrecargo los listeners setOnSeekBarChangeListener de las seekbar
		// para detectar cuando cambien su valor
		
		sbCancion1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{			
			@Override
			/**
			 * Este método se ejecutará cuando se deje de pulsar el seekBar
			 * @param seekBar seekBar
			 */
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				
			}
 
			@Override
			/**
			 * Este método se ejecutará cuando se empiece a pulsar el seekBar
			 * @param seekBar seekBar
			 */
			public void onStartTrackingTouch(SeekBar seekBar) 
			{  
				
			}
 
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				if( todocorrecto && configuracionactividad.getEstaActivaCancion01() )
				{
					cancion01.cambiarVolumen((int) ((100 * progress) / cancion01.getDuracion()));
				}
				/*
		    	 * Evito que se lancen eventos en menos de TIEMPO_ESPERA_CLICK milisegundos
		    	 * Con esto consigo que la canción vaya sonando sin demasiados cortes mientras 
		    	 * el niño va deslizando el dedo
		    	 */
				if( todocorrecto && configuracionactividad.getEstaActivaCancion01() && (System.currentTimeMillis() - lastClick > TIEMPO_ESPERA_CLICK))
				{
					//comprobarDesvio(idseekbar1);
					lastClick = System.currentTimeMillis();
					// Si no he iniciado todavía el crónometro 01, lo inicio
					if( !cronometro01 )
					{
						cronometro01 = Boolean.TRUE;
						jugadofila01.iniciar();
					}
					if(jugadofila01.estaPausado())
					{
						jugadofila01.pausar();
					}
					
					// Pauso los cronómetros de las filas 02 y 03
					if( cronometro02 )
						jugadofila02.pausar();
					if( cronometro03 )
						jugadofila03.pausar();
					
					try
					{
						if( estimulo == TipoEstimulo.VOLUMEN )
						{
							/*
							 * Realizo la funcionalidad del volumen
							 */
							if( configuracionactividad.getEstaActivaCancion01() )
							{
								/*
								 * Pauso los otros sonidos
								 */
								if( cancion02 != null && cancion02.seEstaReproduciendo() )
									cancion02.pausa();
								if( cancion03 != null && cancion03.seEstaReproduciendo() )
									cancion03.pausa();
								
								if( cancion01 != null )
								{
									//cancion01.cambiarVolumen((int) ((100 * progress) / cancion01.getDuracion()));
									cancion01.reproducirDesde(progress);
								}
							}
						}
						else
						{
							UtilidadesVarias.mostrarToastText(Juego_cerca_lejos.this, "Estímulo " + estimulo.toString() + " aún no implementado");
						}
					}
					catch (Exception e) 
					{
						Log.i("Juego_cerca_lejos", "Error al mover sbCancion01: " + e.toString());
					}
				}
			}
		});
		
		sbCancion2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				
			}
 
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) 
			{  
				
			}
 
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				/*
		    	 * Evito que se lancen eventos en menos de TIEMPO_ESPERA_CLICK milisegundos
		    	 * Con esto consigo que la canción vaya sonando sin demasiados cortes mientras 
		    	 * el niño va deslizando el dedo
		    	 */
				if( todocorrecto && configuracionactividad.getEstaActiva02() && (System.currentTimeMillis() - lastClick > TIEMPO_ESPERA_CLICK))
				{
					//comprobarDesvio(idseekbar2);
					lastClick = System.currentTimeMillis();
					// Si no he iniciado todavía el crónometro 02, lo inicio
					if( !cronometro02 )
					{
						cronometro02 = Boolean.TRUE;
						jugadofila02.iniciar();
					}
					if(jugadofila02.estaPausado())
					{
						jugadofila02.pausar();
					}
					
					// Pauso los cronómetros de las filas 01 y 03
					if( cronometro01 )
						jugadofila01.pausar();
					if( cronometro03 )
						jugadofila03.pausar();
					
					try
					{
						if( estimulo == TipoEstimulo.VOLUMEN )
						{
							/*
							 * Realizo la funcionalidad del volumen
							 */
							if( configuracionactividad.getEstaActiva02() )
							{
								/*
								 * Pauso los otros sonidos
								 */
								if( cancion01 != null && cancion01.seEstaReproduciendo() )
									cancion01.pausa();
								if( cancion03 != null && cancion03.seEstaReproduciendo() )
									cancion03.pausa();
								
								if( cancion02 != null )
								{
									cancion02.cambiarVolumen((int) ((100 * progress) / cancion02.getDuracion()));
									cancion02.reproducirDesde(progress);
								}
							}
						}
						else
						{
							UtilidadesVarias.mostrarToastText(Juego_cerca_lejos.this, "Estímulo " + estimulo.toString() + " aún no implementado");
						}
					}
					catch (Exception e) 
					{
						Log.i("Juego_cerca_lejos", "Error al mover sbCancion02: " + e.toString());
					}
				}
			}
		});
		
		sbCancion3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				
			}
 
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) 
			{  
				
			}
 
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				/*
		    	 * Evito que se lancen eventos en menos de TIEMPO_ESPERA_CLICK milisegundos
		    	 * Con esto consigo que la canción vaya sonando sin demasiados cortes mientras 
		    	 * el niño va deslizando el dedo
		    	 */
				if( todocorrecto && configuracionactividad.getEstaActivaCancion03() && (System.currentTimeMillis() - lastClick > TIEMPO_ESPERA_CLICK))
				{
					//comprobarDesvio(idseekbar3);
					lastClick = System.currentTimeMillis();
					// Si no he iniciado todavía el crónometro 03, lo inicio
					if( !cronometro03 )
					{
						cronometro03 = Boolean.TRUE;
						jugadofila03.iniciar();
					}
					if(jugadofila03.estaPausado())
					{
						jugadofila03.pausar();
					}
					
					// Pauso los cronómetros de las filas 01 y 02
					if( cronometro01 )
						jugadofila01.pausar();
					if( cronometro02 )
						jugadofila02.pausar();
					
					try
					{
						if( estimulo == TipoEstimulo.VOLUMEN )
						{
							/*
							 * Realizo la funcionalidad del volumen
							 */
							if( configuracionactividad.getEstaActivaCancion03() )
							{
								/*
								 * Pauso los otros sonidos
								 */
								if( cancion02 != null && cancion02.seEstaReproduciendo() )
									cancion02.pausa();
								if( cancion01 != null && cancion01.seEstaReproduciendo() )
									cancion01.pausa();
								
								if( cancion03 != null )
								{
									cancion03.cambiarVolumen((int) ((100 * progress) / cancion03.getDuracion()));
									cancion03.reproducirDesde(progress);
								}
							}
						}
						else
						{
							UtilidadesVarias.mostrarToastText(Juego_cerca_lejos.this, "Estímulo " + estimulo.toString() + " aún no implementado");
						}
					}
					catch (Exception e) 
					{
						Log.i("Juego_cerca_lejos", "Error al mover sbCancion03: " + e.toString());
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego_cerca_lejos, menu);
		return true;
	}
	
	/*
	 * Funciones hechas por mi
	 */
	
	/**
	 * Aumenta en uno los desvíos en la primera canción
	 */
	public void aumentarDesviosFila01()
	{
		desviosfila01++;
	}

	/**
	 * Aumenta en uno los desvíos en la segunda canción
	 */
	public void aumentarDesviosFila02()
	{
		desviosfila02++;
	}
	
	/**
	 * Aumenta en uno los desvíos en la tercera canción
	 */
	public void aumentarDesviosFila03()
	{
		desviosfila03++;
	}
	
	/**
	 * Reproduce la ayuda del juego
	 */
	public void reproducirAyuda()
	{
		try
		{
			if( todocorrecto && ayuda != null )
				ayuda.reproducir();
		}
		catch (Exception e) 
		{
			Log.i("Juego_cerca_lejos", "Error al pulsar Ayuda: " + e.toString());
		}
	}
	
	/**
	 * Termina la partida, libera recursos y guarda los datos de la evaluación
	 */
	public void terminarPartida()
	{
		try
		{
			if( todocorrecto )
			{
				totaljugado.pausar();
				if( jugadofila01 != null )
					jugadofila01.pausar();
				if( jugadofila02 != null )
					jugadofila02.pausar();
				if( jugadofila03 != null )
					jugadofila03.pausar();
				
				// Obtengo la fecha actual
				Calendar c = Calendar.getInstance();
				SimpleDateFormat formatofechahora = new SimpleDateFormat("dd-MM-yyy_HH:mm:ss");
				String fechastring = formatofechahora.format(c.getTime());
				datos.insertarString("ULTIMAFECHA", fechastring);
				
				/*
				 * Guardo los datos de la actividad
				 */
				
				int horasfila01 = 0, minutosfila01 = 0, segundosfila01 = 0;
				int horasfila02 = 0, minutosfila02 = 0, segundosfila02 = 0;
				int horasfila03 = 0, minutosfila03 = 0, segundosfila03 = 0;
				
				if( jugadofila01 != null )
				{
					horasfila01 = jugadofila01.getHoras();
					minutosfila01 = jugadofila01.getMinutos();
					segundosfila01 = jugadofila01.getSegundos();
				}
				if( jugadofila02 != null )
				{
					horasfila02 = jugadofila02.getHoras();
					minutosfila02 = jugadofila02.getMinutos();
					segundosfila02 = jugadofila02.getSegundos();
				}
				if( jugadofila03 != null )
				{
					horasfila03 = jugadofila03.getHoras();
					minutosfila03 = jugadofila03.getMinutos();
					segundosfila03 = jugadofila03.getSegundos();
				}
				
				datos.insertarInt("HFILA01", horasfila01);
				datos.insertarInt("MFILA01", minutosfila01);
				datos.insertarInt("SFILA01", segundosfila01);
				datos.insertarInt("HFILA02", horasfila02);
				datos.insertarInt("MFILA02", minutosfila02);
				datos.insertarInt("SFILA02", segundosfila02);
				datos.insertarInt("HFILA03", horasfila03);
				datos.insertarInt("MFILA03", minutosfila03);
				datos.insertarInt("SFILA03", segundosfila03);
				datos.insertarInt("HFILATOTAL", totaljugado.getHoras());
				datos.insertarInt("MFILATOTAL", totaljugado.getMinutos());
				datos.insertarInt("SFILATOTAL", totaljugado.getSegundos());
				datos.insertarInt("DESVIOS01", desviosfila01);
				datos.insertarInt("DESVIOS02", desviosfila02);
				datos.insertarInt("DESVIOS03", desviosfila03);
				
				/*
				 * Libero los recursos
				 */
				
				if( configuracionactividad.getEstaActivaCancion01() )
				{
					cancion01.liberar();
					cancion01 = null;
				}
				if( configuracionactividad.getEstaActiva02() )
				{
					cancion02.liberar();
					cancion02 = null;
				}
				if( configuracionactividad.getEstaActivaCancion03() )
				{
					cancion03.liberar();
					cancion03 = null;
				}
				if( configuracionactividad.getRutaAyuda().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
				{
					ayuda.liberar();
					ayuda = null;
				}
				if( configuracionactividad.getActivaDescripcion() )
				{
					descripcion.liberar();
					descripcion = null;
				}
				if( totaljugado != null )
					totaljugado.liberar();
				if( jugadofila01 != null )
					jugadofila01.liberar();
				if( jugadofila02 != null )
					jugadofila02.liberar();
				if( jugadofila03 != null )
					jugadofila03.liberar();
			}
			
			/*
			 * Salgo de la actividad
			 */
			
			finish();
			
			if( todocorrecto )
			{
				Intent intent = new Intent(Juego_cerca_lejos.this, ComentarActividad.class);
				startActivity(intent);
			}
		}
		catch (Exception e) 
		{
			Log.i("Juego_cerca_lejos", "Error al pulsar Cerrar: " + e.toString());
		}
	}
	
	/**
	 * Comprueba si ha habido un desvío es un seekBar
	 * @param idseekbaract ID del seekbar para comprobar si ha habido desvío
	 */
	/*public void comprobarDesvio(int idseekbaract)
	{
		idseekbaranterior = idseekbaractual;
		idseekbaractual = idseekbaract;
		
		if( idseekbaranterior != idseekbaractual )
		{
			if( idseekbaranterior == idseekbar1 )
			{
				aumentarDesviosFila01();
			}
			else
			{
				if( idseekbaranterior == idseekbar2 )
				{
					aumentarDesviosFila02();
				}
				else
				{
					if( idseekbaranterior == idseekbar3 )
					{
						aumentarDesviosFila03();
					}
				}
			}
		}
	}*/
}

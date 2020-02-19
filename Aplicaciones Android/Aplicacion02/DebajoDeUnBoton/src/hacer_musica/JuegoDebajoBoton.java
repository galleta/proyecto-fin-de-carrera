/**
 * @author francis
 * Esta clase es la del juego debajo de un botón
 */

package hacer_musica;

import imagen.Imagen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sonido.Sonido;

import cajon_desastre.Cronometro;
import cajon_desastre.DatosPrograma;
import cajon_desastre.DetectorGestos;
import cajon_desastre.DetectorGestosAyuda;
import cajon_desastre.DetectorGestosCerrar;
import cajon_desastre.ModoJuego;
import cajon_desastre.UtilidadesVarias;

import com.example.debajodeunboton.R;

import evaluacion.EvaluacionNinioActividad;

import actividad.ConfiguracionActividad;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class JuegoDebajoBoton extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private DatosPrograma datos;
	private GestureDetector detectorcalificaciones;
	private GestureDetector detectorayuda;
	private GestureDetector detectorcerrar;
	private int aciertos, fallos;
	private ConfiguracionActividad configuracionactividad;
	private Sonido descripcion, cancion01, cancion02, cancion03, cancion04, cancion05, cancion06, ayuda, estimulonegativo;
	private Sonido beep01, beep02, beep06, beep07;
	private Imagen imagen01, imagen02, imagen03, imagen04, imagen05, imagen06;
	private ArrayList<Sonido> estimulospositivos;
	private Cronometro totaljugado;
	private boolean todocorrecto = Boolean.TRUE;
	private ArrayList<Integer> ordenreproduccion, eleccionninio;
	private ModoJuego modo;
	private int ESTADO_ACTIVIDAD, vecesescuchado = 0;
	private boolean resultadoactividad;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_juego_debajo_boton.xml.
		 */
		setContentView(R.layout.activity_juego_debajo_boton);
		
		// Obtengo los recursos de la actividad
		
		final RelativeLayout layoutCalificaciones = (RelativeLayout)findViewById(R.id.layoutCalificaciones);
		final RelativeLayout layoutAyuda = (RelativeLayout)findViewById(R.id.layoutAyuda);
		final RelativeLayout layoutCerrar = (RelativeLayout)findViewById(R.id.layoutCerrar);
		final ImageButton ibElemento01 = (ImageButton)findViewById(R.id.ibElemento01);
		final ImageButton ibElemento02 = (ImageButton)findViewById(R.id.ibElemento02);
		final ImageButton ibElemento03 = (ImageButton)findViewById(R.id.ibElemento03);
		final ImageButton ibElemento04 = (ImageButton)findViewById(R.id.ibElemento04);
		final ImageButton ibElemento05 = (ImageButton)findViewById(R.id.ibElemento05);
		final ImageButton ibElemento06 = (ImageButton)findViewById(R.id.ibElemento06);
		
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
		ordenreproduccion = new ArrayList<Integer>();
		eleccionninio = new ArrayList<Integer>();
		
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
			// Me creo los detectores de gestos
			detectorcalificaciones = new GestureDetector(new DetectorGestos(this));
			detectorayuda = new GestureDetector(new DetectorGestosAyuda(this));
					
			aciertos = 0;
			fallos = 0;
			
			if( datos.obtenerString("MODOJUEGO").compareTo("Recordar") == 0 )
				modo = ModoJuego.RECORDAR;
			else
				modo = ModoJuego.COMPONER;
					
			/*
			 * Obtengo el orden de reproduccion
			 */
			ordenreproduccion = configuracionactividad.getOrdenReproduccion();
			
			/*
			 * Creo los sonidos
			 */
			
			beep01 = new Sonido(getBaseContext(), "beep01");
			beep01.cargar(R.raw.beep01, Boolean.FALSE);
			beep02 = new Sonido(getBaseContext(), "beep02");
			beep02.cargar(R.raw.beep02, Boolean.FALSE);
			beep06 = new Sonido(getBaseContext(), "beep06");
			beep06.cargar(R.raw.beep06, Boolean.FALSE);
			beep07 = new Sonido(getBaseContext(), "beep07");
			beep07.cargar(R.raw.beep07, Boolean.FALSE);
			
			if( configuracionactividad.getActivaDescripcion() )
			{
				descripcion = new Sonido(this, "Descripción actividad");
				descripcion.cargar(configuracionactividad.getRutaDescripcion(), Boolean.FALSE);
				descripcion.reproducir();
			}
			if( configuracionactividad.getEstaActivaCancion01() )
			{
				cancion01 = new Sonido(getBaseContext(), "Sonido elemento 01");
				cancion01.cargar(configuracionactividad.getRutacancion01(), Boolean.FALSE);
			}
			if( configuracionactividad.getEstaActivaCancion02() )
			{
				cancion02 = new Sonido(getBaseContext(), "Sonido elemento 02");
				cancion02.cargar(configuracionactividad.getRutacancion02(), Boolean.FALSE);
			}
			if( configuracionactividad.getEstaActivaCancion03() )
			{
				cancion03 = new Sonido(getBaseContext(), "Sonido elemento 03");
				cancion03.cargar(configuracionactividad.getRutacancion03(), Boolean.FALSE);
			}
			if( configuracionactividad.getEstaActivaCancion04() )
			{
				cancion04 = new Sonido(getBaseContext(), "Sonido elemento 04");
				cancion04.cargar(configuracionactividad.getRutacancion04(), Boolean.FALSE);
			}
			if( configuracionactividad.getEstaActivaCancion05() )
			{
				cancion05 = new Sonido(getBaseContext(), "Sonido elemento 05");
				cancion05.cargar(configuracionactividad.getRutacancion05(), Boolean.FALSE);
			}
			if( configuracionactividad.getEstaActivaCancion06() )
			{
				cancion06 = new Sonido(getBaseContext(), "Sonido elemento 06");
				cancion06.cargar(configuracionactividad.getRutacancion06(), Boolean.FALSE);
			}
			if( configuracionactividad.getRutaAyuda().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
			{
				ayuda = new Sonido(getBaseContext(), "Ayuda");
				ayuda.cargar(configuracionactividad.getRutaAyuda(), Boolean.FALSE);
			}
			estimulospositivos = new ArrayList<Sonido>();
			for(int i = 0; i < configuracionactividad.getRutasEstimulosPositivos().size(); i++)
			{
				Sonido estimulopositivo = new Sonido(getBaseContext(), "Estimulo positivo " + Integer.toString(i));
				estimulopositivo.cargar(configuracionactividad.getRutasEstimulosPositivos().get(i), Boolean.FALSE);
				estimulospositivos.add(estimulopositivo);
			}
			if( configuracionactividad.estaActivoEstimuloNegativo() )
			{
				estimulonegativo = new Sonido(getBaseContext(), "Estimulo negativo");
				estimulonegativo.cargar(configuracionactividad.getRutaEstimuloNegativo(), Boolean.FALSE);
			}
			
			/*
			 * Pongo las imágenes en los botones que tengan imagen
			 */
			
			imagen01 = new Imagen(getBaseContext());
			imagen02 = new Imagen(getBaseContext());
			imagen03 = new Imagen(getBaseContext());
			imagen04 = new Imagen(getBaseContext());
			imagen05 = new Imagen(getBaseContext());
			imagen06 = new Imagen(getBaseContext());
			
			if( configuracionactividad.getEstaActivaImagen01() )
			{
				imagen01.cargar(configuracionactividad.getRutaImagen01());
				ibElemento01.setImageBitmap(imagen01.getImagen());
			}
			if( configuracionactividad.getEstaActivaImagen02() )
			{
				imagen02.cargar(configuracionactividad.getRutaImagen02());
				ibElemento02.setImageBitmap(imagen02.getImagen());
			}
			if( configuracionactividad.getEstaActivaImagen03() )
			{
				imagen03.cargar(configuracionactividad.getRutaImagen03());
				ibElemento03.setImageBitmap(imagen03.getImagen());
			}
			if( configuracionactividad.getEstaActivaImagen04() )
			{
				imagen04.cargar(configuracionactividad.getRutaImagen04());
				ibElemento04.setImageBitmap(imagen04.getImagen());
			}
			if( configuracionactividad.getEstaActivaImagen05() )
			{
				imagen05.cargar(configuracionactividad.getRutaImagen05());
				ibElemento05.setImageBitmap(imagen05.getImagen());
			}
			if( configuracionactividad.getEstaActivaImagen06() )
			{
				imagen06.cargar(configuracionactividad.getRutaImagen06());
				ibElemento06.setImageBitmap(imagen06.getImagen());
			}
			
			/*
			 * Doy funcionalidad a los botones
			 */
			
			ibElemento01.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento01
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(1);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion01() )
									{
										reproducirSonido(1);
										eleccionninio.add(1);
										Log.i("Juego", "meto el indice 1, " + eleccionninio.size());
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion01() )
									{
										reproducirSonido(1);
										eleccionninio.add(1);	
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento01: " + e.toString());
					}
				}
			});
			
			ibElemento02.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento02
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(2);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion02() )
									{
										reproducirSonido(2);
										eleccionninio.add(2);
										Log.i("Juego", "meto el indice 2, " + eleccionninio.size());
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion02() )
									{
										reproducirSonido(2);
										eleccionninio.add(2);
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento02: " + e.toString());
					}
				}
			});
			
			ibElemento03.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento03
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(3);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion03() )
									{
										reproducirSonido(3);
										eleccionninio.add(3);
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion03() )
									{
										reproducirSonido(3);
										eleccionninio.add(3);
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento03: " + e.toString());
					}
				}
			});
			
			ibElemento04.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento04
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(4);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion04() )
									{
										reproducirSonido(4);
										eleccionninio.add(4);
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion04() )
									{
										reproducirSonido(4);
										eleccionninio.add(4);
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento04: " + e.toString());
					}
				}
			});
			
			ibElemento05.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento05
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(5);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion05() )
									{
										reproducirSonido(5);
										eleccionninio.add(5);
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion05() )
									{
										reproducirSonido(5);
										eleccionninio.add(5);
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento05: " + e.toString());
					}
				}
			});
			
			ibElemento06.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				/**
				 * Acción onClick del botón ibElemento06
				 * @param evento onClick
				 */
				public void onClick(View evento)
				{
					try
					{
						switch( ESTADO_ACTIVIDAD )
						{
							case 1:
								if( getModo().compareTo("Recordar") == 0 )
									reproducirSonido(6);
								else	// Modo Componer
									if( configuracionactividad.getEstaActivaCancion06() )
									{
										reproducirSonido(6);
										eleccionninio.add(6);
									}
								break;
							case 2:
								if( getModo().compareTo("Recordar") == 0 )
									if( configuracionactividad.getEstaActivaCancion06() )
									{
										reproducirSonido(6);
										eleccionninio.add(6);
									}
								break;
						}
						
					}
					catch (Exception e) 
					{
						Log.i("JuegoSonidos", "Error al pulsar ibElemento06: " + e.toString());
					}
				}
			});
			
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
			
			/*
			 * Inicio el cronómetro del tiempo jugado
			 */
			
			totaljugado = new Cronometro("Cronometro total jugado");
			totaljugado.iniciar();
			
		}
		comenzarActividad();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego_debajo_boton, menu);
		return true;
	}
	
	/*
	 * Funciones hechas por mi
	 */
	
	/**
	 * Reproduce un estímulo positivo aleatorio
	 */
	public void reproducirEstimuloPositivo()
	{
		if( estimulospositivos != null )
			if( estimulospositivos.size() > 0 )
				estimulospositivos.get(UtilidadesVarias.aleatorioEntreDosNumeros(0, estimulospositivos.size())).reproducir();
	}
	
	/**
	 * Reproduce el estímulo negativo
	 */
	public void reproducirEstimuloNegativo()
	{
		if( configuracionactividad.estaActivoEstimuloNegativo() )
			estimulonegativo.reproducir();
	}
	
	/**
	 * Aumenta en uno los fallos
	 */
	public void aumentarFallos()
	{
		fallos++;
	}

	/**
	 * Aumenta en uno los aciertos
	 */
	public void aumentarAciertos()
	{
		aciertos++;
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
			Log.i("JuegoDebajoBoton", "Error al pulsar Ayuda: " + e.toString());
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
				
				// Obtengo la fecha actual
				Calendar c = Calendar.getInstance();
				SimpleDateFormat formatofechahora = new SimpleDateFormat("dd-MM-yyy_HH:mm:ss");
				String fechastring = formatofechahora.format(c.getTime());
				datos.insertarString("ULTIMAFECHA", fechastring);
				
				/*
				 * Guardo los datos de la actividad
				 */
				
				datos.insertarInt("HFILATOTAL", totaljugado.getHoras());
				datos.insertarInt("MFILATOTAL", totaljugado.getMinutos());
				datos.insertarInt("SFILATOTAL", totaljugado.getSegundos());
				datos.insertarInt("ACIERTOS", aciertos);
				datos.insertarInt("FALLOS", fallos);
				datos.insertarInt("TOTALORDENES", this.getComposicionNinio().size());
				for(int i = 0; i < this.getComposicionNinio().size(); i++)
				{
					String nombre = "ORDEN";
					nombre += String.valueOf(i);
					datos.insertarInt(nombre, this.getComposicionNinio().get(i));
				}
				datos.insertarInt("vecesescuchado", vecesescuchado);
				
				/*
				 * Libero los recursos
				 */
				
				if( configuracionactividad.getEstaActivaCancion01() )
				{
					cancion01.liberar();
					cancion01 = null;
				}
				if( configuracionactividad.getEstaActivaCancion02() )
				{
					cancion02.liberar();
					cancion02 = null;
				}
				if( configuracionactividad.getEstaActivaCancion03() )
				{
					cancion03.liberar();
					cancion03 = null;
				}
				if( configuracionactividad.getEstaActivaCancion04() )
				{
					cancion04.liberar();
					cancion04 = null;
				}
				if( configuracionactividad.getEstaActivaCancion05() )
				{
					cancion05.liberar();
					cancion05 = null;
				}
				if( configuracionactividad.getEstaActivaCancion06() )
				{
					cancion06.liberar();
					cancion06 = null;
				}
				if( configuracionactividad.getActivaDescripcion() )
				{
					descripcion.liberar();
					descripcion = null;
				}
				if( ayuda != null )
				{
					ayuda.liberar();
					ayuda = null; 
				}
				beep01.liberar();
				beep01 = null;
				beep02.liberar();
				beep02 = null;
				beep06.liberar();
				beep06 = null;
				beep07.liberar();
				beep07 = null;
				if( configuracionactividad.estaActivoEstimuloNegativo() )
				{
					estimulonegativo.liberar();
					estimulonegativo = null;
				}
				for(int i = 0; i < estimulospositivos.size(); i++)
				{
					estimulospositivos.get(i).liberar();
				}
				estimulospositivos.clear();
				if( totaljugado != null )
					totaljugado.liberar();
			}
			/*
			 * Salgo de la actividad
			 */
			
			finish();
			if( todocorrecto )
			{
				Intent intent = new Intent(JuegoDebajoBoton.this, ComentarActividad.class);
				startActivity(intent);
			}
		}
		catch (Exception e) 
		{
			Log.i("JuegoDebajoBoton", "Error al pulsar Cerrar: " + e.toString());
		}
	}
	
	/**
	 * Devuelve el estado en el que está la actividad
	 * @return Estado actual de la actividad
	 */
	public int getEstadoActividad()
	{
		return ESTADO_ACTIVIDAD;
	}
	
	/**
	 * Vuelve al primer estado de la actividad
	 */
	public void comenzarActividad()
	{
		ESTADO_ACTIVIDAD = 0;
		vecesescuchado = 0;
		if( beep07 != null )
			beep07.reproducir();
	}
	
	/**
	 * Aumenta en uno las veces que el niño escucha su composición
	 * (Válido únicamente para el modo Componer)
	 */
	public void aumentarVecesEscuchado()
	{
		vecesescuchado++;
	}
	
	/**
	 * Pasa al siguiente estado de la actividad
	 */
	public void pasarAlSiguienteEstadoActividad()
	{
		if( (getModo().toString().compareTo("Recordar") == 0 && ESTADO_ACTIVIDAD < 3) ||
			(getModo().toString().compareTo("Componer") == 0 && ESTADO_ACTIVIDAD < 2) )
			ESTADO_ACTIVIDAD++;
		
		switch(ESTADO_ACTIVIDAD)
		{
			case 1:
				if( beep01 != null )
					beep01.reproducir();
				break;
			case 2:
				if( beep02 != null )
					beep02.reproducir();
				break;
			case 3:
				if( beep06 != null )
					beep06.reproducir();
				break;
		}
	}
	
	/**
	 * Indica el modo de la actividad
	 * @return Modo de la actividad, Recordar / Componer
	 */
	public String getModo()
	{
		return modo.toString();
	}
	
	/**
	 * Indica el orden en el que se han de reproducir los sonidos
	 * @return Array con el orden de reproducción de los sonidos en la actividad
	 */
	public ArrayList<Integer> getOrdenReproduccion()
	{
		return this.ordenreproduccion;
	}
	
	/**
	 * Reproduce un sonido
	 * @param sonido Sonido a reproducir
	 */
	public void reproducirSonido(int sonido)
	{
		switch(sonido)
		{
			case 1:
				if( configuracionactividad.getEstaActivaCancion01() )
				{
					try 
					{
						cancion01.reproducir();
						Thread.sleep(cancion01.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			case 2:
				if( configuracionactividad.getEstaActivaCancion02() )
				{
					try 
					{
						cancion02.reproducir();
						Thread.sleep(cancion02.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			case 3:
				if( configuracionactividad.getEstaActivaCancion03() )
				{
					try 
					{
						cancion03.reproducir();
						Thread.sleep(cancion03.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			case 4:
				if( configuracionactividad.getEstaActivaCancion04() )
				{
					try 
					{
						cancion04.reproducir();
						Thread.sleep(cancion04.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			case 5:
				if( configuracionactividad.getEstaActivaCancion05() )
				{
					try 
					{
						cancion05.reproducir();
						Thread.sleep(cancion05.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			case 6:
				if( configuracionactividad.getEstaActivaCancion06() )
				{
					try 
					{
						cancion06.reproducir();
						Thread.sleep(cancion06.getDuracion());
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				break;
		}
	}
	
	public void resetearEleccionNinio()
	{
		eleccionninio.clear();
	}
	
	/**
	 * Comprueba si el orden de los trozos elegido por el niño es el correcto
	 * @return Verdadero si el orden es correcto, falso en caso contrario
	 */
	public boolean comprobarCorrectitudActividad()
	{
		boolean correcto = Boolean.TRUE;
		
		ArrayList<Integer> eleccionfinal = new ArrayList<Integer>();
		for(int i = 0; i < configuracionactividad.getOrdenReproduccion().size(); i++)
		{
			int elemento = configuracionactividad.getOrdenReproduccion().get(i);
			if( elemento != 0 )
				eleccionfinal.add(elemento);
		}
		
		/*String eleccion = "", original = "";
		for(int i = 0; i < eleccionninio.size(); i++)
		{
			eleccion += Integer.toString(eleccionninio.get(i));
			eleccion += ", ";
		}
		for(int i = 0; i < eleccionfinal.size(); i++)
		{
			original += Integer.toString(eleccionfinal.get(i));
			original += ", ";
		}
		
		Log.i("JuegoDebajoBoton", "Eleccion del niño: " + eleccion + ", elementos " + eleccionninio.size());
		Log.i("JuegoDebajoBoton", "Orden original: " + original + ", elementos " + eleccionfinal.size());*/
		
		if( !UtilidadesVarias.tieneRepetidos(eleccionninio) && (eleccionninio.size() == eleccionfinal.size()) )
		{			
			for(int i = 0; correcto && i < eleccionninio.size(); i++)
			{
				if( eleccionninio.get(i) != eleccionfinal.get(i) )
					correcto = Boolean.FALSE;
			}
		}
		else
			correcto = Boolean.FALSE;
			
		return correcto;
	}
	
	/**
	 * Indica el resultado de la actividad
	 * @param resultado Resultado, verdadero es que ha ido correctamente, falso es que no
	 */
	public void indicarResultadoActividad(boolean resultado)
	{
		resultadoactividad = resultado;
	}
	
	/**
	 * Devuelve la grabación del niño
	 * @return Grabación del niño
	 */
	public ArrayList<Integer> getComposicionNinio()
	{
		return eleccionninio;
	}

}

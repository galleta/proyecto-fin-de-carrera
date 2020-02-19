/**
 * @author francis
 * Esta clase es la del juego descubre sonidos
 */

package descubre_sonidos;

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
import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;

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

@SuppressWarnings("unused")
public class JuegoSonidos extends Activity 
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
	private Sonido cancion01, cancion02, cancion03, cancion04, cancion05, cancion06, ayuda, estimulonegativo;
	private Sonido descripcion;
	private Imagen imagen01, imagen02, imagen03, imagen04, imagen05, imagen06;
	private ArrayList<Sonido> estimulospositivos;
	private Cronometro totaljugado;
	private boolean todocorrecto = Boolean.TRUE;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_juego_sonidos.xml.
		 */
		setContentView(R.layout.activity_juego_sonidos);
				
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
						if( configuracionactividad.getEstaActivaCancion01() )
							cancion01.reproducir();
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
						if( configuracionactividad.getEstaActivaCancion02() )
							cancion02.reproducir();
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
						if( configuracionactividad.getEstaActivaCancion03() )
							cancion03.reproducir();
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
						if( configuracionactividad.getEstaActivaCancion04() )
							cancion04.reproducir();
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
						if( configuracionactividad.getEstaActivaCancion05() )
							cancion05.reproducir();
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
						if( configuracionactividad.getEstaActivaCancion06() )
							cancion06.reproducir();
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
					if( todocorrecto && ayuda != null )
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
		
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego_sonidos, menu);
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
			Log.i("JuegoSonidos", "Error al pulsar Ayuda: " + e.toString());
		}
	}
	
	/**
	 * Termina la partida, libera recursos y guarda los datos de la evaluación
	 */
	@SuppressLint("SimpleDateFormat")
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
				if( configuracionactividad.getRutaAyuda().compareTo(UtilidadesVarias.obtenerStringXML(getBaseContext(), R.string.no_sonido_seleccionado)) != 0 )
				{
					ayuda.liberar();
					ayuda = null;
				}
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
				if( configuracionactividad.getActivaDescripcion() )
				{
					descripcion.liberar();
					descripcion = null;
				}
				if( totaljugado != null )
				{
					totaljugado.liberar();
				}
				/*imagen01.liberar();
				imagen02.liberar();
				imagen03.liberar();
				imagen04.liberar();
				imagen05.liberar();
				imagen06.liberar();*/
			}
			/*
			 * Salgo de la actividad
			 */
			
			finish();
			if( todocorrecto )
			{
				Intent intent = new Intent(JuegoSonidos.this, ComentarActividad.class);
				startActivity(intent);
			}
		}
		catch (Exception e) 
		{
			Log.i("JuegoSonidos", "Error al pulsar Cerrar: " + e.toString());
		}
	}

}

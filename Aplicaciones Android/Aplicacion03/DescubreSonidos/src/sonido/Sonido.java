/**
 * Esta clase va a representar un sonido
 * @author francis
 */

package sonido;

import java.io.File;
import java.io.IOException;

import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;

public class Sonido 
{
	/*
	 * Atributos de la clase
	 */
	
	private MediaPlayer sonido;		// Clase para reproducir sonidos
	private Context contexto;		// Contexto
	private String rutasonido;		// Ruta del sonido
	private int volumen, posicion;	// Volumen del sonido y posición por la que va (para pause)
	private String nombre;			// Nombre del fichero de sonido (para posibles errores)
	private MediaRecorder grabador;
	
	/*
	 * Variables para control de volumen
	 */
	
	private final static int INT_VOLUMEN_MAXIMO = 100;
	private final static int INT_VOLUMEN_MINIMO = 0;
	private final static float FLOAT_VOLUMEN_MAXIMO = 1;
	private final static float FLOAT_VOLUMEN_MINIMO = 0;
	
	/*
	 * Métodos de la clase
	 */
	
	/**
	 * Constructor de la clase
	 * @param contexto Contexto de la aplicación
	 * @param nombre Nombre del objeto
	 */
	public Sonido(Context contexto, String nombre)
	{
		sonido = new MediaPlayer();
		grabador = new MediaRecorder();
		this.nombre = nombre;
		this.setContexto(contexto);
		volumen = 10;
		cambiarVolumen(0);
		rutasonido = UtilidadesVarias.obtenerStringXML(contexto, R.string.no_sonido_seleccionado);
	}
	
	/**
	 * Carga el sonido
	 * @param ruta Ruta del fichero del sonido
	 * @param looping Looping
	 */
	public void cargar(String ruta, boolean looping)
	{
		rutasonido = ruta;
		sonido = MediaPlayer.create(contexto, Uri.fromFile(new File(ruta)));
		sonido.setLooping(looping);
	}

	/**
	 * Carga el sonido
	 * @param direccion Recurso android
	 * @param looping Looping
	 */
	public void cargar(int direccion, boolean looping)
	{
		/*
		 * Quitar este método, es sólo para pruebas
		 */
		rutasonido = "";
	    sonido = MediaPlayer.create(contexto, direccion);
	    sonido.setLooping(looping);
	}
	
	/**
	 * Devuelve el ID del sonido
	 * @return ID único del sonido
	 */
	@SuppressLint("NewApi")
	public int getID()
	{
		return sonido.getAudioSessionId();
	}
	
	/**
	 * Modifica el contexto de la clase
	 * @param contexto Nuevo contexto
	 */
	private void setContexto(Context contexto) 
	{
		this.contexto = contexto;
	}

	/**
	 * Devuelve el nombre del sonido
	 * @return Nombre del sonido
	 */
	public String getNombreSonido()
	{
		return nombre;
	}
	
	/**
	 * Devuelve la duración del sonido en milisegundos
	 * @return Duración del sonido
	 */
	public int getDuracion()
	{
		return sonido.getDuration();
	}
	
	/**
	 * Devuelve la ruta del sonido
	 * @return Ruta del sonido
	 */
	public String getRutaSonido() 
	{
		return rutasonido;
	}

	/**
	 * Modifica la ruta del sonido a reproducir
	 * @param rutasonido Nueva ruta del sonido
	 */
	public void setRutaSonido(String rutasonido) 
	{
		this.rutasonido = rutasonido;
	}
	
	/**
	 * Reproduce el sonido
	 */
	public void reproducir()
	{
		if(!sonido.isPlaying()) 
			sonido.start();
	}
	
	/**
	 * Pausa / Reanuda el sonido
	 */
	public void pausa()
	{
		if (sonido.isPlaying())
		{
			sonido.pause();
			posicion = getPosicionActual();
		}
		else
		{
			irAPosicion(posicion);
			sonido.start();
		}
	}
	
	/**
	 * Para el sonido
	 */
	public void stop()
	{
		sonido.stop();
	}
	
	/**
	 * Reproduce el sonido desde una posición en concreto
	 * @param posicion Posición en milisegundos para reproducir
	 */
	public void reproducirDesde(int posicion)
	{
		if( sonido.isPlaying() )
			sonido.pause();
			
		irAPosicion(posicion);
		sonido.start();
	}
	
	/**
	 * Incrementa / Decrementa el volumen del sonido
	 * @param incremento Incremento / Decremento del volumen
	 */
	public void incrementarVolumen(int incremento)
	{
	    // Incremento o decremento dependiendo de lo que me llegue
	    volumen += incremento;

	    // Garantizo que el volumen estará dentro de los límites
	    if (volumen < INT_VOLUMEN_MINIMO)
	        volumen = INT_VOLUMEN_MINIMO;
	    else 
	    	if (volumen > INT_VOLUMEN_MAXIMO)
	    		volumen = INT_VOLUMEN_MAXIMO;

	    // Convierto a float en el intervalo 0 - 1
	    float volumenfloat = 1 - ((float) Math.log(INT_VOLUMEN_MAXIMO - volumen) / (float) Math.log(INT_VOLUMEN_MAXIMO));

	    // Garantizo que el volumen float estará dentro de los límites
	    if (volumenfloat < FLOAT_VOLUMEN_MINIMO)
	        volumenfloat = FLOAT_VOLUMEN_MINIMO;
	    else 
	    	if (volumenfloat > FLOAT_VOLUMEN_MAXIMO)
	    		volumenfloat = FLOAT_VOLUMEN_MAXIMO;     

	    float sonidoderecha = volumenfloat, sonidoizquierda = volumenfloat;
	    sonido.setVolume(sonidoderecha, sonidoizquierda);
	}
	
	/**
	 * Cambia el volumen del sonido
	 * @param valor Nuevo volumen
	 */
	public void cambiarVolumen(int valor)
	{
		volumen = valor;
		
	    // Garantizo que el volumen estará dentro de los límites
	    if (volumen < INT_VOLUMEN_MINIMO)
	        volumen = INT_VOLUMEN_MINIMO;
	    else 
	    	if (volumen > INT_VOLUMEN_MAXIMO)
	    		volumen = INT_VOLUMEN_MAXIMO;

	    // Convierto a float en el intervalo 0 - 1
	    float volumenfloat = 1 - ((float) Math.log(INT_VOLUMEN_MAXIMO - volumen) / (float) Math.log(INT_VOLUMEN_MAXIMO));

	    // Garantizo que el volumen float estará dentro de los límites
	    if (volumenfloat < FLOAT_VOLUMEN_MINIMO)
	        volumenfloat = FLOAT_VOLUMEN_MINIMO;
	    else 
	    	if (volumenfloat > FLOAT_VOLUMEN_MAXIMO)
	    		volumenfloat = FLOAT_VOLUMEN_MAXIMO;     

	    float sonidoderecha = volumenfloat, sonidoizquierda = volumenfloat;
	    sonido.setVolume(sonidoderecha, sonidoizquierda);
	}
	
	/**
	 * Va a una posición determinada del sonido
	 * @param posicion Milisigundo a posicionarse
	 */
	public void irAPosicion(int posicion)
	{
		sonido.seekTo(posicion);
	}
	
	/**
	 * Devuelve la posición actual de reproducción del sonido
	 * @return Posición actual de reproducción del sonido en milisegundos
	 */
	public int getPosicionActual()
	{
		return sonido.getCurrentPosition();
	}
	
	/**
	 * Indica si el sonido se está reproduciendo o no
	 * @return True si el sonido se está reproduciendo, False en caso contrario
	 */
	public boolean seEstaReproduciendo()
	{
		return sonido.isPlaying();
	}
	
	/**
	 * Libera el recurso de sonido
	 */
	public void liberar()
	{
		sonido.stop();
		sonido.reset();
		sonido.release();
		sonido = null;
		if( grabador != null )
		{
			grabador.release();
			grabador = null;
		}
	}
	
	/**
	 * Graba un sonido del micrófono en formato wav
	 * @param ruta Ruta donde se va a guardar el fichero grabado
	 */
	@SuppressLint("InlinedApi")
	public void comenzarGrabacion(String ruta)
	{
		try 
		{
			rutasonido = ruta;
			grabador.setAudioSource(MediaRecorder.AudioSource.MIC); // Indico que quiero grabar del micrófono
			grabador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			grabador.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
			grabador.setOutputFile(ruta);
			grabador.prepare();
			grabador.start();
		} 
		catch (IllegalStateException e) 
		{
			Log.i("Sonido", "Error grabando el sonido " + nombre + ": " + e.toString());
		} 
		catch (IOException e) 
		{
			Log.i("Sonido", "Error grabando el sonido " + nombre + ": " + e.toString());
		}
	}
	
	/**
	 * Finaliza la grabación de un fichero del micrófono
	 */
	public void finalizarGrabacion()
	{
		if( grabador != null )
		{
			grabador.stop();
			grabador.release();
			grabador = null;
			this.cargar(rutasonido, Boolean.FALSE);
		}
	}
}


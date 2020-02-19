/**
 * @author francis
 * Esta clase representa un cronómetro
 */

package cajon_desastre;

import android.util.Log;

public class Cronometro extends Thread
{
	// Atributos privados de la clase
	private String nombrecronometro;		// Nombre del cronómetro
	private int segundos, minutos, horas;	// Segundos, minutos y horas que lleva activo el cronómetro
	private Boolean pausado;				// Para pausar el cronómetro
	
	/**
	 * Constructor de la clase
	 * @param nombre Nombre del cronómetro
	 */
	public Cronometro(String nombre)
	{
		segundos = 0;
		minutos = 0;
		horas = 0;
		nombrecronometro = nombre;
		pausado = Boolean.FALSE;
	}
	
	/**
	 * Reinicia el cronómetro
	 */
	public void reiniciar()
	{
		segundos = 0;
		minutos = 0;
		horas = 0;
		pausado = Boolean.FALSE;
	}
	
	/**
	 * Pausa y vuelve a reaudar el cronómetro
	 */
	public void pausar()
	{
		pausado = !pausado;
	}
	
	/**
	 * Iniciar el cronómetro
	 */
	public void iniciar()
	{
		this.start();
	}
	
	/**
	 * Para el cronómetro
	 */
	public void parar()
	{
		pausado = Boolean.TRUE;
		horas = 0;
		minutos = 0;
		segundos = 0;
	}
	
	@Override
	/**
	 * Acción del cronómetro, contar tiempo en segundo plano
	 */
	public void run() 
	{
		try 
		{
			while(Boolean.TRUE)
			{
				Thread.sleep(1000);
				if( !pausado )
				{
					segundos++;
					if(segundos == 60)
					{
						segundos = 0;
						minutos++;
					}
					if(minutos == 60)
					{
						minutos = 0;
						horas++;
					}
				}
			}
		} 
		catch (InterruptedException e) 
		{
			Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
		}
	}
	
	/**
	 * Devuelve las horas que ha estado activo el cronómetro
	 * @return Horas
	 */
	public int getHoras()
	{
		return horas;
	}
	
	/**
	 * Devuelve los minutos que ha estado activo el cronómetro
	 * @return Minutos
	 */
	public int getMinutos()
	{
		return minutos;
	}
	
	/**
	 * Devuelve los segundos que ha estado activo el cronómetro
	 * @return Segundos
	 */
	public int getSegundos()
	{
		return segundos;
	}
	
	/**
	 * Libera la hebra del cronómetro
	 */
	public void liberar()
	{
		this.interrupt();
	}
	
	/**
	 * Indica si el cronómetro está pausado
	 * @return Verdadero si el cronómetro está en pausa, falso en caso contrario
	 */
	public boolean estaPausado()
	{
		return pausado;
	}

}


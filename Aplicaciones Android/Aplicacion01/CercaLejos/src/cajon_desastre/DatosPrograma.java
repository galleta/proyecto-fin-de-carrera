/**
 * Clase para guardar los datos de la aplicación
 * @author francis
 * 
 * Esta clase implementa el método singleton
 *
 */

package cajon_desastre;

import android.os.Bundle;

public class DatosPrograma 
{
	/*
	 * Variables de la clase
	 */
	
	private static DatosPrograma instancia;
	private Bundle datos;
	
	/**
	 * Inicializa la instancia, método singleton
	 */
	public static void initInstance()
	{
		if (instancia == null)
			instancia = new DatosPrograma();
	}
	
	/**
	 * Devuelve la instancia de la clase
	 * @return Configuracion
	 */
	public static DatosPrograma getInstance()
	{
		return instancia;
	}
	
	/**
	 * Constructor, oculto por el singleton
	 */
	private DatosPrograma()
	{
		datos = new Bundle();
	}
	
	/**
	 * Devuelve los datos
	 * @return Datos completos de la aplicación
	 */
	public Bundle getDatos()
	{
		return datos;
	}
	
	/**
	 * Inserta un String en la clase
	 * @param key Identificador
	 * @param value Valor
	 */
	public void insertarString(String key, String value)
	{
		datos.putString(key, value);
	}
	
	/**
	 * Devuelve un String de los datos
	 * @param key Identificador
	 * @return Valor de ese String de los datos
	 */
	public String obtenerString(String key)
	{
		return datos.getString(key);
	}
	
	/**
	 * Inserta un int en la clase
	 * @param key Identificador
	 * @param value Valor
	 */
	public void insertarInt(String key, int value)
	{
		datos.putInt(key, value);
	}
	
	/**
	 * Devuelve un int de los datos
	 * @param key Identificador
	 * @return Valor de ese int de los datos
	 */
	public int obtenerInt(String key)
	{
		return datos.getInt(key);
	}
}

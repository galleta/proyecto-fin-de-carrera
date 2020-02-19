/**
 * @author francis
 * Esta clase representa la configuración de una actividad
 */

package actividad;


import cajon_desastre.TipoEstimulo;
import cajon_desastre.UtilidadesVarias;

import com.example.cerca_lejos.R;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracionActividad 
{
	/*
	 * Atributos de la clase
	 */
	
	private Context contexto;
	private String nombreactividad;
	private String rutacancion01, rutacancion02, rutacancion03;
	private String rutadescripcion;
	private boolean estaactiva01, estaactiva02, estaactiva03, estaactivadescripcion;
	private String rutaayuda;
	private TipoEstimulo tipoestimulo;
	
	/*
	 * Métodos de la clase
	 */
	
	/**
	 * Constructor por defecto (no hace nada)
	 * @param contexto Contexto de la aplicación
	 */
	public ConfiguracionActividad(Context contexto)
	{
		this.contexto = contexto;
		nombreactividad = "";
		rutacancion01 = "";
		rutacancion02 = "";
		rutacancion03 = "";
		rutaayuda = "";
		rutadescripcion = "";
		estaactiva01 = Boolean.TRUE;
		estaactiva02 = Boolean.TRUE;
		estaactiva03 = Boolean.TRUE;
		estaactivadescripcion = Boolean.TRUE;
		tipoestimulo = TipoEstimulo.VOLUMEN;
	}
	
	/**
	 * Constructor de la clase
	 * @param contexto Contexto de la aplicación
	 * @param nombreactividad Nombre de la actividad
	 * @param rutacancion01 Ruta de la canción 01
	 * @param rutacancion02 Ruta de la canción  02
	 * @param rutacancion03 Ruta de la canción 03
	 * @param rutadescipcion Ruta de la descripción de la actividad
	 * @param estaactiva01 Indica si está activa la canción 01
	 * @param estaactiva02 Indica si está activa la canción 02
	 * @param estaactiva03 Indica si está activa la canción 03
	 * @param estaactivadescripcion Indica si está activa la descripción de la actividad
	 * @param rutaayuda Ruta de la ayuda
	 * @param tipoestimulo Tipo del estímulo
	 */
	public ConfiguracionActividad(Context contexto, String nombreactividad, 
			String rutacancion01, String rutacancion02, String rutacancion03,
			String rutadescripcion, boolean estaactiva01, boolean estaactiva02, 
			boolean estaactiva03, boolean estaactivadescripcion,
			String rutaayuda, TipoEstimulo tipoestimulo)
	{
		this.contexto = contexto;
		this.setNombreactividad(nombreactividad);
		this.setRutacancion01(rutacancion01);
		this.setRutacancion02(rutacancion02);
		this.setRutacancion03(rutacancion03);
		this.setEstaActivaCancion01(estaactiva01);
		this.setEstaActivaCancion02(estaactiva02);
		this.setEstaActivaCancion03(estaactiva03);
		this.setRutaAyuda(rutaayuda);
		this.setTipoestimulo(tipoestimulo);
		this.setRutaDescripcion(rutadescripcion);
		this.setActivaDescripcion(estaactivadescripcion);
	}

	/**
	 * Devuelve el nombre de la actividad
	 * @return Nombre de la actividad
	 */
	public String getNombreactividad() 
	{
		return nombreactividad;
	}

	/**
	 * Modifica el nombre de la actividad
	 * @param nombreactividad Nuevo nombre de la actividad
	 */
	private void setNombreactividad(String nombreactividad) 
	{
		this.nombreactividad = nombreactividad;
	}
	
	/**
	 * Devuelve la ruta de la canción 02
	 * @return Ruta de la canción 02
	 */
	public String getRutacancion02() 
	{
		return rutacancion02;
	}

	/**
	 * Modifica la ruta de la canción 02
	 * @param rutacancion02 Nueva ruta de la canción 02
	 */
	private void setRutacancion02(String rutacancion02) 
	{
		this.rutacancion02 = rutacancion02;
	}

	/**
	 * Devuelve la ruta de la canción 01
	 * @return Ruta de la canción 01
	 */
	public String getRutacancion01() 
	{
		return rutacancion01;
	}

	/**
	 * Modifica la ruta de la canción 01
	 * @param rutacancion01 Nueva ruta de la canción 01
	 */
	private void setRutacancion01(String rutacancion01) 
	{
		this.rutacancion01 = rutacancion01;
	}

	/**
	 * Devuelve la ruta de la canción 03
	 * @return Ruta de la canción 03
	 */
	public String getRutacancion03() 
	{
		return rutacancion03;
	}

	/**
	 * Modifica la ruta de la canción 03
	 * @param rutacancion03 Nueva ruta de la canción 03
	 */
	private void setRutacancion03(String rutacancion03) 
	{
		this.rutacancion03 = rutacancion03;
	}

	/**
	 * Devuelve si está activa la canción 02
	 * @return Verdadero si está activa la canción 02, Falso en caso contrario
	 */
	public boolean getEstaActiva02() 
	{
		return estaactiva02;
	}

	/**
	 * Modifica si está activa la canción 02
	 * @param estaactiva02 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion02(boolean estaactiva02) 
	{
		this.estaactiva02 = estaactiva02;
	}

	/**
	 * Devuelve si está activa la canción 03
	 * @return Verdadero si está activa la canción 03, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion03() 
	{
		return estaactiva03;
	}

	/**
	 * Modifica si está activa la canción 03
	 * @param estaactiva03 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion03(boolean estaactiva03) 
	{
		this.estaactiva03 = estaactiva03;
	}

	/**
	 * Devuelve si está activa la canción 01
	 * @return Verdadero si está activa la canción 01, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion01() 
	{
		return estaactiva01;
	}

	/**
	 * Modifica si está activa la canción 01
	 * @param estaactiva01 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion01(boolean estaactiva01) 
	{
		this.estaactiva01 = estaactiva01;
	}

	/**
	 * Devuelve la ruta de la ayuda
	 * @return Ruta de la ayuda
	 */
	public String getRutaAyuda() 
	{
		return rutaayuda;
	}

	/**
	 * Modifica la ruta de la ayuda
	 * @param rutaayuda Nueva ruta de la ayuda
	 */
	private void setRutaAyuda(String rutaayuda) 
	{
		this.rutaayuda = rutaayuda;
	}

	/**
	 * Devuelve el tipo de estímulo que se trata en la actividad
	 * @return Tipo de estímulo
	 */
	public String getTipoestimulo() 
	{
		return tipoestimulo.toString();
	}

	/**
	 * Modifica el tipo de estímulo que se trata en la actividad
	 * @param tipoestimulo Nuevo tipo de estímulo
	 */
	private void setTipoestimulo(TipoEstimulo tipoestimulo) 
	{
		this.tipoestimulo = tipoestimulo;
	}
	
	/**
	 * Almacena la actividad en un fichero del tipo ConfiguracionActividadXXX.xml
	 */
	public void almacenar()
	{
		/*
		 * Para almacenar datos en ficheros xml utilizamos SharedPreferences, los modos
		 * para almacenar son los siguientes:
		 * 	1.- MODE_PRIVATE. Sólo nuestra aplicación tiene acceso a estas preferencias.
		 * 	2.- MODE_WORLD_READABLE. Todas las aplicaciones pueden leer estas preferencias, pero sólo la nuestra puede modificarlas.
		 * 	3.- MODE_WORLD_WRITABLE. Todas las aplicaciones pueden leer y modificar estas preferencias.
		 */
		SharedPreferences prefs = contexto.getSharedPreferences(UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_actividad) + this.getNombreactividad(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("NOMBREACTIVIDAD", this.getNombreactividad());
		editor.putString("RUTACANCION01", this.getRutacancion01());
		editor.putBoolean("ESTAACTIVACANCION01", this.getEstaActivaCancion01());
		editor.putString("RUTACANCION02", this.getRutacancion02());
		editor.putBoolean("ESTAACTIVACANCION02", this.getEstaActiva02());
		editor.putString("RUTACANCION03", this.getRutacancion03());		
		editor.putBoolean("ESTAACTIVACANCION03", this.getEstaActivaCancion03());
		editor.putString("RUTAAYUDA", this.getRutaAyuda());
		editor.putString("TIPOESTIMULO", this.getTipoestimulo());
		editor.putString("RUTADESCRIPCION", this.getRutaDescripcion());
		editor.putBoolean("ESTAACTIVADESCRIPCION", this.getActivaDescripcion());
		editor.commit();
		
		/*
		 * Una vez llamado commit() para almacenar se guardará en la ruta:
		 * 
		 * 	/data/data/paquete.java/shared_prefs/nombre_coleccion.xml
		 * 
		 * en este caso: /data/data/com.example.cerca_lejos/shared_prefs/nombre_coleccion.xml
		 */
	}
	
	/**
	 * Obtiene la actividad que se quiera de un fichero del tipo ConfiguracionActividadXXX.xml
	 * @param nombreactividad Nombre de la actividad a obtener
	 */
	public void obtener(String nombreactividad)
	{
		if( nombreactividad.compareTo(UtilidadesVarias.obtenerStringXML(contexto, R.string.no_actividades_creadas)) != 0 )
		{
			SharedPreferences prefs = contexto.getSharedPreferences(UtilidadesVarias.obtenerStringXML(contexto, R.string.configuracion_actividad) + nombreactividad, Context.MODE_PRIVATE);
			this.setNombreactividad(prefs.getString("NOMBREACTIVIDAD", ""));
			this.setRutacancion01(prefs.getString("RUTACANCION01", ""));
			this.setEstaActivaCancion01(prefs.getBoolean("ESTAACTIVACANCION01", Boolean.FALSE));
			this.setRutacancion02(prefs.getString("RUTACANCION02", ""));
			this.setEstaActivaCancion02(prefs.getBoolean("ESTAACTIVACANCION02", Boolean.FALSE));
			this.setRutacancion03(prefs.getString("RUTACANCION03", ""));
			this.setEstaActivaCancion03(prefs.getBoolean("ESTAACTIVACANCION03", Boolean.FALSE));
			this.setRutaAyuda(prefs.getString("RUTAAYUDA", ""));
			this.setRutaDescripcion(prefs.getString("RUTADESCRIPCION", ""));
			this.setActivaDescripcion(prefs.getBoolean("ESTAACTIVADESCRIPCION", Boolean.FALSE));
		
			TipoEstimulo estimulo;
			String estimulocadena = prefs.getString("TIPOESTIMULO", "Volumen");
			if( estimulocadena.compareTo("Volumen") == 0 )
			{
				estimulo = TipoEstimulo.VOLUMEN;
			}
			else
			{
				if( estimulocadena.compareTo("Timbre") == 0 )
				{
					estimulo = TipoEstimulo.TIMBRE;
				}
				else
				{
					estimulo = TipoEstimulo.FRECUENCIA;
				}
			}		
			this.setTipoestimulo(estimulo);
		}
	}

	/**
	 * Actualiza los atributos de la actividad
	 * @param rutacancion01 Ruta de la canción 01
	 * @param rutacancion02 Ruta de la canción  02
	 * @param rutacancion03 Ruta de la canción 03
	 * @param rutadescripcion Ruta de la descripción de la actividad
	 * @param estaactiva01 Indica si está activa la canción 01
	 * @param estaactiva02 Indica si está activa la canción 02
	 * @param estaactiva03 Indica si está activa la canción 03
	 * @param estaactivadescripcion Indica si está activa la descripción de la actividad
	 * @param rutaayuda Ruta de la ayuda
	 * @param tipoestimulo Tipo del estímulo
	 */
	public void actualizar(String rutacancion01, String rutacancion02, String rutacancion03,
			String rutadescripcion, boolean estaactiva01, boolean estaactiva02, 
			boolean estaactiva03, boolean estaactivadescripcion,
			String rutaayuda, TipoEstimulo tipoestimulo)
	{
		this.setRutacancion01(rutacancion01);
		this.setRutacancion02(rutacancion02);
		this.setRutacancion03(rutacancion03);
		this.setEstaActivaCancion01(estaactiva01);
		this.setEstaActivaCancion02(estaactiva02);
		this.setEstaActivaCancion03(estaactiva03);
		this.setRutaAyuda(rutaayuda);
		this.setTipoestimulo(tipoestimulo);
		this.setRutaDescripcion(rutadescripcion);
		this.setActivaDescripcion(estaactivadescripcion);
	}
	
	private void setRutaDescripcion(String rutanueva)
	{
		rutadescripcion = rutanueva;
	}
	
	public String getRutaDescripcion()
	{
		return rutadescripcion;
	}
	
	private void setActivaDescripcion(boolean estaactivadescripcion)
	{
		this.estaactivadescripcion = estaactivadescripcion;
	}
	
	public boolean getActivaDescripcion()
	{
		return estaactivadescripcion;
	}
}

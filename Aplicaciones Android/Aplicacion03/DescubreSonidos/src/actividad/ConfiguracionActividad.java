/**
 * @author francis
 * Esta clase representa la configuración de una actividad
 */

package actividad;

import java.util.ArrayList;

import cajon_desastre.UtilidadesVarias;

import com.example.descubre_sonidos.R;
import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracionActividad 
{
	/*
	 * Atributos de la clase
	 */
	
	private Context contexto;
	private String nombreactividad;
	private String rutacancion01, rutacancion02, rutacancion03, rutacancion04, rutacancion05, rutacancion06;
	private String rutaimagen01, rutaimagen02, rutaimagen03, rutaimagen04, rutaimagen05, rutaimagen06;
	private boolean estaactivacancion01, estaactivacancion02, estaactivacancion03, estaactivacancion04, estaactivacancion05, estaactivacancion06;
	private boolean estaactivaimagen01, estaactivaimagen02, estaactivaimagen03, estaactivaimagen04, estaactivaimagen05, estaactivaimagen06;
	private boolean estaactivoestimulonegativo;
	private String rutaayuda;
	private ArrayList<String> rutasestimulospositivos;
	private String rutaestimulonegativo;
	private String rutadescripcion;
	private boolean estaactivadescripcion;
	
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
		rutasestimulospositivos = new ArrayList<String>();
		nombreactividad = "";
		rutacancion01 = "";
		rutacancion02 = "";
		rutacancion03 = "";
		rutacancion04 = "";
		rutacancion05 = "";
		rutacancion06 = "";
		rutaimagen01 = "";
		setRutaImagen02("");
		rutaimagen03 = "";
		rutaimagen04 = "";
		rutaimagen05 = "";
		rutaimagen06 = "";
		rutaayuda = "";
		rutaestimulonegativo = "";
		estaactivacancion01 = Boolean.TRUE;
		estaactivacancion02 = Boolean.TRUE;
		estaactivacancion03 = Boolean.TRUE;
		estaactivacancion04 = Boolean.TRUE;
		estaactivacancion05 = Boolean.TRUE;
		estaactivacancion06 = Boolean.TRUE;
		estaactivaimagen01 = Boolean.TRUE;
		estaactivaimagen02 = Boolean.TRUE;
		setEstaActivaImagen03(Boolean.TRUE);
		estaactivaimagen04 = Boolean.TRUE;
		estaactivaimagen05 = Boolean.TRUE;
		estaactivaimagen06 = Boolean.TRUE;
		estaactivoestimulonegativo = Boolean.TRUE;
		rutadescripcion = "";
		estaactivadescripcion = Boolean.TRUE;
	}
	
	/**
	 * Constructor de la clase
	 * @param contexto Contexto de la aplicación
	 * @param nombreactividad Nombre de la actividad
	 * @param rutacancion01 Ruta de la canción 01
	 * @param rutacancion02 Ruta de la canción 02
	 * @param rutacancion03 Ruta de la canción 03
	 * @param rutacancion04 Ruta de la canción 04
	 * @param rutacancion05 Ruta de la canción 05
	 * @param rutacancion06 Ruta de la canción 06
	 * @param rutaimagen01 Ruta de la imagen 01
	 * @param rutaimagen02 Ruta de la imagen 02
	 * @param rutaimagen03 Ruta de la imagen 03
	 * @param rutaimagen04 Ruta de la imagen 04
	 * @param rutaimagen05 Ruta de la imagen 05
	 * @param rutaimagen06 Ruta de la imagen 06
	 * @param estaactivacancion01 Indica si está activa la canción 01
	 * @param estaactivacancion02 Indica si está activa la canción 02
	 * @param estaactivacancion03 Indica si está activa la canción 03
	 * @param estaactivacancion04 Indica si está activa la canción 04
	 * @param estaactivacancion05 Indica si está activa la canción 05
	 * @param estaactivacancion06 Indica si está activa la canción 06
	 * @param estaactivaimagen01 Indica si está activa la imagen 01
	 * @param estaactivaimagen02 Indica si está activa la imagen 02
	 * @param estaactivaimagen03 Indica si está activa la imagen 03
	 * @param estaactivaimagen04 Indica si está activa la imagen 04
	 * @param estaactivaimagen05 Indica si está activa la imagen 05
	 * @param estaactivaimagen06 Indica si está activa la imagen 06
	 * @param estaactivoestimulonegativo Indica si está activo o no el estímulo negativo
	 * @param rutaayuda Ruta de la ayuda
	 * @param rutasestimulospositivos Array con las rutas de los estímulos positivos
	 * @param rutaestimulonegativo Ruta del estímulo negativo
	 * @param rutadescripcion Ruta de la descripción de la actividad
	 * @param estaactivadescripcion Indica si está activa la descripción de la actividad
	 */
	public ConfiguracionActividad(Context contexto, String nombreactividad, 
			String rutacancion01, String rutacancion02, String rutacancion03, String rutacancion04, String rutacancion05, String rutacancion06,
			String rutaimagen01, String rutaimagen02, String rutaimagen03, String rutaimagen04, String rutaimagen05, String rutaimagen06,
			boolean estaactivacancion01, boolean estaactivacancion02, boolean estaactivacancion03, boolean estaactivacancion04, boolean estaactivacancion05, boolean estaactivacancion06,
			boolean estaactivaimagen01, boolean estaactivaimagen02, boolean estaactivaimagen03, boolean estaactivaimagen04, boolean estaactivaimagen05, boolean estaactivaimagen06,
			String rutaayuda, ArrayList<String> rutasestimulospositivos, 
			String rutaestimulonegativo, boolean estaactivoestimulonegativo,
			String rutadescripcion, boolean estaactivadescripcion)
	{
		this.contexto = contexto;
		this.setNombreactividad(nombreactividad);
		this.setRutacancion01(rutacancion01);
		this.setRutacancion02(rutacancion02);
		this.setRutacancion03(rutacancion03);
		this.setRutacancion04(rutacancion04);
		this.setRutacancion05(rutacancion05);
		this.setRutacancion06(rutacancion06);
		this.setRutaImagen01(rutaimagen01);
		this.setRutaImagen02(rutaimagen02);
		this.setRutaImagen03(rutaimagen03);
		this.setRutaImagen04(rutaimagen04);
		this.setRutaImagen05(rutaimagen05);
		this.setRutaImagen06(rutaimagen06);
		this.setEstaActivaCancion01(estaactivacancion01);
		this.setEstaActivaCancion02(estaactivacancion02);
		this.setEstaActivaCancion03(estaactivacancion03);
		this.setEstaActivaCancion04(estaactivacancion04);
		this.setEstaActivaCancion05(estaactivacancion05);
		this.setEstaActivaCancion06(estaactivacancion06);
		this.setEstaActivaImagen01(estaactivaimagen01);
		this.setEstaActivaImagen02(estaactivaimagen02);
		this.setEstaActivaImagen03(estaactivaimagen03);
		this.setEstaActivaImagen04(estaactivaimagen04);
		this.setEstaActivaImagen05(estaactivaimagen05);
		this.setEstaActivaImagen06(estaactivaimagen06);
		this.setActivoEstimuloNegativo(estaactivoestimulonegativo);
		this.setRutaAyuda(rutaayuda);
		this.setRutasEstimulosPositivos(rutasestimulospositivos);
		this.setRutaEstimuloNegativo(rutaestimulonegativo);
		this.setActivaDescripcion(estaactivadescripcion);
		this.setRutaDescripcion(rutadescripcion);
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
	 * Devuelve la cantidad de estímulos positivos que tiene la configuración
	 * @return Cantidad de estímulos positivos
	 */
	public int getCantidadEstimulosPositivos()
	{
		return this.getRutasEstimulosPositivos().size();
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
	 * Devuelve la ruta de la canción 04
	 * @return Ruta de la canción 04
	 */
	public String getRutacancion04() 
	{
		return rutacancion04;
	}

	/**
	 * Modifica la ruta de la canción 04
	 * @param rutacancion04 Nueva ruta de la canción 04
	 */
	private void setRutacancion04(String rutacancion04) 
	{
		this.rutacancion04 = rutacancion04;
	}
	
	/**
	 * Devuelve la ruta de la canción 05
	 * @return Ruta de la canción 05
	 */
	public String getRutacancion05() 
	{
		return rutacancion05;
	}

	/**
	 * Modifica la ruta de la canción 05
	 * @param rutacancion05 Nueva ruta de la canción 05
	 */
	private void setRutacancion05(String rutacancion05) 
	{
		this.rutacancion05 = rutacancion05;
	}
	
	/**
	 * Devuelve la ruta de la canción 06
	 * @return Ruta de la canción 06
	 */
	public String getRutacancion06() 
	{
		return rutacancion06;
	}

	/**
	 * Modifica la ruta de la canción 06
	 * @param rutacancion06 Nueva ruta de la canción 06
	 */
	private void setRutacancion06(String rutacancion06) 
	{
		this.rutacancion06 = rutacancion06;
	}

	/**
	 * Devuelve si está activa la canción 02
	 * @return Verdadero si está activa la canción 02, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion02() 
	{
		return estaactivacancion02;
	}

	/**
	 * Modifica si está activa la canción 02
	 * @param estaactiva02 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion02(boolean estaactiva02) 
	{
		this.estaactivacancion02 = estaactiva02;
	}

	/**
	 * Devuelve si está activa la canción 03
	 * @return Verdadero si está activa la canción 03, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion03() 
	{
		return estaactivacancion03;
	}

	/**
	 * Modifica si está activa la canción 03
	 * @param estaactiva03 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion03(boolean estaactiva03) 
	{
		this.estaactivacancion03 = estaactiva03;
	}

	/**
	 * Devuelve si está activa la canción 01
	 * @return Verdadero si está activa la canción 01, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion01() 
	{
		return estaactivacancion01;
	}

	/**
	 * Modifica si está activa la canción 01
	 * @param estaactiva01 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion01(boolean estaactiva01) 
	{
		this.estaactivacancion01 = estaactiva01;
	}
	
	/**
	 * Devuelve si está activa la canción 04
	 * @return Verdadero si está activa la canción 04, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion04() 
	{
		return estaactivacancion04;
	}

	/**
	 * Modifica si está activa la canción 04
	 * @param estaactiva04 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion04(boolean estaactiva04) 
	{
		this.estaactivacancion04 = estaactiva04;
	}
	
	/**
	 * Devuelve si está activa la canción 05
	 * @return Verdadero si está activa la canción 05, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion05() 
	{
		return estaactivacancion05;
	}

	/**
	 * Modifica si está activa la canción 05
	 * @param estaactiva05 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion05(boolean estaactiva05) 
	{
		this.estaactivacancion05 = estaactiva05;
	}
	
	/**
	 * Devuelve si está activa la canción 06
	 * @return Verdadero si está activa la canción 06, Falso en caso contrario
	 */
	public boolean getEstaActivaCancion06() 
	{
		return estaactivacancion06;
	}

	/**
	 * Modifica si está activa la canción 06
	 * @param estaactiva06 Verdadero si la canción está activa, Falso en caso contrario
	 */
	private void setEstaActivaCancion06(boolean estaactiva06) 
	{
		this.estaactivacancion06 = estaactiva06;
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
		editor.putBoolean("ESTAACTIVACANCION02", this.getEstaActivaCancion02());
		editor.putString("RUTACANCION03", this.getRutacancion03());		
		editor.putBoolean("ESTAACTIVACANCION03", this.getEstaActivaCancion03());
		editor.putString("RUTACANCION04", this.getRutacancion04());		
		editor.putBoolean("ESTAACTIVACANCION04", this.getEstaActivaCancion04());
		editor.putString("RUTACANCION05", this.getRutacancion05());		
		editor.putBoolean("ESTAACTIVACANCION05", this.getEstaActivaCancion05());
		editor.putString("RUTACANCION06", this.getRutacancion06());		
		editor.putBoolean("ESTAACTIVACANCION06", this.getEstaActivaCancion06());
		editor.putString("RUTAIMAGEN01", this.getRutaImagen01());
		editor.putBoolean("ESTAACTIVAIMAGEN01", this.getEstaActivaImagen01());
		editor.putString("RUTAIMAGEN02", this.getRutaImagen02());
		editor.putBoolean("ESTAACTIVAIMAGEN02", this.getEstaActivaImagen02());
		editor.putString("RUTAIMAGEN03", this.getRutaImagen03());		
		editor.putBoolean("ESTAACTIVAIMAGEN03", this.getEstaActivaImagen03());
		editor.putString("RUTAIMAGEN04", this.getRutaImagen04());		
		editor.putBoolean("ESTAACTIVAIMAGEN04", this.getEstaActivaImagen04());
		editor.putString("RUTAIMAGEN05", this.getRutaImagen05());		
		editor.putBoolean("ESTAACTIVAIMAGEN05", this.getEstaActivaImagen05());
		editor.putString("RUTAIMAGEN06", this.getRutaImagen06());		
		editor.putBoolean("ESTAACTIVAIMAGEN06", this.getEstaActivaImagen06());
		editor.putString("RUTAAYUDA", this.getRutaAyuda());
		int cantidadpositivos = this.getRutasEstimulosPositivos().size();
		editor.putInt("CANTIDADESTIMULOSPOSITIVOS", cantidadpositivos);
		for(int i = 0; i < cantidadpositivos; i++)
		{
			String nombre = "RUTAESTIMULOPOSITIVO";
			if( i < 10 )
			{
				nombre += "0";
			}
			nombre += Integer.toString(i);
			editor.putString(nombre, this.getRutasEstimulosPositivos().get(i));
		}
		editor.putString("RUTAESTIMULONEGATIVO", this.getRutaEstimuloNegativo());
		editor.putBoolean("ESTAACTIVOESTIMULONEGATIVO", this.estaActivoEstimuloNegativo());
		editor.putString("RUTADESCRIPCION", this.getRutaDescripcion());
		editor.putBoolean("ESTAACTIVADESCRIPCION", this.getActivaDescripcion());
		editor.commit();
		
		/*
		 * Una vez llamado commit() para almacenar se guardará en la ruta:
		 * 
		 * 	/data/data/paquete.java/shared_prefs/nombre_coleccion.xml
		 * 
		 * en este caso: /data/data/com.example.descubre_sonidos/shared_prefs/nombre_coleccion.xml
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
			this.setRutacancion04(prefs.getString("RUTACANCION04", ""));
			this.setEstaActivaCancion04(prefs.getBoolean("ESTAACTIVACANCION04", Boolean.FALSE));
			this.setRutacancion05(prefs.getString("RUTACANCION05", ""));
			this.setEstaActivaCancion05(prefs.getBoolean("ESTAACTIVACANCION05", Boolean.FALSE));
			this.setRutacancion06(prefs.getString("RUTACANCION06", ""));
			this.setEstaActivaCancion06(prefs.getBoolean("ESTAACTIVACANCION06", Boolean.FALSE));
			this.setRutaImagen01(prefs.getString("RUTAIMAGEN01", ""));
			this.setEstaActivaImagen01(prefs.getBoolean("ESTAACTIVAIMAGEN01", Boolean.FALSE));
			this.setRutaImagen02(prefs.getString("RUTAIMAGEN02", ""));
			this.setEstaActivaImagen02(prefs.getBoolean("ESTAACTIVAIMAGEN02", Boolean.FALSE));
			this.setRutaImagen03(prefs.getString("RUTAIMAGEN03", ""));
			this.setEstaActivaImagen03(prefs.getBoolean("ESTAACTIVAIMAGEN03", Boolean.FALSE));
			this.setRutaImagen04(prefs.getString("RUTAIMAGEN04", ""));
			this.setEstaActivaImagen04(prefs.getBoolean("ESTAACTIVAIMAGEN04", Boolean.FALSE));
			this.setRutaImagen05(prefs.getString("RUTAIMAGEN05", ""));
			this.setEstaActivaImagen05(prefs.getBoolean("ESTAACTIVAIMAGEN05", Boolean.FALSE));
			this.setRutaImagen06(prefs.getString("RUTAIMAGEN06", ""));
			this.setEstaActivaImagen06(prefs.getBoolean("ESTAACTIVAIMAGEN06", Boolean.FALSE));
			this.setRutaAyuda(prefs.getString("RUTAAYUDA", ""));
		
			int cantidadpositivos = prefs.getInt("CANTIDADESTIMULOSPOSITIVOS", 0);
			ArrayList<String> rutasposit = new ArrayList<String>();
			for(int i = 0; i < cantidadpositivos; i++)
			{
				String nombre = "RUTAESTIMULOPOSITIVO";
				if( i < 10 )
				{
					nombre += "0";
				}
				nombre += Integer.toString(i);
				rutasposit.add(prefs.getString(nombre, ""));
			}
			this.setRutasEstimulosPositivos(rutasposit);
		
			this.setRutaEstimuloNegativo(prefs.getString("RUTAESTIMULONEGATIVO", ""));
			this.setActivoEstimuloNegativo(prefs.getBoolean("ESTAACTIVOESTIMULONEGATIVO", Boolean.FALSE));
			this.setRutaDescripcion(prefs.getString("RUTADESCRIPCION", ""));
			this.setActivaDescripcion(prefs.getBoolean("ESTAACTIVADESCRIPCION", Boolean.FALSE));
		}
	}

	/**
	 * Indica si el estímulo negativo para la actividad está activado
	 * @return True si está activado, False en caso contrario
	 */
	public boolean estaActivoEstimuloNegativo() 
	{
		return estaactivoestimulonegativo;
	}

	/**
	 * Activa o desactiva el estímulo negativo en la actividad
	 * @param estaactivoestimulonegativo TRUE o FALSE dependiendo de si se quiere activar o desactivar el estímulo
	 */
	private void setActivoEstimuloNegativo(boolean estaactivoestimulonegativo) 
	{
		this.estaactivoestimulonegativo = estaactivoestimulonegativo;
	}

	/**
	 * Devuelve las rutas de los sonidos de los estímulos positivos
	 * @return Array con las rutas de los estímulos positivos de la actividad
	 */
	public ArrayList<String> getRutasEstimulosPositivos() 
	{
		return rutasestimulospositivos;
	}

	/**
	 * Modifica las rutas de los estímulos positivos de la actividad
	 * @param rutasestimulospositivos Array con las nuevas rutas de los sonidos de los estímulos positivos para la actividad
	 */
	private void setRutasEstimulosPositivos(ArrayList<String> rutasestimulospositivos) 
	{
		this.rutasestimulospositivos = rutasestimulospositivos;
	}

	/**
	 * Devuelve la ruta del sonido del estímulo negativo para la actividad
	 * @return Ruta del estímulo negativo
	 */
	public String getRutaEstimuloNegativo() 
	{
		return rutaestimulonegativo;
	}

	/**
	 * Modifica la ruta para el sonido del estímulo negativo de la actividad
	 * @param rutaestimulonegativo Nueva ruta para el estímulo negativo
	 */
	private void setRutaEstimuloNegativo(String rutaestimulonegativo) 
	{
		this.rutaestimulonegativo = rutaestimulonegativo;
	}
	
	/**
	 * Actualiza los atributos de la actividad
	 * @param contexto Contexto de la aplicación
	 * @param nombreactividad Nombre de la actividad
	 * @param rutacancion01 Ruta de la canción 01
	 * @param rutacancion02 Ruta de la canción 02
	 * @param rutacancion03 Ruta de la canción 03
	 * @param rutacancion04 Ruta de la canción 04
	 * @param rutacancion05 Ruta de la canción 05
	 * @param rutacancion06 Ruta de la canción 06
	 * @param rutaimagen01 Ruta de la imagen 01
	 * @param rutaimagen02 Ruta de la imagen 02
	 * @param rutaimagen03 Ruta de la imagen 03
	 * @param rutaimagen04 Ruta de la imagen 04
	 * @param rutaimagen05 Ruta de la imagen 05
	 * @param rutaimagen06 Ruta de la imagen 06
	 * @param estaactivacancion01 Indica si está activa la canción 01
	 * @param estaactivacancion02 Indica si está activa la canción 02
	 * @param estaactivacancion03 Indica si está activa la canción 03
	 * @param estaactivacancion04 Indica si está activa la canción 04
	 * @param estaactivacancion05 Indica si está activa la canción 05
	 * @param estaactivacancion06 Indica si está activa la canción 06
	 * @param estaactivaimagen01 Indica si está activa la imagen 01
	 * @param estaactivaimagen02 Indica si está activa la imagen 02
	 * @param estaactivaimagen03 Indica si está activa la imagen 03
	 * @param estaactivaimagen04 Indica si está activa la imagen 04
	 * @param estaactivaimagen05 Indica si está activa la imagen 05
	 * @param estaactivaimagen06 Indica si está activa la imagen 06
	 * @param estaactivoestimulonegativo Indica si está activo o no el estímulo negativo
	 * @param rutaayuda Ruta de la ayuda
	 * @param rutasestimulospositivos Array con las rutas de los estímulos positivos
	 * @param rutaestimulonegativo Ruta del estímulo negativo
	 * @param rutadescripcion Ruta de la descripción de la actividad
	 * @param estaactivadescripcion Indica si está activa la descripción de la actividad
	 */
	public void actualizar(Context contexto, String nombreactividad, 
			String rutacancion01, String rutacancion02, String rutacancion03, String rutacancion04, String rutacancion05, String rutacancion06,
			String rutaimagen01, String rutaimagen02, String rutaimagen03, String rutaimagen04, String rutaimagen05, String rutaimagen06,
			boolean estaactivacancion01, boolean estaactivacancion02, boolean estaactivacancion03, boolean estaactivacancion04, boolean estaactivacancion05, boolean estaactivacancion06,
			boolean estaactivaimagen01, boolean estaactivaimagen02, boolean estaactivaimagen03, boolean estaactivaimagen04, boolean estaactivaimagen05, boolean estaactivaimagen06,
			String rutaayuda, ArrayList<String> rutasestimulospositivos, 
			String rutaestimulonegativo, boolean estaactivoestimulonegativo,
			String rutadescripcion, boolean estaactivadescripcion)
	{
		this.contexto = contexto;
		this.setNombreactividad(nombreactividad);
		this.setRutacancion01(rutacancion01);
		this.setRutacancion02(rutacancion02);
		this.setRutacancion03(rutacancion03);
		this.setRutacancion04(rutacancion04);
		this.setRutacancion05(rutacancion05);
		this.setRutacancion06(rutacancion06);
		this.setRutaImagen01(rutaimagen01);
		this.setRutaImagen02(rutaimagen02);
		this.setRutaImagen03(rutaimagen03);
		this.setRutaImagen04(rutaimagen04);
		this.setRutaImagen05(rutaimagen05);
		this.setRutaImagen06(rutaimagen06);
		this.setEstaActivaCancion01(estaactivacancion01);
		this.setEstaActivaCancion02(estaactivacancion02);
		this.setEstaActivaCancion03(estaactivacancion03);
		this.setEstaActivaCancion04(estaactivacancion04);
		this.setEstaActivaCancion05(estaactivacancion05);
		this.setEstaActivaCancion06(estaactivacancion06);
		this.setEstaActivaImagen01(estaactivaimagen01);
		this.setEstaActivaImagen02(estaactivaimagen02);
		this.setEstaActivaImagen03(estaactivaimagen03);
		this.setEstaActivaImagen04(estaactivaimagen04);
		this.setEstaActivaImagen05(estaactivaimagen05);
		this.setEstaActivaImagen06(estaactivaimagen06);
		this.setActivoEstimuloNegativo(estaactivoestimulonegativo);
		this.setRutaAyuda(rutaayuda);
		this.setRutasEstimulosPositivos(rutasestimulospositivos);
		this.setRutaEstimuloNegativo(rutaestimulonegativo);
		this.setActivaDescripcion(estaactivadescripcion);
		this.setRutaDescripcion(rutadescripcion);
	}

	/**
	 * Devuelve la ruta de la imagen del elemento 01
	 * @return Ruta de la imagen del elemento 01
	 */
	public String getRutaImagen01() 
	{
		return rutaimagen01;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 01
	 * @param rutaimagen01 Nueva ruta para la imagen del elemento 01
	 */
	public void setRutaImagen01(String rutaimagen01) 
	{
		this.rutaimagen01 = rutaimagen01;
	}
	
	/**
	 * Devuelve la ruta de la imagen del elemento 02
	 * @return Ruta de la imagen del elemento 02
	 */
	public String getRutaImagen02() 
	{
		return rutaimagen02;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 02
	 * @param rutaimagen02 Nueva ruta para la imagen del elemento 02
	 */
	public void setRutaImagen02(String rutaimagen02) 
	{
		this.rutaimagen02 = rutaimagen02;
	}
	
	/**
	 * Devuelve la ruta de la imagen del elemento 03
	 * @return Ruta de la imagen del elemento 03
	 */
	public String getRutaImagen03() 
	{
		return rutaimagen03;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 03
	 * @param rutaimagen03 Nueva ruta para la imagen del elemento 03
	 */
	public void setRutaImagen03(String rutaimagen03) 
	{
		this.rutaimagen03 = rutaimagen03;
	}
	
	/**
	 * Devuelve la ruta de la imagen del elemento 04
	 * @return Ruta de la imagen del elemento 04
	 */
	public String getRutaImagen04() 
	{
		return rutaimagen04;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 04
	 * @param rutaimagen04 Nueva ruta para la imagen del elemento 04
	 */
	public void setRutaImagen04(String rutaimagen04) 
	{
		this.rutaimagen04 = rutaimagen04;
	}
	
	/**
	 * Devuelve la ruta de la imagen del elemento 05
	 * @return Ruta de la imagen del elemento 05
	 */
	public String getRutaImagen05() 
	{
		return rutaimagen05;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 05
	 * @param rutaimagen05 Nueva ruta para la imagen del elemento 05
	 */
	public void setRutaImagen05(String rutaimagen05) 
	{
		this.rutaimagen05 = rutaimagen05;
	}
	
	/**
	 * Devuelve la ruta de la imagen del elemento 06
	 * @return Ruta de la imagen del elemento 06
	 */
	public String getRutaImagen06() 
	{
		return rutaimagen06;
	}

	/**
	 * Modifica la ruta de la imagen del elemento 06
	 * @param rutaimagen06 Nueva ruta para la imagen del elemento 06
	 */
	public void setRutaImagen06(String rutaimagen06) 
	{
		this.rutaimagen06 = rutaimagen06;
	}

	/**
	 * Indica si la imagen del elemento 01 está activa
	 * @return Verdadero si la imagen del elemento 01 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen01() 
	{
		return estaactivaimagen01;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 01
	 * @param estaactivaimagen01 Nuevo estado de la imagen del elemento 01
	 */
	public void setEstaActivaImagen01(boolean estaactivaimagen01) 
	{
		this.estaactivaimagen01 = estaactivaimagen01;
	}
	
	/**
	 * Indica si la imagen del elemento 02 está activa
	 * @return Verdadero si la imagen del elemento 02 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen02() 
	{
		return estaactivaimagen02;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 02
	 * @param estaactivaimagen02 Nuevo estado de la imagen del elemento 02
	 */
	public void setEstaActivaImagen02(boolean estaactivaimagen02) 
	{
		this.estaactivaimagen02 = estaactivaimagen02;
	}
	
	/**
	 * Indica si la imagen del elemento 03 está activa
	 * @return Verdadero si la imagen del elemento 03 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen03() 
	{
		return estaactivaimagen03;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 03
	 * @param estaactivaimagen03 Nuevo estado de la imagen del elemento 03
	 */
	public void setEstaActivaImagen03(boolean estaactivaimagen03) 
	{
		this.estaactivaimagen03 = estaactivaimagen03;
	}
	
	/**
	 * Indica si la imagen del elemento 04 está activa
	 * @return Verdadero si la imagen del elemento 04 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen04() 
	{
		return estaactivaimagen04;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 04
	 * @param estaactivaimagen04 Nuevo estado de la imagen del elemento 04
	 */
	public void setEstaActivaImagen04(boolean estaactivaimagen04) 
	{
		this.estaactivaimagen04 = estaactivaimagen04;
	}
	
	/**
	 * Indica si la imagen del elemento 05 está activa
	 * @return Verdadero si la imagen del elemento 05 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen05() 
	{
		return estaactivaimagen05;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 05
	 * @param estaactivaimagen05 Nuevo estado de la imagen del elemento 05
	 */
	public void setEstaActivaImagen05(boolean estaactivaimagen05) 
	{
		this.estaactivaimagen05 = estaactivaimagen05;
	}
	
	/**
	 * Indica si la imagen del elemento 06 está activa
	 * @return Verdadero si la imagen del elemento 06 está activa, falso en caso contrario
	 */
	public boolean getEstaActivaImagen06() 
	{
		return estaactivaimagen06;
	}

	/**
	 * Modifica si está activa la eimagen del elemento 06
	 * @param estaactivaimagen06 Nuevo estado de la imagen del elemento 06
	 */
	public void setEstaActivaImagen06(boolean estaactivaimagen06) 
	{
		this.estaactivaimagen06 = estaactivaimagen06;
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

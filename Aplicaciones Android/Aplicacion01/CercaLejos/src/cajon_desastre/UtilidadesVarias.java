/**
 * @author francis
 * Esta clase tiene los métodos que se van a utilizar en muchas de las actividades del proyecto
 * por lo cual los tengo aquí agrupados en una solo clase de utilidades
 */

package cajon_desastre;

import java.util.ArrayList;
import java.util.Collections;

import com.example.cerca_lejos.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.daidalos.afiledialog.FileChooserDialog;

public final class UtilidadesVarias 
{
	/**
	 * Genera un número aleatorio entre dos números
	 * @param minimo Límite inferior
	 * @param maximo Límite superior
	 * @return Número aleatorio entre minimo y maximo
	 */
	public static int aleatorioEntreDosNumeros(int minimo, int maximo)
	{
		return (int) Math.floor(Math.random()*(minimo - maximo + 1) + maximo);
	}
	
	/**
	 * Devuelve el String indicado del fichero strings.xml
	 * @param contexto Contexto de la aplicación
	 * @param recurso String que se quiere
	 * @return String almacenado en strings.xml
	 */
	public static String obtenerStringXML(Context contexto, int recurso)
	{
		return contexto.getResources().getString(recurso).toString();
	}
	
	/**
     * Método para cerrar la app
     * @param actividad Actividad que llama
     */
    public static void cerrarApp(Activity actividad)
    {    	
    	AlertDialog.Builder dialogocerrar = new AlertDialog.Builder(actividad);
    	
    	dialogocerrar.setTitle("Salir");
		
    	TextView mensaje = new TextView(actividad);
    	mensaje.setText("¿Salir de la aplicación?");
    	mensaje.setPadding(10, 10, 10, 10);
    	mensaje.setGravity(Gravity.CENTER);
    	mensaje.setTextSize(23);
    	    										
    	dialogocerrar.setView(mensaje);
    	dialogocerrar.setCancelable(Boolean.FALSE);
    	
    	// Creo el botón "Si" del diálogo para cerrar la app
    	dialogocerrar.setPositiveButton("Si", new DialogInterface.OnClickListener() 
    	{
    	  @Override
    	  public void onClick(DialogInterface dialog, int which) 
    	  {
    		  System.runFinalization();
    		  System.exit(0);
    	  }
    	});
    	
    	// Creo el botón "No" del diálogo para cerrar la app
    	dialogocerrar.setNegativeButton("No", new DialogInterface.OnClickListener() 
    	{ 
    	   @Override
    	   public void onClick(DialogInterface dialog, int which) 
    	   {
    	      dialog.cancel();
    	   }
    	});
    	
    	dialogocerrar.show();
    }
	
	/**
	 * Muestra la ventana para elegir ficheros
	 * @param filtros Filtros para los ficheros
	 */
	public static void mostrarFileChooser(Activity actividad, FileChooserDialog.OnFileSelectedListener listener, String filtros)
	{
		// Utilizo la biblioteca aFileDialog, con esta se puede seleccionar cualquier tipo de fichero
		
		// Creo el diálogo
		FileChooserDialog dialog = new FileChooserDialog(actividad);
		dialog.setTitle("Seleccione un fichero");
		// Añado el listener
		dialog.addListener(listener);
		// Hago que aparezca de primeras en la sdcard, si no hay aparece en la raíz
		dialog.setCurrentFolderName(Environment.getExternalStorageDirectory().getAbsolutePath());
		// Desactivo el modo carpeta (no se pueden elegir carpetas)
		dialog.setFolderMode(Boolean.FALSE);
		// Agrego los filtros de las extensiones de los ficheros
		dialog.setFilter(filtros);
		// Le digo que únicamente me muestre los ficheros que se pueden seleccionar
		dialog.setShowOnlySelectable(Boolean.TRUE);
		// Indico que no se pueden crear ficheros, sólo se van a poner seleccionar
		dialog.setCanCreateFiles(Boolean.FALSE);
		// Activo la confirmación de diálogos
		dialog.setShowConfirmation(Boolean.TRUE, Boolean.TRUE);
		// Muestro el diálogo
		dialog.show();
		        
		/* 
			Otra forma para seleccionar audio:
			
			Hay que definir primero una constante que se refiera a esto, por ejemplo:
				final int ELEGIR_AUDIO = 1;
			
			Intent pickAudioIntent = new Intent();
			pickAudioIntent.setType("audio/*");
			pickAudioIntent.setAction(Intent.ACTION_GET_CONTENT);
			actividad.startActivityForResult(pickAudioIntent, ELEGIR_AUDIO);
			
			Luego hay que sobrecargar el método onActivityResult donde se llame:
			
			protected void onActivityResult(int requestCode, int resultCode, final Intent data) 
			{
	    		super.onActivityResult(requestCode, resultCode, data);
	   
	    		if (resultCode == RESULT_OK)
	    		{
	    			switch( requestCode )
	    			{
	    				case ELEGIR_AUDIO:
	    					UtilidadesVarias.mostrarToastText(this, "Seleccionado el fichero de audio: " + data.getDataString());
	    					break;
	    			}
	    		}
			}
		*/
	}
	
	/**
	 * Rellena un spinner con unos valores concretos
	 * @param actividad Actividad que llama
	 * @param spin Spinner a rellenar
	 * @param contenido Valores a poner en el spinner
	 */
	public static void rellenarSpinner(Activity actividad, Spinner spin, ArrayList<String> contenido)
	{
		/*
		 * Primero vacío el spinner
		 */
		
		ArrayList<String> vacio = new ArrayList<String>();
		ArrayAdapter<String> adaptadorvacio = new ArrayAdapter<String>(actividad, android.R.layout.simple_spinner_item, vacio);
		adaptadorvacio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adaptadorvacio);
		
		/*
		 * Relleno el spinner
		 */
		
		Collections.reverse(contenido);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(actividad, R.layout.spinner_item, contenido);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adaptador);
	}
	
	/**
	 * Muestra un texto en la pantalla
	 * @param actividad Actividad que llama
	 * @param texto Texto a mostrar
	 */
	public static void mostrarToastText(Activity actividad, String texto)
	{
		Toast toast = Toast.makeText(actividad, texto, Toast.LENGTH_LONG);
		toast.show();
	}
	
	/**
	 * Indica si un EditText está vacío
	 * @param texto EditText para comprobar
	 * @return Verdadero si el EditText está vacío, Falso en caso contrario
	 */
	public static boolean estaEditTextVacio(EditText texto) 
	{
		return texto.getText().toString().trim().length() == 0;
	}
	
	/*private void eliminarAccesoDirectoApp(Activity actividad)
	{
		Intent shortcutIntent = new Intent(actividad, MainActivity.class);
	    shortcutIntent.setAction(Intent.ACTION_MAIN);
	     
	    Intent addIntent = new Intent();
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, UtilidadesVarias.obtenerStringXML(actividadactual, R.string.app_name));
	 
	    addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
	    actividad.sendBroadcast(addIntent);
	}*/
	
	/**
	 * Indica si un array tiene elementos repetidos
	 * @param lista Array 
	 * @return Verdadero si el array tiene elementos repetidos, falso en caso contrario
	 */
	public static boolean tieneRepetidos(ArrayList<Integer> lista) 
	{
		boolean repetidos = Boolean.FALSE;
		
		for(int i = 0; !repetidos && i < lista.size(); i++)
			for(int j = 0; !repetidos && j < lista.size(); j++)
				if( j != i)
					repetidos = ( lista.get(i) == lista.get(j) );
		
		return repetidos;
	}
}

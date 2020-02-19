/**
 * Esta clase se lanza cuando se instala la aplicación
 * Para hacer que se lance esta se ha de modificar el manifest
 * @author francis
 */

package cajon_desastre;

import cerca_lejos.MainActivity;

import com.example.cerca_lejos.R;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Aplicacion extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		/*
		 * Creo un acceso directo de la aplicación en el escritorio cuando
		 * se instale la primera vez
		 */
		SharedPreferences preferenciasapp;
	    boolean aplicacioninstalada = Boolean.FALSE;
		
		/*
		 * Compruebo si es la primera vez que se ejecuta la alicación,
		 * entonces es cuando creo el acceso directo
         */
        preferenciasapp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        aplicacioninstalada = preferenciasapp.getBoolean("aplicacioninstalada", Boolean.FALSE);
        
        if(!aplicacioninstalada)
        {
	        /*
	         * Código creación acceso directo
	         */
	        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
	        shortcutIntent.setAction(Intent.ACTION_MAIN);
	        Intent intent = new Intent();
	        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, UtilidadesVarias.obtenerStringXML(getApplicationContext(), R.string.app_name));
	        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
	        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	        getApplicationContext().sendBroadcast(intent);
	        
	        /*
	         * Indico que ya se ha creado el acceso directo para que no se vuelva a crear mas
	         */
	        SharedPreferences.Editor editor = preferenciasapp.edit();
	        editor.putBoolean("aplicacioninstalada", true);
	        editor.commit();
        }
	}

}

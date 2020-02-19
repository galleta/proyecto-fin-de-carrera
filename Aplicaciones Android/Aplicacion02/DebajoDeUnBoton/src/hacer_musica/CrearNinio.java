/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor crear un niño
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package hacer_musica;

import ninio.Ninio;
import ninio.UtilidadesNinios;
import cajon_desastre.UtilidadesVarias;

import com.example.debajodeunboton.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CrearNinio extends Activity 
{
	/*
	 * Variables de la clase
	 */
	private EditText edNombreNinioCrear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_crear_ninio.xml.
		 */
		setContentView(R.layout.activity_crear_ninio);
		
		// Obtengo los recursos de la actividad
		final Button bAceptarNinioCrear = (Button)findViewById(R.id.bAceptarNinioCrear);
		final Button bCancelarNinioCrera = (Button)findViewById(R.id.bCancelarNinioCrera);
		edNombreNinioCrear = (EditText)findViewById(R.id.edNombreNinioCrear);
					
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bAceptarNinioCrear.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bAceptarNinioCrear
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					int error = comprobarCorrectitudDatosCrearNinio();
					if( error == 0 )
					{
						Ninio n = new Ninio(edNombreNinioCrear.getText().toString(), getBaseContext());
						n.almacenar();
						
						UtilidadesVarias.mostrarToastText(CrearNinio.this,"Niño " + n.getNombre() + " creado con éxito.");
						edNombreNinioCrear.setText("");
						
						/*
						 * Creo el niño y lo almaceno en la ruta /data/data/com.example.cerca_lejos/shared_prefs/
						 */
					}
					else
					{
						String textoerror = "";
						switch(error)
						{
							case 1:
								textoerror =  "ERROR: Falta el nombre del niño";
								break;
							case 2:
								textoerror = "ERROR: El niño " + edNombreNinioCrear.getText().toString() + " ya está registrado";
								break;
						}
						UtilidadesVarias.mostrarToastText(CrearNinio.this, textoerror);
					}
				}
				catch (Exception e) 
				{
					Log.i("Crear niño", "Error al pulsar aceptar: " + e.toString());
				}
			}
		});
		
		bCancelarNinioCrera.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bCancelarNinioCrera
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					finish();
				}
				catch (Exception e) 
				{
					Log.i("Crear niño", "Error al pulsar cancelar: " + e.toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crear_ninio, menu);
		return true;
	}
	
	/*
	 * Funciones creadas por mi
	 */
	
	/**
	 * Comprueba que los datos son correctos
	 * @return Código de error
	 * 				1.-	No se ha elegido el nombre del niño
	 * 				2.- El niño ya está registrado
	*/
	private int comprobarCorrectitudDatosCrearNinio()
	{
		int error = 0;
		
		if( UtilidadesVarias.estaEditTextVacio(edNombreNinioCrear) )
			error = 1;
		else
			if( UtilidadesNinios.existeNinio(this, edNombreNinioCrear.getText().toString()) )
				error = 2;
		
		return error;
	}

}

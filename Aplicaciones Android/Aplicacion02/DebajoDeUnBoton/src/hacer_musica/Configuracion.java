/**
 * @author francis
 * Clase que representa la actividad que va a permitir al tutor crear un perfil de un
 * niño nuevo o calificar a un niño
 * Características:
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package hacer_musica;

import cajon_desastre.DatosPrograma;

import com.example.debajodeunboton.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

@SuppressWarnings("unused")
public class Configuracion extends Activity 
{
	private DatosPrograma datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/*
		 * Con esta llamada estaremos indicando a Android que debe establecer 
		 * como interfaz gráfica de esta actividad la definida en el recurso 
		 * R.layout.activity_hola_usuario, que no es más que la que hemos 
		 * especificado en el fichero /res/layout/activity_main.xml.
		 */
		setContentView(R.layout.activity_configuracion);
		
		// Obtengo el bundle de la pantalla anterior para ir pasando los datos
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
					
		// Obtengo los recursos de la actividad
		final Button bNuevoPerfil = (Button)findViewById(R.id.bNuevoPerfil);
		final Button bEvaluar = (Button)findViewById(R.id.bEvaluar);
		final Button bCrearNinio = (Button)findViewById(R.id.bCrearNinio);
		final Button bAceptarConf = (Button)findViewById(R.id.bVolverConfiguacion);
		final Button bEliminarNinio = (Button)findViewById(R.id.bEliminarNinio);
		final Button bEliminarActividadConf = (Button)findViewById(R.id.bEliminarActividadConf);
		final Button bModificarActividad = (Button)findViewById(R.id.bModificarActividad);
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bEvaluar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			/**
			 * Acción onClick del botón bCalificar
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, Evaluar.class);
									
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a Calificar.class " + e.toString());
				}
			}
		});
		
		bCrearNinio.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bCrearNinio
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, CrearNinio.class);
								
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a CrearNinio.class " + e.toString());
				}
			}
		});
		
		bEliminarNinio.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEliminarNinio
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, EliminarNinio.class);
									
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a EliminarNinio.class " + e.toString());
				}
			}
		});
		
		bNuevoPerfil.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bNuevoPerfil
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, CrearActividad.class);
					
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a CrearPerfilSonidos.class " + e.toString());
				}
			}
		});
		
		bEliminarActividadConf.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bEliminarPerfil
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, EliminarActividad.class);
					
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a EliminarActividad.class " + e.toString());
				}
			}
		});
		
		bAceptarConf.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bCalificar
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
					Log.i("Configuracion", "Error al pulsar aceptar: " + e.toString());
				}
			}
		});
		
		bModificarActividad.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bModificarActividad
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					Intent intent = new Intent(Configuracion.this, Modificar_actividad.class);
										
					startActivity(intent);
				}
				catch (Exception e) 
				{
					Log.i("Configuracion", "Error al ir a Modificar_actividad.class " + e.toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracion, menu);
		return true;
	}

}

/**
 * @author francis
 * Con esta clase se puede comentar una actividad de un niño
 * Características:
 * 	1.- Fuerza a utilizar la pantalla en horizontal
 * 	2.- Se ve en pantalla completa
 * 	3.- No muestra el título de la aplicación
 */

package descubre_sonidos;

import cajon_desastre.DatosPrograma;

import com.example.descubre_sonidos.R;

import evaluacion.EvaluacionNinioActividad;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ComentarActividad extends Activity 
{
	/*
	 * Atributos de la clase
	 */
	
	private DatosPrograma datos;
	private String nombreninio, nombreactividad, fecha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentar_actividad);
		
		DatosPrograma.initInstance();
		datos = DatosPrograma.getInstance();
		nombreninio = datos.obtenerString("NINIO");
		nombreactividad = datos.obtenerString("ACTIVIDAD");
		fecha = datos.obtenerString("ULTIMAFECHA");
		
		// Obtengo los recursos de la actividad
		final RatingBar rbPuntuacionActividad = (RatingBar)findViewById(R.id.rbPuntuacionActividad);
		final EditText edComentarioActividad = (EditText)findViewById(R.id.edComentarioActividad);
		final TextView tInformacionActividad = (TextView)findViewById(R.id.tInformacionActividad);
		final Button bAceptarComentario = (Button)findViewById(R.id.bAceptarComentario);
	
		tInformacionActividad.setText("Comentar a " + nombreninio + " en " + nombreactividad + " en la fecha " + fecha);
		
		// Sobrecargo los listeners onClick de los botones para darles funcionalidad
		
		bAceptarComentario.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			/**
			 * Acción onClick del botón bAceptarComentario
			 * @param evento onClick
			 */
			public void onClick(View evento)
			{
				try
				{
					EvaluacionNinioActividad datosevaluacion = new EvaluacionNinioActividad(
							getBaseContext(), fecha, datos.obtenerString("ACTIVIDAD"), datos.obtenerString("NINIO"),
							datos.obtenerInt("ACIERTOS"), datos.obtenerInt("FALLOS"),
							datos.obtenerInt("HFILATOTAL"), datos.obtenerInt("MFILATOTAL"), datos.obtenerInt("SFILATOTAL"));
					datosevaluacion.setRating((int) rbPuntuacionActividad.getRating());
					datosevaluacion.setComentario(edComentarioActividad.getText().toString());
					datosevaluacion.almacenar();
					
					finish();
				}
				catch (Exception e) 
				{
					Log.i("ComentarActividad", "Error al ir a MainActivity.class " + e.toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentar_actividad, menu);
		return true;
	}

}

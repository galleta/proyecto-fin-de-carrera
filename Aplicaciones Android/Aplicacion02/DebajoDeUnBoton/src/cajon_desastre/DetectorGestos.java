/**
 * Esta clase se encarga de obtener la información de los gestos que el usuario
 * hace en la aplicación
 * Hereda de SimpleOnGestureListener
 * @author francis
 * @see SimpleOnGestureListener
 */

package cajon_desastre;

import hacer_musica.JuegoDebajoBoton;

import java.util.ArrayList;


import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class DetectorGestos extends SimpleOnGestureListener
{
	private final int TIEMPO_ESPERA = 3000;
	private JuegoDebajoBoton llamante;			// 	Actividad que llama al listener
	private Direccion dire;						//	Dirección del movimiento
	private boolean actividadcalificada = Boolean.FALSE;
	
	private final int UMBRAL_GESTO = 10;
    private final int VELOCIDAD_MINIMA_GESTO = 10;
	
	/**
	 * Constructor de la clase
	 * @param i ImageWiew para mostrar información
	 * @param t TextView para mostrar información
	 */
	public DetectorGestos(JuegoDebajoBoton act)
	{
		llamante = act;
		dire = Direccion.ABAJO;
		actividadcalificada = Boolean.FALSE;
	}
	
	/**
	 * Notificación de un evento de aventura cuando se presenta con la inicial para 
	 * start MotionEvent hasta MotionEvent finish. 
	 * La velocidad calculada es suministrado a lo largo del los ejes x e y en píxeles por segundo.
	 * @param start El primer evento de movimiento hacia abajo que se inició la aventura.
	 * @param finish El segundo evento de movimiento hacia abajo que se finalizó la aventura.
	 * @param velocityX Velocidad en píxeles por segundo del eje X
	 * @param velocityY Velocidad en píxeles por segundo del eje Y
	 */
    public boolean onFling(MotionEvent start, MotionEvent finish, float velocityX, float velocityY) 
	{		
		try
        {
            float diferenciax = finish.getX() - start.getX();
            float diferenciay = finish.getY() - start.getY();

            if(Math.abs(diferenciax) > Math.abs(diferenciay))
            {
                if(Math.abs(diferenciax) > UMBRAL_GESTO && Math.abs(velocityX) > VELOCIDAD_MINIMA_GESTO)
                {
                    if(diferenciax > 0)
                    	dire = Direccion.DERECHA;
                    else
                    	dire = Direccion.IZQUIERDA;
                }
            }
            else
            {
                if(Math.abs(diferenciay) > UMBRAL_GESTO && Math.abs(velocityY) > VELOCIDAD_MINIMA_GESTO)
                {
                    if(diferenciay > 0)
                    	dire = Direccion.ABAJO;
                    else
                    	dire = Direccion.ARRIBA;
                }
            }
        }
        catch(Exception e)
        {
        	Log.i("DetectorGestos", "Error: " + e.toString());
        }
        		
		// Miro la dirección obtenido y muestro la información pertinente
		switch(dire.toInt())
		{
			case 0:	// Derecha

				break;
			case 1: // Izquierda

				break;
			case 2: // Arriba

				break;
			case 3: // Abajo
				//UtilidadesVarias.mostrarToastText(llamante, "Comenzamos de nuevo!!");
				//Log.i("DetectorGestos", "Comenzamos de nuevo!!");
				actividadcalificada = Boolean.FALSE;
				llamante.resetearEleccionNinio();
				llamante.comenzarActividad();
				break;
		}
		
        return Boolean.FALSE;
    }
	
    public boolean onTouchEvent(MotionEvent me) 
    {
        return Boolean.FALSE;
    }
    
    /**
     * Este método se activará inmediatamente para cada evento hacia abajo. 
     * Todos los demás eventos deben estar precedidos por esto.
     * @param arg0 Evento
     */
    public boolean onDown(MotionEvent arg0) 
	{
		return Boolean.FALSE;
	}
    
    /**
     * Este método se activa cuando se pulsa de forma prolongada en la pantalla
     * @param arg0 Evento
     */
    public void onLongPress(MotionEvent arg0) 
	{
    	/*
    	 * En el click prolongado pasa al siguiente estado
    	 */
    	llamante.pasarAlSiguienteEstadoActividad();
    	//UtilidadesVarias.mostrarToastText(llamante, "Pasamos al estado: " + llamante.getEstadoActividad() + " del modo " + llamante.getModo());
    	Log.i("DetectorGestos", "Pasamos al estado: " + llamante.getEstadoActividad() + " del modo " + llamante.getModo());
    	if( llamante.getModo().compareTo("Recordar") == 0 )
    	{
    		/*
    		 * Estoy en el modo Recordar
    		 */
    		
	    	switch( llamante.getEstadoActividad() )
	    	{
	    		case 3:		// Comprobar si es correcto y dar el estímulo correspondiente
	    			if( llamante.comprobarCorrectitudActividad() )
	    			{
	    				llamante.reproducirEstimuloPositivo();
	    				llamante.indicarResultadoActividad(Boolean.TRUE);
	    				if( !actividadcalificada )
	    				{
	    					//UtilidadesVarias.mostrarToastText(llamante, "Lo hiciste bien!! :)");
	    					//Log.i("DetectorGestos", "Lo hiciste bien!! :)");
	    					llamante.aumentarAciertos();
	    					actividadcalificada = Boolean.TRUE;
	    				}
	    			}
	    			else
	    			{
	    				llamante.reproducirEstimuloNegativo();
	    				llamante.indicarResultadoActividad(Boolean.FALSE);
	    				if( !actividadcalificada )
	    				{
	    					//UtilidadesVarias.mostrarToastText(llamante, "Vuelve a intentarlo");
	    					//Log.i("DetectorGestos", "Vuelve a intentarlo");
	    					llamante.aumentarFallos();
	    					actividadcalificada = Boolean.TRUE;
	    				}
	    			}
	    			
	    			break;
	    	}
    	}
    	//Log.i("DetectorGestos", "Pasamos al siguiente estado: " + llamante.getEstadoActividad());
	}
    
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) 
	{
		return Boolean.FALSE;
	}
    
    public void onShowPress(MotionEvent arg0) 
	{
		
	} 
    
    /**
     * Este método se activa cuando se levanta el dedo después de un click
     * @param arg0 Evento
     */
    public boolean onSingleTapUp(MotionEvent arg0) 
	{
		return Boolean.FALSE;
	}
    
    /**
     * Este evento se activar cuando se hace doble click
     * @param arg0 Evento
     */
    public boolean onDoubleTap(MotionEvent arg0) 
	{    	
    	/*llamante.aumentarVecesEscuchado();
    	UtilidadesVarias.mostrarToastText(llamante, "veces escuchado");*/
    	/*
    	 * En doble click reproducirá la acción del estado correspondiente
    	 */
    	
    	if( llamante.getModo().compareTo("Recordar") == 0 )
    	{
    		/*
    		 * Estoy en el modo Recordar
    		 */
    		
	    	switch( llamante.getEstadoActividad() )
	    	{
	    		case 0:		// El niño escucha los fragmentos originales
	    			reproducirTodosLosFragmentos(llamante.getOrdenReproduccion());	    			
	    			break;
	    		case 1:		// El niño busca los fragmentos, es decir, toquetea para escuchar
	    			// Aquí no se hace nada, ya que el niño está toqueando escuchando
	    			break;
	    		case 2:		// El niño se dispone a grabar su elección de los fragmentos
	    			// Esto se hace en la clase JuegoDebajoBoton
	    			break;
	    		/*case 3:		// Comprobar si es correcto y dar el estímulo correspondiente
	    			if( llamante.comprobarCorrectitudActividad() )
	    			{
	    				llamante.reproducirEstimuloPositivo();
	    				llamante.indicarResultadoActividad(Boolean.TRUE);
	    				if( !actividadcalificada )
	    				{
	    					UtilidadesVarias.mostrarToastText(llamante, "Lo hiciste bien!! :)");
	    					//Log.i("DetectorGestos", "Lo hiciste bien!! :)");
	    					llamante.aumentarAciertos();
	    					actividadcalificada = Boolean.TRUE;
	    				}
	    			}
	    			else
	    			{
	    				llamante.reproducirEstimuloNegativo();
	    				llamante.indicarResultadoActividad(Boolean.FALSE);
	    				if( !actividadcalificada )
	    				{
	    					UtilidadesVarias.mostrarToastText(llamante, "Vuelve a intentarlo");
	    					//Log.i("DetectorGestos", "Vuelve a intentarlo");
	    					llamante.aumentarFallos();
	    					actividadcalificada = Boolean.TRUE;
	    				}
	    			}
	    			
	    			break;*/
	    	}
    	}
    	else
    	{
    		/*
    		 * Estoy en el modo componer
    		 */
    		
    		switch( llamante.getEstadoActividad() )
    		{
    			case 0: // El niño escucha los fragmentos originales
    				//Log.i("DetectorGestos", "" + llamante.getOrdenReproduccion().size());
    				reproducirTodosLosFragmentos(llamante.getOrdenReproduccion());
    				break;
    			case 1: // El niño graba sus fragmentos
    				// Esto se hace en la clase JuegoDebajoBoton
    				break;
    			case 2: // El niño escucha su grabación
    				llamante.aumentarVecesEscuchado();
    				reproducirFragmentos(llamante.getComposicionNinio());
    				break;
    		}
    	}
    	
		return Boolean.FALSE;
	}
    
    /**
     * Este método se activa cuando se hace un click
     * @param arg0 Evento
     */
    public boolean onSingleTapConfirmed(MotionEvent arg0) 
    {
    	/*llamante.aumentarFallos();
    	UtilidadesVarias.mostrarToastText(llamante, "fallo!!");*/
		return Boolean.FALSE;
    }
    
    public boolean onDoubleTapEvent(MotionEvent arg0) 
	{
		return Boolean.FALSE;
	}
    
    /**
     * Reproduce todos los fragmentos de la canción con un tiempo entre fragmento y fragmento
     * @param orden Orden de reproducción
     */
    private void reproducirFragmentos(ArrayList<Integer> orden)
    {
    	for(int i = 0; i < orden.size(); i++)
    	{
    		try 
			{
				//Log.i("DetectorGestos", "Reproducciendo el sonido número " + i + ": " + orden.get(i));
				llamante.reproducirSonido(orden.get(i));	// Reproduzco el sonido
				Thread.sleep(TIEMPO_ESPERA);				// Espero un tiempo entre sonido y sonido
			} 
			catch (InterruptedException e) 
			{
				Log.i("DetectorGestos", "Error reproducciendo el sonido: " + llamante.getOrdenReproduccion().get(i) + ": " + e.toString());
			}
    	}
    }
    
    /**
     * Reproduce todos los fragmentos de la canción con un tiempo entre fragmento y fragmento
     * @param orden Orden de reproducción
     */
    private void reproducirTodosLosFragmentos(ArrayList<Integer> orden)
    {
    	ArrayList<Integer> eleccionfinal = new ArrayList<Integer>();
		for(int i = 0; i < orden.size(); i++)
		{
			int elemento = orden.get(i);
			if( elemento != 0 )
				eleccionfinal.add(elemento);
		}
    	
    	
    	for(int i = 0; i < eleccionfinal.size(); i++)
    	{
    		int sonido = eleccionfinal.get(i);
    		try 
			{
				//Log.i("DetectorGestos", "Reproducciendo el sonido número " + i );
				llamante.reproducirSonido(sonido);	// Reproduzco el sonido
				Thread.sleep(TIEMPO_ESPERA);		// Espero un tiempo entre sonido y sonido
			} 
			catch (InterruptedException e) 
			{
				Log.i("DetectorGestos", "Error reproducciendo el sonido: " + llamante.getOrdenReproduccion().get(i) + ": " + e.toString());
			}
    	}
    }
}

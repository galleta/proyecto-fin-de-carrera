/**
 * Esta clase se encarga de obtener la información de los gestos que el usuario
 * hace en la aplicación
 * Hereda de SimpleOnGestureListener
 * @author francis
 * @see SimpleOnGestureListener
 */

package cajon_desastre;

import descubre_sonidos.JuegoSonidos;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class DetectorGestos extends SimpleOnGestureListener
{
	//private Toast toast;					//	Para mostrar los textos
	private JuegoSonidos llamante;			// 	Actividad que llama al listener
	//private Direccion dire;					//	Dirección del movimiento
	
	//private final int UMBRAL_GESTO = 10;
    //private final int VELOCIDAD_MINIMA_GESTO = 10;
	
	/**
	 * Constructor de la clase
	 * @param i ImageWiew para mostrar información
	 * @param t TextView para mostrar información
	 */
	public DetectorGestos(JuegoSonidos act)
	{
		llamante = act;
		//dire = Direccion.ABAJO;
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
		/*try
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
        }*/
        		
		// Miro la dirección obtenido y muestro la información pertinente
		/*switch(dire.toInt())
		{
			case 0:
				toast = Toast.makeText(llamante.getBaseContext(), "Te has movido hacia la derecha", Toast.LENGTH_LONG);
				toast.show();
				break;
			case 1:
				toast = Toast.makeText(llamante.getBaseContext(), "Te has movido hacia la izquierda", Toast.LENGTH_LONG);
				toast.show();
				break;
			case 2:
				toast = Toast.makeText(llamante.getBaseContext(), "Te has movido hacia arriba", Toast.LENGTH_LONG);
				toast.show();
				break;
			case 3:
				toast = Toast.makeText(llamante.getBaseContext(), "Te has movido hacia abajo", Toast.LENGTH_LONG);
				toast.show();
				break;
		}*/
		
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
    	//UtilidadesVarias.mostrarToastText(llamante, "Fallo!!");
    	llamante.aumentarFallos();
    	llamante.reproducirEstimuloNegativo();
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
    	//UtilidadesVarias.mostrarToastText(llamante, "Acierto!!");
    	llamante.aumentarAciertos();
    	llamante.reproducirEstimuloPositivo();
		return Boolean.FALSE;
	}
    
    /**
     * Este método se activa cuando se hace un click
     * @param arg0 Evento
     */
    public boolean onSingleTapConfirmed(MotionEvent arg0) 
    {
    	/*toast = Toast.makeText(llamante.getBaseContext(), "Detectado un click", Toast.LENGTH_LONG);
		toast.show();*/
		return Boolean.FALSE;
    }
    
    public boolean onDoubleTapEvent(MotionEvent arg0) 
	{
		return Boolean.FALSE;
	}
}

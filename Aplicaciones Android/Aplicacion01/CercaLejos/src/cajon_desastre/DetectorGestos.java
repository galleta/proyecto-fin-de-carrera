/**
 * Esta clase se encarga de obtener la información de los gestos que el usuario
 * hace en la aplicación
 * Hereda de SimpleOnGestureListener
 * @author francis
 * @see SimpleOnGestureListener
 */

package cajon_desastre;

import cerca_lejos.Juego_cerca_lejos;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class DetectorGestos extends SimpleOnGestureListener
{
	private Juego_cerca_lejos llamante;		// 	Actividad que llama al listener
	
	/**
	 * Constructor de la clase
	 * @param i ImageWiew para mostrar información
	 * @param t TextView para mostrar información
	 */
	public DetectorGestos(Juego_cerca_lejos act)
	{
		llamante = act;
	}
	
	@Override
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
    	//UtilidadesVarias.mostrarToastText(llamante, "Desvío fila 3!!");
    	llamante.aumentarDesviosFila03();
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
    	//UtilidadesVarias.mostrarToastText(llamante, "Desvío fila 2!!");
    	llamante.aumentarDesviosFila02();
		return Boolean.FALSE;
	}
    
    /**
     * Este método se activa cuando se hace un click
     * @param arg0 Evento
     */
    public boolean onSingleTapConfirmed(MotionEvent arg0) 
    {
    	//UtilidadesVarias.mostrarToastText(llamante, "Desvío fila 1!!");
    	llamante.aumentarDesviosFila01();
		return Boolean.FALSE;
    }
    
    public boolean onDoubleTapEvent(MotionEvent arg0) 
	{
		return Boolean.FALSE;
	}
}
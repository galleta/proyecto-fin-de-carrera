/**
 * Esta clase se encarga de ejecutar cerrar el juego en el doble click
 * Hereda de SimpleOnGestureListener
 * @author francis
 * @see SimpleOnGestureListener
 */

package cajon_desastre;

import descubre_sonidos.JuegoSonidos;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class DetectorGestosCerrar extends SimpleOnGestureListener
{
	private JuegoSonidos llamante;		// 	Actividad que llama al listener
	
	/**
	 * Constructor de la clase
	 * @param i ImageWiew para mostrar información
	 * @param t TextView para mostrar información
	 */
	public DetectorGestosCerrar(JuegoSonidos act)
	{
		llamante = act;
	}
	
	/**
     * Este evento se activar cuando se hace doble click
     * @param arg0 Evento
     */
    public boolean onDoubleTap(MotionEvent arg0) 
	{
    	//UtilidadesVarias.mostrarToastText(llamante, "Doble click en cerrar");
    	llamante.terminarPartida();
		return Boolean.FALSE;
	}
}

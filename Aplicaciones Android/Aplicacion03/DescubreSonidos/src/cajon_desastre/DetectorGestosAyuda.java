/**
 * Esta clase se encarga de ejecutar la ayuda en el doble click
 * Hereda de SimpleOnGestureListener
 * @author francis
 * @see SimpleOnGestureListener
 */

package cajon_desastre;

import descubre_sonidos.JuegoSonidos;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class DetectorGestosAyuda extends SimpleOnGestureListener
{
	private JuegoSonidos llamante;		// 	Actividad que llama al listener
	
	/**
	 * Constructor de la clase
	 * @param i ImageWiew para mostrar información
	 * @param t TextView para mostrar información
	 */
	public DetectorGestosAyuda(JuegoSonidos act)
	{
		llamante = act;
	}
	
	/**
     * Este evento se activar cuando se hace doble click
     * @param arg0 Evento
     */
    public boolean onDoubleTap(MotionEvent arg0) 
	{
    	//UtilidadesVarias.mostrarToastText(llamante, "Doble click en ayuda");
    	llamante.reproducirAyuda();
		return Boolean.FALSE;
	}
}

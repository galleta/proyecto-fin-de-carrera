/**
 * Este enumerado contiene los posibles movimientos que se pueden hacer:
 * 		DERECHA
 * 		IZQUIERDA
 * 		ARRIBA
 * 		ABAJO
 * @author francis
 */

package cajon_desastre;

public enum Direccion 
{
	DERECHA("Derecha", 0),
    IZQUIERDA("Izquierda", 1),
	ARRIBA("Arriba", 2),
	ABAJO("Abajo", 3);

    private String stringValue;
    private int intValue;
    
    /**
     * Constructor del enumerado
     * @param valorcadena Valor en cadena
     * @param valorentero Valor en entero
     */
    private Direccion(String valorcadena, int valorentero) 
    {
        stringValue = valorcadena;
        intValue = valorentero;
    }

    @Override
    /**
     * Devuelve el valor cadena del enumerado
     */
    public String toString() 
    {
        return stringValue;
    }
    
    /**
     * Devuelve el valor entero del enumerado
     * @return
     */
    public int toInt()
    {
    	return intValue;
    }
}
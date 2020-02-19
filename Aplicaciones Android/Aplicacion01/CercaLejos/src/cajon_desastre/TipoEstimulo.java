/**
 * Este enumerado contiene los posibles estímulos para los niños
 * 		NORMAL
 * 		VOLUMEN
 * 		TIMBRE
 * @author francis
 */

package cajon_desastre;

public enum TipoEstimulo 
{
    VOLUMEN("Volumen", 0),
	TIMBRE("Timbre", 1),
	FRECUENCIA("Frecuencia", 2);

    private String stringValue;
    private int intValue;
    
    /**
     * Constructor del enumerado
     * @param valorcadena Valor en cadena
     * @param valorentero Valor en entero
     */
    private TipoEstimulo(String valorcadena, int valorentero) 
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

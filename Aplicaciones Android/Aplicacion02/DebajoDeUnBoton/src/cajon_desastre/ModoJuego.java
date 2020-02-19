/**
 * Este enumerado contiene los posibles modos de juego
 * 		RECORDAR
 * 		COMPONER
 * @author francis
 */

package cajon_desastre;

public enum ModoJuego 
{
	RECORDAR("Recordar", 0),
	COMPONER("Componer", 1);

    private String stringValue;
    private int intValue;
    
    /**
     * Constructor del enumerado
     * @param valorcadena Valor en cadena
     * @param valorentero Valor en entero
     */
    private ModoJuego(String valorcadena, int valorentero) 
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

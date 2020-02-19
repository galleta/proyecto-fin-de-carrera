/**
 * Esta clase se utilizará para formatear las etiquetas de los gráficos
 * en las evaluaciones
 * @author francis
 */

package grafica;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;

public class FormatoEtiquetasGrafico extends Format
{
	/*
	 * Atributos de la clase
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> etiquetas;

	/*
	 * Métodos de la clase
	 */
	
	public FormatoEtiquetasGrafico(ArrayList<String> etiquetas)
	{
		this.etiquetas = etiquetas;
	}
	
	@Override
	public StringBuffer format(Object arg0, StringBuffer arg1, FieldPosition arg2) 
	{
		int posicion = Math.round(Float.parseFloat(arg0.toString()));
        String etiquetastring = etiquetas.get(posicion);
        arg1.append(etiquetastring);
        return arg1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object parseObject(String arg0, ParsePosition arg1) 
	{
		return java.util.Arrays.asList(etiquetas).indexOf(arg0);
	}
}

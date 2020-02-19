/**
 * @author francis
 * Esta clase tiene los métodos de utilidad para las gráficas
 */

package grafica;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

public final class UtilidadesGraficas 
{
	/**
	 * MUestra un grafíco con los valores de los desvíos de los niños
	 * @param titulo Título del gráfico
	 * @param aciertos Evolución de los aciertos
	 * @param fallos Evolución de los fallos
	 * @param tiempos Tiempos que ha estado jugando el niño
	 * @param fechas Fechas de ejecución de las actividades
	 * @param layoutgrafico Layout para mostrar el gráfico
	 */
	public static void mostrarGrafico(String titulo, ArrayList<Integer> aciertos, ArrayList<Integer> fallos, ArrayList<Double> tiempos, ArrayList<String> fechas, XYPlot layoutgrafico)
	{
		layoutgrafico.clear();
		layoutgrafico.setTitle(titulo);
		layoutgrafico.setDomainLabel("Actividades");
		layoutgrafico.setRangeLabel("Aciertos / Fallos / Tiempo (minutos)");
		layoutgrafico.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
		// Formateo las etiquetas para que salgan las fechas
		layoutgrafico.setDomainStep(XYStepMode.SUBDIVIDE, fechas.size());
		layoutgrafico.getGraphWidget().setDomainValueFormat(new FormatoEtiquetasGrafico(fechas));
        
		// Cambio el color de fondo del gráfico
		//layoutgrafico.getBackgroundPaint().setColor(Color.WHITE);
		//layoutgrafico.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
		layoutgrafico.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
		
		// Formateo la leyenda
		layoutgrafico.getLegendWidget().setTableModel(new DynamicTableModel(3, 1));
		layoutgrafico.getLegendWidget().setSize(new SizeMetrics(25, SizeLayoutType.ABSOLUTE, 325, SizeLayoutType.ABSOLUTE));
		// Le aplico a la leyenda un fondo transparente
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.BLACK);
		bgPaint.setStyle(Paint.Style.FILL);
		bgPaint.setAlpha(50);
		layoutgrafico.getLegendWidget().setBackgroundPaint(bgPaint);
		layoutgrafico.getLegendWidget().setPadding(10, 1, 1, 1);        
		// Posición de la leyenda
		layoutgrafico.getLegendWidget().position(
		  		55,
		   		XLayoutStyle.ABSOLUTE_FROM_LEFT,
		   		20,
		  		YLayoutStyle.ABSOLUTE_FROM_TOP,
		        AnchorPosition.LEFT_TOP);
				
		// Refresco el layout del gráfico par que surta efecto
		layoutgrafico.getLayoutManager().refreshLayout();
		
        // Añado la serie número 1
        XYSeries serieaciertos = new SimpleXYSeries(
        		aciertos,  								// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Aciertos");							// Nombre de la serie
        // Añado la serie número 2
        XYSeries seriefallos = new SimpleXYSeries(
        		fallos,  								// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Fallos"); 								// Nombre de la serie
        // Añado la serie número 3
        XYSeries serietiempos = new SimpleXYSeries(
        		tiempos,  								// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Tiempo de juego");			// Nombre de la serie
        
        // Modificamos los colores de las series
        LineAndPointFormatter formatofallos = new LineAndPointFormatter(
                Color.rgb(255, 0, 0),                   // Color de la línea
                Color.rgb(255, 0, 0),                 	// Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        LineAndPointFormatter formatoaciertos = new LineAndPointFormatter(
                Color.rgb(0, 0, 255),                   // Color de la línea
                Color.rgb(0, 0, 255),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        LineAndPointFormatter formatotiempos = new LineAndPointFormatter(
                Color.rgb(255, 255, 0),                   // Color de la línea
                Color.rgb(255, 255, 0),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        
        // Añado las series
        layoutgrafico.addSeries(serieaciertos, formatoaciertos);
        layoutgrafico.addSeries(seriefallos, formatofallos);
        layoutgrafico.addSeries(serietiempos, formatotiempos);
        // Redibujo el gráfico
        layoutgrafico.redraw();
	}
}

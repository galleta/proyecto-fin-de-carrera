/**
 * @author francis
 * Esta clase tiene los métodos de utilidad para las gráficas
 */

package grafica;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.DashPathEffect;
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
	 * Muestra un gráfico con los valores de los desvíos de los niños
	 * @param titulo Título del gráfico
	 * @param desvios01 Evolución de los desvíos en la fila 01
	 * @param desvios02 Evolución de los desvíos en la fila 02
	 * @param desvios03 Evolución de los desvíos en la fila 03
	 * @param tiemposfila01 Tiempos de la fila 01
	 * @param tiemposfila02 Tiempos de la fila 01
	 * @param tiemposfila03 Tiempos de la fila 03
	 * @param tiempostotales Tiempos totales de ejecución de la actividad
	 * @param fechas Fechas de ejecución de las actividades
	 * @param layoutgrafico Layout para mostrar el gráfico
	 */
	public static void mostrarGrafico(String titulo, ArrayList<Integer> desvios01, ArrayList<Integer> desvios02, ArrayList<Integer> desvios03, ArrayList<Double> tiemposfila01, ArrayList<Double> tiemposfila02, ArrayList<Double> tiemposfila03, ArrayList<Double> tiempostotales, ArrayList<String> fechas, XYPlot layoutgrafico)
	{
		layoutgrafico.clear();
		layoutgrafico.setTitle(titulo);
		layoutgrafico.setDomainLabel("Actividades");
		layoutgrafico.setRangeLabel("Desvíos / Tiempos (minutos)");
		layoutgrafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
		
		// Formateo las etiquetas para que salgan las fechas
		layoutgrafico.setDomainStep(XYStepMode.SUBDIVIDE, fechas.size());
		layoutgrafico.getGraphWidget().setDomainValueFormat(new FormatoEtiquetasGrafico(fechas));
        
		// Cambio el color de fondo del gráfico
		//layoutgrafico.getBackgroundPaint().setColor(Color.WHITE);
		//layoutgrafico.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
		layoutgrafico.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
		
		// Formateo la leyenda
		layoutgrafico.getLegendWidget().setTableModel(new DynamicTableModel(2, 4));
		layoutgrafico.getLegendWidget().setSize(new SizeMetrics(100, SizeLayoutType.ABSOLUTE, 275, SizeLayoutType.ABSOLUTE));
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
        XYSeries seriedesvios01 = new SimpleXYSeries(
        		desvios01,  							// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Desvíos fila 01");						// Nombre de la serie
        // Añado la serie número 2
        XYSeries seriedesvios02 = new SimpleXYSeries(
        		desvios02,  							// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Desvios fila 02"); 					// Nombre de la serie
        // Añado la serie número 3
        XYSeries seriedesvios03 = new SimpleXYSeries(
        		desvios03,  							// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Desvios fila 03"); 					// Nombre de la serie
        // Añado la serie número 4
        XYSeries serietiempostotales = new SimpleXYSeries(
        		tiempostotales,  						// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Tiempo de juego total");		// Nombre de la serie
        // Añado la serie número 5
        XYSeries serietiemposfila01 = new SimpleXYSeries(
        		tiemposfila01,  						// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Tiempo juego fila 01");		// Nombre de la serie
        // Añado la serie número 6
        XYSeries serietiemposfila02 = new SimpleXYSeries(
        		tiemposfila02,  						// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Tiempo juego fila 02");		// Nombre de la serie
        // Añado la serie número 7
        XYSeries serietiemposfila03 = new SimpleXYSeries(
        		tiemposfila03,  						// Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                "Tiempo juego fila 03");		// Nombre de la serie
        
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.rgb(255, 0, 0));
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);
        p.setPathEffect(new DashPathEffect(new float[] {10,5}, 0));
        
        // Modificamos los colores de las series
        LineAndPointFormatter formatodesvios01 = new LineAndPointFormatter(
                Color.rgb(255, 0, 0),                   // Color de la línea
                Color.rgb(255, 0, 0),                 	// Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        formatodesvios01.setLinePaint(p);
        
        LineAndPointFormatter formatodesvios02 = new LineAndPointFormatter(
                Color.rgb(0, 66, 33),                   // Color de la línea
                Color.rgb(0, 66, 33),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        Paint p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(Color.rgb(0, 66, 33));
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(2);
        p2.setPathEffect(new DashPathEffect(new float[] {10,5}, 0));
        formatodesvios02.setLinePaint(p2);
        
        LineAndPointFormatter formatodesvios03 = new LineAndPointFormatter(
                Color.rgb(0, 0, 255),                   // Color de la línea
                Color.rgb(0, 0, 255),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        Paint p3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p3.setColor(Color.rgb(0, 0, 255));
        p3.setStyle(Paint.Style.STROKE);
        p3.setStrokeWidth(2);
        p3.setPathEffect(new DashPathEffect(new float[] {10,5}, 0));
        formatodesvios03.setLinePaint(p3);
        
        LineAndPointFormatter formatotiempostotales = new LineAndPointFormatter(
                Color.rgb(255, 255, 0),                   // Color de la línea
                Color.rgb(255, 255, 0),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        LineAndPointFormatter formatotiemposfila01 = new LineAndPointFormatter(
                Color.rgb(128, 0, 0),                   // Color de la línea
                Color.rgb(128, 0, 0),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        LineAndPointFormatter formatotiemposfila02 = new LineAndPointFormatter(
                Color.rgb(0, 128, 128),                   // Color de la línea
                Color.rgb(0, 128, 128),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        LineAndPointFormatter formatotiemposfila03 = new LineAndPointFormatter(
                Color.rgb(255, 0, 127),                   // Color de la línea
                Color.rgb(255, 0, 127),                   // Color del punto
                null, 									// Relleno (no hay relleno)
                null /*new PointLabelFormatter(Color.WHITE)*/);  // Texto para los puntos
        
        // Añado las series
        layoutgrafico.addSeries(seriedesvios01, formatodesvios01);
        layoutgrafico.addSeries(seriedesvios02, formatodesvios02);
        layoutgrafico.addSeries(seriedesvios03, formatodesvios03);
        layoutgrafico.addSeries(serietiempostotales, formatotiempostotales);
        layoutgrafico.addSeries(serietiemposfila01, formatotiemposfila01);
        layoutgrafico.addSeries(serietiemposfila02, formatotiemposfila02);
        layoutgrafico.addSeries(serietiemposfila03, formatotiemposfila03);
        // Redibujo el gráfico
        layoutgrafico.redraw();
	}
}

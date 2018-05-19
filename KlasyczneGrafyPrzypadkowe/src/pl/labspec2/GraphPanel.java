package pl.labspec2;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphPanel extends JPanel {

    /*
     * Wykres histogramu zliczeń 
     */

    private static final long serialVersionUID = 1L;

    int maxN;
    int[] data;
    double[] teor;

    XYSeries series;
    XYSeries seriesT;
    XYSeriesCollection dataset;
    JFreeChart chart;
    public GraphPanel(int N) {

	setLayout(new BorderLayout());
	setBackground(Color.WHITE);

	maxN=65;
	data = new int[maxN];
	teor = new double[maxN];

	clearData();
	ChartPanel cp = new ChartPanel(chart);
	add(cp, BorderLayout.CENTER);

	updateChart(data, N);

    }

    public void setData(int x, int y){ data[x]= y; }

    public void clearData(){
	for(int ii=0; ii<data.length; ii++){
	    data[ii]=-1;
	}
    }



    public void calcTeor(int N, double p, int total){	///TODO wykresik troche sie chyba nie zgadza

	if(p != 0) {
	    PoissonDistribution poisson =  new PoissonDistribution(p*N) ;

	    for(int k=0; k<teor.length; k++){
		teor[k] = poisson.probability(k)*total;
	    }
	} else {
	    for(int k=0; k<teor.length; k++){
		teor[k] = 0;
	    }
	}

    }




    public void updateChart(int[] data, int N) {

	this.removeAll();
	this.revalidate();

	series = new XYSeries("XYGraph");
	for(int ii=0; ii<data.length;ii++){
	    if(data[ii] >=0 ){
		series.add(ii , data[ii] );
	    }
	}

	seriesT = new XYSeries("XYGraphT");
	for(int ii=0; ii<teor.length;ii++){
	    if(teor[ii] >=0 ){
		seriesT.add(ii , teor[ii] );
	    }
	}

	dataset = new XYSeriesCollection();
	dataset.addSeries(series);
	dataset.addSeries(seriesT);


	chart = ChartFactory.createXYLineChart(
		null, // Title
		"k", // x-axis Label
		"Stopień węzła sieci", // y-axis Label
		dataset, // Dataset
		PlotOrientation.VERTICAL, 
		false, // Show Legend
		true, // Use tooltips
		false // Configure chart to generate URLs?
		);

	XYPlot plot = chart.getXYPlot();
	plot.setBackgroundPaint( Color.WHITE );

	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	renderer.setSeriesPaint( 0 , Color.RED );
	renderer.setSeriesLinesVisible(0, false);
	renderer.setSeriesPaint( 1 , Color.GREEN );
	renderer.setSeriesShapesVisible(1, false);

	ValueAxis yAxis = plot.getRangeAxis();
	yAxis.setRange(0, 30);
	ValueAxis xAxis = plot.getDomainAxis();
	xAxis.setRange(0, N);

	plot.setRenderer( renderer ); 

	ChartPanel cp = new ChartPanel(chart);
	cp.setBackground(Color.WHITE);

	this.setLayout(new BorderLayout());
	this.add(cp, BorderLayout.CENTER);
	this.repaint();

    }

}

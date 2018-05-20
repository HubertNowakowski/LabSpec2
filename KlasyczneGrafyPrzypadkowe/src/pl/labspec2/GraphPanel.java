package pl.labspec2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

public class GraphPanel extends JPanel {

    /*
     * Wykres histogramu zliczeń 
     */

    private static final long serialVersionUID = 1L;

    int maxN;
    int[] data;
    double[] teor;
    double[] teor2;

    XYSeries series;
    XYSeries seriesT;
    XYSeries seriesT2;
    XYSeriesCollection dataset;
    JFreeChart chart;
    public GraphPanel(int N) {

	setLayout(new BorderLayout());
	setBackground(Color.WHITE);

	maxN=65;
	data = new int[maxN];
	teor = new double[maxN];

	teor2 = new double[maxN];

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
	    BinomialDistribution binomial = new BinomialDistribution(N, p);

	    for(int k=0; k<teor.length; k++){
		teor[k] = poisson.probability(k)*N;
		teor2[k]=binomial.probability(k)*N;
	    }
	} else {
	    for(int k=0; k<teor.length; k++){
		teor[k] = 0;
		teor2[k]= 0;
	    }
	}

    }




    public void updateChart(int[] data, int N) {

	this.removeAll();
	this.revalidate();

	series = new XYSeries("dane");
	for(int ii=0; ii<data.length;ii++){
	    if(data[ii] >=0 ){
		series.add(ii , data[ii] );
	    }
	}

	seriesT = new XYSeries("Poisson");
	for(int ii=0; ii<teor.length;ii++){
	    if(teor[ii] >=0 ){
		seriesT.add(ii , teor[ii] );
	    }
	}
	seriesT2 = new XYSeries("Binomial");
	for(int ii=0; ii<teor.length;ii++){
	    if(teor2[ii] >=0 ){
		seriesT2.add(ii , teor2[ii] );
	    }
	}

	dataset = new XYSeriesCollection();
	dataset.addSeries(series);
	dataset.addSeries(seriesT);
	dataset.addSeries(seriesT2);


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
	renderer.setSeriesPaint( 2 , Color.BLUE );
	renderer.setSeriesShapesVisible(2, false);

	ValueAxis xAxis = plot.getDomainAxis();
	xAxis.setRange(0, N);

	plot.setRenderer( renderer ); 
	
	LegendTitle lt = new LegendTitle(plot);
	lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
	lt.setBackgroundPaint(Color.white);
	lt.setFrame(new BlockBorder(Color.white));
	lt.setPosition(RectangleEdge.TOP);
	XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.98, lt,RectangleAnchor.TOP_RIGHT);

	ta.setMaxWidth(0.48);
	plot.addAnnotation(ta);

	ChartPanel cp = new ChartPanel(chart);
	cp.setBackground(Color.WHITE);

	this.setLayout(new BorderLayout());
	this.add(cp, BorderLayout.CENTER);
	this.repaint();

    }

}

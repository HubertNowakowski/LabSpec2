package pl.labspec2;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;




public class MainFrame extends JFrame {

	/*
	 * Główna klasa 
	 */
	private static final long serialVersionUID = 1L;
	DrawPanel drawPanel;
	BottomPanel bottomPanel;



	MainFrame( String title, int N,double p) {
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(400, 200, 800, 800);
		this.setSize(800, 800);
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		drawPanel = new DrawPanel(N, p);
		bottomPanel = new BottomPanel(N, p);
		this.add(drawPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		this.bottomPanel.settings.pSlider.addChangeListener(new SliderListener(this));
		this.bottomPanel.settings.nSlider.addChangeListener(new SliderListener(this));
	}

	public static void main(String[] args) throws Exception {

		MainFrame frame = new MainFrame("Klasyczne Grafy Przypadkowe", 250, 0.1);
		frame.setVisible(true);
		frame.updateSim();
		frame.bottomPanel.graph.repaint();

	}

	void updateSim() {
		double p = MainFrame.this.bottomPanel.settings.pSlider.getValue()/100.0;
		int N = MainFrame.this.bottomPanel.settings.nSlider.getValue();
		MainFrame.this.bottomPanel.settings.pLabel.setText( Double.toString(p) );
		MainFrame.this.bottomPanel.settings.nLabel.setText( Double.toString(N) );

		MainFrame.this.drawPanel.recalculate(p, N);
		obliczStopnie(N, p);


	}

	private void obliczStopnie(int N, double p) {
		int stopnie[] = new int[N];
		int hist[] = new int[N];
		int total=0;

		for (int ii = 0; ii< N; ii++) {
			stopnie[ii]=0;
			for (int jj = 0; jj< N; jj++) {
				stopnie[ii] += MainFrame.this.drawPanel.array[ii][jj];
				total += MainFrame.this.drawPanel.array[ii][jj];
			}
			//	    System.out.println(ii+" " + stopnie[ii]);
			hist[stopnie[ii]]++;
		}

		for (int ii = 0; ii< N; ii++) {
			//	    System.out.println(ii+" " + hist[ii]);
			MainFrame.this.bottomPanel.graph.setData(ii,hist[ii]);
			MainFrame.this.bottomPanel.graph.updateChart(MainFrame.this.bottomPanel.graph.data, N);
		}

		MainFrame.this.bottomPanel.graph.calcTeor(N, p, total);
		MainFrame.this.bottomPanel.graph.repaint();
	}



}

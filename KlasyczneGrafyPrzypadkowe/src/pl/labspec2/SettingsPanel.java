package pl.labspec2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel{

	/*
	 * Panel z ustawieniami parametrów prawdopodobieństwa połączenia p i liczby węzłów N
	 */
	private static final long serialVersionUID = 1L;
	JSlider pSlider;
	JLabel  pSliderLabel;
	JLabel  pLabel;

	JSlider nSlider;
	JLabel  nSliderLabel;
	JLabel  nLabel;

	JLabel settingsLabel;

	JButton histogramButton;

	int maxN;

	public SettingsPanel(int N, double p) {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		setPreferredSize(new Dimension(250,120));
		setBackground(Color.WHITE);

		this.maxN=500;

		this.histogramButton = new JButton("policz histogram krawedzi");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,7,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.LINE_END;
		this.add(histogramButton);


		this.settingsLabel = new JLabel("USTAWIENIA SYMULACJI");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,2,0);
		this.add(this.settingsLabel,c);


		this.pSliderLabel = new JLabel("Parametr p : ");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor=GridBagConstraints.LINE_START;
		this.add(this.pSliderLabel,c);

		this.pLabel = new JLabel(Double.toString(p));
		c.gridx = 1;
		c.anchor=GridBagConstraints.LINE_END;
		this.add(this.pLabel,c);

		this.pSlider = new JSlider(0,100, (int)(p*100) );
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,7,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.LINE_END;

		this.pSlider.setBackground(Color.WHITE);

		this.pSlider.setMajorTickSpacing(1);
		this.pSlider.setSnapToTicks(true);
		this.add(this.pSlider,c);



		this.nSliderLabel = new JLabel("Liczba wierzchołków: ");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor=GridBagConstraints.LINE_START;
		this.add(this.nSliderLabel,c);

		this.nLabel = new JLabel(Integer.toString(N));
		c.gridx = 3;
		c.anchor=GridBagConstraints.LINE_END;
		this.add(this.nLabel,c);

		this.nSlider = new JSlider(5,maxN,N);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,7,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.LINE_END;

		this.nSlider.setBackground(Color.WHITE);

		this.nSlider.setMajorTickSpacing(5);
		this.nSlider.setSnapToTicks(true);
		this.add(this.nSlider,c);



		histogramButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int N=0;
				int nMax=500;
				
				JFrame ramka = new JFrame("");
				//ramka.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				ramka.setBounds(400, 200, 800, 800);
				ramka.setSize(800, 800);
				
				GraphPanel gp = new GraphPanel();
				ramka.add(gp);
				ramka.setVisible(true);
								
				for(int kk=10; kk<nMax; kk+=10)
				{
					int array[][]=null;
					array = new int[kk][kk];
					//wyzerowanie
					for (int ii = 0; ii< kk; ii++) {
						for (int jj = 0; jj< kk; jj++) {
							array[ii][jj] = -1;
						}
					}
					//wylosowanie polaczen z prawdopodobienstwem p
					for (int ii = 0; ii< kk; ii++) {
						for (int jj = 0; jj< kk; jj++) {
							if(ii==jj) {
								array[ii][jj] = 0;
								continue;
							}

							if( array[ii][jj] == -1){
								if( Math.random() <= p) {
									array[ii][jj] = 1;
									array[jj][ii] = 1;
								}
								else {
									array[ii][jj] = 0;
									array[jj][ii] = 0;
								}
							}
						}
					}
					
					int total=0;
					for (int ii = 0; ii< kk; ii++) {
						for (int jj = 0; jj< kk; jj++) {
							total += array[ii][jj];

							//System.out.println(kk+" "+total+" "+p+" "+array[ii][jj]);
						}
					}
					
					gp.setData(kk,total/2);
					gp.updateChart(gp.data, nMax);
					
					gp.teor[kk]=p*(kk)*(kk-1)/2;
					
				}
					
			}
		});


	}

}

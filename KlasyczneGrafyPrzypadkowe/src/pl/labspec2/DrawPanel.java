package pl.labspec2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;


public class DrawPanel extends JPanel{

    /**
     * 
     */

    int array[][];
    int N;
    int stopnie[];
    ArrayList<Wierzcholek> wierzcholki = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    DrawPanel(int N, double p) {
	super();
	setBackground(Color.WHITE);
	this.N = N;
	
	stopnie = new int[N];
	recalculate(p, N);
    }


    @Override
    public void paintComponent(Graphics g ){
	Graphics2D g2d = (Graphics2D) g;
	g2d.setColor( Color.white);
	g2d.fill(new Rectangle(getWidth(),getHeight()));

	//rysuje wierzchołki
	g2d.setColor(Color.blue);
	for (Wierzcholek w : wierzcholki) {
	    g2d.fill(w);
	}

	//rysuje polaczenia
	g2d.setColor(Color.red);
	for (int ii = 0; ii< N; ii++) {
	    for (int jj = ii+1; jj< N; jj++) {		//tylko górna macierz trójkątna bez przekątnej
		if( array[ii][jj] == 1 ) {

		    g2d.drawLine(wierzcholki.get(ii).x, wierzcholki.get(ii).y, wierzcholki.get(jj).x, wierzcholki.get(jj).y);
		}
	    }
	}



    }

    public void recalculate(double p, int N) {		//tworzy macierz polaczeń miedzy wezlami

	array=null;
	wierzcholki.clear();
	this.N = N;

	array = new int[N][N];
	int row=0;
	int endln = N/5;
	int space = 55;
	for (int ii = 1; ii<=N; ii++) {
	    wierzcholki.add(new Wierzcholek(10 + space*ii-endln*space*row , 50 + space*row, 15,15));
	    if( ii%endln == 0 ) row++;
	}


	//wyzerowanie
	for (int ii = 0; ii< N; ii++) {
	    for (int jj = 0; jj< N; jj++) {
		array[ii][jj] = -1;
	    }
	}

	//wylosowanie polaczen z prawdopodobienstwem p
	for (int ii = 0; ii< N; ii++) {
	    for (int jj = 0; jj< N; jj++) {
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
	repaint();
    }


    
}

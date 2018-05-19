package pl.labspec2;

import java.awt.Rectangle;

public class Wierzcholek extends Rectangle{

    /*
     * Klasa z wierzvhołkami, w sumie ni jest bardzo potrzebna. Starczyłby sam Rectangle ale jakoś tak mi sie napisało.
     
     */
    private static final long serialVersionUID = 1L;
    int x;
    int y;

    public Wierzcholek( int x, int y, int width,int height) {
	super(x, y, width, height);
	this.x = x + width/2;
	this.y = y + height/2;
    }

}

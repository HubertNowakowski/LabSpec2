package pl.labspec2;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener { 
    /*
     * Listener do slajderów który uaktualnia symulacje przy każdfej zmianie.
     */
	MainFrame frame;

	public SliderListener(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		frame.updateSim();
	}

}


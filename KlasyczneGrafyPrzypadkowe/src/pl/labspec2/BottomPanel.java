package pl.labspec2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JPanel;

public class BottomPanel extends JPanel { 

	/*
	 * Dolny panel z ustawieniami oraz wykresem
	 */
	private static final long serialVersionUID = 1L;
	
	SettingsPanel settings;
	GraphPanel graph;
	
	public BottomPanel(int N, double p) throws HeadlessException {
		setBackground(Color.WHITE);

		setLayout(new GridLayout(1,3,3,3));

		setPreferredSize(new Dimension(800,300));

		this.settings = new SettingsPanel(N, p);
		this.add(this.settings);

		this.graph = new GraphPanel(N);
		this.add(graph);
	}


}

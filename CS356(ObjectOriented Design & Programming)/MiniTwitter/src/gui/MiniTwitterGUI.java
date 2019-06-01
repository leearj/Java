package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MiniTwitterGUI{

	JFrame f;
	JPanel p;
	JButton b1;
	JLabel l;
	
	JPanel p2;
	JButton b2;
	JLabel l2;
	
	public MiniTwitterGUI() {
		f = new JFrame("MiniTwitterGUI");
		f.setVisible(true);
		f.setSize(600,400);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		
		p = new JPanel();
		p.setBackground(Color.RED);
		
		b1 = new JButton("Click me");
		JLabel l = new JLabel("I am Label");
		
		p.add(b1);	// Adding button1 to the panel.
		p.add(l);	// Adding Label to the panel.
		
		f.add(p);
		
		
		
		b2 = new JButton("Click me2");
		JLabel l2 = new JLabel("I am Label2");
		
		p2.add(b2);	// Adding button1 to the panel.
		p2.add(l2);	// Adding Label to the panel.
		
		f.add(p2);
	}
	
	
	
}

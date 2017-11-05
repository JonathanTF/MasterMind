/*initiate.java: does the initial settings window*/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class initiate extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1423476015372164510L;
	
	JButton ok;
	JSlider numguesses;
	JComboBox<?> numpegs;
	JComboBox<?> numcolors;
	boolean lock;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		lock = false;
	}
	
	public initiate(){
		//set up the intro GUI
		
		super("Game Settings");
		lock = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		JPanel head = new JPanel();
		GridBagLayout L3 = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		head.setLayout(L3);
		JTextField text0 = new JTextField("Press 'ok' to begin the game!                       ");
		text0.setEditable(false);
		JTextField text1 = new JTextField("Select number of guesses:");
		text1.setEditable(false);
		JTextField text2 = new JTextField("Select number of pegs:");
		text2.setEditable(false);
		JTextField text3 = new JTextField("Select number of colors:");
		text3.setEditable(false);
		
		head.add(text0,c);
		c.gridwidth =1;
		head.add(text1,c);
		c.gridwidth =1;
		head.add(text2,c);
		head.add(text3,c);
		c.gridy = 1;
		
		ok = new JButton("ok");
		ok.addActionListener(this);
		head.add(ok,c);
		
		numguesses = new JSlider(2,20);
		numguesses.setMajorTickSpacing(2);
		numguesses.setPaintTicks(true);
		numguesses.setPaintLabels(true);
		
		c.gridwidth =1;
		head.add(numguesses,c);
		c.gridwidth =1;
		String[] x = new String[3];
		x[0] = "4";x[1] = "5";x[2] = "6";
		numpegs = new JComboBox<Object>(x);
		head.add(numpegs,c);
		String[] y = new String[3];
		y[0] = "6";y[1] = "7 - adds 'maroon'";y[2] = "8 - adds 'maroon' and 'silver'";
		numcolors = new JComboBox<Object>(y);
		head.add(numcolors,c);
		
		setContentPane(head);
		head.validate();
		head.repaint();
		validate();
		repaint();
		pack();
		setVisible(true);	
	}
}
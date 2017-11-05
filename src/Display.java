/*Display.java: handles the main output*/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class Display extends JFrame implements ActionListener {
	private static final long serialVersionUID = -65265453301792387L;
	
	//GUI fields
	static int lock = 0;
	JPanel rightPanel;
	JLabel theLabel;
	JLabel EmptyPeg;
	JPanel p;
	JPanel p2;
	JPanel head;
	JPanel remaining;
	JPanel results;
	JTextField text;
	JTextField answerField;
	int remaining_turns;
	JButton hitIt;
	JButton again;
	JTextArea rightField;
	JComboBox<?> Select1Box;
	JComboBox<?> Select2Box;
	JComboBox<?> Select3Box;
	JComboBox<?> Select4Box; 
	JComboBox<?> Select5Box;
	JComboBox<?> Select6Box;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==hitIt){
			//made a guess
			
			String[] sets = new String[Driver.pegs];
			sets[0] = (String) Select1Box.getSelectedItem();
			sets[1] = (String) Select2Box.getSelectedItem();
			sets[2] = (String) Select3Box.getSelectedItem();
			sets[3] = (String) Select4Box.getSelectedItem();
			
			if(Driver.pegs >=5){
				sets[4] = (String) Select5Box.getSelectedItem();
			}
			if(Driver.pegs == 6){
				sets[5] = (String) Select6Box.getSelectedItem();
			}
			
			Driver.GameBoard.setBoard(sets);
			
			changeOutput(Driver.GameBoard);
			lock = 0;
		}
		if(arg0.getSource()==again){
			//play again
			
			Driver.GAME_CONDITION=0;
		}
	}
	
	public Display(Board gameBoard){
		//initialize the board with 'empty' images
		
		super("MasterMind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		head = new JPanel();
		GridBagLayout L3 = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		head.setLayout(L3);

		//left board 'guess'
		p = new JPanel();
		GridLayout L = new GridLayout(0,Driver.pegs,0,0);
		p.setLayout(L);
		int max = Driver.pegs*Driver.guesses;
		for(int o =0;o<max;o++){
			JLabel EmptyPegSet = new JLabel();
			EmptyPegSet.setIcon(new ImageIcon("EmptyPeg.jpg"));
			p.add(EmptyPegSet);
		}
		c.gridx = 0;
		c.gridy = 0;
		head.add(p,c);
		
		//right board 'hint'
		p2 = new JPanel();
		GridLayout L2 = new GridLayout(0,Driver.pegs);
		p2.setLayout(L2);
		for(int o =0;o<max;o++){
			JLabel EmptyPegSet = new JLabel();
			EmptyPegSet.setIcon(new ImageIcon("EmptyAnswer.jpg"));
			p2.add(EmptyPegSet);
		}
		c.gridx = 1;
		c.gridy = 0;
		head.add(p2,c);
		
		//set up the select boxes with the colors
		
		String[] colors = {"blue","green","orange","purple","red","yellow"};
		if(Driver.colors==7){
			colors = new String[7];
			colors[0] = "blue"; colors[1] = "green";colors[2] = "orange";colors[3] = "purple";colors[4] = "red";colors[5] = "yellow";colors[6] = "maroon";
		}else if(Driver.colors==8){
			colors = new String[8];
			colors[0] = "blue"; colors[1] = "green";colors[2] = "orange";colors[3] = "purple";colors[4] = "red";colors[5] = "yellow";colors[6] = "maroon";colors[7] = "silver";
		}
			
		JPanel selectors = new JPanel();
		GridLayout scrollers = new GridLayout(1,Driver.pegs);
		selectors.setLayout(scrollers);
		Select1Box = new JComboBox<Object>(colors);
		Select1Box.setSelectedIndex(4);
		Select1Box.addActionListener(this);

		Select2Box = new JComboBox<Object>(colors);
		Select2Box.setSelectedIndex(4);
		Select2Box.addActionListener(this);
		
		Select3Box = new JComboBox<Object>(colors);
		Select3Box.setSelectedIndex(4);
		Select3Box.addActionListener(this);
		
		Select4Box = new JComboBox<Object>(colors);
		Select4Box.setSelectedIndex(4);
		Select4Box.addActionListener(this);
		
		selectors.add(Select1Box);
		selectors.add(Select2Box);
		selectors.add(Select3Box);
		selectors.add(Select4Box);
		
		//add additional listeners for 5 and 6 peg variant
		
		if(Driver.pegs>=5){
			Select5Box = new JComboBox<Object>(colors);
			Select5Box.setSelectedIndex(4);
			Select5Box.addActionListener(this);
			selectors.add(Select5Box);
		}
		if(Driver.pegs==6){
			Select6Box = new JComboBox<Object>(colors);
			Select6Box.setSelectedIndex(4);
			Select6Box.addActionListener(this);
			selectors.add(Select6Box);
		}
		
		//text/check setup
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		head.add(selectors,c);;
		
		results = new JPanel();
		hitIt = new JButton("Check");
		hitIt.addActionListener(this);
		results.add(hitIt);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		head.add(results, c);
		
		remaining_turns = Driver.guesses;
		remaining = new JPanel();
		text = new JTextField("Turns Remaining: " + remaining_turns);
		text.setEditable(false);
		remaining.add(text);
		c.gridx = 0;
		c.gridy = 3;
		head.add(remaining, c);
		
		//right text panel
		rightField = new JTextArea("Welcome to Mastermind. Here are the rules:\nThis is a programmed version of the classic board game Mastermind.\nThe computer will think of a secret code. The code consists of 4 colored pegs.\nThe pegs may be one of six colors: blue, green, orange, purple, red, or yellow. A color may appear\nmore than once in the code. You try to guess what colored pegs are in the code and what order they are\nin. After you make a valid guess the result (feedback) will be displayed.\nThe result consists of a black peg for each peg you have guessed exactly correct (color and position) in\nyour guess. For each peg in the guess that is the correct color, but is out of position, you get a white\npeg. For each peg, which is fully incorrect, you get no feedback.\nYou have 12 guesses to figure out the secret code or you lose the game.\nINSTRUCTIONS:\nUse the drop-down boxes to select your guess for the secret code.\nHit the 'Check' button and the left side of the board will record your guess.\nThe right side of the board will display how many of your guesses were \ncorrect and how many were the correct color but wrong location.\nWhen you guess correctly or miss on the last turn, the game ends.");
		rightField.setEditable(false);
		head.add(rightField);
		
		setContentPane(head);

		pack();
		setVisible(true);
	}

	public void changeOutput(Board GameBoard){
		//display the new state of the board
		
		GridBagConstraints c = new GridBagConstraints();
		GridLayout L = new GridLayout(0,Driver.pegs,0,0);
		p.setLayout(L);
		int x = -1;
		int y = Driver.guesses -1;
		int max = (Driver.guesses*Driver.pegs);
		
		//display the correct image at each location
		for(int o =0;o<max;o++){
			x++;
			if(x==Driver.pegs){
				y--;
				x=0;
			}
			JLabel Peg = new JLabel();
			if(GameBoard.leftBoard[y][x]=="blue"){
				Peg.setIcon(new ImageIcon("BluePeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="green"){
				Peg.setIcon(new ImageIcon("GreenPeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="orange"){
				Peg.setIcon(new ImageIcon("OrangePeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="purple"){
				Peg.setIcon(new ImageIcon("PurplePeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="red"){
				Peg.setIcon(new ImageIcon("RedPeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="yellow"){
				Peg.setIcon(new ImageIcon("YellowPeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="Empty"){
				Peg.setIcon(new ImageIcon("EmptyPeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="maroon"){
				Peg.setIcon(new ImageIcon("MaroonPeg.jpg"));
			}else if(GameBoard.leftBoard[y][x]=="silver"){
				Peg.setIcon(new ImageIcon("SilverPeg.jpg"));
			}
			
			p.remove(o);
			p.add(Peg, o);
			p.validate();
			p.repaint();
		}
		c.gridx = 0;
		c.gridy = 0;
		head.add(p,c);

		//remove a guess
		int n = -1;
		int m = Driver.guesses-1;
		
		//set up the next row of the hint field
		for(int o =0;o<max;o++){
			n++;
			if(n==Driver.pegs){
				m--;
				n=0;
			}
			JLabel Peg = new JLabel();
			if(GameBoard.rightBoard[m][n]=="white"){
				Peg.setIcon(new ImageIcon("WhiteAnswer.jpg"));
			}else if(GameBoard.rightBoard[m][n]=="black"){
				Peg.setIcon(new ImageIcon("BlackAnswer.jpg"));
			}else if(GameBoard.rightBoard[m][n]=="Empty"){
				Peg.setIcon(new ImageIcon("EmptyAnswer.jpg"));
			}
			
			p2.remove(o);
			p2.add(Peg, o);
			p2.validate();
			p2.repaint();
		}

		//next turn
		head.remove(remaining);
		remaining_turns = Driver.guesses-(GameBoard.currentRow);
		remaining = new JPanel();
		JTextField text = new JTextField("Turns Remaining: " + remaining_turns);
		text.setEditable(false);
		remaining.add(text);
		c.gridx = 0;
		c.gridy = 3;
		head.add(remaining, c);
		setContentPane(head);
		head.validate();
		head.repaint();
		validate();
		repaint();
		pack();
		setVisible(true);
		
	}
	
	public void ending(Board GameBoard, int condition){
		//perform ending based on condition
		
		hitIt.setEnabled(false);
		GridBagConstraints c = new GridBagConstraints();
		remaining_turns = Driver.guesses-(GameBoard.currentRow);
		String stringAnswer = new String();
		
		for(int i = 0;i<Driver.pegs;i++){
			stringAnswer += GameBoard.answer[i];
			stringAnswer += " ";
		}
		
		if(condition == 1){//you win!!
			answerField = new JTextField("SECRET CODE FOUND: " +stringAnswer+ " You Win!!!");
			answerField.setEditable(false);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 1;
			head.add(answerField, c);
			head.repaint();
		}else{//you lost!!!
			answerField = new JTextField("SECRET CODE: "+stringAnswer +" You Lost!");
			answerField.setEditable(false);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 1;
			head.add(answerField, c);
			head.repaint();
			remaining_turns = 0;
		}
		head.remove(remaining);
		
		
		remaining = new JPanel();
		JTextField text = new JTextField("Turns Remaining: " + remaining_turns);
		text.setEditable(false);
		remaining.add(text);
		c.gridx = 0;
		c.gridy = 3;
		head.add(remaining, c);

		head.validate();
		head.repaint();
		
		//play again button
		
		again = new JButton("click here to play again");
		again.addActionListener(this);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 3;
		head.add(again,c);
		head.validate();
		head.repaint();
	}
	
	
	public Display() {

	}

	public void reset() {
		
		//setVisible(false);
		remove(head);
		head = new JPanel();
		GridBagLayout L3 = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		head.setLayout(L3);
	
		//left board
		p = new JPanel();
		GridLayout L = new GridLayout(0,Driver.pegs,0,0);
		p.setLayout(L);
		int max = Driver.pegs*Driver.guesses;
		for(int o =0;o<max;o++){
			JLabel EmptyPegSet = new JLabel();
			EmptyPegSet.setIcon(new ImageIcon("EmptyPeg.jpg"));
			p.add(EmptyPegSet);
		}
		c.gridx = 0;
		c.gridy = 0;
		head.add(p,c);
		
		//right board
		p2 = new JPanel();
		GridLayout L2 = new GridLayout(0,Driver.pegs);
		p2.setLayout(L2);
		for(int o =0;o<max;o++){
			JLabel EmptyPegSet = new JLabel();
			EmptyPegSet.setIcon(new ImageIcon("EmptyAnswer.jpg"));
			p2.add(EmptyPegSet);
		}
		c.gridx = 1;
		c.gridy = 0;
		head.add(p2,c);
		
		String[] colors = {"blue","green","orange","purple","red","yellow"};
		if(Driver.colors==7){
			colors = new String[7];
			colors[0] = "blue"; colors[1] = "green";colors[2] = "orange";colors[3] = "purple";colors[4] = "red";colors[5] = "yellow";colors[6] = "maroon";
		}else if(Driver.colors==8){
			colors = new String[8];
			colors[0] = "blue"; colors[1] = "green";colors[2] = "orange";colors[3] = "purple";colors[4] = "red";colors[5] = "yellow";colors[6] = "maroon";colors[7] = "silver";
		}
			
		JPanel selectors = new JPanel();
		GridLayout scrollers = new GridLayout(1,Driver.pegs);
		selectors.setLayout(scrollers);
		Select1Box = new JComboBox<Object>(colors);
		Select1Box.setSelectedIndex(4);
		Select1Box.addActionListener(this);

		Select2Box = new JComboBox<Object>(colors);
		Select2Box.setSelectedIndex(4);
		Select2Box.addActionListener(this);
		
		Select3Box = new JComboBox<Object>(colors);
		Select3Box.setSelectedIndex(4);
		Select3Box.addActionListener(this);
		
		Select4Box = new JComboBox<Object>(colors);
		Select4Box.setSelectedIndex(4);
		Select4Box.addActionListener(this);
		
		selectors.add(Select1Box);
		selectors.add(Select2Box);
		selectors.add(Select3Box);
		selectors.add(Select4Box);
		
		if(Driver.pegs>=5){
			Select5Box = new JComboBox<Object>(colors);
			Select5Box.setSelectedIndex(4);
			Select5Box.addActionListener(this);
			selectors.add(Select5Box);
		}
		if(Driver.pegs==6){
			Select6Box = new JComboBox<Object>(colors);
			Select6Box.setSelectedIndex(4);
			Select6Box.addActionListener(this);
			selectors.add(Select6Box);
		}
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		head.add(selectors,c);;
		
		results = new JPanel();
		hitIt = new JButton("Check");
		hitIt.addActionListener(this);
		results.add(hitIt);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		head.add(results, c);
		
		remaining_turns = Driver.guesses;
		remaining = new JPanel();
		text = new JTextField("Turns Remaining: " + remaining_turns);
		text.setEditable(false);
		remaining.add(text);
		c.gridx = 0;
		c.gridy = 3;
		head.add(remaining, c);
		
		
		//rightPanel
		rightField = new JTextArea("Welcome to Mastermind. Here are the rules:\nThis is a programmed version of the classic board game Mastermind.\nThe computer will think of a secret code. The code consists of 4 colored pegs.\nThe pegs may be one of six colors: blue, green, orange, purple, red, or yellow. A color may appear\nmore than once in the code. You try to guess what colored pegs are in the code and what order they are\nin. After you make a valid guess the result (feedback) will be displayed.\nThe result consists of a black peg for each peg you have guessed exactly correct (color and position) in\nyour guess. For each peg in the guess that is the correct color, but is out of position, you get a white\npeg. For each peg, which is fully incorrect, you get no feedback.\nYou have 12 guesses to figure out the secret code or you lose the game.\nINSTRUCTIONS:\nUse the drop-down boxes to select your guess for the secret code.\nHit the 'Check' button and the left side of the board will record your guess.\nThe right side of the board will display how many of your guesses were \ncorrect and how many were the correct color but wrong location.\nWhen you guess correctly or miss on the last turn, the game ends.");
		//rightPanel.add("NORTH",rightField);
		rightField.setEditable(false);
		head.add(rightField);

		setContentPane(head);

		pack();
		setVisible(true);
	}	
}

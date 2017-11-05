/*Driver.java: main driver class that runs the game*/

public class Driver{
	static int GAME_CONDITION;//0 = in progress; 1 = game was won; -1 = game was lost
	static int guesses;
	static int colors;
	static int pegs;
	static Board GameBoard;
	static boolean GAME_OVER = false;

	public static void main(String[] args) {
		guesses = 12;
		colors = 4;
		pegs = 4;
		
		initiate D = new initiate();
		while(D.lock==true){
			guesses = D.numguesses.getValue();
			colors = 6 + D.numcolors.getSelectedIndex();
			pegs = 4+ D.numpegs.getSelectedIndex();
		}
		D.setVisible(false);
		GAME_OVER=false;
		GAME_CONDITION = 0;
		Display T = new Display(GameBoard);
		while(true){
			//main game loop
			GameBoard = new Board();
			
				boolean check = false;
				while(GAME_OVER == false){
					synchronized(T){
						
						//get the answer from user and check for a game over
						
						String[] sets = new String[Driver.pegs];
						sets[0] = (String) T.Select1Box.getSelectedItem();
						sets[1] = (String) T.Select2Box.getSelectedItem();
						sets[2] = (String) T.Select3Box.getSelectedItem();
						sets[3] = (String) T.Select4Box.getSelectedItem();
						if(Driver.pegs >=5){
							sets[4] = (String) T.Select5Box.getSelectedItem();
						}
						if(Driver.pegs == 6){
							sets[5] = (String) T.Select6Box.getSelectedItem();
						}
						if(Display.lock==0){
							check = Driver.GameBoard.isAnswer(sets);
							if(check==true){
								GAME_OVER = true;
								GAME_CONDITION = 1;
							}
							Display.lock=1;
						}
					}
				}

				T.ending(GameBoard, GAME_CONDITION);
				while(GAME_CONDITION!=0){
					synchronized(T){
						
					}
				}
				
				//Reinitialize game
				
				GAME_OVER = false;
				GAME_CONDITION = 0;
				D = new initiate();
				while(D.lock==true){
					guesses = D.numguesses.getValue();
					colors = 6 + D.numcolors.getSelectedIndex();
					pegs = 4+ D.numpegs.getSelectedIndex();
				}
				D.setVisible(false);
				T.reset();
			}
		}
	}



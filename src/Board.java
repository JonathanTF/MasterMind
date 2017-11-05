/*Board.java: saves the state of the game*/

import java.util.Random;

public class Board {
	
	String[][] board;
	String[][] leftBoard;
	String[][] rightBoard;
	int currentRow = 0;
	String[] answer;
	
	//turn the board ON
	public Board() {
		
		answer = new String[Driver.pegs];
		String[] colors = {"blue","green","orange","purple","red","yellow","maroon","silver"};
		
		//generate a new answer
		for(int i = 0;i<Driver.pegs;i++){
			Random random = new Random();
			int key = random.nextInt(Driver.colors);
			answer[i] = colors[key];
		}
		
		//fill the board with empty answers
		leftBoard = new String[Driver.guesses][Driver.pegs];
		rightBoard = new String[Driver.guesses][Driver.pegs];
		for(int y =0;y<Driver.guesses;y++){
			for(int x=0;x<Driver.pegs;x++){
				leftBoard[y][x] = "Empty";
				rightBoard[y][x] = "Empty";
			}
		}
	}
	
	//returns a string of the entire board
	public String getBoard() {
		
		String result = new String();
		
		for (int i = 0; i < Driver.guesses; i++) {
			for (int k = 0; k < Driver.pegs; k++) {
				result = result + board[i][k] + "  ";
			}
			result = result + "\n";
		}
		
		return result;
	}
	
	//set the current row answer
	public void setBoard(String sets[]){
		
		leftBoard[currentRow][0] = sets[0];
		leftBoard[currentRow][1] = sets[1];
		leftBoard[currentRow][2] = sets[2];
		leftBoard[currentRow][3] = sets[3];
		
		if(Driver.pegs>=5){
			leftBoard[currentRow][4] = sets[4];
		}
		if(Driver.pegs==6){
			leftBoard[currentRow][5] = sets[5];
		}
		setDifference(sets);
		if(currentRow<(Driver.guesses -1)){
			currentRow++;
		}else{
			Driver.GAME_OVER = true;
			if(isAnswer(sets)==false){
				Driver.GAME_CONDITION = -1;
			}
		}
	}
	
	//returns true if the following strings are the answer, false otherwise
	public boolean isAnswer(String[] sets){

		for(int i = 0;i<Driver.pegs;i++){
			if(!sets[i].equals(answer[i])){
				return false;
			}
		}
		
		return true;
	}
	
	//sets the hint board (right hand side)
	public void setDifference(String[] query){
		boolean[] correct = new boolean[Driver.pegs];
		boolean[] counted = new boolean[Driver.pegs];

		for(int i = 0;i<Driver.pegs;i++){
			correct[i] = false;
			counted[i] = false;
		}
		
		int[] result = new int[2];
		int matches = 0;
		
		for(int j = 0;j<Driver.pegs;j++){
			if(query[j].equals(answer[j])){
				matches++;
				correct[j] = true;
				counted[j] = true;
			}
		}
		
		result[0] = matches;
		int semiMatches = 0;
		//if correct[0] is false find out if another has the correct color that has not already been counted
		
		for(int i=0;i<Driver.pegs;i++){
			if(correct[i] == false && (counted[i]==false)){
				for(int j=0;j<Driver.pegs;j++){
					if((query[j]==answer[i])&&(counted[j]==false)){
						semiMatches++;
						counted[j] = true;
					}
				}
			}
		}
		
		result[1] = semiMatches;
		int k = 0;
		
		for(k=0;k<matches;k++){
			rightBoard[currentRow][k] = "black";
		}
		for(int l=0;l<semiMatches;l++){
			rightBoard[currentRow][k+l] = "white";
		}	
	}
}

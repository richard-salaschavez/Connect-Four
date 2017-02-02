/**
 * Maintain the current status of the board (i.e. where the pieces are)
 * Determine if a player has won. 
 * 
 * @author Richard Salas Chavez (7764077)
 * @version June 15, 2016
 */

import java.util.Arrays;

public class ConnectLogic implements ConnectController {
	private GameDisplay gd;
	private Status[][] board; 		// maintain current status of the board 
	private int difficulty;
	private ConnectAI testAI; 
	private int pieces;
	
	private final int WIDTH = 7; 	
	private final int HEIGHT = 6;	
	private final int MAX_DIFFICULTY = 2;
	
    /**
     * Constructor for objects of class ConnectLogic
     */////////////////////////////////////////////////////////////////////
	public ConnectLogic(GameDisplay gd) {
		this.gd = gd;
		board = new Status[HEIGHT][WIDTH]; // rows x columns
		//difficulty = gd.promptForOpponentDifficulty(MAX_DIFFICULTY); // prompt user for game difficulty
		// use above difficulty to make specific AI
		//makeAI(difficulty);
		pieces = 0;
	}
	
    /**
     * Take the last move made by the human player and return the next 
     * valid move made by the AI
     * 
     * @param  int lastCol -- last move played by human
     * @return     ai's next move
     */////////////////////////////////////////////////////////////////////
	private void makeAI(int difficulty){
		if (difficulty == 1)
			testAI = new ConnectAI1(); // make a new AI1 instance
		
		else if (difficulty == 2)
			testAI = new ConnectAI2(); // make a new AI2 instance
	}
	
    /**
     * Goes up specific column to check for the next available position if any.
     *
     * @param   col, column number
     * @return true, if there is a valid position, otherwise returns false
     */////////////////////////////////////////////////////////////////////
	public boolean addPiece(int col){
		
		int row = whichRow(col);
		
		if (row >= 0) {
			board[row][col] =  Status.ONE; 	// add piece to correct row
			pieces++;
			gd.updateBoard(board);			// update board
			Status status = getWinner();
			
			if (!status.equals(Status.NEITHER))
				gd.gameOver(status);
			else // neither has won yet
				aiMove(col);
			
			return true;
		}
		
		else {
			return false;
		}
	}
	
    /**
     * resets board by simply creating a fresh new board, prompts user for 
     * difficulty and makes the correct AI
     */////////////////////////////////////////////////////////////////////
	public void reset(){ // should this simply make a new TextBoard and reset it?
		board = new Status[HEIGHT][WIDTH];
		
		for (int i = 0; i < HEIGHT; i++) {			// how does this work?
			Arrays.fill(board[i], Status.NEITHER);	// board will be empty
		}
		
		difficulty = gd.promptForOpponentDifficulty(MAX_DIFFICULTY); // prompt user for game difficulty
		makeAI(difficulty); // use above difficulty to make specific AI
		
		//gd.updateBoard(board);
		pieces = 0;
	}
	
    /**
     * loops up column looking for next empty spot
     * 
     * @param  int col -- column you wish to insert piece into
     * @return returns row in which to insert, if row = -1 then move is invalid
     */////////////////////////////////////////////////////////////////////
	private int whichRow(int col) {
		int row = HEIGHT - 1;					// 5
		Status s = board[row][col]; 			// status of position
		
		while(row >= 0) {
			if (s.equals(Status.NEITHER)) {
				return row;
			}
			row--;
			if (row >= 0)
				s = board[row][col];
		}
		return row;
	}
		
    /**
     * calls the AI's makeMove method using polymorphism!
     * 
     * @param  int lastCol -- column human put part in
     */////////////////////////////////////////////////////////////////////
	private void aiMove(int lastCol){
		
		int col = testAI.makeMove(lastCol);
		int row = whichRow(col);

		board[row][col] = Status.TWO; 	// add piece to correct row
		pieces++;
		gd.updateBoard(board);			// update board
		
		winner(); 						// check if AI has won yet?
	}
	
	/**
     * Determines if a player has won, or it is a draw if board is full
     *
     */
	private void winner(){
		Status status = getWinner();

		if (!status.equals(Status.NEITHER))
			gd.gameOver(status);
		else if (pieces == 42)
			gd.gameOver(Status.NEITHER);
	}
	
    /**
     * Determines if a player has won by checking if there are four 
     * pieces in a row, column, or diagonal
     *
     * @return Status of player who has won or NEITHER if no one has won yet
     */
	private Status getWinner(){
		// loops trough board and check if there are four in a row 
		// if there is a winner, call gameOver
		
		int human = 0; // how many consecutive pieces are the same
		int ai = 0; 
		
		Status currentStatus;
		
		//check rows		
		for(int row = 0; row < HEIGHT; row++){
			human = 0;
			ai = 0;
			
			for(int col = 0; col < WIDTH; col++){
				currentStatus = board[row][col];
				
				if (currentStatus.equals(Status.ONE)) {
					human++;
					ai = 0;
				}
				
				else if (currentStatus.equals(Status.TWO)){
					ai++;
					human = 0;
				}
				
				else {
					human = 0;
					ai = 0;
				}
				
				// check if there is a winner
				if (human == 4)
					return Status.ONE;
				else if (ai == 4)
					return Status.TWO;			
			
			}
			
		}
		
		human = 0;
		ai = 0;

		//check columns
		for(int col = 0; col < WIDTH; col++){
			human = 0;
			ai = 0;
			
			for(int row = 0; row < HEIGHT; row++){
				
				currentStatus = board[row][col];
				
				if (currentStatus.equals(Status.ONE)) {
					human++;
					ai = 0;
				}
				
				else if (currentStatus.equals(Status.TWO)){
					ai++;
					human = 0;
				}
				
				else {
					human = 0;
					ai = 0;
				}
				
				// check if there is a winner
				if (human == 4)
					return Status.ONE;
				else if (ai == 4)
					return Status.TWO;	
			}
		}
		
		human = 0;
		ai = 0;
		
		// check (\) diagonals
        for (int row = HEIGHT - 4; row >= 0; row--) {
    		
            for (int col = WIDTH - 4; col >= 0; col--) {
            	human = 0;
        		ai = 0;
            	
                for (int i = 3; i >= 0; i--) {
                	currentStatus = board[row + i][col + i];
                	
    				if (currentStatus.equals(Status.ONE))
    					human++;
    				else if (currentStatus.equals(Status.TWO))
    					ai++;
                }
                
                // check if there is a winner
				if (human == 4)
					return Status.ONE;
				
				else if (ai == 4)
					return Status.TWO;
            }
        }
        
        human = 0;
        ai = 0;
        
		// check (/) diagonals
        for (int row = HEIGHT - 4; row >= 0; row--) {
        	
            for (int col = WIDTH - 4; col >= 0; col--) {
            	human = 0;
            	ai = 0;
            	
                for (int i = 3; i >= 0; i--) {
                   	currentStatus = board[row + i][col - i + 3];
                   	
    				if (currentStatus.equals(Status.ONE))
    					human++;
    				else if (currentStatus.equals(Status.TWO))
    					ai++; 
                }
                
                // check if there is a winner
				if (human == 4)
					return Status.ONE;
				
				else if (ai == 4)
					return Status.TWO;
            }
        }
		
        return Status.NEITHER;	
	}
}

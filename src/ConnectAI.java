/**
 * abstract class which all gameAIs will inherit, to allow for polymorphism.
 * 
 * @author Richard Salas Chavez (7764077)
 * @version June 10, 2016
 */

import java.util.Arrays;

public abstract class ConnectAI {
	
	private final int WIDTH = 7; 	
	private final int HEIGHT = 6;	
	private Status[][] board;
	
    /**
     * Constructor for objects of class ConnectAI
     */////////////////////////////////////////////////////////////////////
	public ConnectAI(){
		board = new Status[HEIGHT][WIDTH];
		for (int i = 0; i < HEIGHT; i++) {			// how does this work?
			Arrays.fill(board[i], Status.NEITHER);	// board will be empty
		}
	}
	
    /**
     * Take the last move made by the human player and return the next 
     * valid move made by the AI.
     * 
     * @param  int lastCol -- last move played by human
     * @return     ai's next move
     */////////////////////////////////////////////////////////////////////
	abstract public int makeMove(int lastCol);
	
    /**
     * adds piece to board using provided player status
     * 
     * @param  int col, Status player -- 
     */////////////////////////////////////////////////////////////////////
	protected void addPiece(int col, Status player){
		int row = whichRow(col);
		board[row][col] = player; 	// add piece to correct row
	}
	
    /**
     * loops up column looking for next empty spot
     * 
     * @param  int col -- column you wish to insert piece into
     * @return returns row in which to insert, if row = -1 then move is invalid
     */////////////////////////////////////////////////////////////////////
	protected int whichRow(int col) {
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
     * check status of board at a specified position 
     * 
     * @param  int row, int column -- specified position
     * @return returns Status of specified position
     */////////////////////////////////////////////////////////////////////
	protected Status checkStatus(int row, int column){
		return board[row][column];
	}
	
    /**
     * getter -- gets HEIGHT
     */////////////////////////////////////////////////////////////////////
	public int getHeight(){
		return HEIGHT;
	}
	
    /**
     * getter -- gets WIDTH
     */////////////////////////////////////////////////////////////////////
	public int getWidth(){
		return 	WIDTH;
	}
	
}

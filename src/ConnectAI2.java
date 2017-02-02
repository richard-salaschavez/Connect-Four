/**
 * Controls which moves the game AI2 will make. It checks if there are three pieces in either a row or column.
 * If there is, it places a piece in the correct place to either extend or block the row. If there aren't any
 * rows or columns then it randomly chooses a column as long as it's not full.
 * 
 * @author Richard Salas Chavez (7764077)
 * @version June 15, 2016
 */

import java.util.Random;

public class ConnectAI2 extends ConnectAI implements ConnectPlayer {
	
    /**
     * Constructor for objects of class ConnectAI2
     */////////////////////////////////////////////////////////////////////
	public ConnectAI2(){
		super();
	}
		
    /**
     * Take the last move made by the human player and return the next 
     * valid move made by the AI
     * 
     * @param  int lastCol -- last move played by human
     * @return     ai's next move
     */////////////////////////////////////////////////////////////////////
	public int makeMove(int lastCol) {
		addPiece(lastCol, Status.ONE);
		
		int three = threeInARow();
		
		if (three >= 0 && whichRow(three) >= 0) {
			addPiece(three, Status.TWO);
			return three;
		}
		
		else {
			int aiMove = getValidMove();
			addPiece(aiMove, Status.TWO);
			return aiMove;			
		}
	}
	
    /**
     * It randomly chooses a column, and checks that the column is not full.
     * 
     * @return	random valid column
     */////////////////////////////////////////////////////////////////////
	private int getValidMove(){
		Random rand = new Random(); // insert into a random column if it isn't full
		int randMove = rand.nextInt(7);
		int row = whichRow(randMove);
		
		while (row < 0) {
			randMove = rand.nextInt(7);
			row = whichRow(randMove);
		}
		
		return randMove;
	}
	
    /**
     * checks if there are three pieces in either a row or column.
     * If there is, it places a piece in the correct place to either 
     * extend or block the row. Also checks if its move is valid,
     * i.e. the column isn't full.
     * 
     * @return     ai's next move
     */////////////////////////////////////////////////////////////////////
	private int threeInARow(){
		// loops trough board and check if there are four in a row 
		// if there is a winner, call gameOver
		
		int human = 0; // how many consecutive pieces are the same
		int ai = 0; 
		
		Status currentStatus;
		
		//check rows		
		for(int row = 0; row < getHeight(); row++){
			human = 0;
			ai = 0;
			
			for(int col = 0; col < getWidth(); col++){
				currentStatus = checkStatus(row, col);
				
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
				
				if ((human == 3 || ai == 3) && (col >= 3 && col < 6)){
					if (checkStatus(row, col + 1).equals(Status.NEITHER))
						if (whichRow(col + 1) >= 0)
							return col + 1; // block next column	
					
					else if (checkStatus(row, col - 3).equals(Status.NEITHER))
						if (whichRow(col-3) >= 0)
							return col - 3;
				}
			}
		}
		
		human = 0;
		ai = 0;

		//check columns
		for(int col = 0; col < getWidth(); col++){
			human = 0;
			ai = 0;
			
			for(int row = 0; row < getHeight(); row++){
				
				currentStatus = checkStatus(row, col);
				
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
				
				if (human == 3 || ai == 3)
					if (whichRow(col) >= 0)
						return col;	
			}
		}
		return -1;
	}
}
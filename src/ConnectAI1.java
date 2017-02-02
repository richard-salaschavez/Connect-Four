/**
 * Controls which moves the game AI1 will make. It randomly chooses a column as long as it's not full.
 * 
 * @author Richard Salas Chavez (7764077)
 * @version June 15, 2016
 */

import java.util.Random;

public class ConnectAI1 extends ConnectAI implements ConnectPlayer {
	
    /**
     * Constructor for objects of class ConnectAI1
     */////////////////////////////////////////////////////////////////////
	public ConnectAI1() {
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
		int aiMove = getValidMove();
		addPiece(aiMove, Status.TWO);
		
		return aiMove;
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
	
}

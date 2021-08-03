import java.util.ArrayList;
public class Knight extends Piece {
	public Knight(String color)	{
		super(color);
	}
	
	public boolean canMove(String newPosition) {
		String[] moves = getAllMoves();
		for(int i = 0; i < moves.length; i++)
			if(newPosition.equals(moves[i]))
				return true;
		return false;
	}
	
	public String[] getAllMoves() {
		String[] sortedMoves;
		ArrayList<String> sortedMovesList = new ArrayList<String>();
		
		 // char indexes
		for(int i = 0; i <= 7; i++) {
			 // position nums
			for(int j =  1; j <= 8; j++) {
				if((Math.abs(i - (getPositionCharNum())) == 2 && Math.abs(j - getPositionNum()) == 1) || (Math.abs(i - (getPositionCharNum())) == 1 && Math.abs(j - getPositionNum()) == 2))
					sortedMovesList.add((char)('a' + i) + Integer.toString(j));
			}
		}
		
		sortedMoves = new String[sortedMovesList.size()];
		for(int i = 0; i < sortedMoves.length; i++) {
			sortedMoves[i] = sortedMovesList.get(i);
		}
		
		return sortedMoves;		
	}
}
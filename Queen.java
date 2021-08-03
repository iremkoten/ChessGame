import java.util.ArrayList;
public class Queen extends Piece {
	public Queen(String color)	{
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
		String position = getPosition();
		
		// char index + position number. Example:Position c3 = 2 + 3
		int sumIndex = getPositionCharNum() + getPositionNum();
		
		// char index + matrix value. Example: Position c3 corresponds (5,2) in the matrix. So c3 = (8 - 3) + 2
		int sumIndex2 = getPositionCharNum() +  getPositionMatrixNum();
		
		// char indexes
		for(int i = 0; i <= 7; i++) {
			// position nums
			for(int j = 1; j <= 8; j++) { 
				if(position.charAt(0) != 'a' + i) {
					if(i + j == sumIndex || i + (8 - j) == sumIndex2 || j == getPositionNum())
						sortedMovesList.add((char)('a' + i) + Integer.toString(j));
				} else {
					 // position nums.
					for(int k = 1; k <= 8; k++)
						if(k != position.charAt(1) - '0')
							sortedMovesList.add(position.substring(0,1) + Integer.toString(k));
					i++;
				}
			}
		}
		
		sortedMoves = new String[sortedMovesList.size()];				
		for(int i = 0; i < sortedMoves.length; i++) {
			sortedMoves[i] = sortedMovesList.get(i);
		}
		return sortedMoves;
	}
}
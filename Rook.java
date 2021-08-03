import java.util.ArrayList;
public class Rook extends Piece {
	public Rook(String color) {
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
		
		// char indexes
		for(int i = 0; i <= 7; i++) {
			if(position.charAt(0) != 'a' + i) {
				sortedMovesList.add((char)('a' + i) + position.substring(1,2));
			} else {
				// position nums
				for(int j = 1; j <= 8; j++) {
					if(j != position.charAt(1) - '0')
						sortedMovesList.add(position.substring(0,1) + Integer.toString(j));
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
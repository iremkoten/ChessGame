import java.util.ArrayList;
public class Pawn extends Piece {
	public Pawn (String color) {
		super(color);
	}
	
	public boolean canMove (String newPosition) {
		String[] moves = getAllMoves();
		for(int i = 0; i < moves.length; i++)
			if(newPosition.equals(moves[i]))
				return true;
		return false;
	}
	
	public String[] getAllMoves(){
		String[] sortedMoves;
		ArrayList<String> sortedMovesList = new ArrayList<String>();
		String position = getPosition();
		
		// char indexes
		for(int i = 0; i <= 7; i++) {
			// position nums.
			for(int j =  1; j <= 8; j++) { 
				
				if(getColor().equals("white")) {
					if(j - getPositionNum() == 1 && Math.abs(i - (getPositionCharNum())) <= 1) {
						sortedMovesList.add((char)('a' + i) + Integer.toString(j));
					}
				} else {
    				if(j - getPositionNum() == -1 && Math.abs(i - (getPositionCharNum())) <= 1)
    					sortedMovesList.add((char)('a' + i) + Integer.toString(j));
    			}
			}
		
			if((char)('a' + i) == position.charAt(0) && position.charAt(1) == '2' && getColor().equals("white"))
				sortedMovesList.add(position.charAt(0) + "4");
			
			if((char)('a' + i) == position.charAt(0) && position.charAt(1) == '7' && getColor().equals("black"))
				sortedMovesList.add(1, position.charAt(0) + "5");
		}

		sortedMoves = new String[sortedMovesList.size()];
		for(int i = 0; i < sortedMoves.length; i++) {
			sortedMoves[i] = sortedMovesList.get(i);
		}
		
		return sortedMoves;		
	}
}
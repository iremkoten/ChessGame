import java.util.ArrayList;
public class Board {
	private Piece[][] board = new Piece[8][8];
	
	public Board() 	{
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board.length; j++)
				board[i][j] = null;
	}
	
	public Piece[][] getBoard()	{
		return board;
	}
	
	public boolean putPiece(Piece p, String pos) {
		p.setPosition(pos);
		int y = p.getPositionCharNum();
		int x = p.getPositionMatrixNum();
		board[x][y] = p;
		return true;
	}
	
	public Piece getPiece(String pos) {
		int y = pos.charAt(0) - 'a';
		int x = 8 - Integer.parseInt(pos.substring(1,2));
		return board[x][y];
	}
	
	public boolean betweenIsEmpty(String pos1, String pos2) {
		int x = 8 - Integer.parseInt(pos1.substring(1,2));
		int y = pos1.charAt(0) - 'a';
		int diffX = (8 - Integer.parseInt(pos2.substring(1,2))) - (8 - Integer.parseInt(pos1.substring(1,2)));
		int diffY = (pos2.charAt(0) - 'a') - (pos1.charAt(0) - 'a');
		
		// If positions are not intersecting in a particular position, then there is no need to look at their between positions
		if(Math.abs(diffX) == Math.abs(diffY) || (diffX == 0 || diffY == 0)) {
			int posSign = (int)Math.signum(diffX);
			int charSign = (int)Math.signum(diffY);
			x += posSign;
			y += charSign;
			while(x != 8 - Integer.parseInt(pos2.substring(1,2)) || y != pos2.charAt(0) - 'a') {
				if(board[x][y] != null)
					return false;
				x += posSign;
				y += charSign;			
			}
		}
		
		return true;		
	}
	
	public Piece getPieceWhoIsChecking(String color) {
		ArrayList<Piece> pieces;
		if(color.equals("white"))
			pieces = getAllPieces("black");
		else
			pieces = getAllPieces("white");
		
		Piece king = null;
		Piece checkingPiece = null;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(board[i][j] instanceof King && board[i][j].getColor().equals(color))
					king = board[i][j];
		
		for(Piece piece: pieces) {
			String[] moves = piece.getAllMoves();
			for(int i = 0; i < moves.length; i++) {
				if(moves[i].equals(king.getPosition()))
					if(piece instanceof Knight)
						checkingPiece = piece;
					else if(piece instanceof Pawn && moves[i].charAt(0) == piece.getPosition().charAt(0))
						checkingPiece = null;
					else
						if(betweenIsEmpty(king.getPosition(), piece.getPosition()))
							checkingPiece = piece;
			}
		}
		
		return checkingPiece;
	}
	
	// Returns pieces that have same color with given color as paramater
	private ArrayList<Piece> getAllPieces(String color)	{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(board[i][j] != null && board[i][j].getColor().equals(color))
					pieces.add(board[i][j]);
		
		return pieces;
	}
	
	public boolean check(String color) {
		ArrayList<Piece> pieces;
		if(color.equals("white"))
			pieces = getAllPieces("black");
		else
			pieces = getAllPieces("white");
		
		Piece king = getKing(color);
		for(Piece piece: pieces) {
			String[] moves = piece.getAllMoves();
			for(int i = 0; i < moves.length; i++) {
				if(moves[i].equals(king.getPosition())) {
					if(piece instanceof Pawn && moves[i].charAt(0) == piece.getPosition().charAt(0))
						return false;
					if(betweenIsEmpty(king.getPosition(), piece.getPosition()))
						return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean checkMate(String color) {
		Piece king = getKing(color);
		
		if(check(color)) {
			ArrayList<Piece> pieces = getAllPieces(color);

			for(Piece piece: pieces) {
				String[] moves = piece.getAllMoves();
				if(piece instanceof King) {
					for(String kingMoves: moves)
						if(movePiece(king, kingMoves))
							return false;
				} else {
					// CheckPositions are positions that between king and the piece who is checking king.
					ArrayList<String> checkPositions = getBetweenPositionsForCheckPositions(king.getPosition(), getPieceWhoIsChecking(color).getPosition());
					
					// Need to examine checkPositions and the position of the piece who is checking king for find out whether checkmate can prevented.
					for(int i = 0; i < moves.length; i++)
						for(int j = 0; j < checkPositions.size(); j++)
							if(moves[i].equals(checkPositions.get(j)) && movePiece(piece, checkPositions.get(j)) || (moves[i].equals(getPieceWhoIsChecking(color).getPosition()) && movePiece(piece, getPieceWhoIsChecking(color).getPosition())))
								return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private ArrayList<String> getBetweenPositionsForCheckPositions(String pos1, String pos2) {
		ArrayList<String> positions = new ArrayList<String>();
		if(getPiece(pos1) instanceof Knight || getPiece(pos2) instanceof Knight || getPiece(pos1) instanceof Pawn || getPiece(pos2) instanceof Pawn)
			if(getPiece(pos1) instanceof Knight || getPiece(pos1) instanceof Pawn)
				positions.add(pos1);
			else
				positions.add(pos2);
		else
		{
			int x = 8 - Integer.parseInt(pos1.substring(1,2));
			int y = pos1.charAt(0) - 'a';
			int posSign = (int)Math.signum((8 - Integer.parseInt(pos2.substring(1,2))) - (8 - Integer.parseInt(pos1.substring(1,2))));
			int charSign = (int)Math.signum((pos2.charAt(0) - 'a') - (pos1.charAt(0) - 'a'));
			x += posSign;
			y += charSign;
			while(x != 8 - Integer.parseInt(pos2.substring(1,2)) || y != pos2.charAt(0) - 'a')
			{
				positions.add((char)('a' + y) + Integer.toString(8 - x));
				x += posSign;
				y += charSign;
			}			
		}

		return positions;
	}
	
	public boolean isEmpty(String pos) {
		return getPiece(pos) == null;
	}
	
	private boolean movePiece(Piece p, String newPosition) {
		String position = p.getPosition();
		if((p.canMove(newPosition) && betweenIsEmpty(position, newPosition)) && (getPiece(newPosition) == null || (getPiece(newPosition) != null && !getPiece(newPosition).getColor().equals(p.getColor())))) {
			if(p instanceof Pawn && ((p.getPosition().charAt(0) == newPosition.charAt(0) && getPiece(newPosition) != null) || (p.getPosition().charAt(0) != newPosition.charAt(0) && getPiece(newPosition) == null)))
				return false;
			Piece otherPiece = null;
			if(getPiece(newPosition) != null)
				otherPiece = getPiece(newPosition);
			removePiece(position);
			putPiece(p, newPosition);
			if(check(p.getColor())) {
				removePiece(p.getPosition());
				putPiece(p, position);
				if(otherPiece != null)
					putPiece(otherPiece, newPosition);
				return false;
			}
			removePiece(p.getPosition());
			putPiece(p, position);
			if(otherPiece != null)
				putPiece(otherPiece, newPosition);
			return true;
		}
		
		return false;
	}
	
	private boolean removePiece(String pos) {
		if(isEmpty(pos))
			return false;
		int y = pos.charAt(0) - 'a';
		int x = 8 - Integer.parseInt(pos.substring(1,2));
		board[x][y] = null;
		return true;
	}
	
	private Piece getKing(String color)	{
		Piece king = null;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(board[i][j] instanceof King && board[i][j].getColor().equals(color))
					king = board[i][j];
		return king;
	}
}
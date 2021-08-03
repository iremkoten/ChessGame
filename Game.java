public class Game {
	
	public static void main(String[] args) {
		Board B = new Board();
    
        B.putPiece(new King("black"), "g5");
        B.putPiece(new King("white"), "f3");
        B.putPiece(new Pawn("white"), "g3");
        B.putPiece(new Pawn("white"), "h4");
        B.putPiece(new Pawn("black"), "f5");
        B.putPiece(new Pawn("black"), "h5");
        B.putPiece(new Rook("white"), "b6");

        if (B.checkMate("white"))
            System.out.println("Siyah kazandi");
        
        if (B.checkMate("black"))
            System.out.println("Beyaz kazandi");
	}
}
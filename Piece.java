public class Piece {
	private String position;
	private String color;
	
	public Piece(String color) {
		this.color = color;
	}
	
	public boolean canMove(String newPosition) {
		return false;
	}
	
	public String[] getAllMoves() {
		return null;
	}
	
	public void setPosition(String newPosition)	{
		position = newPosition;
	}
	
	public String getPosition()	{
		return position;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getPositionNum()	{
		return Integer.parseInt(position.substring(1,2));
	}
	
	public int getPositionMatrixNum() {
		return 8 - Integer.parseInt(position.substring(1,2));
	}
	
	public int getPositionCharNum()	{
		return position.charAt(0) - 'a';
	}
}
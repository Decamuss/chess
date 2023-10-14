package chess;



public class Bishop extends Piece {
    
    // Constructor to set the color of the Bishop
    public Bishop(boolean white) {
        super(white);  // This calls the constructor of the Piece class
    }
    
    @Override
    public boolean canMove(int oldX, int oldY, int newX, int newY, boolean isNewSpotEmpty) {
        // Bishops move diagonally, so the absolute difference between the x-coordinates
        // and y-coordinates of the old position and the new position should be equal.
        return Math.abs(oldX - newX) == Math.abs(oldY - newY);
    }
    
    @Override
    public void movePiece() {
        // Implement the logic to move the piece.
        // This might involve updating the board and the piece's position.
    }
    
    @Override
    public String drawPiece() {
        // Return a string representation of the Bishop.
        // For simplicity, we'll use "B" to represent a Bishop.
        return isWhite() ? "B" : "b";
    }
}

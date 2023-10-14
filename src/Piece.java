package chess;

import java.util.List;

// Abstract class representing a general chess piece
public abstract class Piece {
    
    // Enum for Piece Color
    public enum Color {
        WHITE, BLACK;
    }

    // Member variables
    protected Color color;
    protected int x, y;

    // Constructor
    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    private boolean white = true;
    public boolean firstMove = true;

    // New constructor
    public Piece(boolean white) {
        this.white = white;
    }

    // Abstract method to be implemented by each specific type of piece
    public abstract List<String> getPossibleMoves();

    // Getters for color and position
    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Method to move the piece to a new position
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}

// Subclass for Pawn
class Pawn extends Piece {
    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public List<String> getPossibleMoves() {
        // Implement pawn-specific logic to generate possible moves
        return null;  // Placeholder
    }
}

// Similarly, you would create subclasses for other types of pieces: Rook, Knight, Bishop, Queen, and King.

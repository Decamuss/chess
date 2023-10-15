package chess;

public class Bishop extends ReturnPiece {

    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;  // Assume PieceType.BB for black bishop or PieceType.WB for white bishop
        this.pieceFile = pieceFile;  // Assume PieceFile.a through PieceFile.h
        this.pieceRank = pieceRank;  // Assume 1 through 8
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, Board board) {
        // Check if the move is along a diagonal
        if (Math.abs(oldX - newX) != Math.abs(oldY - newY)) {
            return false;
        }

        // Determine the direction of the move
        int xDirection = (newX > oldX) ? 1 : -1;
        int yDirection = (newY > oldY) ? 1 : -1;

        // Check for any pieces blocking the path
        int x = oldX + xDirection;
        int y = oldY + yDirection;
        while (x != newX && y != newY) {
            if (!board.isSpotEmpty(x, y) || board.isSameColor(x, y, this.isWhite())) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }

        // Check if the destination spot is empty or contains an opponent's piece
        if (!board.isSpotEmpty(newX, newY) || board.isSameColor(newX, newY, this.isWhite())) {
            return false;
        }

        return true;
    }

    public void move(int newX, int newY) {
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;
    }

    public boolean isWhite(){
        return this.pieceType.toString().charAt(0) == 'W';
    }

    @Override
    public String toString() {
        return super.toString() + ":Bishop";
    }
}

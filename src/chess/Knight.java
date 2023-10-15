package chess;

public class Knight extends ReturnPiece {

    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;  // Assume PieceType.BN for black knight or PieceType.WN for white knight
        this.pieceFile = pieceFile;  // Assume PieceFile.a through PieceFile.h
        this.pieceRank = pieceRank;  // Assume 1 through 8
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, Board board) {
        int xDiff = Math.abs(oldX - newX);
        int yDiff = Math.abs(oldY - newY);
        boolean isMoveLShape = (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2);

        if (!isMoveLShape) {
            return false;
        }

        if (!board.isSpotEmpty(newX, newY) && board.isSameColor(newX, newY, this.isWhite())) {
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
        return super.toString() + ":Knight";
    }
}

package chess;

public class King extends ReturnPiece {

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, Board board) {
        int deltaX = Math.abs(oldX - newX);
        int deltaY = Math.abs(oldY - newY);
        if (deltaX > 1 || deltaY > 1) {
            return false;
        }

        return !board.isSameColor(newX, newY, this.isWhite());
    }

    public void move(int newX, int newY) {
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;
    }

    public boolean isWhite() {
        return this.pieceType.toString().charAt(0) == 'W';
    }

    @Override
    public String toString() {
        return super.toString() + ":King";
    }
}

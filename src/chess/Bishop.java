package chess;

public class Bishop extends ReturnPiece {

    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, Board board) {
        if (Math.abs(oldX - newX) != Math.abs(oldY - newY)) {
            return false;
        }

        int xDirection = (newX > oldX) ? 1 : -1;
        int yDirection = (newY > oldY) ? 1 : -1;
        int steps = Math.abs(newX - oldX);

        for (int i = 1; i < steps; i++) {
            int x = oldX + i * xDirection;
            int y = oldY + i * yDirection;
            if (!board.isSpotEmpty(x, y) || board.isSameColor(x, y, this.isWhite())) {
                return false;
            }
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
        return super.toString() + ":Bishop";
    }
}

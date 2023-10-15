package chess;

public class Queen extends ReturnPiece {

    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, Board board) {
        boolean isRookMove = (oldX == newX || oldY == newY);
        boolean isBishopMove = (Math.abs(oldX - newX) == Math.abs(oldY - newY));
        if (!isRookMove && !isBishopMove) {
            return false;
        }

        int stepX = (newX > oldX) ? 1 : (newX < oldX) ? -1 : 0;
        int stepY = (newY > oldY) ? 1 : (newY < oldY) ? -1 : 0;

        for (int x = oldX + stepX, y = oldY + stepY; x != newX || y != newY; x += stepX, y += stepY) {
            if (!board.isSpotEmpty(x, y) || board.isSameColor(x, y, this.isWhite())) {
                return false;
            }
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
        return super.toString() + ":Queen";
    }
}

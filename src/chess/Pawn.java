package chess;

import java.util.ArrayList;

public class Pawn extends ReturnPiece implements Piece{

    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {

        oldY -=1;
        newY -=1;
        int direction = (this.pieceType.toString().charAt(0) == 'W') ? 1 : -1;  // White pawns move up, black pawns move down
        
        // Check for standard one-step forward move
        if (oldX == newX && oldY + direction == newY && isSpotEmpty(newX, newY, piecesOnBoard)) {
            return true;
        }
        
        // Check for initial two-step forward move
        if (oldX == newX && oldY + (2 * direction) == newY && isSpotEmpty(newX, newY, piecesOnBoard) && isSpotEmpty(newX, oldY + direction, piecesOnBoard)) {
            return true;
        }
        
        // Check for diagonal capture
        if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard) && !isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            return true;
        }
        
        return false;
    }

    private boolean isSpotEmpty(int x, int y, ArrayList<ReturnPiece> piecesOnBoard) {
        for (ReturnPiece piece : piecesOnBoard) {
            int pieceX = piece.pieceFile.ordinal();
            int pieceY = piece.pieceRank - 1;
            if (pieceX == x && pieceY == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isSameColor(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        boolean isWhite = Chess.game.piecesOnBoard.get(oldX + oldY * 8).pieceType.toString().charAt(0) == 'W';
        for (ReturnPiece piece : piecesOnBoard) {
            int pieceX = piece.pieceFile.ordinal();
            int pieceY = piece.pieceRank - 1;
            if (pieceX == newX && pieceY == newY) {
                return piece.pieceType.toString().charAt(0) == (isWhite ? 'W' : 'B');
            }
        }
        return false;
    }

    public void move(int newX, int newY) {
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY;
    }

    @Override
    public String toString() {
        return super.toString() + ":Pawn";
    }
}

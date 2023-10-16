package chess;

import java.util.ArrayList;

public class Pawn extends ReturnPiece implements Piece{
    public boolean hasMoved =false;

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
        if (hasMoved == false && oldX == newX && oldY + (2 * direction) == newY && isSpotEmpty(newX, newY, piecesOnBoard) && isSpotEmpty(newX, oldY + direction, piecesOnBoard)) {
            this.hasMoved = true;
            return true;
        }
        
        // Check for diagonal capture
        if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard)){
            if(!isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
            piecesOnBoard.remove(capturedPiece);
            return true;
            }
            return false;
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
        ReturnPiece oldPiece = getPieceAt(oldX, oldY, piecesOnBoard);
        ReturnPiece newPiece = getPieceAt(newX, newY, piecesOnBoard);
        if (oldPiece != null && newPiece != null) {
            return oldPiece.pieceType.toString().charAt(0) == newPiece.pieceType.toString().charAt(0);
        }
        return false;
    }

    public void move(int newX, int newY) {
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY;
    }



    private ReturnPiece getPieceAt(int x, int y, ArrayList<ReturnPiece> piecesOnBoard) {
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceFile.ordinal() == x && piece.pieceRank == y + 1) {
                return piece;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ":Pawn";
    }
}

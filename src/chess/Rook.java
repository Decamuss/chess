package chess;

import java.util.ArrayList;

public class Rook extends ReturnPiece implements Piece{

    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        // Check if the move is along a straight line

        oldY -=1;
        newY -=1;
        if (oldX != newX && oldY != newY) {
            return false;
        }

        // Determine the direction of the move
        int xDirection = (newX > oldX) ? 1 : (newX < oldX) ? -1 : 0;
        int yDirection = (newY > oldY) ? 1 : (newY < oldY) ? -1 : 0;

        // Check for any pieces blocking the path
        int x = oldX + xDirection;
        int y = oldY + yDirection;
        while (x != newX || y != newY) {
            if (!isSpotEmpty(x, y, piecesOnBoard) || isSameColor(oldX, oldY, x, y, piecesOnBoard)) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }

        if (!isSpotEmpty(newX, newY, piecesOnBoard)) {
            if (!isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
                // Capture opponent's piece
                ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
                piecesOnBoard.remove(capturedPiece);
                return true;
            }
            return false;
        }
        
        return true;
    }

    private boolean isSpotEmpty(int x, int y, ArrayList<ReturnPiece> piecesOnBoard) {
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceFile.ordinal() == x && piece.pieceRank == y + 1) {
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

    private ReturnPiece getPieceAt(int x, int y, ArrayList<ReturnPiece> piecesOnBoard) {
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceFile.ordinal() == x && piece.pieceRank == y + 1) {
                return piece;
            }
        }
        return null;
    }

    public void move(int newX, int newY) {
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY;
    }

    public boolean isWhite() {
        return this.pieceType.toString().charAt(0) == 'W';
    }

    @Override
    public String toString() {
        return super.toString() + ":Rook";
    }
}

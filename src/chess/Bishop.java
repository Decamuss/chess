package chess;

import java.util.ArrayList;

public class Bishop extends ReturnPiece implements Piece{

    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        // Ensure the move is along a diagonal
        if (Math.abs(oldX - newX) != Math.abs(oldY - newY)) {
            return false;
        }

        int stepX = (newX > oldX) ? 1 : -1;
        int stepY = (newY > oldY) ? 1 : -1;

        // Check for any pieces blocking the path
        for (int x = oldX + stepX, y = oldY + stepY; x != newX || y != newY; x += stepX, y += stepY) {
            if (getPieceAt(x, y, piecesOnBoard) != null) {
                return false;
            }
        }

        // Check if the destination spot is occupied by a piece of the same color
        if (isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            return false;
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
        this.pieceRank = newY + 1;
    }

    public boolean isWhite() {
        return this.pieceType.toString().charAt(0) == 'W';
    }

    @Override
    public String toString() {
        return super.toString() + ":Bishop";
    }
}

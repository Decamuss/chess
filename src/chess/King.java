package chess;

import java.util.ArrayList;

public class King extends ReturnPiece implements Piece {

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        // Check if the move is a single step in any direction
        oldY -=1;
        newY -=1;
        if (Math.abs(oldX - newX) > 1 || Math.abs(oldY - newY) > 1) {
            return false;
        }

        // // Check if the destination spot is empty or contains an opponent's piece
        // if (!isSpotEmpty(newX, newY, piecesOnBoard) || isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
        //     return false;
        // }

        //method to see if this piece can capture
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
        return super.toString() + ":King";
    }
}

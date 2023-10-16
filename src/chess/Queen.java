package chess;

import java.util.ArrayList;

public class Queen extends ReturnPiece implements Piece{

    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        oldY -=1;
        newY -=1;
        boolean isRookMove = (oldX == newX || oldY == newY);
        boolean isBishopMove = (Math.abs(oldX - newX) == Math.abs(oldY - newY));
        if (!isRookMove && !isBishopMove) {
            return false;
        }

        int stepX = (newX > oldX) ? 1 : (newX < oldX) ? -1 : 0;
        int stepY = (newY > oldY) ? 1 : (newY < oldY) ? -1 : 0;

        for (int x = oldX + stepX, y = oldY + stepY; x != newX || y != newY; x += stepX, y += stepY) {
            if (!isSpotEmpty(x, y, piecesOnBoard) || isSameColor(oldX, oldY, x, y, piecesOnBoard)) {
                return false;
            }
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
        //return !isSameColor(oldX, oldY, newX, newY, piecesOnBoard);
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
    
    public PieceType getType()
    {
        return pieceType;
    }

    public PieceFile getFile()
    {
        return pieceFile;
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
        return super.toString() + ":Queen";
    }
}

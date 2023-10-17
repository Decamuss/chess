package chess;

import java.util.ArrayList;
import java.util.List;

public class King extends ReturnPiece implements Piece {

    private ChessBoard board;

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank, ChessBoard board) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.board = board;
    }

    public boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        oldY -=1;
        newY -=1;
        return (Math.abs(oldX - newX) <= 1 && Math.abs(oldY - newY) <= 1);
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard ,boolean simulation) {
        // First, let's check if the move is legal without considering the check
        if (!isLegalMoveWithoutCheck(oldX, oldY, newX, newY, piecesOnBoard)) {
            return false;  // Move is not legal
        }

        // Save the current state
        PieceFile originalFile = this.pieceFile;
        int originalRank = this.pieceRank;
    
        // Simulate the move
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;

        char kingColor = this.pieceType.toString().charAt(0);
        boolean kingInCheck = board.isKingInCheck(piecesOnBoard, kingColor);

        // Undo the simulated move
        this.pieceFile = originalFile;
        this.pieceRank = originalRank;

        // Now check if the king is in check
        if (kingInCheck) {
            return false;  // Move is illegal, it puts the king in check
        }

        oldY -=1;
        newY -=1;
        // Proceed with the capturing logic only if the king is not in check
        if (!isSpotEmpty(newX, newY, piecesOnBoard)) {
            if (!isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
                // Capture opponent's piece
                ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
                if (!simulation){
                    piecesOnBoard.remove(capturedPiece);
                }
                return true;
            }
            return false;
        }

        return true;  // Move is legal
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

    public int getRank()
    {
        return pieceRank;
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

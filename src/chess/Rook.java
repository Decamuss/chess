package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ReturnPiece implements Piece{
    public boolean hasMoved = false;
    private ChessBoard board;

    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank, ChessBoard board) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.board = board;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
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
                ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
                piecesOnBoard.remove(capturedPiece);
                return true;
            }
            return false;
        }
    
        hasMoved = true;
        return true;  // Move is legal
    }
    
    public boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        oldY -=1;
        newY -=1;
        if (oldX != newX && oldY != newY) {
            return false;
        }
    
        int xDirection = (newX > oldX) ? 1 : (newX < oldX) ? -1 : 0;
        int yDirection = (newY > oldY) ? 1 : (newY < oldY) ? -1 : 0;
    
        int x = oldX + xDirection;
        int y = oldY + yDirection;
        while (x != newX || y != newY) {
            if (!isSpotEmpty(x, y, piecesOnBoard) || isSameColor(oldX, oldY, x, y, piecesOnBoard)) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
    
        return true;  // Move is legal without considering the check
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

    public boolean getHasMoved()
    {
        return this.hasMoved;
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

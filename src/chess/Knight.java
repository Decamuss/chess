package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ReturnPiece implements Piece{

    private ChessBoard board;

    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank, ChessBoard board) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.board = board;
    }

    
    public boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        oldY -=1;
        newY -=1;
        return ((Math.abs(oldX - newX) == 2 && Math.abs(oldY - newY) == 1) ||
                (Math.abs(oldX - newX) == 1 && Math.abs(oldY - newY) == 2));
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard ,boolean simulation) {
        // First, let's check if the move is legal without considering the check
        if (!isLegalMoveWithoutCheck(oldX, oldY, newX, newY, piecesOnBoard)) {
            return false;  // Move is not legal
        }


        oldY -=1;
        newY -=1;
        // Save the current state
        PieceFile originalFile = this.pieceFile;
        int originalRank = this.pieceRank;
        
        char kingColor = this.pieceType.toString().charAt(0);
        boolean kingCurrCheck = board.isKingInCheck(piecesOnBoard, kingColor);

        ReturnPiece temp = new ReturnPiece();
        if(getPieceAt(newX, newY, piecesOnBoard) != null)
        {
            temp = getPieceAt(newX, newY, piecesOnBoard);
        }

        if(kingCurrCheck && temp.pieceType != null)
        {
            piecesOnBoard.remove(temp);  
        }

        // Simulate the move
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;

        
        boolean kingInCheck = board.isKingInCheck(piecesOnBoard, kingColor);

        // Undo the simulated move
        this.pieceFile = originalFile;
        this.pieceRank = originalRank;
        if(temp.pieceType != null && kingCurrCheck)
        {
           piecesOnBoard.add(temp);  
        }

        // Now check if the king is in check
        if (kingCurrCheck && kingInCheck) {
            return false;  // Move is illegal, it puts the king in check
        }
        else if(!kingCurrCheck &&kingInCheck)
        {
            return false;
        }


        // Proceed with the capturing logic only if the king is not in check
        if (!isSpotEmpty(newX, newY, piecesOnBoard)) {
            if (!isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
                // Capture opponent's piece
                ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
                if (!simulation){
                    piecesOnBoard.remove(capturedPiece);
                    kingCurrCheck =false;
                }
                kingCurrCheck =false;
                return true;
            }
           // kingCurrCheck =false;
            return false;
        }
        kingCurrCheck =false;
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

    public boolean getHasMoved()
    {
        return true;
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
        return super.toString() + ":Knight";
    }
}

package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ReturnPiece implements Piece{

    private ChessBoard board;
    public boolean hasMoved =false;
    public boolean enPassantPossible = false;

    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank, ChessBoard board) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.board = board;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard, boolean simulation) {
        // First, check basic move legality
        if (!isLegalMoveWithoutCheck(oldX, oldY, newX, newY, piecesOnBoard)) {
            return false;  // Move is not legal
        }
    
        oldY -= 1;
        newY -= 1;
        int direction = (this.pieceType.toString().charAt(0) == 'W') ? 1 : -1;  // White pawns move up, black pawns move down
    
        // Save the current state
        PieceFile originalFile = this.pieceFile;
        int originalRank = this.pieceRank;
        boolean originalHasMoved = this.hasMoved;
        
        char kingColor = this.pieceType.toString().charAt(0);
        boolean kingCurrCheck = board.isKingInCheck(piecesOnBoard, kingColor);
        
      
         // Simulate the move
        ReturnPiece temp = new ReturnPiece();
        if(getPieceAt(newX, newY, piecesOnBoard) != null)
        {
            temp = getPieceAt(newX, newY, piecesOnBoard);
        }

        if(kingCurrCheck && temp.pieceType != null)
        {
            piecesOnBoard.remove(temp);  
        }
 
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;
        this.hasMoved = true;
        
        
        boolean kingInCheck = board.isKingInCheck(piecesOnBoard, kingColor);
    
        // Undo the simulated move
        this.pieceFile = originalFile;
        this.pieceRank = originalRank;
        this.hasMoved = originalHasMoved;
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
    
        // Check for initial two-step forward move
        if (hasMoved == false && oldX == newX && oldY + (2 * direction) == newY && isSpotEmpty(newX, newY, piecesOnBoard) && isSpotEmpty(newX, oldY + direction, piecesOnBoard)) {
            if(!simulation)
            {
                this.hasMoved = true;
            }
            
            kingCurrCheck = false;
        } else if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard) && !isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            // Capture opponent's piece
            if (!simulation){
            ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
            piecesOnBoard.remove(capturedPiece);
            this.hasMoved = true;
            kingCurrCheck = false;
            }
            return true;

        } else {
            // If none of the above, then it's just a standard move.
            //this.hasMoved = true;
            if(!simulation)
            {
                this.hasMoved = true;
            }
            kingCurrCheck = false;
        }
    
        return true;  // Move is legal
    }
    

    public boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard) {
        oldY -=1;
        newY -=1;
        int direction = (this.pieceType.toString().charAt(0) == 'W') ? 1 : -1;  // White pawns move up, black pawns move down
        
        // Check for standard one-step forward move
        if (oldX == newX && oldY + direction == newY && isSpotEmpty(newX, newY, piecesOnBoard)) {
            
            return true;
        }
        
        // Check for initial two-step forward move
        if (!hasMoved && oldX == newX && oldY + (2 * direction) == newY && isSpotEmpty(newX, newY, piecesOnBoard) && isSpotEmpty(newX, oldY + direction, piecesOnBoard)) {
            return true;
        }
        
        // Check for diagonal capture
        if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard) && !isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            return true;
        }

        if (isSpotEmpty(newX, newY, piecesOnBoard) && (oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY) {
            ReturnPiece adjacentPiece = getPieceAt(newX, oldY, piecesOnBoard);
            if (adjacentPiece instanceof Pawn && adjacentPiece.pieceType.toString().charAt(0) != this.pieceType.toString().charAt(0) && ((Pawn) adjacentPiece).enPassantPossible) {
                return true;
            }
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

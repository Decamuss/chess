package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ReturnPiece implements Piece{

    private ChessBoard board;
    public boolean hasMoved =false;
    public boolean isEnPassantAvailable = false; // New member

    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank, ChessBoard board) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.board = board;
    }

    public boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard ,boolean simulation) {
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
    
        // Simulate the move
        this.pieceFile = PieceFile.values()[newX];
        this.pieceRank = newY + 1;
        this.hasMoved = true;
    
        char kingColor = this.pieceType.toString().charAt(0);
        boolean kingInCheck = board.isKingInCheck(piecesOnBoard, kingColor);
    
        // Undo the simulated move
        this.pieceFile = originalFile;
        this.pieceRank = originalRank;
        this.hasMoved = originalHasMoved;
    
        // Now check if the king is in check
        if (kingInCheck) {
            return false;  // Move is illegal, it puts the king in check
        }
        if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard) && !isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            // Capture opponent's piece
            ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
            if (!simulation){
                piecesOnBoard.remove(capturedPiece);
            }
            return true;
        } else if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && isSpotEmpty(newX, newY, piecesOnBoard)) {
            // Possible en passant capture
            ReturnPiece targetPawn = getPieceAt(newX, newY - direction, piecesOnBoard);
            if (targetPawn != null && targetPawn instanceof Pawn && ((Pawn) targetPawn).isEnPassantAvailable && !isSameColor(oldX, oldY, newX, newY - direction, piecesOnBoard)) {
                if (!simulation){
                    piecesOnBoard.remove(targetPawn);
                }
                return true;
            } else {
                return false; // The move is invalid because there's no piece to capture with en passant
            }
        } else {
            // If none of the above, then it's just a standard move.
            this.hasMoved = true;
            this.isEnPassantAvailable = false; // Clear the flag if it's a standard move
        }
    
        // Check for initial two-step forward move
        if (hasMoved == false && oldX == newX && oldY + (2 * direction) == newY && isSpotEmpty(newX, newY, piecesOnBoard) && isSpotEmpty(newX, oldY + direction, piecesOnBoard)) {
            this.hasMoved = true;
            this.isEnPassantAvailable = true; // Set the flag
        } else if ((oldX + 1 == newX || oldX - 1 == newX) && oldY + direction == newY && !isSpotEmpty(newX, newY, piecesOnBoard) && !isSameColor(oldX, oldY, newX, newY, piecesOnBoard)) {
            // Capture opponent's piece
            ReturnPiece capturedPiece = getPieceAt(newX, newY, piecesOnBoard);
            piecesOnBoard.remove(capturedPiece);
        } else {
            // If none of the above, then it's just a standard move.
            this.hasMoved = true;
            this.isEnPassantAvailable = false; // Set the flag
        }
    
        return true;  // Move is legal
    }

    public void clearOtherPawnsEnPassantFlags(ArrayList<ReturnPiece> piecesOnBoard) {
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece instanceof Pawn && piece != this) {
                ((Pawn) piece).isEnPassantAvailable = false;
            }
        }
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

        if (!hasMoved && Math.abs(newY - (this.pieceRank - 1)) == 2) {
            this.isEnPassantAvailable = true;
        } else {
            this.isEnPassantAvailable = false;
        }
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

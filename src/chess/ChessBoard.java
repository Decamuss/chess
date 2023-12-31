package chess;

import java.util.ArrayList;
import java.util.List;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

public class ChessBoard {
    private ArrayList<ReturnPiece> piecesOnBoard;

    public ChessBoard() {
        this.piecesOnBoard = new ArrayList<>();
        // Initialize the board with pieces...
    }

    public boolean isKingInCheck(ArrayList<ReturnPiece> piecesOnBoard, char kingColor) {
        // Find the location of the king
        int kingX = -1;
        int kingY = -1;
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceType.toString().charAt(0) == kingColor &&
               (piece.pieceType == PieceType.WK || piece.pieceType == PieceType.BK)) {
                kingX = piece.pieceFile.ordinal();
                kingY = piece.pieceRank;
                break;  // Exit the loop once the king is found
            }
        }
    
        // If the king is not found, return false
        if (kingX == -1 || kingY == -1) {
            return false;
        }
    
        // Check if any opponent piece can capture the king
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceType.toString().charAt(0) != kingColor) {  // If the piece is not the same color as the king
                Piece movingPiece = mapToDerivedPiece(piece);
                if (movingPiece != null) {
                    if (movingPiece.isLegalMoveWithoutCheck(piece.pieceFile.ordinal(), piece.pieceRank, kingX, kingY, piecesOnBoard)) {
                        return true;  // The king is in check
                    }
                }
            }
        }


        
        return false;  // No piece can capture the king
    }
    
    public boolean isKingInCheckMate(ArrayList<ReturnPiece> piecesOnBoard, char kingColor) {
        // Iterate over all pieces of the player whose king is potentially in checkmate
        for (ReturnPiece returnPiece : piecesOnBoard) {
            Piece piece = mapToDerivedPiece(returnPiece);
            
            // If the piece isn't of the correct color, skip it
            if (piece.getType().toString().charAt(0) != kingColor) {
                continue;
            }
            
            // Get the current position of the piece
            int currentX = piece.getFile().ordinal();
            int currentY = piece.getRank(); // Assuming 1-8 indexing for rank
            
            

            // Try all possible moves for the piece
            for (int x = 0; x < 8; x++) {
                for (int y = 1; y <= 8; y++) {  // Adjusted for 1-8 indexing for y-values
                    // If the move is legal without considering check

                    if (piece.isLegalMove(currentX, currentY, x, y, piecesOnBoard, true)) {
                        // Save the current state of the piece
                        ReturnPiece pieceAtXY = getPieceAt(x, y, piecesOnBoard);

                        PieceFile originalFile = piece.getFile();
                        int originalRank = piece.getRank();
                        ReturnPiece possibleCapture = getPieceAt(x, y, piecesOnBoard);  // Get the piece that might be captured
    
                        // Virtually move the piece
                        piecesOnBoard.remove(pieceAtXY);
                        piece.move(x, y);
    
                        // Check if the king would still be in check after this move
                        boolean kingStillInCheck = isKingInCheck(piecesOnBoard, kingColor);

                        // If the move captures a piece and the king is no longer in check, then it's not checkmate
                        if (possibleCapture != null && !kingStillInCheck) {
                            if (doesMoveResolveCheck(currentX, currentY, x, y, piecesOnBoard, kingColor)) {
                                return false;  // The move captures the threatening piece and resolves the check
                            }
                        }
    
                        // Restore the piece's original position
                        piece.move(originalFile.ordinal(), originalRank); // No conversion needed since rank is 1-8
                        if(pieceAtXY != null)
                        {
                            piecesOnBoard.add(pieceAtXY);
                        }
                        // If we find any move that gets the king out of check, it's not a checkmate
                        if (!kingStillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }
    
        // If we've tried every possible move and the king is still in check, it's checkmate
        return true;
    }
    private boolean doesMoveResolveCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard, char kingColor) {
        // Get the moving piece and the potentially captured piece
        Piece movingPiece = mapToDerivedPiece(getPieceAt(oldX, oldY, piecesOnBoard));
        ReturnPiece potentiallyCaptured = getPieceAt(newX, newY, piecesOnBoard);
        
        if (movingPiece == null) {
            return false;  // Can't make a move if there's no piece to move
        }
    
        // Save the state before simulating the move
        PieceFile originalFile = movingPiece.getFile();
        int originalRank = movingPiece.getRank();
    
        // Virtually perform the move
        ArrayList<ReturnPiece> virtualBoard = new ArrayList<>(piecesOnBoard); // Create a copy of the board to simulate the move
        if (potentiallyCaptured != null) {
            virtualBoard.remove(potentiallyCaptured);
        }
        movingPiece.move(newX, newY);
    
        // Check if this move resolves the check
        boolean kingStillInCheck = isKingInCheck(virtualBoard, kingColor);
    
        // Restore the moving piece's state
        movingPiece.move(originalFile.ordinal(), originalRank);
    
        // If the king is not in check after the move, then this move does resolve the check
        return !kingStillInCheck;
    }
    
    private static Piece mapToDerivedPiece(ReturnPiece returnPiece) {
        if(returnPiece == null) {
            return null;  // or throw a custom exception if you prefer
        }
        
        switch (returnPiece.pieceType) {
            case WB: case BB:
                return (Bishop) returnPiece;
            case WN: case BN:
                return (Knight) returnPiece;
            case WR: case BR:
                return (Rook) returnPiece;
            case WQ: case BQ:
                return (Queen) returnPiece;
            case WK: case BK:
                return (King) returnPiece;
            case WP: case BP:
                return (Pawn) returnPiece;
            default:
                return null;
        }
    }
private ReturnPiece getPieceAt(int x, int y, ArrayList<ReturnPiece> piecesOnBoard) {
    for (ReturnPiece piece : piecesOnBoard) {
        if (piece.pieceFile.ordinal() == x && piece.pieceRank == y) {
            return piece;
        }
    }
    return null;
}

}

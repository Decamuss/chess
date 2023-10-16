package chess;

import java.util.ArrayList;
import java.util.List;

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
                        System.out.println(movingPiece);
                        System.out.println("Piece File: " + piece.pieceFile.ordinal());
                        System.out.println("Piece Rank: " + (piece.pieceRank));
                        System.out.println("King X: " + (kingX));
                        System.out.println("King Y: " + (kingY));

                        System.out.println("testsett");
                        return true;  // The king is in check
                    }
                }
            }
        }
        return false;  // No piece can capture the king
    }
    


    
private static Piece mapToDerivedPiece(ReturnPiece returnPiece) {
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
        if (piece.pieceFile.ordinal() == x && piece.pieceRank == y + 1) {
            return piece;
        }
    }
    return null;
}

}

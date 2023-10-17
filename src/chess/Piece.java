package chess;
import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
public interface Piece {
    boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard ,boolean simulation);
    boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard);
    void move(int x, int y);
    PieceType getType();
    PieceFile getFile();
    int getRank();
    boolean getHasMoved();
}

package chess;
import java.util.ArrayList;
import java.util.List;
public interface Piece {
    boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard);
    boolean isLegalMoveWithoutCheck(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard);
    void move(int x, int y);

}

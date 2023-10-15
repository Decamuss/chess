package chess;
import java.util.ArrayList;
public interface Piece {
    boolean isLegalMove(int oldX, int oldY, int newX, int newY, ArrayList<ReturnPiece> piecesOnBoard);
    void move(int x, int y);
}

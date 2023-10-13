package chess;

public class Queen extends ReturnPiece {
    public Queen(PieceFile pieceFile, int pieceRank)
    {
       if((pieceFile == PieceFile.d) && (pieceRank == 1))
       {
        this.pieceType = PieceType.WQ;
       }
       if((pieceFile == PieceFile.d) && (pieceRank == 8))
       {
        this.pieceType = PieceType.BQ;
       }
    }
}

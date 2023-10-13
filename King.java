package chess;

public class King extends ReturnPiece {
    public King(PieceFile pieceFile, int pieceRank)
    {
       if((pieceFile == PieceFile.e) && (pieceRank == 1))
       {
        this.pieceType = PieceType.WK;
       }
       if((pieceFile == PieceFile.e) && (pieceRank == 8))
       {
        this.pieceType = PieceType.BK;
       }
    }
}

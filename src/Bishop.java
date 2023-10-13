package chess;



public class Bishop extends ReturnPiece {
    public Bishop(PieceFile pieceFile, int pieceRank)
    {
       if((pieceFile == PieceFile.c|| pieceFile == PieceFile.f) && (pieceRank == 1))
       {
        this.pieceType = PieceType.WB;
       }
       if((pieceFile == PieceFile.c|| pieceFile == PieceFile.f) && (pieceRank == 8))
       {
        this.pieceType = PieceType.BB;
       }
    }
}

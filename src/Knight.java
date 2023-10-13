package chess;


public class Knight extends ReturnPiece {
    public Knight(PieceFile pieceFile, int pieceRank)
    {
       if((pieceFile == PieceFile.b|| pieceFile == PieceFile.g) && (pieceRank == 1))
       {
        this.pieceType = PieceType.WN;
       }
       if((pieceFile == PieceFile.b|| pieceFile == PieceFile.g) && (pieceRank == 8))
       {
        this.pieceType = PieceType.BN;
       }
    }
}

package chess;





public class Rook extends ReturnPiece {
    
    public Rook(PieceFile pieceFile, int pieceRank)
        {
           if((pieceFile == PieceFile.a|| pieceFile == PieceFile.h) && (pieceRank == 1))
           {
            this.pieceType = PieceType.WR;
           }
           if((pieceFile == PieceFile.a|| pieceFile == PieceFile.h) && (pieceRank == 1)){
            this.pieceType = PieceType.BR;
           }
        }        

}


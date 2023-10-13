package chess;
public class Pawn extends ReturnPiece {
    public Pawn(PieceFile pieceFile, int pieceRank) {
        if(pieceRank == 2)
        {
         this.pieceType = PieceType.WP;
        }
        if(pieceRank == 7){
         this.pieceType = PieceType.BP;
        }          
 }
}

package chess;


public class Knight extends ReturnPiece {
    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank)
    {
        this.pieceType = pieceType;  // Assume PieceType.BB for black bishop or PieceType.WB for white bishop
        this.pieceFile = pieceFile;  // Assume PieceFile.a through PieceFile.h
        this.pieceRank = pieceRank;  // Assume 1 through 8
    }
}

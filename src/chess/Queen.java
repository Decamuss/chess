package chess;

public class Queen extends ReturnPiece {
    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank)
    {
        this.pieceType = pieceType;  // Assume PieceType.BB for black bishop or PieceType.WB for white bishop
        this.pieceFile = pieceFile;  // Assume PieceFile.a through PieceFile.h
        this.pieceRank = pieceRank;  // Assume 1 through 8
    }
}

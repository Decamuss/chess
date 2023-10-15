package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//need to import for some reason to get the pieces to construct
import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8


	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}


class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};

	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;

}

public class Chess {
	static ReturnPlay game;
	static Player playerToMove;

	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return null;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		playerToMove = Player.white;
		game = new ReturnPlay();
	
		//make pieces
		Rook whiteRook1 = new Rook(PieceFile.a, 1);
		Rook whiteRook2 = new Rook(PieceFile.h, 1);
		Rook blackRook1 = new Rook(PieceFile.a, 8);
		Rook blackRook2 = new Rook(PieceFile.h, 8);

		Knight whiteKnight1 = new Knight(PieceFile.b, 1);
		Knight whiteKnight2 = new Knight(PieceFile.g, 1);
		Knight blackKnight1 = new Knight(PieceFile.b, 8);
		Knight blackKnight2 = new Knight(PieceFile.g, 8);

		Bishop whiteBishop1 = new Bishop(PieceType.WB, PieceFile.c, 1);
		Bishop whiteBishop2 = new Bishop(PieceType.WB, PieceFile.f, 1);
		Bishop blackBishop1 = new Bishop(PieceType.WB, PieceFile.c, 8);
		Bishop blackBishop2 = new Bishop(PieceType.WB, PieceFile.f, 8);

		Queen whiteQueen = new Queen(PieceFile.d, 1);
		Queen blackQueen = new Queen(PieceFile.d, 8);

		King whiteKing = new King(PieceFile.e, 1);
		King blackKing = new King(PieceFile.e, 8);


		//make list for pawn add loop
		List<PieceFile> pieceFiles = Arrays.asList(PieceFile.values());
		
		
		
		for(PieceFile file: pieceFiles)
		{
			Pawn whitePawn = new Pawn(file, 2);
			Pawn blackPawn = new Pawn(file, 7);
			game.piecesOnBoard.add(whitePawn);
			game.piecesOnBoard.add(blackPawn);
		}
	//add pieces to board
		game.piecesOnBoard.add(whiteRook1);
		game.piecesOnBoard.add(whiteRook2);
		game.piecesOnBoard.add(blackRook1);
		game.piecesOnBoard.add(blackRook2);
		
		game.piecesOnBoard.add(whiteKnight1);
		game.piecesOnBoard.add(whiteKnight2);
		game.piecesOnBoard.add(blackKnight1);
		game.piecesOnBoard.add(blackKnight2);

		game.piecesOnBoard.add(whiteBishop1);
		game.piecesOnBoard.add(whiteBishop2);
		game.piecesOnBoard.add(blackBishop1);
		game.piecesOnBoard.add(blackBishop2);

		game.piecesOnBoard.add(whiteQueen);
		game.piecesOnBoard.add(blackQueen);

		game.piecesOnBoard.add(whiteKing);
		game.piecesOnBoard.add(blackKing);
		

	}
}

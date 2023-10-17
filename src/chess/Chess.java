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

	private static ChessBoard chessBoard;

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
		game.message = null;
		List<String> listType = Arrays.asList("n", "r", "b");
        String[] moveParts = move.split(" ", 3);
		if (moveParts.length == 0)
		{
	        game.message = ReturnPlay.Message.ILLEGAL_MOVE;
         	
		}
		else if(moveParts.length ==1 && moveParts[0].equalsIgnoreCase("resign"))
		{
			resign();
		}
		else if (moveParts.length ==2)
		{
			 move(moveParts);
			
		}
		else if (moveParts.length ==3 && moveParts[2].equalsIgnoreCase("draw?"))
		{
			boolean valid = move(moveParts);
			if(valid)
			{
				game.message = ReturnPlay.Message.DRAW;
			}
			
		}
		else if(moveParts.length ==3 && listType.contains(moveParts[2]))
		{
			move(moveParts);
		}

		else
		{
			 game.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}
		return game;

       
	}


	private static void resign()
	{
		if(playerToMove == Player.white)
		{
			game.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
		}
		else if(playerToMove == Player.black)
		{
			game.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
		}
	}



	private static boolean move(String[] moveParts)
	{

        // Parse the source and destination squares
        String source = moveParts[0];
        String destination = moveParts[1];
		

        PieceFile oldFile = PieceFile.valueOf(source.substring(0, 1));
        int oldRank = Integer.parseInt(source.substring(1));
        PieceFile newFile = PieceFile.valueOf(destination.substring(0, 1));
        int newRank = Integer.parseInt(destination.substring(1));
		boolean simulation = false;

        ReturnPiece returnPiece = getPieceAt(oldFile, oldRank);
        if (returnPiece == null) {
            // No piece at the specified position
            game.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return false;
        }

        Piece piece = mapToDerivedPiece(returnPiece);
        if (piece == null) {
            // Unknown piece type
            game.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return false;
        }

		if (!isCorrectPlayerTurn(oldFile, oldRank)) {
			game.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return false;
		}
		if (piece.isLegalMove(oldFile.ordinal(), oldRank, newFile.ordinal(), newRank, game.piecesOnBoard, simulation)) {
            piece.move(newFile.ordinal(), newRank);
            updateGameState();
			switchPlayerTurn();
        } else {
            game.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return false;
        }

		//Promotion Check
		if(piece.getType() == ReturnPiece.PieceType.WP && newRank == 8 )
		{
			String promotionPiece = moveParts.length<3 ? "q" : moveParts[2];
			game.piecesOnBoard.remove((ReturnPiece)piece);

			if(promotionPiece.equalsIgnoreCase("n")){
				game.piecesOnBoard.add(new Knight(PieceType.WN, piece.getFile(), 8, chessBoard));
			}

			else if(promotionPiece.equalsIgnoreCase("b")){
				game.piecesOnBoard.add(new Bishop(PieceType.WB, piece.getFile(), 8, chessBoard));
			}

			else if(promotionPiece.equalsIgnoreCase("r")){
				game.piecesOnBoard.add(new Rook(PieceType.WR, piece.getFile(), 8, chessBoard));
			}
			else{
				game.piecesOnBoard.add(new Queen(PieceType.WQ, piece.getFile(), 8, chessBoard));
			}
		}  

		else if (piece.getType() == ReturnPiece.PieceType.BP && newRank ==1)
		{
			String promotionPiece = moveParts.length<3 ? "q" : moveParts[2];
			game.piecesOnBoard.remove((ReturnPiece)piece);

			if(promotionPiece.equalsIgnoreCase("n")){
				game.piecesOnBoard.add(new Knight(PieceType.BN, piece.getFile(), 1, chessBoard));
			}

			else if(promotionPiece.equalsIgnoreCase("b")){
				game.piecesOnBoard.add(new Bishop(PieceType.BB, piece.getFile(), 1, chessBoard));
			}

			else if(promotionPiece.equalsIgnoreCase("r")){
				game.piecesOnBoard.add(new Rook(PieceType.BR, piece.getFile(), 1, chessBoard));
			}
			else{
				game.piecesOnBoard.add(new Queen(PieceType.BQ, piece.getFile(), 1, chessBoard));
			}
		}
		return true;
	}

	private static boolean isCorrectPlayerTurn(PieceFile file, int rank) {
		ReturnPiece sourcePiece = getPieceAt(file,rank);
		if (sourcePiece == null) {
			// No piece at source, can't be the correct player's turn
			return false;
		}
		
		char currentPlayerColor = (playerToMove == Player.white) ? 'W' : 'B';
		// Assuming the PieceType in ReturnPiece uses 'W' and 'B' prefixes to distinguish between white and black pieces
		return sourcePiece.pieceType.name().charAt(0) == currentPlayerColor;
	}
	
	
	private static void switchPlayerTurn() {
		playerToMove = (playerToMove == Player.white) ? Player.black : Player.white;
	}

	

	private static Piece mapToDerivedPiece(ReturnPiece returnPiece) {
		switch (returnPiece.pieceType) {
			case WB: case BB:
				return (Bishop) returnPiece;
			case WN: case BN:
				return (Knight) returnPiece;
			case WR: case BR:
				return (Rook) returnPiece;
			case WQ: case BQ:
				return (Queen) returnPiece;
			case WK: case BK:
				return (King) returnPiece;
			case WP: case BP:
				return (Pawn) returnPiece;
			default:
				return null;
		}
	}

	
	private static void updateGameState() {
		char opponentColor = (playerToMove == Player.white) ? 'B' : 'W';
		if (chessBoard.isKingInCheck(game.piecesOnBoard, opponentColor)) {
			if (chessBoard.isKingInCheckMate(game.piecesOnBoard, opponentColor)) {
				game.message = (opponentColor == 'W') ? ReturnPlay.Message.CHECKMATE_WHITE_WINS : ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			} else {
				game.message = ReturnPlay.Message.CHECK;
			}
		}
	}

	private static ReturnPiece getPieceAt(PieceFile file, int rank) {
        for (ReturnPiece piece : game.piecesOnBoard) {
            if (piece.pieceFile == file && piece.pieceRank == rank) {
                return piece;
            }
        }
        return null;
    }
	
	public static void start() {
		/* FILL IN THIS METHOD */
		playerToMove = Player.white;
		game = new ReturnPlay();
		game.piecesOnBoard = new ArrayList<ReturnPiece>();

		chessBoard = new ChessBoard();  // assuming ChessBoard has a no-argument constructor

	
		//make pieces
		Rook whiteRook1 = new Rook(PieceType.WR, PieceFile.a, 1, chessBoard);
		Rook whiteRook2 = new Rook(PieceType.WR, PieceFile.h, 1, chessBoard);
		Rook blackRook1 = new Rook(PieceType.BR, PieceFile.a, 8, chessBoard);
		Rook blackRook2 = new Rook(PieceType.BR, PieceFile.h, 8, chessBoard);

		Knight whiteKnight1 = new Knight(PieceType.WN,PieceFile.b, 1, chessBoard);
		Knight whiteKnight2 = new Knight(PieceType.WN, PieceFile.g, 1, chessBoard);
		Knight blackKnight1 = new Knight(PieceType.BN, PieceFile.b, 8, chessBoard);
		Knight blackKnight2 = new Knight(PieceType.BN, PieceFile.g, 8, chessBoard);

		Bishop whiteBishop1 = new Bishop(PieceType.WB, PieceFile.c, 1, chessBoard);
		Bishop whiteBishop2 = new Bishop(PieceType.WB, PieceFile.f, 1, chessBoard);
		Bishop blackBishop1 = new Bishop(PieceType.BB, PieceFile.c, 8, chessBoard);
		Bishop blackBishop2 = new Bishop(PieceType.BB, PieceFile.f, 8, chessBoard);

		Queen whiteQueen = new Queen(PieceType.WQ, PieceFile.d, 1, chessBoard);
		Queen blackQueen = new Queen(PieceType.BQ, PieceFile.d, 8, chessBoard);

		King whiteKing = new King(PieceType.WK, PieceFile.e, 1, chessBoard);
		King blackKing = new King(PieceType.BK, PieceFile.e, 8, chessBoard);


		//make list for pawn add loop
		List<PieceFile> pieceFiles = Arrays.asList(PieceFile.values());
		
		
		
		for(PieceFile file: pieceFiles)
		{
			Pawn whitePawn = new Pawn(PieceType.WP, file, 2, chessBoard);
			Pawn blackPawn = new Pawn(PieceType.BP, file, 7, chessBoard);
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

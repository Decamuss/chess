// // File: Move.java
// package chess;


// public class Move {
//     private int startRow;
//     private int startCol;
//     private int endRow;
//     private int endCol;
//     private String promotionPiece;  // Holds the piece type for pawn promotion (e.g., "Q" for Queen)

//     // Constructor for normal moves
//     public Move(int startRow, int startCol, int endRow, int endCol) {
//         this.startRow = startRow;
//         this.startCol = startCol;
//         this.endRow = endRow;
//         this.endCol = endCol;
//         this.promotionPiece = null;  // No promotion piece for normal moves
//     }

//     // Constructor for pawn promotion moves
//     public Move(int startRow, int startCol, int endRow, int endCol, String promotionPiece) {
//         this(startRow, startCol, endRow, endCol);  // Call the other constructor
//         this.promotionPiece = promotionPiece;
//     }

//     // Getter methods
//     public int getStartRow() {
//         return startRow;
//     }

//     public int getStartCol() {
//         return startCol;
//     }

//     public int getEndRow() {
//         return endRow;
//     }

//     public int getEndCol() {
//         return endCol;
//     }

//     public String getPromotionPiece() {
//         return promotionPiece;
//     }
// }

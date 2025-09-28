package com.chessgame.board;

import com.chessgame.pieces.*;

/**
 * Represents the chess board and manages piece positions
 */
public class ChessBoard {
    private Piece[][] board;
    private static final int BOARD_SIZE = 8;
    
    public ChessBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }
    
    /**
     * Initialize the board with pieces in starting positions
     */
    private void initializeBoard() {
        // Clear the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = null;
            }
        }
        
        // Place white pieces (bottom of board)
        setupPiecesForColor(Piece.Color.WHITE, 7, 6);
        
        // Place black pieces (top of board)
        setupPiecesForColor(Piece.Color.BLACK, 0, 1);
    }
    
    /**
     * Setup pieces for a specific color
     * @param color The color of pieces to setup
     * @param backRow The row for major pieces (rook, knight, bishop, queen, king)
     * @param pawnRow The row for pawns
     */
    private void setupPiecesForColor(Piece.Color color, int backRow, int pawnRow) {
        // Place pawns
        for (int col = 0; col < BOARD_SIZE; col++) {
            Position pawnPos = new Position(pawnRow, col);
            board[pawnRow][col] = new Pawn(color, pawnPos);
        }
        
        // Place major pieces
        Position rookPos1 = new Position(backRow, 0);
        Position rookPos2 = new Position(backRow, 7);
        board[backRow][0] = new Rook(color, rookPos1);
        board[backRow][7] = new Rook(color, rookPos2);
        
        Position knightPos1 = new Position(backRow, 1);
        Position knightPos2 = new Position(backRow, 6);
        board[backRow][1] = new Knight(color, knightPos1);
        board[backRow][6] = new Knight(color, knightPos2);
        
        Position bishopPos1 = new Position(backRow, 2);
        Position bishopPos2 = new Position(backRow, 5);
        board[backRow][2] = new Bishop(color, bishopPos1);
        board[backRow][5] = new Bishop(color, bishopPos2);
        
        Position queenPos = new Position(backRow, 3);
        Position kingPos = new Position(backRow, 4);
        board[backRow][3] = new Queen(color, queenPos);
        board[backRow][4] = new King(color, kingPos);
    }
    
    /**
     * Get the piece at a specific position
     * @param position The position to check
     * @return The piece at that position, or null if empty
     */
    public Piece getPiece(Position position) {
        if (!position.isValid()) {
            return null;
        }
        return board[position.getRow()][position.getCol()];
    }
    
    /**
     * Get the piece at specific coordinates
     * @param row The row coordinate
     * @param col The column coordinate
     * @return The piece at that position, or null if empty
     */
    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return null;
        }
        return board[row][col];
    }
    
    /**
     * Move a piece from one position to another
     * @param from The starting position
     * @param to The destination position
     * @return true if the move was successful, false otherwise
     */
    public boolean movePiece(Position from, Position to) {
        if (!from.isValid() || !to.isValid()) {
            return false;
        }
        
        Piece piece = getPiece(from);
        if (piece == null) {
            return false;
        }
        
        // Update piece position
        piece.setPosition(to);
        
        // Move piece on board
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;
        
        return true;
    }
    
    /**
     * Get a copy of the board array
     * @return 2D array representing the current board state
     */
    public Piece[][] getBoardArray() {
        Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.arraycopy(board[row], 0, copy[row], 0, BOARD_SIZE);
        }
        return copy;
    }
    
    /**
     * Find the king of a specific color
     * @param color The color of the king to find
     * @return The position of the king, or null if not found
     */
    public Position findKing(Piece.Color color) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    return piece.getPosition();
                }
            }
        }
        return null;
    }
    
    /**
     * Get a string representation of the board
     * @return String representation of the current board state
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  a b c d e f g h\n");
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            sb.append(8 - row).append(" ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    sb.append(". ");
                } else {
                    sb.append(piece.getSymbol()).append(" ");
                }
            }
            sb.append(8 - row).append("\n");
        }
        
        sb.append("  a b c d e f g h\n");
        return sb.toString();
    }
}
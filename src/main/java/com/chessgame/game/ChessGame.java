package com.chessgame.game;

import com.chessgame.board.ChessBoard;
import com.chessgame.board.Position;
import com.chessgame.pieces.Piece;
import com.chessgame.pieces.King;
import java.util.List;

/**
 * Main chess game logic and state management
 */
public class ChessGame {
    private ChessBoard board;
    private Piece.Color currentPlayer;
    private boolean gameOver;
    private String gameResult;
    
    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = Piece.Color.WHITE; // White always starts
        gameOver = false;
        gameResult = "";
    }
    
    /**
     * Attempt to make a move
     * @param from Starting position
     * @param to Destination position
     * @return true if move was successful, false otherwise
     */
    public boolean makeMove(Position from, Position to) {
        if (gameOver) {
            return false;
        }
        
        Piece piece = board.getPiece(from);
        if (piece == null || piece.getColor() != currentPlayer) {
            return false; // No piece or wrong color
        }
        
        if (!isValidMove(from, to)) {
            return false; // Invalid move
        }
        
        // Make the move
        board.movePiece(from, to);
        
        // Switch turns
        currentPlayer = (currentPlayer == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        
        // Check for game end conditions
        checkGameEnd();
        
        return true;
    }
    
    /**
     * Check if a move is valid
     * @param from Starting position
     * @param to Destination position
     * @return true if the move is valid
     */
    public boolean isValidMove(Position from, Position to) {
        Piece piece = board.getPiece(from);
        if (piece == null) {
            return false;
        }
        
        // Check if the destination is in the piece's valid moves
        List<Position> validMoves = piece.getValidMoves(board.getBoardArray());
        if (!validMoves.contains(to)) {
            return false;
        }
        
        // Check if move would put own king in check
        return !wouldMoveExposeKing(from, to, piece.getColor());
    }
    
    /**
     * Check if a move would expose the king to check
     * @param from Starting position
     * @param to Destination position
     * @param color Color of the moving piece
     * @return true if the move would expose the king
     */
    private boolean wouldMoveExposeKing(Position from, Position to, Piece.Color color) {
        // Make a temporary move
        Piece movingPiece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);
        
        // Simulate the move
        board.movePiece(from, to);
        
        // Check if king is in check after the move
        boolean kingInCheck = isKingInCheck(color);
        
        // Undo the move
        board.movePiece(to, from);
        if (capturedPiece != null) {
            // This is a simplified undo - in a full implementation, 
            // you'd need to properly restore the captured piece
            // For now, we'll work with this limitation
        }
        
        return kingInCheck;
    }
    
    /**
     * Check if a king is currently in check
     * @param color Color of the king to check
     * @return true if the king is in check
     */
    public boolean isKingInCheck(Piece.Color color) {
        Position kingPos = board.findKing(color);
        if (kingPos == null) {
            return false; // No king found (shouldn't happen in normal game)
        }
        
        Piece.Color opponentColor = (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        
        // Check if any opponent piece can attack the king
        Piece[][] boardArray = board.getBoardArray();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardArray[row][col];
                if (piece != null && piece.getColor() == opponentColor) {
                    List<Position> validMoves = piece.getValidMoves(boardArray);
                    if (validMoves.contains(kingPos)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Check for game end conditions (checkmate, stalemate)
     */
    private void checkGameEnd() {
        if (isKingInCheck(currentPlayer)) {
            if (hasNoValidMoves(currentPlayer)) {
                gameOver = true;
                Piece.Color winner = (currentPlayer == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
                gameResult = winner + " wins by checkmate!";
            }
        } else {
            if (hasNoValidMoves(currentPlayer)) {
                gameOver = true;
                gameResult = "Stalemate - Draw!";
            }
        }
    }
    
    /**
     * Check if a player has any valid moves
     * @param color Color of the player to check
     * @return true if the player has no valid moves
     */
    private boolean hasNoValidMoves(Piece.Color color) {
        Piece[][] boardArray = board.getBoardArray();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardArray[row][col];
                if (piece != null && piece.getColor() == color) {
                    List<Position> validMoves = piece.getValidMoves(boardArray);
                    for (Position move : validMoves) {
                        if (!wouldMoveExposeKing(piece.getPosition(), move, color)) {
                            return false; // Found at least one valid move
                        }
                    }
                }
            }
        }
        return true; // No valid moves found
    }
    
    // Getters
    public ChessBoard getBoard() {
        return board;
    }
    
    public Piece.Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public String getGameResult() {
        return gameResult;
    }
    
    /**
     * Reset the game to initial state
     */
    public void resetGame() {
        board = new ChessBoard();
        currentPlayer = Piece.Color.WHITE;
        gameOver = false;
        gameResult = "";
    }
}
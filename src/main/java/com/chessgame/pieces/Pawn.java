package com.chessgame.pieces;

import com.chessgame.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pawn piece in chess
 */
public class Pawn extends Piece {
    
    public Pawn(Color color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getValidMoves(Piece[][] board) {
        List<Position> validMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();
        
        // Pawn direction depends on color
        int direction = (color == Color.WHITE) ? -1 : 1; // White moves up (decreasing row), Black moves down
        
        // Forward move (one square)
        int newRow = currentRow + direction;
        if (newRow >= 0 && newRow < 8) {
            if (board[newRow][currentCol] == null) {
                validMoves.add(new Position(newRow, currentCol));
                
                // Initial two-square move
                if (!hasMoved) {
                    int twoSquareRow = currentRow + (2 * direction);
                    if (twoSquareRow >= 0 && twoSquareRow < 8 && board[twoSquareRow][currentCol] == null) {
                        validMoves.add(new Position(twoSquareRow, currentCol));
                    }
                }
            }
        }
        
        // Diagonal captures
        for (int colOffset = -1; colOffset <= 1; colOffset += 2) {
            int captureCol = currentCol + colOffset;
            int captureRow = currentRow + direction;
            
            if (captureRow >= 0 && captureRow < 8 && captureCol >= 0 && captureCol < 8) {
                Piece targetPiece = board[captureRow][captureCol];
                if (targetPiece != null && targetPiece.getColor() != this.color) {
                    validMoves.add(new Position(captureRow, captureCol));
                }
            }
        }
        
        return validMoves;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "♙" : "♟";
    }
    
    @Override
    public String getPieceType() {
        return "Pawn";
    }
}
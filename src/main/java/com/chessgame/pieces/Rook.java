package com.chessgame.pieces;

import com.chessgame.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Rook piece in chess
 */
public class Rook extends Piece {
    
    public Rook(Color color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getValidMoves(Piece[][] board) {
        List<Position> validMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();
        
        // Rook moves horizontally and vertically
        int[] rowDirections = {-1, 1, 0, 0}; // up, down, left, right
        int[] colDirections = {0, 0, -1, 1};
        
        for (int dir = 0; dir < 4; dir++) {
            int rowStep = rowDirections[dir];
            int colStep = colDirections[dir];
            
            for (int step = 1; step < 8; step++) {
                int newRow = currentRow + (step * rowStep);
                int newCol = currentCol + (step * colStep);
                Position newPos = new Position(newRow, newCol);
                
                if (!newPos.isValid()) {
                    break; // Out of bounds
                }
                
                Piece targetPiece = board[newRow][newCol];
                
                if (targetPiece == null) {
                    // Empty square - can move here
                    validMoves.add(newPos);
                } else if (targetPiece.getColor() != this.color) {
                    // Opponent's piece - can capture
                    validMoves.add(newPos);
                    break; // Can't move further in this direction
                } else {
                    // Own piece - can't move here or further
                    break;
                }
            }
        }
        
        return validMoves;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "♖" : "♜";
    }
    
    @Override
    public String getPieceType() {
        return "Rook";
    }
}
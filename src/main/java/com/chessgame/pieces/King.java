package com.chessgame.pieces;

import com.chessgame.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King piece in chess
 */
public class King extends Piece {
    
    public King(Color color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getValidMoves(Piece[][] board) {
        List<Position> validMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();
        
        // King can move one square in any direction
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            int newRow = currentRow + rowOffsets[i];
            int newCol = currentCol + colOffsets[i];
            Position newPos = new Position(newRow, newCol);
            
            if (newPos.isValid()) {
                Piece targetPiece = board[newRow][newCol];
                // Can move to empty square or capture opponent's piece
                if (targetPiece == null || targetPiece.getColor() != this.color) {
                    validMoves.add(newPos);
                }
            }
        }
        
        return validMoves;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "♔" : "♚";
    }
    
    @Override
    public String getPieceType() {
        return "King";
    }
}
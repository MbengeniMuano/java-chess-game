package com.chessgame.pieces;

import com.chessgame.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Knight piece in chess
 */
public class Knight extends Piece {
    
    public Knight(Color color, Position position) {
        super(color, position);
    }
    
    @Override
    public List<Position> getValidMoves(Piece[][] board) {
        List<Position> validMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getCol();
        
        // Knight moves in L-shape: 2 squares in one direction, 1 square perpendicular
        int[] rowOffsets = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] colOffsets = {-1, 1, -2, 2, -2, 2, -1, 1};
        
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
        return color == Color.WHITE ? "♘" : "♞";
    }
    
    @Override
    public String getPieceType() {
        return "Knight";
    }
}
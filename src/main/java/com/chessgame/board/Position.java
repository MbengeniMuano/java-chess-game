package com.chessgame.board;

/**
 * Represents a position on the chess board
 */
public class Position {
    private final int row;
    private final int col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    /**
     * Check if this position is within the board boundaries
     * @return true if position is valid (0-7 for both row and col)
     */
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    /**
     * Get chess notation for this position (e.g., "e4")
     * @return String representation in chess notation
     */
    public String toChessNotation() {
        if (!isValid()) {
            return "Invalid";
        }
        char file = (char) ('a' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }
    
    /**
     * Create position from chess notation (e.g., "e4")
     * @param notation Chess notation string
     * @return Position object
     */
    public static Position fromChessNotation(String notation) {
        if (notation.length() != 2) {
            throw new IllegalArgumentException("Invalid chess notation: " + notation);
        }
        
        char file = notation.charAt(0);
        char rank = notation.charAt(1);
        
        int col = file - 'a';
        int row = 8 - (rank - '0');
        
        return new Position(row, col);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }
    
    @Override
    public int hashCode() {
        return row * 8 + col;
    }
    
    @Override
    public String toString() {
        return toChessNotation();
    }
}
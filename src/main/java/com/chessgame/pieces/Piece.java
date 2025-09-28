package com.chessgame.pieces;

import com.chessgame.board.Position;
import java.util.List;

/**
 * Abstract base class for all chess pieces
 */
public abstract class Piece {
    protected Color color;
    protected Position position;
    protected boolean hasMoved;
    
    public enum Color {
        WHITE, BLACK
    }
    
    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
        this.hasMoved = false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        this.hasMoved = true;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    /**
     * Get all valid moves for this piece
     * @param board The current board state
     * @return List of valid positions this piece can move to
     */
    public abstract List<Position> getValidMoves(Piece[][] board);
    
    /**
     * Get the symbol representing this piece
     * @return Unicode symbol for the piece
     */
    public abstract String getSymbol();
    
    /**
     * Get a fallback text symbol for this piece when Unicode symbols don't work
     * @return Text-based symbol for the piece
     */
    public String getFallbackSymbol() {
        String pieceType = getPieceType();
        String colorPrefix = (color == Color.WHITE) ? "W" : "B";
        
        switch (pieceType) {
            case "King": return colorPrefix + "K";
            case "Queen": return colorPrefix + "Q";
            case "Rook": return colorPrefix + "R";
            case "Bishop": return colorPrefix + "B";
            case "Knight": return colorPrefix + "N";
            case "Pawn": return colorPrefix + "P";
            default: return colorPrefix + "?";
        }
    }
    
    /**
     * Get the type of this piece
     * @return String representation of piece type
     */
    public abstract String getPieceType();
    
    @Override
    public String toString() {
        return color + " " + getPieceType() + " at " + position;
    }
}
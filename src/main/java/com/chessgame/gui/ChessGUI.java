package com.chessgame.gui;

import com.chessgame.game.ChessGame;
import com.chessgame.board.Position;
import com.chessgame.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * GUI for the chess game using Java Swing
 */
public class ChessGUI extends JFrame {
    private ChessGame game;
    private JButton[][] boardButtons;
    private Position selectedPosition;
    private JLabel statusLabel;
    private JLabel currentPlayerLabel;
    private boolean useUnicodeSymbols;
    
    // Colors for the chess board
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE = new Color(255, 255, 0, 128);
    private static final Color VALID_MOVE = new Color(0, 255, 0, 128);
    
    public ChessGUI() {
        game = new ChessGame();
        selectedPosition = null;
        useUnicodeSymbols = testUnicodeSupport();
        initializeGUI();
        updateBoard();
    }
    
    private void initializeGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create the chess board panel
        JPanel boardPanel = createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        
        // Create the status panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
        
        // Create the menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        // Set window properties
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        boardButtons = new JButton[8][8];
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));
                // Try multiple fonts for better Unicode chess symbol support
                Font chessFont = getChessFont();
                button.setFont(chessFont);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                
                // Set square color
                if ((row + col) % 2 == 0) {
                    button.setBackground(LIGHT_SQUARE);
                } else {
                    button.setBackground(DARK_SQUARE);
                }
                
                // Add click listener
                final int finalRow = row;
                final int finalCol = col;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });
                
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        
        return boardPanel;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        currentPlayerLabel = new JLabel("Current Player: WHITE", JLabel.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        statusLabel = new JLabel("Game in progress", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        statusPanel.add(currentPlayerLabel, BorderLayout.NORTH);
        statusPanel.add(statusLabel, BorderLayout.SOUTH);
        
        return statusPanel;
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> {
            game.resetGame();
            selectedPosition = null;
            updateBoard();
            updateStatus();
        });
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);
        
        return menuBar;
    }
    
    private void handleSquareClick(int row, int col) {
        Position clickedPosition = new Position(row, col);
        
        if (selectedPosition == null) {
            // First click - select a piece
            Piece piece = game.getBoard().getPiece(clickedPosition);
            if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
                selectedPosition = clickedPosition;
                highlightValidMoves();
            }
        } else {
            // Second click - attempt to move
            if (clickedPosition.equals(selectedPosition)) {
                // Clicked same square - deselect
                selectedPosition = null;
                updateBoard();
            } else {
                // Attempt to move
                boolean moveSuccessful = game.makeMove(selectedPosition, clickedPosition);
                selectedPosition = null;
                updateBoard();
                updateStatus();
                
                if (!moveSuccessful) {
                    statusLabel.setText("Invalid move!");
                }
            }
        }
    }
    
    private void highlightValidMoves() {
        if (selectedPosition == null) return;
        
        Piece selectedPiece = game.getBoard().getPiece(selectedPosition);
        if (selectedPiece == null) return;
        
        // Reset board colors
        updateBoard();
        
        // Highlight selected square
        boardButtons[selectedPosition.getRow()][selectedPosition.getCol()].setBackground(SELECTED_SQUARE);
        
        // Highlight valid moves
        List<Position> validMoves = selectedPiece.getValidMoves(game.getBoard().getBoardArray());
        for (Position move : validMoves) {
            if (game.isValidMove(selectedPosition, move)) {
                boardButtons[move.getRow()][move.getCol()].setBackground(VALID_MOVE);
            }
        }
    }
    
    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = boardButtons[row][col];
                Piece piece = game.getBoard().getPiece(row, col);
                
                // Set piece symbol
                if (piece != null) {
                    String symbol = useUnicodeSymbols ? piece.getSymbol() : piece.getFallbackSymbol();
                    button.setText(symbol);
                } else {
                    button.setText("");
                }
                
                // Reset square color
                if ((row + col) % 2 == 0) {
                    button.setBackground(LIGHT_SQUARE);
                } else {
                    button.setBackground(DARK_SQUARE);
                }
            }
        }
    }
    
    private void updateStatus() {
        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer());
        
        if (game.isGameOver()) {
            statusLabel.setText(game.getGameResult());
            currentPlayerLabel.setText("Game Over");
        } else if (game.isKingInCheck(game.getCurrentPlayer())) {
            statusLabel.setText(game.getCurrentPlayer() + " is in check!");
        } else {
            statusLabel.setText("Game in progress");
        }
    }
    
    /**
     * Test if the system supports Unicode chess symbols
     */
    private boolean testUnicodeSupport() {
        try {
            // Create a test font to check Unicode support
            Font testFont = new Font("Segoe UI Symbol", Font.PLAIN, 36);
            String testSymbol = "♔";
            
            // Check if the font can display the chess symbol
            if (testFont.canDisplay(testSymbol.charAt(0))) {
                return true;
            }
            
            // Try alternative fonts
            String[] testFonts = {"Arial Unicode MS", "Lucida Sans Unicode", "DejaVu Sans"};
            for (String fontName : testFonts) {
                testFont = new Font(fontName, Font.PLAIN, 36);
                if (testFont.canDisplay(testSymbol.charAt(0))) {
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get the best available font for displaying chess symbols
     */
    private Font getChessFont() {
        // List of fonts that typically support Unicode chess symbols
        String[] fontNames = {
            "Segoe UI Symbol",    // Windows
            "Apple Symbols",      // macOS
            "Noto Color Emoji",   // Linux
            "DejaVu Sans",        // Cross-platform
            "Arial Unicode MS",   // Windows
            "Lucida Sans Unicode", // Windows
            "SansSerif"           // Java fallback
        };
        
        // Try each font and return the first available one
        for (String fontName : fontNames) {
            Font font = new Font(fontName, Font.PLAIN, useUnicodeSymbols ? 36 : 20);
            if (font.getFamily().equals(fontName)) {
                return font;
            }
        }
        
        // Ultimate fallback - use default font with appropriate size
        int fontSize = useUnicodeSymbols ? 36 : 20;
        return new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChessGUI().setVisible(true);
        });
    }
}
# Chess Game in Java

A fully functional chess game implemented in Java with a beautiful graphical user interface using Java Swing. This project demonstrates object-oriented programming principles, game logic implementation, and GUI development.

## 🎯 Features

- **Complete Chess Implementation**: All standard chess pieces with proper movement rules
- **Beautiful GUI**: Modern and intuitive interface with visual feedback
- **Move Validation**: Comprehensive move validation including check detection
- **Game State Management**: Turn-based gameplay with proper game flow
- **Visual Indicators**: 
  - Highlighted selected pieces
  - Valid move indicators
  - Check notifications
  - Game status updates
- **Interactive Gameplay**: Click-to-select and click-to-move interface
- **Game Controls**: New game option and proper game reset functionality

## 🏗️ Architecture

The project follows a clean, modular architecture with separation of concerns:

```
src/main/java/com/chessgame/
├── pieces/          # Chess piece implementations
│   ├── Piece.java   # Abstract base class
│   ├── King.java    # King piece logic
│   ├── Queen.java   # Queen piece logic
│   ├── Rook.java    # Rook piece logic
│   ├── Bishop.java  # Bishop piece logic
│   ├── Knight.java  # Knight piece logic
│   └── Pawn.java    # Pawn piece logic
├── board/           # Board representation
│   ├── ChessBoard.java  # Board state management
│   └── Position.java    # Position utilities
├── game/            # Game logic
│   └── ChessGame.java   # Main game controller
└── gui/             # User interface
    └── ChessGUI.java    # Swing-based GUI
```

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/chess-game-java.git
   cd chess-game-java
   ```

2. **Compile the project**
   ```bash
   # Navigate to the src directory
   cd src/main/java
   
   # Compile all Java files
   javac -d ../../../build com/chessgame/**/*.java
   ```

3. **Run the game**
   ```bash
   # Navigate to build directory
   cd ../../../build
   
   # Run the main class
   java com.chessgame.gui.ChessGUI
   ```

### Alternative: Using an IDE

1. Import the project into your preferred Java IDE
2. Set the source folder to `src/main/java`
3. Run the `ChessGUI.java` file

## 🎮 How to Play

1. **Starting the Game**: The game starts with White pieces at the bottom
2. **Making Moves**: 
   - Click on a piece to select it (valid pieces are highlighted)
   - Click on a destination square to move the piece
   - Valid moves are shown in green
3. **Game Rules**: Standard chess rules apply
   - Each piece moves according to chess rules
   - You cannot move into check
   - The game detects checkmate and stalemate
4. **New Game**: Use the "Game" menu to start a new game

## 🔧 Technical Implementation

### Key Design Patterns

- **Inheritance**: All pieces inherit from the abstract `Piece` class
- **Polymorphism**: Each piece implements its own movement logic
- **Encapsulation**: Game state is properly encapsulated within classes
- **Observer Pattern**: GUI updates based on game state changes

### Core Features

- **Move Validation**: Comprehensive validation including:
  - Piece-specific movement rules
  - Path obstruction checking
  - Check/checkmate detection
  - Turn validation

- **Game Logic**: 
  - Turn-based gameplay
  - Check detection
  - Checkmate and stalemate detection
  - Game state management

- **User Interface**:
  - Responsive grid-based board
  - Visual feedback for selections and valid moves
  - Status indicators for game state
  - Menu system for game controls

## 🎨 Screenshots

The game features a clean, modern interface with:
- Traditional chess board colors (light and dark squares)
- Unicode chess piece symbols for clear piece identification
- Visual highlighting for selected pieces and valid moves
- Status bar showing current player and game state

## 🔮 Future Enhancements

Potential improvements for the project:

- [ ] **Advanced Features**:
  - Castling implementation
  - En passant capture
  - Pawn promotion
  - Move history and undo functionality

- [ ] **AI Implementation**:
  - Computer opponent with different difficulty levels
  - Minimax algorithm with alpha-beta pruning

- [ ] **Enhanced UI**:
  - Piece animations
  - Sound effects
  - Themes and customizable board colors
  - Move notation display

- [ ] **Game Features**:
  - Save/load game functionality
  - PGN (Portable Game Notation) support
  - Timer functionality
  - Online multiplayer support



## 👨‍💻 Author

Created as a demonstration of Java programming skills, object-oriented design, and GUI development.

## 🙏 Acknowledgments

- Chess piece Unicode symbols for clean visual representation
- Java Swing framework for the graphical interface
- Standard chess rules and gameplay mechanics


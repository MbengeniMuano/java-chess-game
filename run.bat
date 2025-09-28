@echo off
echo Starting Chess Game...

:: Check if build directory exists
if not exist "build" (
    echo Build directory not found. Please run build.bat first.
    pause
    exit /b 1
)

:: Run the chess game
java -cp build com.chessgame.gui.ChessGUI

if %errorlevel% neq 0 (
    echo Failed to start the game. Please check if the game is compiled correctly.
    pause
)
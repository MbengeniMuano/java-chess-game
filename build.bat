@echo off
echo Building Chess Game...

:: Create build directory if it doesn't exist
if not exist "build" mkdir build

:: Compile all Java files
echo Compiling Java files...
javac -d build -cp src\main\java src\main\java\com\chessgame\pieces\*.java src\main\java\com\chessgame\board\*.java src\main\java\com\chessgame\game\*.java src\main\java\com\chessgame\gui\*.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo.
    echo To run the game, execute:
    echo java -cp build com.chessgame.gui.ChessGUI
    echo.
    echo Or run: run.bat
) else (
    echo Compilation failed!
    pause
    exit /b 1
)

pause
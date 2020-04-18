#!/bin/bash

BASEDIR=$(dirname "$0")

cd $BASEDIR || exit

javac -d out fieldGames/Field.java fieldGames/FieldState.java fieldGames/Game.java fieldGames/fiveInARowMain.java fieldGames/ticTacToeMain.java fieldGames/State.java fieldGames/tictactoe/TicTacToeField.java fieldGames/tictactoe/TicTacToeGame.java fieldGames/fiveInARow/FiveInARowField.java fieldGames/fiveInARow/FiveInARowGame.java

cp -a resource out/

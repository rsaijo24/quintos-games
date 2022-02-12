package games_java.TicTacToe;

import static games_java.QuintOS.*;

public class TicTacToe {

  String title = """
      TTTTT IIIII   CCC
        T     I    C
        T     I    C
        T     I    C
        T   IIIII   CCC

      TTTTT  AAA    CCC
        T   A   A  C
        T   AAAAA  C
        T   A   A  C
        T   A   A   CCC

      TTTTT  OOO   EEEE
        T   O   O  E
        T   O   O  EEE
        T   O   O  E
        T    OOO   EEEE""";

  String bigSpace = "        \n".repeat(7);

  String bigO = """
       OOOOOO
      OO    OO
      OO    OO
      OO    OO
      OO    OO
      OO    OO
       OOOOOO""".substring(1); // slice off the first newline character

  String bigX = """
      XX    XX
       XX  XX
        XXXX
         XX
        XXXX
       XX  XX
      XX    XX""".substring(1);

  int gridRow = 3;
  int gridCol = 26;

  boolean xTurn = true;

  char[][] board = new char[][]{
    new char[] {' ', ' ', ' '},
    new char[] {' ', ' ', ' '},
    new char[] {' ', ' ', ' '}
  };
  
  int xScore = 0;
  int oScore = 0;

  public TicTacToe() {
    setup();
  }

  void setup() {
    text(title, 5, 6);
    /* PART A: finish the grid of 9x8 spaces */
    text("─".repeat(26), gridRow + 7, gridCol);
    text("─".repeat(26), gridRow + 15, gridCol); // draw another horizontal line

    text("│\n".repeat(23), gridRow, gridCol + 8);
    text("│\n".repeat(23), gridRow, gridCol + 17); // draw another vertical line

    /* PART A: Make the buttons in the grid */

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        final int finalRow = row;
        final int finalCol = col;
        button(bigSpace, gridRow + (row * 8), gridCol + (col * 9), () -> {
          this.takeTurn(finalRow, finalCol);
        });
      }
    }
    randomTurn();
  }

  void randomTurn() {
    int turnRandom = (int) (Math.random() * 2) + 1;

    if (turnRandom == 1) {
      displayTurn('X');
    }
    else {
      displayTurn('O');
      xTurn = false;
    }
  }

  void takeTurn(int row, int col) {
    if (board[row][col] != ' ') {
      alert("That spot is taken, please choose another.", 19, 55, 20);
      return;
    }
    char mark;
    char nextMark;
    if (xTurn) {
      text(bigX,  gridRow + (row * 8), gridCol + (col * 9));
      mark = 'X';
      nextMark = 'O';
    }
    else {
      text(bigO,  gridRow + (row * 8), gridCol + (col * 9)); 
      mark = 'O';
      nextMark = 'X';
    }
    board[row][col] = mark;

    if (checkForWinner(mark)) {
      alert(mark + " player has won!", 11, 55, 20);
      if (mark == 'X') {
        xScore++;
        xTurn = false;
        displayTurn('O');
      }
      else {
        oScore++;
        xTurn = true;
        displayTurn('X');
      }
      startNewGame();
      return;
    }

    if (checkFullBoard()) {
      alert("It's a draw!", 11, 55, 20);
      randomTurn();
      startNewGame();
      return;
    }
    xTurn = !xTurn;

    displayTurn(nextMark);
  }
  
  void displayTurn(char mark) {
    text(mark + "'s turn! \nScore:\nX: " + xScore + "\nO: " + oScore, 4, 55, 20);
  }

  boolean checkForWinner(char mark) {
    for (int i = 0; i < 3; i++) {
      if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) {
        return true;
      }
      else if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark) {
        return true;
      }
    } 

    if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark){
      return true;
    }
    else if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark) {
      return true;
    }
    return false;
  }

  boolean checkFullBoard() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (board [row][col] == ' ') {
          return false;
        }
      }
    }
    return true;
  }

  void startNewGame() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        text(bigSpace, gridRow + (row * 8), gridCol + (col * 9));
        board[row][col] = ' ';
      }
    }
  }

  public static void main(String[] args) {
    new TicTacToe();
  }
}

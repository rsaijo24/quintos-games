package games_java.TicTacAIO;

import static games_java.QuintOS.*;

import java.util.ArrayList;

public class TicTacAIO {

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

  boolean onePlayer = false;
  boolean zeroPlayer = false;
  int level = 0;

  boolean xTurn = true;

  char[][] board = new char[][]{
    new char[] {' ', ' ', ' '},
    new char[] {' ', ' ', ' '},
    new char[] {' ', ' ', ' '}
  };
  
  int xScore = 0;
  int oScore = 0;
  int turn = 1;

  boolean challenge = false;

  public TicTacAIO() {
    chooseGameMode();
  }

  void chooseGameMode() {
    text(title, 5, 6);
    button("Zero Player Start", 10, 30, () -> {
      this.zeroPlayer = true;
      this.setup();
    });
    button("One Player Start", 12, 30, () -> {
      this.difficultySelect();
    });
    button("Two Player Start", 14, 30, () -> {
      this.onePlayer = false;
      this.setup();
    });
  }


  void difficultySelect() {
    onePlayer = true;
    button("Easy Difficulty", 10, 50, () -> {
      this.level = 0;
      this.setup();
    });
    button("Medium Difficulty", 12, 50, () -> {
      this.level = 1;
      this.setup();
    });
    button("Hard Difficulty", 14, 50, () -> {
      this.level = 2;
      this.setup();
    });
    button("Challenge Difficulty", 16, 50, () -> {
      this.challenge = true;
      this.setup();
    });
    button("Impossible Difficulty", 18, 50, () -> {
      this.level = 3;
      this.setup();
    });
  }

  void setup() {
    eraseRect(10, 30, 40, 20);
    
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

    if ((onePlayer && !xTurn) || zeroPlayer) {
      aiTakeTurn();
    }  
  }

  void logBoardString() {
    String s = "";
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        s += board[row][col];
        s += " ";
      }
      s += "\n";
    }
    log(s);
  }

  void aiTakeTurn() {
    log(xTurn);
    log(board);

    char myMark = 'O';
    char yourMark = 'X';

    if (zeroPlayer) {
      if (xTurn) {
        myMark = 'X';
        yourMark = 'O';
        level = 2;
      } else {
        level = 3;
      }
    }

    if (level == 0) {
      for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
          if (board[r][c] == ' ') {
            takeTurn(r, c);
            return;
          }
        }
      }
    }
    if (level == 2 || level == 3) {
      char[] marks = new char[] {myMark, yourMark};
      for (int i = 0; i < 2; i++) {
        for (int r = 0; r < 3; r++) {
          for (int c = 0; c < 3; c++) {
            if (board[r][c] == ' ') {
              board[r][c] = marks[i];
              if (checkForWinner(marks[i])) {
                board[r][c] = ' ';
                takeTurn(r, c);
                return;
              }
              board[r][c] = ' ';
            }
          }
        }
      }
    }
    if (level == 3) {
      if (turn == 1) {
        takeTurn(0, 0);
        return;
      }
      else if (turn == 2) {
        if (board[1][1] == yourMark) {
          takeTurn(0, 0);
          return;
        }
        else {
          takeTurn(1, 1);
          return;
        }
      }
      else if (turn == 3 && board[1][1] == ' ') {
        if (board[0][1] == yourMark || board [0][2] == yourMark || board [2][1] == yourMark || board[2][2] == yourMark) {
          takeTurn(2, 0);
          return;
        }
        else if (board[1][0] == yourMark || board [2][0] == yourMark || board [1][2] == yourMark) {
          takeTurn(0, 2);
          return;
        }
      }
      else if (turn == 3 && board[1][1] == yourMark) {
        takeTurn(2, 2);
        return;
      }
      else if (turn == 4) {
        if (board[0][0] == yourMark && board[2][2] == yourMark || board[0][2]== yourMark && board[2][0] == yourMark){
          takeTurn(0, 1);
          return;
        }
        int[][] cornerCoords = new int[][] { new int[]{0,0}, new int[]{2,0}, new int[]{2,2}, new int[]{0,2} };
        int[][] midCoords = new int[][] { new int[]{0,1}, new int[]{1,0}, new int[]{2,1}, new int[]{1,2} };

        char[] corners = new char[] { board[0][0], board[2][0], board[2][2], board[0][2] };
        char[] mids = new char[] { board[0][1], board[1][0], board[2][1], board[1][2] };

        for (int i = 0; i < 4; i++) {
          if (mids[i] == yourMark && mids[(i+1)%4] == yourMark) {
            takeTurn(cornerCoords[i][0], cornerCoords[i][1]);
            return;
          }
          if (corners[i] == yourMark && mids[(i+2)%4] == yourMark) {
            takeTurn(cornerCoords[(i+1)%4][0], cornerCoords[(i+1)%4][1]);
            return;
          }
          if (corners[i] == yourMark || mids[(i+3)%4] == yourMark) {
            takeTurn(cornerCoords[(i+3)%4][0], cornerCoords[(i+3)%4][1]);
            return;
          }
        }


      }
      else if (turn == 5 && board[1][1] == ' ') {
        if (board[2][1] == yourMark) {
          takeTurn(0, 2);
          return;
        }
        else if (board[1][2] == yourMark) {
          takeTurn(2, 0);
          return;
        }
        else if (board[2][2] == yourMark) {
          takeTurn(0, 2);
          return;
        }
        else {
          takeTurn(2, 2);
          return;
        }
      }
    }
    ArrayList<int[]> avail = new ArrayList<int[]>();
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == ' ') {
          avail.add(new int[] {r, c});
        }
      }
    }
    int randAvail = (int) (Math.random() * avail.size());
    int[] coords = avail.get(randAvail);
    takeTurn(coords[0], coords[1]);
  }

  void randomTurn() {
    if (Math.random() > .5) {
      displayTurn('X');
      xTurn = true;
    } else {
      displayTurn('O');
      xTurn = false;
    }
  }

  void takeTurn(int row, int col) {
    turn++;
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

    logBoardString();

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
      if (mark == 'X' && challenge == true && level < 2) {
        level += 1;
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

    if ((onePlayer && xTurn == false) || zeroPlayer) {
      aiTakeTurn();
    }
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
    turn = 1;
    if ((onePlayer && !xTurn) || zeroPlayer) {
      aiTakeTurn();
    }
  }

  public static void main(String[] args) {
    new TicTacAIO();
  }
}

import java.util.Scanner;

public class TicTacToe {
  private static final int ROW = 3;
  private static final int COL = 3;
  private static final String[][] board = new String[ROW][COL];
  private static final String[] players = new String[2];

  public static void main(String[] args) {
    players[0] = "X"; // player 1
    players[1] = "O"; // player 2
    int row;
    int col;
    int player;
    int move = 0;

    Scanner in = new Scanner(System.in);

    clearBoard();
    while (true) {
      boolean stop = false;
      for (int i = 0; i < players.length; i++) {
        move++;
        player = i + 1;
        while (true) {
          row = SafeInput.getRangedInt(in, "Player " + player + ", enter the row for your move", 1, ROW);
          col = SafeInput.getRangedInt(in, "Player " + player + ", enter the column for your move" , 1, COL);
          System.out.println();
          if (isValidMove(row, col)) {
            board[row - 1][col - 1] = players[i];
            break;
          } else {
            System.out.println("Invalid move, try again");
          }
        }
        display();
        if (isWin(players[i])) {
          System.out.println("Player " + player + " wins!");
          if (!SafeInput.getYNConfirm(in, "Play Again?")) {
            stop = true;
          } else {
            move = 0;
            clearBoard();
          }
        } else if ((move == 7 && isTie(players[i])) || move == 9) {
          System.out.println("It's a tie!");
          if (!SafeInput.getYNConfirm(in, "Play Again?")) {
            stop = true;
          } else {
            move = 0;
            clearBoard();
          }
        }
        if(stop) {
          break;
        }
      }
      if(stop) {
        break;
      }
    }
  }
  private static void clearBoard() {
    for (int c = 0; c < board[0].length; c++) {
      for (int r = 0; r < board.length; r++) {
        board[r][c] = null;
      }
    }
  }
  private static void display() {
    String filled_str;
    String empty_str = "[ ]";
    boolean filled;
    for (String[] strings : board) {
      for (String string : strings) {
        filled = false;
        for (String player : players) {
          filled_str = "[" + player + "]";
          if (string != null && string.equals(player)) {
            System.out.print(filled_str);
            filled = true;
            break;
          }
        }
        if (!filled) {
          System.out.print(empty_str); //blank if nobody moved here
        }
      }
      System.out.print('\n'); // new row
    }
    System.out.println();
  }
  private static boolean isValidMove(int row, int col) {
    return board[row - 1][col - 1] == null;
  }
  private static boolean isWin(String player) {
    return isColWin(player) || (isRowWin(player)) || (isDiagonalWin(player));
  }
  private static boolean isColWin(String player) {
    boolean win;
    for (int c = 0; c < board[0].length; c++) {
      win = true; // will be made false if there is not 3 in a row
      for (String[] strings : board) {
        if (strings[c] == null || !strings[c].equals(player)) {
          win = false;
          break;
        }
      }
      if (win) {
        return true;
      }
    }
    return false;
  }
  private static boolean isRowWin(String player) {
    boolean win;
    for (String[] strings : board) {
      win = true; // will be made false if there is not 3 in a row
      for (String string : strings) {
        if (string == null || !string.equals(player)) {
          win = false;
          break;
        }
      }
      if (win) {
        return true;
      }
    }
    return false;
  }
  private static boolean isDiagonalWin(String player) {
    boolean win = true;
    for (int i = 0, j = board.length - 1; i < board.length && j >= 0; i++, j--) {
      if (board[i][i] == null || ! board[i][i].equals(player)) {
          win = false;
      } else if (board[i][j] == null || ! board[i][j].equals(player)) {
        win = false;
      }
    }
    return win;
  }
  private static boolean isTie(String player) {
    return !isColWin(player) || (!isRowWin(player)) || !(isDiagonalWin(player));
  }
}
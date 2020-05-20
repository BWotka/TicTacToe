
package ttt.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Class game, stores moves and field.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Game implements IGame {
  IPlayer xplay;
  IPlayer oplay;
  protected IPlayer current;


  // x = xplayers field, o = oplayers field, 'empty' = free field
  // gameField[colum][row]
  protected char[][] gameField;

  /**
   * creates a new Game object and does the set up.
   */
  public Game() {



    gameField = new char[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        gameField[i][j] = '-';

      }

    }

  }


  // Spieler X setzen
  @Override
  public void setPlayerX(IPlayer p) {
    xplay = p;
    current = xplay;
  }

  // Spieler O setzen
  @Override
  public void setPlayerO(IPlayer p) {
    oplay = p;
  }

  // Wer ist gerade dran?
  @Override
  public IPlayer currentPlayer() {
    return current;
  }

  // Welche Zuege sind noch moeglich (aka freie Felder)
  @Override
  public List<IMove> remainingMoves() {
    List<IMove> remain = new ArrayList<IMove>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (Character.valueOf(gameField[i][j]).equals('-')) {
          remain.add(new Move(i, j));
        }

      }
    }
    return remain;
  }

  // Zug ausfuehren (Feld setzen), naechster Spieler ist "dran"
  @Override
  public void doMove(IMove m) {
    boolean free = false;
    for (IMove move : remainingMoves()) {
      if (move.getColumn() == m.getColumn() && move.getRow() == m.getRow()) {
        free = true;
      }
    }
    if (free) {
      gameField[m.getColumn()][m.getRow()] = currentPlayer().getSymbol();


      if (currentPlayer() == xplay) {
        current = oplay;
      } else {
        current = xplay;
      }
    } else {
      System.out.println("Not in the list of left moves");
    }

  }

  // Zug zuruecknehmen (Feld setzen), voriger Spieler ist "dran"
  @Override
  public void undoMove(IMove m) {
    // if that field was already changed
    if (!(gameField[m.getColumn()][m.getRow()] == '-')) {
      // set it back to normal
      gameField[m.getColumn()][m.getRow()] = '-';
      if (currentPlayer() == xplay) {
        current = oplay;
      } else {
        current = xplay;
      }
    } else {
      System.out.println("That move was not performed.");
    }
  }

  // Spiel zuende?
  @Override
  public boolean ended() {
    if (remainingMoves().isEmpty()) {
      return true;
    }
    if (evalState(xplay) == 0) {
      return false;
    } else {
      return true;
    }
  }

  // Bewertung des Zustandes aus Sicht des Players p
  // +1: p hat gewonnen
  // -1: p hat verloren
  // 0: Unentschieden
  @Override
  public int evalState(IPlayer p) {
    // check rows
    for (int i = 0; i < 3; i++) {
      if (gameField[0][i] == gameField[1][i] && gameField[1][i] == gameField[2][i]
          && !(gameField[0][i] == '-')) {
        if (gameField[0][i] == p.getSymbol()) {
          return 1;
        } else {
          return -1;
        }
      }
    }

    // check columns
    for (int j = 0; j < 3; j++) {
      if (gameField[j][0] == gameField[j][1] && gameField[j][1] == gameField[j][2]
          && !(gameField[j][0] == '-')) {
        if (gameField[j][0] == p.getSymbol()) {
          return 1;
        } else {
          return -1;
        }
      }
    }

    // check diagonals

    // check diagonal \
    if (gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2]
        && !(gameField[0][0] == '-')) {
      if (gameField[0][0] == p.getSymbol()) {
        return 1;
      } else {
        return -1;
      }

    }
    // check diagonal /
    if (gameField[2][0] == gameField[1][1] && gameField[1][1] == gameField[0][2]
        && !(gameField[2][0] == '-')) {
      if (gameField[2][0] == p.getSymbol()) {
        return 1;
      } else {
        return -1;
      }

    }

    return 0;
  }

  @Override
  public void printField() {
    System.out.println("Current Player: " + currentPlayer().getSymbol());
    System.out.println("The game looks like this now:");
    for (int rowi = 0; rowi < 3; rowi++) {

      for (int columni = 0; columni < 3; columni++) {
        System.out.print(gameField[columni][rowi] + " ");
      }

      System.out.println();
    }
    String state;
    if (evalState(currentPlayer()) == 1) {
      state = " has won!";
    } else if (evalState(currentPlayer()) == -1) {
      state = " has lost!";
    } else {
      state = " has neither lost nor won yet";
    }
    System.out.println("Game State: Player " + currentPlayer().getSymbol() + state);
  }

  /**
   * The game itself runs in here.
   * 
   * @return the game after it ended to read the info
   */

}

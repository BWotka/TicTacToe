/**
 * 
 */
package ttt.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Game implements IGame {
  IPlayer xplay;
  IPlayer oplay;
  IPlayer current;

  List<IMove> madeMoves;
  List<IMove> leftMoves;

  // x = xplayers field, o = oplayers field, 'empty' = free field
  char[][] gameField;

  public Game() {


    madeMoves = new ArrayList<IMove>();
    leftMoves = new ArrayList<IMove>();



    gameField = new char[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        gameField[i][j] = '\u0000';
        leftMoves.add(new Move(i, j));
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
    return leftMoves;
  }

  // Zug ausfuehren (Feld setzen), naechster Spieler ist "dran"
  @Override
  public void doMove(IMove m) {

    if (leftMoves.remove(m)) {
      gameField[m.getColumn()][m.getRow()] = currentPlayer().getSymbol();

      if (currentPlayer() == xplay) {
        current = oplay;
      } else {
        current = xplay;
      }
    }

  }

  // Zug zuruecknehmen (Feld setzen), voriger Spieler ist "dran"
  @Override
  public void undoMove(IMove m) {
    if (madeMoves.remove(m)) {
      leftMoves.add(m);
      gameField[m.getColumn()][m.getRow()] = '\u0000';
      if (currentPlayer() == xplay) {
        current = oplay;
      } else {
        current = xplay;
      }



    }
  }

  // Spiel zuende?
  @Override
  public boolean ended() {
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
          && !(gameField[0][i] == '\u0000')) {
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
          && !(gameField[j][0] == '\u0000')) {
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
        && !(gameField[0][0] == '\u0000')) {
      if (gameField[0][0] == p.getSymbol()) {
        return 1;
      } else {
        return -1;
      }

    }
    // check diagonal /
    if (gameField[2][0] == gameField[1][1] && gameField[1][1] == gameField[0][2]
        && !(gameField[2][0] == '\u0000')) {
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
    for (int i = 0; i < 3; i++) {

      for (int j = 0; j < 3; j++) {
        System.out.print(gameField[i][j] + " ");
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

}

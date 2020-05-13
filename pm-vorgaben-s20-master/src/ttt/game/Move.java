/**
 * 
 */
package ttt.game;

/**
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Move implements IMove {
  int row;
  int column;

  public Move(int pcolumn, int prow) {
    row = prow;
    column = pcolumn;
  }

  // Zeile auf dem Spielfeld
  @Override
  public int getRow() {
    return row;
  }

  // Spalte auf dem Spielfeld
  @Override
  public int getColumn() {
    return column;
  }

}

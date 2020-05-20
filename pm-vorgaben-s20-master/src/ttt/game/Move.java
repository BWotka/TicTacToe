
package ttt.game;

/**
 * Move class, stores row and column.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Move implements IMove {
  private int row;
  private int column;

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

  @Override
  public boolean equals(Object obj) {
    Move m = (Move) obj;
    return (m.getColumn() == this.getColumn() && m.getRow() == this.getRow());
  }

}

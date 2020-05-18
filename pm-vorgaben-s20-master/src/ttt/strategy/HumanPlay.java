
package ttt.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import ttt.game.IGame;
import ttt.game.IMove;
import ttt.game.Move;


/**
 * Strategy is to ask the user for moves.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class HumanPlay implements IGameStrategy {
  IMove nextMove;
  private BufferedReader reader;

  public HumanPlay(BufferedReader breader) {
    System.out.println("The Strategy HumanPlay was choosen. YOU will have to play!");
    reader = breader;
  }

  @Override
  public IMove nextMove(IGame g) {
    // reading the column
    int column;
    System.out.println("Which column do you want to play? [0-2]");
    try {
      column = Integer.parseInt(reader.readLine());
    } catch (NumberFormatException e1) {
      System.out.println("That was not a integer! Default 1 was used.");
      column = 1;
      e1.printStackTrace();
    } catch (IOException e1) {
      System.out.println("Error when reading the console. Default value 1 was used.");
      column = 1;
      e1.printStackTrace();
    }

    // reading the row
    int row;
    System.out.println("Which row do you want to play? [0-2]");
    try {
      row = Integer.parseInt(reader.readLine());
    } catch (NumberFormatException e) {
      System.out.println("That was not a integer! Default 1 was used.");
      row = 1;
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Error when reading the console. Default value 1 was used.");
      row = 1;
      e.printStackTrace();
    }

    // testing if the values are in range
    if (row < 0 || row > 2) {
      System.out.println("The row is not between 0 and 2, default value 1 was used");
      row = 1;
    }
    if (column < 0 || column > 2) {
      System.out.println("The column is not between 0 and 2, default value 1 was used");
      column = 1;
    }

    // testing if the move is still available
    boolean moveInSet = false;
    nextMove = new Move(column, row);
    for (IMove m : g.remainingMoves()) {
      if (m.getColumn() == nextMove.getColumn() && m.getRow() == nextMove.getRow()) {
        moveInSet = true;
      }
    }
    if (!moveInSet) {
      System.out.println(
          "Your move on the coordinates was not available, a other move was choosen for you.");
      nextMove = g.remainingMoves().get(0);
    }

    return nextMove;
  }

}

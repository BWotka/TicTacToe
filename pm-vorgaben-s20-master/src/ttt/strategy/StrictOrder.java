
package ttt.strategy;

import ttt.game.IGame;
import ttt.game.IMove;
import ttt.game.Move;

/**
 * Has a set order which fields should be played.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class StrictOrder implements IGameStrategy {

  @Override
  public IMove nextMove(IGame g) {
    if (g.remainingMoves().contains(new Move(1, 1))) {
      return new Move(1, 1);
    } else if (g.remainingMoves().contains(new Move(1, 0))) {
      return new Move(1, 0);
    } else if (g.remainingMoves().contains(new Move(0, 1))) {
      return new Move(0, 1);
    } else if (g.remainingMoves().contains(new Move(2, 1))) {
      return new Move(2, 1);
    } else if (g.remainingMoves().contains(new Move(1, 2))) {
      return new Move(1, 2);
    } else if (g.remainingMoves().contains(new Move(0, 0))) {
      return new Move(0, 0);
    } else if (g.remainingMoves().contains(new Move(2, 0))) {
      return new Move(2, 0);
    } else if (g.remainingMoves().contains(new Move(0, 2))) {
      return new Move(0, 2);
    } else {
      return g.remainingMoves().get(0);
    }

  }

}


package ttt.strategy;

import ttt.game.IGame;
import ttt.game.IMove;

/**
 * Calculates 2 moves ahead to find a good move.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class NoEasyLose implements IGameStrategy {

  @Override
  public IMove nextMove(IGame g) {
    IMove goodMove = g.remainingMoves().get(g.remainingMoves().size() / 2);
    IMove firstMove;
    IMove secondMove;
    for (int i = 0; i < g.remainingMoves().size(); i++) {
      firstMove = g.remainingMoves().get(i);
      g.doMove(firstMove);
      if (g.evalState(g.currentPlayer()) == 1) {
        g.undoMove(firstMove);
        goodMove = firstMove;
        break;
      }
      for (int j = 0; j < g.remainingMoves().size(); j++) {
        secondMove = g.remainingMoves().get(j);
        g.doMove(secondMove);
        if (g.evalState(g.currentPlayer()) == -1) {
          g.undoMove(secondMove);
          goodMove = secondMove;
          break;
        }
        g.undoMove(secondMove);
      }
      g.undoMove(firstMove);
    }
    return goodMove;

  }

}

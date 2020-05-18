
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

    // if no move will win soon, do a move to the relative center of the field
    IMove goodMove = g.remainingMoves().get(g.remainingMoves().size() / 2);
    IMove testMove;
    IMove testMovesec;

    // for every possible move
    for (int i = 0; i < g.remainingMoves().size(); i++) {
      testMove = g.remainingMoves().get(i);
      g.doMove(testMove);
      // test if the move would make you win
      if (g.evalState(g.currentPlayer()) == 1) {
        // if so undo the move and give it back
        g.undoMove(testMove);
        goodMove = testMove;
        break;
      }
      // test every possible following move
      for (int j = 0; j < g.remainingMoves().size(); j++) {
        testMovesec = g.remainingMoves().get(j);
        g.doMove(testMovesec);
        // if one of them would make the opponent win, do that move and block him
        if (g.evalState(g.currentPlayer()) == 1) {
          // undo the current and following move and give the opponents winning move back
          g.undoMove(testMovesec);
          g.undoMove(testMove);
          goodMove = testMovesec;
          break;
        }
        g.undoMove(testMovesec);
      }
      g.undoMove(testMove);

    }
    return goodMove;

  }

}


package ttt.game;

import ttt.strategy.IGameStrategy;

/**
 * Player class, saves symbol and uses strategy to find a move.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Player implements IPlayer {
  private char symbol;
  private IGameStrategy strat;

  public Player(char psymbol) {
    symbol = psymbol;
  }

  @Override
  public void setStrategy(IGameStrategy s) {
    strat = s;
  }

  @Override
  public IMove nextMove(IGame g) {
    return strat.nextMove(g);
  }

  @Override
  public char getSymbol() {
    return symbol;
  }

  // returns the name of the Strategy
  public String getStrategy() {
    return strat.getClass().getName().substring(13);
  }
}

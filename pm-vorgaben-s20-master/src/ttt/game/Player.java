/**
 * 
 */
package ttt.game;

import ttt.strategy.IGameStrategy;

/**
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Player implements IPlayer {
  char symbol;
  IGameStrategy strat;

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
}

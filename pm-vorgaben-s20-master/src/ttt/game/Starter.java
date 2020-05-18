
package ttt.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import ttt.strategy.HumanPlay;
import ttt.strategy.MinMaxStrategy;

/**
 * Starter class, sets up the game.
 * 
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Starter {

  /**
   * Static void main to set up all the objects and classes.
   * 
   * @param args String[] args
   */
  public static void main(String[] args) {

    InputStreamReader inread = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(inread);

    IPlayer xplayer = new Player('X');
    xplayer.setStrategy(new HumanPlay(reader));
    IPlayer oplayer = new Player('O');
    oplayer.setStrategy(new MinMaxStrategy());
    IGame maingame = new Game();



    maingame.setPlayerX(xplayer);
    maingame.setPlayerO(oplayer);

    System.out.println("Game starting");

    while (maingame.evalState(xplayer) == 0 && !maingame.remainingMoves().isEmpty()) {
      maingame.doMove(maingame.currentPlayer().nextMove(maingame));
      System.out.println("**************");
      maingame.printField();
      System.out.println("**************");
    }

    System.out.println("Game ended");



  }

}

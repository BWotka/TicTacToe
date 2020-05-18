/**
 * 
 */
package ttt.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import ttt.strategy.HumanPlay;
import ttt.strategy.MinMaxStrategy;

/**
 * @author Benedikt Wotka, David Nickel
 *
 */
public class Starter {

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

    while (maingame.evalState(xplayer) == 0) {
      maingame.doMove(maingame.currentPlayer().nextMove(maingame));
      maingame.printField();
    }

  }

}


package ttt.game;

import Charts.Charts;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import jcharts.jcharts;
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
   * @param args String[] args for console input, not used here
   */
  public static void main(String[] args) {

    InputStreamReader inread = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(inread);

    IPlayer xplayer = new Player('X');
    xplayer.setStrategy(new HumanPlay(reader));
    IPlayer oplayer = new Player('O');
    oplayer.setStrategy(new MinMaxStrategy());

    Game maingame = new Game();

    jcharts jchar = new jcharts();


    maingame.setPlayerX(xplayer);
    maingame.setPlayerO(oplayer);

    maingame.playGame();
    // jchar.saveStats(maingame.playGame());
    // jchar.showStats();

    Charts chart = new Charts();
    if (maingame.evalState(xplayer) == 1) {
      chart.addGameToFile("xplayer", maingame.remainingMoves().size());
    }
    if (maingame.evalState(oplayer) == 1) {
      chart.addGameToFile("oplayer", maingame.remainingMoves().size());
    }
    chart.addPlayerToFile("xplayer", maingame.evalState(xplayer));
    chart.addPlayerToFile("oplayer", maingame.evalState(oplayer));

    chart.chartPlayer("xplayer");
    String[] players = {"xplayer", "oplayer"};
    chart.chartPlayers(players);
    chart.chartGames();



  }

}

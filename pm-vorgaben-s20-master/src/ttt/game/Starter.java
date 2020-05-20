
package ttt.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import charts.Charts;
import ttt.strategy.HumanPlay;
import ttt.strategy.StrictOrder;

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

    // setup of the reader for strat human play
    InputStreamReader inread = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(inread);

    // setup of two players. one with strat human play, the other one using strictorder
    Player xplayer = new Player('X');
    xplayer.setStrategy(new HumanPlay(reader));
    Player oplayer = new Player('O');
    oplayer.setStrategy(new StrictOrder());

    // creating the Game instance
    Game maingame = new Game();


    // setting the players in the game
    maingame.setPlayerX(xplayer);
    maingame.setPlayerO(oplayer);

    // starting the game

    System.out.println("Game starting");

    while (maingame.evalState(maingame.currentPlayer()) == 0
        && !maingame.remainingMoves().isEmpty()) {
      System.out.println("**************");
      maingame.printField();
      System.out.println("**************");
      maingame.doMove(maingame.currentPlayer().nextMove(maingame));

    }
    maingame.printField();
    System.out.println("~~~Game ended!~~~");



    // showing stats fir all games until now
    Charts chart = new Charts();
    chart.saveStats(maingame, xplayer, oplayer);
    chart.chartPlayer(xplayer);
    chart.chartPlayer(oplayer);
    Player[] players = {xplayer, oplayer};
    chart.chartPlayers(players);

    chart.chartGames(xplayer, oplayer);



  }

}

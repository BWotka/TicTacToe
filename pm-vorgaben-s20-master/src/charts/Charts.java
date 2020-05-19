package charts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ttt.game.IGame;
import ttt.game.Player;

/**
 * 
 * @author Benedikt Wotka, David Nickel
 */
public class Charts {
  private Scanner sc;
  ArrayList<String> playerList;

  /**
   * 
   * @return
   */

  public void saveStats(IGame game, Player xplayer, Player oplayer) {
    if (game.evalState(xplayer) == 1) {
      addGameToFile(xplayer.getStrategy(), game.remainingMoves().size());
    }
    if (game.evalState(oplayer) == 1) {
      addGameToFile(oplayer.getStrategy(), game.remainingMoves().size());
    }
    addPlayerToFile(xplayer.getStrategy(), game.evalState(xplayer));
    addPlayerToFile(oplayer.getStrategy(), game.evalState(oplayer));
  }

  /**
   * 
   * @param player the name of the player or strategy from that the values would be searched
   * @return values of name
   */
  private int[] valueOfPlayer(Player player) {
    int[] values = {0, 0, 0};
    readListFromFile("Player.txt");
    for (int i = 0; i < playerList.size(); i++) {
      String[] string = playerList.get(i).split(",");
      if (string[0].equals(player.getStrategy())) {
        values[0] = values[0] + Integer.parseInt(string[1]);
        values[1] = values[1] + Integer.parseInt(string[2]);
        values[2] = values[2] + Integer.parseInt(string[3]);
      }
    }
    return values;
  }

  /**
   * shows a pie-chart of player with wins, patts and looses.
   * 
   * @param player name of the player
   */
  public void chartPlayer(Player player) {
    int[] values = valueOfPlayer(player);
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    pieDataset.setValue("wins", values[0]);
    pieDataset.setValue("Unentschieden", values[1]);
    pieDataset.setValue("losses", values[2]);
    JFreeChart chart =
        ChartFactory.createPieChart(player.getStrategy(), pieDataset, true, false, false);

    // create and display a frame...
    ChartFrame frame = new ChartFrame("statistic of " + player, chart);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * shows a bar chart with the compare of wins and loses of the players
   * 
   * @param players name of the player
   */
  public void chartPlayers(Player[] players) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < players.length; i++) {
      int[] values = valueOfPlayer(players[i]);
      if (values[2] != 0) { // not divide with zero
        dataset.addValue((float) values[0] / (float) values[2], "wins/losses",
            players[i].getStrategy());
      } else {
        dataset.addValue(values[0], "number of wins, no losses", players[i].getStrategy());
      }
    }

    JFreeChart chart = ChartFactory.createBarChart("Compare of strategies", "Category", "Value",
        dataset, PlotOrientation.VERTICAL, true, true, false);

    ChartFrame frame = new ChartFrame("Compare Strategies", chart);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * shows a chart of every game how many moves left.
   */
  public void chartGames(Player player1, Player player2) {
    readListFromFile("Plays.txt");
    XYSeries seriePlayer1 = new XYSeries(player1.getStrategy());
    XYSeries seriePlayer2 = new XYSeries(player2.getStrategy());
    for (int i = 0; i < playerList.size(); i++) {
      String[] string = playerList.get(i).split(",");
      if (string[0].contentEquals(player1.getStrategy())) {
        seriePlayer1.add(i, Integer.parseInt(string[1]));
      }
      if (string[0].contentEquals(player2.getStrategy())) {
        seriePlayer2.add(i, Integer.parseInt(string[1]));
      }
    }
    XYSeriesCollection dataset = new XYSeriesCollection();
    if (seriePlayer1 != null) {
      dataset.addSeries(seriePlayer1);
    }
    if (seriePlayer2 != null) {
      dataset.addSeries(seriePlayer2);
    }
    XYLineAndShapeRenderer line = new XYLineAndShapeRenderer();
    NumberAxis xax = new NumberAxis("games");
    NumberAxis yax = new NumberAxis("left moves");
    XYPlot plot = new XYPlot(dataset, xax, yax, line);
    JFreeChart chart = new JFreeChart(plot);
    ApplicationFrame splineframe = new ApplicationFrame("left moves in the games");
    ChartPanel chartPanel = new ChartPanel(chart);
    splineframe.setContentPane(chartPanel);
    splineframe.pack();
    splineframe.setVisible(true);
  }

  /**
   * read a list of a txt.file
   * 
   * @param fileName Name of the txt-file
   * @return
   */
  private boolean readListFromFile(String fileName) {
    playerList = new ArrayList<>();
    try {
      sc = new Scanner(new File(fileName));
    } catch (Exception e) {
      System.out.println("cannot read from file");
      return false;
    }
    while (sc.hasNextLine()) {
      playerList.add(sc.nextLine());
    }
    return true;
  }

  /**
   * writes a player and if he wins, loses or has a patt
   * 
   * @param name Name of player of a game
   * @param game int shows if the player win (1), lose (-1) or has a patt (0)
   * @return
   */
  private boolean addPlayerToFile(String name, int game) {
    FileWriter fw;
    try {
      fw = new FileWriter("Player.txt", true);
    } catch (IOException e) {
      return false;
    }

    BufferedWriter bw = new BufferedWriter(fw);
    try {
      switch (game) {
        case 1:
          bw.append(name + "," + 1 + "," + 0 + "," + 0 + "\n");
          break;
        case -1:
          bw.append(name + "," + 0 + "," + 0 + "," + 1 + "\n");
          break;
        case 0:
          bw.append(name + "," + 0 + "," + 1 + "," + 0 + "\n");
          break;
        default:
          break;
      }


    } catch (IOException e) {
      try {
        bw.close();
      } catch (IOException e1) {
        return false;
      }
      return false;
    }

    try {
      bw.close();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  /**
   * winner and left moves of the last game
   * 
   * @param winner winner of the game
   * @param left left moves in the game
   * @return
   */
  private boolean addGameToFile(String winner, int left) {
    FileWriter fw;
    try {
      fw = new FileWriter("Plays.txt", true);
    } catch (IOException e) {
      return false;
    }

    BufferedWriter bw = new BufferedWriter(fw);
    try {
      bw.write(winner + "," + left + "\n");
    } catch (IOException e) {
      System.out.print("cannot write with BufferedWriter");
      try {
        bw.close();
      } catch (IOException e1) {
        System.out.print("cannot close BufferedWriter");
        return false;
      }
      return false;
    }

    try {
      bw.close();
    } catch (IOException e) {
      return false;
    }

    return true;
  }
}

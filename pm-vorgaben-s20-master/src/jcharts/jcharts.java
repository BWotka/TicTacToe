package jcharts;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class jcharts {
  public static void chartPlayer(String player, int win, int patt, int lose)
  {
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    pieDataset.setValue("Siege", win);
    pieDataset.setValue("Unentschieden", patt);
    pieDataset.setValue("Niederlagen", lose);
    JFreeChart chart = ChartFactory.createPieChart(player,pieDataset, true, false, false);

    // create and display a frame...
    ChartFrame frame = new ChartFrame("Example", chart);
    frame.pack();
    frame.setVisible(true);
  }
  
  public static void chartPlayers(String[] player, int[][] games) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < player.length; i++)
    {
      dataset.addValue(games[i][0],"Siege"  ,player[i]);
      dataset.addValue(games[i][1], "Unentschieden" ,player[i]);
      dataset.addValue(games[i][2], "Niederlagen" ,player[i]);
    }
    
    JFreeChart chart = ChartFactory.createBarChart("Vergleich der Spieler","Category","Value", dataset, PlotOrientation.VERTICAL,true,true,false);
    
    ChartFrame frame = new ChartFrame("Example", chart);
    frame.pack();
    frame.setVisible(true);
  }
  
}

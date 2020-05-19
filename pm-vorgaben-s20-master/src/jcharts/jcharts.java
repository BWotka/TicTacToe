package jcharts;

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
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class jcharts {
  private Scanner sc;
  ArrayList<String> playerList;
  
  private int[] valueOfPlayer(String name) {
    int[] values = {0,0,0};
    readListFromFile("Player.txt");
    for (int i = 0; i < playerList.size(); i++) {
      String[] string = playerList.get(i).split(",");
      if (string[0].equals(name)) {
        values[0] = values[0] + Integer.parseInt(string[1]);
        values[1] = values[1] + Integer.parseInt(string[2]);
        values[2] = values[2] + Integer.parseInt(string[3]);
      }
    }
    return values;
  }
  
  
  public void chartPlayer(String player)
  {
    int[] values = valueOfPlayer("ich");
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    pieDataset.setValue("Siege", values[0]);
    pieDataset.setValue("Unentschieden", values[1]);
    pieDataset.setValue("Niederlagen", values[2]);
    JFreeChart chart = ChartFactory.createPieChart(player,pieDataset, true, false, false);

    // create and display a frame...
    ChartFrame frame = new ChartFrame("Example", chart);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void chartPlayers(String[] player) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < player.length; i++)
    {
      int[] values = valueOfPlayer(player[i]);
      if(values[2]!=0) {
        dataset.addValue((float) values[0]/ (float) values[2],"Siege/Niederlagen"  ,player[i]);
      }
    }
    
    JFreeChart chart = ChartFactory.createBarChart("Vergleich der Spieler","Category","Value", dataset, PlotOrientation.VERTICAL,true,true,false);
    
    ChartFrame frame = new ChartFrame("Example", chart);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void chartPlays() {
    readListFromFile("Plays.txt");
    
    XYSeries serie = new XYSeries("left moves in the games");
    for (int i = 0; i < playerList.size(); i++) {
      String[] string = playerList.get(i).split(",");
      serie.add(i,Integer.parseInt(string[1]));
    }
    
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(serie);
    
    XYLineAndShapeRenderer line = new XYLineAndShapeRenderer();
    NumberAxis xax = new NumberAxis("games");
    NumberAxis yax = new NumberAxis("left moves");
    XYPlot plot = new XYPlot(dataset,xax,yax,line);
    JFreeChart chart = new JFreeChart(plot);
    
    ApplicationFrame splineframe = new ApplicationFrame("left moves in the games");
    ChartPanel chartPanel = new ChartPanel(chart);
    splineframe.setContentPane(chartPanel);
    splineframe.pack();
    splineframe.setVisible(true);
  }
  
  public boolean readListFromFile(String fileName) {
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
  
  public boolean writeListInFile(String name, int win, int patt, int lose) {
    FileWriter fw;
    try {
      fw = new FileWriter("Player.txt",true);
    } catch (IOException e) {
      return false;
    }
    
    BufferedWriter bw = new BufferedWriter(fw);
    try {
      bw.append(name + "," + win + "," + patt + "," + lose + "\n"); 
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
  
  public boolean addGameToList(String winner, int left) {
    FileWriter fw;
    try {
      fw = new FileWriter("Plays.txt",true);
    } catch (IOException e) {
      return false;
    }
    
    BufferedWriter bw = new BufferedWriter(fw);
    try {
      bw.write(winner + "," + left + "\n");
    } catch (IOException e) {
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

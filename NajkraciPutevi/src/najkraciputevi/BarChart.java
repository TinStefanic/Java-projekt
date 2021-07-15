/*
 
 */
package najkraciputevi;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * klasa za crtanje stupčastog dijagrama s vremenima izvođenja svakog algoritma
 * @source: https://stackoverflow.com/questions/29708147/custom-graph-java-swing
 */
public class BarChart {
    
    private ArrayList<CompletedAlgorithm> algs;
    private DefaultCategoryDataset dataset;
    JFreeChart chart;
    String graphName;
    
    public BarChart( ArrayList<CompletedAlgorithm> als, String name)
    {
        algs = als;
        dataset = new DefaultCategoryDataset();
        graphName = name;
        
    }
    
    private void createDataset()
    {
        for(CompletedAlgorithm alg: algs)
            dataset.addValue( alg.getTime(), "row", alg.getName());
    }
    
    private void makeChart(){
        
        createDataset();
        CategoryAxis categoryAxis = new CategoryAxis("algorithm");
        ValueAxis valueAxis = new NumberAxis("time in ms");
        valueAxis.setVisible(true);
         BarRenderer renderer = new BarRenderer() {

            @Override
            public Paint getItemPaint(int row, int column) {
                switch (column) {
                    case 0:
                        return Color.red.darker();
                    case 1:
                        return Color.orange;
                    case 2:
                        return Color.blue.darker();
                    case 3:
                        return Color.magenta.darker();
                    case 4:
                        return Color.green.darker();
                    case 5:
                        return Color.cyan.darker();
                    default:
                        return Color.black;
                }
            }
        };
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter(new StandardBarPainter());
        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);
        chart = new JFreeChart("Comparison of times for " + graphName, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(Color.white);
        
    }
    
    public void display()
    {
        makeChart();
        JFrame f = new JFrame("BarChart");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ChartPanel(chart));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    
}

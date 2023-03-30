package View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Grafico extends JPanel{

    public Grafico() {
        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFrame frame = new JFrame();

        try {
            // Imposta il look and feel Nimbus del fileChooser
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Visualizza la finestra di dialogo per selezionare un file
        int result = fileChooser.showOpenDialog(frame);

        //controllo che effettivamente sia stato selezionato un file
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            HashMap<Float, Date> risultati = ReadCSVFile.readFile(file);
            // Creazione della serie di dati
            XYSeries serie = new XYSeries("Dati di esempio");
            Date primaData=null;
            long DT=0;
            for (Float key : risultati.keySet()) {
                if(primaData==null){
                    primaData=risultati.get(key);
                    DT = primaData.getTime();
                }
                if(risultati.get(key).getTime()-DT<0){
                    DT=risultati.get(key).getTime();
                }
            }

            for (Float key : risultati.keySet()) {

                Date value = risultati.get(key);
                long timeinMillis = value.getTime()-DT;

                serie.add(timeinMillis/1000, key);
            }

            // Creazione della collezione di serie
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(serie);

            // Creazione del grafico
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Grafico a linee", // Titolo del grafico
                    "X", // Etichetta asse X
                    "Y", // Etichetta asse Y
                    dataset, // Collezione di serie
                    PlotOrientation.VERTICAL, // Orientamento del grafico
                    true, // Mostra legenda
                    true, // Mostra tooltip
                    false // Mostra URL
            );
            NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
            yAxis.setAutoRange(true);

            NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
            xAxis.setAutoRange(true);

            // Personalizzazione del grafico
            chart.setBackgroundPaint(Color.white);
            XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);


            JPanel grafico = new JPanel();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(700,700));
            JScrollPane scrollPane = new JScrollPane(chartPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
            scrollPane.getVerticalScrollBar().setUnitIncrement(10);
            scrollPane.setPreferredSize(new Dimension(700,700));
            grafico.add(scrollPane);
            frame1.add(grafico);
            frame1.setSize(new Dimension(700,700));
            frame1.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "Nessun file selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }


    public static void main(String[] args) throws IOException {
        new Grafico();
    }
}

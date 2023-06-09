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
import org.jfree.ui.RectangleInsets;

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
            GeneraGrafico(frame1,fileChooser);
        }
        else{
            JOptionPane.showMessageDialog(null, "Nessun file selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }
    public void GeneraGrafico(JFrame frame1, JFileChooser fileChooser){
        File file = fileChooser.getSelectedFile();

        HashMap<Float, Date> risultati = ReadCSVFile.readFile(file);
        // Creazione della serie di dati
        XYSeries serie = new XYSeries("Dati di esempio");
        //ORDINO I DATI
        //LI USO PER CREARE LA QUERY XML
        //GRAZIE A TEMPO OTTENGO UNA RISPOSTA
        //IN BASE ALLE RISPOSTE PER OGNI TREND O STATO TROVATO  CREO UNA SERIE DIVERSA

        //trovo il DT da sottrarre a ogni vaore sull'asse x per fare iniziare il nostro grafico al tempo 0
        Date primaData=null;
        long DT=0;
        for (Float key : risultati.keySet()) {
            if(primaData==null){
                primaData=risultati.get(key);
                DT = primaData.getTime();
            }
            break;
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
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);



        JPanel grafico = new JPanel();

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1300, 500));

        // Abilita lo zoom con la rotella del mouse
        chartPanel.setMouseZoomable(true);

        // Aggiungi il ChartPanel al contenitore
        add(chartPanel);

        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(500,500));
        grafico.add(scrollPane);
        frame1.add(grafico);
        frame1.setSize(new Dimension(600,600));
        frame1.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Grafico();
    }
}

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.*;

public class Grafico extends JPanel{

    public Grafico() throws IOException {
        // Creazione della serie di dati
        XYSeries serie = new XYSeries("Dati di esempio");
        serie.add(1, 5);
        serie.add(2, 7);
        serie.add(3, 4);
        serie.add(4, 9);
        serie.add(5, 3);

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

        // Personalizzazione del grafico
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // Salvataggio del grafico su file
        int larghezza = 640; // Larghezza in pixel del grafico
        int altezza = 480; // Altezza in pixel del grafico
        File fileGrafico = new File("grafico.png");
        ChartUtilities.saveChartAsPNG(fileGrafico, chart, larghezza, altezza);

        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Visualizza la finestra di dialogo per selezionare un file
        int result = fileChooser.showOpenDialog(frame); //IN filechooser AVRO IL FILE SCELTO
        File file = fileChooser.getSelectedFile();
        ReadExcelFile.readFile(file);

    }


    public static void main(String args[]) throws IOException {
        new Grafico();
    }
}

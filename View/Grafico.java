package View;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class Grafico extends JPanel implements ActionListener {
    private JButton bottoneSelezionaFile;
    private JButton bottoneCreaGrafico;
    private HashMap<Float, Date> risultati;
    private JLabel selectedFileLabel;
    private JFrame frame;

    private JFileChooser fileChooser = new JFileChooser();

    public Grafico() {
        //GENERA FRAME E BUTTON PER FAR PARTIRE IL FILECHOOSER
        this.frame = new JFrame();
        this.frame.setSize(new Dimension(500, 600));
        this.frame.setLayout(new GridBagLayout());

        this.bottoneSelezionaFile = new JButton("Seleziona file");
        this.bottoneSelezionaFile.addActionListener(this);
        this.bottoneSelezionaFile.setPreferredSize(new Dimension(200, 50));

        this.selectedFileLabel = new JLabel("File da caricare");
        this.selectedFileLabel.setPreferredSize(new Dimension(300,200));
        this.selectedFileLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.bottoneCreaGrafico = new JButton("Crea grafico");
        this.bottoneCreaGrafico.addActionListener(this);
        this.bottoneCreaGrafico.setVisible(false);
        this.bottoneCreaGrafico.setPreferredSize(new Dimension(200, 50));

        this.frame.add(selectedFileLabel);
        this.frame.add(bottoneSelezionaFile);
        this.frame.add(bottoneCreaGrafico);


        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);


        try {
            // Imposta il look and feel Nimbus del fileChooser
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.bottoneSelezionaFile) {
            fileChooser.setPreferredSize(new Dimension(800, 600));
            // Visualizza la finestra di dialogo per selezionare un file
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.selectedFileLabel.setText(selectedFile.getName());
                this.bottoneSelezionaFile.setVisible(false);
                this.bottoneCreaGrafico.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Nessun file selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        if(e.getSource() == this.bottoneCreaGrafico) {
            generaGrafico(this.fileChooser);
        }
    }

    public void generaGrafico(JFileChooser fileChooser) {
        File file = fileChooser.getSelectedFile();

        risultati = ReadCSVFile.readFile(file);
        // Creazione della serie di dati
        XYSeries serie = new XYSeries("Dati di esempio");

        ordinaDati(serie);

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
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setAutoRange(false);
        int start = 0;
        int end = 200;
        xAxis.setRange(start, end);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(false);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setMouseZoomable(true, false);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            // Implementa il metodo chartMouseClicked
            public void chartMouseClicked(ChartMouseEvent event) {
                // Verifica se è stato premuto il pulsante sinistro del mouse
                if (event.getTrigger().getButton() == MouseEvent.BUTTON1) {
                    // Calcola la nuova posizione dell'asse X
                    double x = plot.getDomainAxis().java2DToValue(event.getTrigger().getX(),
                            chartPanel.getScreenDataArea(), plot.getDomainAxisEdge());

                    // Calcola la nuova posizione dell'asse Y
                    double y = plot.getRangeAxis().java2DToValue(event.getTrigger().getY(),
                            chartPanel.getScreenDataArea(), plot.getRangeAxisEdge());

                    // Calcola la larghezza e l'altezza dell'area visibile del grafico
                    double width = plot.getDomainAxis().getRange().getLength();
                    double height = plot.getRangeAxis().getRange().getLength();

                    // Imposta il nuovo range dell'asse X e dell'asse Y in base alla nuova posizione
                    plot.getDomainAxis().setRange(x - width / 2, x + width / 2);
                    plot.getRangeAxis().setRange(y - height / 2, y + height / 2);
                }
            }

            public void chartMouseMoved(ChartMouseEvent event) {
                // Metodo non utilizzato in questo esempio
            }
        });


        JScrollPane scrollPane = new JScrollPane(chartPanel);
        if (chartPanel.getPreferredSize().width > 800) {
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        } else {
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
        JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, start, end - start, 0, 1000);
        scrollBar.addAdjustmentListener(e1 -> xAxis.setRange(e1.getValue(), e1.getValue() + end - start));
        scrollPane.setHorizontalScrollBar(scrollBar);

        JFrame frame1 = new JFrame("Line Chart Example");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(scrollPane);
        frame1.pack();
        frame1.setVisible(true);
    }

    public void ordinaDati(XYSeries serie) {
        //ORDINO I DATI
        //LI USO PER CREARE LA QUERY XML
        //GRAZIE A TEMPO OTTENGO UNA RISPOSTA
        //IN BASE ALLE RISPOSTE PER OGNI TREND O STATO TROVATO  CREO UNA SERIE DIVERSA

        Date primaData = null;
        long DT = 0;
        for (Float key : risultati.keySet()) {
            if (primaData == null) {
                primaData = risultati.get(key);
            } else {
                if (risultati.get(key).before(primaData)) {
                    primaData = risultati.get(key);
                }
            }
        }

        for (Float key : risultati.keySet()) {
            Date value = risultati.get(key);
            long timeinMillis = value.getTime() - primaData.getTime(); // calcola la differenza tra i tempi
            serie.add(timeinMillis / 1000, key);
        }
    }

    public static void main(String[] args) {
        new Grafico();
    }
}

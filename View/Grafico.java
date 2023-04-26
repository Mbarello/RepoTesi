package View;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Grafico extends JPanel implements ActionListener {
    private JLabel parametriOrdinamentoStatoLabel;
    private JLabel minDurationStatoLabel;
    private JTextField minDurationStatoField;
    private JLabel maxTimeGapStatoLabel;
    private JTextField maxTimeGapStatoField;
    private JLabel minThreshold;
    private JTextField minThresholdField;
    private JTextField maxThresholdField;
    private JLabel maxThreshold;
    private JButton bottoneSelezionaFile;
    private JButton bottoneCreaGrafico;
    private JButton confermaScelta;
    private JComboBox<String> selezionaOrdine;
    private JPanel topPanel;
    private JLabel fileLabel;
    private JPanel filePanel;
    private JPanel bottomPanel;
    private JTextField localWinField;
    private JTextField minDurationField;
    private JTextField maxRateField;
    private JTextField minRateField;
    private JTextField maxTimeGapField;
    private JLabel ordineLabel;
    private JPanel centerPanel;
    private JLabel sceltaLabel;
    private JLabel parametriOrdinamentoLabel;
    private JLabel localWinLabel;
    private JLabel minDurationLabel;
    private JLabel maxTimeGapLabel;
    private JLabel minRateLabel;
    private JLabel maxRateLabel;
    private final JButton creaQuery;
    private LinkedHashMap<LocalDateTime, Float> risultati;
    private JLabel selectedFileLabel;
    private JComboBox selezionaScelta;
    private final JFrame frame;
    private int result = -1;
    private JFileChooser fileChooser = new JFileChooser();
    private JTextField maxOscMarginField;
    private JLabel maxOscMarginLabel;
    private File risposta;
    private ArrayList<Intervallo> rispostaQuery;
    private XYSeriesCollection dataset1;

    public Grafico() {
        // Imposta il look and feel Nimbus per migliorare l'aspetto del file chooser
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Genera il frame principale
        this.frame = new JFrame("Grafico");
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setMinimumSize(new Dimension(600, 800));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        this.pannelloTop();
        this.pannelloInferiore();
        this.pannelloCentro();
        this.sceltaTrend();
        this.sceltaStato();

        this.creaQuery = new JButton("Crea query XML");
        this.creaQuery.addActionListener(this);
        this.creaQuery.setVisible(false);
        this.centerPanel.add(this.creaQuery);

        this.frame.add(centerPanel, BorderLayout.CENTER);

        // Centra il frame sullo schermo
        this.frame.setLocationRelativeTo(null);

        // Rendi il frame visibile
        this.frame.setVisible(true);
    }

    public void pannelloTop() {
        // Pannello superiore con il pulsante "Seleziona file" e la label per mostrare il file selezionato
        this.topPanel = new JPanel(new BorderLayout());
        this.topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.fileLabel = new JLabel("File selezionato:");
        this.fileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.topPanel.add(this.fileLabel, BorderLayout.NORTH);
        this.filePanel = new JPanel(new BorderLayout());
        this.filePanel.setPreferredSize(new Dimension(400, 40));
        this.selectedFileLabel = new JLabel("Nessun file selezionato");
        this.selectedFileLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.selectedFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.filePanel.add(this.selectedFileLabel, BorderLayout.CENTER);
        this.bottoneSelezionaFile = new JButton("Seleziona file");
        this.bottoneSelezionaFile.addActionListener(this);
        this.bottoneSelezionaFile.setPreferredSize(new Dimension(150, 40));
        this.filePanel.add(this.bottoneSelezionaFile, BorderLayout.EAST);
        this.topPanel.add(this.filePanel, BorderLayout.CENTER);
        this.frame.add(this.topPanel, BorderLayout.NORTH);
    }

    public void pannelloInferiore() {
        // Pannello inferiore con il pulsante "Crea grafico"
        this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.bottoneCreaGrafico = new JButton("Crea grafico");
        this.bottoneCreaGrafico.addActionListener(this);
        this.bottoneCreaGrafico.setPreferredSize(new Dimension(150, 40));
        this.bottoneCreaGrafico.setEnabled(false);
        this.bottomPanel.add(this.bottoneCreaGrafico);
        this.frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void pannelloCentro() {
        //Aggiunge la JComboBox per la scelta dell'ordinamento
        this.centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.sceltaLabel = new JLabel("Scelta:");
        this.sceltaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.centerPanel.add(this.sceltaLabel);
        this.selezionaScelta = new JComboBox<>(new String[]{"-", "Stato", "Trend"});
        this.selezionaScelta.setPreferredSize(new Dimension(100, 30));
        this.confermaScelta = new JButton("Conferma");
        this.confermaScelta.addActionListener(this);
        this.centerPanel.add(this.selezionaScelta);
        this.centerPanel.add(this.confermaScelta);
    }

    public void sceltaTrend() {
        //Aggiungi la JLabel e le JTextField per la selezione dei parametri di ordinamento
        this.ordineLabel = new JLabel("Ordinamento:");
        this.ordineLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.centerPanel.add(this.ordineLabel);
        String[] ordineOpzioni = {"Fortemente Decrescente", "Decrescente", "Stazionario", "Crescente", "Fortemente Crescente"};
        this.selezionaOrdine = new JComboBox<>(ordineOpzioni);
        this.selezionaOrdine.setPreferredSize(new Dimension(200, 30));
        this.selezionaOrdine.setSelectedItem("Stazionario");
        this.centerPanel.add(this.selezionaOrdine);
        this.selezionaOrdine.setVisible(false);
        this.ordineLabel.setVisible(false);

        // Aggiungi i campi per la selezione dei parametri di ordinamento
        this.parametriOrdinamentoLabel = new JLabel("Parametri di ordinamento:");
        this.parametriOrdinamentoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.centerPanel.add(this.parametriOrdinamentoLabel);
        this.minDurationLabel = new JLabel("Min duration:");
        this.centerPanel.add(this.minDurationLabel);
        this.minDurationField = new JTextField("0", 5);
        this.centerPanel.add(this.minDurationField);
        this.maxTimeGapLabel = new JLabel("Max time gap:");
        this.centerPanel.add(this.maxTimeGapLabel);
        this.maxTimeGapField = new JTextField("0", 5);
        this.centerPanel.add(this.maxTimeGapField);
        this.minRateLabel = new JLabel("Min rate:");
        this.centerPanel.add(this.minRateLabel);
        this.minRateField = new JTextField("0.0", 5);
        this.centerPanel.add(this.minRateField);
        this.maxRateLabel = new JLabel("Max rate:");
        this.centerPanel.add(this.maxRateLabel);
        this.maxRateField = new JTextField("0.0", 5);
        this.centerPanel.add(this.maxRateField);
        this.localWinLabel = new JLabel("Local win:");
        this.centerPanel.add(this.localWinLabel);
        this.localWinField = new JTextField("0", 5);
        this.centerPanel.add(this.localWinField);

        this.maxOscMarginLabel = new JLabel("Max Oscillation margin");
        this.centerPanel.add(this.maxOscMarginLabel);
        this.maxOscMarginField = new JTextField("0", 5);
        this.centerPanel.add(this.maxOscMarginField);

        this.parametriOrdinamentoLabel.setVisible(false);
        this.minDurationField.setVisible(false);
        this.minDurationLabel.setVisible(false);
        this.maxTimeGapField.setVisible(false);
        this.maxTimeGapLabel.setVisible(false);
        this.maxRateField.setVisible(false);
        this.maxRateLabel.setVisible(false);
        this.minRateLabel.setVisible(false);
        this.minRateField.setVisible(false);
        this.maxRateLabel.setVisible(false);
        this.maxRateField.setVisible(false);
        this.localWinLabel.setVisible(false);
        this.localWinField.setVisible(false);
        this.maxOscMarginField.setVisible(false);
        this.maxOscMarginLabel.setVisible(false);
    }

    public void sceltaStato() {
        //Se è selezionato lo stato devo far comparire dei Text field dove inserire la min duration, il maximum time gap, la soglia minima e la soglia massima
        this.parametriOrdinamentoStatoLabel = new JLabel("Parametri di ordinamento:");
        this.parametriOrdinamentoStatoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.centerPanel.add(this.parametriOrdinamentoStatoLabel);
        this.minDurationStatoLabel = new JLabel("Min duration:");
        this.centerPanel.add(this.minDurationStatoLabel);
        this.minDurationStatoField = new JTextField("0", 5);
        this.centerPanel.add(this.minDurationStatoField);
        this.maxTimeGapStatoLabel = new JLabel("Max time gap:");
        this.centerPanel.add(this.maxTimeGapStatoLabel);
        this.maxTimeGapStatoField = new JTextField("0", 5);
        this.centerPanel.add(this.maxTimeGapStatoField);
        this.minThreshold = new JLabel("Soglia minima:");
        this.centerPanel.add(this.minThreshold);
        this.minThresholdField = new JTextField("0", 5);
        this.centerPanel.add(this.minThresholdField);
        this.maxThreshold = new JLabel("Soglia massima:");
        this.centerPanel.add(this.maxThreshold);
        this.maxThresholdField = new JTextField("0", 5);
        this.centerPanel.add(this.maxThresholdField);

        this.parametriOrdinamentoStatoLabel.setVisible(false);
        this.minDurationStatoField.setVisible(false);
        this.minDurationStatoLabel.setVisible(false);
        this.maxTimeGapStatoLabel.setVisible(false);
        this.maxTimeGapStatoField.setVisible(false);
        this.minThreshold.setVisible(false);
        this.minThresholdField.setVisible(false);
        this.maxThreshold.setVisible(false);
        this.maxThresholdField.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.bottoneSelezionaFile) {
            this.fileChooser = new JFileChooser();
            this.fileChooser.setPreferredSize(new Dimension(800, 600));
            // Visualizza la finestra di dialogo per selezionare un file
            this.result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.selectedFileLabel.setText(selectedFile.getName());
                this.bottoneSelezionaFile.setEnabled(false);
                this.bottoneCreaGrafico.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Nessun file selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        if (e.getSource() == this.bottoneCreaGrafico) {
            generaGrafico(this.fileChooser);
        }
        if (e.getSource() == this.confermaScelta && this.selezionaScelta.getSelectedItem().equals("Trend")) {
            this.creaQuery.setVisible(false);
            //Rende visibili i field che compaiono con la scelta di Trend
            this.selezionaOrdine.setVisible(true);
            this.ordineLabel.setVisible(true);
            this.parametriOrdinamentoLabel.setVisible(true);
            this.minDurationField.setVisible(true);
            this.minDurationLabel.setVisible(true);
            this.maxTimeGapField.setVisible(true);
            this.maxTimeGapLabel.setVisible(true);
            this.maxRateField.setVisible(true);
            this.maxRateLabel.setVisible(true);
            this.minRateLabel.setVisible(true);
            this.minRateField.setVisible(true);
            this.localWinLabel.setVisible(true);
            this.localWinField.setVisible(true);
            this.maxOscMarginField.setVisible(false);
            this.maxOscMarginLabel.setVisible(false);
            if (this.selezionaOrdine.getSelectedItem() == "Stazionario") {
                this.maxOscMarginField.setVisible(true);
                this.maxOscMarginLabel.setVisible(true);
                this.minRateLabel.setVisible(false);
                this.minRateField.setVisible(false);
                this.localWinLabel.setVisible(false);
                this.localWinField.setVisible(false);
            }

            //Rende visibile il pulsante di Query
            if (this.result == JFileChooser.APPROVE_OPTION) {
                this.creaQuery.setVisible(true);
            }
            //Rende non visibili i campi che non gli servono
            this.parametriOrdinamentoStatoLabel.setVisible(false);
            this.minDurationStatoField.setVisible(false);
            this.minDurationStatoLabel.setVisible(false);
            this.maxTimeGapStatoLabel.setVisible(false);
            this.maxTimeGapStatoField.setVisible(false);
            this.minThreshold.setVisible(false);
            this.minThresholdField.setVisible(false);
            this.maxThreshold.setVisible(false);
            this.maxThresholdField.setVisible(false);

        } else if (e.getSource() == this.confermaScelta && Objects.equals(this.selezionaScelta.getSelectedItem(), "Stato")) {
            this.creaQuery.setVisible(false);
            //Rende non visibili i field che compaiono con la scelta di Trend
            this.parametriOrdinamentoLabel.setVisible(false);
            this.minDurationField.setVisible(false);
            this.minDurationLabel.setVisible(false);
            this.maxTimeGapField.setVisible(false);
            this.maxTimeGapLabel.setVisible(false);
            this.maxRateField.setVisible(false);
            this.maxRateLabel.setVisible(false);
            this.minRateLabel.setVisible(false);
            this.minRateField.setVisible(false);
            this.maxRateLabel.setVisible(false);
            this.maxRateField.setVisible(false);
            this.localWinLabel.setVisible(false);
            this.localWinField.setVisible(false);
            this.selezionaOrdine.setVisible(false);
            this.ordineLabel.setVisible(false);
            this.creaQuery.setVisible(false);
            this.maxOscMarginField.setVisible(false);
            this.maxOscMarginLabel.setVisible(false);

            if (this.result == JFileChooser.APPROVE_OPTION) {
                this.creaQuery.setVisible(true);
            }
            //Rende visibili i campi che gli servono
            this.parametriOrdinamentoStatoLabel.setVisible(true);
            this.minDurationStatoField.setVisible(true);
            this.minDurationStatoLabel.setVisible(true);
            this.maxTimeGapStatoLabel.setVisible(true);
            this.maxTimeGapStatoField.setVisible(true);
            this.minThreshold.setVisible(true);
            this.minThresholdField.setVisible(true);
            this.maxThreshold.setVisible(true);
            this.maxThresholdField.setVisible(true);
            //Rende visibile il pulsante di query

        } else if (e.getSource() == this.confermaScelta && this.selezionaScelta.getSelectedItem().equals("-")) {
            //Rende invisibili tutti i campi
            this.parametriOrdinamentoLabel.setVisible(false);
            this.minDurationField.setVisible(false);
            this.minDurationLabel.setVisible(false);
            this.maxTimeGapField.setVisible(false);
            this.maxTimeGapLabel.setVisible(false);
            this.maxRateField.setVisible(false);
            this.maxRateLabel.setVisible(false);
            this.minRateLabel.setVisible(false);
            this.minRateField.setVisible(false);
            this.maxRateLabel.setVisible(false);
            this.maxRateField.setVisible(false);
            this.localWinLabel.setVisible(false);
            this.localWinField.setVisible(false);
            this.selezionaOrdine.setVisible(false);
            this.ordineLabel.setVisible(false);
            this.parametriOrdinamentoStatoLabel.setVisible(false);
            this.minDurationStatoField.setVisible(false);
            this.minDurationStatoLabel.setVisible(false);
            this.maxTimeGapStatoLabel.setVisible(false);
            this.maxTimeGapStatoField.setVisible(false);
            this.minThreshold.setVisible(false);
            this.minThresholdField.setVisible(false);
            this.maxThreshold.setVisible(false);
            this.maxThresholdField.setVisible(false);
            //Rende invisibile il pulsante di query
            this.creaQuery.setVisible(false);
            this.maxOscMarginField.setVisible(false);
            this.maxOscMarginLabel.setVisible(false);
        }

        if (e.getSource() == this.creaQuery) {
            if (Objects.equals(this.selezionaScelta.getSelectedItem(), "Trend")) {

                try {
                    Query query = new Query();
                    this.risposta = query.creaQuery(Objects.requireNonNull(this.selezionaOrdine.getSelectedItem()).toString(), this.minDurationField.getText(), this.maxTimeGapField.getText(), this.minRateField.getText(), this.maxRateField.getText(), this.localWinField.getText(), fileChooser.getSelectedFile(), this.maxOscMarginField.getText());
                    if (this.risposta.exists()) {
                        QueryPerform queryPerform = new QueryPerform();
                        queryPerform.queryPerform(this.risposta);
                    }
                } catch (XMLStreamException | TransformerException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (Objects.equals(this.selezionaScelta.getSelectedItem(), "Stato")) {
                try {
                    Query query = new Query();
                    this.risposta = query.creaQuery(this.minDurationStatoField.getText(), this.maxTimeGapStatoField.getText(), this.minThresholdField.getText(), this.maxThresholdField.getText(), fileChooser.getSelectedFile());
                    if (this.risposta.exists()) {
                        QueryPerform queryPerform = new QueryPerform();
                        queryPerform.queryPerform(this.risposta);
                    }
                } catch (XMLStreamException | IOException | TransformerException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


    public void generaGrafico(JFileChooser fileChooser) {
        File file = fileChooser.getSelectedFile();

        this.risultati = ReadCSVFile.readFile(file);
        // Creazione della serie di dati
        XYSeries serie = new XYSeries("Dati di esempio");

        LocalDateTime DT = this.ordinaDati(serie); //DT da sottrarre ale serie trovate come risultato per rendere il grafico compatibile

        // Creazione della collezione di serie
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        String path = System.getProperty("user.dir") + "/risposta/";
        RecuperaDati RD = new RecuperaDati();
        this.rispostaQuery = RD.RecuperaDati(path);

        this.dataset1 = new XYSeriesCollection();
        this.aggiungiSerie(this.rispostaQuery, DT);
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "Grafico a linee", // Titolo del grafico
                "X", // Etichetta asse X
                "Y", // Etichetta asse Y
                this.dataset1, // Collezione di serie
                PlotOrientation.VERTICAL, // Orientamento del grafico
                false, // Mostra legenda
                true, // Mostra tooltip
                false // Mostra URL
        );

        chart1.setBackgroundPaint(Color.white);
        XYPlot plot1 = chart1.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2.5, -2.5, 5, 5)); // Imposta la forma dei punti (in questo caso, un cerchio con raggio 2.5)
        plot1.setRenderer(renderer);

        plot1.setDomainPannable(true);
        plot1.setRangePannable(true);
        plot1.setDomainZeroBaselineVisible(true);
        plot1.setBackgroundPaint(Color.lightGray);
        plot1.setDomainGridlinePaint(Color.white);
        plot1.setRangeGridlinePaint(Color.white);


        NumberAxis xAxis1 = (NumberAxis) plot1.getDomainAxis();
        xAxis1.setAutoRange(true);
        int start1 = 0;
        int end1 = 200;
        xAxis1.setRange(start1, end1);


        ChartPanel chartPanel1 = new ChartPanel(chart1);
        chartPanel1.setPreferredSize(new Dimension(800, 400));
        chartPanel1.setDomainZoomable(true);
        chartPanel1.setRangeZoomable(false);
        chartPanel1.setMouseWheelEnabled(true);
        chartPanel1.setMouseZoomable(true, false);

        chartPanel1.addChartMouseListener(new ChartMouseListener() {
            // Implementa il metodo chartMouseClicked
            public void chartMouseClicked(ChartMouseEvent event) {
                // Verifica se è stato premuto il pulsante sinistro del mouse
                if (event.getTrigger().getButton() == MouseEvent.BUTTON1) {
                    // Calcola la nuova posizione dell'asse X
                    double x = plot1.getDomainAxis().java2DToValue(event.getTrigger().getX(),
                            chartPanel1.getScreenDataArea(), plot1.getDomainAxisEdge());

                    // Calcola la nuova posizione dell'asse Y
                    double y = plot1.getRangeAxis().java2DToValue(event.getTrigger().getY(),
                            chartPanel1.getScreenDataArea(), plot1.getRangeAxisEdge());

                    // Calcola la larghezza e l'altezza dell'area visibile del grafico
                    double width = plot1.getDomainAxis().getRange().getLength();
                    double height = plot1.getRangeAxis().getRange().getLength();

                    // Imposta il nuovo range dell'asse X e dell'asse Y in base alla nuova posizione
                    plot1.getDomainAxis().setRange(x - width / 2, x + width / 2);
                    plot1.getRangeAxis().setRange(y - height / 2, y + height / 2);
                }
            }

            public void chartMouseMoved(ChartMouseEvent event) {
                // Metodo non utilizzato in questo esempio
            }
        });
        JScrollBar scrollBar1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 6700);
        scrollBar1.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int scrollValue1 = e.getValue();
                for (int i = scrollValue1; i < 6700; i++) {
                    plot1.getDomainAxis().setRange(scrollValue1, scrollValue1 + 100);
                }
            }
        });

        JPanel chartPanelWithScrollbar1 = new JPanel(new BorderLayout());
        chartPanelWithScrollbar1.add(chartPanel1, BorderLayout.CENTER);
        chartPanelWithScrollbar1.add(scrollBar1, BorderLayout.SOUTH);


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
        plot.getRenderer().setSeriesPaint(0, Color.BLACK);

        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);


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
        JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 6700);
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int scrollValue = e.getValue();
                for (int i = scrollValue; i < 6700; i++) {
                    plot.getDomainAxis().setRange(scrollValue, scrollValue + 100);
                }
            }
        });

        JPanel chartPanelWithScrollbar = new JPanel(new BorderLayout());
        chartPanelWithScrollbar.add(chartPanel, BorderLayout.CENTER);
        chartPanelWithScrollbar.add(scrollBar, BorderLayout.SOUTH);

// Aggiungi il nuovo pannello al contenuto della finestra
        JFrame frame1 = new JFrame("Line Chart Example");
        JFrame frame2 = new JFrame("Line Chart Example 2");
        frame1.setContentPane(chartPanelWithScrollbar);
        frame2.setContentPane(chartPanelWithScrollbar1);
        frame1.pack();
        frame1.setVisible(true);
        frame2.pack();
        frame2.setVisible(true);

    }

    public LocalDateTime ordinaDati(XYSeries serie) {
        //ORDINO I DATI
        //LI USO PER CREARE LA QUERY XML
        //GRAZIE A TEMPO OTTENGO UNA RISPOSTA
        //IN BASE ALLE RISPOSTE PER OGNI TREND O STATO TROVATO  CREO UNA SERIE DIVERSA

        //trovo il DT da sottrarre a ogni valore sull'asse x per fare iniziare il nostro grafico al tempo 0
        LocalDateTime primaData = null;

        for (LocalDateTime key : risultati.keySet()) {
            if (primaData == null) {
                primaData = key;
            }
            break;
        }
        for (LocalDateTime key : risultati.keySet()) {
            LocalDateTime value = key;
            long seconds = ChronoUnit.SECONDS.between(primaData, value);
            serie.add(seconds, risultati.get(key));
        }
        return primaData;
    }

    private void aggiungiSerie(ArrayList<Intervallo> intervalli, LocalDateTime primaData) {
        int i = 0;
        if (this.risultati.isEmpty()) {
            System.out.println("La mappa risultati è vuota");
            return;
        }
        for (Intervallo intervallo : intervalli) {
            // Creo una serie per ogni intervallo nella risposta
            XYSeries serie1 = new XYSeries("" + i);
            i++;
            for (LocalDateTime key : this.risultati.keySet()) {
                if (key.isAfter(intervallo.getInizio()) || key.isEqual(intervallo.getInizio())) {
                    if (key.isBefore(intervallo.getFine()) || key.isEqual(intervallo.getFine())) {
                        long seconds = ChronoUnit.SECONDS.between(primaData, key);
                        Float value = this.risultati.get(key);
                        if (value != null) {
                            serie1.add(seconds, value);
                        }
                    }
                }
            }
            if (!serie1.isEmpty()) {
                this.dataset1.addSeries(serie1);
            }
        }
    }

    public static void main(String[] args) {
        new Grafico();
    }
}
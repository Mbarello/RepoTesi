import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.sql.*;

import cases.*;
import indexing.*;
import loader.*;
import retrieval.*;
import temporalabstraction.configmodule.*;
import temporalabstraction.configuration.*;
import temporalabstraction.instance.*;
import temporalabstraction.tamodule.*;
import timeserie.*;
import transform.*;
import visual.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class RHENE extends JFrame {
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  JPanel jPanel2 = new JPanel();
  TitledBorder titledBorder2;
  java.awt.List list2 = new java.awt.List();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField Epsilon1 = new JTextField();
  Border border1;
  TitledBorder titledBorder3;
  JPanel jPanel4 = new JPanel();
  Border border2;
  TitledBorder titledBorder4;
  JLabel jLabel2 = new JLabel();
  JTextField Tempo1 = new JTextField();
  JTextField KNN = new JTextField();
  JLabel jLabel4 = new JLabel();
  Border border3;
  TitledBorder titledBorder5;
  Border border4;
  TitledBorder titledBorder6;
  JLabel jLabel5 = new JLabel();
  JTextField Tempo2 = new JTextField();
  JMenuItem jMenuItem5 = new JMenuItem();
  JComboBox Paziente1 = new JComboBox();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JComboBox Dialisi1 = new JComboBox();
  JLabel jLabel8 = new JLabel();
  JComboBox Segnale1 = new JComboBox();
  JButton Visualizza1 = new JButton();
  JButton Visualizza2 = new JButton();
  JCheckBox normalizzata1 = new JCheckBox();
  JCheckBox interpolazione = new JCheckBox();
  Border border5;
  TitledBorder titledBorder7;
  Border border6;
  JPanel jPanel6 = new JPanel();
  Border border7;
  TitledBorder titledBorder8;
  JLabel jLabel9 = new JLabel();
  JTextField KNN1 = new JTextField();
  Border border8;
  TitledBorder titledBorder9;
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JCheckBox ignoraClassificazione = new JCheckBox();
  JButton visCaso1 = new JButton();
  JButton visCaso2 = new JButton();
  DialogHandler dh = new DialogHandler();
  TMessageDialog dialog;
  JButton chiudiCaso = new JButton();
  JButton chiudiCaso2 = new JButton();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenuItem jMenuItem7 = new JMenuItem();
  JMenuItem jMenuItem8 = new JMenuItem();
  JMenuItem jMenuItem9 = new JMenuItem();
  JMenuItem jMenuItem10 = new JMenuItem();
  JMenuItem jMenuItem11 = new JMenuItem();
  JMenuItem jMenuItem13 = new JMenuItem();
  JMenuItem jMenuItem12 = new JMenuItem();
  JMenuItem jMenuItem14 = new JMenuItem();
  JMenuItem jMenuItem15 = new JMenuItem();
  JCheckBox normalizzata2 = new JCheckBox();
  JTextField Epsilon2 = new JTextField();
  JLabel jLabel3 = new JLabel();
  Border border9;
  TitledBorder titledBorder10;
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JTextField coeffourier = new JTextField();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JTextField dimensioniattive = new JTextField();
  JTextField figlipernodo = new JTextField();
  JCheckBox considero8 = new JCheckBox();
  JLabel jLabel35 = new JLabel();
  JLabel jLabel125 = new JLabel();
  JCheckBox considero6 = new JCheckBox();
  JLabel jLabel37 = new JLabel();
  JCheckBox considero2 = new JCheckBox();
  JCheckBox considero9 = new JCheckBox();
  JLabel jLabel34 = new JLabel();
  JCheckBox considero10 = new JCheckBox();
  JLabel jLabel33 = new JLabel();
  JLabel jLabel123 = new JLabel();
  JLabel jLabel32 = new JLabel();
  JCheckBox considero3 = new JCheckBox();
  JLabel jLabel30 = new JLabel();
  JLabel jLabel124 = new JLabel();
  JCheckBox considero7 = new JCheckBox();
  JCheckBox considero5 = new JCheckBox();
  JLabel jLabel122 = new JLabel();
  JCheckBox considero4 = new JCheckBox();
  JCheckBox considero11 = new JCheckBox();
  JLabel jLabel36 = new JLabel();
  JLabel jLabel121 = new JLabel();
  JLabel jLabel31 = new JLabel();
  JCheckBox considero1 = new JCheckBox();
  JMenu jMenu3 = new JMenu();
  JMenuItem jMenuItemSetup1 = new JMenuItem();
  JMenuItem jMenuItemSetup2 = new JMenuItem();

  String fileName = "dati_al.dat";
  TReadSerie readSerie = new TReadSerie();

  //LA FUNZIONE "CARICASETTAGGI" LEGGE DA FILE IL NUMERO DI COEFFICIENTI DI FOURIER EFFETTIVAMENTE DA UTILIZZARE
  int numCoefficients = 3; //COEFFICIENTI DI FOURIER UTILIZZATI (VALORE INDICATIVO)
  int alpha = 3; //VALORE DI ALPHA UTILIZZATO NEI TVTREE PER LE DIMENSIONI ATTIVE
  int numfiglitv = 3; //NUMERO DEI FIGLI DEFINITI PER I NODI DELLA STRUTTURA TVTREE

  int numSegnali = 11;
  int numPazienti = 10;
  int[] pazienti = {
      2, 3, 4, 5, 8, 9, 10, 12, 29, 33};
  TGlobalLoader loader;
  TemplateLoader templateLoader;
  int[] segnali = {
      13, 22, 9, 56, 41, 25, 23, 24, 2, 64, 65};
  String[] nomesegnali = {
      "QB", "PA", "PV", "PT", "QF", "FC", "PS", "PD", "CD", "HB", "VE"};
  TKDTree[] kdTree = new TKDTree[segnali.length];
  TTVTree[] tvTree = new TTVTree[segnali.length];
  TRange[] range = new TRange[segnali.length];
  double[] weights = {
      4, 2, 2, 2, 4, 3, 3, 3, 1, 3, 3, 4};
  double[] maxvals = {
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
  int[] soglie = {
      numCoefficients, numCoefficients, numCoefficients, numCoefficients,
      numCoefficients, numCoefficients, numCoefficients, numCoefficients,
      numCoefficients, numCoefficients, numCoefficients};
  boolean kdindexLoaded = false;
  boolean tvindexLoaded = false;
  TShowSerie[] visSerie1 = new TShowSerie[segnali.length];
  TShowSerie[] visSerie2 = new TShowSerie[segnali.length];
  int maxdialisi[]; /*VETTORE DEI PAZIENTI CON LE MASSIMEDIALISI*/
  boolean[] daconsiderare = new boolean[this.numSegnali];
  DBConnection connection = new DBConnection();
  DBConnection connectionT = new DBConnection();
  String url = "localhost";
  String userName = "root";
  String password = "print a$";
  String dbase = "esrd3";
  String driver = "com.mysql.jdbc.Driver";
  String driverName = "MySQL";
  String connectionString = "jdbc:mysql://";
  JMenuItem jMenuItem16 = new JMenuItem();
  JLabel jLabel15 = new JLabel();
  JLabel jLabel17 = new JLabel();
  JLabel jLabel18 = new JLabel();
  JTextField MacroSX = new JTextField();
  JTextField MacroDX = new JTextField();
  List list1 = new List();
  JButton visMacroCaso1 = new JButton();
  JToggleButton visMacroCaso2 = new JToggleButton();
  JMenuItem jMenuItem17 = new JMenuItem();
  JMenuItem jMenuItem18 = new JMenuItem();
  JMenuItem jMenuItem19 = new JMenuItem();
  JMenuItem jMenuItem20 = new JMenuItem();

  public RHENE() throws HeadlessException {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws HeadlessException {
    RHENE RHENE1 = new RHENE();
    int i;

    RHENE1.setBounds(100, 10, 775, 605);
    try {
      RHENE1.setVisible(true);
      System.out.println(
          "  -------> CARICAMENTO DEI SETTAGGI IN CORSO <-------  ");
      RHENE1.caricaSettaggi();
      System.out.println(
          "----------   SETUP TERMINATO: PRONTO PER L'USO   ----------");
    }
    catch (IOException e) {
    }
    catch (java.sql.SQLException sqlexc) {
      sqlexc.printStackTrace();
    }
    finally {

    }
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)), " Time Serie o Caso Query : ");
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)),
                                     " Risultati (paziente - segnale - dialisi - distanza) : ");
    border1 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)),
                                     "Query sulla Singola Serie (far partire dal menu) ");
    border2 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)), " Coefficienti di Fourier : ");
    border3 = BorderFactory.createEmptyBorder();
    titledBorder5 = new TitledBorder(border3, "Tempi di Esecuzione : ");
    border4 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder6 = new TitledBorder(border4, " Tempi di Esecuzione : ");
    border5 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder7 = new TitledBorder(border5, " Operazioni in corso : ");
    border6 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    border7 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder8 = new TitledBorder(border7, " Query sul Caso : ");
    border8 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder9 = new TitledBorder(border8, " Tipo di distanza : ");
    border9 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(165, 163, 151));
    titledBorder10 = new TitledBorder(border9, "Indicatore di avanzamento: ");
    this.setLocale(java.util.Locale.getDefault());
    this.setJMenuBar(jMenuBar1);
    this.setTitle("RHENE: C.B. Retrieval for Hemodialisys Cases");
    this.addWindowListener(new RHENE_this_windowAdapter(this));
    this.getContentPane().setLayout(null);
    jMenu1.setText("File");
    jMenuItem2.addActionListener(new RHENE_jMenuItem2_actionAdapter(this));
    jMenuItem1.setText("Fine Lavoro");
    jMenuItem1.addActionListener(new RHENE_jMenuItem1_actionAdapter(this));
    jMenu2.setEnabled(true);
    jMenu2.setText("Query");
    jMenuItem3.setText("Range Query sulla TS selezionata");
    jMenuItem3.addActionListener(new RHENE_jMenuItem3_actionAdapter(this));
    jMenuItem4.setText("KNN sul Caso selezionato");
    jMenuItem4.addActionListener(new RHENE_jMenuItem4_actionAdapter(this));
    jPanel1.setBorder(titledBorder1);
    jPanel1.setBounds(new Rectangle(3, 3, 443, 192));
    jPanel1.setLayout(null);
    jPanel2.setForeground(Color.black);
    jPanel2.setBorder(titledBorder2);
    jPanel2.setBounds(new Rectangle(447, 3, 320, 541));
    jPanel2.setLayout(null);
    list2.setBounds(new Rectangle(9, 19, 297, 223));
    jMenuBar1.setDebugGraphicsOptions(0);
    jMenuBar1.setDoubleBuffered(true);
    jPanel3.setBorder(titledBorder3);
    jPanel3.setBounds(new Rectangle(3, 195, 443, 95));
    jPanel3.setLayout(null);
    jLabel1.setText("Distanza Epsilon (default = 50) : ");
    jLabel1.setBounds(new Rectangle(7, 23, 308, 15));
    Epsilon1.setText("1");
    Epsilon1.setHorizontalAlignment(SwingConstants.RIGHT);
    Epsilon1.setBounds(new Rectangle(360, 19, 58, 21));
    jPanel4.setBorder(titledBorder6);
    jPanel4.setBounds(new Rectangle(4, 461, 442, 83));
    jPanel4.setLayout(null);
    jLabel2.setText("Esecuzione Query : ");
    jLabel2.setBounds(new Rectangle(12, 20, 153, 15));
    Tempo1.setEnabled(false);
    Tempo1.setDisabledTextColor(Color.black);
    Tempo1.setEditable(true);
    Tempo1.setHorizontalAlignment(SwingConstants.CENTER);
    Tempo1.setBounds(new Rectangle(173, 18, 260, 21));
    KNN.setBounds(new Rectangle(360, 43, 58, 21));
    KNN.setText("0");
    KNN.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setBounds(new Rectangle(6, 46, 320, 15));
    jLabel4.setText("Valore KNN (Per Kd-Tree: 0 = Range Query) : ");
    jLabel5.setBounds(new Rectangle(9, 48, 180, 15));
    jLabel5.setText("Fase di Post Processing : ");
    Tempo2.setBounds(new Rectangle(173, 44, 261, 21));
    Tempo2.setEnabled(false);
    Tempo2.setDisabledTextColor(Color.black);
    Tempo2.setHorizontalAlignment(SwingConstants.CENTER);
    jMenuItem5.setText("Carica Indici in Memoria Kd-Tree");
    jMenuItem5.addActionListener(new RHENE_jMenuItem5_actionAdapter(this));
    Paziente1.setBounds(new Rectangle(80, 30, 114, 21));
    Paziente1.addActionListener(new RHENE_Paziente1_actionAdapter(this));
    jLabel6.setText("Paziente : ");
    jLabel6.setBounds(new Rectangle(12, 31, 80, 15));
    jLabel7.setBounds(new Rectangle(12, 58, 80, 15));
    jLabel7.setText("Dialisi : ");
    Dialisi1.setBounds(new Rectangle(80, 57, 114, 21));
    jLabel8.setBounds(new Rectangle(12, 86, 80, 15));
    jLabel8.setText("Segnale : ");
    Segnale1.setBounds(new Rectangle(80, 85, 114, 21));
    Visualizza1.setBounds(new Rectangle(62, 117, 129, 27));
    Visualizza1.setText("Visualizza Serie");
    Visualizza1.addActionListener(new RHENE_Visualizza1_actionAdapter(this));
    Visualizza2.setBounds(new Rectangle(18, 248, 125, 27));
    Visualizza2.setText("Visualizza Serie");
    Visualizza2.addActionListener(new RHENE_Visualizza2_actionAdapter(this));
    normalizzata1.setSelected(true);
    normalizzata1.setText("Distanze normalizzate");
    normalizzata1.setBounds(new Rectangle(7, 68, 218, 23));
    normalizzata1.addActionListener(new RHENE_normalizzata1_actionAdapter(this));
    normalizzata1.addItemListener(new RHENE_normalizzata1_itemAdapter(this));
    jPanel6.setLayout(null);
    jPanel6.setBounds(new Rectangle(5, 292, 441, 168));
    jPanel6.setBorder(titledBorder8);
    jLabel9.setText("Valore KNN (default = 10) : ");
    jLabel9.setBounds(new Rectangle(11, 27, 178, 15));
    KNN1.setHorizontalAlignment(SwingConstants.RIGHT);
    KNN1.setText("10");
    KNN1.setBounds(new Rectangle(210, 24, 50, 21));
    ignoraClassificazione.setText("Ignora la classificazione");
    ignoraClassificazione.setBounds(new Rectangle(9, 96, 205, 23));
    interpolazione.setSelected(true);
    interpolazione.setText("Utilizzo dell'interpolazione");
    interpolazione.setBounds(new Rectangle(220, 96, 205, 23));
    visCaso1.setBounds(new Rectangle(208, 118, 128, 26));
    visCaso1.setText("Visualizza Caso");
    visCaso1.addActionListener(new RHENE_visCaso1_actionAdapter(this));
    visCaso2.setBounds(new Rectangle(172, 248, 125, 27));
    visCaso2.setText("Visualizza Caso");
    visCaso2.setVerticalTextPosition(SwingConstants.CENTER);
    visCaso2.addActionListener(new RHENE_visCaso2_actionAdapter(this));
    chiudiCaso.setBounds(new Rectangle(208, 153, 129, 26));
    chiudiCaso.setText("Chiudi Caso");
    chiudiCaso.setVerticalTextPosition(SwingConstants.CENTER);
    chiudiCaso.addActionListener(new RHENE_chiudiCaso_actionAdapter(this));
    chiudiCaso2.setBounds(new Rectangle(172, 280, 125, 25));
    chiudiCaso2.setText("Chiudi Caso");
    chiudiCaso2.addActionListener(new RHENE_chiudiCaso2_actionAdapter(this));
    jMenuItem6.setText("Carica Indici in Memoria TV-Tree");
    jMenuItem6.addActionListener(new RHENE_jMenuItem6_actionAdapter(this));
    jMenuItem7.setText("Reindicizza Archivio TV-Tree");
    jMenuItem7.addActionListener(new RHENE_jMenuItem7_actionAdapter(this));
    jMenuItem8.setText("Range Query sulla TS selezionata");
    jMenuItem8.addActionListener(new RHENE_jMenuItem8_actionAdapter(this));
    jMenuItem9.setActionCommand("Range Query sul Caso selezionato");
    jMenuItem9.setText("KNN sul Caso selezionato");
    jMenuItem9.addActionListener(new RHENE_jMenuItem9_actionAdapter(this));
    jMenuItem11.setBackground(Color.lightGray);
    jMenuItem11.setEnabled(false);
    jMenuItem11.setBorder(BorderFactory.createEtchedBorder());
    jMenuItem11.setActionCommand("Kd-Tree");
    jMenuItem11.setHorizontalAlignment(SwingConstants.CENTER);
    jMenuItem11.setHorizontalTextPosition(SwingConstants.CENTER);
    jMenuItem11.setText("Kd-Tree");
    jMenuItem10.setBackground(Color.lightGray);
    jMenuItem10.setEnabled(false);
    jMenuItem10.setBorder(BorderFactory.createEtchedBorder());
    jMenuItem10.setHorizontalAlignment(SwingConstants.CENTER);
    jMenuItem10.setHorizontalTextPosition(SwingConstants.CENTER);
    jMenuItem10.setText("TV-tree");
    jMenuItem13.setBackground(Color.lightGray);
    jMenuItem13.setEnabled(false);
    jMenuItem13.setBorder(BorderFactory.createEtchedBorder());
    jMenuItem13.setHorizontalAlignment(SwingConstants.CENTER);
    jMenuItem13.setHorizontalTextPosition(SwingConstants.CENTER);
    jMenuItem13.setText("TV-tree");
    jMenuItem12.setBackground(Color.lightGray);
    jMenuItem12.setEnabled(false);
    jMenuItem12.setForeground(Color.black);
    jMenuItem12.setHorizontalAlignment(SwingConstants.CENTER);
    jMenuItem12.setHorizontalTextPosition(SwingConstants.CENTER);
    jMenuItem12.setText("Kd-Tree");
    jMenuItem14.setText("KNN Nativa sulla TS selezionata");
    jMenuItem14.addActionListener(new RHENE_jMenuItem14_actionAdapter(this));
    jMenuItem15.setEnabled(false);
    jMenuItem15.setText("KNN Query sul Caso selezionato");
    jMenuItem15.addActionListener(new RHENE_jMenuItem15_actionAdapter(this));
    titledBorder8.setTitle(" Query sul Caso (far partire da menu) ");
    normalizzata2.setBounds(new Rectangle(9, 76, 207, 23));
    normalizzata2.setText("Distanze normalizzate");
    normalizzata2.setSelected(true);
    Epsilon2.setBounds(new Rectangle(210, 52, 49, 21));
    Epsilon2.setHorizontalAlignment(SwingConstants.RIGHT);
    Epsilon2.setText("1");
    jLabel3.setBounds(new Rectangle(12, 55, 180, 15));
    jLabel3.setText("Distanza Epsilon (default = 50) : ");
    jLabel10.setText("Macrocasi Recuperati :");
    jLabel10.setBounds(new Rectangle(7, 306, 146, 14));
    jLabel11.setText("TV-Tree - Configurazione");
    jLabel11.setBounds(new Rectangle(269, 8, 140, 24));
    jLabel12.setBounds(new Rectangle(226, 91, 167, 24));
    jLabel12.setText("Coefficienti di Fourier scelti:");
    coeffourier.setText("3");
    coeffourier.setHorizontalAlignment(SwingConstants.CENTER);
    coeffourier.setBounds(new Rectangle(394, 92, 35, 26));
    coeffourier.addFocusListener(new RHENE_coeffourier_focusAdapter(this));
    jLabel13.setText("Dimensioni attive:");
    jLabel13.setBounds(new Rectangle(225, 35, 163, 16));
    jLabel14.setBounds(new Rectangle(226, 65, 164, 16));
    jLabel14.setText("Figli per nodo:");
    dimensioniattive.setBounds(new Rectangle(394, 33, 35, 26));
    dimensioniattive.addFocusListener(new RHENE_dimensioniattive_focusAdapter(this));
    dimensioniattive.setHorizontalAlignment(SwingConstants.CENTER);
    dimensioniattive.setText("8");
    figlipernodo.setBounds(new Rectangle(394, 62, 35, 26));
    figlipernodo.addFocusListener(new RHENE_figlipernodo_focusAdapter(this));
    figlipernodo.setHorizontalAlignment(SwingConstants.CENTER);
    figlipernodo.setText("10");
    considero8.setBounds(new Rectangle(277, 130, 20, 22));
    considero8.setText("jCheckBox1");
    jLabel35.setText("PT");
    jLabel35.setBounds(new Rectangle(193, 116, 28, 15));
    jLabel125.setText("PS");
    jLabel125.setBounds(new Rectangle(260, 116, 28, 15));
    considero6.setBounds(new Rectangle(231, 130, 20, 22));
    considero6.setText("jCheckBox1");
    jLabel37.setText("PA");
    jLabel37.setBounds(new Rectangle(149, 116, 28, 15));
    considero2.setBounds(new Rectangle(145, 130, 20, 22));
    considero2.setText("jCheckBox1");
    considero9.setBounds(new Rectangle(299, 130, 20, 22));
    considero9.setText("jCheckBox1");
    jLabel34.setBounds(new Rectangle(236, 116, 28, 15));
    jLabel34.setText("FC");
    considero10.setBounds(new Rectangle(320, 130, 20, 22));
    considero10.setText("jCheckBox1");
    jLabel33.setBounds(new Rectangle(12, 115, 96, 15));
    jLabel33.setText("Codice Serie : ");
    jLabel123.setBounds(new Rectangle(345, 116, 28, 15));
    jLabel123.setText("VE");
    jLabel32.setBounds(new Rectangle(11, 133, 100, 15));
    jLabel32.setText("Somiglianza : ");
    considero3.setBounds(new Rectangle(166, 130, 20, 22));
    considero3.setText("jCheckBox1");
    jLabel30.setBounds(new Rectangle(214, 116, 28, 15));
    jLabel30.setText("QF");
    jLabel124.setBounds(new Rectangle(282, 116, 28, 15));
    jLabel124.setText("PD");
    considero7.setBounds(new Rectangle(253, 130, 20, 22));
    considero7.setText("jCheckBox1");
    considero5.setBounds(new Rectangle(209, 130, 20, 22));
    considero5.setText("jCheckBox1");
    jLabel122.setBounds(new Rectangle(325, 116, 28, 15));
    jLabel122.setText("HB");
    considero4.setBounds(new Rectangle(187, 130, 20, 22));
    considero4.setText("jCheckBox1");
    considero11.setBounds(new Rectangle(341, 130, 20, 22));
    considero11.setText("jCheckBox1");
    jLabel36.setBounds(new Rectangle(171, 116, 28, 15));
    jLabel36.setText("PV");
    jLabel121.setBounds(new Rectangle(304, 116, 28, 15));
    jLabel121.setText("CD");
    jLabel31.setBounds(new Rectangle(127, 116, 28, 15));
    jLabel31.setText("QB");
    considero1.setText("jCheckBox1");
    considero1.setBounds(new Rectangle(124, 130, 20, 22));
    jMenu3.setText("Setup");
    jMenuItemSetup1.setActionCommand("Interfaccia settaggio parametri");
    jMenuItemSetup1.setText("Modifica dei parametri");
    jMenuItemSetup1.addActionListener(new RHENE_jMenuItemSetup1_actionAdapter(this));
    jMenuItemSetup2.setText("Rilettura dei parametri");
    jMenuItemSetup2.addActionListener(new RHENE_jMenuItemSetup2_actionAdapter(this));
    jMenuItem16.setText("Classificazione");
    jMenuItem16.addActionListener(new RHENE_jMenuItem16_actionAdapter(this));
    jLabel15.setText("N.Casi SX :");
    jLabel15.setBounds(new Rectangle(271, 37, 68, 16));
    jLabel17.setText("N.Casi DX :");
    jLabel17.setBounds(new Rectangle(271, 64, 66, 18));
    jLabel18.setText("Macrocasi:");
    jLabel18.setBounds(new Rectangle(313, 18, 91, 14));
    MacroSX.setText("6");
    MacroSX.setBounds(new Rectangle(370, 35, 55, 21));
    MacroDX.setText("0");
    MacroDX.setBounds(new Rectangle(370, 61, 55, 21));
    list1.setBounds(new Rectangle(9, 322, 299, 212));
    visMacroCaso1.setBounds(new Rectangle(60, 153, 132, 26));
    visMacroCaso1.setText("Vis. Macrocaso");
    visMacroCaso1.addActionListener(new RHENE_visMacroCaso1_actionAdapter(this));
    visMacroCaso2.setText("Vis. MacroCaso");
    visMacroCaso2.setBounds(new Rectangle(18, 279, 126, 26));
    visMacroCaso2.addActionListener(new RHENE_visMacroCaso2_actionAdapter(this));
    jMenuItem17.setEnabled(false);
    jMenuItem17.setText("Macrocasi");
    jMenuItem18.setText("Query sui Macrocasi individuati");
    jMenuItem18.addActionListener(new RHENE_jMenuItem18_actionAdapter(this));
    interpolazione.setBounds(new Rectangle(241, 92, 188, 22));
    jMenuItem19.setActionCommand("Recupero primo contesto");
    jMenuItem19.setText("Recupero primo contesto");
    jMenuItem19.addActionListener(new RHENE_jMenuItem19_actionAdapter(this));
    jMenuItem20.setText("Esportazione jColibri");
    jMenuItem20.addActionListener(new RHENE_jMenuItem20_actionAdapter(this));
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenuBar1.add(jMenu3);
    jMenu1.add(jMenuItem12);
    jMenu1.add(jMenuItem5);
    jMenu1.add(jMenuItem2);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem13);
    jMenu1.add(jMenuItem6);
    jMenu1.add(jMenuItem7);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem1);
    jMenu2.add(jMenuItem11);
    jMenu2.add(jMenuItem3);
    jMenu2.add(jMenuItem4);
    jMenu2.add(jMenuItem10);
    jMenu2.add(jMenuItem8);
    jMenu2.add(jMenuItem9);
    jMenu2.add(jMenuItem17);
    jMenu2.add(jMenuItem18);
    jMenu2.addSeparator();
    jMenu2.add(jMenuItem14);
    jMenu2.add(jMenuItem15);
    jMenu2.addSeparator();
    jMenu2.add(jMenuItem16);
    jMenu2.addSeparator();
    jMenu2.add(jMenuItem19);
    jPanel2.add(list2, null);
    jPanel2.add(Visualizza2, null);
    jPanel2.add(visCaso2, null);
    jPanel2.add(chiudiCaso2, null);
    jPanel2.add(jLabel10, null);
    jPanel2.add(list1);
    jPanel2.add(visMacroCaso2);
    jPanel3.add(jLabel4, null);
    jPanel3.add(jLabel1, null);
    jPanel3.add(normalizzata1, null);
    jPanel3.add(KNN, null);
    jPanel3.add(Epsilon1, null);
    this.getContentPane().add(jPanel6, null);
    jPanel1.add(jLabel6, null);
    jPanel1.add(jLabel7, null);
    jPanel1.add(jLabel8, null);
    jPanel1.add(Paziente1, null);
    jPanel1.add(Dialisi1, null);
    jPanel1.add(Segnale1, null);
    jPanel1.add(jLabel11, null);
    jPanel1.add(coeffourier, null);
    jPanel1.add(jLabel13, null);
    jPanel1.add(jLabel14, null);
    jPanel1.add(figlipernodo, null);
    jPanel1.add(dimensioniattive, null);
    jPanel1.add(jLabel12, null);
    jPanel1.add(chiudiCaso, null);
    jPanel1.add(visCaso1, null);
    jPanel1.add(Visualizza1, null);
    jPanel1.add(visMacroCaso1);
    this.getContentPane().add(jPanel3, null);
    this.getContentPane().add(jPanel2, null);
    jPanel4.add(jLabel2, null);
    jPanel4.add(Tempo1, null);
    jPanel4.add(jLabel5, null);
    jPanel4.add(Tempo2, null);
    this.getContentPane().add(jPanel4, null);
    this.getContentPane().add(jPanel1, null);
    jPanel6.add(jLabel3, null);
    jPanel6.add(jLabel9, null);
    jPanel6.add(considero2, null);
    jPanel6.add(considero3, null);
    jPanel6.add(considero4, null);
    jPanel6.add(considero5, null);
    jPanel6.add(considero6, null);
    jPanel6.add(considero7, null);
    jPanel6.add(considero8, null);
    jPanel6.add(considero9, null);
    jPanel6.add(considero10, null);
    jPanel6.add(considero11, null);
    jPanel6.add(considero1, null);
    jPanel6.add(jLabel32, null);
    jPanel6.add(KNN1, null);
    jPanel6.add(Epsilon2, null);
    jPanel6.add(KNN1, null);
    this.getContentPane().add(jPanel3, null);
    jMenu3.add(jMenuItemSetup1);
    jMenu3.add(jMenuItemSetup2);
    jMenu3.addSeparator();
    jMenu3.add(jMenuItem20);
    jPanel6.add(jLabel33, null);
    jPanel6.add(jLabel35, null);
    jPanel6.add(jLabel37, null);
    jPanel6.add(jLabel36, null);
    jPanel6.add(jLabel124, null);
    jPanel6.add(jLabel34, null);
    jPanel6.add(jLabel121, null);
    jPanel6.add(jLabel30, null);
    jPanel6.add(jLabel31, null);
    jPanel6.add(jLabel122, null);
    jPanel6.add(jLabel123, null);
    jPanel6.add(jLabel125, null);
    jPanel6.add(jLabel18);
    jPanel6.add(MacroSX);
    jPanel6.add(MacroDX);
    jPanel6.add(jLabel17);
    jPanel6.add(jLabel15);
    jPanel6.add(normalizzata2, null);
    jPanel6.add(ignoraClassificazione, null);
    jPanel6.add(interpolazione);
  }

  int getPaziente(String S) {
    try {
      String Temp = "";
      int I;

      I = 0;
      while (S.charAt(I) != ' ') {
        Temp = Temp + S.charAt(I);
        I++;
      }
      return Integer.parseInt(Temp);
    }
    catch (Exception e) {
      return -1;
    }
  }

  int getSerie(String S) {
    try {
      String Temp = "";
      int I;

      I = 0;
      while (S.charAt(I) != ' ') {
        I++;
      }
      I++;
      while ( (I < S.length()) && S.charAt(I) != ' ') {
        Temp = Temp + S.charAt(I);
        I++;
      }
      return Integer.parseInt(Temp);
    }
    catch (Exception e) {
      return -1;
    }
  }

  int getDialisi(String S) {
    try {
      String Temp = "";
      int I;

      I = 0;
      while (S.charAt(I) != ' ') {
        I++;
      }
      I++;
      while (S.charAt(I) != ' ') {
        I++;
      }
      I++;
      while ( (I < S.length()) && S.charAt(I) != ' ') {
        Temp = Temp + S.charAt(I);
        I++;
      }
      return Integer.parseInt(Temp);
    }
    catch (Exception e) {
      return -1;
    }
  }

  int getPrec() {
    try {
      return Integer.parseInt( (String) MacroSX.getText());
    }
    catch (NumberFormatException ex1) {
      return 0;
    }
  }

  int getSucc() {
    try {
      return Integer.parseInt( (String) MacroDX.getText());
    }
    catch (NumberFormatException ex1) {
      return 0;
    }
  }

  public void caricaSettaggi() throws IOException, java.sql.SQLException {
    int i;

    RandomAccessFile file = new RandomAccessFile("DBConfig.dat", "rw");
    userName = file.readUTF();
    password = file.readUTF();
    url = file.readUTF();
    dbase = file.readUTF();
    driverName = file.readUTF();
    driver = file.readUTF();
    connectionString = file.readUTF();
    file.close();

    connection.connectToDB(driver, url, userName, password, dbase,
                           connectionString);
    loader = new TGlobalLoader(pazienti, segnali, connection);
    connectionT.connectToDB(driver, url, userName, password, "astrazioni",
                            connectionString);
    templateLoader = new TemplateLoader(segnali, connectionT);

    file = new RandomAccessFile("coeffsoglia.dat", "rw");
    numCoefficients = file.readInt();
    this.coeffourier.setText(String.valueOf(numCoefficients));
    for (i = 0; i < numSegnali; i++) {
      soglie[i] = file.readInt();
    }
    file.close();

    file = new RandomAccessFile("numAlpha.dat", "rw");
    this.alpha = file.readInt();
    file.close();

    file = new RandomAccessFile("numFigliTV.dat", "rw");
    this.numfiglitv = file.readInt();
    file.close();

    file = new RandomAccessFile("pesi.dat", "rw");
    for (i = 0; i < numSegnali; i++) {
      weights[i] = file.readInt();
    }
    file.close();

    connection.RunQuery("select count(*) from r_patient");
    connection.nextRecord();
    numPazienti = connection.getRowValueInt("count");
    pazienti = new int[numPazienti];

    connection.RunQuery("SELECT patientid FROM r_patient ORDER BY patientid");
    i = 0;
    while (connection.nextRecord()) {
      pazienti[i] = connection.getRowValueInt("patientid");
      i++;
    }

    /* file = new RandomAccessFile ("pazienti.dat", "rw");
         numPazienti = file.readInt();
         pazienti = new int[numPazienti];
         for (i= 0; i < numPazienti; i++) {
      pazienti[i] = file.readInt();
         }
         file.close(); */

    this.maxdialisi = new int[pazienti[numPazienti - 1] + 1];
    try {
      file = new RandomAccessFile("maxDialisi.dat", "rw");
      for (i = 0; i < this.maxdialisi.length; i++)
        this.maxdialisi[i] = file.readInt();
      file.close();
    }
    catch (IOException exc) {
    }
    System.out.println("Massimo numero di dialisi calcolate per paziente:");
    for (i = 0; i < this.maxdialisi.length; i++) {
      System.out.println("Paziente [" + (i + 1) + "]: " + this.maxdialisi[i]);
    }

    file = new RandomAccessFile("maxval.dat", "rw");
    for (i = 0; i < numSegnali; i++) {
      maxvals[i] = file.readDouble();
    }
    file.close();

    this.coeffourier.setText(String.valueOf(this.numCoefficients));
    this.dimensioniattive.setText(String.valueOf(this.alpha));
    this.figlipernodo.setText(String.valueOf(this.numfiglitv));

    /*CARICAMENTO DEI PAZIENTI PRESENTI IN TABELLA*/
    for (i = 0; i < pazienti.length; i++) {
      Paziente1.addItem(String.valueOf(pazienti[i]));
    }
    /*CARICAMENTO DEI SEGNALI PRESENTI IN TABELLA*/
    for (i = 0; i < segnali.length; i++) {
      Segnale1.addItem(String.valueOf(segnali[i]));
    }

    System.out.println("FOURIER: " + this.numCoefficients);
    System.out.println("ALPHA: " + this.alpha);
    System.out.println("FIGLI: " + this.numfiglitv);

  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

  int getPaziente() {
    try {
      return Integer.parseInt( (String) Paziente1.getItemAt(Paziente1.
          getSelectedIndex()));
    }
    catch (NumberFormatException e) {
      return -1;
    }
  }

  int getSerie() {
    try {
      return Integer.parseInt( (String) Segnale1.getItemAt(Segnale1.
          getSelectedIndex()));
    }
    catch (NumberFormatException e) {
      return -1;
    }
  }

  int getDialisi() {
    try {
      return Integer.parseInt( (String) Dialisi1.getItemAt(Dialisi1.
          getSelectedIndex()));
    }
    catch (NumberFormatException e) {
      return -1;
    }
  }

  void Visualizza1_actionPerformed(ActionEvent e) {
    int paziente, segnale, dialisi; /*PAZIENTE SEGNALE E DIALISI*/
    TTimeSerie SerieLetta = new TTimeSerie();
    TShowSerie showSerie;
    double T1, T2;

    paziente = getPaziente();
    segnale = getSerie();
    dialisi = getDialisi();

    if ( (paziente != -1) && (segnale != -1) && (dialisi != -1)) {

      T1 = java.lang.System.currentTimeMillis();
      loader.loadSingleSerie(paziente, dialisi, segnale, SerieLetta,
                             interpolazione.isSelected());
      T2 = java.lang.System.currentTimeMillis();
      Tempo1.setText(String.valueOf(T2 - T1));

      SerieLetta.setName("Paziente " + String.valueOf(paziente) + " - Segnale " +
                         String.valueOf(segnale)
                         + " - N. Dialisi " + String.valueOf(dialisi));
      showSerie = new TShowSerie(SerieLetta, 0, 0, 400, 200);
      showSerie.setVisible(true);
    }
  }

  void Visualizza2_actionPerformed(ActionEvent e) {
    String riga;
    int paziente, segnale, dialisi;
    TTimeSerie SerieLetta = new TTimeSerie();
    TShowSerie showSerie;

    riga = list2.getSelectedItem();
    if (riga != null) {
      paziente = getPaziente(riga);
      segnale = getSerie(riga);
      dialisi = getDialisi(riga);
      if (segnale != 0) {
        loader.loadSingleSerie(paziente, dialisi, segnale, SerieLetta,
                               interpolazione.isSelected());
        SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                           " - Segnale " + String.valueOf(segnale)
                           + " - N. Dialisi " + String.valueOf(dialisi));

        showSerie = new TShowSerie(SerieLetta, 0, 0, 400, 200);
        showSerie.setVisible(true);
      }
    }
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {
    TTimeSerie Caricata = new TTimeSerie();
    TTimeSerie Query = new TTimeSerie();
    TTimeSerie Originale = new TTimeSerie();
    double dist1, dist2;
    double T1, T2;
    int paziente, serie, dialisi, maxDialisi;
    int[] mindialisi;
    TFourier fourier = new TFourier();
    String numLetto, Riga;
    int I;
    TIndex indice = new TIndex();

    list2.removeAll();

    for (I = 0; I < kdTree.length; I++) kdTree[I] = new TKDTree();
    for (I = 0; I < range.length; I++) range[I] = new TRange(segnali[I]);

    /* DA ELIMINARE IN QUANTO INDICI
        indiciCaricamento= new loaderIndex[pazienti.length];
     */
    fourier.setNumCoefficients(numCoefficients);

    /* DA ELIMINARE IN QUANTO INDICI
        for (I= 0; I < pazienti.length; I++) {
          indiciCaricamento[I] = new loaderIndex (loader.getMaxDialisi(pazienti[I]), numSegnali, pazienti[I]);
        }*/

    T1 = java.lang.System.currentTimeMillis();

    this.maxdialisi = new int[pazienti[numPazienti - 1] + 1];
    //    loader.getMaxDialisi2 (this.maxdialisi);

    for (int i = 0; i < this.pazienti.length; i++)
      this.maxdialisi[pazienti[i]] = loader.getMaxDialisi(pazienti[i]);

    mindialisi = new int[pazienti[numPazienti - 1] + 1];
    for (int i = 0; i < this.pazienti.length; i++)
      mindialisi[pazienti[i]] = loader.getMinDialisi(pazienti[i]);

    for (serie = 0; serie < segnali.length; serie++) {
      System.out.println("Indicizzazione Segnale " + segnali[serie]);
      for (I = 0; I < pazienti.length; I++) {
        paziente = pazienti[I];
        /*maxDialisi= loader.getMaxDialisi(paziente);*/
        maxDialisi = this.maxdialisi[paziente];
        for (dialisi = mindialisi[paziente]; dialisi <= maxDialisi; dialisi++) {

          /* DA ELIMINARE IN QUANTO INDICI
                       posizione= loader.loadBlindSingleSerie(paziente, dialisi, segnali[serie], Caricata);
           indiciCaricamento[I].setIndex(dialisi, serie, posizione);*/

          loader.loadSingleSerie(paziente, dialisi, segnali[serie], Caricata,
                                 interpolazione.isSelected());
//            System.out.println("Paziente - Dialisi : " + paziente + " - " + dialisi);

          if (Caricata.getNumValues() >= soglie[serie]) {

            range[serie].checkRange(Caricata);
            kdTree[serie].checkMaxLength(Caricata.getNumValues());

            fourier.setOriginalSerie(Caricata);
            fourier.calculate();
            Caricata = fourier.getTransformedSerie();

            kdTree[serie].addKey(Caricata, paziente, dialisi, segnali[serie], true);
          }
        }
      }
    }

    for (I = 0; I < segnali.length; I++) {
      indice.saveIndex(kdTree[I], segnali[I], numCoefficients);
      range[I].saveRange();
    }
    try {
      RandomAccessFile file = new RandomAccessFile("maxDialisi.dat", "rw");
      for (int i = 0; i < this.maxdialisi.length; i++)
        file.writeInt(this.maxdialisi[i]);
      file.close();
    }
    catch (IOException exc) {
    }

    /*CARICAMENTO DEI PAZIENTI PRESENTI IN TABELLA*/
    Paziente1.removeAllItems();
    for (int i = 0; i < pazienti.length; i++) {
      Paziente1.addItem(String.valueOf(pazienti[i]));
    }

    T2 = java.lang.System.currentTimeMillis();
    System.out.println("Tempo totale Impiegato: " + (T2 - T1) / 1000 +
                       "secondi");

    /* DA ELIMINARE IN QUANTO INDICI
          for (I= 0; I < pazienti.length; I++) {
            indiciCaricamento[I].saveIndex();
          }
          loader.setIndex(indiciCaricamento);
     */
    kdindexLoaded = true;
  }

  int searchSignal(int Signal) {
    int Result;
    boolean Trovato;

    Result = -1;
    Trovato = false;
    while (Trovato == false) {
      Result++;
      if (segnali[Result] == Signal)
        Trovato = true;
    }
    return Result;
  }

  int searchPaziente(int Paziente) {
    int Result;
    boolean Trovato;

    Result = -1;
    Trovato = false;
    while (Trovato == false) {
      Result++;
      if (pazienti[Result] == Paziente)
        Trovato = true;
    }
    return Result;
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
    TIndex indice = new TIndex();
    int I;

    for (I = 0; I < kdTree.length; I++) kdTree[I] = new TKDTree();
    for (I = 0; I < range.length; I++) range[I] = new TRange(segnali[I]);

    for (I = 0; I < segnali.length; I++) {
      indice.loadIndex(kdTree[I], segnali[I], numCoefficients);
      range[I].loadRange();
    }

    kdindexLoaded = true;
  }

  //RANGE QUERY CON KDTREE SULLA TIMESERIE SELEZIONATA - CON E SENZA NORMALIZZAZIONE
  void jMenuItem3_actionPerformed(ActionEvent e) {
    double Epsilon;
    int paziente, serie, dialisi, Key, K;
    int pazienteR, serieR, dialisiR;
    String riga;
    TTimeSerie Query = new TTimeSerie();
    TTimeSerie transformed;
    TFourier fourier;
    TPiecewise piecewise;
    TLinkedList Risultati;
    TResultNode Result;
    TPostProcessing postProcessing;
    double T1, T2, T3;
    int indiceSegnale;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;
    String riga2;

    if (kdindexLoaded == true) { //SE GLI INDICI DEI KDTREE SONO STATI CARICATI IN MEMORIA

      //ACQUISISCI I DATI DEL PAZIENTE, SEGNALE E DIALISI DALL'INTERFACCIA GRAFICA
      try {
        paziente = getPaziente();
        serie = getSerie();
        dialisi = getDialisi();
        indiceSegnale = searchSignal(serie);
        //SE SONO DEI DATI VALIDI
        if ( (paziente != -1) && (serie != -1) && (dialisi != -1)) {
          try { //PRELEVA IL VALORE DI EPSILON DA USARSI NELLA RANGE QUERY
            Epsilon = Double.parseDouble(Epsilon1.getText());
          }
          catch (NumberFormatException exc) { //SE NON SI SPECIFICA NIENTE
            if (normalizzata1.isSelected() == true) Epsilon = 1; //EPSILON=1 SE E' NORMALIZZATA
            else Epsilon = 50; //ESPISLON=50 SE NON E' NORMALIZZATA (FORSE NON HA MOLTO SENSO NON NORMALIZZARE
          }
          try {
            // PRELEVA L'EVENTUALE K PER LA KNN QUERY SIMULATA CON LA RANGE QUERY NEI KDTREE
            K = Integer.parseInt(KNN.getText());
          }
          catch (NumberFormatException exc) {
            K = 0; //SE NON C'E' IL VALORE CONSIDERA K=0
          }

          //INIZIALIZZA LA QUERY E CARICALA
          Query.init();
          loader.loadSingleSerie(paziente, dialisi, serie, Query,
                                 interpolazione.isSelected());

          T1 = java.lang.System.currentTimeMillis();
          //TRASFORMALA CON FOURIER DANDO numCoefficients COEFFICIENTI PER LA SERIE (SONO 3 IN PRIMA STESURA)
          fourier = new TFourier(Query, numCoefficients);
          fourier.calculate();
          transformed = fourier.getTransformedSerie();

          //SE E' UNA RANGE QUERY NORMALIZZATA
          if (normalizzata1.isSelected() == true)
            Risultati = kdTree[indiceSegnale].rangeQuery(transformed, Epsilon,
                range[indiceSegnale], false);
          //ALTRIMENTI SE NON E' NORMALIZZATA
          else Risultati = kdTree[indiceSegnale].rangeQuery(transformed,
              Epsilon, false);

          T2 = java.lang.System.currentTimeMillis();
          T1 = T2 - T1;

          //SE E' NORMALIZZATA FAI UN CERTO TIPO DI POSTPROCESSING CONSIDERANDO I RANGE DEI SETTAGGI
          if (normalizzata1.isSelected() == true)
            postProcessing = new TPostProcessing(Query, Risultati, Epsilon,
                                                 pazienti, range[indiceSegnale],
                                                 loader);
          //ALTRIMENTI FAI UN POST PROCESSING DIVERSO
          else
            postProcessing = new TPostProcessing(Query, Risultati, Epsilon,
                                                 pazienti, loader);

          //SE SI TRATTA DI UNA KNN SIMULATA FAI UN POST PROCESSING CON UN CERTO VALORE DI K
          if (K > 0)
            postProcessing.Calculate(K + 1, 1, false, interpolazione.isSelected());
          //SE SI TRATTA DI UNA RANGE QUERY FAI UN POSTO PROCESSING PASSANDO K=0
          else
            postProcessing.Calculate(K, 1, false, interpolazione.isSelected());

          //FAI IL POST PROCESSING DEI RISULTATI
          Risultati = postProcessing.getResult();
          Result = Risultati.getFirst();

          T3 = java.lang.System.currentTimeMillis();
          T2 = T3 - T2;

          list2.removeAll();

          if (K == 0)
            fileName = "DATA_KD_F_" + numCoefficients +
                "_RQ_casiEstratti_Segnale_" + serie + "_" + Epsilon + "_" +
                normalizzata1.isSelected() + ".txt";
          else
            fileName = "DATA_KD_F_" + numCoefficients +
                "_KNN_casiEstratti_Segnale_" + serie + "_" + K + "_" +
                normalizzata1.isSelected() + ".txt";

          outFile = new FileOutputStream(fileName);
          outS = new OutputStreamWriter(outFile);
          outStream = new BufferedWriter(outS);

          if (K > 0) { //FASE DI STAMPA DEI VALORI SE SI E' SCELTA UNA KNN SIMULATA K FA DA "TAGLIO" DEI RISULTATI

            outStream.write(
                "KNN SIMULATA CON KDTREE SULLA TIMESERIE SELEZIONATA", 0,
                "KNN SIMULATA CON KDTREE SULLA TIMESERIE SELEZIONATA".length());
            outStream.newLine();

            while (Result != null) {
              pazienteR = Result.getLinkPaziente();
              serieR = Result.getLinkSegnale();
              dialisiR = Result.getLinkDialisi();
              if ( ( (paziente != pazienteR) || (serie != serieR) ||
                    (dialisi != dialisiR)) && (K > 0)) {
                riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                    " " + String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                riga2 = String.valueOf(pazienteR) + " " +
                    String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                list2.add(riga);
                outStream.write(riga2, 0, riga2.length());
                outStream.newLine();
                K--;
              }
              Result = Risultati.getNext();
            }
          }
          else { //FASE DI STAMPA DEI VALORI SE SI E' SCELTA UNA RANGE QUERY

            outStream.write(
                "RANGE QUERY CON KDTREE SULLA TIMESERIE SELEZIONATA", 0,
                "RANGE QUERY CON KDTREE SULLA TIMESERIE SELEZIONATA".length());
            outStream.newLine();

            while (Result != null) {
              pazienteR = Result.getLinkPaziente();
              serieR = Result.getLinkSegnale();
              dialisiR = Result.getLinkDialisi();
              if ( (paziente != pazienteR) || (serie != serieR) ||
                  (dialisi != dialisiR)) {
                riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                    " " + String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                riga2 = String.valueOf(pazienteR) + " " +
                    String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                list2.add(riga);
                outStream.write(riga2, 0, riga2.length());
                outStream.newLine();
              }
              Result = Risultati.getNext();
            }
          }

          riga2 = "Tempo di esecuzione della query: " + T1;
          outStream.write(riga2, 0, riga2.length());
          outStream.newLine();
          riga2 = "Tempo di esecuzione del post processing: " + T2;
          outStream.write(riga2, 0, riga2.length());
          outStream.newLine();

          outStream.close();
          outS.close();
          outFile.close();

          Tempo1.setText(String.valueOf(T1));
          Tempo2.setText(String.valueOf(T2));
        }
      }
      catch (IOException exc) {
        System.out.println("Errore ! : " + exc.getMessage());
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (Kd-tree) da disco, o aver reindicizzato l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (Kd-tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  void Paziente1_itemStateChanged(ItemEvent e) {
    int I, paziente, maxval;

    Dialisi1.removeAllItems();
    paziente = getPaziente();
    if (paziente != -1) {
      try {
        connection.RunQuery(
            "SELECT ssessionid FROM r_ssession WHERE r_ssession.patientid = '"
            + paziente + "' ORDER BY ssessionid");
        while (connection.nextRecord()) {
          Dialisi1.addItem(connection.getRowValueString("ssessionid"));
        }
      }
      catch (java.sql.SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  void Paziente1_actionPerformed(ActionEvent e) {
    int I, paziente, maxval;

    Dialisi1.removeAllItems();
    paziente = getPaziente();
    if (paziente != -1) {
      try {
        connection.RunQuery(
            "SELECT ssessionid FROM r_ssession WHERE r_ssession.patientid = '"
            + paziente + "' ORDER BY ssessionid");
        while (connection.nextRecord()) {
          Dialisi1.addItem(connection.getRowValueString("ssessionid"));
        }
      }
      catch (java.sql.SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  class DialogHandler
      extends WindowAdapter implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      RHENE.this.setVisible(true);
      dialog.dispose();
    }
  }

  void normalizzata1_itemStateChanged(ItemEvent e) {
    if (normalizzata1.isSelected() == true) Epsilon1.setText("1");
    else Epsilon1.setText("50");
  }

  void normalizzata2_itemStateChanged(ItemEvent e) {
  }

  //RANGE QUERY CON KDTREE SUL CASO SELEZIONATO - CON NORMALIZZAZIONE
  void jMenuItem4_actionPerformed(ActionEvent e) {
    TCase caso;
    TCaseKNN ricerca;
    int paziente, dialisi;
    boolean normalized, elimina_nonvalidi, classificata;
    int transformationType;
    String riga;
    TResultNode Result;
    TLinkedList risultati;
    int pazienteR, dialisiR, serieR;
    double T1, T2, T3, T4;
    double Epsilon;
    int K, i;
    RandomAccessFile file;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;

    transformationType = 0;
    if (kdindexLoaded == true) {
      try {
        file = new RandomAccessFile("maxval.dat", "rw");
        for (i = 0; i < numSegnali; i++) {
          maxvals[i] = file.readDouble();
        }
        file.close();

        paziente = getPaziente();
        dialisi = getDialisi();

        if ( (paziente != -1) && (dialisi != -1)) {
          try {
            K = Integer.parseInt(KNN1.getText());
          }
          catch (NumberFormatException exc) {
            K = 0;
          }

          caso = new TCase(segnali.length,
                           paziente, dialisi, soglie);
          loader.loadCase(caso, interpolazione.isSelected());
          normalized = true;
          classificata = ! (ignoraClassificazione.isSelected());
          elimina_nonvalidi = false;

          ricerca = new TCaseKNN(caso, maxvals, K + 1, range, loader, kdTree,
                                 weights, pazienti, soglie, segnali);

          System.out.println("Esecuzione Primo Step:");
          System.out.println(
              "Range Query in direzione singole feature + intersezione risultati");

          T1 = java.lang.System.currentTimeMillis();
          ricerca.step1(normalized, numCoefficients, classificata,
                        interpolazione.isSelected());

          System.out.println("Esecuzione Secondo Step:");
          System.out.println("Estrazione dei K casi piu vicini");

          T2 = java.lang.System.currentTimeMillis();
          ricerca.step2(elimina_nonvalidi, soglie, normalized,
                        interpolazione.isSelected());

          T3 = java.lang.System.currentTimeMillis();

          risultati = ricerca.getResult();
          Result = risultati.getFirst();

          list2.removeAll();

          /*          fileName= "DATA_KD_F_"+numCoefficients+"_RQ_casiEstratti_Caso_"+paziente+"_"+dialisi+"_K_"+K+".txt";
                    outFile= new FileOutputStream (fileName);
                    outS= new OutputStreamWriter (outFile);
                    outStream= new BufferedWriter (outS);

                    outStream.write("RANGE QUERY CON KDTREE SUL CASO SELEZIONATO - CON NORMALIZZAZIONE", 0, "RANGE QUERY CON KDTREE SUL CASO SELEZIONATO - CON NORMALIZZAZIONE".length());
                    outStream.newLine(); */

          while (Result != null) {
            pazienteR = Result.getLinkPaziente();
            serieR = Result.getLinkSegnale();
            dialisiR = Result.getLinkDialisi();
            if ( ( (paziente != pazienteR) || (dialisi != dialisiR)) && (K > 0)) {
              riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                  " " + String.valueOf(dialisiR) +
                  "   " + String.valueOf(Result.getDistance());
              list2.add(riga);
              /*              outStream.write(riga, 0, riga.length());
                            outStream.newLine(); */
              K--;
            }
            Result = risultati.getNext();
          }

          riga = "Tempo di esecuzione dello STEP1: " + (T2 - T1);
          /*          outStream.write(riga, 0, riga.length());
                    outStream.newLine(); */
          riga = "Tempo di esecuzione dello STEP2: " + (T3 - T2);
          /*          outStream.write(riga, 0, riga.length());
                    outStream.newLine(); */

          /*          outStream.close();
                    outS.close();
                    outFile.close(); */

          list2.add("");
          list2.add("  Tempo Step 1 : " + String.valueOf(T2 - T1));
          list2.add("  Tempo Step 2 : " + String.valueOf(T3 - T2));

          Tempo1.setText(String.valueOf(T3 - T1));
          Tempo2.setText("");
        }
      }
      catch (IOException ex) {
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (Kd-tree) da disco, o aver reindicizzato l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (Kd-tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  void visCaso1_actionPerformed(ActionEvent e) {
    int paziente, dialisi, i;
    TCase caso;
    TTimeSerie SerieLetta;
    double T1, T2;

    paziente = getPaziente();
    dialisi = getDialisi();

    if ( (paziente != -1) && (dialisi != -1)) {
      try {
        caso = new TCase(segnali.length, paziente, dialisi, soglie);

        T1 = java.lang.System.currentTimeMillis();
        loader.loadCase(caso, interpolazione.isSelected());
        T2 = java.lang.System.currentTimeMillis();
        Tempo1.setText(String.valueOf(T2 - T1));

        for (i = 0; i < 6; i++) {
          SerieLetta = caso.getSerie(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie1[i] = new TShowSerie(SerieLetta, 0, 120 * i, 256, 120);
          visSerie1[i].setVisible(true);
        }
        for (i = 6; i < segnali.length; i++) {
          SerieLetta = caso.getSerie(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie1[i] = new TShowSerie(SerieLetta, 256, 120 * (i - 6), 256,
                                        120);
          visSerie1[i].setVisible(true);
        }

      }
      catch (IOException exc) {
      }
    }
  }

  void visCaso2_actionPerformed(ActionEvent e) {
    int paziente, dialisi, i;
    TCase caso;
    TTimeSerie SerieLetta;
    String riga;

    riga = list2.getSelectedItem();
    if (riga != null) {
      paziente = getPaziente(riga);
      dialisi = getDialisi(riga);

      try {
        caso = new TCase(segnali.length, paziente, dialisi, soglie);
        loader.loadCase(caso, interpolazione.isSelected());

        for (i = 0; i < 6; i++) {
          SerieLetta = caso.getSerie(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie2[i] = new TShowSerie(SerieLetta, 512, 120 * i, 256, 120);
          visSerie2[i].setVisible(true);
        }
        for (i = 6; i < segnali.length; i++) {
          SerieLetta = caso.getSerie(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie2[i] = new TShowSerie(SerieLetta, 512 + 256, 120 * (i - 6),
                                        256, 120);
          visSerie2[i].setVisible(true);
        }
      }
      catch (IOException exc) {
      }
    }
  }

  void normalizzata1_actionPerformed(ActionEvent e) {

  }

  void chiudiCaso_actionPerformed(ActionEvent e) {
    int i;

    for (i = 0; i < visSerie1.length; i++) {
      if (visSerie1[i] != null) {
        visSerie1[i].setVisible(false);
      }
    }
  }

  void chiudiCaso2_actionPerformed(ActionEvent e) {
    int i;

    for (i = 0; i < visSerie1.length; i++) {
      if (visSerie2[i] != null) {
        visSerie2[i].setVisible(false);
      }
    }
  }

  void jMenuItem6_actionPerformed(ActionEvent e) {
    TIndex indice = new TIndex();
    double T1, T2, T3, T4;
    int I;

    for (I = 0; I < tvTree.length; I++) tvTree[I] = new TTVTree(numCoefficients,
        numfiglitv, alpha, loader);
    for (I = 0; I < range.length; I++) range[I] = new TRange(segnali[I]);

    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    //System.out.println("@@ INIZIO CARICAMENTO INDICI TV-Tree DA DISCO  @@");
    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    T1 = java.lang.System.currentTimeMillis(); /*INIZIO CARICAMENTI*/
    for (I = 0; I < segnali.length; I++) {
      //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      //System.out.println("      CARICAMENTO INDICE PER SEGNALE "+segnali[I]);

      T2 = java.lang.System.currentTimeMillis(); /*INIZIO CARICAMENTI*/
      indice.loadIndex(tvTree[I], segnali[I], numCoefficients);
      range[I].loadRangeTV();
      T3 = java.lang.System.currentTimeMillis(); /*INIZIO CARICAMENTI*/

      //System.out.println("-->  SEGNALE "+segnali[I]+": "+((T3-T2)/1000)+" secondi <--");
    }
    T4 = java.lang.System.currentTimeMillis(); /*INIZIO CARICAMENTI*/

    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println("-->  TEMPO TOTALE: " + ( (T4 - T1) / 1000) +
                       " secondi <--");
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    tvindexLoaded = true;
  }

  void jMenuItem7_actionPerformed(ActionEvent e) {
    double T1, T2, T3, T4;
    TTimeSerie Caricata = new TTimeSerie();
    TTimeSerie Query = new TTimeSerie();
    TTimeSerie Originale = new TTimeSerie();
    double dist1, dist2;
    int paziente, serie, dialisi, maxDialisi, posizione;
    int[] mindialisi;
    TFourier fourier = new TFourier();
    TPiecewise piecewise = new TPiecewise();
    TPiecewise piecewise1 = new TPiecewise();
    String numLetto, Riga;
    int I;
    TIndex indice = new TIndex();

    list2.removeAll();

    for (I = 0; I < tvTree.length; I++) tvTree[I] = new TTVTree(numCoefficients,
        numfiglitv, alpha, loader);
    for (I = 0; I < range.length; I++) range[I] = new TRange(segnali[I]);

//    loader.getMaxDialisi2 (this.maxdialisi);

    this.maxdialisi = new int[pazienti[numPazienti - 1] + 1];
    for (int i = 0; i < this.pazienti.length; i++)
      this.maxdialisi[pazienti[i]] = loader.getMaxDialisi(pazienti[i]);

    mindialisi = new int[pazienti[numPazienti - 1] + 1];
    for (int i = 0; i < this.pazienti.length; i++)
      mindialisi[pazienti[i]] = loader.getMinDialisi(pazienti[i]);

    fourier.setNumCoefficients(numCoefficients);
    piecewise.setNumSegmenti(numCoefficients);
    piecewise1.setNumSegmenti(numCoefficients);

    System.out.println("**************************************************");
    System.out.println("** Inizio generazione indici TV-Tree in memoria **");
    System.out.println("**************************************************");

    T1 = java.lang.System.currentTimeMillis(); /*INIZIO CARICAMENTI*/
    for (serie = 0; serie < segnali.length; serie++) {
      System.out.println("**************************************************");
      System.out.println("** Inizio caricamento segnale " + segnali[serie] +
                         " in memoria **");
      System.out.println("**************************************************");
      T2 = java.lang.System.currentTimeMillis(); /*INIZIO SINGOLO PAZIENTE*/
      for (I = 0; I < pazienti.length; I++) {
        paziente = pazienti[I];
        maxDialisi = this.maxdialisi[paziente];
        for (dialisi = mindialisi[paziente]; dialisi <= maxDialisi; dialisi++) {
          loader.loadSingleSerie(paziente, dialisi, segnali[serie], Caricata,
                                 interpolazione.isSelected());

          if (Caricata.getNumValues() > soglie[serie]) {
            range[serie].checkRange(Caricata);
            tvTree[serie].checkMaxLength(Caricata.getNumValues());

            fourier.setOriginalSerie(Caricata);
            fourier.calculate();
            Caricata = fourier.getTransformedSerie();

            tvTree[serie].addKey(Caricata, paziente, dialisi, segnali[serie], true);
          }
        }
      }
      T3 = java.lang.System.currentTimeMillis();
      /*FINE CARICAMENTO INDICI SINGOLO PAZIENTE*/
      System.out.println("   --> Tempo impiegato: " + ( (T3 - T2) / 1000) +
                         " secondi.");
    }
    T4 = java.lang.System.currentTimeMillis();
    /*FINE CARICAMENTO INDICI IN MEMORIA*/
    System.out.println("***********************************************");
    System.out.println("** Tempo TOTALE: " + ( (T4 - T1) / 1000) +
                       " secondi. **");
    System.out.println("***********************************************");

    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println("@@     INIZIO SCRITTURA INDICI SU DISCO     @@");
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    T1 = java.lang.System.currentTimeMillis();
    /*INIZIO SCRITTURA INDICI SU DISCO*/
    for (I = 0; I < segnali.length; I++) {
      indice.saveIndex(tvTree[I], segnali[I], numCoefficients);
      range[I].saveRangeTV();
    }

    try {
      RandomAccessFile file = new RandomAccessFile("maxDialisi.dat", "rw");
      for (int i = 0; i < this.maxdialisi.length; i++)
        file.writeInt(this.maxdialisi[i]);
      file.close();
    }
    catch (IOException exc) {
    }

    /*CARICAMENTO DEI PAZIENTI PRESENTI IN TABELLA*/
    Paziente1.removeAllItems();
    for (int i = 0; i < pazienti.length; i++) {
      Paziente1.addItem(String.valueOf(pazienti[i]));
    }

    T2 = java.lang.System.currentTimeMillis();
    /*FINE SCRITTURA INDICI SU DISCO*/
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println("@@ Tempo TOTALE: " + ( (T2 - T1) / 1000) +
                       " secondi. @@");
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    tvindexLoaded = true;
  }

  //RANGE QUERY CON TV-TREE SULLA TS SELEZIONATA
  void jMenuItem8_actionPerformed(ActionEvent e) {
    double Epsilon;
    int paziente, serie, dialisi, Key;
    int pazienteR, serieR, dialisiR;
    String riga;
    TTimeSerie Query = new TTimeSerie();
    TTimeSerie transformed;
    TFourier fourier;
    TPiecewise piecewise;
    TLinkedList Risultati;
    TResultNode Result;
    TPostProcessing postProcessing;
    double T1, T2, T3;
    int indiceSegnale;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;
    String riga2;

    if (tvindexLoaded == true) { //SE GLI INDICI TV-TREE SONO STATI CARICATI
      //ACQUISISCI I DATI DEL PAZIENTE, SEGNALE E DIALISI DALL'INTERFACCIA GRAFICA
      try {
        paziente = getPaziente();
        serie = getSerie();
        dialisi = getDialisi();
        indiceSegnale = searchSignal(serie);
        //SE SONO DEI DATI VALIDI
        if ( (paziente != -1) && (serie != -1) && (dialisi != -1)) {
          try { //PRELEVA IL VALORE DI EPSILON DA USARSI NELLA RANGE QUERY
            Epsilon = Double.parseDouble(Epsilon1.getText());
          }
          catch (NumberFormatException exc) { //SE NON SI SPECIFICA NIENTE
            if (normalizzata1.isSelected() == true) Epsilon = 1; //EPSILON=1 SE E' NORMALIZZATA
            else Epsilon = 50; //ESPISLON=50 SE NON E' NORMALIZZATA (FORSE NON HA MOLTO SENSO NON NORMALIZZARE
          }
          //INIZIALIZZA LA QUERY E CARICALA
          Query.init();
          loader.loadSingleSerie(paziente, dialisi, serie, Query,
                                 interpolazione.isSelected());

          T1 = java.lang.System.currentTimeMillis();
          //TRASFORMALA CON FOURIER DANDO numCoefficients COEFFICIENTI PER LA SERIE (SONO 3 IN PRIMA STESURA)
          fourier = new TFourier(Query, numCoefficients);
          fourier.calculate();
          transformed = fourier.getTransformedSerie();

          if (normalizzata1.isSelected() == true) //CON NORMALIZZAZIONE
            Risultati = tvTree[indiceSegnale].rangeQueryNormalized(transformed,
                Epsilon, range[indiceSegnale], false);
          else //SENZA NORMALIZZAZIONE
            Risultati = tvTree[indiceSegnale].rangeQuery(transformed, Epsilon, false);

          T2 = java.lang.System.currentTimeMillis();
          T1 = T2 - T1;

          //SE E' NORMALIZZATA FAI UN CERTO TIPO DI POSTPROCESSING CONSIDERANDO I RANGE DEI SETTAGGI
          if (normalizzata1.isSelected() == true)
            postProcessing = new TPostProcessing(Query, Risultati, Epsilon,
                                                 pazienti, range[indiceSegnale],
                                                 loader);
          //ALTRIMENTI FAI UN POST PROCESSING DIVERSO
          else
            postProcessing = new TPostProcessing(Query, Risultati, Epsilon,
                                                 pazienti, loader);
          //SE SI TRATTA DI UNA RANGE QUERY FAI UN POSTO PROCESSING PASSANDO K=0
          postProcessing.Calculate(0, 1, false, interpolazione.isSelected());
          //FAI IL POST PROCESSING DEI RISULTATI
          Risultati = postProcessing.getResult();
          Result = Risultati.getFirst();
          T3 = java.lang.System.currentTimeMillis();

          T2 = T3 - T2;

          list2.removeAll();

          /*          fileName= "DATA_TV_F_"+numCoefficients+"_RQ_casiEstratti_Segnale_"+serie+"_"+Epsilon+"_"+normalizzata1.isSelected()+".txt";
                    outFile= new FileOutputStream (fileName);
                    outS= new OutputStreamWriter (outFile);
                    outStream= new BufferedWriter (outS);

                    outStream.write("RANGE QUERY CON TV-TREE SULLA TS SELEZIONATA", 0, "RANGE QUERY CON TV-TREE SULLA TS SELEZIONATA".length());
                    outStream.newLine(); */

          while (Result != null) {
            pazienteR = Result.getLinkPaziente();
            serieR = Result.getLinkSegnale();
            dialisiR = Result.getLinkDialisi();
            if ( (paziente != pazienteR) || (serie != serieR) ||
                (dialisi != dialisiR)) {
              riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                  " " + String.valueOf(dialisiR) +
                  "   " + String.valueOf(Result.getDistance());
              riga2 = String.valueOf(pazienteR) + " " + String.valueOf(dialisiR) +
                  "   " + String.valueOf(Result.getDistance());
              list2.add(riga);
              /*               outStream.write(riga2, 0, riga2.length());
                             outStream.newLine(); */
            }
            Result = Risultati.getNext();
          }

          riga2 = "Tempo di esecuzione della query: " + T1;
          /*         outStream.write(riga2, 0, riga2.length());
                   outStream.newLine(); */
          riga2 = "Tempo di esecuzione del post processing: " + T2;
          /*         outStream.write(riga2, 0, riga2.length());
                   outStream.newLine(); */

          /*         outStream.close();
                   outS.close();
                   outFile.close(); */

          Tempo1.setText(String.valueOf(T1));
          Tempo2.setText(String.valueOf(T2));
        }
      }
      catch (IOException exc) {
        System.out.println("Errore ! : " + exc.getMessage());
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (TV-Tree) da disco, o aver reindicizzato (TV-Tree) l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (TV-Tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  //RANGE QUERY CON TV-TREE SUL CASO SELEZIONATO
  void jMenuItem9_actionPerformed(ActionEvent e) {
    TCase caso;
    TCaseKNN ricerca;
    int paziente, dialisi;
    boolean normalized, elimina_nonvalidi, classificata;
    int transformationType;
    String riga;
    TResultNode Result;
    TLinkedList risultati;
    int pazienteR, dialisiR, serieR;
    double T1, T2, T3, T4;
    double Epsilon;
    int K, i;
    RandomAccessFile file;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;

    transformationType = 0;
    if (tvindexLoaded == true) {
      try {
        file = new RandomAccessFile("maxval.dat", "rw");
        for (i = 0; i < numSegnali; i++) {
          maxvals[i] = file.readDouble();
        }
        file.close();

        paziente = getPaziente();
        dialisi = getDialisi();

        if ( (paziente != -1) && (dialisi != -1)) {
          try {
            K = Integer.parseInt(KNN1.getText());
          }
          catch (NumberFormatException exc) {
            K = 0;
          }
          try { //PRELEVA IL VALORE DI EPSILON DA USARSI NELLA RANGE QUERY
            Epsilon = Double.parseDouble(Epsilon2.getText());
          }
          catch (NumberFormatException exc) { //SE NON SI SPECIFICA NIENTE
            if (normalizzata1.isSelected() == true)
              Epsilon = 1; //EPSILON=1 SE E' NORMALIZZATA
            else
              Epsilon = 50; //ESPISLON=50 SE NON E' NORMALIZZATA (FORSE NON HA MOLTO SENSO NON NORMALIZZARE
          }

          caso = new TCase(segnali.length,
                           paziente, dialisi, soglie);
          loader.loadCase(caso, interpolazione.isSelected());
          normalized = true; //SI IMPONE CHE UNA QUERY SUL CASO ABBIA UNA DISTANZA SEMPRE!!! NORMALIZZATA ?!?
          classificata = ! (ignoraClassificazione.isSelected());
          elimina_nonvalidi = false;

          ricerca = new TCaseKNN(caso, maxvals, K + 1, range, loader, tvTree,
                                 weights, pazienti, soglie, segnali, paziente,
                                 dialisi, this.daconsiderare);

          System.out.println("Esecuzione Primo Step:");
          System.out.println(
              "Range Query in direzione singole feature + intersezione risultati");

          T1 = java.lang.System.currentTimeMillis();
          ricerca.tvstep1rq(normalized, numCoefficients, classificata,
                            interpolazione.isSelected());

          System.out.println("Esecuzione Secondo Step:");
          System.out.println("Estrazione dei K casi piu vicini");

          T2 = java.lang.System.currentTimeMillis();
          ricerca.step2(elimina_nonvalidi, soglie, normalized,
                        interpolazione.isSelected());

          T3 = java.lang.System.currentTimeMillis();

          risultati = ricerca.getResult();
          Result = risultati.getFirst();

          list2.removeAll();
          /*          fileName= "DATA_TV_F_"+numCoefficients+"_RQ_CasiEstratti_Caso_"+paziente+"_"+dialisi+"_K_"+K+"_EPSILON_"+Epsilon+".txt";
                    outFile= new FileOutputStream (fileName);
                    outS= new OutputStreamWriter (outFile);
                    outStream= new BufferedWriter (outS);

                    outStream.write("RANGE QUERY CON TV-TREE SUL CASO SELEZIONATO", 0, "RANGE QUERY CON TV-TREE SUL CASO SELEZIONATO".length());
                    outStream.newLine(); */

          while (Result != null) {
            pazienteR = Result.getLinkPaziente();
            serieR = Result.getLinkSegnale();
            dialisiR = Result.getLinkDialisi();
            if ( ( (paziente != pazienteR) || (dialisi != dialisiR)) && (K > 0)) {
              riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                  " " + String.valueOf(dialisiR) +
                  "   " + String.valueOf(Result.getDistance());
              list2.add(riga);
              /*              outStream.write(riga, 0, riga.length());
                            outStream.newLine(); */

              K--;
            }
            Result = risultati.getNext();
          }

          riga = "Tempo di esecuzione dello STEP1: " + (T2 - T1);
          /*          outStream.write(riga, 0, riga.length());
                    outStream.newLine(); */
          riga = "Tempo di esecuzione dello STEP2: " + (T3 - T2);
          /*          outStream.write(riga, 0, riga.length());
                    outStream.newLine(); */

          /*          outStream.close();
                    outS.close();
                    outFile.close(); */

          list2.add("");
          list2.add("  Tempo Step 1 : " + String.valueOf(T2 - T1));
          list2.add("  Tempo Step 2 : " + String.valueOf(T3 - T2));

          Tempo1.setText(String.valueOf(T3 - T1));
          Tempo2.setText("");
        }
      }
      catch (IOException ex) {
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (TV-Tree) da disco, o aver reindicizzato (TV-Tree) l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (TV-Tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  //KNN QUERY CON TV-TREE SULLA TS SELEZIONATA
  void jMenuItem14_actionPerformed(ActionEvent e) {
    int paziente, serie, dialisi, Key;
    int pazienteR, serieR, dialisiR;
    String riga;
    TTimeSerie Query = new TTimeSerie();
    TTimeSerie transformed;
    TFourier fourier;
    TPiecewise piecewise;
    TLinkedList Risultati;
    TResultNode Result;
    TPostProcessing postProcessing;
    double T1, T2;
    int indiceSegnale, K;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;
    String riga2;

    if (tvindexLoaded == true) { //SE GLI INDICI TV-TREE SONO STATI CARICATI
      //ACQUISISCI I DATI DEL PAZIENTE, SEGNALE E DIALISI DALL'INTERFACCIA GRAFICA
      try {
        paziente = getPaziente();
        serie = getSerie();
        dialisi = getDialisi();
        indiceSegnale = searchSignal(serie);
        //SE SONO DEI DATI VALIDI
        try {
          K = Integer.parseInt(KNN.getText());
        }
        catch (NumberFormatException exc) {
          K = 0;
        }
        if (K > 0) {
          if ( (paziente != -1) && (serie != -1) && (dialisi != -1)) {
            //INIZIALIZZA LA QUERY E CARICALA
            Query.init();
            loader.loadSingleSerie(paziente, dialisi, serie, Query,
                                   interpolazione.isSelected());

            T1 = java.lang.System.currentTimeMillis();
            //TRASFORMALA CON FOURIER DANDO numCoefficients COEFFICIENTI PER LA SERIE (SONO 3 IN PRIMA STESURA)
            fourier = new TFourier(Query, numCoefficients);
            fourier.calculate();
            transformed = fourier.getTransformedSerie();

            TTVNode passoate = new TTVNode(this.numCoefficients, this.alpha,
                                           this.numfiglitv, true);
            passoate.getCenter().copySerie(transformed);
            passoate.setRadius(0);
            passoate.setLink(paziente, dialisi, serie);

            int i;
            tvTree[indiceSegnale].setP(K + 2);
            tvTree[indiceSegnale].setSOL1(new TTVNode[tvTree[indiceSegnale].getP()]);
            tvTree[indiceSegnale].setSOL2(new double[tvTree[indiceSegnale].getP()]);
            for (i = 0; i < tvTree[indiceSegnale].getP(); i++) {
              tvTree[indiceSegnale].setSOL1(i, new TTVNode(tvTree[indiceSegnale].getK(),
                  tvTree[indiceSegnale].getAlpha(),
                  tvTree[indiceSegnale].getNumFigli(), true));
              tvTree[indiceSegnale].setSOL2(i, 999999999);
            }

            Risultati = tvTree[indiceSegnale].knnQuery(passoate, K + 2,
                normalizzata1.isSelected(), range[indiceSegnale],
                interpolazione.isSelected());

            T2 = java.lang.System.currentTimeMillis();
            T1 = T2 - T1;

            Result = Risultati.getFirst();

            list2.removeAll();
            fileName = "DATA_TV_F_" + numCoefficients +
                "_KNN_casiEstratti_Segnale_" + serie + "_" + K + "_" +
                normalizzata1.isSelected() + ".txt";
            outFile = new FileOutputStream(fileName);
            outS = new OutputStreamWriter(outFile);
            outStream = new BufferedWriter(outS);

            outStream.write("KNN QUERY CON TV-TREE SULLA TS SELEZIONATA", 0,
                            "KNN QUERY CON TV-TREE SULLA TS SELEZIONATA".length());
            outStream.newLine();

            while ( (Result != null) && (K >= 0)) {
              pazienteR = Result.getLinkPaziente();
              serieR = Result.getLinkSegnale();
              dialisiR = Result.getLinkDialisi();
              if ( (paziente != pazienteR) || (serie != serieR) ||
                  (dialisi != dialisiR)) {
                riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                    " " + String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                riga2 = String.valueOf(pazienteR) + " " +
                    String.valueOf(dialisiR) +
                    "   " + String.valueOf(Result.getDistance());
                list2.add(riga);
                outStream.write(riga2, 0, riga2.length());
                outStream.newLine();
              }
              Result = Risultati.getNext();
              K--;
            }

            riga2 =
                "Tempo di esecuzione della query KNN e postprocessing integrato: " +
                T1;
            outStream.write(riga2, 0, riga2.length());
            outStream.newLine();

            outStream.close();
            outS.close();
            outFile.close();
            Tempo1.setText(String.valueOf(T1));
          }
        }
        else {
          String[] text = {
              "Per poter effettuare una KNN Query, il valore di KNN deve essere impostato almeno ad 1"};
          String[] buttons = {
              "OK"};
          dialog = new TMessageDialog(RHENE.this,
                                      "Errore - Valore KNN errato o mancante", true,
                                      text, buttons, dh, dh);
          dialog.setLocation(200, 200);
          dialog.setVisible(true);
        }
      }
      catch (IOException exc) {
        System.out.println("Errore ! : " + exc.getMessage());
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (TV-Tree) da disco, o aver reindicizzato (TV-Tree) l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (TV-Tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  //KNN QUERY CON TV-TREE SU CASO SELEZIONATO
  void jMenuItem15_actionPerformed(ActionEvent e) {
    TCase caso;
    TCaseKNN ricerca;
    int paziente, dialisi;
    boolean normalized, elimina_nonvalidi, classificata;
    int transformationType;
    String riga;
    TResultNode Result;
    TLinkedList risultati;
    int pazienteR, dialisiR, serieR;
    double T1, T2, T3, T4;
    double Epsilon;
    boolean[] daconsiderare = new boolean[this.numSegnali];
    int K, i;
    RandomAccessFile file;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName;
    TTimeSerie trasformo, Query = new TTimeSerie();
    TFourier fourier;

    transformationType = 0;
    if (tvindexLoaded == true) {
      try {
        file = new RandomAccessFile("maxval.dat", "rw");
        for (i = 0; i < numSegnali; i++) {
          maxvals[i] = file.readDouble();
        }
        file.close();

        paziente = getPaziente();
        dialisi = getDialisi();

        this.daconsiderare[0] = considero1.isSelected();
        this.daconsiderare[1] = considero2.isSelected();
        this.daconsiderare[2] = considero3.isSelected();
        this.daconsiderare[3] = considero4.isSelected();
        this.daconsiderare[4] = considero5.isSelected();
        this.daconsiderare[5] = considero6.isSelected();
        this.daconsiderare[6] = considero7.isSelected();
        this.daconsiderare[7] = considero8.isSelected();
        this.daconsiderare[8] = considero9.isSelected();
        this.daconsiderare[9] = considero10.isSelected();
        this.daconsiderare[10] = considero11.isSelected();

        boolean procedo = false;
        for (i = 0; i < numSegnali; i++)
          if (this.daconsiderare[i] == true)
            procedo = true;

        if ( (paziente != -1) && (dialisi != -1) && (procedo == true)) {
          try {
            K = Integer.parseInt(KNN1.getText());
          }
          catch (NumberFormatException exc) {
            K = 0;
          }

          caso = new TCase(segnali.length,
                           paziente, dialisi, soglie);
          loader.loadCase(caso, interpolazione.isSelected());
          normalized = true;
          classificata = ! (ignoraClassificazione.isSelected());
          elimina_nonvalidi = false;

          ricerca = new TCaseKNN(caso, maxvals, K + 1, range, loader, tvTree,
                                 weights, pazienti, soglie, segnali, paziente,
                                 dialisi, this.daconsiderare);

          System.out.println("************************");
          System.out.println("** KNN QUERY SUL CASO **");
          System.out.println("************************");
          System.out.println("Parametri della query utilizzati:");
          System.out.println("Paziente: " + paziente);
          System.out.println("Dialisi: " + dialisi);
          for (i = 0; i < numSegnali; i++) {
            if (this.daconsiderare[i] == true) {
              System.out.println("SEGNALE " + this.nomesegnali[i]);
            }
          }

          System.out.println("Valore di K specificato: " + K);
          System.out.println("********************");
          System.out.println("*Inizio dello step1*");
          System.out.println("********************");

          T1 = java.lang.System.currentTimeMillis();
          ricerca.tvstep1knn(normalized, numCoefficients,
                             interpolazione.isSelected());
          T2 = java.lang.System.currentTimeMillis();

          System.out.println("SECONDI DI ESECUZIONE DELLO STEP1:    " +
                             ( (T2 - T1) / 1000));
          System.out.println("********************");
          System.out.println("*Inizio dello step2*");
          System.out.println("********************");

          ricerca.tvstep2knn(elimina_nonvalidi, soglie, normalized,
                             interpolazione.isSelected());
          T3 = java.lang.System.currentTimeMillis();
          System.out.println("SECONDI DI ESECUZIONE DELLO STEP2:    " +
                             ( (T3 - T2) / 1000));
          System.out.println("Termine esecuzione KNN sul Caso selezionato.");

          risultati = ricerca.getResult();
          Result = risultati.getFirst();

          list2.removeAll();

          String valori = "I";
          for (i = 0; i < numSegnali; i++)
            if (this.daconsiderare[i] == true)
              valori = valori + "_" + this.nomesegnali[i];

          fileName = "DATA_TV_F_" + numCoefficients + "_KNN_CasiEstratti_Caso_" +
              paziente + "_" + dialisi + "_K_" + K + "_VALOR" + valori + ".txt";
          outFile = new FileOutputStream(fileName);
          outS = new OutputStreamWriter(outFile);
          outStream = new BufferedWriter(outS);

          outStream.write("KNN QUERY CON TV-TREE SU CASO SELEZIONATO", 0,
                          "KNN QUERY CON TV-TREE SU CASO SELEZIONATO".length());
          outStream.newLine();

          while ( (Result != null) /*&&(K>=0)*/) {
            pazienteR = Result.getLinkPaziente();
            serieR = Result.getLinkSegnale();
            dialisiR = Result.getLinkDialisi();
            if ( ( (paziente != pazienteR) || (dialisi != dialisiR)) && (K > 0)) {
              riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
                  " " + String.valueOf(dialisiR) +
                  "   " + String.valueOf(Result.getDistance());
              list2.add(riga);
              outStream.write(riga, 0, riga.length());
              outStream.newLine();
              K--;
            }
            Result = risultati.getNext();
          }

          riga = "Tempo di esecuzione dello STEP 1: " + (T2 - T1);
          outStream.write(riga, 0, riga.length());
          outStream.newLine();
          riga = "Tempo di esecuzione dello STEP 2: " + (T3 - T2);
          outStream.write(riga, 0, riga.length());
          outStream.newLine();

          outStream.close();
          outS.close();
          outFile.close();

          list2.add("");
          list2.add("  Tempo Step 1 : " + String.valueOf(T2 - T1));
          list2.add("  Tempo Step 2 : " + String.valueOf(T3 - T2));

          Tempo1.setText(String.valueOf(T3 - T1));
          Tempo2.setText("");
        }
        else
        if (procedo == false) {
          String[] text = {
              " Per poter effettuare la KNN sul caso del paziente, e necessario attivare almeno un segnale. ",
              " Se viene specificato solamente un segnale, si otterra la KNN sul segnale del paziente. "};
          String[] buttons = {
              "OK"};
          dialog = new TMessageDialog(RHENE.this,
                                      "Errore - Assenza di segnali in query KNN", true,
                                      text, buttons,
                                      dh, dh);
          dialog.setLocation(200, 200);
          dialog.setVisible(true);
        }
      }
      catch (IOException ex) {
      }
    }
    else {
      String[] text = {
          "Assicurarsi di aver caricato gli indici (TV-Tree) da disco, o aver reindicizzato (TV-Tree) l'archivio,",
          "prima di effettuare qualsiasi query"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Errore - Indici (TV-Tree) non caricati", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
    }
  }

  void jMenuItemSetup1_actionPerformed(ActionEvent e) {
    setParameters setP = new setParameters();
    setP.main(null);
  }

  void coeffourier_focusLost(FocusEvent e) {
    if (Integer.parseInt(this.coeffourier.getText()) != this.numCoefficients) {
      String[] text = {
          " Si e rilevata una modifica sul numero di coefficienti di Fourier:",
          " si rende necessaria, per un corretto funzionamento, una reindicizzazione di KD-TREE e TV-TREE",
          " prima dell'esecuzione di ogni loro operazione.",
          " --> Eseguire la modifica da setParameters e reindicizzare <--"};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Avviso di reindicizzazione strutture SAM", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
      this.coeffourier.setText(String.valueOf(this.numCoefficients));
    }
  }

  void figlipernodo_focusLost(FocusEvent e) {
    if (Integer.parseInt(this.figlipernodo.getText()) != this.numfiglitv) {
      String[] text = {
          "Il numero di figli per nodo nel TV-Tree e stato reimpostato."};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Variazione figli per nodo TV-Tree", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
      this.numfiglitv = Integer.parseInt(this.figlipernodo.getText());

      try {
        RandomAccessFile file = new RandomAccessFile("numFigliTV.dat", "rw");
        file.writeInt(this.numfiglitv);
        file.close();
      }
      catch (IOException exc) {
        exc.printStackTrace();
      }
    }
  }

  void dimensioniattive_focusLost(FocusEvent e) {
    if (Integer.parseInt(this.dimensioniattive.getText()) != this.alpha) {
      String[] text = {
          "Il numero di dimensioni attive nel TV-Tree e stato reimpostato."};
      String[] buttons = {
          "OK"};
      dialog = new TMessageDialog(RHENE.this,
                                  "Variazione dimensioni attive TV-Tree", true,
                                  text, buttons, dh, dh);
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
      this.alpha = Integer.parseInt(this.dimensioniattive.getText());

      try {
        RandomAccessFile file = new RandomAccessFile("numAlpha.dat", "rw");
        file.writeInt(this.alpha);
        file.close();
      }
      catch (IOException exc) {
        exc.printStackTrace();
      }
    }
  }

  void jMenuItemSetup2_actionPerformed(ActionEvent e) {
    try {
      this.caricaSettaggi();
    }
    catch (IOException exc) {
      exc.printStackTrace();
    }
    catch (java.sql.SQLException sqlexc) {
      sqlexc.printStackTrace();
    }
  }

  void jMenuItem16_actionPerformed(ActionEvent e) {
    ClassificatorFrame c = new ClassificatorFrame();
    c.setParameters(kdTree, tvTree, connection);
    c.setVisible(true);
  }

  public void visMacroCaso1_actionPerformed(ActionEvent e) {
    int paziente, dialisi, prec, succ, i;
    MacroCase caso;
    TTimeSerie SerieLetta;
    double T1, T2;

    paziente = getPaziente();
    dialisi = getDialisi();
    prec = getPrec();
    succ = getSucc();

    if ( (paziente != -1) && (dialisi != -1)) {
      try {
        caso = new MacroCase(paziente, dialisi, prec, succ);

        T1 = java.lang.System.currentTimeMillis();
        loader.loadMacroCase(caso, interpolazione.isSelected());
        T2 = java.lang.System.currentTimeMillis();
        Tempo1.setText(String.valueOf(T2 - T1));

        for (i = 0; i < 6; i++) {
          SerieLetta = caso.getMedian(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie1[i] = new TShowSerie(SerieLetta, 0, 120 * i, 256, 120);
          visSerie1[i].setVisible(true);
        }
        for (i = 6; i < segnali.length; i++) {
          SerieLetta = caso.getMedian(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie1[i] = new TShowSerie(SerieLetta, 256, 120 * (i - 6), 256,
                                        120);
          visSerie1[i].setVisible(true);
        }

      }
      catch (IOException exc) {
      }
    }
  }

  public void visMacroCaso2_actionPerformed(ActionEvent e) {
    int paziente, dialisi, prec, succ, i;
    MacroCase caso;
    TTimeSerie SerieLetta;
    String riga;

    riga = list1.getSelectedItem();
    if (riga != null) {
      paziente = getPaziente(riga);
      dialisi = getDialisi(riga);
      prec = getPrec();
      succ = getSucc();

      try {
        caso = new MacroCase(paziente, dialisi, prec, succ);
        loader.loadMacroCase(caso, interpolazione.isSelected());

        for (i = 0; i < 6; i++) {
          SerieLetta = caso.getMedian(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie2[i] = new TShowSerie(SerieLetta, 512, 120 * i, 256, 120);
          visSerie2[i].setVisible(true);
        }
        for (i = 6; i < segnali.length; i++) {
          SerieLetta = caso.getMedian(i);
          SerieLetta.setName("Paziente " + String.valueOf(paziente) +
                             " - N. Dialisi " + String.valueOf(dialisi) +
                             " - Segnale " + String.valueOf(segnali[i]));
          visSerie2[i] = new TShowSerie(SerieLetta, 512 + 256, 120 * (i - 6),
                                        256, 120);
          visSerie2[i].setVisible(true);
        }
      }
      catch (IOException exc) {
      }
    }
  }

  public void jMenuItem18_actionPerformed(ActionEvent e) {
    double T1, T2, T3, T4;
    int paziente, dialisi, prec, succ;

    MacroCaseKnn ricerca;
    TLinkedList risultati;
    TResultNode Result;
    String riga;
    int count, i;

    paziente = getPaziente();
    dialisi = getDialisi();
    prec = getPrec();
    succ = getSucc();

    if ( (paziente != -1) && (dialisi != -1) && ( (succ + prec) != 0)) {
      System.out.println(
          "Impostazione del Retrieval: Preparazione dei macrocasi");

      ricerca = new MacroCaseKnn(paziente, dialisi, prec, succ, weights, loader,
                                 interpolazione.isSelected());
      count = list2.getItemCount();
      for (i = 0; i < count; i++) {
        riga = list2.getItem(i);
        paziente = getPaziente(riga);
        dialisi = getDialisi(riga);
        if ( (paziente != -1) && (dialisi != -1))
          ricerca.addMacroCase(paziente, dialisi);
      }

      System.out.println("Esecuzione Retrieval Macrocasi:");
      T1 = java.lang.System.currentTimeMillis();

      risultati = ricerca.calculate(range);
      Result = risultati.getFirst();

      T2 = java.lang.System.currentTimeMillis();
      list1.removeAll();

      while (Result != null) {
        paziente = Result.getLinkPaziente();
        dialisi = Result.getLinkDialisi();
        riga = String.valueOf(paziente) + " 0 " +
            String.valueOf(dialisi) + "   " + String.valueOf(Result.getDistance());
        list1.add(riga);
//          System.out.print(paziente + " - ");
//          System.out.print(dialisi + " - ");
//          System.out.println(Result.getDistance());
        Result = risultati.getNext();
      }

      riga = "Tempo di esecuzione del Retrieval: " + (T2 - T1);

      Tempo1.setText(String.valueOf(T2 - T1));
    }
  }

  public void jMenuItem19_actionPerformed(ActionEvent e) {
    int paziente, dialisi, I,j;
    int X, Y;
    RetrievedContext context;
    Template template;
    TCase caso;
    TQueriesXml queryXml;
    SICollection sInst;
    TemporalAbstractionModule module = new TemporalAbstractionModule(loader, templateLoader);

    paziente= getPaziente();
    dialisi= getDialisi();
    caso = new TCase(segnali.length, paziente, dialisi, soglie);

    context= module.retrieveSimilarContext(paziente, dialisi);
    if(context != null) {
      System.out.println("Contesto recuperato: " + context.getContextId());
      System.out.println("Template associato: " + context.getTemplateId());

      try {
        template= templateLoader.loadCompleteTemplate(context.getTemplateId());
        loader.loadCase(caso, interpolazione.isSelected());
        System.out.println("Template caricato");

        queryXml = new TQueriesXml(caso, template);
        System.out.println("Oggetto QueryXml creato");

        sInst = new SICollection(segnali.length, paziente,dialisi);

        for(j=0; j < segnali.length; j++){
            sInst.setSignalInstance(j, queryXml.getSignalInstance(j));
            System.out.println("Trasformazione effettuata");


        //  visualizzazione dei risultati


            for (I= 0; I < sInst.getSignalInstance(j).getStates().size(); I++) {
                StateInstance state = sInst.getSignalInstance(j).getStateInstance(I);

                System.out.println(state.toString());
            }
            for (I= 0; I < sInst.getSignalInstance(j).getTrends().size(); I++) {
                TrendInstance state = sInst.getSignalInstance(j).getTrendInstance(I);

                System.out.println(state.toString());
            }
            for (I= 0; I < sInst.getSignalInstance(j).getStationarities().size(); I++) {
                StationaryInstance state = sInst.getSignalInstance(j).getStationaryInstance(I);

                System.out.println(state.toString());
            }

            //checkInstances check = new checkInstances();
            sInst.getTrendInstances(j).check(sInst.getSignalInstance(j).getTrends(), 1);
            //check.check(sInst.arSignal[j].trendInstances, 1);
            // visualizzazione degli overlaps dei trends
            System.out.println("Check/overlaps sui trends effettuato");

            System.out.println("Numero di overlaps: " + sInst.getTrendInstances(j).getNumOverlaps());

            for (I= 0; I < sInst.getTrendInstances(j).getNumOverlaps(); I++) {
                System.out.println("   Overlap numero " + I + " : ");
                System.out.println("      Tipo di overlap : " + sInst.getTrendInstances(j).getOverlayType(I));
                System.out.println("      Inizio : " + sInst.getTrendInstances(j).getFirstInterval(I) +
                             " - Fine : " + sInst.getTrendInstances(j).getSecondInterval(I));
                }

            sInst.getStateInstances(j).check(sInst.getSignalInstance(j).getStates(), 100);
            // visualizzazione degli overlaps degli states
            System.out.println("Check/overlaps sugli states effettuato");

            System.out.println("Numero di overlaps: " + sInst.getStateInstances(j).getNumOverlaps());

            for (I= 0; I < sInst.getStateInstances(j).getNumOverlaps(); I++) {
                System.out.println("   Overlap numero " + I + " : ");
                System.out.println("      Tipo di overlap : " + sInst.getStateInstances(j).getOverlayType(I));
                System.out.println("      Inizio : " + sInst.getStateInstances(j).getFirstInterval(I) +
                             " - Fine : " + sInst.getStateInstances(j).getSecondInterval(I));
            }

            JointModule jModule = new JointModule (sInst.getSignalInstance(j));
            jModule.createJoints();

            for (I= 0; I < sInst.getSignalInstance(j).getStationarities().size(); I++) {
                JointInstance state = sInst.getSignalInstance(j).getJointInstance(I);

                System.out.println(state.toString());
            }

            X= (j % 4) * 320;
            Y= (j / 4) * 250;
            ShowAbstractions showAbstractions = new ShowAbstractions (caso.getSerie(j), sInst.getSignalInstance(j),
                                                      template.getSignalTemplate(j), X, Y, 320, 250);
            showAbstractions.setVisible(true);

        }

            } catch (SQLException ex) {
            ex.printStackTrace();
            } catch (java.io.IOException ex2) {
            ex2.printStackTrace();
            } catch (java.lang.Exception ex3) {
            ex3.printStackTrace();
            }
    }
    else System.out.println("Nessun contesto recuperato");
  }

  public void jMenuItem20_actionPerformed(ActionEvent e) {
    int paziente, sessione, I, J, numeroRighe;
    int[] sessioni;
    TCase caso;
    Statement stat;
    String sqlCommand;
    TTimeSerie TS;
    DBConnection connection2 = new DBConnection();

    // per ogni caso nel db:
    //     caricamento del caso
    //     insert into nuovo_db solo di paziente e caso
    //     per ogni TS:
    //          update di nuovo_db dato paziente e caso, con feature dinamica= TS.toString()

    try {
      connection2.connectToDB(driver, url, userName, password, dbase,
                       connectionString);
      stat = connection2.getConnection().createStatement();

      for (I = 0; I < pazienti.length; I++) {
        paziente = pazienti[I];

        connection.RunQuery("SELECT ssessionid FROM r_ssession WHERE r_ssession.patientid = '"
        + paziente + "' ORDER BY ssessionid");
        numeroRighe= 0;
        while (connection.nextRecord()) {
          numeroRighe++;
        }
        sessioni= new int[numeroRighe];

        connection.RunQuery("SELECT ssessionid FROM r_ssession WHERE r_ssession.patientid = '"
            + paziente + "' ORDER BY ssessionid");
        J= 0;
        while (connection.nextRecord()) {
          sessioni[J]= connection.getRowValueInt("ssessionid");
          J++;
        }

        for (numeroRighe= 0; numeroRighe < sessioni.length; numeroRighe++) {
          sessione = sessioni[numeroRighe];
          caso = new TCase(segnali.length, paziente, sessione, soglie);
          loader.loadCase(caso, interpolazione.isSelected());

          sqlCommand = "INSERT INTO jcolibri (paziente, sessione) ";
          sqlCommand = sqlCommand + "VALUES ('" + Integer.toString(paziente);
          sqlCommand = sqlCommand + "', '" + Integer.toString(sessione) + "')";

          stat.executeUpdate(sqlCommand);

          for (J = 0; J < caso.getNumSignals(); J++) {
            TS = caso.getSerie(J);

            sqlCommand = "UPDATE jcolibri ";
            sqlCommand = sqlCommand + "SET segnale_" +
                Integer.toString(segnali[J]);
            sqlCommand = sqlCommand + " = '" + TS.toString();
            sqlCommand = sqlCommand + "' WHERE paziente = " +
                Integer.toString(paziente);
            sqlCommand = sqlCommand + " AND sessione = " +
                Integer.toString(sessione);

            stat.executeUpdate(sqlCommand);
          }
        }
      }
      stat.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  class RHENE_jMenuItem20_actionAdapter
      implements ActionListener {
    private RHENE adaptee;
    RHENE_jMenuItem20_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem20_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem18_actionAdapter
      implements ActionListener {
    private RHENE adaptee;
    RHENE_jMenuItem18_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem18_actionPerformed(e);
    }
  }

  class RHENE_visMacroCaso2_actionAdapter
      implements ActionListener {
    private RHENE adaptee;
    RHENE_visMacroCaso2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.visMacroCaso2_actionPerformed(e);
    }
  }

  class RHENE_visMacroCaso1_actionAdapter
      implements ActionListener {
    private RHENE adaptee;
    RHENE_visMacroCaso1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.visMacroCaso1_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem1_actionPerformed(e);
    }
  }

  class RHENE_this_windowAdapter
      extends java.awt.event.WindowAdapter {
    RHENE adaptee;

    RHENE_this_windowAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
      adaptee.this_windowClosing(e);
    }
  }

  class RHENE_jMenuItem2_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem2_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem5_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem5_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem5_actionPerformed(e);
    }
  }

  class RHENE_Visualizza1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_Visualizza1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.Visualizza1_actionPerformed(e);
    }
  }

  class RHENE_Visualizza2_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_Visualizza2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.Visualizza2_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem3_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem3_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem3_actionPerformed(e);
    }
  }

  class RHENE_Paziente1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_Paziente1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.Paziente1_actionPerformed(e);
    }
  }

  class RHENE_normalizzata1_itemAdapter
      implements java.awt.event.ItemListener {
    RHENE adaptee;

    RHENE_normalizzata1_itemAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
      adaptee.normalizzata1_itemStateChanged(e);
    }
  }

  class RHENE_jMenuItem4_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem4_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem4_actionPerformed(e);
    }
  }

  class RHENE_visCaso1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_visCaso1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.visCaso1_actionPerformed(e);
    }
  }

  class RHENE_visCaso2_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_visCaso2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.visCaso2_actionPerformed(e);
    }
  }

  class RHENE_normalizzata1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_normalizzata1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.normalizzata1_actionPerformed(e);
    }
  }

  class RHENE_chiudiCaso_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_chiudiCaso_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.chiudiCaso_actionPerformed(e);
    }
  }

  class RHENE_chiudiCaso2_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_chiudiCaso2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.chiudiCaso2_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem6_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem6_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem6_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem7_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem7_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem7_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem8_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem8_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem8_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem9_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem9_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem9_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem14_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem14_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem14_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem15_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem15_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem15_actionPerformed(e);
    }
  }

  class RHENE_coeffourier_focusAdapter
      extends java.awt.event.FocusAdapter {
    RHENE adaptee;

    RHENE_coeffourier_focusAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
      adaptee.coeffourier_focusLost(e);
    }
  }

  class RHENE_figlipernodo_focusAdapter
      extends java.awt.event.FocusAdapter {
    RHENE adaptee;

    RHENE_figlipernodo_focusAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
      adaptee.figlipernodo_focusLost(e);
    }
  }

  class RHENE_dimensioniattive_focusAdapter
      extends java.awt.event.FocusAdapter {
    RHENE adaptee;

    RHENE_dimensioniattive_focusAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
      adaptee.dimensioniattive_focusLost(e);
    }
  }

  class RHENE_jMenuItemSetup1_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItemSetup1_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItemSetup1_actionPerformed(e);
    }
  }

  class RHENE_jMenuItemSetup2_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItemSetup2_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItemSetup2_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem16_actionAdapter
      implements java.awt.event.ActionListener {
    RHENE adaptee;

    RHENE_jMenuItem16_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem16_actionPerformed(e);
    }
  }

  class RHENE_jMenuItem19_actionAdapter
      implements ActionListener {
    private RHENE adaptee;
    RHENE_jMenuItem19_actionAdapter(RHENE adaptee) {
      this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
      adaptee.jMenuItem19_actionPerformed(e);
    }
  }
}

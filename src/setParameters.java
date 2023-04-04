import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class setParameters extends JFrame {
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  JLabel jLabel1 = new JLabel();
  JTextField Coefficienti = new JTextField();
  JPanel jPanel2 = new JPanel();
  Border border2;
  TitledBorder titledBorder2;
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField Soglia1 = new JTextField();
  JTextField Soglia2 = new JTextField();
  JTextField Soglia3 = new JTextField();
  JTextField Soglia4 = new JTextField();
  JTextField Soglia5 = new JTextField();
  JTextField Soglia6 = new JTextField();
  JTextField Soglia7 = new JTextField();
  JTextField Soglia8 = new JTextField();
  JTextField Soglia9 = new JTextField();
  JTextField Soglia10 = new JTextField();
  JTextField Soglia11 = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JTextField Peso6 = new JTextField();
  JLabel jLabel15 = new JLabel();
  JLabel jLabel16 = new JLabel();
  JLabel jLabel17 = new JLabel();
  JTextField Peso9 = new JTextField();
  JTextField Peso7 = new JTextField();
  JLabel jLabel18 = new JLabel();
  JLabel jLabel19 = new JLabel();
  JTextField Peso3 = new JTextField();
  JLabel jLabel20 = new JLabel();
  JLabel jLabel110 = new JLabel();
  JTextField Peso8 = new JTextField();
  JPanel jPanel3 = new JPanel();
  JTextField Peso5 = new JTextField();
  JLabel jLabel21 = new JLabel();
  JTextField Peso11 = new JTextField();
  JTextField Peso10 = new JTextField();
  JLabel jLabel111 = new JLabel();
  JLabel jLabel22 = new JLabel();
  JTextField Peso2 = new JTextField();
  JLabel jLabel23 = new JLabel();
  JTextField Peso1 = new JTextField();
  JTextField Peso4 = new JTextField();
  JLabel jLabel112 = new JLabel();
  JLabel jLabel24 = new JLabel();
  Border border3;
  TitledBorder titledBorder3;
  JPanel jPanel4 = new JPanel();
  Border border4;
  TitledBorder titledBorder4;
  JLabel jLabel25 = new JLabel();
  JTextField Paziente = new JTextField();
  JButton aggiungi = new JButton();
  List pazienti = new List();
  JLabel jLabel26 = new JLabel();
  JLabel jLabel27 = new JLabel();
  JButton cancella = new JButton();
  JButton OK = new JButton();
  JButton Annulla = new JButton();
  JPanel jPanel5 = new JPanel();
  TitledBorder titledBorder5;
  JLabel jLabel28 = new JLabel();
  JLabel jLabel29 = new JLabel();
  JTextField Peso12 = new JTextField();
  JPanel jPanel6 = new JPanel();
  JLabel jLabel210 = new JLabel();
  JTextField Peso13 = new JTextField();
  JLabel jLabel211 = new JLabel();
  JTextField Peso14 = new JTextField();
  JTextField Peso15 = new JTextField();
  JTextField Peso16 = new JTextField();
  JLabel jLabel113 = new JLabel();
  JLabel jLabel114 = new JLabel();
  JLabel jLabel115 = new JLabel();
  JLabel jLabel116 = new JLabel();
  JTextField Peso17 = new JTextField();
  JLabel jLabel117 = new JLabel();
  JLabel jLabel118 = new JLabel();
  JTextField Peso18 = new JTextField();
  JLabel jLabel212 = new JLabel();
  JTextField Peso19 = new JTextField();
  JLabel jLabel119 = new JLabel();
  JTextField Peso20 = new JTextField();
  JLabel jLabel120 = new JLabel();
  JTextField Peso110 = new JTextField();
  JTextField Peso21 = new JTextField();
  JLabel jLabel30 = new JLabel();
  JTextField Somiglianza4 = new JTextField();
  JLabel jLabel31 = new JLabel();
  JLabel jLabel121 = new JLabel();
  JTextField Somiglianza1 = new JTextField();
  JLabel jLabel122 = new JLabel();
  JTextField Somiglianza9 = new JTextField();
  JLabel jLabel32 = new JLabel();
  JTextField Somiglianza10 = new JTextField();
  JLabel jLabel33 = new JLabel();
  JTextField Somiglianza2 = new JTextField();
  JLabel jLabel123 = new JLabel();
  JTextField Somiglianza3 = new JTextField();
  JLabel jLabel34 = new JLabel();
  JTextField Somiglianza8 = new JTextField();
  JLabel jLabel124 = new JLabel();
  JLabel jLabel35 = new JLabel();
  JTextField Somiglianza5 = new JTextField();
  JTextField Somiglianza6 = new JTextField();
  JLabel jLabel36 = new JLabel();
  JTextField Somiglianza7 = new JTextField();
  JTextField Somiglianza11 = new JTextField();
  JLabel jLabel125 = new JLabel();
  JLabel jLabel37 = new JLabel();
  JButton Salva = new JButton();
  JLabel jLabel38 = new JLabel();
  JTextField numFigli = new JTextField();
  JLabel jLabel39 = new JLabel();
  JTextField dimAttive = new JTextField();
  JPanel jPanel7 = new JPanel();
  JTextField url = new JTextField();
  JLabel jLabel310 = new JLabel();
  JLabel jLabel40 = new JLabel();
  JTextField userName = new JTextField();
  JLabel jLabel311 = new JLabel();
  JTextField dbname = new JTextField();
  JLabel jLabel41 = new JLabel();
  JLabel jLabel42 = new JLabel();
  JPasswordField password = new JPasswordField();
  Border border5;
  TitledBorder titledBorder6;
  JComboBox dbDriver = new JComboBox();

  RandomAccessFile file;
  int numSegnali= 11;
  int numCoefficients, numPazienti;
  int[] soglia = new int[numSegnali];
  double[] maxval = new double[numSegnali];
  int[] peso = new int[numSegnali];
  int[] codPazienti;
  int numFigliPerNodo, alpha;
  String user, pwd, host, nomeDB, driverDB, connectionString, driverName;

  public setParameters() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws HeadlessException {
    setParameters setParameters1 = new setParameters();

    setParameters1.carica();
    setParameters1.setBounds(100, 10, 565, 560);
    setParameters1.setVisible(true);
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140))," Configurazione K-d Tree / TV Tree : ");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140))," Soglie minime di punti per considerare una serie valida : ");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(border3," Pesi per Calcolo delle Distanze : ");
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border4," Codici dei Pazienti : ");
    titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140))," Somiglianza richiesta alle Serie (0 = max. somiglianza; 1 = considera tutte le Serie) ");
    border5 = BorderFactory.createEmptyBorder();
    titledBorder6 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140))," Configurazione Accesso al DB");
    jLabel24.setText("PA");
    jLabel24.setBounds(new Rectangle(141, 27, 28, 15));
    jLabel112.setText("PS");
    jLabel112.setBounds(new Rectangle(330, 26, 28, 15));
    Peso4.setText("");
    Peso4.setBounds(new Rectangle(211, 48, 37, 21));
    Peso1.setText("");
    Peso1.setBounds(new Rectangle(98, 48, 37, 21));
    jLabel23.setText("PV");
    jLabel23.setBounds(new Rectangle(178, 26, 28, 15));
    Peso2.setText("");
    Peso2.setBounds(new Rectangle(135, 48, 37, 21));
    jLabel22.setText("PT");
    jLabel22.setBounds(new Rectangle(215, 26, 28, 15));
    jLabel111.setText("PD");
    jLabel111.setBounds(new Rectangle(369, 26, 28, 15));
    Peso10.setText("");
    Peso10.setBounds(new Rectangle(436, 48, 37, 21));
    Peso11.setText("");
    Peso11.setBounds(new Rectangle(473, 48, 37, 21));
    jLabel21.setText("FC");
    jLabel21.setBounds(new Rectangle(292, 26, 28, 15));
    Peso5.setText("");
    Peso5.setBounds(new Rectangle(249, 48, 37, 21));
    jPanel3.setBorder(titledBorder3);
    jPanel3.setDebugGraphicsOptions(0);
    jPanel3.setBounds(new Rectangle(14, 300, 527, 79));
    jPanel3.setLayout(null);
    Peso8.setText("");
    Peso8.setBounds(new Rectangle(361, 48, 37, 21));
    jLabel110.setBounds(new Rectangle(477, 26, 28, 15));
    jLabel110.setText("VE");
    jLabel20.setText("Codice Serie : ");
    jLabel20.setBounds(new Rectangle(12, 25, 96, 15));
    Peso3.setText("");
    Peso3.setBounds(new Rectangle(173, 48, 37, 21));
    jLabel19.setBounds(new Rectangle(441, 25, 28, 15));
    jLabel19.setText("HB");
    jLabel18.setText("Pesi : ");
    jLabel18.setBounds(new Rectangle(12, 49, 73, 15));
    Peso7.setText("");
    Peso7.setBounds(new Rectangle(324, 48, 37, 21));
    Peso9.setText("");
    Peso9.setBounds(new Rectangle(399, 48, 37, 21));
    jLabel17.setBounds(new Rectangle(405, 25, 28, 15));
    jLabel17.setText("CD");
    jLabel16.setBounds(new Rectangle(254, 26, 28, 15));
    jLabel16.setText("QF");
    jLabel15.setText("QB");
    jLabel15.setBounds(new Rectangle(105, 27, 28, 15));
    Peso6.setText("");
    Peso6.setBounds(new Rectangle(287, 48, 37, 21));
    jLabel14.setText("VE");
    jLabel14.setBounds(new Rectangle(476, 26, 28, 15));
    jLabel13.setText("HB");
    jLabel13.setBounds(new Rectangle(440, 25, 28, 15));
    jLabel12.setText("CD");
    jLabel12.setBounds(new Rectangle(404, 25, 28, 15));
    jLabel11.setText("PD");
    jLabel11.setBounds(new Rectangle(368, 26, 28, 15));
    jLabel10.setText("PS");
    jLabel10.setBounds(new Rectangle(329, 26, 28, 15));
    jLabel9.setText("FC");
    jLabel9.setBounds(new Rectangle(291, 26, 28, 15));
    jLabel8.setText("QF");
    jLabel8.setBounds(new Rectangle(253, 26, 28, 15));
    jLabel7.setText("PT");
    jLabel7.setBounds(new Rectangle(214, 26, 28, 15));
    jLabel6.setText("PV");
    jLabel6.setBounds(new Rectangle(177, 26, 28, 15));
    jLabel5.setText("PA");
    jLabel5.setBounds(new Rectangle(140, 27, 28, 15));
    Soglia11.setText("");
    Soglia11.setBounds(new Rectangle(472, 48, 37, 21));
    Soglia10.setText("");
    Soglia10.setBounds(new Rectangle(435, 48, 37, 21));
    Soglia9.setText("");
    Soglia9.setBounds(new Rectangle(398, 48, 37, 21));
    Soglia8.setText("");
    Soglia8.setBounds(new Rectangle(360, 48, 37, 21));
    Soglia7.setText("");
    Soglia7.setBounds(new Rectangle(323, 48, 37, 21));
    Soglia6.setText("");
    Soglia6.setBounds(new Rectangle(286, 48, 37, 21));
    Soglia5.setText("");
    Soglia5.setBounds(new Rectangle(248, 48, 37, 21));
    Soglia4.setText("");
    Soglia4.setBounds(new Rectangle(210, 48, 37, 21));
    Soglia3.setText("");
    Soglia3.setBounds(new Rectangle(172, 48, 37, 21));
    Soglia2.setText("");
    Soglia2.setBounds(new Rectangle(134, 48, 37, 21));
    Soglia1.setText("");
    Soglia1.setBounds(new Rectangle(97, 48, 37, 21));
    jLabel4.setText("QB");
    jLabel4.setBounds(new Rectangle(104, 27, 28, 15));
    jLabel3.setText("Soglie : ");
    jLabel3.setBounds(new Rectangle(11, 49, 73, 15));
    jLabel2.setText("Codice Serie : ");
    jLabel2.setBounds(new Rectangle(11, 25, 96, 15));
    jPanel2.setBorder(titledBorder2);
    jPanel2.setBounds(new Rectangle(13, 221, 527, 79));
    jPanel2.setLayout(null);
    Coefficienti.setText("3");
    Coefficienti.setBounds(new Rectangle(224, 24, 32, 21));
    jLabel1.setText("Numero di Coefficienti per le trasformate :");
    jLabel1.setBounds(new Rectangle(13, 25, 206, 15));
    this.setTitle("Settaggio Parametri");
    this.addWindowListener(new setParameters_this_windowAdapter(this));
    this.getContentPane().setLayout(null);
    jPanel1.setBorder(titledBorder1);
    jPanel1.setBounds(new Rectangle(14, 145, 525, 79));
    jPanel1.setLayout(null);
    jPanel4.setEnabled(false);
    jPanel4.setBorder(titledBorder4);
    jPanel4.setBounds(new Rectangle(15, 626, 527, 118));
    jPanel4.setLayout(null);
    jLabel25.setText("Codice Paziente : ");
    jLabel25.setBounds(new Rectangle(17, 18, 119, 15));
    Paziente.setText("");
    Paziente.setBounds(new Rectangle(142, 18, 57, 21));
    aggiungi.setBounds(new Rectangle(51, 50, 149, 28));
    aggiungi.setText("Aggiungi Paziente");
    aggiungi.addActionListener(new setParameters_aggiungi_actionAdapter(this));
    pazienti.setBounds(new Rectangle(377, 19, 136, 86));
    jLabel26.setText("Pazienti");
    jLabel26.setBounds(new Rectangle(294, 23, 79, 15));
    jLabel27.setText("presenti :");
    jLabel27.setBounds(new Rectangle(294, 59, 80, 15));
    cancella.setBounds(new Rectangle(50, 82, 149, 26));
    cancella.setText("Annulla Pazienti");
    cancella.addActionListener(new setParameters_cancella_actionAdapter(this));
    OK.setBounds(new Rectangle(208, 483, 148, 31));
    OK.setText("Conferma ed esci");
    OK.setVerticalTextPosition(SwingConstants.CENTER);
    OK.addActionListener(new setParameters_OK_actionAdapter(this));
    Annulla.setBounds(new Rectangle(388, 483, 147, 31));
    Annulla.setText("Annulla ed esci");
    Annulla.addActionListener(new setParameters_Annulla_actionAdapter(this));
    jPanel5.setBorder(titledBorder5);
    jPanel5.setBounds(new Rectangle(14, 378, 528, 89));
    jPanel5.setLayout(null);
    jLabel28.setBounds(new Rectangle(292, 29, 28, 15));
    jLabel28.setText("FC");
    jLabel29.setBounds(new Rectangle(12, 28, 96, 15));
    jLabel29.setText("Codice Serie : ");
    Peso12.setBounds(new Rectangle(135, 51, 37, 21));
    Peso12.setText("");
    jPanel6.setLayout(null);
    jPanel6.setDebugGraphicsOptions(0);
    jPanel6.setBounds(new Rectangle(263, 26, 1, 1));
    jPanel6.setBorder(titledBorder3);
    jLabel210.setText("PA");
    jLabel210.setBounds(new Rectangle(141, 30, 28, 15));
    Peso13.setText("");
    Peso13.setBounds(new Rectangle(324, 51, 37, 21));
    jLabel211.setText("PV");
    jLabel211.setBounds(new Rectangle(178, 29, 28, 15));
    Peso14.setText("");
    Peso14.setBounds(new Rectangle(361, 51, 37, 21));
    Peso15.setText("");
    Peso15.setBounds(new Rectangle(249, 51, 37, 21));
    Peso16.setText("");
    Peso16.setBounds(new Rectangle(287, 51, 37, 21));
    jLabel113.setBounds(new Rectangle(441, 28, 28, 15));
    jLabel113.setText("HB");
    jLabel114.setText("PD");
    jLabel114.setBounds(new Rectangle(369, 29, 28, 15));
    jLabel115.setBounds(new Rectangle(254, 29, 28, 15));
    jLabel115.setText("QF");
    jLabel116.setText("PS");
    jLabel116.setBounds(new Rectangle(330, 29, 28, 15));
    Peso17.setText("");
    Peso17.setBounds(new Rectangle(436, 51, 37, 21));
    jLabel117.setText("Pesi : ");
    jLabel117.setBounds(new Rectangle(12, 52, 73, 15));
    jLabel118.setBounds(new Rectangle(405, 28, 28, 15));
    jLabel118.setText("CD");
    Peso18.setText("");
    Peso18.setBounds(new Rectangle(173, 51, 37, 21));
    jLabel212.setText("PT");
    jLabel212.setBounds(new Rectangle(215, 29, 28, 15));
    Peso19.setText("");
    Peso19.setBounds(new Rectangle(399, 51, 37, 21));
    jLabel119.setBounds(new Rectangle(477, 29, 28, 15));
    jLabel119.setText("VE");
    Peso20.setText("");
    Peso20.setBounds(new Rectangle(211, 51, 37, 21));
    jLabel120.setText("QB");
    jLabel120.setBounds(new Rectangle(105, 30, 28, 15));
    Peso110.setText("");
    Peso110.setBounds(new Rectangle(473, 51, 37, 21));
    Peso21.setText("");
    Peso21.setBounds(new Rectangle(98, 51, 37, 21));
    jLabel30.setBounds(new Rectangle(256, 31, 28, 15));
    jLabel30.setText("QF");
    Somiglianza4.setBounds(new Rectangle(213, 53, 37, 21));
    Somiglianza4.setText("");
    jLabel31.setBounds(new Rectangle(107, 32, 28, 15));
    jLabel31.setText("QB");
    jLabel121.setBounds(new Rectangle(407, 30, 28, 15));
    jLabel121.setText("CD");
    Somiglianza1.setBounds(new Rectangle(100, 53, 37, 21));
    Somiglianza1.setText("");
    jLabel122.setBounds(new Rectangle(443, 30, 28, 15));
    jLabel122.setText("HB");
    Somiglianza9.setBounds(new Rectangle(401, 53, 37, 21));
    Somiglianza9.setText("");
    jLabel32.setBounds(new Rectangle(13, 55, 73, 15));
    jLabel32.setText("Somiglianza : ");
    Somiglianza10.setBounds(new Rectangle(438, 53, 37, 21));
    Somiglianza10.setText("");
    jLabel33.setBounds(new Rectangle(14, 30, 96, 15));
    jLabel33.setText("Codice Serie : ");
    Somiglianza2.setBounds(new Rectangle(137, 53, 37, 21));
    Somiglianza2.setText("");
    jLabel123.setBounds(new Rectangle(479, 31, 28, 15));
    jLabel123.setText("VE");
    Somiglianza3.setBounds(new Rectangle(175, 53, 37, 21));
    Somiglianza3.setText("");
    jLabel34.setBounds(new Rectangle(294, 31, 28, 15));
    jLabel34.setText("FC");
    Somiglianza8.setBounds(new Rectangle(363, 53, 37, 21));
    Somiglianza8.setText("");
    jLabel124.setBounds(new Rectangle(371, 31, 28, 15));
    jLabel124.setText("PD");
    jLabel35.setBounds(new Rectangle(217, 31, 28, 15));
    jLabel35.setText("PT");
    Somiglianza5.setBounds(new Rectangle(251, 53, 37, 21));
    Somiglianza5.setText("");
    Somiglianza6.setBounds(new Rectangle(289, 53, 37, 21));
    Somiglianza6.setText("");
    jLabel36.setBounds(new Rectangle(180, 31, 28, 15));
    jLabel36.setText("PV");
    Somiglianza7.setBounds(new Rectangle(326, 53, 37, 21));
    Somiglianza7.setText("");
    Somiglianza11.setBounds(new Rectangle(475, 53, 37, 21));
    Somiglianza11.setText("");
    jLabel125.setBounds(new Rectangle(332, 31, 28, 15));
    jLabel125.setText("PS");
    jLabel37.setBounds(new Rectangle(143, 32, 28, 15));
    jLabel37.setText("PA");
    Salva.setBounds(new Rectangle(16, 483, 148, 31));
    Salva.setText("Conferma");
    Salva.addActionListener(new setParameters_Salva_actionAdapter(this));
    jLabel38.setBounds(new Rectangle(297, 25, 179, 15));
    jLabel38.setText("Numero di Figli per Nodo (TV Tree) :");
    numFigli.setBounds(new Rectangle(479, 23, 32, 21));
    numFigli.setText("3");
    jLabel39.setBounds(new Rectangle(283, 49, 193, 15));
    jLabel39.setText("Numero di dimensioni attive (TV Tree) :");
    dimAttive.setBounds(new Rectangle(479, 47, 32, 21));
    dimAttive.setText("3");
    jPanel7.setLayout(null);
    jPanel7.setBorder(titledBorder6);
    jPanel7.setDebugGraphicsOptions(0);
    jPanel7.setBounds(new Rectangle(15, 1, 525, 146));
    url.setBounds(new Rectangle(181, 67, 334, 21));
    url.setText("localhost");
    jLabel310.setBounds(new Rectangle(111, 69, 71, 15));
    jLabel310.setText("URL :");
    jLabel40.setText("Username :");
    jLabel40.setBounds(new Rectangle(81, 22, 99, 15));
    userName.setText("root");
    userName.setBounds(new Rectangle(181, 20, 334, 21));
    jLabel311.setBounds(new Rectangle(84, 48, 97, 15));
    jLabel311.setText("Password :");
    dbname.setBounds(new Rectangle(181, 91, 334, 21));
    dbname.setText("esrd3");
    jLabel41.setBounds(new Rectangle(70, 93, 113, 15));
    jLabel41.setText("Nome del DB :");
    jLabel42.setBounds(new Rectangle(42, 120, 138, 15));
    jLabel42.setText("Driver da utilizzare :");
    password.setText("");
    password.setBounds(new Rectangle(181, 44, 334, 20));
    dbDriver.setToolTipText("");
    dbDriver.setEditor(null);
    dbDriver.setBounds(new Rectangle(180, 115, 334, 22));
    dbDriver.addItem("PostgreSQL");
    dbDriver.addItem("MySQL");
    jPanel1.add(Coefficienti, null);
    jPanel1.add(numFigli, null);
    jPanel1.add(dimAttive, null);
    jPanel1.add(jLabel39, null);
    jPanel1.add(jLabel38, null);
    jPanel1.add(jLabel1, null);
    jPanel2.add(Soglia2, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(Soglia1, null);
    jPanel2.add(jLabel5, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(Soglia3, null);
    jPanel2.add(jLabel7, null);
    jPanel2.add(Soglia4, null);
    jPanel2.add(jLabel8, null);
    jPanel2.add(Soglia5, null);
    jPanel2.add(jLabel9, null);
    jPanel2.add(Soglia6, null);
    jPanel2.add(jLabel10, null);
    jPanel2.add(Soglia7, null);
    jPanel2.add(jLabel11, null);
    jPanel2.add(Soglia8, null);
    jPanel2.add(jLabel12, null);
    jPanel2.add(Soglia9, null);
    jPanel2.add(jLabel13, null);
    jPanel2.add(Soglia10, null);
    jPanel2.add(jLabel14, null);
    jPanel2.add(Soglia11, null);
    jPanel3.add(Peso2, null);
    jPanel3.add(jLabel20, null);
    jPanel3.add(jLabel18, null);
    jPanel3.add(jLabel15, null);
    jPanel3.add(Peso1, null);
    jPanel3.add(jLabel24, null);
    jPanel3.add(jLabel23, null);
    jPanel3.add(Peso3, null);
    jPanel3.add(jLabel22, null);
    jPanel3.add(Peso4, null);
    jPanel3.add(jLabel16, null);
    jPanel3.add(Peso5, null);
    jPanel3.add(jLabel21, null);
    jPanel3.add(Peso6, null);
    jPanel3.add(jLabel112, null);
    jPanel3.add(Peso7, null);
    jPanel3.add(jLabel111, null);
    jPanel3.add(Peso8, null);
    jPanel3.add(jLabel17, null);
    jPanel3.add(Peso9, null);
    jPanel3.add(jLabel19, null);
    jPanel3.add(Peso10, null);
    jPanel3.add(jLabel110, null);
    jPanel3.add(Peso11, null);
    this.getContentPane().add(jPanel4, null);
    this.getContentPane().add(jPanel7, null);
    this.getContentPane().add(jPanel2, null);
    jPanel4.add(jLabel27, null);
    jPanel4.add(Paziente, null);
    jPanel4.add(jLabel25, null);
    jPanel4.add(jLabel26, null);
    jPanel4.add(pazienti, null);
    jPanel4.add(aggiungi, null);
    jPanel4.add(cancella, null);
    this.getContentPane().add(Annulla, null);
    this.getContentPane().add(OK, null);
    this.getContentPane().add(Salva, null);
    this.getContentPane().add(jPanel5, null);
    jPanel7.add(url, null);
    jPanel7.add(dbname, null);
    jPanel7.add(password, null);
    jPanel7.add(userName, null);
    jPanel7.add(jLabel40, null);
    jPanel7.add(jLabel311, null);
    jPanel7.add(jLabel310, null);
    jPanel7.add(jLabel41, null);
    jPanel7.add(jLabel42, null);
    jPanel7.add(dbDriver);
    this.getContentPane().add(jPanel1, null);
    jPanel6.add(jLabel29, null);
    jPanel6.add(jLabel117, null);
    jPanel6.add(Peso21, null);
    jPanel6.add(Peso12, null);
    jPanel6.add(Peso18, null);
    jPanel6.add(Peso20, null);
    jPanel6.add(Peso15, null);
    jPanel6.add(Peso16, null);
    jPanel6.add(Peso13, null);
    jPanel6.add(Peso14, null);
    jPanel6.add(Peso19, null);
    jPanel6.add(Peso17, null);
    jPanel6.add(Peso110, null);
    jPanel6.add(jLabel210, null);
    jPanel6.add(jLabel28, null);
    jPanel6.add(jLabel116, null);
    jPanel6.add(jLabel114, null);
    jPanel6.add(jLabel120, null);
    jPanel6.add(jLabel211, null);
    jPanel6.add(jLabel212, null);
    jPanel6.add(jLabel115, null);
    jPanel6.add(jLabel113, null);
    jPanel6.add(jLabel119, null);
    jPanel6.add(jLabel118, null);
    this.getContentPane().add(jPanel3, null);
    jPanel5.add(Somiglianza1, null);
    jPanel5.add(jLabel33, null);
    jPanel5.add(jLabel32, null);
    jPanel5.add(jLabel31, null);
    jPanel5.add(jLabel37, null);
    jPanel5.add(jLabel36, null);
    jPanel5.add(jLabel35, null);
    jPanel5.add(jLabel30, null);
    jPanel5.add(jLabel34, null);
    jPanel5.add(jLabel125, null);
    jPanel5.add(jLabel124, null);
    jPanel5.add(jLabel121, null);
    jPanel5.add(jLabel122, null);
    jPanel5.add(jLabel123, null);
    jPanel5.add(Somiglianza2, null);
    jPanel5.add(Somiglianza3, null);
    jPanel5.add(Somiglianza4, null);
    jPanel5.add(Somiglianza5, null);
    jPanel5.add(Somiglianza6, null);
    jPanel5.add(Somiglianza7, null);
    jPanel5.add(Somiglianza8, null);
    jPanel5.add(Somiglianza9, null);
    jPanel5.add(Somiglianza10, null);
    jPanel5.add(Somiglianza11, null);
    jPanel5.add(jPanel6, null);
  }

  public void carica () {
    try {
      int i;

      file = new RandomAccessFile ("coeffsoglia.dat", "rw");
      numCoefficients = file.readInt();
      for (i= 0; i < numSegnali; i++) {
        soglia[i] = file.readInt();
      }
      file.close();

      file = new RandomAccessFile ("pesi.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        peso[i] = file.readInt();
      }
      file.close();

/*      file = new RandomAccessFile ("pazienti.dat", "rw");
      numPazienti = file.readInt();
      codPazienti = new int[numPazienti];
      for (i= 0; i < numPazienti; i++) {
        codPazienti[i] = file.readInt();
      }
      file.close(); */

      file = new RandomAccessFile ("maxval.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        maxval[i] = file.readDouble();
      }
      file.close();

      file = new RandomAccessFile ("numAlpha.dat", "rw");
      alpha = file.readInt();
      file.close();

      file = new RandomAccessFile ("numFigliTV.dat", "rw");
      numFigliPerNodo = file.readInt();
      file.close();

      file = new RandomAccessFile ("DBConfig.dat", "rw");
      user = file.readUTF();
      pwd = file.readUTF();
      host = file.readUTF();
      nomeDB = file.readUTF();
      driverName= file.readUTF();
      driverDB= file.readUTF();
      connectionString= file.readUTF();
      file.close();


      Coefficienti.setText(String.valueOf(numCoefficients));
      numFigli.setText(String.valueOf(numFigliPerNodo));
      dimAttive.setText(String.valueOf(alpha));

      userName.setText(user);
      password.setText(pwd);
      url.setText(host);
      dbname.setText(nomeDB);
      dbDriver.setSelectedItem(driverName);

      Soglia1.setText(String.valueOf(soglia[0]));
      Soglia2.setText(String.valueOf(soglia[1]));
      Soglia3.setText(String.valueOf(soglia[2]));
      Soglia4.setText(String.valueOf(soglia[3]));
      Soglia5.setText(String.valueOf(soglia[4]));
      Soglia6.setText(String.valueOf(soglia[5]));
      Soglia7.setText(String.valueOf(soglia[6]));
      Soglia8.setText(String.valueOf(soglia[7]));
      Soglia9.setText(String.valueOf(soglia[8]));
      Soglia10.setText(String.valueOf(soglia[9]));
      Soglia11.setText(String.valueOf(soglia[10]));

      Peso1.setText(String.valueOf(peso[0]));
      Peso2.setText(String.valueOf(peso[1]));
      Peso3.setText(String.valueOf(peso[2]));
      Peso4.setText(String.valueOf(peso[3]));
      Peso5.setText(String.valueOf(peso[4]));
      Peso6.setText(String.valueOf(peso[5]));
      Peso7.setText(String.valueOf(peso[6]));
      Peso8.setText(String.valueOf(peso[7]));
      Peso9.setText(String.valueOf(peso[8]));
      Peso10.setText(String.valueOf(peso[9]));
      Peso11.setText(String.valueOf(peso[10]));

      Somiglianza1.setText(String.valueOf(maxval[0]));
      Somiglianza2.setText(String.valueOf(maxval[1]));
      Somiglianza3.setText(String.valueOf(maxval[2]));
      Somiglianza4.setText(String.valueOf(maxval[3]));
      Somiglianza5.setText(String.valueOf(maxval[4]));
      Somiglianza6.setText(String.valueOf(maxval[5]));
      Somiglianza7.setText(String.valueOf(maxval[6]));
      Somiglianza8.setText(String.valueOf(maxval[7]));
      Somiglianza9.setText(String.valueOf(maxval[8]));
      Somiglianza10.setText(String.valueOf(maxval[9]));
      Somiglianza11.setText(String.valueOf(maxval[10]));

/*      pazienti.removeAll();
      for (i= 0; i < numPazienti; i++) {
        pazienti.add(String.valueOf (codPazienti[i]));
      } */
    } catch (IOException e) {
    }
  }

  void cancella_actionPerformed(ActionEvent e) {
    pazienti.removeAll();
  }

  void aggiungi_actionPerformed(ActionEvent e) {
    int pazienteLetto;

    try {
      pazienteLetto= Integer.parseInt(Paziente.getText());

      pazienti.add(String.valueOf(pazienteLetto));
      Paziente.setText("");
    } catch (NumberFormatException exc) {
    }
  }

  void Annulla_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void OK_actionPerformed(ActionEvent e) {
    try {
      int i;
      String[] Temp;

      numCoefficients = Integer.parseInt (Coefficienti.getText());
      alpha = Integer.parseInt (dimAttive.getText());
      numFigliPerNodo = Integer.parseInt (numFigli.getText());

      soglia[0] = Integer.parseInt(Soglia1.getText());
      soglia[1] = Integer.parseInt(Soglia2.getText());
      soglia[2] = Integer.parseInt(Soglia3.getText());
      soglia[3] = Integer.parseInt(Soglia4.getText());
      soglia[4] = Integer.parseInt(Soglia5.getText());
      soglia[5] = Integer.parseInt(Soglia6.getText());
      soglia[6] = Integer.parseInt(Soglia7.getText());
      soglia[7] = Integer.parseInt(Soglia8.getText());
      soglia[8] = Integer.parseInt(Soglia9.getText());
      soglia[9] = Integer.parseInt(Soglia10.getText());
      soglia[10] = Integer.parseInt(Soglia11.getText());

      peso[0] = Integer.parseInt(Peso1.getText());
      peso[1] = Integer.parseInt(Peso2.getText());
      peso[2] = Integer.parseInt(Peso3.getText());
      peso[3] = Integer.parseInt(Peso4.getText());
      peso[4] = Integer.parseInt(Peso5.getText());
      peso[5] = Integer.parseInt(Peso6.getText());
      peso[6] = Integer.parseInt(Peso7.getText());
      peso[7] = Integer.parseInt(Peso8.getText());
      peso[8] = Integer.parseInt(Peso9.getText());
      peso[9] = Integer.parseInt(Peso10.getText());
      peso[10] = Integer.parseInt(Peso11.getText());

      maxval[0] = Double.parseDouble(Somiglianza1.getText());
      maxval[1] = Double.parseDouble(Somiglianza2.getText());
      maxval[2] = Double.parseDouble(Somiglianza3.getText());
      maxval[3] = Double.parseDouble(Somiglianza4.getText());
      maxval[4] = Double.parseDouble(Somiglianza5.getText());
      maxval[5] = Double.parseDouble(Somiglianza6.getText());
      maxval[6] = Double.parseDouble(Somiglianza7.getText());
      maxval[7] = Double.parseDouble(Somiglianza8.getText());
      maxval[8] = Double.parseDouble(Somiglianza9.getText());
      maxval[9] = Double.parseDouble(Somiglianza10.getText());
      maxval[10] = Double.parseDouble(Somiglianza11.getText());

/*      Temp = pazienti.getItems();
      numPazienti = Temp.length;
      codPazienti = new int[numPazienti];
      for (i= 0; i < numPazienti; i++) {
        codPazienti[i] = Integer.parseInt(Temp[i]);
      } */

      user= userName.getText();
      pwd= new String (password.getPassword());
      host= url.getText();
      nomeDB= dbname.getText();
      driverName= (String)dbDriver.getSelectedItem();
      if (driverName.compareTo("PostgreSQL") == 0) {
        driverDB= "org.postgresql.Driver";
        connectionString= "jdbc:postgresql://";
      } else {
        driverDB= "com.mysql.jdbc.Driver";
        connectionString= "jdbc:mysql://";
      }


      file = new RandomAccessFile ("numAlpha.dat", "rw");
      file.writeInt(alpha);
      file.close();

      file = new RandomAccessFile ("numFigliTV.dat", "rw");
      file.writeInt(numFigliPerNodo);
      file.close();

      file = new RandomAccessFile ("DBConfig.dat", "rw");
      file.writeUTF(user);
      file.writeUTF(pwd);
      file.writeUTF(host);
      file.writeUTF(nomeDB);
      file.writeUTF(driverName);
      file.writeUTF(driverDB);
      file.writeUTF(connectionString);
      file.close();

      file = new RandomAccessFile ("coeffsoglia.dat", "rw");
      file.writeInt (numCoefficients);
      for (i= 0; i < numSegnali; i++) {
        file.writeInt(soglia[i]);
      }
      file.close();

      file = new RandomAccessFile ("pesi.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        file.writeInt(peso[i]);
      }
      file.close();

/*      file = new RandomAccessFile ("pazienti.dat", "rw");
      numPazienti = codPazienti.length;
      file.writeInt(numPazienti);
      for (i= 0; i < numPazienti; i++) {
        file.writeInt(codPazienti[i]);
      }
      file.close(); */

      file = new RandomAccessFile ("maxval.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        file.writeDouble(maxval[i]);
      }
      file.close();

      this.dispose();
    } catch (IOException exc) {
      this.dispose();
    } catch (NumberFormatException exc) {
      this.dispose();
    }
  }

  void Salva_actionPerformed(ActionEvent e) {
    try {
      int i;
      String[] Temp;

      numCoefficients = Integer.parseInt (Coefficienti.getText());
      alpha = Integer.parseInt (dimAttive.getText());
      numFigliPerNodo = Integer.parseInt (numFigli.getText());

      soglia[0] = Integer.parseInt(Soglia1.getText());
      soglia[1] = Integer.parseInt(Soglia2.getText());
      soglia[2] = Integer.parseInt(Soglia3.getText());
      soglia[3] = Integer.parseInt(Soglia4.getText());
      soglia[4] = Integer.parseInt(Soglia5.getText());
      soglia[5] = Integer.parseInt(Soglia6.getText());
      soglia[6] = Integer.parseInt(Soglia7.getText());
      soglia[7] = Integer.parseInt(Soglia8.getText());
      soglia[8] = Integer.parseInt(Soglia9.getText());
      soglia[9] = Integer.parseInt(Soglia10.getText());
      soglia[10] = Integer.parseInt(Soglia11.getText());

      peso[0] = Integer.parseInt(Peso1.getText());
      peso[1] = Integer.parseInt(Peso2.getText());
      peso[2] = Integer.parseInt(Peso3.getText());
      peso[3] = Integer.parseInt(Peso4.getText());
      peso[4] = Integer.parseInt(Peso5.getText());
      peso[5] = Integer.parseInt(Peso6.getText());
      peso[6] = Integer.parseInt(Peso7.getText());
      peso[7] = Integer.parseInt(Peso8.getText());
      peso[8] = Integer.parseInt(Peso9.getText());
      peso[9] = Integer.parseInt(Peso10.getText());
      peso[10] = Integer.parseInt(Peso11.getText());

      maxval[0] = Double.parseDouble(Somiglianza1.getText());
      maxval[1] = Double.parseDouble(Somiglianza2.getText());
      maxval[2] = Double.parseDouble(Somiglianza3.getText());
      maxval[3] = Double.parseDouble(Somiglianza4.getText());
      maxval[4] = Double.parseDouble(Somiglianza5.getText());
      maxval[5] = Double.parseDouble(Somiglianza6.getText());
      maxval[6] = Double.parseDouble(Somiglianza7.getText());
      maxval[7] = Double.parseDouble(Somiglianza8.getText());
      maxval[8] = Double.parseDouble(Somiglianza9.getText());
      maxval[9] = Double.parseDouble(Somiglianza10.getText());
      maxval[10] = Double.parseDouble(Somiglianza11.getText());

/*      Temp = pazienti.getItems();
      numPazienti = Temp.length;
      codPazienti = new int[numPazienti];
      for (i= 0; i < numPazienti; i++) {
        codPazienti[i] = Integer.parseInt(Temp[i]);
      } */

      user= userName.getText();
      pwd= new String (password.getPassword());
      host= url.getText();
      nomeDB= dbname.getText();
      driverName= (String)dbDriver.getSelectedItem();
      if (driverName.compareTo("PostgreSQL") == 0) {
        driverDB= "org.postgresql.Driver";
        connectionString= "jdbc:postgresql://";
      } else {
        driverDB= "com.mysql.jdbc.Driver";
        connectionString= "jdbc:mysql://";
      }


      file = new RandomAccessFile ("numAlpha.dat", "rw");
      file.writeInt(alpha);
      file.close();

      file = new RandomAccessFile ("numFigliTV.dat", "rw");
      file.writeInt(numFigliPerNodo);
      file.close();

      file = new RandomAccessFile ("DBConfig.dat", "rw");
      file.writeUTF(user);
      file.writeUTF(pwd);
      file.writeUTF(host);
      file.writeUTF(nomeDB);
      file.writeUTF(driverName);
      file.writeUTF(driverDB);
      file.writeUTF(connectionString);
      file.close();

      file = new RandomAccessFile ("coeffsoglia.dat", "rw");
      file.writeInt (numCoefficients);
      for (i= 0; i < numSegnali; i++) {
        file.writeInt(soglia[i]);
      }
      file.close();

      file = new RandomAccessFile ("pesi.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        file.writeInt(peso[i]);
      }
      file.close();

/*      file = new RandomAccessFile ("pazienti.dat", "rw");
      numPazienti = codPazienti.length;
      file.writeInt(numPazienti);
      for (i= 0; i < numPazienti; i++) {
        file.writeInt(codPazienti[i]);
      }
      file.close(); */

      file = new RandomAccessFile ("maxval.dat", "rw");
      for (i= 0; i < numSegnali; i++) {
        file.writeDouble(maxval[i]);
      }
      file.close();
    } catch (IOException exc) {
    } catch (NumberFormatException exc) {
    }
  }

  void this_windowClosing(WindowEvent e) {
    this.dispose();
  }

}

class setParameters_cancella_actionAdapter implements java.awt.event.ActionListener {
  setParameters adaptee;

  setParameters_cancella_actionAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancella_actionPerformed(e);
  }
}

class setParameters_aggiungi_actionAdapter implements java.awt.event.ActionListener {
  setParameters adaptee;

  setParameters_aggiungi_actionAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.aggiungi_actionPerformed(e);
  }
}

class setParameters_Annulla_actionAdapter implements java.awt.event.ActionListener {
  setParameters adaptee;

  setParameters_Annulla_actionAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.Annulla_actionPerformed(e);
  }
}

class setParameters_OK_actionAdapter implements java.awt.event.ActionListener {
  setParameters adaptee;

  setParameters_OK_actionAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.OK_actionPerformed(e);
  }
}

class setParameters_Salva_actionAdapter implements java.awt.event.ActionListener {
  setParameters adaptee;

  setParameters_Salva_actionAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.Salva_actionPerformed(e);
  }
}

class setParameters_this_windowAdapter extends java.awt.event.WindowAdapter {
  setParameters adaptee;

  setParameters_this_windowAdapter(setParameters adaptee) {
    this.adaptee = adaptee;
  }
  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

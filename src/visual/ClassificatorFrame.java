package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import indexing.*;
import loader.DBConnection;
import retrieval.TClassificator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2004</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.1
 */

public class ClassificatorFrame extends JFrame {
  private int NUMERIC_NULL = -1;
  private char CHAR_NULL = ' ';
  private String STRING_NULL = "";

  String[] ComboSex = {"-","M","F"};
  String[] ComboOutcome = {"-","0","1","2"};

  TKDTree[] kdTrees;
  TTVTree[] tvTrees;
  DBConnection connection;

  DialogHandler dh = new DialogHandler ();
  TMessageDialog dialogCL;

  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();

  JComboBox sesso = new JComboBox(ComboSex);
  JComboBox risultato = new JComboBox(ComboOutcome);
  JTextField etaMin = new JTextField();
  JTextField etaMax = new JTextField();
  JTextField caloPeso = new JTextField();
  JTextField pesoSecco = new JTextField();
  JTextField tempoDurata = new JTextField();
  JTextField tipoDializzatore = new JTextField();
  JTextField trattamentoF = new JTextField();

  JButton ResetButton = new JButton();
  JButton CancelButton = new JButton();
  JButton OKButton = new JButton();
  JLabel jLabel10 = new JLabel();
  JTextField valoreK = new JTextField();

  public ClassificatorFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    etaMax.setText("");
    tipoDializzatore.setEnabled(false);
    tipoDializzatore.setText("");
    trattamentoF.setEnabled(false);
    trattamentoF.setText("");
    tempoDurata.setEnabled(false);
    tempoDurata.setText("");
    caloPeso.setText("");
    pesoSecco.setEnabled(false);
    pesoSecco.setText("");
    etaMin.setText("");
    jLabel1.setText("Anno di nascita Min");
    jLabel1.setBounds(new Rectangle(20, 37, 130, 15));
    this.getContentPane().setLayout(null);
    etaMin.setText("");
    etaMin.setBounds(new Rectangle(140, 36, 76, 21));
    jLabel2.setBounds(new Rectangle(20, 69, 130, 15));
    jLabel2.setText("Anno di nascita Max");
    etaMax.setBounds(new Rectangle(140, 68, 77, 21));
    jLabel3.setToolTipText("");
    jLabel3.setText("Sesso");
    jLabel3.setBounds(new Rectangle(20, 115, 81, 15));
    sesso.setBounds(new Rectangle(117, 109, 79, 21));
    jLabel4.setBounds(new Rectangle(22, 164, 90, 15));
    jLabel4.setText("Calo Peso");
    caloPeso.setBounds(new Rectangle(116, 161, 78, 21));
    jLabel5.setText("Peso Secco");
    jLabel5.setBounds(new Rectangle(23, 202, 91, 15));
    pesoSecco.setBounds(new Rectangle(116, 202, 78, 21));
    tempoDurata.setBounds(new Rectangle(117, 237, 79, 21));
    jLabel6.setBounds(new Rectangle(49, 293, 263, 15));
    jLabel6.setText("Numero di casi nella classificazione (K) : ");
    jLabel7.setText("Tipo di dializzatore");
    jLabel7.setBounds(new Rectangle(235, 162, 149, 15));
    jLabel8.setText("Risultato");
    jLabel8.setBounds(new Rectangle(236, 238, 133, 15));
    tipoDializzatore.setText("");
    tipoDializzatore.setBounds(new Rectangle(392, 157, 82, 21));
    trattamentoF.setBounds(new Rectangle(393, 195, 81, 21));
    jLabel9.setBounds(new Rectangle(236, 198, 153, 17));
    jLabel9.setText("Trattamento farmacologico");
    risultato.setBounds(new Rectangle(393, 233, 81, 21));
    ResetButton.setBounds(new Rectangle(297, 41, 177, 25));
    ResetButton.setText("Azzera Valori");
    ResetButton.addActionListener(new ClassificatorFrame_ResetButton_actionAdapter(this));
    CancelButton.setBounds(new Rectangle(184, 359, 136, 25));
    CancelButton.setText("Chiudi Finestra");
    CancelButton.addActionListener(new ClassificatorFrame_CancelButton_actionAdapter(this));
    OKButton.setText("Esegui Classificazione");
    OKButton.addActionListener(new ClassificatorFrame_OKButton_actionAdapter(this));
    OKButton.setBounds(new Rectangle(296, 103, 178, 25));
    this.setResizable(false);
    this.setTitle("Classificazione");
    this.addWindowListener(new ClassificatorFrame_this_windowAdapter(this));
    jLabel10.setText("Tempo durata");
    jLabel10.setBounds(new Rectangle(23, 240, 83, 15));
    valoreK.setBounds(new Rectangle(318, 290, 79, 21));
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(pesoSecco, null);
    this.getContentPane().add(caloPeso, null);
    this.getContentPane().add(jLabel7, null);
    this.getContentPane().add(jLabel8, null);
    this.getContentPane().add(jLabel9, null);
    this.getContentPane().add(trattamentoF, null);
    this.getContentPane().add(tipoDializzatore, null);
    this.getContentPane().add(risultato, null);
    this.getContentPane().add(etaMax, null);
    this.getContentPane().add(etaMin, null);
    this.getContentPane().add(sesso, null);
    this.getContentPane().add(OKButton, null);
    this.getContentPane().add(ResetButton, null);
    this.getContentPane().add(CancelButton, null);
    this.getContentPane().add(jLabel10);
    this.getContentPane().add(tempoDurata, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(valoreK);
    this.setBounds(100,100,530,420);
  }

  public void setParameters (TKDTree[] kdTrees, TTVTree[] tvTrees, DBConnection conn) {
    this.kdTrees = kdTrees;
    this.tvTrees = tvTrees;
    this.connection = conn;
  }

  void CancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void ResetButton_actionPerformed(ActionEvent e) {
    trattamentoF.setText("");
    tipoDializzatore.setText("");
    tempoDurata.setText("");
    pesoSecco.setText("");
    caloPeso.setText("");
    etaMax.setText("");
    etaMin.setText("");

    sesso.setSelectedIndex(0);
    risultato.setSelectedIndex(0);
  }

  void OKButton_actionPerformed(ActionEvent e) {
    int K, EtaMin, EtaMax;
    char Sesso, Outcome;
    float calopeso, pesosecco, tempodurata;
    String tipodializzatore, trattamento;

    K= -1;
    if (!(valoreK.getText()).equals("")) K = Integer.parseInt(valoreK.getText());
    EtaMin= -1;
    if (!(etaMin.getText()).equals("")) EtaMin = Integer.parseInt(etaMin.getText());
    EtaMax= -1;
    if (!(etaMax.getText()).equals("")) EtaMax = Integer.parseInt(etaMax.getText());

    Sesso= ' ';
    if (sesso.getSelectedIndex() != 0) Sesso = (ComboSex[sesso.getSelectedIndex()]).charAt(0);
    Outcome= ' ';
    if (risultato.getSelectedIndex() != 0) Outcome = (ComboOutcome[risultato.getSelectedIndex()]).charAt(0);

    calopeso= -1;
    if (!(caloPeso.getText()).equals("")) calopeso = Float.parseFloat(caloPeso.getText());
    pesosecco= -1;
    if (!(pesoSecco.getText()).equals("")) pesosecco = Float.parseFloat(pesoSecco.getText());
    tempodurata= -1;
    if (!(tempoDurata.getText()).equals("")) tempodurata = Float.parseFloat(tempoDurata.getText());

    tipodializzatore = tipoDializzatore.getText();
    trattamento = trattamentoF.getText();

    TClassificator classificator = new TClassificator (connection);
    classificator.setParameters(EtaMin, EtaMax, Sesso, calopeso, pesosecco, tempodurata,
                                trattamento, tipodializzatore, Outcome);
    if ((EtaMin == NUMERIC_NULL) && (EtaMax == NUMERIC_NULL) && (Sesso == CHAR_NULL) &&
        (Outcome == CHAR_NULL) && (calopeso == NUMERIC_NULL) && (pesosecco == NUMERIC_NULL) &&
        (tempodurata == NUMERIC_NULL) && (tipodializzatore.equals(STRING_NULL)) &&
        (trattamento.equals(STRING_NULL))) {
      String[] text = {"Specificare almeno un parametro per restringere la classificazione!"};
      String[] buttons = {"OK"};
      dialogCL = new TMessageDialog (ClassificatorFrame.this, "Errore - Nessun Parametro Specificato", true, text, buttons, dh, dh);
      dialogCL.setLocation(200, 200);
      dialogCL.setVisible(true);
    }
    else if (K > 0) {
      classificator.calculate(K, kdTrees, tvTrees);

      String[] text = {"Classificazione conclusa con successo"};
      String[] buttons = {"OK"};
      dialogCL = new TMessageDialog (ClassificatorFrame.this, "Fine Operazione", true, text, buttons, dh, dh);
      dialogCL.setLocation(200, 200);
      dialogCL.setVisible(true);
    }
    else {
      String[] text = {"Specificare il numero di casi da inserire nella classificazione!"};
      String[] buttons = {"OK"};
      dialogCL = new TMessageDialog (ClassificatorFrame.this, "Errore - Parametro K Mancante", true, text, buttons, dh, dh);
      dialogCL.setLocation(200, 200);
      dialogCL.setVisible(true);
    }
  }

  public void this_windowClosing(WindowEvent e) {
    this.dispose();
  }

  class DialogHandler extends WindowAdapter implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ClassificatorFrame.this.setVisible(true);
      ClassificatorFrame.this.dialogCL.dispose();
    }
  }
}

class ClassificatorFrame_this_windowAdapter
    extends WindowAdapter {
  private ClassificatorFrame adaptee;
  ClassificatorFrame_this_windowAdapter(ClassificatorFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class ClassificatorFrame_CancelButton_actionAdapter implements java.awt.event.ActionListener {
  ClassificatorFrame adaptee;

  ClassificatorFrame_CancelButton_actionAdapter(ClassificatorFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.CancelButton_actionPerformed(e);
  }
}

class ClassificatorFrame_ResetButton_actionAdapter implements java.awt.event.ActionListener {
  ClassificatorFrame adaptee;

  ClassificatorFrame_ResetButton_actionAdapter(ClassificatorFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ResetButton_actionPerformed(e);
  }
}

class ClassificatorFrame_OKButton_actionAdapter implements java.awt.event.ActionListener {
  ClassificatorFrame adaptee;

  ClassificatorFrame_OKButton_actionAdapter(ClassificatorFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.OKButton_actionPerformed(e);
  }
}

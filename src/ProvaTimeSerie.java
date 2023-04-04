
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import timeserie.TTimeSerie;
import transform.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class ProvaTimeSerie extends Frame {
  TTimeSerie Originale;
  THaar HaarTransformer;
  TNormalizer Normalizer;
  TPiecewise Piecewise;

  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  public ProvaTimeSerie() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static void main (String args[]) {
    ProvaTimeSerie app = new ProvaTimeSerie();
    app.setSize(900, 600);
    app.setVisible(true);
  }

  private void jbInit() throws Exception {
    jButton1.setBounds(new Rectangle(18, 27, 123, 25));
    jButton1.setText("Serie Originale");
    jButton1.addActionListener(new ProvaTimeSerie_jButton1_actionAdapter(this));
    this.setLayout(null);
    jButton2.setBounds(new Rectangle(588, 27, 108, 25));
    jButton2.setText("Fine Lavoro");
    jButton2.addActionListener(new ProvaTimeSerie_jButton2_actionAdapter(this));
    jButton3.setBounds(new Rectangle(157, 28, 108, 25));
    jButton3.setText("Step Wavelet");
    jButton3.addActionListener(new ProvaTimeSerie_jButton3_actionAdapter(this));
    jButton4.setBounds(new Rectangle(286, 28, 112, 25));
    jButton4.setText("Normalizzata");
    jButton4.addActionListener(new ProvaTimeSerie_jButton4_actionAdapter(this));
    jButton5.setBounds(new Rectangle(416, 28, 103, 24));
    jButton5.setText("Piecewise");
    jButton5.addActionListener(new ProvaTimeSerie_jButton5_actionAdapter(this));
    this.add(jButton1, null);
    this.add(jButton2, null);
    this.add(jButton3, null);
    this.add(jButton4, null);
    this.add(jButton5, null);
  }

  void disegna (TTimeSerie TS, Graphics gr) {
    int N, I;
    int X1, Y1, X2, Y2;

    N= TS.getNumValues();
    for (I= 0; I < N - 1; I++) {
      X1= java.lang.Math.round (I * 900 / N);
      X2= java.lang.Math.round ((I + 1) * 900 / N);
      Y1= 600 - 30 - (int)TS.getValue(I);
      Y2= 600 - 30 - (int)TS.getValue(I + 1);
      gr.drawLine(X1, Y1, X2, Y2);
    }
  }

  void jButton1_actionPerformed(ActionEvent e) {
    Graphics g= this.getGraphics();
    int I;
    java.util.Random rand = new java.util.Random ();

    Originale= new TTimeSerie (451);
    for (I= 0; I < 451; I++) {
      Originale.addValue(I, rand.nextInt(500));
    }
    Normalizer= new TNormalizer (Originale);
    HaarTransformer= new THaar (Originale);
    Piecewise= new TPiecewise (Originale, 6);

    disegna (Originale, g);
  }

  void jButton2_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void jButton3_actionPerformed(ActionEvent e) {
    Graphics g = this.getGraphics();
    TTimeSerie Temp;

    HaarTransformer.calculate();
    HaarTransformer.step();
    Temp= HaarTransformer.getTransformedSerie();
    disegna (Temp, g);
  }

  void jButton4_actionPerformed(ActionEvent e) {
    Graphics g = this.getGraphics();
    TTimeSerie Temp;

    Normalizer.calculate();
    Normalizer.step();
    Temp= Normalizer.getTransformedSerie();
    disegna (Temp, g);
  }

  void jButton5_actionPerformed(ActionEvent e) {
    Graphics g = this.getGraphics();
    TTimeSerie Temp;

    Piecewise.calculate();
//    Piecewise.step();
    Temp= Piecewise.getTransformedSerie();
    disegna (Temp, g);
  }

}

class ProvaTimeSerie_jButton1_actionAdapter implements java.awt.event.ActionListener {
  ProvaTimeSerie adaptee;

  ProvaTimeSerie_jButton1_actionAdapter(ProvaTimeSerie adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class ProvaTimeSerie_jButton2_actionAdapter implements java.awt.event.ActionListener {
  ProvaTimeSerie adaptee;

  ProvaTimeSerie_jButton2_actionAdapter(ProvaTimeSerie adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class ProvaTimeSerie_jButton3_actionAdapter implements java.awt.event.ActionListener {
  ProvaTimeSerie adaptee;

  ProvaTimeSerie_jButton3_actionAdapter(ProvaTimeSerie adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}

class ProvaTimeSerie_jButton4_actionAdapter implements java.awt.event.ActionListener {
  ProvaTimeSerie adaptee;

  ProvaTimeSerie_jButton4_actionAdapter(ProvaTimeSerie adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}

class ProvaTimeSerie_jButton5_actionAdapter implements java.awt.event.ActionListener {
  ProvaTimeSerie adaptee;

  ProvaTimeSerie_jButton5_actionAdapter(ProvaTimeSerie adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton5_actionPerformed(e);
  }
}

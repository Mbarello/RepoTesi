package visual;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TShowSerie extends JFrame {
  protected int dimX, dimY, Width, Height, diffY;
  protected int startX, startY;
  protected int endX, endY;
  protected TTimeSerie serie;
  protected int N;
  protected double minY, maxY, Exc_Y;
  protected Dimension dimension = new Dimension ();
  JButton redraw = new JButton ("Redraw");

  public TShowSerie(TTimeSerie Serie, int  X, int Y, int dimx, int dimy) throws HeadlessException {
    super ("Serie: " + Serie.getName());
    serie= new TTimeSerie ();
    dimX= dimx;
    dimY= dimy;
    setSerie (Serie);
    setBounds (X, Y, dimX, dimY);
    addWindowListener (new WindowHandler ());
    redraw.addActionListener(new ButtonHandler ());
  }

  private void copySerie (TTimeSerie Source, TTimeSerie Dest) {
    int I, N;

    Dest.setName(Source.getName());
    N= Source.getNumValues();
    Dest.setSize(N);
    Dest.init();
    for (I= 0; I < N; I++)
      Dest.addValue(Source.getTStamp(I), Source.getValue(I));
  }

  public void setSerie (TTimeSerie Serie) {
    copySerie (Serie, serie);
    N= serie.getNumValues() - 1;
    maxY= serie.getMaxPeak();
    if (maxY < 0) maxY= 0;
    minY= serie.getMinPeak();
    if (minY > 0) minY= 0;
    Exc_Y= maxY - minY;
  }

  protected void prepareGraph (int StartX, int EndX, int StartY, int EndY) {
    Graphics gr = this.getGraphics();
    Container frameContainer = getContentPane();
    int inizioX, inizioY, fineX, fineY;

    dimension= frameContainer.getSize();
    startX= StartX;
    endX= EndX;
    startY= StartY;
    endY= EndY;
    Width= dimension.width - startX - endX;
    Height= dimension.height - startY - endY;
    diffY= dimension.height - endY;

    redraw.setBounds(dimension.width - 82, 2, 80, 20);
    frameContainer.add(redraw);

    inizioX= getX (0) - 5;
    fineX= getX (N) + 5;
    inizioY= getY (minY) + 5;
    fineY= getY (maxY) - 7;

    gr.clearRect(0, 0, dimension.width, dimension.height);

    gr.setColor(java.awt.Color.black);
    gr.drawLine(inizioX, getY (0), fineX, getY (0));
    gr.drawLine(fineX - 5, getY (0) - 2, fineX, getY (0));
    gr.drawLine(fineX - 5, getY (0) + 2, fineX, getY (0));

    gr.drawLine(getX (0), inizioY, getX (0), fineY);
    gr.drawLine(getX (0) - 2, fineY + 5, getX (0), fineY);
    gr.drawLine(getX (0) + 2, fineY + 5, getX (0), fineY);

    gr.drawString(String.valueOf(maxY), 5, 36);
    gr.drawString(String.valueOf(minY), 5, dimension.height + 18 - endY);
    gr.drawString(String.valueOf(serie.getNumValues()), dimension.width - 18, dimension.height + 18 - endY);
  }

  protected int getX (int x) {
    int X;

    X= startX + (int)(((double)x/N) * Width);
    return X;
  }

  protected int getY (double y) {
    int Y;

    Y= (int)(((y - minY)/Exc_Y) * Height);
    Y= diffY - Y;
    return Y;
  }

  protected void drawSerie() {
    int I;
    int X1, Y1, X2, Y2;
    Graphics gr = this.getGraphics();

    for (I= 0; I < N; I++) {
      if ( (serie.getValue(I) != 0) && (serie.getValue(I + 1) != 0)) {
        X1 = getX(I);
        X2 = getX((I + 1));
        Y1 = getY(serie.getValue(I));
        Y2 = getY(serie.getValue(I + 1));
        gr.drawLine(X1, Y1, X2, Y2);
      }
    }
  }

  public void showSerie () {
    prepareGraph(10, 10, 48, 0);

    drawSerie();
  }

  public class WindowHandler extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      setVisible(false);
      dispose();
    }
  }

  public class ButtonHandler implements ActionListener {
    public void actionPerformed (ActionEvent e) {
      showSerie();
    }
  }

  public void paint (Graphics g) {
    showSerie();
  }

  public void setVisible (boolean b) {
    super.setVisible(b);
//    showSerie();
  }

/*  public void show () {
    super.show();
//    showSerie();
  }
*/
}

package visual;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Color;
import temporalabstraction.configuration.SignalTemplate;
import temporalabstraction.expected.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Giorgio Leonardi
 * @version 1.1
 */
public class AbstractionsLegend
    extends JFrame {

  protected int dimX, dimY;
  protected SignalTemplate template;
  protected Dimension dimension = new Dimension ();
  protected int trendDim, stateDim;
  protected int xDescr, xSym;
  protected int rx, ry;
  protected int nTrends, nStationaries, nStates;
  protected int separY = 3;
  protected int qdx = 5;
  JButton redraw = new JButton ("Redraw");

  public AbstractionsLegend(SignalTemplate templ, int  X, int Y, int dimx, int dimy) {
    super ("Template: " + " - segnale " + templ.getSignal());
    template= templ;

    dimX= dimx;
    dimY= dimy;

    nTrends= template.getNumTrends();
    nStationaries= template.getNumStationaries();
    nStates= template.getNumStates();

    setBounds (X, Y, dimX, dimY);
    addWindowListener (new WindowHandler ());
    redraw.addActionListener(new ButtonHandler ());
  }

  protected void prepareViz () {
    Container frameContainer = getContentPane();
    int inizioX, inizioY, fineX, fineY;

    dimension= frameContainer.getSize();
    dimX= dimension.width;
    dimY= dimension.height;

    trendDim= (int)((nTrends + 1 + nStationaries)  * dimY / (nTrends + 1 + nStates + 1 + nStationaries));
    stateDim= dimY - trendDim;

    xDescr= (int)((dimX * 2) / 3);
    xSym= dimX - xDescr;

    rx= (int)((xSym / 3) - 2*qdx);
    ry= (int)((trendDim / (nTrends + 1 + nStationaries)) - 2*qdx);

    redraw.setBounds(dimension.width - 82, 2, 80, 20);
    frameContainer.add(redraw);
  }

  protected int getY (int y) {
    return y  + separY + 40;
  }
  protected int getYS (int y) {
    return y  + separY + trendDim + 40;
  }

  protected int qdy (int I) {
    return I * (ry + qdx);
  }

  protected void showLegend () {
    int I, y;
    ExpectedTrend trend;
    ExpectedStationary stationary;
    ExpectedState state;
    Graphics gr = this.getGraphics();
    gr.setColor(java.awt.Color.black);

    prepareViz();

    gr.drawString("TRENDS :", 10, 38);
    gr.drawLine(70, 34, dimX - 5, 34);

    gr.drawString("STATES :", 10, trendDim + 38);
    gr.drawLine(66, trendDim + 34, dimX - 5, trendDim + 34);

    for (I= 0; I < nTrends; I++) {
      trend= (ExpectedTrend)(template.getExpectedTrend(I));
      y= getY (qdy(I));

      gr.drawRect(qdx, y, rx, ry);
      gr.setColor(trend.getSymbol().getColor());
      gr.fillRect(qdx + 1, y + 1, rx - 1, ry - 1);
      gr.setColor(java.awt.Color.black);

      gr.drawString(trend.getSymbol().getSymbol() + " -> " + trend.getSymbol().getDescription(), 2*qdx + rx, y + 13);
    }

    for (I= 0; I < nStationaries; I++) {
      stationary= (ExpectedStationary)(template.getExpectedStationary(I));
      y= getY (qdy(I + nTrends));

      gr.drawRect(qdx, y, rx, ry);
      gr.setColor(stationary.getSymbol().getColor());
      gr.fillRect(qdx + 1, y + 1, rx - 1, ry - 1);
      gr.setColor(java.awt.Color.black);

      gr.drawString(stationary.getSymbol().getSymbol() + " -> " + stationary.getSymbol().getDescription(), 2*qdx + rx, y + 13);
    }

    I= nTrends + nStationaries;
    y= getY (qdy(I));

    gr.setColor(Color.black);
    gr.fillRect(qdx, y, rx, ry);

    gr.drawString("U -> Unknown", 2*qdx + rx, y + 13);
    for (I= 0; I < nStates; I++) {
      state= (ExpectedState)(template.getExpectedState(I));
      y= getYS (qdy(I));

      gr.drawRect(qdx, y, rx, ry);
      gr.setColor(state.getSymbol().getColor());
      gr.fillRect(qdx + 1, y + 1, rx - 1, ry - 1);
      gr.setColor(java.awt.Color.black);

      gr.drawString(state.getSymbol().getSymbol() + " -> " + state.getSymbol().getDescription(), 2*qdx + rx, y + 13);
    }

    I= nStates;
    y= getYS (qdy(I));

    gr.setColor(Color.black);
    gr.fillRect(qdx, y, rx, ry);

    gr.drawString("U -> Unknown", 2*qdx + rx, y + 13);

  }

  public class WindowHandler extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      setVisible(false);
      dispose();
    }
  }

  public class ButtonHandler implements ActionListener {
    public void actionPerformed (ActionEvent e) {
      showLegend();
    }
  }

  public void paint (Graphics g) {
    showLegend();
  }

  public void setVisible (boolean b) {
    super.setVisible(b);
//    showSerie();
  }


/*  public void show () {
    super.show();
//    showSerie();
  } */

}


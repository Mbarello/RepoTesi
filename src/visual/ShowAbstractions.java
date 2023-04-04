package visual;

import timeserie.TTimeSerie;
import temporalabstraction.configuration.SignalTemplate;
import temporalabstraction.instance.*;
import temporalabstraction.symbol.*;
import temporalabstraction.tamodule.SignalInstance;

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


import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ShowAbstractions extends TShowSerie {
  protected SignalInstance signalInstance;
  protected SignalTemplate signalTemplate;
  protected double TSStart, TSEnd;
  protected int YIup, YFup, YIdown, YFdown;
  protected int stationaryY, stateY, jointY;
  JButton legenda = new JButton ("Legenda");

  public ShowAbstractions(TTimeSerie Serie, SignalInstance Instance, SignalTemplate T, int  X, int Y, int dimx, int dimy) {
    super (Serie,  X, Y, dimx, dimy);
    legenda.addActionListener(new ButtonHandler ());

    signalInstance= Instance;
    signalTemplate= T;
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

    legenda.setBounds(dimension.width - 182, 2, 90, 20);
    frameContainer.add(legenda);

    redraw.setBounds(dimension.width - 82, 2, 80, 20);
    frameContainer.add(redraw);

    inizioX= super.getX (0) - 5;
    fineX= super.getX (N) + 5;
    inizioY= super.getY (minY) + 5;
    fineY= super.getY (maxY) - 7;

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

  protected void prepareAbstractionWindow () {
    int intdist, bardim, extdist;
    double temp;
    Graphics gr = this.getGraphics();
    Container frameContainer = getContentPane();

    if (serie.getNumValues() > 0) {
      TSStart= serie.getTStamp(0);
      TSEnd= serie.getTStamp(serie.getNumValues() - 1);

      stationaryY= (int)(endY / 4);
      stateY= (int)(endY / 3);
      jointY= (int)(endY * 2 / 3);

//    trendY= (int)(endY / 3);
//    stateY= (int)((endY * 2) / 3);

      intdist= (int)(stationaryY / 6);
      bardim= (int)((stationaryY - intdist) / 4);
      extdist= stationaryY - intdist - bardim;

      YIup= extdist;
      YFup= YIup + bardim;

      YIdown= YFup + intdist;
      YFdown= YIdown + bardim;

      int textX = (frameContainer.getWidth() / 2);
      int textY = diffY + 17;

      gr.setColor(Color.BLACK);
      gr.drawString("TRENDS :", textX - 28, textY);
      gr.drawString("STATES :", textX - 26, textY + stateY);
      gr.drawString("JOINT :", textX - 20, textY + jointY);

      int textXb = (frameContainer.getWidth() / 4);
      int textXe = (frameContainer.getWidth() * 3 / 4);
      textY = diffY + 13;

      gr.drawLine(textXb, textY, textX - 35, textY);
      gr.drawLine(textX + 35, textY, textXe, textY);

      gr.drawLine(textXb, textY + stateY, textX - 35, textY + stateY);
      gr.drawLine(textX + 35, textY + stateY, textXe, textY + stateY);

      gr.drawLine(textXb, textY + jointY, textX - 35, textY + jointY);
      gr.drawLine(textX + 35, textY + jointY, textXe, textY + jointY);
    }
  }

  protected int getXPI (SymbolInstance instance) {
    double result;

    result= ((instance.getInterval().getStart()/1000 - TSStart) * Width);
    result= result / (TSEnd - TSStart);

    return startX + (int)result;
  }

  protected int getXPF (SymbolInstance instance) {
    double result;

    result= ((instance.getInterval().getEnd()/1000 - TSStart) * Width);
    result= result / (TSEnd - TSStart);

    return startX + (int)result;
  }

  protected boolean odd (int N) {
    if ((N % 2) == 0) return false;
    return true;
  }

  protected void drawTrends (ArrayList TR) {
    int I, YI, YF, XPI, XPF;
    TrendInstance instance;
    TrendSymbol symbol;
    Graphics gr = this.getGraphics();
    gr.setColor(java.awt.Color.black);

    for (I= 0; I < TR.size(); I++) {
      instance= (TrendInstance)TR.get(I);
      XPI= getXPI (instance);
      XPF= getXPF (instance);

      if (odd(I)) {
        YI= YIdown;
        YF= YFdown;
      } else {
        YI= YIup;
        YF= YFup;
      }

      gr.drawRect(XPI, diffY + YI + 4, (XPF - XPI), (YF - YI) + 4);

      // colora il rettangolo appena disegnato col colore del trend
      symbol= instance.getTrendSymbol();
      gr.setColor(symbol.getColor());
      gr.fillRect(XPI + 1, diffY + YI + 1 + 4, (XPF - XPI - 1), (YF - YI - 1 + 4));
      gr.setColor(Color.BLACK);
    }
  }

  protected void drawStationaries (ArrayList ST) {
    int I, YI, YF, XPI, XPF;
    StationaryInstance instance;
    StationarySymbol symbol;
    Graphics gr = this.getGraphics();
    gr.setColor(java.awt.Color.black);

    for (I= 0; I < ST.size(); I++) {
      instance = (StationaryInstance) ST.get(I);
      XPI = getXPI(instance);
      XPF = getXPF(instance);

      if (odd(I)) {
        YI = YIdown;
        YF = YFdown;
      }
      else {
        YI = YIup;
        YF = YFup;
      }

      gr.drawRect(XPI, diffY + stationaryY + YI + 4, (XPF - XPI), (YF - YI) + 4);
      // colora il rettangolo appena disegnato col colore del trend
      symbol= instance.getStationarySymbol();
      gr.setColor(symbol.getColor());
      gr.fillRect(XPI + 1, diffY + stationaryY + YI + 1 + 4, (XPF - XPI - 1), (YF - YI - 1 + 4));
      gr.setColor(Color.BLACK);
    }
  }

  protected void drawStates (ArrayList ST) {
    int I, YI, YF, XPI, XPF;
    StateInstance instance;
    StateSymbol symbol;
    Graphics gr = this.getGraphics();
    gr.setColor(java.awt.Color.black);

    for (I= 0; I < ST.size(); I++) {
      instance= (StateInstance)ST.get(I);
      XPI= getXPI (instance);
      XPF= getXPF (instance);

      if (odd(I)) {
        YI= YIdown;
        YF= YFdown;
      } else {
        YI= YIup;
        YF= YFup;
      }

      gr.drawRect(XPI, diffY + stateY + YI + 4, (XPF - XPI), (YF - YI) + 4);
      // colora il rettangolo appena disegnato col colore del trend
      symbol= instance.getStateSymbol();
      gr.setColor(symbol.getColor());
      gr.fillRect(XPI + 1, diffY + stateY + YI + 1 + 4, (XPF - XPI - 1), (YF - YI - 1) + 4);
      gr.setColor(Color.BLACK);
    }
  }

  protected void drawJoints (ArrayList JN) {
    int I, YI, YF, XPI, XPF, mid;
    JointInstance instance;
    TrendSymbol symbolT;
    StateSymbol symbolS;
    Graphics gr = this.getGraphics();
    gr.setColor(java.awt.Color.black);

    for (I= 0; I < JN.size(); I++) {
      instance= (JointInstance)JN.get(I);
      XPI= getXPI (instance);
      XPF= getXPF (instance);

      if (odd(I)) {
        YI= YIdown;
        YF= YFdown;
      } else {
        YI= YIup;
        YF= YFup;
      }

      mid= (YF - YI) / 2;
      // colora il rettangolo appena disegnato col colore del trend
      symbolT= instance.getJointSymbol().getTrendSymbol();
      gr.setColor(symbolT.getColor());
      gr.fillRect(XPI, diffY + jointY + YI + 4, (XPF - XPI + 1), (mid + 1) + 4);

      symbolS= instance.getJointSymbol().getStateSymbol();
      gr.setColor(symbolS.getColor());
      gr.fillRect(XPI, (diffY + jointY + YI + mid + 1) + 4, (XPF - XPI + 1), (mid + 1) + 4);

      gr.setColor(Color.BLACK);
    }
  }


  public void showSerie () {
    Container frameContainer = getContentPane();

    prepareGraph(10, 10, 48, (frameContainer.getHeight() * 2 / 5));
    prepareAbstractionWindow();

    drawSerie();
    drawTrends(signalInstance.getTrends());
//    drawStationaries(signalInstance.stationaryInstances);
    drawStates(signalInstance.getStates());
    drawJoints(signalInstance.getJoints());
  }

  public class WindowHandler extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      setVisible(false);
      dispose();
    }
  }

  public class ButtonHandler implements ActionListener {
    public void actionPerformed (ActionEvent e) {
      if (e.getActionCommand().compareTo("Redraw") == 0) {
        showSerie();
      }
      else {
        AbstractionsLegend legend = new AbstractionsLegend (signalTemplate, 0, 0, 400, 200);
        legend.setVisible(true);
        /// Visualizza legenda
      }
    }
  }

  public void paint (Graphics g) {
    this.showSerie();
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

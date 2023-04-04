package loader;

import java.io.*;
import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TDBFLoader {
  String nomeFile;
  TXBase xbase;

  public TDBFLoader(int paziente) throws IOException {
    String inputLine= new String ();

    inputLine= Integer.toString(paziente);
    while (inputLine.length() < 6) {
      inputLine= "0" + inputLine;
    }
    nomeFile= "SI" + inputLine + ".DBF";

    xbase= new TXBase (nomeFile, "r");
    xbase.use();
  }

  public int getCurrentRecord () {
    return xbase.getCurrentRecord();
  }

  public TXBase getXBase () {
    return xbase;
  }

  public int searchSignalPosition (int numDialisi, int numSegnale) throws IOException {
    int posizione;

    posizione= xbase.bSearch (numDialisi, 1);
    xbase.goTo(posizione);
    if (posizione != -1) {
      while ((xbase.getCurrentRecord() > 1) && (xbase.getIntegerField(2) >= numSegnale)) {
        xbase.previous();
      }
      while (xbase.getIntegerField(2) < numSegnale) {
        xbase.next();
      }
      posizione= xbase.getCurrentRecord();
    }
    return posizione;
  }

  public int loadBlindSingleSerie (int numDialisi, int numSegnale, TTimeSerie Serie) throws IOException {
    int posizione;
    int time;
    double value;

    Serie.init();
    posizione= xbase.bSearch (numDialisi, 1);
    xbase.goTo(posizione);
    if (posizione != -1) {
      while ((xbase.getCurrentRecord () > 1) && (xbase.getIntegerField(2) >= numSegnale)) {
        xbase.previous();
      }
      while (xbase.getIntegerField(2) < numSegnale) {
        xbase.next();
      }
      posizione= xbase.getCurrentRecord();
      xbase.firstReadLine();
      while (xbase.getSegnale () == numSegnale) {
        time= xbase.getTStamp ();
        value= xbase.getValue ();
        if ((numSegnale == 13) && (value == 32767)) value= 0;
        if ((numSegnale == 22) && (value == 32767)) value= 0;
        if ((numSegnale == 9) && (value == 32767)) value= 0;
        if ((numSegnale == 56) && (value == 32767)) value= 0;
        if ((numSegnale == 41) && (value == 32.77)) value= 0;
        if ((numSegnale == 25) && (value == 32767)) value= 0;
        if ((numSegnale == 23) && (value == 32767)) value= 0;
        if ((numSegnale == 24) && (value == 32767)) value= 0;
        if ((numSegnale == 2) && (value == 3276.7)) value= 0;
        if ((numSegnale == 64) && (value == 3276.7)) value= 0;
        if ((numSegnale == 65) && (value == 3276.7)) value= 0;
        Serie.addValue(time, value);
        xbase.nextReadLine();
      }
    }
    return posizione;
  }

  public void loadSingleSerie (int posizione, int numSegnale, TTimeSerie Serie) throws IOException {
    int time;
    double value;

    Serie.init();
    xbase.goTo(posizione);
    xbase.firstReadLine();
    while (xbase.getSegnale () == numSegnale) {
      time= xbase.getTStamp ();
      value= xbase.getValue ();
      if ((numSegnale == 13) && (value == 32767)) value= 0;
      if ((numSegnale == 22) && (value == 32767)) value= 0;
      if ((numSegnale == 9) && (value == 32767)) value= 0;
      if ((numSegnale == 56) && (value == 32767)) value= 0;
      if ((numSegnale == 41) && (value == 32.77)) value= 0;
      if ((numSegnale == 25) && (value == 32767)) value= 0;
      if ((numSegnale == 23) && (value == 32767)) value= 0;
      if ((numSegnale == 24) && (value == 32767)) value= 0;
      if ((numSegnale == 2) && (value == 3276.7)) value= 0;
      if ((numSegnale == 64) && (value == 3276.7)) value= 0;
      if ((numSegnale == 65) && (value == 3276.7)) value= 0;
      Serie.addValue(time, value);
      xbase.nextReadLine();
    }
  }

  public void loadSingleSerieNotOrdered (int numDialisi, int numSegnale, TTimeSerie Serie) throws IOException {
    int posizione;
    int segnale;
    int time;
    double value;

    Serie.init();
    posizione= xbase.bSearch (numDialisi, 1);
    if (posizione != -1) {
      xbase.goTo(posizione);
      while ((xbase.getCurrentRecord () > 1) && (xbase.getIntegerField(1) >= numDialisi)) {
        xbase.previous();
      }
      while (xbase.getIntegerField(1) < numDialisi) {
        xbase.next();
      }
      while ((xbase.eof() == false) && (xbase.getIntegerField(1) == numDialisi)) {
        segnale= xbase.getIntegerField(2);
        if (segnale == numSegnale) {
          time= xbase.getIntegerField(3);
          value= xbase.getDoubleField(4);
          if ((numSegnale == 13) && (value == 32767)) value= 0;
          if ((numSegnale == 22) && (value == 32767)) value= 0;
          if ((numSegnale == 9) && (value == 32767)) value= 0;
          if ((numSegnale == 56) && (value == 32767)) value= 0;
          if ((numSegnale == 41) && (value == 32.77)) value= 0;
          if ((numSegnale == 25) && (value == 32767)) value= 0;
          if ((numSegnale == 23) && (value == 32767)) value= 0;
          if ((numSegnale == 24) && (value == 32767)) value= 0;
          if ((numSegnale == 2) && (value == 3276.7)) value= 0;
          if ((numSegnale == 64) && (value == 3276.7)) value= 0;
          if ((numSegnale == 65) && (value == 3276.7)) value= 0;
          Serie.addValue(time, value);
        }
        xbase.next();
      }
    }
  }

}

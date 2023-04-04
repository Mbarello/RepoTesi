package loader;

import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class TReadSerie {
  FileInputStream inFile;
  InputStreamReader in;
  BufferedReader inStream;
  int numValue, posValue;

  public TReadSerie() {
    numValue= 0;
    posValue= 0;
  }

  private int getNumPaziente (String input) {
    int num;
    String temp = new String ("");

    num= -1;
    posValue= 0;

    if (input != null) {
      while (input.charAt(posValue) != ' ') {
          temp= temp + input.charAt(posValue);
          posValue++;
      }
      if (temp.length() != 0) {
        num = Integer.parseInt(temp);
        posValue++;
        numValue= 1;
      }
    }

    return num;
  }

  private int getNumDialisi (String input) {
    int num;
    String temp = new String ("");

    num= -1;

    if (input != null) {
      if (numValue != 1) {
        posValue= 0;
        while (input.charAt(posValue) != ' ') {
            posValue++;
        }
        posValue++;
      }

      while (input.charAt(posValue) != ' ') {
          temp= temp + input.charAt(posValue);
          posValue++;
      }
      if (temp.length() != 0) {
        num = Integer.parseInt(temp);
        posValue++;
        numValue= 2;
      }
    }

    return num;
  }

  private double getValue (String input, int n) {
    double num;
    String temp = new String ("");

    num= -1;
    n= n + 2;

    if (input != null) {
      if ((numValue + 1) < n) {
        do {
          while (input.charAt(posValue) != ' ') {
              posValue++;
          }
          numValue++;
          posValue++;
        } while ((numValue + 1) < n);
      }
      else if ((numValue + 1) > n) {
        posValue= posValue - 2;
        do {
          while (input.charAt(posValue) != ' ') {
              posValue--;
          }
          numValue--;
          posValue--;
        } while ((numValue + 1) > n);
        posValue= posValue + 2;
      }

      while (input.charAt(posValue) != ' ') {
          temp= temp + input.charAt(posValue);
          posValue++;
      }
      if (temp.length() != 0) {
        num= Float.parseFloat(temp);
        num= num * 1000;
        num= java.lang.Math.round((float)num);
        num= num / 1000;
        posValue++;
        numValue++;
      }
    }

    return num;
  }

  public void loadSingleSerie (String nomeFile, int numPaziente, int numSerie,
                               TTimeSerie Serie) throws IOException {
    String inputLine;
    int numDialisi;
    double value;

    inFile = new FileInputStream (nomeFile);
    in = new InputStreamReader (inFile);
    inStream = new BufferedReader (in);
    Serie.init();

    inputLine= inStream.readLine();
    while ((inputLine != null) && (getNumPaziente (inputLine) != numPaziente)) {
      inputLine= inStream.readLine();
    }
    while ((inputLine != null) && (getNumPaziente (inputLine) == numPaziente)) {
      numDialisi= getNumDialisi (inputLine);
      value= getValue (inputLine, numSerie);
      Serie.addValue(numDialisi, value);
      inputLine= inStream.readLine();
    }

    inStream.close();
    in.close();
    inFile.close();
  }

  public void loadMultpleSeries (String nomeFile, int numPaziente, int[] numSerie,
                                 TTimeSerie[] Serie) throws IOException {
    String inputLine;
    int i, numDialisi;
    double value;

    inFile = new FileInputStream (nomeFile);
    in = new InputStreamReader (inFile);
    inStream = new BufferedReader (in);
    for (i= 0; i < Serie.length; i++) Serie[i].init();

    inputLine= inStream.readLine();
    while ((inputLine != null) && (getNumPaziente (inputLine) != numPaziente)) {
      inputLine= inStream.readLine();
    }
    while ((inputLine != null) && (getNumPaziente (inputLine) == numPaziente)) {
      numDialisi= getNumDialisi (inputLine);
      for (i= 0; i < numSerie.length; i++) {
        value= getValue (inputLine, numSerie[i]);
        Serie[i].addValue(numDialisi, value);
      }
      inputLine= inStream.readLine();
    }

    inStream.close();
    in.close();
    inFile.close();
  }

}

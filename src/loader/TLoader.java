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

public class TLoader {
  FileInputStream inFile;
  InputStreamReader in;
  BufferedReader inStream;
  int numValue, posValue;
  String fileName;

  public TLoader(int paziente) throws IOException {
    numValue= 0;
    posValue= 0;

    String inputLine= new String ();
    inputLine= Integer.toString(paziente);
    while (inputLine.length() < 6) {
      inputLine= "0" + inputLine;
    }
    fileName= "SI" + inputLine + ".DAT";

    inFile = new FileInputStream (fileName);
    in = new InputStreamReader (inFile);
    inStream = new BufferedReader (in);
    inStream.mark(inFile.available());
  }

  private int getNumDialisi (String input) {
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

  private int getSegnale (String input) {
    int num;
    String temp = new String ("");

    num= -1;

    if (input != null) {
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

  private double getTime (String input) {
    double num;
    String temp = new String ("");

    num= -1;

    if (input != null) {
      while (input.charAt(posValue) != ' ') {
          temp= temp + input.charAt(posValue);
          posValue++;
      }

      if (temp.length() != 0) {
        num = Double.parseDouble(temp);
        posValue++;
        numValue= 2;
      }
    }

    return num;
  }

  private double getValue (String input) {
    double num;
    String temp = new String ("");
    char a;

    num= -1;

    if (input != null) {
      while (posValue < input.length()) {
        a= input.charAt(posValue);
        if (a == ',') {
          temp= temp + '.';
        }
        else {
          temp= temp + a;
        }
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

  public void loadSingleSerie (int numDialisi, int numSegnale,
                               TTimeSerie Serie) throws IOException {
    String nomeFile, inputLine;
    int dialisi, segnale;
    double time, value;

    Serie.init();
    inStream.reset();

    do {
      inputLine= inStream.readLine();
      dialisi= getNumDialisi (inputLine);
      segnale= getSegnale (inputLine);
    } while ((inputLine != null) && ((dialisi != numDialisi) || (segnale != numSegnale)));

    while ((inputLine != null) && (dialisi == numDialisi) && (segnale == numSegnale)) {
      time= getTime (inputLine);
      value= getValue (inputLine);
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

      inputLine= inStream.readLine();
      dialisi= getNumDialisi (inputLine);
      segnale= getSegnale (inputLine);
    }
  }

/*  public void loadMultpleSeries (String nomeFile, int numPaziente, int[] numSerie,
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
  } */

}

package retrieval;

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

public class TRange {
  double min, max, range, maxDim;
  boolean first;
  int signal;

  public TRange(int Signal) {
    min= 0;
    max= 0;
    range= 0;
    maxDim= 0;
    signal= Signal;

    first= true;
  }

  public double getMin () {
    return min;
  }

  public double getMax () {
    return max;
  }

  public double getRange () {
    return range;
  }

  public double getMaxDim () {
    return maxDim;
  }

  public void checkRange (TTimeSerie checked) {
    double minPeak, maxPeak, dim;

    minPeak= checked.getMinPeak();
    maxPeak= checked.getMaxPeak();
    dim= checked.getNumValues();

    if (first == true) {
      min= minPeak;
      max= maxPeak;
      maxDim= dim;

      first= false;
    }
    else {
      if (minPeak < min) min= minPeak;
      if (maxPeak > max) max= maxPeak;
      if (dim > maxDim) maxDim= dim;
    }

    range= max - min;
  }

  public void saveRange () {
    String fileName = "R_KD_SIG" + String.valueOf(signal) + ".RANGE";
    RandomAccessFile file;

    try {
      file = new RandomAccessFile (fileName, "rw");
      file.setLength(0);

      file.writeDouble(min);
      file.writeDouble(max);
      file.writeDouble(maxDim);

      file.close();
    } catch (IOException e) {
    }
  }

  public void saveRangeTV () {
  String fileName = "R_TV_SIG" + String.valueOf(signal) + ".RANGE";
  RandomAccessFile file;

  try {
    file = new RandomAccessFile (fileName, "rw");
    file.setLength(0);

    file.writeDouble(min);
    file.writeDouble(max);
    file.writeDouble(maxDim);

    file.close();
  } catch (IOException e) {
  }
}


  public void loadRange () {
    String fileName = "R_KD_SIG" + String.valueOf(signal) + ".RANGE";
    RandomAccessFile file;

    try {
      file = new RandomAccessFile (fileName, "r");

      min= file.readDouble();
      max= file.readDouble();
      maxDim= file.readDouble();

      range= max - min;
      first= false;

      file.close();
    } catch (IOException e) {
    }

  }

  public void loadRangeTV () {
  String fileName = "R_TV_SIG" + String.valueOf(signal) + ".RANGE";
  RandomAccessFile file;

  try {
    file = new RandomAccessFile (fileName, "r");

    min= file.readDouble();
    max= file.readDouble();
    maxDim= file.readDouble();

    range= max - min;
    first= false;

    file.close();
  } catch (IOException e) {
  }

}


}

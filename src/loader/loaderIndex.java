package loader;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class loaderIndex {
  int[][] indici;
  int numDialisi, numSegnali, numPaziente;

  public loaderIndex(int numdialisi, int numsegnali, int numpaziente) {
    numDialisi= numdialisi;
    numSegnali= numsegnali;
    numPaziente= numpaziente;

    indici= new int [numDialisi][numSegnali];
  }

  public void setIndex (int dialisi, int segnale, int valore) {
    indici[dialisi - 1][segnale]= valore;
  }

  public int getIndex (int dialisi, int segnale) {
    return (indici[dialisi - 1][segnale]);
  }

  public void saveIndex () {
    String fileName = "PAZ" + String.valueOf(numPaziente) + ".IDX";
    RandomAccessFile file;
    int I, Q;

    try {
      file = new RandomAccessFile (fileName, "rw");
      file.setLength(0);

      for (I= 0; I < numDialisi; I++) {
        for (Q= 0; Q < numSegnali; Q++) {
          file.writeInt (indici[I][Q]);
        }
      }

      file.close();
    } catch (IOException e) {
    }
  }

  public void loadIndex () {
    String fileName = "PAZ" + String.valueOf(numPaziente) + ".IDX";
    RandomAccessFile file;
    int I, Q;

    try {
      file = new RandomAccessFile (fileName, "rw");

      for (I= 0; I < numDialisi; I++) {
        for (Q= 0; Q < numSegnali; Q++) {
          indici[I][Q]= file.readInt ();
        }
      }

      file.close();
    } catch (IOException e) {
    }
  }

}

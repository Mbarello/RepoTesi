package indexing;

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

public class TIndex {
  RandomAccessFile file;

  public TIndex() {
  }

  public void writeTree (TNodo node, int Coefficients) throws IOException {
    int I;

    if (node != null) {
        file.writeUTF(node.getKey().getName());
        file.writeInt(node.getLinkPaziente());
        file.writeInt(node.getLinkDialisi());

        for (I= 0; I < Coefficients; I++) {
          file.writeDouble(node.getKey().getTStamp(I));
          file.writeDouble(node.getKey().getValue(I));
        }

        writeTree (node.left, Coefficients);
        writeTree (node.right, Coefficients);
    }
  }

  public void writeTree (TTVNode node, int Coefficients) throws IOException {
    int I;

    if (node != null) {
      /*SE E' UNA FOGLIA SCRIVI SU FILE IL VALORE*/
      if (node.getNumChilds()==0 && node.IsLeaf()==false)
      {
        file.writeUTF(node.getKey().getName());
        file.writeInt(node.getLinkPaziente());
        file.writeInt(node.getLinkDialisi());
        for (I = 0; I < Coefficients; I++)
          {
            file.writeDouble(node.getKey().getTStamp(I));
            file.writeDouble(node.getKey().getValue(I));
          }
      }
      /*PASSO RICORSIVO*/
      for(I=0;I<node.getNumChilds();I++)
           writeTree (node.childs[I], Coefficients);
    }
  }


  public void saveIndex (TKDTree Tree, int Signal, int Coefficients) {
    String fileName = "IKDSIG" + String.valueOf(Signal) + "FOURIER" + Coefficients + ".IDX";

    try {
      file = new RandomAccessFile (fileName, "rw");
      file.setLength(0);

      file.writeInt(Coefficients);
      file.writeInt(Tree.getMaxLength());
      writeTree (Tree.getRoot(), Coefficients);

      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveIndex (TTVTree Tree, int Signal, int Coefficients) {
    String fileName = "ITVSIG" + String.valueOf(Signal) + "FOURIER" + Coefficients + ".IDX";

    try {
      file = new RandomAccessFile (fileName, "rw");
      file.setLength(0);

      file.writeInt(Coefficients);
      file.writeInt(Tree.getMaxLength());
      writeTree (Tree.getRoot(), Coefficients);

      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void loadIndex (TKDTree Tree, int Signal, int fourier) {
    int Coefficients, I, maxLen;
    int Dialisi, Paziente;
    double TStamp, Valore;
    String nomeIndice;
    TTimeSerie Indice = new TTimeSerie();
    String fileName = "IKDSIG" + String.valueOf(Signal) + "FOURIER" + fourier + ".IDX";

    try {
      file = new RandomAccessFile (fileName, "r");

      Coefficients= file.readInt();
      maxLen= file.readInt();
      while (file.getFilePointer() < file.length()) {
        nomeIndice= file.readUTF();
        Indice.setName(nomeIndice);

        Paziente= file.readInt();
        Dialisi= file.readInt();

        Indice.init();
        for (I= 0; I < Coefficients; I++) {
          TStamp= file.readDouble();
          Valore= file.readDouble();
          Indice.addValue(TStamp, Valore);
        }
        Tree.addKey(Indice, Paziente, Dialisi, Signal, true);
      }
      Tree.setMaxLength(maxLen);

      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadIndex (TTVTree Tree, int Signal,int fourier) {
    int Coefficients, I, maxLen;
    int Dialisi, Paziente;
    double TStamp, Valore;
    String nomeIndice;
    String fileName = "ITVSIG" + String.valueOf(Signal) + "FOURIER" + fourier + ".IDX";

    try {
      file = new RandomAccessFile (fileName, "r");

      Coefficients= file.readInt();
      maxLen= file.readInt();
      while (file.getFilePointer() < file.length()) {
        nomeIndice= file.readUTF();
        Paziente= file.readInt();
        Dialisi= file.readInt();

        TTimeSerie Indice = new TTimeSerie();
        Indice.setName(nomeIndice);
        Indice.init();

        for (I= 0; I < Coefficients; I++) {
          TStamp= file.readDouble();
          Valore= file.readDouble();
          Indice.addValue(TStamp, Valore);
        }
        Tree.addKey(Indice, Paziente, Dialisi, Signal, true);
      }
      Tree.setMaxLength(maxLen);

      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}

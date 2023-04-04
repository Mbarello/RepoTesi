
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

import java.io.*;
import cases.TCase;
import loader.DBConnection;
import loader.TGlobalLoader;

public class testBontaCasi {
  public testBontaCasi() {
  }

  public static void main(String[] args) {
    testBontaCasi testBontaCasi1 = new testBontaCasi();
    int[] pazienti = {2, 3, 4, 5, 8, 9, 10, 12, 29, 33};
    int[] segnali = {13, 22, 9, 56, 41, 25, 23, 24, 2, 64, 65};
    int I, Q, paziente, dialisi, maxDialisi, segnale;
    int numCoefficients = 6;
    int[] soglie = {numCoefficients, numCoefficients, numCoefficients, numCoefficients,
        numCoefficients, numCoefficients, numCoefficients, numCoefficients,
        numCoefficients, numCoefficients, numCoefficients};
    TCase caso;
    boolean completo;
    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    String fileName = "casiValidi.txt";
    String riga;
    /*BLOCCODIPROVABLOCCODIPROVABLOCCODIPROVABLOCCODIPROVABLOCCODIPROVA*/
    int maxdialisi[]; /*VETTORE DEI PAZIENTI CON LE MASSIMEDIALISI*/
    int i;
    DBConnection a=new DBConnection();
    a.DBConnection();
    maxdialisi=new int[500];
    for(i=0;i<500;i++) maxdialisi[i]=0;
    a.getMaxDialisi2(maxdialisi);
    /*BLOCCODIPROVABLOCCODIPROVABLOCCODIPROVABLOCCODIPROVABLOCCODIPROVA*/


    try {
      TGlobalLoader loader = new TGlobalLoader (pazienti, segnali, a);
      outFile = new FileOutputStream (fileName);
      outS = new OutputStreamWriter (outFile);
      outStream = new BufferedWriter (outS);

      for (I= 0; I < pazienti.length; I++) {
        paziente = pazienti[I];
        /*maxDialisi = loader.getMaxDialisi(paziente);PROVAPROVAPROVA*/
        maxDialisi=maxdialisi[paziente];

        for (dialisi = 1; dialisi <= maxDialisi; dialisi++) {
          caso = new TCase (segnali.length, paziente, dialisi, soglie);
          loader.loadCase(caso, true);

          riga= "Paziente " + String.valueOf(paziente) + " - Dialisi " + String.valueOf(dialisi);
//          System.out.println (riga);
          outStream.write(riga, 0, riga.length());
          outStream.newLine();
          riga= "   Segnali Mancanti : ";
//          System.out.print (riga);
          outStream.write(riga, 0, riga.length());

          completo= true;
          for (Q= 0; Q < segnali.length; Q++) {
            if (caso.getSerie(Q).getNumValues() <= soglie[Q]) {
              completo= false;
              riga= String.valueOf(segnali[Q]) + "  ";
//              System.out.print (riga);
              outStream.write(riga, 0, riga.length());
            }
          }

          if (completo == true) {
            riga= "Nessuno";
//            System.out.print (riga);
            outStream.write(riga, 0, riga.length());
          }
//          System.out.println();
          outStream.newLine();
        }
      }
      outStream.close();
      outS.close();
      outFile.close();
    } catch (IOException e) {
      System.out.println(" ERRORE !!! ");
      e.printStackTrace();
    }
  }

}

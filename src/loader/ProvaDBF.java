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

public class ProvaDBF {
  public ProvaDBF() {
  }

  public static void main(String[] args) {
    ProvaDBF provaDBF1 = new ProvaDBF();
    int i= 0;

    try {
      TXBase xbase = new TXBase ("SI000002.DBF", "r");
      xbase.use();

      xbase.first();
      while (i < 10) /*(xbase.eof() == false)*/ {
        System.out.print(xbase.getStringField(1) + "  ");
        System.out.print(xbase.getStringField(2) + "  ");
        System.out.print(xbase.getStringField(3) + "  ");
        System.out.println(xbase.getStringField(4));

        xbase.next();
        i++;
      }
      xbase.close();
    }
    catch (IOException exc) {
      System.out.println("Errore nella creazione della classe!");
    }
  }

}


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

import java.io.*;
import timeserie.TTimeSerie;
import indexing.*;
import transform.*;
import visual.TShowSerie;
import loader.TReadSerie;

public class ProvaCalcoli {
  static BufferedReader b = new BufferedReader (new InputStreamReader (System.in));
  TTimeSerie Serie1, Serie2;

  public ProvaCalcoli() {
    Serie1= new TTimeSerie (20, "Serie 1");
    Serie2= new TTimeSerie (20, "Serie 2");
  }

  void nuovaSerie (TTimeSerie T, int n, int Range, java.util.Random rand) {
    int I;

    T.setSize(n);
    T.init();

    for (I= 0; I < n; I++) {
      T.addValue(I, rand.nextInt(Range));
    }
  }

  void creaSerie () {
    int I;
    int numData, Range;
    String scelta;
    java.util.Random rand = new java.util.Random ();

    try {
      System.out.print("Numero di elementi delle due Serie : ");
      scelta= b.readLine();
      numData= Integer.parseInt(scelta);

      System.out.print("Gli elementi delle Serie vanno da 0 ad n. Inserire il valore di n : ");
      b.readLine();
      Range= Integer.parseInt(scelta);

      nuovaSerie (Serie1, numData, Range, rand);
      nuovaSerie (Serie2, numData, Range, rand);

      System.out.println("Serie Temporali Create :");
      System.out.println();

      System.out.print("Serie 1 : ");
      visualizzaSerie (Serie1);
      System.out.print("Serie 2 : ");
      visualizzaSerie (Serie2);

      System.out.print("Distanza Manhattan : ");
      System.out.println(Serie1.getDistance(Serie2, 1));
      System.out.print("Distanza Euclidea : ");
      System.out.println(Serie1.getDistance(Serie2, 2));
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  void creaNormalizzate () {
    TNormalizer Norm1, Norm2;
    TTimeSerie Temp1, Temp2;

    Norm1= new TNormalizer (Serie1);
    Norm2= new TNormalizer (Serie2);

    Norm1.calculate();
    Norm2.calculate();

    Temp1= Norm1.getTransformedSerie();
    Temp2= Norm2.getTransformedSerie();

    System.out.println();

    System.out.print("Serie 1 Normalizzata : ");
    visualizzaSerie (Temp1);
    System.out.print("Serie 2 Normalizzata : ");
    visualizzaSerie (Temp2);

    System.out.print("Distanza Manhattan Normalizzata : ");
    System.out.println(Temp1.getDistance(Temp2, 1));
    System.out.print("Distanza Euclidea Normalizzata : ");
    System.out.println(Temp1.getDistance(Temp2, 2));
  }

  void creaFourier () {
    TFourier Fourier1, Fourier2;
    TTimeSerie Temp1, Temp2;
    int numCoeff;
    String scelta;

    try {
      System.out.print("Numero di Coefficienti da calcolare : ");
      scelta= b.readLine();
      numCoeff= Integer.parseInt(scelta);

      Fourier1= new TFourier (Serie1, numCoeff);
      Fourier2= new TFourier (Serie2, numCoeff);

      Fourier1.calculate();
      Fourier2.calculate();

      Temp1= Fourier1.getTransformedSerie();
      Temp2= Fourier2.getTransformedSerie();

      System.out.println();

      System.out.print("Serie 1 Fourier : ");
      visualizzaSerie (Temp1);
      System.out.print("Serie 2 Fourier : ");
      visualizzaSerie (Temp2);

      System.out.print("Distanza Manhattan : ");
      System.out.println(Temp1.getDistance(Temp2, 1));
      System.out.print("Distanza Euclidea : ");
      System.out.println(Temp1.getDistance(Temp2, 2));
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  void creaPiecewise () {
    TPiecewise Piece1, Piece2;
    TTimeSerie Temp1, Temp2;
    int numCoeff;
    String scelta;

    try {
      System.out.print("Numero di Coefficienti da calcolare : ");
      scelta= b.readLine();
      numCoeff= Integer.parseInt(scelta);

      Piece1= new TPiecewise (Serie1, numCoeff);
      Piece2= new TPiecewise (Serie2, numCoeff);

      Piece1.calculate();
      Piece2.calculate();

      Temp1= Piece1.getTransformedSerie();
      Temp2= Piece2.getTransformedSerie();

      System.out.println();

      System.out.print("Serie 1 Piecewise : ");
      visualizzaSerie (Temp1);
      System.out.print("Serie 2 Piecewise : ");
      visualizzaSerie (Temp2);

      System.out.print("Distanza Manhattan delle Trasformate : ");
      System.out.println(Temp1.getDistance(Temp2, 1));
      System.out.print("Distanza Euclidea delle Trasformate : ");
      System.out.println(Temp1.getDistance(Temp2, 2));
      System.out.print("Distanza Piecewise : ");
      System.out.println(Piece1.getDistance(Piece2));
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  void creaWavelet () {
    THaar Haar1, Haar2;
    TTimeSerie Temp1, Temp2;
    int numStep, i;
    String scelta;

    try {
      System.out.print("Numero di Step da calcolare : ");
      scelta= b.readLine();
      numStep= Integer.parseInt(scelta);

      Haar1= new THaar (Serie1);
      Haar2= new THaar (Serie2);

      for (i= 0; i < numStep; i++) {
        Haar1.calculate();
        Haar1.step();
        Haar2.calculate();
        Haar2.step();
      }

      Temp1= Haar1.getTransformedSerie();
      Temp2= Haar2.getTransformedSerie();

      System.out.println();

      System.out.print("Serie 1 Wavelet : ");
      visualizzaSerie (Temp1);
      System.out.print("Serie 2 Wavelet : ");
      visualizzaSerie (Temp2);

      System.out.print("Distanza Manhattan delle Wavelet : ");
      System.out.println(Temp1.getDistance(Temp2, 1));
      System.out.print("Distanza Euclidea delle Wavelet : ");
      System.out.println(Temp1.getDistance(Temp2, 2));
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  void visualizzaSerie (TTimeSerie S) {
    int I, N;

    N= S.getNumValues();
    for (I= 0; I < N; I++) {
      System.out.print(S.getValue(I));
      System.out.print(" ");
    }
    System.out.println();
  }

  void visualizzaKDTree (TNodo X, int level) {
    if (X != null) {
      System.out.print("Serie a livello " + level + " : ");
      visualizzaSerie (X.getKey());
      System.out.print ("<");
      if (X.left == null) System.out.print ("null , ");
      else System.out.print ("left , ");
      if (X.right == null) System.out.print ("null");
      else System.out.print ("right");
      System.out.println (">");

      visualizzaKDTree (X.left, (level + 1));
      visualizzaKDTree (X.right, (level + 1));
    }
  }

  void provaKDTree () {
    java.util.Random rand = new java.util.Random ();
    TKDTree KDTree = new TKDTree();
    TTimeSerie Temp = new TTimeSerie ();
    TLinkedList Risultati;
    TResultNode current;
    TNodo Radice;
    String scelta;
    int i, numCoeff, numSerie, range;
    double coefficiente, epsilon;

    try {
      System.out.print("Numero di serie da indicizzare : ");
      scelta= b.readLine();
      numSerie= Integer.parseInt(scelta);
      System.out.print("Numero di coefficienti da indicizzare : ");
      scelta= b.readLine();
      numCoeff= Integer.parseInt(scelta);
      System.out.print("Valore massimo dei coefficienti (range) : ");
      scelta= b.readLine();
      range= Integer.parseInt(scelta);

      for (i= 0; i < numSerie; i++) {
        nuovaSerie (Temp, numCoeff, range, rand);
        System.out.print("Serie " + i + " : ");
        visualizzaSerie (Temp);
        KDTree.addKey(Temp, 0, 0, 0, true);
      }

/*      System.out.println();
      System.out.println("Albero risultante : ");
      System.out.println();

      Radice= KDTree.getRoot();
      visualizzaKDTree (Radice, 0); */

      System.out.println();
      System.out.println("Inserimento Query : ");
      System.out.println("Inserire i coefficienti della query : ");
      System.out.println();

      Temp.init();
      for (i= 0; i < numCoeff; i++) {
        System.out.print("Coefficiente " + (i + 1) + " : ");
        scelta= b.readLine();
        coefficiente= Double.parseDouble(scelta);
        Temp.addValue(i, coefficiente);
      }
      System.out.println();
      System.out.print("Inserire la distanza Epsilon : ");
      scelta= b.readLine();
      epsilon= Double.parseDouble(scelta);

      Risultati= KDTree.rangeQuery(Temp, epsilon, false);
      System.out.println();
      System.out.println("Risultati della Query : ");
      System.out.println();
      i= 1;
      current= Risultati.getFirst();
      while (current != null) {
          System.out.print("Matching Serie " + i + " : ");
          visualizzaSerie (current.getKey());
          current= Risultati.getNext();
          i++;
      }
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  void caricaSingolaSerie () {
    TTimeSerie Temp = new TTimeSerie ();
    TReadSerie Reader = new TReadSerie();
    TShowSerie showSerie;
    int paziente, serie;
    String fileName, scelta;

    try {
      fileName = new String("dati_al.dat");
/*      System.out.print("Nome del File : ");
      fileName= b.readLine(); */

      System.out.print("Numero del Paziente : ");
      scelta= b.readLine();
      paziente= Integer.parseInt(scelta);
      System.out.print("Numero della Serie (1 - 30) : ");
      scelta= b.readLine();
      serie= Integer.parseInt(scelta);

      Reader.loadSingleSerie(fileName, paziente, serie, Temp);
      Serie1= Temp;

      System.out.println();
      System.out.print("Serie caricata : ");
      visualizzaSerie (Temp);
      showSerie= new TShowSerie (Temp, 0, 0, 320, 200);
      showSerie.setVisible(true);
    } catch (IOException e) {
      System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
      System.out.println("\n\n  Operazione annullata.");
      System.out.println("\n\n  Errore : " + e.getMessage());
    } catch (NumberFormatException e) {
            System.out.println("\n\n  Errore nella digitazione delle Informazioni.");
            System.out.println("\n\n  Operazione annullata.");
    }
  }

  public static void main(String[] args) {
    String scelta;
    int opzione;

    ProvaCalcoli provaCalcoli1 = new ProvaCalcoli();

    do {
      System.out.println(" Menu Principale : ");
      System.out.println();
      System.out.println();
      System.out.println("  1 ............. Creazione nuova coppia di serie");
      System.out.println("  2 ............................. Normalizzazione");
      System.out.println("  3 .................... Trasformazione Piecewise");
      System.out.println("  4 ................... Trasformazione di Fourier");
      System.out.println("  5 ...................... Trasformazione Wavelet");
      System.out.println("  6 ............................. Prova k-d Trees");
      System.out.println("  7 ................... Caricamento Singola Serie");
      System.out.println();
      System.out.println("  9 ................................. Fine Lavoro");
      System.out.println();
      System.out.println();
      System.out.print("  Scelta : ");

      try {
        scelta= b.readLine();

        opzione= Integer.parseInt (scelta);
      } catch (IOException e) {
        System.out.println ("\n\n  Scelta Errata !!!\n\n");
        opzione = 8;
      }
      catch (NumberFormatException e) {
        System.out.println ("\n\n  Scelta Errata !!!\n\n");
        opzione = 8;
      }


      switch (opzione) {
          case 1 : provaCalcoli1.creaSerie();
            break;
          case 2 : provaCalcoli1.creaNormalizzate();
            break;
          case 3 : provaCalcoli1.creaPiecewise();
            break;
          case 4 : provaCalcoli1.creaFourier();
            break;
          case 5 : provaCalcoli1.creaWavelet();
            break;
          case 6 : provaCalcoli1.provaKDTree();
            break;
          case 7 : provaCalcoli1.caricaSingolaSerie();
            break;

          default: break;
      }
    } while (opzione != 9);

    System.exit (0);
  }

}

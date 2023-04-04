package loader;

import java.io.*;
import java.sql.SQLException;
import loader.DBConnection;
import transform.Interpolation;
import timeserie.TTimeSerie;
import cases.TCase;
import cases.MacroCase;
import temporalabstraction.configuration.QueryContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TGlobalLoader {
  int[] pazienti;
  int[] segnali;
//  CONNESSIONEDB a=new CONNESSIONEDB();
  DBConnection connection;
  Interpolation interpolator = new Interpolation();

  public TGlobalLoader(int[] Pazienti, int[] Segnali, DBConnection conn) throws IOException {
    int I;
    connection= conn;

    pazienti= new int[Pazienti.length];
    for (I= 0; I < Pazienti.length; I++) {
      pazienti[I]= Pazienti[I];
    }
    segnali= new int[Segnali.length];
    for (I= 0; I < Segnali.length; I++) {
      segnali[I]= Segnali[I];
    }
  }

  int searchPaziente (int Paziente) {
    int Result;
    boolean Trovato;

    Result= -1;
    Trovato= false;
    while (Trovato == false) {
      Result++;
      if (pazienti[Result] == Paziente)
        Trovato= true;
    }
    if (Trovato == true)
      return Result;
    else return -1;
  }

  int searchSignal (int Signal) {
    int Result;
    boolean Trovato;

    Result= -1;
    Trovato= false;
    while (Trovato == false) {
      Result++;
      if (segnali[Result] == Signal)
        Trovato= true;
    }
    return Result;
  }

  public int getMaxDialisi (int numPaziente) {
    int maxdialisi = 0;

    connection.setSql("SELECT max(ssessionid) FROM r_ssession where patientid = '"
                + numPaziente + "'");

    try {
      connection.RunQuery();
      connection.nextRecord();
      maxdialisi = connection.getRowValueInt ("max");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    System.out.println("MAX Dialisi per Paziente " + numPaziente + ": " + maxdialisi);

    return maxdialisi;
  }

  public int getMinDialisi (int numPaziente) {
    int mindialisi = 0;

    connection.setSql("SELECT min(ssessionid) FROM r_ssession where patientid = '"
                + numPaziente + "'");

    try {
      connection.RunQuery();
      connection.nextRecord();
      mindialisi = connection.getRowValueInt ("min");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    System.out.println("MIN Dialisi per Paziente " + numPaziente + ": " + mindialisi);

    return mindialisi;
  }

  public void getMaxDialisi2(int v[]) {
    connection.getMaxDialisi2(v);
  }

/*  public void preprocessor (TTimeSerie originalTimeSerie) {
    int I, min, max;
    TTimeSerie transTimeSerie = new TTimeSerie ();

    transTimeSerie.init();

    min= 0;
    while ((min < originalTimeSerie.getNumValues()) && (originalTimeSerie.getValue(min) == 0)) {
      min++;
    }

    max= originalTimeSerie.getNumValues() - 1;
    while ((max >= 0) && (originalTimeSerie.getValue(max) == 0)) {
      max--;
    }

    for (I= min; I <= max; I++) {
      transTimeSerie.addValue(originalTimeSerie.getTStamp(I), originalTimeSerie.getValue(I));
    }
    originalTimeSerie.init();
    for (I= 0; I < transTimeSerie.getNumValues(); I++) {
      originalTimeSerie.addValue(transTimeSerie.getTStamp(I), transTimeSerie.getValue(I));
    }
  }
*/
  public void preprocessor (TTimeSerie originalTimeSerie) {
    int I, min, max;

    max= originalTimeSerie.getNumValues() - 1;
    while ((max >= 0) && (originalTimeSerie.getValue(max) == 0)) {
      max--;
    }
    originalTimeSerie.trunc(max + 1);
  }

  public void loadSingleSerie (int numPaziente, int numDialisi, int numSegnale, TTimeSerie Serie, boolean interpolation) {
      this.connection.loadSingleSerie(numPaziente, numDialisi, numSegnale, Serie);
      preprocessor(Serie);
      if (interpolation) {
        interpolator.setOriginalSerie(Serie);
        interpolator.calculate();
        Serie= interpolator.getOriginalSerie();
      }
  }

  public void loadCase (TCase caso, boolean interpolation) throws IOException {
     int i;
     int numPaziente, numDialisi, numSegnale;
     TTimeSerie Temp;
     String nome;

     numPaziente= caso.getIdPaziente();
     numDialisi= caso.getIdDialisi();
     for (i= 0; i < segnali.length; i++) {
       Temp = new TTimeSerie();
       numSegnale = segnali[i];
       nome= "Serie: " + numSegnale + " - Paziente: " + numPaziente + " - Sessione: " + numDialisi;
       Temp.setName(nome);

       this.loadSingleSerie(numPaziente, numDialisi, numSegnale, Temp, interpolation);
       caso.setSerie(Temp, i);
     }
   }

   public void loadMacroCase (MacroCase caso, boolean interpolation) throws IOException {
     int inf, sup, i, n, numSegnale;
     TTimeSerie temp;

     inf= caso.getStartSession() - caso.getNumPrec();
     sup= caso.getStartSession() + caso.getNumSucc();
     temp= new TTimeSerie ();

     for (n= 0; n < 11; n++) {
       caso.setMedian(n, new TTimeSerie (sup - inf + 1));
       caso.setPerc10(n, new TTimeSerie (sup - inf + 1));
       caso.setPerc90(n, new TTimeSerie (sup - inf + 1));
       numSegnale= segnali[n];
       for (i= inf; i <= sup; i++) {
         temp.init();

//         System.out.println (" PZ : " + caso.startPatient + " - SES : " +  i + " - SIG : " + numSegnale);

         loadSingleSerie (caso.getStartPatient(), i, numSegnale, temp, interpolation);
         caso.getMedian(n).addValue(i, temp.getPercentile(50));
         caso.getPerc10(n).addValue(i, temp.getPercentile(10));
         caso.getPerc90(n).addValue(i, temp.getPercentile(90));
       }
       if (interpolation) {
         interpolator.setOriginalSerie(caso.getMedian(n));
         interpolator.calculate();
         caso.setMedian(n, interpolator.getOriginalSerie());

         interpolator.setOriginalSerie(caso.getPerc10(n));
         interpolator.calculate();
         caso.setPerc10(n, interpolator.getOriginalSerie());

         interpolator.setOriginalSerie(caso.getPerc90(n));
         interpolator.calculate();
         caso.setPerc90(n, interpolator.getOriginalSerie());
       }
     }
   }

   public void loadQueryContext (int paziente, int dialisi, QueryContext Context) {
     this.connection.loadQueryContext(paziente, dialisi, Context);
   }
 }

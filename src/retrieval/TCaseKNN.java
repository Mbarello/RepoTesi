package retrieval;

import java.io.*;
import cases.TCase;
import loader.TGlobalLoader;
import indexing.*;
import retrieval.TPostProcessing;
import timeserie.TTimeSerie;
import transform.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TCaseKNN {
  TCase query, root;
  TRange[] ranges;
  TGlobalLoader loader;
  TKDTree[] kdTrees;
  TTVTree[] TVTrees;
  TLinkedList[] results;
  TLinkedList queryResult;
  TPostProcessing postProcessor;
  int K, numSegnali;
  double[] weights;
  int[] pazienti;
  int[] soglie;
  int[] segnali;
  double[] maxvals;
  boolean segnaliValidi[];
  int idpaziente;
  int iddialisi;
  boolean[] considero;
  double maxDist;
  int currentK;

  public TCaseKNN(TCase Caso, double[] Maxvals, int Kn, TRange[] Ranges, TGlobalLoader Loader, TKDTree[] Trees,
                  double[] Weights, int[] Pazienti, int[] Soglie, int[] Segnali) {
    int i;

    query= Caso;
    maxvals= Maxvals;
    K= Kn;
    ranges= Ranges;
    loader= Loader;
    kdTrees= Trees;
    numSegnali= query.getNumSignals();
    weights= Weights;
    pazienti= Pazienti;
    soglie= Soglie;
    segnali= Segnali;
    segnaliValidi= new boolean[soglie.length];

    results= new TLinkedList[numSegnali];
  }

  public TCaseKNN(TCase Caso, double[] Maxvals, int Kn, TRange[] Ranges, TGlobalLoader Loader, TTVTree[] Trees,
                double[] Weights, int[] Pazienti, int[] Soglie,int[] Segnali,int Idpaziente,int Iddialisi,boolean[] consid) {
    int i;

    query= Caso;
    maxvals= Maxvals;
    K= Kn;
    ranges= Ranges;
    loader= Loader;
    TVTrees= Trees;
    numSegnali= query.getNumSignals();
    weights= Weights;
    pazienti= Pazienti;
    soglie= Soglie;
    segnaliValidi= new boolean[soglie.length];
    segnali= Segnali;
    idpaziente=Idpaziente;
    iddialisi=Iddialisi;
    considero=consid;

    results= new TLinkedList[numSegnali];
  }

  private void intersectLists (TLinkedList Result, TLinkedList Reslist) {
    TResultNode nodoR, current;
    boolean terminato;

    nodoR= Result.getFirst();
    current= Reslist.getFirst();
    terminato= false;

    while ((nodoR != null) && (terminato)) {
      while ((current != null) && (current.getLinkPaziente() < nodoR.getLinkPaziente())) {
        current= Reslist.getNext();
      }
      if (current == null) {
        Result.truncCurrent();
      }
      else {
        while ((current != null) &&
               (current.getLinkPaziente() == nodoR.getLinkPaziente()) &&
               (current.getLinkDialisi() < nodoR.getLinkDialisi())) {
          current= Reslist.getNext();
        }
        if (current == null) {
          Result.truncCurrent();
        }
        else {
          if ((current.getLinkPaziente() != nodoR.getLinkPaziente()) ||
              (current.getLinkDialisi() != nodoR.getLinkDialisi())) {
            Result.deleteCurrent();
          }
          else nodoR= Result.getNext();
        }
      }
    }
  }

  public void step1 (boolean normalized, int numCoefficienti, boolean classificazione, boolean interpolation) throws IOException {
    int i;
    TTimeSerie tempQuery, tempQueryR;
    TFourier trasformata;
    boolean allCases;

    if (normalized == true) {
      results[0]= new TLinkedList ();
      results[0].init();
      allCases= true;
      results[1]= new TLinkedList ();

      for (i= 0; i < numSegnali; i++) {
        System.out.println("Feature corrente: Segnale " + segnali[i]);

        results[1].init();
        tempQuery= query.getSerie(i);
        if ((tempQuery.getNumValues() >= soglie[i]) && (maxvals[i] != 1)) {
          trasformata = new TFourier (tempQuery, numCoefficienti);
          trasformata.calculate();
          tempQueryR= trasformata.getTransformedSerie();

          results[1]= kdTrees[i].rangeQuery (tempQueryR, maxvals[i], ranges[i], classificazione);
          postProcessor= new TPostProcessing (tempQuery, results[1], maxvals[i], pazienti, ranges[i], loader);
          postProcessor.Calculate(0, 2, false, interpolation);
          results[1] = postProcessor.getResult();

          if (allCases) {
            results[0]= results[1];
            allCases= false;
            results[1]= new TLinkedList();
          }
          else intersectLists (results[0], results[1]);
        }
      }
    }
    else {
      results[0]= new TLinkedList ();
      results[0].init();
      allCases= true;
      results[1]= new TLinkedList ();

      for (i= 0; i < numSegnali; i++) {
        results[1].init();
        tempQuery= query.getSerie(i);
        if ((tempQuery.getNumValues() >= soglie[i]) && (maxvals[i] != 1)) {
          trasformata = new TFourier (tempQuery, numCoefficienti);
          trasformata.calculate();
          tempQueryR= trasformata.getTransformedSerie();

          results[1]= kdTrees[i].rangeQuery (tempQueryR, maxvals[i], classificazione);
          postProcessor= new TPostProcessing (tempQuery, results[1], maxvals[i], pazienti, loader);
          postProcessor.Calculate(0, 2, false, interpolation);
          results[1] = postProcessor.getResult();

          if (allCases) {
            results[0]= results[1];
            allCases= false;
            results[1]= new TLinkedList();
          }
          else intersectLists (results[0], results[1]);
        }
      }
    }
  }

  public void tvstep1rq (boolean normalized, int numCoefficienti, boolean classificazione, boolean interpolation) throws IOException {
    int i;
    TTimeSerie tempQuery, tempQueryR;
    TFourier trasformata;
    boolean allCases;

    if (normalized == true) {
      results[0]= new TLinkedList ();
      results[0].init();
      allCases= true;
      results[1]= new TLinkedList ();

      for (i= 0; i < numSegnali; i++) {
        System.out.println("Feature corrente: Segnale " + segnali[i]);

        results[1].init();
        tempQuery= query.getSerie(i);
        if ((tempQuery.getNumValues() >= soglie[i]) && (maxvals[i] != 1)) {
          trasformata = new TFourier (tempQuery, numCoefficienti);
          trasformata.calculate();
          tempQueryR= trasformata.getTransformedSerie();

          results[1]= TVTrees[i].rangeQueryNormalized (tempQueryR, maxvals[i], ranges[i], classificazione);
          postProcessor= new TPostProcessing (tempQuery, results[1], maxvals[i], pazienti, ranges[i], loader);
          postProcessor.Calculate(0, 2, false, interpolation);
          results[1] = postProcessor.getResult();

          if (allCases) {
            results[0]= results[1];
            allCases= false;
            results[1]= new TLinkedList();
          }
          else intersectLists (results[0], results[1]);
        }
      }
    }
    else {
      results[0]= new TLinkedList ();
      results[0].init();
      allCases= true;
      results[1]= new TLinkedList ();

      for (i= 0; i < numSegnali; i++) {
        results[1].init();
        tempQuery= query.getSerie(i);
        if ((tempQuery.getNumValues() >= soglie[i]) && (maxvals[i] != 1)) {
          trasformata = new TFourier (tempQuery, numCoefficienti);
          trasformata.calculate();
          tempQueryR= trasformata.getTransformedSerie();

          results[1]= TVTrees[i].rangeQuery (tempQueryR, maxvals[i], classificazione);
          postProcessor= new TPostProcessing (tempQuery, results[1], maxvals[i], pazienti, loader);
          postProcessor.Calculate(0, 2, false, interpolation);
          results[1] = postProcessor.getResult();

          if (allCases) {
            results[0]= results[1];
            allCases= false;
            results[1]= new TLinkedList();
          }
          else intersectLists (results[0], results[1]);
        }
      }
    }
  }


  public void tvstep1knn (boolean normalized, int numCoefficienti, boolean interpolation) throws IOException {
    int i,j;
    TTimeSerie queryoriginale=new TTimeSerie();
    TTimeSerie Querysulsegnale;
    TFourier fourier;
    TResultNode Results;

    for (i= 0; i < numSegnali; i++)
    {

      //INIZIALIZZA LA QUERY E CARICALA
      queryoriginale.init();
      loader.loadSingleSerie(idpaziente, iddialisi, segnali[i], queryoriginale, interpolation);
      //TRASFORMALA CON FOURIER DANDO numCoefficients COEFFICIENTI PER LA SERIE (SONO 3 IN PRIMA STESURA)
      fourier = new TFourier(queryoriginale, TVTrees[0].getK());
      fourier.calculate();
      Querysulsegnale = fourier.getTransformedSerie();

//      System.out.println("SERIE ORIGINALE - NUMERO PUNTI: "+queryoriginale.getNumValues());
//      System.out.println("SERIE RIDOTTA - NUMERO PUNTI: "+Querysulsegnale.getNumValues());
//      System.out.println("SOGLIA CONSIDERATA: "+soglie[i]);

      segnaliValidi[i]=false;
      if ((queryoriginale.getNumValues() >= soglie[i]) && (considero[i]==true))
      {
//        System.out.println("--> SEGNALE CONSIDERATO <--");
        segnaliValidi[i]= true;
        TTVNode passoate = new TTVNode(TVTrees[0].getK(), TVTrees[0].getAlpha(),
                                       TVTrees[0].getNumFigli(), true);
        passoate.getCenter().copySerie(Querysulsegnale);
        passoate.setLink(this.idpaziente, this.iddialisi, segnali[i]);

/*        passoate.linkDialisi = this.iddialisi;
        passoate.linkPaziente = this.idpaziente;
        passoate.linkSegnale = segnali[i];
        System.out.println("QUERY SUL SEGNALE --> PAZIENTE:" + idpaziente + " DIALISI:" +
                           iddialisi + " SEGNALE:" + segnali[i]);
*/
        TVTrees[i].setP(K+2);
        TVTrees[i].setSOL1(new TTVNode[TVTrees[i].getP()]);
        TVTrees[i].setSOL2(new double[TVTrees[i].getP()]);
        for (j = 0; j < TVTrees[i].getP(); j++) {
          TVTrees[i].setSOL1(j, new TTVNode(TVTrees[i].getK(), TVTrees[i].getAlpha(),
                                             TVTrees[i].getNumFigli(), true));
          TVTrees[i].setSOL2(j, 999999999);
        }

        results[i] = TVTrees[i].knnQuery(passoate, K+2, normalized, ranges[i], interpolation);
      }
//      else
//        System.out.println("!!! SEGNALE NON CONSIDERATO !!!");
    }

  }

  private void addCase (TCase caso, boolean normalized) {
    double distanza;

    distanza= query.getDistance (caso, weights, ranges, 2, normalized);
    if ((distanza <= maxDist) || (currentK <= K)) {

      maxDist= queryResult.insertSortedAndTrunc (null, caso.getIdPaziente(), caso.getIdDialisi(), 0, distanza, K + 1);
//      queryResult.insertSorted (null, caso.getIdPaziente(), caso.getIdDialisi(), 0, distanza);
//      maxDist= queryResult.trunc (K + 1);
      if (currentK <= K) {
        currentK++;
      }

//      System.out.println("Aggiunto " + caso.getIdPaziente() + " " + caso.getIdDialisi());
    }
//    else System.out.println("Scartato " +  caso.getIdPaziente() + " " + caso.getIdDialisi());
  }

  private void addCaseTvtree (TCase caso, boolean normalized,double distance) {
    queryResult.insertSorted (null, caso.getIdPaziente(), caso.getIdDialisi(), 0, distance);
    queryResult.trunc(K);
  }


  public void step2 (boolean elimina, int[] Soglie, boolean normalized, boolean interpolation) throws IOException {
    TResultNode nodoA;
    TCase Temp;
    int[] soglie;

/*    FileOutputStream outFile;
    OutputStreamWriter outS;
    BufferedWriter outStream;
    int pazienteR, serieR, dialisiR;
    String riga;

    outFile= new FileOutputStream ("CasiCandidatiKNN.txt");
    outS= new OutputStreamWriter (outFile);
    outStream= new BufferedWriter (outS);

    nodoA= results[0].getFirst();

    while (nodoA != null) {
      pazienteR = nodoA.getLinkPaziente();
      serieR = nodoA.getLinkSegnale();
      dialisiR = nodoA.getLinkDialisi();
       riga = String.valueOf(pazienteR) + " " + String.valueOf(serieR) +
           " " + String.valueOf(dialisiR) +
           "   " + String.valueOf(nodoA.getDistance());
       outStream.write(riga, 0, riga.length());
       outStream.newLine();
       nodoA= results[0].getNext();
    }

    outStream.close();
    outS.close();
    outFile.close();
*/

    queryResult = new TLinkedList ();

    root= null;
    soglie= Soglie;
    currentK= 0;
    maxDist= 0;

    nodoA= results[0].getFirst();

    while (nodoA != null) {
      Temp = new TCase(numSegnali, nodoA.getLinkPaziente(),
                       nodoA.getLinkDialisi(), soglie);
      loader.loadCase(Temp, interpolation);

      if (elimina == true) {
        if (Temp.isValid()) {
          addCase(Temp, normalized);
        }
      }
      else {
        addCase(Temp, normalized);
      }

      nodoA= results[0].getNext();
    }
  }

  public void tvstep2knn (boolean elimina, int[] Soglie, boolean normalized, boolean interpolation) throws IOException {
    TResultNode nodoA, nodoC;
    TCase Temp;
    int[] soglie;
    int i,singolosegnale=0;
    boolean trovato;
    TLinkedList listaCasi= new TLinkedList();
    TResultNode Results;
    queryResult = new TLinkedList ();
    root= null;
    soglie= Soglie;
    trovato= false;
    listaCasi.init();

    for(i=0;i<numSegnali;i++)
      if (segnaliValidi[i]==true)
        singolosegnale++;

    i=0;
    while ((trovato == false) && (i < numSegnali)) {
      if ((maxvals[i] != 1) && (segnaliValidi[i] == true))
        trovato= true;
      else
        i++;
    }

    if (trovato == true)
    {
      listaCasi= results[i];
      if (singolosegnale>1) /*DISRTINGUO SE STO ESAMINANDO UN SINGOLO SEGNALE O MENO*/
        segnaliValidi[i]= false;
    }
    nodoA= listaCasi.getFirst();
    while (nodoA != null) {
      i = 0;
      nodoC=null;
      trovato = true;
      while ((trovato == true) && (i < numSegnali)) {
        if ((maxvals[i] == 1)||(this.considero[i]==false))
          trovato = true;
        else
        {
          if (results[i]!=null)
            nodoC = results[i].getFirst();
          trovato = false;
          while ((nodoC != null) && (trovato == false)) {
            if ((nodoC.getLinkPaziente() == nodoA.getLinkPaziente()) &&
                (nodoC.getLinkDialisi() == nodoA.getLinkDialisi()))
              trovato = true;
            else
              nodoC = results[i].getNext();
          }
        }
        i++;
      }
      if (trovato == true)
      {
//        System.out.println("HO TROVATO UN ELEMENTO: E' ("+nodoA.getLinkPaziente()+";"+nodoA.getLinkDialisi()+")");
        Temp = new TCase(numSegnali, nodoA.getLinkPaziente(),
                         nodoA.getLinkDialisi(), soglie);
        loader.loadCase(Temp, interpolation);

        if (elimina == true)
        {
          if (Temp.isValid())
          {
            if (nodoC!=null)
              addCaseTvtree(Temp, normalized,nodoC.getDistance());
            else
              addCaseTvtree(Temp, normalized,0);
//            System.out.println("Aggiunto " + nodoA.getLinkPaziente() + " " + nodoA.getLinkDialisi());
          }
        }
        else
        {
          if (nodoC!=null)
            addCaseTvtree(Temp, normalized,nodoC.getDistance());
          else
            addCaseTvtree(Temp, normalized,0);
//          System.out.println("Aggiunto " + nodoA.getLinkPaziente() + " " + nodoA.getLinkDialisi());
        }
      }
//      else System.out.println("Scartato " +  + nodoA.getLinkPaziente() + " " + nodoA.getLinkDialisi());
      nodoA= listaCasi.getNext();
    }
  }

  public void calculate(boolean normalized, boolean elimina_nonvalidi,
                        boolean transformed, int transformationType,
                        int numCoefficienti, int[] Soglie, boolean classificazione, boolean interpolation) throws IOException {
    step1 (normalized, numCoefficienti, classificazione, interpolation);
    step2 (elimina_nonvalidi, Soglie, normalized, interpolation);
  }

  public TLinkedList getResult () {
    return queryResult;
  }

}

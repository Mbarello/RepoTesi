package retrieval;

import java.io.IOException;
import timeserie.TTimeSerie;
import loader.TGlobalLoader;
import indexing.TLinkedList;
import indexing.TResultNode;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TPostProcessing {
  double epsilon;
  TTimeSerie query;
  TLinkedList Originale, Risultato;
  String nomeFile;
  TGlobalLoader loader;
  TRange range;
  boolean normalized;

  public TPostProcessing(TTimeSerie Query, TLinkedList Results, double Epsilon, int[] Pazienti, TGlobalLoader Loader) throws IOException {
    query= Query;
    Originale= Results;
    Risultato= new TLinkedList ();
    epsilon= Epsilon;
    normalized= false;
    loader= Loader;
  }

  public TPostProcessing(TTimeSerie Query, TLinkedList Results, double Epsilon, int[] Pazienti, TRange Range, TGlobalLoader Loader) throws IOException {
    query= Query;
    Originale= Results;
    Risultato= new TLinkedList ();
    epsilon= Epsilon;
    range= Range;
    normalized= true;
    loader= Loader;
  }

  private void loadSerie (TResultNode Current, TTimeSerie Loaded, boolean interpolation) {
    int paziente, segnale, dialisi;

    paziente = Current.getLinkPaziente();
    segnale = Current.getLinkSegnale();
    dialisi = Current.getLinkDialisi();
    loader.loadSingleSerie (paziente, dialisi, segnale, Loaded, interpolation);
}

  public void Calculate (int K, int sorted, boolean insertOriginalSerie, boolean interpolation) {
    TResultNode current;
    TTimeSerie loaded = new TTimeSerie();
    double dist;
    int I;

    Risultato.init();
    current= Originale.getFirst();
    while (current != null) {
      loadSerie (current, loaded, interpolation);

      if (normalized == false)
        dist= query.getDistance(loaded, 2);
      else
        dist= query.getDistance(loaded, 2, range);

      if (dist <= epsilon) {
        // Creare il nodo senza chiave, oppure inserire la TS completa
        // Scegliere se mettere il risultato gia ordinato, oppure in testa
        if (sorted == 1) {
          if (insertOriginalSerie) {
            Risultato.insertSorted(loaded, current.getLinkPaziente(),
                                   current.getLinkDialisi(),
                                   current.getLinkSegnale(),
                                   dist);
          }
          else {
            Risultato.insertSorted(null, current.getLinkPaziente(),
                                   current.getLinkDialisi(),
                                   current.getLinkSegnale(),
                                   dist);
          }
        }
        else if (sorted == 2) {
            if (insertOriginalSerie) {
              Risultato.insertSortedByKey(loaded, current.getLinkPaziente(),
                                     current.getLinkDialisi(),
                                     current.getLinkSegnale(),
                                     dist);
            }
            else {
              Risultato.insertSortedByKey(null, current.getLinkPaziente(),
                                     current.getLinkDialisi(),
                                     current.getLinkSegnale(),
                                     dist);
            }
        }
        else {
          if (insertOriginalSerie) {
            Risultato.insertHead(loaded, current.getLinkPaziente(),
                                   current.getLinkDialisi(),
                                   current.getLinkSegnale(),
                                   dist);
          }
          else {
            Risultato.insertHead(null, current.getLinkPaziente(),
                                   current.getLinkDialisi(),
                                   current.getLinkSegnale(),
                                   dist);
          }
        }
      }
      current= Originale.getNext();
    }
    if (K > 0) {
      Risultato.trunc(K);
    }
  }

  public TLinkedList getResult () {
    return Risultato;
  }
}

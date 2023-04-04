package retrieval;

import cases.MacroCase;
import loader.TGlobalLoader;
import indexing.TLinkedList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Giorgio Leonardi
 * @version 1.1
 */
public class MacroCaseKnn {
  class MCaseNode {
    MacroCase value;
    MCaseNode next;
  }

  MacroCase MCaseQuery;
  MCaseNode head;

  int nPrec, nSucc;
  double[] weights;
  TGlobalLoader loader;
  boolean interpolation;

  TLinkedList results;

  public MacroCaseKnn(int Patient, int Session, int Prec, int Succ, double[] Weights, TGlobalLoader Loader, boolean Interpolation) {
    MCaseQuery = new MacroCase (Patient, Session, Prec, Succ);
    nPrec= Prec;
    nSucc= Succ;
    weights= Weights;
    loader= Loader;
    head= null;
    interpolation= Interpolation;
  }

  public void addMacroCase (int Patient, int Session) {
    MCaseNode temp = new MCaseNode();
    temp.value = new MacroCase (Patient, Session, nPrec, nSucc);
    temp.next= head;
    head= temp;
  }

  public TLinkedList calculate (TRange[] Range) {
    results = new TLinkedList ();
    MCaseNode temp;
    double dist;

    try {
      loader.loadMacroCase(MCaseQuery, interpolation);

      temp= head;
      while (temp != null) {
        loader.loadMacroCase(temp.value, interpolation);

        dist= MCaseQuery.getDistance(temp.value, weights, 2, Range);
        results.insertSorted(null, temp.value.getStartPatient(), temp.value.getStartSession(), 0, dist);

        temp= temp.next;
      }
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }

    return results;
  }
}

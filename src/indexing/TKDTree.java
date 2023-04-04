package indexing;

import retrieval.TRange;
import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TKDTree {
  private TNodo root;
  private TResultNode head;
  private TLinkedList risultati;
  private TRange range;
  int K, maxLength;

  public TKDTree() {
    risultati = new TLinkedList ();
    root= null;
    K= -1;
    range = new TRange (0);
    maxLength= 0;
  }

  public void checkMaxLength (int N) {
    if (N > maxLength) maxLength= N;
  }

  public void setMaxLength (int N) {
    maxLength= N;
  }

  public int getMaxLength () {
    return maxLength;
  }

  public TLinkedList getResult () {
    return risultati;
  }

  public void init () {
    root= null;
    K= -1;
  }

  private int getDiscriminator (int n) {
    return n % K;
  }

  public TNodo getRoot () {
    return root;
  }

  public void addKey (TTimeSerie Key, int LinkPaziente, int LinkDialisi, int LinkSegnale, boolean classificato) {
    TNodo newNode;
    TNodo currentNode;
    int level, D;
    boolean inserted;

    newNode= new TNodo (Key, LinkPaziente, LinkDialisi, LinkSegnale);
    newNode.classificato= classificato;
    range.checkRange(Key);

    if (root == null) {
      root= newNode;
      K= Key.getNumValues();
    }
    else {
      currentNode= root;
      level= 0;
      inserted= false;

      while (inserted == false) {
        D= getDiscriminator (level);
        if (currentNode.getKey().getValue(D) > newNode.getKey().getValue(D)) {
          if (currentNode.left == null) {
            currentNode.left= newNode;
            inserted= true;
          }
          else {
            currentNode= currentNode.left;
            level++;
          }
        }
        else {
          if (currentNode.right == null) {
            currentNode.right= newNode;
            inserted= true;
          }
          else {
            currentNode= currentNode.right;
            level++;
          }
        }
      }
    }
  }

  public TNodo searchKey (TTimeSerie Key) {
    TNodo currentNode;
    currentNode= root;

    return currentNode;
  }

  public void removeKey (TTimeSerie Key) {

  }

  private void execQuery (TNodo X, TTimeSerie Key, double Distance, int level, boolean classificazione) {
    int D;
    double dist;

    if (X != null) {
      D= getDiscriminator (level);
      if ((!classificazione) || (classificazione & X.classificato)) {
        dist= Key.getDistance(X.getKey(), 2);
        if (dist <= Distance) {
          risultati.insertHead (null, X.getLinkPaziente(), X.getLinkDialisi(), X.getLinkSegnale(), dist);
        }
      }
      if (X.getKey().getValue(D) > (Key.getValue(D) - Distance)) {
        execQuery (X.left, Key, Distance, (level + 1), classificazione);
      }
      if (X.getKey().getValue(D) < (Key.getValue(D) + Distance)) {
        execQuery (X.right, Key, Distance, (level + 1), classificazione);
      }
    }
  }

  private void execNormalizedQuery (TNodo X, TTimeSerie Key, double Distance, int level, TRange Range, boolean classificazione) {
    int D;
    double dist;

    if (X != null) {
      D= getDiscriminator (level);
      if ((!classificazione) || (classificazione & X.classificato)) {
        dist= Key.getDistance(X.getKey(), 2, Range);
        if (dist <= Distance) {
          risultati.insertHead (null, X.getLinkPaziente(), X.getLinkDialisi(), X.getLinkSegnale(), dist);
        }
      }
      if ((X.getKey().getValue(D) / Range.getRange() * java.lang.Math.pow(maxLength, 0.5)) >
          ((Key.getValue(D) / Range.getRange() * java.lang.Math.pow(maxLength, 0.5) - Distance))) {
        execNormalizedQuery (X.left, Key, Distance, (level + 1), Range, classificazione);
      }
      if ((X.getKey().getValue(D) / Range.getRange() * java.lang.Math.pow(maxLength, 0.5)) <
          ((Key.getValue(D) / Range.getRange() * java.lang.Math.pow(maxLength, 0.5) + Distance))) {
        execNormalizedQuery (X.right, Key, Distance, (level + 1), Range, classificazione);
      }
    }
  }

  public TLinkedList rangeQuery (TTimeSerie Key, double Distance, boolean classificazione) {
    risultati.init();
    head= null;
    execQuery (root, Key, Distance, 0, classificazione);

    return risultati;
  }

  public TLinkedList rangeQuery (TTimeSerie Key, double Distance, TRange Range, boolean classificazione) {
    risultati.init();
    head= null;
    execNormalizedQuery (root, Key, Distance, 0, Range, classificazione);

    return risultati;
  }

}

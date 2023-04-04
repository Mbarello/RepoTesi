package indexing;

import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TResultNode {
  private TTimeSerie chiave;
  private int linkPaziente, linkDialisi, linkSegnale;
  double distance;
  TResultNode next;

  public TResultNode() {
    chiave= null;
    linkPaziente= -1;
    linkDialisi= -1;
    linkSegnale= -1;
    next= null;
  }

  public TResultNode (TTimeSerie Key) {
    chiave= null;

    linkPaziente= -1;
    linkDialisi= -1;
    linkSegnale= -1;
    next= null;

    if (Key != null) {
      chiave= new TTimeSerie (Key.getNumValues());
      chiave.copySerie (Key);
    }
  }

  public TResultNode (TTimeSerie Key, int LinkPaziente, int LinkDialisi, int LinkSegnale) {
    chiave= null;

    linkPaziente= LinkPaziente;
    linkDialisi= LinkDialisi;
    linkSegnale= LinkSegnale;
    next= null;

    if (Key != null) {
      chiave= new TTimeSerie (Key.getNumValues());
      chiave.copySerie (Key);
    }
  }

  public TResultNode (TTimeSerie Key, int LinkPaziente, int LinkDialisi, int LinkSegnale, double Distance) {
    chiave= null;
    linkPaziente= LinkPaziente;
    linkDialisi= LinkDialisi;
    linkSegnale= LinkSegnale;
    next= null;
    distance= Distance;

    if (Key != null) {
      chiave= new TTimeSerie (Key.getNumValues());
      chiave.copySerie (Key);
    }
  }

  public void setDistance (double Distance) {
    distance= Distance;
  }

  public double getDistance () {
    return distance;
  }

  public void setKey (TTimeSerie Key) {
    if (chiave != null)
      chiave= new TTimeSerie (Key.getNumValues());
    chiave.copySerie (Key);
  }

  public void setLink (int LinkPaziente, int LinkDialisi, int LinkSegnale) {
    linkPaziente= LinkPaziente;
    linkDialisi= LinkDialisi;
    linkSegnale= LinkSegnale;
  }

  public TTimeSerie getKey () {
    return chiave;
  }

  public int getLinkPaziente () {
    return linkPaziente;
  }

  public int getLinkDialisi () {
    return linkDialisi;
  }

  public int getLinkSegnale () {
    return linkSegnale;
  }

  public TResultNode getNext() {
    return next;
  }

}

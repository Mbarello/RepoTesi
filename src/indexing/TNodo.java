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

public class TNodo {
  private TTimeSerie chiave;
  private int linkPaziente, linkDialisi, linkSegnale;
  public TNodo left, right;
  public boolean classificato;

  public TNodo() {
    chiave= new TTimeSerie ();
    linkPaziente= -1;
    linkDialisi= -1;
    linkSegnale= -1;
    left= null;
    right= null;
  }

  public TNodo (TTimeSerie Key) {
    chiave= new TTimeSerie ();
    linkPaziente= -1;
    linkDialisi= -1;
    linkSegnale= -1;
    left= null;
    right= null;

    chiave.copySerie (Key);
  }

  public TNodo (TTimeSerie Key, int LinkPaziente, int LinkDialisi, int LinkSegnale) {
    chiave= new TTimeSerie ();
    linkPaziente= LinkPaziente;
    linkDialisi= LinkDialisi;
    linkSegnale= LinkSegnale;
    left= null;
    right= null;

    chiave.copySerie (Key);
  }

  public void setKey (TTimeSerie Key) {
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

  public TNodo getLeft() {
    return left;
  }

  public TNodo getRight() {
    return right;
  }
}

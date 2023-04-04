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

public class TLinkedList {
  TResultNode head, current, previous;
  int numNodes;

  public TLinkedList() {
    head = current = previous = null;
    numNodes= 0;
  }

  public void init() {
    head = current = previous = null;
    numNodes= 0;
  }

  public void insertHead (TTimeSerie Index, int LinkPaziente, int LinkDialisi, int LinkSegnale, double Distance) {
    TResultNode newNode = new TResultNode (Index, LinkPaziente, LinkDialisi, LinkSegnale, Distance);

    newNode.next= head;
    head= newNode;
    numNodes++;
  }

  public void insertSorted (TTimeSerie Index, int LinkPaziente, int LinkDialisi, int LinkSegnale, double Distance) {
    TResultNode P1, P2;
    TResultNode X = new TResultNode (Index, LinkPaziente, LinkDialisi, LinkSegnale, Distance);

    if (head == null) {
      head= X;
      head.next= null;
      numNodes++;
    }
    else if (head.distance >= X.distance) {
      X.next= head;
      head= X;
      numNodes++;
    }
    else {
      P1= head;
      P2= P1.next;

      while ((P2 != null) && (P2.distance < X.distance)) {
        P1= P2;
        P2= P2.next;
      }
      X.next= P2;
      P1.next= X;
      numNodes++;
    }
  }

  public double insertSortedAndTrunc (TTimeSerie Index, int LinkPaziente, int LinkDialisi, int LinkSegnale, double Distance, int K) {
    TResultNode P1, P2;
    TResultNode X = new TResultNode (Index, LinkPaziente, LinkDialisi, LinkSegnale, Distance);
    int I;
    double currentDist;

    if (head == null) {
      head= X;
      head.next= null;
      numNodes++;
      return X.getDistance();
    }
    else if (head.distance >= X.distance) {
      X.next= head;
      head= X;

      P1= head;
      I= 1;
      currentDist= P1.getDistance();
      while ((P1 != null) && (I < K)) {
        I++;
        currentDist= P1.getDistance();
        P1= P1.next;
      }
      if (P1 != null) {
        P1.next = null;
        currentDist= P1.getDistance();
      }
      numNodes= I - 1;
      return currentDist;
    }
    else {
      P1= head;
      P2= P1.next;
      I= 1;

      while ((P2 != null) && (P2.distance < X.distance)) {
        P1= P2;
        P2= P2.next;
        I++;
      }
      X.next= P2;
      P1.next= X;
      P1= X;
      currentDist= P1.getDistance();
      I++;

      while ((P1 != null) && (I < K)) {
        I++;
        currentDist= P1.getDistance();
        P1= P1.next;
      }
      if (P1 != null) {
        P1.next = null;
        currentDist= P1.getDistance();
      }
      numNodes= I - 1;
      return currentDist;
    }
  }

  public void insertSortedByKey (TTimeSerie Index, int LinkPaziente, int LinkDialisi, int LinkSegnale, double Distance) {
    TResultNode P1, P2;
    TResultNode X = new TResultNode (Index, LinkPaziente, LinkDialisi, LinkSegnale, Distance);

    if (head == null) {
      head= X;
      head.next= null;
    }
    else if ((head.getLinkPaziente() > X.getLinkPaziente()) ||
             ((head.getLinkPaziente() == X.getLinkPaziente()) &&
              (head.getLinkDialisi() > X.getLinkDialisi()))) {
      X.next= head;
      head= X;
    }
    else {
      P1= head;
      P2= P1.next;

      while ((P2 != null) && (P2.getLinkPaziente() < X.getLinkPaziente())) {
        P1= P2;
        P2= P2.next;
      }
      while ((P2 != null) && (P2.getLinkPaziente() == X.getLinkPaziente())
             && (P2.getLinkDialisi() < X.getLinkDialisi())) {
        P1= P2;
        P2= P2.next;
      }
      if (P2 == null) {
        if (P1.getLinkPaziente() > X.getLinkPaziente()) {
          X.next= P1;
          head= X;
        }
        else if (P1.getLinkPaziente() < X.getLinkPaziente()) {
          X.next= null;
          P1.next= X;
        }
        else {
          if (P1.getLinkDialisi() > X.getLinkDialisi()) {
            X.next= P1;
            head= X;
          }
          else {
            X.next= null;
            P1.next= X;
          }
        }
      }
      else {
        X.next= P2;
        P1.next= X;
      }
    }
  }

  public TResultNode getFirst () {
    current= previous= head;
    return (current);
  }

  public TResultNode getNext () {
    if ((previous != current) && (previous != null)) previous= previous.next;
    if (current != null) current= current.next;
    return (current);
  }

  public double trunc (int K) {
    TResultNode Temp;
    int I;
    double currentDist;

    Temp= head;
    I= 1;
    currentDist= 0;
    while ((Temp != null) && (I < (K - 1))) {
      I++;
      currentDist= Temp.getDistance();
      Temp= Temp.next;
    }
    if (Temp != null) {
      Temp.next = null;
      return Temp.getDistance();
    }
    return currentDist;
  }

  public void truncCurrent () {
    if (current == head) {
      this.init();
    }
    else {
      previous.next= null;
      current= null;
    }
  }

  public void merge (TLinkedList List) {
    TResultNode Temp, head2;

    if (head != null) {
      Temp = head;
      head2 = List.getFirst();
      while (Temp.next != null)
        Temp= Temp.next;
      Temp.next= head2;
    }
  }

  public void deleteCurrent () {
    if (current == head) {
      current= head.next;
      previous= current;
      head.next= null;
      head= current;
    }
    else {
      previous.next= current.next;
      current.next= null;
      current= previous.next;
    }
  }

  public boolean searchKey (int patient, int session) {
    TResultNode temp;

    temp= this.getFirst();
    while (temp != null) {
      if ((temp.getLinkPaziente() == patient) && (temp.getLinkDialisi() == session)) return true;
      temp= temp.next;
    }
    return false;
  }


}

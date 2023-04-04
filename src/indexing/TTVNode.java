package indexing;

import timeserie.TTimeSerie;
import retrieval.TRange;

/**
 * <p>Title: TV-Tree implementation</p>
 * <p>Description: Implementing TV-Tree and major algorithms (k-nearest neighbour search too)</p>
 * <p>Copyright: 2004</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.1
 */

public class TTVNode {
  boolean isLeaf; /*Indicatore per verificare se il nodo e una foglia o meno*/
  TTimeSerie center; /*E' la TIMESERIE da salvare nel nodo - punto nello spazio K-dimensionale*/
  double radius; /*Numero reale maggiore di 0*/
  boolean[] activeDims; /*Vettore delle dimensioni attive*/
  int numChilds;  /*Figli attuali posseduti e */
  int maxNumChilds; /*Massimo numero di figli che il TV-TREE puo avere*/
  TTVNode[] childs; /*Vettore dei figli*/
  TTVNode father; /*Riferimento al padre*/
  boolean firstsplitting; /*DEFINISCE se in caso di splitting e la prima volta che occorre o meno*/
  int linkPaziente; /*Collegamento al paziente cui si riferisce la serie temporale*/
  int linkDialisi; /*Collegamento alla dialisi cui si riferisce la serie temporale*/
  int linkSegnale; /*Collegamento al segnale cui si riferisce la serie temporale*/
  public boolean classificato;

  public TTVNode(int k, int alpha,int NumChilds, boolean IsLeaf) {
    this.maxNumChilds= NumChilds; /*Numero massimo dei figli*/
    this.isLeaf = IsLeaf; /*E' foglia o no*/
    this.center = new TTimeSerie(k); /*Serie temporale*/
    this.radius=0;
    this.numChilds=0;
    this.father=null;
    this.firstsplitting=true;
    this.linkDialisi=-1;
    this.linkPaziente=-1;
    this.linkSegnale=-1;

    int I;
    this.activeDims = new boolean[alpha]; /*Dimensioni attive del nodo MASSIMIZZATE con k*/
    for (I= 0; I < alpha; I++)
      this.activeDims[I] = true;
    this.childs=new TTVNode[NumChilds];
    for (I= 0; I < this.numChilds; I++)
      this.childs[I]= null;
  }

  /*Setta un nodo con le impostazioni standard di oggetto attribuendogli un vettore*/
  public void init(TTimeSerie vettore) {
    this.getCenter().init();
    this.setCenter(vettore);
    this.radius=0; /*VALORE PROVVISORIO DI RAGGIO pari a 0: e un punto nello spazio*/
    this.isLeaf=false;
  }

  public void initRootNode(int k) {
    int i;
    center.init();
    for(i=0;i<k;i++)
      center.addValue(i,0);
    this.setCenter(center);
    this.isLeaf=false;
    this.radius=-1; /*VALORE PROVVISORIO DI RAGGIO pari a -1: poi incrementa con l'inserimento*/
  }

  public void setKey (TTimeSerie Key) {
    this.center.copySerie (Key);
  }

  public TTimeSerie getKey () {
    return this.center;
  }

  public void setLink (int LinkPaziente, int LinkDialisi, int LinkSegnale) {
    this.linkPaziente= LinkPaziente;
    this.linkDialisi= LinkDialisi;
    this.linkSegnale= LinkSegnale;
  }

  public int getLinkPaziente () {
    return this.linkPaziente;
  }

  public int getLinkDialisi () {
    return this.linkDialisi;
  }

  public int getLinkSegnale () {
    return this.linkSegnale;
  }



  /*Inserisce un vettore come centro di un nodo*/
  public void setCenter (TTimeSerie Center) {
    this.center.copySerie(Center);
  }

  /*Definisce il raggio*/
  public void setRadius (double Radius) {
    this.radius= Radius;
  }

  /*Restituisce il punto spaziale rappresentato dal nodo*/
  public TTimeSerie getCenter () {
    return this.center;
  }

  /*Restituisce il raggio*/
  public double getRadius () {
    return this.radius;
  }

  /*Restituisce il numero di figli nel nodo*/
  public int getNumChilds () {
    return this.numChilds;
  }

  /*Restituisce il numero di dimensioni massime*/
  public int getNumDimensions () {
    return this.activeDims.length;
  }

  /*Controlla se la dimensione I-esima e una dimensione attiva o meno*/
  public boolean isActiveDim (int I) {
    if (I<=this.activeDims.length)
       return this.activeDims[I];
    System.out.println("Errore - dimensione attiva non contemplata.");
    return false;
  }

  /*Attiva la dimensione in posizione Dimension*/
  public int addActiveDimension (int Dimension) {
    if (Dimension<=this.activeDims.length)
      {
        this.activeDims[Dimension] = true;
        return 0;
      }
    return -1;
  }

  /*Disattiva la dimensione in posizione Dimension*/
  public int removeActiveDimension (int Dimension) {
    if (Dimension<=this.activeDims.length)
      {
        this.activeDims[Dimension] = false;
        return 0;
      }
    return -1;
  }

  /*Metodo che, evocato su di un nodo, ne restituisce la distanza attiva da un altro punto rappresentato dal centro
   del nodo Node.
   La definizione della distanza attiva standard prevede che le coppie di valori siano sottratte ed elevata alla potenza
   P=2, pertanto l'intero P in input varra 2.*/
  public double getActiveDistance (TTVNode Node, int P) {
    int i, N;
    double distanza=0;
    TTimeSerie TS;
    TS= Node.getCenter();
    N= this.center.getNumValues();
    if (TS.getNumValues() < N) N= TS.getNumValues();
    for (i= 0; i < N; i++) {
      if (this.activeDims[i]) { /*Il controllo viene effettuato solamente sulle dimensioni attive del nodo!!!*/
        distanza= distanza + java.lang.Math.pow(java.lang.Math.abs(this.center.getValue (i) - TS.getValue(i)), P);
      }
    }
    distanza= java.lang.Math.pow(distanza, (double)1.0/P);
    return distanza; /*distanza attiva del nodo*/
  }

  /*DISTANZA DA FIGLIO A PADRE - LE DIMENSIONI ATTIVE SONO IGNORATE*/
  public double getDistance2 (TTVNode Node, int P) {
    int i, N;
    double distanza=0;
    TTimeSerie TS;
    TS= Node.getCenter();
    N= this.center.getNumValues();
    if (TS.getNumValues() < N) N= TS.getNumValues();
    for (i= 0; i < N; i++)
        distanza= distanza + java.lang.Math.pow(java.lang.Math.abs(this.center.getValue (i) - TS.getValue(i)), P);
    distanza= java.lang.Math.pow(distanza, (double)1.0/P);
    return distanza; /*distanza del nodo DAL PADRE*/
  }


  /*Metodo che, evocato su di un nodo, ne restituisce la distanza attiva da un altro punto rappresentato da TS.
    La definizione della distanza attiva standard prevede che le coppie di valori siano sottratte ed elevata alla potenza
    P=2, pertanto l'intero P in input varra 2.*/
  public double getActiveDistance (TTimeSerie TS, int P) {
    int i, N;
    double distanza=0;
    N= this.getCenter().getNumValues();
    if (TS.getNumValues() < N) N= TS.getNumValues();
    for (i= 0; i < N; i++) {
      if (this.activeDims[i]) {
        distanza= distanza + java.lang.Math.pow(java.lang.Math.abs(this.getCenter().getValue (i) - TS.getValue(i)), P);
      }
    }
    distanza= java.lang.Math.pow(distanza, (double)1.0/P);
    return distanza;
  }

  /*Restituisce il figlio in posizione N*/
  public TTVNode getChild (int N) {
    if (N<=this.numChilds) {
      return this.childs[N];
    }
    return null;
  }

  public int getLengthChilds () {
    return this.childs.length;
  }

  /*Restituisce true se il nodo e una foglia - altrimenti false*/
  public boolean IsLeaf () {
    return this.isLeaf;
  }

  //Procedura che controlla se un nodo foglia e pieno e deve essere splittato: true se da splittare
  public boolean IsLeafFull() {
    if (this.IsLeaf())
     {
       return this.getNumChilds()==this.maxNumChilds;
     }
    return false;
  }

  //Procedura che controlla se un nodo interno ha un figlio libero: ritorna pos. del 1 figlio libero oppure -1
  public int IsNodoFull() {
    int i;
    boolean trovato=false;
    for(i=0;(i<this.maxNumChilds && trovato==false);i++)
      if (this.childs[i]==null) trovato=true;
    if (trovato==true) return i-1;
    else return -1;
  }

  public boolean IsInRegion(TTVNode nodoB) {
    return (this.getActiveDistance(nodoB,2)<=this.getRadius());
  }

  public boolean Intersect(TTVNode nodoB) {
    if (this.getActiveDistance(nodoB,2)<=(this.getRadius()+nodoB.getRadius()))
        return true;
    return false;
  }

  public boolean IntersectNormalized(TTVNode nodoB,TRange Range) {
    if (this.getCenter().getDistance(nodoB.getCenter(),2,Range)<=(this.getRadius()+nodoB.getRadius()))
      return true;
    return false;
  }


  //Calcolo di exp(v) utile per il branch selection
  public double exp(TTimeSerie v) {
    if (this.getActiveDistance(v,2)<=this.getRadius()) return 0;
    return this.getActiveDistance(v,2)-this.getRadius();
  }

  //Restituisce parte del calcolo discriminatorio in caso di nodi uguali in fase di splitting
  public double Conservative(TTVNode branchN2) {
    return this.getRadius()+branchN2.getRadius()-this.getActiveDistance(branchN2,2);
  }

  //Calcola la distanza minima del vettore v dalla regione del nodo N
  public double min(TTimeSerie v) {
    if (this.getActiveDistance(v,2)<=this.getRadius()) return 0;
    return this.getActiveDistance(v,2)-this.getRadius();
  }

  //Calcola la distanza minima del vettore v dalla regione del nodo N
  public double minNormalized(TTimeSerie v,TRange range) {
  if (this.getCenter().getDistance(v,2,range) <= this.getRadius()) return 0;
  return this.getCenter().getDistance(v,2,range) - this.getRadius();
}


  //Calcola la distanza massima del vettore v dalla regione del nodo N
  public double max(TTimeSerie v) {
    return this.getActiveDistance(v,2)+this.getRadius();
  }

  //Ricalcola il raggio di un nodo considerando i suoi figli
  public void RiCalcolaRaggioDaFigli() {
    int i;
    this.setRadius(-1);
    for(i=0;i<this.getNumChilds();i++)
      if (this.getRadius() < this.getActiveDistance(this.getChild(i), 2) + this.getChild(i).getRadius())
        this.radius = this.getActiveDistance(this.getChild(i), 2) + this.getChild(i).getRadius();
  }

}

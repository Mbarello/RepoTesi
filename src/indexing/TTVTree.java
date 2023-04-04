package indexing;


/**
 * <p>Title: TV-Tree implementation</p>
 * <p>Description: Implementing TV-Tree and major algorithms (k-nearest neighbour search too)</p>
 * <p>Copyright: 2004</p>
 * <p>Company: </p>
 * @author Marco Sotgiu
 * @version 1.1
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.Vector;
import java.io.IOException;
import java.lang.NumberFormatException;
import retrieval.TRange;
import indexing.TTVNode;
import timeserie.TTimeSerie;
import loader.TGlobalLoader;

public class TTVTree {
  TTVNode root;
  int k; //Dimensione delle serie - numero di punti k-dimensionali ospitabili nel nodo
  int alpha; //Numero delle dimensioni attive
  int numfigli; //Massimo numero comune di figli che ogni nodo puo avere
  int p; //Numero di ricercati con la Query KNN
  TTVNode[] SOL1; //Vettore dei risultati per la KNN
  double[] SOL2; // Vettore delle distanze per la KNN
  TTVNode[] SOL3; //Vettore dei risultati per la RANGEQUERY
  int indiceSOL3=0;
  int maxLength;

  public TLinkedList risultati;
  public TResultNode head;
  public TRange range;
  public TGlobalLoader loader;

  public TTVTree(int punti,int maxfigli,int alpha,TGlobalLoader l) {
    int i;
    this.k=punti;
    this.numfigli=maxfigli;
    this.alpha=alpha;
    TTimeSerie data;
    this.root= new TTVNode(punti,alpha,maxfigli,true); //Nodo radice - all'inizio e una foglia
    data=new TTimeSerie();
    data.init();
    for(i=0;i<punti;i++)
      data.addValue(i+1,0); //IMMAGINIAMO CHE TUTTE LE TIMESERIE ABBIANO AL MASSIMO 1 VALORE PUNTO X,Y
    this.root.setCenter(data); //INIZIALIZZAZIONE A 0 DELLA RADICE
    this.maxLength=0;
    this.risultati=new TLinkedList();
    this.range = new TRange (0);
    this.SOL3=new TTVNode[300];
    this.p=0;
    this.loader=l;
  }

  public void addKey(TTimeSerie indice, int paziente, int dialisi, int segnale, boolean classificato) {
    TTVNode nodo;
    nodo=new TTVNode(this.k, this.alpha, this.numfigli, true);
    nodo.init(indice);
    nodo.linkDialisi=dialisi;
    nodo.linkPaziente=paziente;
    nodo.linkSegnale=segnale;
    nodo.classificato= classificato;
    this.range.checkRange(indice);
    this.InsertInTree(nodo);
  }

  public void checkMaxLength (int N) {
    if (N > this.maxLength) this.maxLength= N;
  }

  public void setMaxLength (int N) {
    this.maxLength= N;
  }

  public int getMaxLength () {
    return this.maxLength;
  }

  public TTVNode getRoot () {
    return this.root;
  }

  //Procedura che dato un vettore e un nodo inserisce il vettore nel primo figlio libero
  //0 se tutto ok, -1 se ci sono stati degli errori
  private int InsertInNodeNotFull(TTVNode l,TTVNode v) {
    int i,j,dimact;
    double temp,dimacttemp;

    if (l.IsNodoFull()==-1) return -1;
    l.childs[l.IsNodoFull()]=v;
    v.father=l;
    l.numChilds++;

    //RICALCOLO DEL CENTRO DEL PADRE E DELLE DIMENSIONI ATTIVE DEL PADRE
    for(i=0;i<v.getCenter().getNumValues()&&l.father!=null;i++)
    {
      temp=0;
      dimacttemp=l.getChild(0).getCenter().getValue(0);
      for(j=0,dimact=0;j<l.getNumChilds();j++)
      {
        temp = temp + l.getChild(j).getCenter().getValue(i);
        if (l.getChild(j).getCenter().getValue(i)==dimacttemp)
          dimact++;
      }
      if (dimact!=j-1)
        l.addActiveDimension(i); //AGGIUNGI LA DIMENSIONE ATTIVA - LE COORDINATE I-ESIME DEI FIGLI NON SONO TUTTE UGUALI
      else
        l.removeActiveDimension(i); //RIMUOVI LA DIMESIONE ATTIVA - LE COORDINATE I-ESIME DEI FIGLI SONO TUTTE UGUALI
      if (l.getCenter().getPoint(i)!=null)
      {
        l.getCenter().getPoint(i).setStamp(i);
        l.getCenter().getPoint(i).setValue(temp / l.getNumChilds());
      }
      else
        l.getCenter().addValue(i, temp / l.getNumChilds());
    }
    //ESTENSIONE DEL VALORE DEL RAGGIO DEL NODO IN FASE DI INSERIMENTO
    for(;l!=null;l=l.father)
      l.RiCalcolaRaggioDaFigli();
    return 0;
    // l.activeDims[l.numChilds]=true; /*NON e VERO*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*CI SONO DEGLI ERRORI MA MEGLIO LASCIARE COSI SINO A QUANDO NON SI CAPISCE COME FUNZIONANO LE DIM ATT.*/
    /*ALGORITMO DI SISTEMAZIONE DELLE DIMENSIONI ATTIVE IN CORRISPONDENZA CON I VALORI DELLE SERIE*/
    /*CONFRONTA I VALORI DEI PUNTI DELLA SERIE DEI FIGLI CON I PUNTI DELLA SERIE DEL VETTORE V*/
    /*for(i=0;i<l.numChilds-1;i++)
      for(j=0;j<l.childs[l.numChilds].center.getNumValues() || j<l.childs[i].center.getNumValues();j++)
        if (l.activeDims[j]==true)
          l.activeDims[j]= (l.childs[i].center.getValue(j) == l.childs[i+1].center.getValue(j));
    */
  }

  //Cerca nell'albero T i primi p nodi simili a v e pone la soluzione nel vettore dei nodi SOL e nel vettore delle distanze SOL2
  public void NNSearch(TTVNode T,TTimeSerie v) {
    int i;
    NNSearch1(T,v);
  }

  private void NNSearch1(TTVNode T, TTimeSerie v) {
    boolean done;
    int i,j,r=T.getNumChilds(); //INIZIALIZZAZIONE DI r
    TTVNode TEMP;
    TTVNode[] copia=new TTVNode[T.getNumChilds()];
    double[] SOL3=new double[T.getNumChilds()];
    double TEMP2;

    if (T.IsLeaf()==false&&T.getNumChilds()==0&&T.getActiveDistance(v, 2) < this.SOL2[this.p-1])
        NNInsert(T,v);
    else {
      if (T.IsLeaf()==false&&T.getNumChilds()==0)
        r=0;
      else
      {
        for(i=0;i<T.getNumChilds();i++)
          {
            SOL3[i] = T.getChild(i).min(v);
            copia[i]=T.getChild(i);
          }
        for(i=0;i<T.getNumChilds();i++)
            for(j=i;j<T.getNumChilds();j++)
              if (SOL3[j]<SOL3[i])
              {
                TEMP2=SOL3[i];
                SOL3[i]=SOL3[j];
                SOL3[j]=TEMP2;

                TEMP=copia[i];
                copia[i]=copia[j];
                copia[j]=TEMP;
              }
      }
      done=false; i=0;
      for(;((i<r) && (!done)) && (i<T.getNumChilds());)
      {
        NNSearch(copia[i],v);
        if (i<T.getNumChilds()-1)
          if (this.SOL2[this.p-1] < copia[i+1].min(v))
              done=true;
        i++;
      }
    }
  }

  private void NNInsert(TTVNode vec,TTimeSerie q)
  {
    int i,j;
    double temp=0;
    double[] SOL3=new double[this.SOL1.length];
    TTVNode temp2;

    for(i=this.SOL1.length-1;i>0;i--)
      {
        this.SOL1[i] = this.SOL1[i - 1];
        this.SOL2[i] = this.SOL2[i - 1];
      }
    this.SOL1[0]=vec;
    this.SOL2[0]=vec.getActiveDistance(q,2);
    for(i=0;i<this.SOL1.length;i++)
      if (this.SOL1[i].center.getPoint(0)!=null)
        SOL3[i]=this.SOL1[i].getActiveDistance(q,2);
      else
        SOL3[i]=9999999;
    for(i=0;i<SOL3.length;i++)
      for(j=i;j<SOL3.length;j++)
        if (SOL3[j]<SOL3[i])
        {
           temp=SOL3[i];
           SOL3[i]=SOL3[j];
           SOL3[j]=temp;

           temp2=this.SOL1[i];
           this.SOL1[i]=this.SOL1[j];
           this.SOL1[j]=temp2;

           temp=SOL2[i];
           SOL2[i]=SOL2[j];
           SOL2[j]=temp;
        }
  }

  //Cerca nell'albero T i primi p nodi simili a v e pone la soluzione nel vettore dei nodi SOL e nel vettore delle distanze SOL2
  public void NNSearchNormalized(TTVNode T,TTVNode v,TRange range,TTimeSerie TQRY, boolean interpolation) {
    int i;
    NNSearch1Normalized(T,v,TQRY, interpolation);
  }
  private void NNSearch1Normalized(TTVNode T, TTVNode v,TTimeSerie qr, boolean interpolation) {
    boolean done;
    int i,j,r=T.getNumChilds(); //INIZIALIZZAZIONE DI r
    TTVNode TEMP;
    TTVNode[] copia=new TTVNode[T.getNumChilds()];
    double[] SOL3=new double[T.getNumChilds()];
    double TEMP2;

    if ((T.IsLeaf()==false&&T.getNumChilds()==0)&&((T.getCenter().getDistance(v.getCenter(),2,range)) < this.SOL2[this.p-1]))
      NNInsertNormalized(T,v,range,qr, interpolation);
    else {
      if (T.IsLeaf()==false&&T.getNumChilds()==0)
        r=0;
      else
      {
        for(i=0;i<T.getNumChilds();i++)
        {
          SOL3[i] = T.getChild(i).minNormalized(v.getCenter(),range);
          copia[i]=T.getChild(i);
        }
        for(i=0;i<T.getNumChilds();i++)
          for(j=i;j<T.getNumChilds();j++)
            if (SOL3[j]<SOL3[i])
            {
              TEMP2=SOL3[i];
              SOL3[i]=SOL3[j];
              SOL3[j]=TEMP2;

              TEMP=copia[i];
              copia[i]=copia[j];
              copia[j]=TEMP;
            }
      }
      done=false; i=0;
      for(;((i<r) && (!done)) && (i<T.getNumChilds());)
      {
        NNSearchNormalized(copia[i],v,range,qr, interpolation);
        if (i<T.getNumChilds()-1)
          if (this.SOL2[this.p-1] < copia[i+1].minNormalized(v.getCenter(),range))
            done=true;
        i++;
      }
    }
  }

  /*qreale e la serie di query reale (non la trasformata che invece e il centro del nodo q)*/
  private void NNInsertNormalized(TTVNode vec,TTVNode q,TRange range,TTimeSerie qreale, boolean interpolation)
  {
    int i,j;
    double temp=0;
    double[] SOL3=new double[this.SOL1.length];
    TTVNode temp2;

    //CALCOLO DELLA DISTANZA REALE
    TTimeSerie TNODO=new TTimeSerie();
    TNODO.init();
    this.loader.loadSingleSerie(vec.linkPaziente,vec.linkDialisi,vec.linkSegnale,TNODO,interpolation);
    /*CALCOLO DELLA DISTANZA REALE E NON QUELLA SULLA SERIE RIDOTTA
     this.SOL2[0]=vec.getCenter().getDistance2(q,2,range);*/
    this.SOL2[0]=qreale.getDistance(TNODO,2,range);

    temp2=new TTVNode(this.k,this.alpha,this.numfigli,true);
    temp2.linkDialisi=vec.linkDialisi;
    temp2.linkPaziente=vec.linkPaziente;
    temp2.linkSegnale=vec.linkSegnale;
    temp2.center.copySerie(TNODO);

    /*IL VETTORE SOL1 AVRA' DEI NODI CONTENENTI LE SERIE REALI E NON RIDOTTE*/
    for(i=this.SOL1.length-1;i>0;i--)
      {
        this.SOL1[i] = this.SOL1[i - 1];
        this.SOL2[i] = this.SOL2[i - 1];
      }
    this.SOL1[0]=temp2; /*CARICAMENTO DELLA SERIE REALE NEL VETTORE DEI RISULTATI*/

    for(i=0;i<this.SOL1.length;i++)
      if (this.SOL1[i].getCenter().getPoint(0)!=null)
      {
        /* SOL3[i] = this.SOL1[i].getCenter().getDistance2(q, 2, range); */
        SOL3[i] = this.SOL1[i].getCenter().getDistance(qreale, 2, range);
      }
      else
        SOL3[i]=9999999;
    for(i=0;i<SOL3.length;i++)
      for(j=i;j<SOL3.length;j++)
        if (SOL3[j]<SOL3[i])
        {
           temp=SOL3[i];
           SOL3[i]=SOL3[j];
           SOL3[j]=temp;

           temp2=this.SOL1[i];
           this.SOL1[i]=this.SOL1[j];
           this.SOL1[j]=temp2;

           temp=SOL2[i];
           SOL2[i]=SOL2[j];
           SOL2[j]=temp;
        }
  }

  public TLinkedList knnQuery (TTVNode Key, int p,boolean normalized,TRange range, boolean interpolation) {
    int i;
    double dist;
    TTimeSerie chiave;
    this.risultati.init();
    this.head= null;
    this.p=p;

    //CALCOLO DELLA QUERY
    chiave=new TTimeSerie();
    chiave.init();
    this.loader.loadSingleSerie(Key.linkPaziente,Key.linkDialisi,Key.linkSegnale,chiave,interpolation);

    if (normalized==true)
      NNSearchNormalized(this.getRoot(),Key,range,chiave, interpolation);
    else
      NNSearch (this.getRoot(),Key.getCenter());

    /*METTERE A POSTO LE DISTANZE DI MODO CHE SIANO CONTEMPLATE QUELLE NEL REALE*/
    for(i=0;i<this.SOL1.length;i++)
    {
      //CALCOLO DELLA DISTANZA REALE DELLA SOLUZIONE DALLA QUERY PER SUCCESSIVO INSERIMENTO ORDINATO
      if (normalized==true)
        dist=this.SOL1[i].getCenter().getDistance(chiave,2,range);
      else
        dist=this.SOL1[i].getCenter().getDistance(chiave,2);

      risultati.insertSorted(SOL1[i].getCenter(),SOL1[i].getLinkPaziente(),SOL1[i].getLinkDialisi(),SOL1[i].getLinkSegnale(),dist);
    }
    return risultati;
  }

  private void RangeQuery (TTVNode t, TTVNode q, double Epsilon, boolean classificazione) {
    int i;
    double dist;
    if (t.IsLeaf()==false&&t.getNumChilds()==0)
      {
        if ((!classificazione) || (classificazione & t.classificato)) {
          dist=q.getCenter().getDistance(t.getCenter(),2);
          if (dist <= Epsilon)
            risultati.insertHead(null,t.getLinkPaziente(),t.getLinkDialisi(),t.getLinkSegnale(),dist);
        }
      }
    else
      for(i=0;i<t.getNumChilds();i++)
        if (q.Intersect(t.getChild(i))==true)
          RangeQuery (t.getChild(i), q, Epsilon, classificazione);
  }

  private void RangeQueryNormalized (TTVNode t, TTVNode q, double Epsilon, TRange Range,  boolean classificazione) {
    int i;
    double dist;
    if (t.IsLeaf() == false && t.getNumChilds() == 0)
    {
      if ((!classificazione) || (classificazione & t.classificato)) {
        dist = q.getCenter().getDistance(t.getCenter(), 2, Range);
        if (dist <= Epsilon)
          risultati.insertHead(null, t.getLinkPaziente(), t.getLinkDialisi(), t.getLinkSegnale(), dist);
      }
    }
    else
     for (i = 0; i < t.getNumChilds(); i++)
       if (q.IntersectNormalized(t.getChild(i),Range) == true)  /*USO DELLA NORMALIZZAZIONE DI INTERSECT*/
         RangeQueryNormalized (t.getChild(i), q, Epsilon, Range, classificazione);
  }

  public TLinkedList rangeQuery (TTimeSerie Key, double Distance, boolean classificazione) {
      int i;
      double dist;
      this.risultati = new TLinkedList();
      this.head= null;

      //DEFINIZIONE DELLA SERIE DA RICERCARE
      TTVNode nodo=new TTVNode(this.k,this.alpha,this.numfigli,false);
      nodo.center=new TTimeSerie();
      nodo.center.init();
      nodo.center.copySerie(Key);
      nodo.setRadius(Distance);

      RangeQuery (this.getRoot(), nodo, Distance, classificazione);

      return risultati;
    }

  public TLinkedList rangeQueryNormalized (TTimeSerie Key, double Distance, TRange Range, boolean classificazione) {
    int i;
    double dist;
    this.risultati = new TLinkedList();
    this.head= null;

    //DEFINIZIONE DELLA SERIE DA RICERCARE
    TTVNode nodo=new TTVNode(this.k,this.alpha,this.numfigli,false);
    nodo.center=new TTimeSerie();
    nodo.center.init();
    nodo.center.copySerie(Key);
    //IL VALORE DI EPSILON DIVENTA IL RAGGIO DEL NODO QUERY
    nodo.setRadius(Distance);

    RangeQueryNormalized (this.getRoot(), nodo, Distance, Range, classificazione);

    return risultati;
    }

  //Effettua l'operazione di branching dentro l'albero per capire in quale figlio inserire "v"
  //In input viene passata la radice dell'albero e il vettore da inserire in output il nodo candidato
  //Viene calcolato un vettore del percorso fatto di lunghezza this.livellofoglie
  private TTVNode Branching(TTimeSerie v, int percorso[],int level) {
    TTVNode[] risultati=new TTVNode[this.numfigli];
    risultati[0]=this.getRoot();
    Branching2(this.getRoot(),v,percorso,level,risultati);
    return risultati[0];
  }
  private TTVNode Branching2(TTVNode r, TTimeSerie v, int percorso[],int level,TTVNode[] risultati) {
    double expvalue[]; //Vettore dei valori della funzione exp(v)
    double actdistvalue[]; //Vettore delle distanze attive
    int posvalue[]; //Vettore delle posizioni dei figli
    double temp1;
    int temp2;
    int i,j,founded;
    TTVNode supporto;

    if (r.IsLeaf())
    {
      return r;
    }
    expvalue=new double[r.maxNumChilds];
    actdistvalue=new double[r.maxNumChilds];
    posvalue=new int[r.maxNumChilds];
    founded=0;
    if (r.getNumChilds()>1)
    {
      for (i = 0; i < r.getNumChilds(); i++)
      {
        expvalue[i]=r.getChild(i).exp(v);
        risultati[i]=r.getChild(i);
        actdistvalue[i]=r.getChild(i).getActiveDistance(v,2);
        posvalue[i]=i;
      }
      //BUBBLESORT su expvalue e vettori paralleli associati
      for(i=1; i<r.numChilds; i++)
        for(j=0; j<r.numChilds-i; j++)
            if(expvalue[j]>expvalue[j+1])
              {
	        temp1=expvalue[j]; expvalue[j]=expvalue[j+1]; expvalue[j+1]=temp1;
                temp1=actdistvalue[j]; actdistvalue[j]=actdistvalue[j+1]; actdistvalue[j+1]=temp1;
                temp2=posvalue[j]; posvalue[j]=posvalue[j+1]; posvalue[j+1]=temp2;
                supporto=risultati[j]; risultati[j]=risultati[j+1]; risultati[j+1]=supporto;
              }
      if (expvalue[0]!=expvalue[1])
        founded=posvalue[0];
      /*else  //Ricerca del minimo nel vettore delle distanze attive a parita di valore di exp(v)
        {
           founded=posvalue[0];
           temp1=actdistvalue[0];
           for(i=1;(i+1<r.numChilds)&&(expvalue[i]==expvalue[i+1]);i++)
             if (actdistvalue[i]<temp1)
                  {
                    temp1=actdistvalue[i];
                    founded=posvalue[i];
                  }
        }*/
    }
    percorso[level]=founded;
    level=level+1;
    return Branching2(r.getChild(founded),v,percorso,level,risultati);
  }

  private TTVNode SplittingWithBalance(TTVNode a,TTVNode N1prec, TTVNode N2prec, TTVNode aprec, TTVNode v) {
  TTVNode newroot;
  TTVNode N1,N2;
  TTVNode[] temp=null;
  double[] CN1,CN2;
  int i,j,pos;
  double temp2;
  boolean finito=false;
  boolean radicedasplittare=false;

  N1=new TTVNode(this.k, this.alpha, this.numfigli, true);
  N2=new TTVNode(this.k, this.alpha, this.numfigli, true);
  N1.getCenter().init();
  N2.getCenter().init();

  newroot=new TTVNode(this.k, this.alpha, this.numfigli, true);
  newroot.initRootNode(this.k);

  if (a==null) /*se a e' la radice*/
    {
      this.root = newroot;
      radicedasplittare=true;
    }
  else if (a.getNumChilds()==a.maxNumChilds) //se a non ha posto per un figlio
    {
      CN1=new double[this.k];
      CN2=new double[this.k];
      if (v==null)
      {
        temp=new TTVNode[a.getNumChilds()+1];
        for (i = 0,j = 0; i < a.getNumChilds(); i++)
          if (a.getChild(i)!=aprec)
            {
              temp[j] = a.getChild(i);
              j++;
            }
        temp[j] = N1prec;
        temp[j+1] = N2prec;
      }
      else
      {
        temp=new TTVNode[a.getNumChilds()+1];
        for (i = 0; i < a.getNumChilds(); i++)
          temp[i] = a.getChild(i);
        temp[i] = v;
        v = null;
      }
      OrdChildDisVett(temp,a);
      pos=(temp.length/2)-1;
      for(i=0;i<temp.length;i++)
        if (i<=pos)
        {
          InsertInNodeNotFull(N1, temp[i]);
          for(j=0;j<this.k;j++)
            CN1[j] = CN1[j] + temp[i].getCenter().getValue(j);
        }
        else
        {
          InsertInNodeNotFull(N2, temp[i]);
          for(j=0;j<this.k;j++)
            CN2[j] = CN2[j] + temp[i].getCenter().getValue(j);
        }
      for(j=0;j<this.k;j++)
        {
          N1.getCenter().addValue(j,CN1[j]/(pos+1));
          N2.getCenter().addValue(j,CN2[j]/(pos+1));
        }
      N1.RiCalcolaRaggioDaFigli();
      N2.RiCalcolaRaggioDaFigli();
      if (aprec==a)
      {
        N1.isLeaf=true;
        N2.isLeaf=true;
      }
      else
      {
        N1.isLeaf=false;
        N2.isLeaf=false;
      }
      SplittingWithBalance(a.father,N1,N2,a,v);
    }
    else if (a.getNumChilds()<a.maxNumChilds&&finito==false) //se c'e posto per i figli
    {
      /*??????????????????????????????????????????????*/
          for(i=0,j=0;i<a.getNumChilds();i++)
              if (a.getChild(i)!=aprec)
                a.RiCalcolaRaggioDaFigli();
              else j=i;
          a.childs[j]=null;
          a.numChilds--;
          InsertInNodeNotFull(a, N1prec);
          InsertInNodeNotFull(a, N2prec);
    }
    if (radicedasplittare==true) //se a e la radice e non ha posto per figli - n1 e n2 gia creati
    {
      if (N1prec==null&&N2prec==null)
      {
        InsertInNodeNotFull(newroot, N1);
        InsertInNodeNotFull(newroot, N2);
      }
      else
      {
        InsertInNodeNotFull(newroot, N1prec);
        InsertInNodeNotFull(newroot, N2prec);
      }
      a=newroot;
      finito=true;
    }
    return a;
  }

  private void OrdChildDisVett(TTVNode[] v, TTVNode father) {
    int i,j;
    TTVNode temp;
    double temp2;
    double[] disfather;
    disfather=new double[v.length];
    //CALCOLO DELLE DISTANZE DEI FIGLI DAL VETTORE CENTRO DEL LORO PADRE
    for(i=0;i<disfather.length;i++)
      disfather[i]=father.getDistance2(v[i],2);
    //Ordinamento vettore posizioni dei figli per DISTANZA CRESCENTE dal padre
    for(i=1; i<v.length; i++)
      for(j=0; j<v.length-i; j++)
        if (disfather[j] > disfather[j + 1])
        {
          temp=v[j]; v[j]=v[j+1]; v[j+1]=temp;
          temp2=disfather[j]; disfather[j]=disfather[j+1]; disfather[j+1]=temp2;
        }
  }

  private void Reinsert(TTVNode a, double perc) {
    int i,j,k;
    double perctoreinsert;
    TTVNode[] v;
    OrdChildDisVett(a.childs,a);
    perctoreinsert=(a.getNumChilds()*perc);
    v=new TTVNode[(int)perctoreinsert];
    for(k=0,i=a.getNumChilds()-1;k<(int)perctoreinsert;i--,k++)
    {
      v[k]=a.getChild(i); //REINSERISCI A PARTIRE DAL FONDO DI A PERC FIGLI
      a.childs[i]=null;
      a.radius=-1;
      a.numChilds--;
      a.RiCalcolaRaggioDaFigli();
    }
    for(k=0;k<v.length;k++)
      //INSERISCI TUTTI I FIGLI DI A DENTRO L'ALBERO PARTENDO DALL'ALTO
      InsertInTree(v[k]);
  }

  private void ClearRadius(TTVNode t) {
    int i;
    if (t.IsLeaf()==false)
    {
      for(i=0;i<t.getNumChilds();i++)
        ClearRadius(t.getChild(i));
      t.radius=0;
    }
    else
      for(i=0;i<t.getNumChilds();i++)
        t.radius=0;
  }

  private void AdjustNode(TTVNode t) {
    int i;
    if (t.IsLeaf()==false)
    {
      for(i=0;i<t.getNumChilds();i++)
        AdjustNode(t.getChild(i));
      t.RiCalcolaRaggioDaFigli();
    }
    else
      t.RiCalcolaRaggioDaFigli();
  }

  /*Restituisce la profondita dell'albero - il livello cui sono le foglie*/
  public int LivFoglie(TTVTree t) {
    return LivFoglie2(t.getRoot());
  }
  private int LivFoglie2(TTVNode temp) {
    if (temp==null) return 0;
    if (temp.getNumChilds()==0) return 0;
    return 1+LivFoglie2(temp.getChild(0));
  }

  void PrintTreeFoglie(TTVTree t) {
     System.out.println("************************************");
     System.out.println("Stampa delle foglie dell'albero: ");
     System.out.println("************************************");

     if (t!=null)
       PrintFoglieTree(t,t.getRoot(),0,"");
     else System.out.println("ALBERO VUOTO");
  }

  void PrintFoglieTree(TTVTree t,TTVNode a,int posizione,String rimanenti) {
    int i;

    if (a.IsLeaf()==false && a.getChild(0)==null)
    {
      rimanenti=rimanenti+"["+posizione+"]";
      System.out.println(rimanenti);
      System.out.println(" -> (" +
                       a.getCenter().getPoint(0).getValue()+
                       ";" +
                       a.getCenter().getPoint(1).getValue()+
                       ") ACTIVEDIMS: {" +
                       a.activeDims[0] +
                       ";" +
                       a.activeDims[1] +
                       "} RADIUS: {" +
                       a.radius +
                       "} *** " +
                       a +
                       "\n");
    }
    else
    {
      if (a!=t.getRoot())
        rimanenti=rimanenti+"["+posizione+"]";
      for(i=0;i<a.getNumChilds();i++)
        PrintFoglieTree(t,a.getChild(i),i,rimanenti);
    }
  }

  void PrintTreeNodi(TTVTree t) {

     System.out.println("************************************");
     System.out.println("Stampa dei nodi interni dell'albero: ");
     System.out.println("************************************");

     if (t!=null)
       PrintNodiTree(t,t.getRoot(),0,"");
    else System.out.println("ALBERO VUOTO");
  }

  void PrintNodiTree(TTVTree t,TTVNode a,int posizione,String rimanenti) {
    int i;

    if (a.getNumChilds()>0)
    {
      if (t.getRoot()!=a)
        rimanenti = rimanenti + "[" + posizione + "]";
      if (t.getRoot()==a)
        System.out.println("RADICE: ");
      System.out.println(rimanenti);
      System.out.println(" -> (" +
                       a.getCenter().getPoint(0).getValue() +
                       ";" +
                       a.getCenter().getPoint(1).getValue() +
                       ") ***"+a+"\n\tFigli: [" +
                       a.getNumChilds() +
                       "] \n\tRaggio: [" +
                       a.radius +
                       "] \n\tACTIVEDIMS: {" +
                       a.activeDims[0] +
                       ";" +
                       a.activeDims[1] +
                       "}\n");
      for (i = 0; i < a.getNumChilds(); i++)
        PrintNodiTree(t, a.getChild(i), i, rimanenti);
    }
  }

  /*Inserisce una serie in un albero*/
  public void InsertInTree(TTVNode v) {
    InsertInTree2(this.getRoot(),v);
    this.getRoot().RiCalcolaRaggioDaFigli();
  }
  private void InsertInTree2(TTVNode t,TTVNode v) {
    TTVNode a; /*a e il nodo di branching, n e il nodo oggetto con la timeserie inserita*/
    int[] percorso;
    int numfigli=this.numfigli;
    int i,j;
    double temp2,temp;
    boolean primavolta=true;
    percorso=new int[20];
    for(i=0;i<percorso.length;i++)
      percorso[i]=-1;

    a=Branching(v.center,percorso,0);

    /*this.PrintTreeFoglie(this);
    this.PrintTreeNodi(this);
    System.out.print("Inizio ("+v.center.serie[0].getValue()+";"+v.center.serie[1].getValue()+") ");
    for(i=0;percorso[i]!=-1;i++)
      System.out.print("["+percorso[i]+"]");
    System.out.println(" Fine");*/

    v.father=a;

    if (a.IsLeafFull()==false)
    {
      InsertInNodeNotFull(a, v);
      //AGGIORNA I CENTRI DEI NODI CHE SONO STATI TOCCATI PER SCENDERE SINO AD "A"
      for(i=0;percorso[i]!=-1;i++);
      for(;i>0;i--)
      {
        a=a.father;
        for(i=0;i<v.getCenter().getNumValues() && a.father!=null;i++)
        {
          temp = 0;
          for (j = 0; j < a.getNumChilds(); j++)
            temp = temp + a.getChild(j).getCenter().getValue(i);
          if (a.getCenter().getPoint(i) != null) {
            a.getCenter().getPoint(i).setStamp(i);
            a.getCenter().getPoint(i).setValue(temp / a.getNumChilds());
          }
          else
            a.center.addValue(i, temp / a.getNumChilds());
          a.RiCalcolaRaggioDaFigli();
        }
      }
      a=v.father;
    }
    else //LE FOGLIE DEL NODO SONO TUTTE OCCUPATE
      if (a.firstsplitting==true) //PRIMA VOLTA CHE OCCORRE SPLITTARE - SI PROVA A REINSERIRE
      {
        Reinsert(a, 0.5); //REINSERISCI DALLA RADICE UNA DATA PERCENTUALE DEI FIGLI DEL NODO
        a.firstsplitting=false;
        InsertInTree(v);
      }
      else //SI HA GIA' TENTATO DI SPLITTARE IL NODO REINSERENDO - ORA SI SPLITTA
      {
        SplittingWithBalance(a,null,null,a,v);
        a.firstsplitting=true;
      }
  }

  public void setP (int P) {
    p= P;
  }

  public void setSOL1 (TTVNode[] NODES) {
    this.SOL1= NODES;
  }

  public void setSOL1 (int I, TTVNode NODE) {
    this.SOL1[I]= NODE;
  }

  public void setSOL2 (double[] NODES) {
    this.SOL2= NODES;
  }

  public void setSOL2 (int I, double NODE) {
    this.SOL2[I]= NODE;
  }

  public int getK () {
    return k;
  }

  public int getAlpha () {
    return alpha;
  }

  public int getNumFigli() {
    return numfigli;
  }

  public int getP () {
    return p;
  }

}

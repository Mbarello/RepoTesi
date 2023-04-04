package timeserie;

import java.util.ArrayList;
import java.util.List;
import retrieval.TRange;

/**
 * Title:        Time Serie Definition
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author Giorgio Leonardi
 * @author Alessio Bottrighi
 * @version 1.0
 */

public class TTimeSerie {
  String nome;
  TPoint[] serie;
  int nElementi;
  double piccoMin, piccoMax, media, scarto;
  boolean modificataAverage, modificataQuad;

  public TTimeSerie() {
    nome = new String ("Unknown");
    serie= new TPoint[180];
    nElementi= 0;
    piccoMin = piccoMax = media = scarto = 0.0;
    modificataAverage= false;
    modificataQuad= false;
  }

  public TTimeSerie(int N) {
    nome = new String ("Unknown");
    serie= new TPoint[N];
    nElementi= 0;
    piccoMin = piccoMax = media = scarto = 0.0;
    modificataAverage= false;
    modificataQuad= false;
  }

  public TTimeSerie(int N, String Nome) {
    nome = new String (Nome);
    serie= new TPoint[N];
    nElementi= 0;
    piccoMin = piccoMax = media = scarto = 0.0;
    modificataAverage= false;
    modificataQuad= false;
  }

  public TTimeSerie(TTimeSerie Key) {
    copySerie (Key);
    piccoMin = piccoMax = media = scarto = 0.0;
    modificataAverage= false;
    modificataQuad= false;
  }

  public void copySerie (TTimeSerie Source) {
    int I, N;

    if (Source != null) {
      setName(Source.getName());
      N= Source.getNumValues();
      if (N > this.serie.length) serie= new TPoint[N];
      System.arraycopy(Source.serie, 0, this.serie, 0, N);
      nElementi= N;
    }
  }

  public void setSize (int N) {
    TPoint[] temp;
    int i;

    if (N > serie.length) {
      temp= new TPoint[N];
      System.arraycopy(serie, 0, temp, 0, nElementi);
      serie= temp;
    } else nElementi= N;
  }

  public void setFirstSize (int N) {
    if (N > serie.length)
      serie= new TPoint[N];

    nElementi= 0;
  }

  public void trunc (int numElements) {
    if (nElementi > numElements) nElementi= numElements;
  }

  public void init() {
    nElementi= 0;
  }

  public void addValue (double E, double V) {
    int newDimension;

    if (nElementi == serie.length) {
      newDimension= nElementi * 2;
//      newDimension= (int)(nElementi * 1.5);
      setSize (newDimension);
//      System.out.println("Ridimensionamento: nuova dimensione " + newDimension);
    }
    serie[nElementi]= new TPoint(E, V);
    nElementi++;

    modificataAverage= true;
    modificataQuad= true;
  }

  public int getPosition (double E) {
    int i, result;
    boolean trovato;

    result= -1;
    i= 0;
    trovato= false;
    while ((i < nElementi) && (trovato == false)) {
      if (serie[i].getStamp() == E) {
        result= i;
        trovato= true;
      }
    }
    return (result);
  }

  public void setValue (int N, double E, double V) {
    if (N < nElementi) {
      serie[N].setPoint (E, V);
      modificataAverage= true;
      modificataQuad= true;
    }
  }

  public void setValue (int N, double V) {
    if (N < nElementi) {
      serie[N].setValue(V);
      modificataAverage= true;
      modificataQuad= true;
    }
  }

  public void setValue (double E, double V) {
    int i;

    i= getPosition (E);
    if (i >= 0) setValue (i, E, V);
  }

  public void setName (String Name) {
    nome= Name;
  }

  public void removeValue (int N) {
    int i;

    if (N < nElementi) {
      for (i= N; i < nElementi - 1; i++) serie[i]= serie[i + 1];
      if (nElementi > 0) nElementi--;
      modificataAverage= true;
      modificataQuad= true;
    }
  }

  public void removeValue (double E) {
    int i;

    i= getPosition (E);
    if (i >= 0) removeValue (i);
  }

  public int getNumValues () {
    return nElementi;
  }

  public int getArraySize () {
    return serie.length;
  }

  public String getName() {
    return nome;
  }

  public double getValue (int N) {
    if ((N >= 0) && (N < nElementi)) return serie[N].getValue();
    return 0;
  }

  public double getValue (double E) {
    int i;

    i= getPosition (E);
    return getValue (i);
  }

  public TPoint getPoint (int I) {
    return serie[I];
  }

  public double getTStamp (int N) {
    return serie[N].getStamp();
  }

  private void calcAverage () {
    int i;
    double somma;

    piccoMin = piccoMax = media = scarto = 0.0;
    if (nElementi > 0) {
      piccoMin = piccoMax = media = serie[0].getValue();

      for (i= 1; i < nElementi; i++) {
        if (serie[i].getValue() < piccoMin) piccoMin= serie[i].getValue();
        if (serie[i].getValue() > piccoMax) piccoMax= serie[i].getValue();
        media= media + serie[i].getValue();
      }

      media= media / nElementi;
    }
    modificataAverage= false;
  }

  private void calcQuad () {
    int i;
    double somma;

    if (modificataAverage) calcAverage();

    scarto = 0.0;
    if (nElementi > 0) {
      for (i= 0; i < nElementi; i++) {
        scarto= scarto + java.lang.Math.pow((serie[i].getValue() - media), 2);
      }

      scarto= scarto / nElementi;
      scarto= java.lang.Math.sqrt(scarto);
    }
    modificataQuad= false;
  }

  private int part (TPoint[] lista, int a, int z) {
    TPoint scambio;
    int i = a + 1;
    int cf = z;

    while (true) {
      while (true) {
        if ((lista[i].getValue() > lista[a].getValue()) || (i >= cf)) {
          break;
        }
        else {
          i++;
        }
      }
      while (true) {
        if (lista[cf].getValue() <= lista[a].getValue()) {
          break;
        }
        else {
          cf--;
        }
      }
      if (cf <= i) {
        break;
      }
      else {
        scambio = lista[cf];
        lista[cf] = lista[i];
        lista[i] = scambio;
        i++;
        cf--;
      }
    }
    scambio = lista[cf];
    lista[cf] = lista[a];
    lista[a] = scambio;
    return cf;
  }

  private void quicksort (TPoint[] lista, int a, int z) {
    int cf;

    if (z > a) {
      cf = part (lista, a, z);
      quicksort (lista, a, cf-1);
      quicksort (lista, cf+1, z);
    }
//    return lista;
  }

  public double getPercentile (int perc) {
    double indiceReal, percentile;
    int indice, indice2, N = nElementi;

    if (N < 1) return 0;

    TPoint[] temp = new TPoint[N];
    System.arraycopy(serie, 0, temp, 0, N);
    quicksort (temp, 0, N - 1);

/*    for (indice= 0; indice < N; indice++)
      System.out.print("  " + temp[indice].getValue());
    System.out.println();
*/
    indiceReal = ((N + 1.0) * (double)perc) / 100 - 1;
    if (indiceReal < 0) indiceReal+= 1;
    indice = (int)(((N + 1) * perc) / 100) - 1;
    if (indice < 0) indice+= 1;

//    System.out.println(" indiceReal = " + indiceReal + " - indice = " + indice);

    if ((indiceReal - indice) == 0) percentile= temp[indice].getValue();
    else {
      indice2 = indice + 1;
      if (indice2 >= N) indice2--;
      percentile = (temp[indice].getValue() + temp[indice2].getValue()) / 2;
    }
//    System.out.println(" Percentile " + perc + " = " + percentile);

    return percentile;
  }

  public double getMinPeak () {
    if (modificataAverage) calcAverage();
    return piccoMin;
  }

  public double getMaxPeak () {
    if (modificataAverage) calcAverage();
    return piccoMax;
  }

  public double getAverage () {
    if (modificataAverage) calcAverage();
    return media;
  }

  public double getQuad () {
    if (modificataQuad) calcQuad();
    return scarto;
  }

  public double getDistance (TTimeSerie TS, int P) {
    int i, N;
    double distanza;

    distanza= 0;
    N= nElementi;
    if (TS.getNumValues() < N) N= TS.getNumValues();
    if (N > 0) {
      for (i= 0; i < N; i++) {
//      if ((getValue (i) != 0) && (TS.getValue(i) != 0))
          distanza= distanza + java.lang.Math.pow(java.lang.Math.abs(getValue (i) - TS.getValue(i)), P);
      }
      distanza= java.lang.Math.pow(distanza, (double)1.0/P);
    }

    return distanza;
  }

  public double getDistance (TTimeSerie TS, int P, TRange Range) {
    double distanza;
    double normalizzazione;
    double nDati, differenza;

    nDati= nElementi;
    if (TS.getNumValues() < nDati) nDati= TS.getNumValues();

    distanza= this.getDistance(TS, P);

    if (distanza != 0.0) {
      differenza= java.lang.Math.abs(this.getNumValues() - TS.getNumValues());
      distanza= distanza + differenza * Range.getRange();

      normalizzazione= (Range.getRange() * java.lang.Math.pow(nDati, (double)1.0/P));
      distanza= distanza / normalizzazione;
    }

    return distanza;
  }

  public org.jdom.Element getXML(org.jdom.Element datas) {

   org.jdom.Element output = new org.jdom.Element("Series");
   org.jdom.Element data = new org.jdom.Element("Data");
   org.jdom.Element comment = new org.jdom.Element("Comment");
   org.jdom.Element di = new org.jdom.Element("DataInfo");
   org.jdom.Element nds = new org.jdom.Element("NumericDataSeries");

   for(int i=0; i< nElementi; i++)
      nds.addContent(serie[i].getXML());

    ArrayList attribList = new ArrayList();
    org.jdom.Attribute attrib=new org.jdom.Attribute("id","admin-1090086105593");
    attribList.add(attrib);
    di.setAttributes((List)attribList);
    comment.addContent("query");
    di.addContent(comment);
    di.addContent((org.jdom.Element) datas.clone());
    data.addContent(di);
    data.addContent(nds);
    output.addContent(data);

    return output ;
  }

  public String toString () {
    // formattazione per jCOLIBRI:
    // <istante>;<valore>|<istante>....

    String result= new String();
    int i, l;

    for (i= 0; i < nElementi; i++) {
      result= result + String.valueOf(this.getTStamp(i));
      result= result + ";";
      result= result + String.valueOf(this.getValue(i));
      result= result + "|";
    }

    l= result.length();
    if (l > 0) result= result.substring(0, l - 1);

    return result;
  }

}

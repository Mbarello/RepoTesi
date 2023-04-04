package transform;

import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TPiecewise extends TTransformation {
  int numSegmenti;

  public TPiecewise() {
    super ();
  }

  public TPiecewise(TTimeSerie Start) {
    super (Start);
  }

  public TPiecewise(TTimeSerie Start, int N) {
    super (Start);
    numSegmenti= N;
  }

  public void setNumSegmenti (int N) {
    numSegmenti= N;
  }

  public int getNumSegmenti (int N) {
    return numSegmenti;
  }

  public void calculate (int n) {
    int i, j, inf, sup;
    double fract1, fract2, sum;

    transTimeSerie.init();
    transTimeSerie.setSize(numSegmenti);

    fract1= (double)numSegmenti / n;
    fract2= (double)n / numSegmenti;
    for (i= 0; i < numSegmenti; i++) {
      inf= (int)(fract2 * i);
      sup= (int)(fract2 * (i + 1));
      sum= 0;

      for (j= inf; j < sup; j++)
        sum+= originalTimeSerie.getValue(j);
      sum= sum * fract1;

      transTimeSerie.addValue(originalTimeSerie.getTStamp(inf), sum);
    }
  }

  public void calculate () {
    calculate (originalTimeSerie.getNumValues());
  }

  private double distance (TTimeSerie X, TTimeSerie Y, int n, int N, int Ns) {
    int i;
    double fract, dist, prodotto;

    fract= java.lang.Math.sqrt((double)n / N);

    dist= 0;
    for (i= 0; i < Ns; i++) {
      prodotto= X.getValue(i) - Y.getValue(i);
      prodotto*= prodotto;
      dist+= prodotto;
    }
    dist= fract * java.lang.Math.sqrt(dist);

    return dist;
  }

  public double getDistance (TPiecewise Query) {
    double result = -1;
    int sup = 0;

    if (originalTimeSerie.getNumValues() == Query.getOriginalSerie().getNumValues()) {
      sup= numSegmenti;
    }
    else if (originalTimeSerie.getNumValues() > Query.getOriginalSerie().getNumValues()) {
      sup= (int)((numSegmenti * Query.getOriginalSerie().getNumValues()) /
                 originalTimeSerie.getNumValues());
    }
    else {
      Query.calculate(originalTimeSerie.getNumValues());
      sup= numSegmenti;
    }

    result= distance (transTimeSerie, Query.getTransformedSerie(),
                   originalTimeSerie.getNumValues(), numSegmenti, sup);

    return result;
  }

}

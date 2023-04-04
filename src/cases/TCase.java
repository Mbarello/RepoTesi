package cases;

import timeserie.TTimeSerie;
import retrieval.TRange;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TCase {
  int numSegnali;
  int idPaziente, idDialisi;
  TTimeSerie[] serie;
  int[] soglie;
  TCase next;

  public TCase(int nsegnali, int paziente, int dialisi, int[] Soglie) {
    int i;

    numSegnali= nsegnali;
    idPaziente= paziente;
    idDialisi= dialisi;
    soglie = Soglie;
    serie= new TTimeSerie[numSegnali];
    for (i= 0; i < serie.length; i++) serie[i] = new TTimeSerie ();
    next= null;
  }

  public void setSerie (TTimeSerie TS, int i) {
    serie[i]= TS;
  }

  public TTimeSerie getSerie (int i) {
    return serie[i];
  }

  public int getIdPaziente () {
    return idPaziente;
  }

  public int getIdDialisi () {
    return idDialisi;
  }

  public int getNumSignals () {
    return numSegnali;
  }

  public boolean isValid () {
    boolean Result;
    int i;

    Result= true;
    for (i= 0; i < serie.length; i++) {
      if (serie[i] == null) Result= false;
      else {
        if (serie[i].getNumValues() <= soglie[i]) Result= false;
      }
    }

    return Result;
  }

/*    public double getDistance (TCase caso, double[] weights, int P) {
    double distanza, weightsum;
    int i;

    distanza= weightsum = 0;
    for (i= 0; i < serie.length; i++) {
      distanza+= weights[i] * serie[i].getOriginalSerie().getDistance(caso.serie[i].getOriginalSerie(), P);
      weightsum+= weights[i];
    }

    distanza= distanza / weightsum;

    return (distanza);
  } */

  public double getDistance (TCase caso, double[] weights, TRange[] ranges, int P, boolean normalized) {
    double distanza, weightsum;
    double dist;
    int i;

    distanza= weightsum = 0;

    if (normalized == true) {
      for (i= 0; i < serie.length; i++) {

        if ((serie[i].getNumValues() <= soglie[i]) ||
            (caso.serie[i].getNumValues() <= soglie[i])) {
          dist = 1;
        }
        else dist= serie[i].getDistance(caso.serie[i], P, ranges[i]);

        distanza= distanza + (weights[i] * dist);
        weightsum= weightsum + weights[i];
      }
    }
    else {
      for (i= 0; i < serie.length; i++) {

        if ((serie[i].getNumValues() <= soglie[i]) ||
            (caso.serie[i].getNumValues() <= soglie[i])) {
          dist= (ranges[i].getRange() * java.lang.Math.pow(ranges[i].getMaxDim(), (double)1.0/P));
        }
        else dist= serie[i].getDistance(caso.serie[i], P);

        distanza= distanza + (weights[i] * dist);
        weightsum= weightsum + weights[i];
      }
    }

    distanza= distanza / weightsum;

    return (distanza);
  }

}

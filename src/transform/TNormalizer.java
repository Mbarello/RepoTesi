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

public class TNormalizer extends TTransformation {

  public TNormalizer () {
    super ();
  }

  public TNormalizer (TTimeSerie Start) {
    super (Start);
  }

  public void calculate() {
    int N, I;
    double a, b, normalizedValue;

    N= originalTimeSerie.getNumValues();
    transTimeSerie.init();
    transTimeSerie.setSize(originalTimeSerie.getNumValues());

    a= 1 / originalTimeSerie.getQuad();
    b= originalTimeSerie.getAverage() / originalTimeSerie.getQuad();

    for (I= 0; I < N; I++) {
        normalizedValue= a*originalTimeSerie.getValue(I) - b;

        transTimeSerie.addValue(originalTimeSerie.getTStamp(I), normalizedValue);
    }
  }

}

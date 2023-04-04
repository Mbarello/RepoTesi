package transform;

import timeserie.TTimeSerie;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Giorgio Leonardi
 * @version 1.1
 */

public class Interpolation extends TTransformation {
    public Interpolation() {
      super ();
    }

    public Interpolation(TTimeSerie Start) {
      super (Start);
    }

    public void calculate () {
      int I, K, N, bottom, upper;
      double meanValue;

      N= originalTimeSerie.getNumValues();
      I= 0;

      while ((I < N) && (originalTimeSerie.getValue(I) == 0)) I++;
      if (I < N) {
        meanValue= originalTimeSerie.getValue(I);
        for (K= 0; K < I; K++) originalTimeSerie.setValue(K, meanValue);
      }
      while (I < N) {
        if (originalTimeSerie.getValue(I) == 0) {
          bottom= I - 1;
          while ((I < N) && (originalTimeSerie.getValue(I) == 0)) I++;
          if (I < N) {
            upper= I;
            meanValue= (originalTimeSerie.getValue(bottom) + originalTimeSerie.getValue(upper)) / 2.0;
          } else {
            upper= N;
            meanValue= originalTimeSerie.getValue(bottom);
          }
          for (K= bottom + 1; K < upper; K++)
            originalTimeSerie.setValue(K, meanValue);
        }
        I++;
      }
    }

}

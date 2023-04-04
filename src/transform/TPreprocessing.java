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

public class TPreprocessing extends TTransformation {
  public TPreprocessing() {
    super ();
  }

  public TPreprocessing(TTimeSerie Start) {
    super (Start);
  }

  public void calculate () {
    int I, min, max;

    transTimeSerie.init();

    min= 0;
    while ((min < originalTimeSerie.getNumValues()) && (originalTimeSerie.getValue(min) == 0)) {
      min++;
    }

    max= originalTimeSerie.getNumValues() - 1;
    while ((max >= 0) && (originalTimeSerie.getValue(max) == 0)) {
      max--;
    }

    for (I= min; I <= max; I++) {
      transTimeSerie.addValue(originalTimeSerie.getTStamp(I), originalTimeSerie.getValue(I));
    }
  }

}

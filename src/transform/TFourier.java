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

public class TFourier extends TTransformation {
  int numCoefficients;
  TTimeSerie immTimeSerie;

  public TFourier() {
    super ();
    numCoefficients= 0;
//    immTimeSerie= new TTimeSerie();
  }

  public TFourier (TTimeSerie Serie) {
    super (Serie);
    numCoefficients= 0;
  }

  public void setOriginalSerie (TTimeSerie Start) {
    super.setOriginalSerie(Start);

    immTimeSerie = new TTimeSerie(1, Start.getName());
  }

  public TFourier (TTimeSerie Serie, int N) {
    super (Serie);
    numCoefficients= N;
  }

  public void setNumCoefficients (int N) {
    numCoefficients= N;
  }

  public TTimeSerie getImmaginaryTimeSerie() {
    return immTimeSerie;
  }

  public TComplexPoint calculateCoefficient (int k) {
    double exponent, R, I, scale, x;
    double factor;
    int j, n;
    TComplexPoint coefficient;

    n= originalTimeSerie.getNumValues();
    exponent= 2.0 * java.lang.Math.PI * k / n;
//    factor= (double)1 / java.lang.Math.sqrt(n);
    factor= (double)1 / n;

    R= 0.0;
    I= 0.0;

    if (k == 0) {
      for (j= 0; j < n; j++)
        R+= originalTimeSerie.getValue(j);
    }
    else {
      for (j= 0; j < n; j++) {
        x= originalTimeSerie.getValue(j);
        scale= exponent * j;
        R= R + x * java.lang.Math.cos(scale);
        I= I + x * java.lang.Math.sin(scale);
      }
    }
    coefficient= new TComplexPoint ((factor * R), (factor * I));
    return coefficient;
  }

  public void calculate() {
    int i;
    TComplexPoint Point;

    transTimeSerie.setSize(numCoefficients);
    transTimeSerie.init();
    immTimeSerie.setSize(numCoefficients);
    immTimeSerie.init();

    for (i= 1; i <= numCoefficients; i++) {
      Point= calculateCoefficient (i);
      transTimeSerie.addValue(i, Point.getReal());
      immTimeSerie.addValue(i, Point.getImmag());
    }
  }

}

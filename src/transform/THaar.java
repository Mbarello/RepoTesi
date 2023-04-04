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

public class THaar extends TTransformation {
  TTimeSerie coeffTimeSerie;

  public THaar () {
    super ();

    coeffTimeSerie = new TTimeSerie();
  }

  public THaar (TTimeSerie Serie) {
    super (Serie);
  }

  public void setOriginalSerie (TTimeSerie Start) {
    super.setOriginalSerie(Start);

    coeffTimeSerie = new TTimeSerie(100, Start.getName());
  }

  public TTimeSerie getCoefficientsSerie () {
    return coeffTimeSerie;
  }

  public void calculate() {
    int I, N;
    double average, coefficient;

    N= tempTimeSerie.getNumValues();
    transTimeSerie.setSize((int)(N / 2) + 1);
    transTimeSerie.init();
    coeffTimeSerie.setSize((int)(N / 2) + 1);
    coeffTimeSerie.init();

    for (I= 0; I < (N - 1); I+= 2) {
      average= (tempTimeSerie.getValue(I) + tempTimeSerie.getValue(I + 1)) / 2;
      coefficient= (tempTimeSerie.getValue(I) - tempTimeSerie.getValue(I + 1)) / 2;

      transTimeSerie.addValue(tempTimeSerie.getTStamp(I), average);
      coeffTimeSerie.addValue(tempTimeSerie.getTStamp(I), coefficient);
    }
  }

}

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

public class TTransformation {
  TTimeSerie originalTimeSerie;
  TTimeSerie transTimeSerie;
  TTimeSerie tempTimeSerie;
  int currentStep;

  public TTransformation() {
    originalTimeSerie = new TTimeSerie(1);
    transTimeSerie = new TTimeSerie(1);
    tempTimeSerie = new TTimeSerie(1);

    currentStep= 0;
  }

  public TTransformation(TTimeSerie Start) {
    setOriginalSerie (Start);
  }

/*  private void copySerie (TTimeSerie Source, TTimeSerie Dest) {
    int I, N;

    if (Source != null) {
      Dest.setName(Source.getName());
      N= Source.getNumValues();
      Dest.setSize(N);
      System.arraycopy(Source.serie, 0, Dest.serie, 0, N);
      Dest.nElementi= N;
    }
  }
*/

  public void setOriginalSerie (TTimeSerie Start) {
    originalTimeSerie = new TTimeSerie(Start.getNumValues());
    tempTimeSerie = new TTimeSerie(Start.getNumValues());
    transTimeSerie = new TTimeSerie(Start.getNumValues(), Start.getName());

    originalTimeSerie.copySerie(Start);
    tempTimeSerie.copySerie(Start);

    currentStep= 0;
  }

  public TTimeSerie getOriginalSerie () {
    return originalTimeSerie;
  }

  public TTimeSerie getTransformedSerie () {
    return transTimeSerie;
  }

  public int getCurrentStep () {
    return currentStep;
  }

  public void calculate() {

  }

  public void step () {
    tempTimeSerie.copySerie(transTimeSerie);
    currentStep++;
  }

}

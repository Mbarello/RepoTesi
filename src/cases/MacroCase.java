package cases;

import timeserie.TTimeSerie;
import retrieval.TRange;

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
public class MacroCase {
  int startPatient, startSession;
  int nPrec, nSucc;
  TTimeSerie[] medians;
  TTimeSerie[] perc10;
  TTimeSerie[] perc90;

  public MacroCase(int StartPatient, int StartSession, int NPrec, int NSucc) {
    startPatient= StartPatient;
    startSession= StartSession;
    nPrec= NPrec;
    nSucc= NSucc;
    medians= new TTimeSerie[11];
    perc10= new TTimeSerie[11];
    perc90= new TTimeSerie[11];
  }

  public double getDistance (MacroCase Case, double[] weights, int P) {
    double distance, weightSum;
    int i;

    distance= 0; weightSum= 0;
    for (i= 0; i < 11; i++) {
      distance = distance + weights[i] * this.medians[i].getDistance(Case.medians[i], P);
      weightSum= weightSum + weights[i];
    }
    System.out.print(distance + " / " + weightSum + " = ");
    distance= distance / weightSum;
    System.out.println(distance);

    return distance;
  }

  public double getDistance (MacroCase Case, double[] weights, int P, TRange[] Range) {
    double distance, weightSum, tempDistance;
    int i;

    distance= 0; weightSum= 0;
    for (i= 0; i < 11; i++) {
      tempDistance= this.medians[i].getDistance(Case.medians[i], P, Range[i]);
      distance = distance + weights[i] * tempDistance;
//      System.out.println("Glob Dist " + distance + " - Loc Dist " + tempDistance);
      weightSum= weightSum + weights[i];
    }
//    System.out.print(distance + " / " + weightSum + " = ");
    distance= distance / weightSum;
//    System.out.println(distance);

    return distance;
  }

  public void setStartPatient(int Paz) {
    startPatient= Paz;
  }

  public void setStartSession(int Sess) {
    startSession= Sess;
  }

  public void setNumPrec(int Sess) {
    nPrec= Sess;
  }

  public void setNumSucc(int Sess) {
    nSucc= Sess;
  }

  public void setMedian(int I, TTimeSerie Serie) {
    this.medians[I]= Serie;
  }

  public void setPerc10(int I, TTimeSerie Serie) {
    this.perc10[I]= Serie;
  }

  public void setPerc90(int I, TTimeSerie Serie) {
    this.perc90[I]= Serie;
  }

  public int getStartPatient() {
    return startPatient;
  }

  public int getStartSession() {
    return startSession;
  }

  public int getNumPrec() {
    return nPrec;
  }

  public int getNumSucc() {
    return nSucc;
  }

  public TTimeSerie getMedian(int I) {
    return this.medians[I];
  }

  public TTimeSerie getPerc10(int I) {
    return this.perc10[I];
  }

  public TTimeSerie getPerc90(int I) {
    return this.perc90[I];
  }
}

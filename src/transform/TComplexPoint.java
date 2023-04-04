package transform;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TComplexPoint {
  private double Real;
  private double Immag;

  public TComplexPoint() {
    Real= 0;
    Immag= 0;
  }

  public TComplexPoint(double R, double I) {
    Real= R;
    Immag= I;
  }

  public void setPoint (double R, double I) {
    Real= R;
    Immag= I;
  }

  public void setReal (double R) {
    Real= R;
  }

  public void setImmag (double I) {
    Immag= I;
  }

  public double getReal () {
    return Real;
  }

  public double getImmag () {
    return Immag;
  }

  public double getMagnitude () {
    return java.lang.Math.sqrt((Real * Real) + (Immag * Immag));
  }

  public double getAngle () {
    double Angle;

    Angle= java.lang.Math.atan(Immag / Real);
    Angle= (Angle * 180.0) / java.lang.Math.PI;

    return Angle;
  }

}

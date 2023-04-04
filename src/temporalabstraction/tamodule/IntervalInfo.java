package temporalabstraction.tamodule;

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
public class IntervalInfo {
  double slope, average, startValue, stopValue;

  public IntervalInfo() {
    slope= 0;
    average= 0;
    startValue= 0;
    stopValue= 0;
  }

  public IntervalInfo(double Slope, double Average,
                      double StartValue, double StopValue) {
    slope= Slope;
    average= Average;
    startValue= StartValue;
    stopValue= StopValue;
  }

  public void setSlope(double Slope) {
    slope= Slope;
  }
  public void setAverage(double Average) {
    average= Average;
  }
  public void setStartValue(double StartValue) {
    startValue= StartValue;
  }
  public void setStopValue(double StopValue) {
    stopValue= StopValue;
  }

  public double getSlope() {
    return slope;
  }
  public double getAverage() {
    return average;
  }
  public double getStartValue() {
    return startValue;
  }
  public double getStopValue() {
    return stopValue;
  }
}

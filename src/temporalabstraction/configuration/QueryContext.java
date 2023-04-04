package temporalabstraction.configuration;

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

public class QueryContext {
  private double systolicPressure, diastolicPressure;
  private double sessionDuration;
  private int age, intervention;

  public QueryContext() {
  }

  public void setContext (double SystolicPressure, double DiastolicPressure,
                          double SessionDuration, int Age, int Intervention) {
    systolicPressure= SystolicPressure;
    diastolicPressure= DiastolicPressure;
    sessionDuration= SessionDuration;
    age= Age;
    intervention= Intervention;
  }

  public double getSystolicPressure () {
    return systolicPressure;
  }

  public double getDiastolicPressure () {
    return diastolicPressure;
  }

  public double getSessionDuration () {
    return sessionDuration;
  }

  public int getAge () {
    return age;
  }

  public int getIntervention () {
    return intervention;
  }

  public void setSystolicPressure (double Syst) {
    systolicPressure= Syst;
  }

  public void setDiastolicPressure (double Dia) {
    diastolicPressure= Dia;
  }

  public void setSessionDuration (double Dur) {
    sessionDuration= Dur;
  }

  public void setAge (int Age) {
    age= Age;
  }

  public void setIntervention (int Interv) {
    intervention= Interv;
  }
}

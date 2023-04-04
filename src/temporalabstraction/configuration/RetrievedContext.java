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
public class RetrievedContext {
  private int context_id, template_id;
  private double systolicMin, systolicMax, diastolicMin, diastolicMax;
  private double sessionDurationMin, sessionDurationMax;
  private int ageMin, ageMax;
  private int intervention;

  public RetrievedContext() {
  }

  public void setContext (double SystolicPressureMin, double SystolicPressureMax,
                          double DiastolicPressureMin, double DiastolicPressureMax,
                          double SessionDurationMin, double SessionDurationMax,
                          int AgeMin, int AgeMax, int Intervention,
                          int Context_id, int Template_id) {
    systolicMin= SystolicPressureMin;
    systolicMax= SystolicPressureMax;
    diastolicMin= DiastolicPressureMin;
    diastolicMax= DiastolicPressureMax;
    sessionDurationMin= SessionDurationMin;
    sessionDurationMax= SessionDurationMax;
    ageMin= AgeMin;
    ageMax= AgeMax;
    intervention= Intervention;
    context_id= Context_id;
    template_id= Template_id;
  }

  public double getSistolicPressureMin () {
    return systolicMin;
  }

  public double getSistolicPressureMax () {
    return systolicMax;
  }

  public double getDiastolicPressureMin () {
    return diastolicMin;
  }

  public double getDiastolicPressureMax () {
    return diastolicMax;
  }

  public double getSessionDurationMin () {
    return sessionDurationMin;
  }

  public double getSessionDurationMax () {
    return sessionDurationMax;
  }

  public int getAgeMin () {
    return ageMin;
  }

  public int getAgeMax () {
    return ageMax;
  }

  public int getIntervention () {
    return intervention;
  }

  public int getContextId () {
    return context_id;
  }

  public int getTemplateId () {
    return template_id;
  }

  public void setSystolicPressureMin (double Sys) {
    systolicMin= Sys;
  }

  public void setSystolicPressureMax (double Sys) {
    systolicMax= Sys;
  }

  public void setDiastolicPressureMin (double Dia) {
    diastolicMin= Dia;
  }

  public void setDiastolicPressureMax (double Dia) {
    diastolicMax= Dia;
  }

  public void setSessionDurationMin (double Sess) {
    sessionDurationMin= Sess;
  }

  public void setSessionDurationMax (double Sess) {
    sessionDurationMax= Sess;
  }

  public void setAgeMin (int Age) {
    ageMin= Age;
  }

  public void setAgeMax (int Age) {
    ageMax= Age;
  }

  public void setIntervention (int Interv) {
    intervention= Interv;
  }

  public void setContextId (int Cont_ID) {
    context_id= Cont_ID;
  }

  public void setTemplateId (int Temp_ID) {
    template_id= Temp_ID;
  }
}

package temporalabstraction.configmodule;

import loader.*;
import temporalabstraction.configuration.*;

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

import java.util.ArrayList;

public class TemporalAbstractionModule {
  TGlobalLoader caseLoader;
  TemplateLoader abstractionLoader;
  QueryContext queryContext;
  RetrievedContext retrievedContext;

  public TemporalAbstractionModule(TGlobalLoader CaseLoader,
                                   TemplateLoader AbstractionLoader) {
    caseLoader= CaseLoader;
    abstractionLoader= AbstractionLoader;
  }

  public double getDistance (QueryContext queryContext, RetrievedContext retrievedContext) {
    double ris;

    ris= 0.0;
    if ((queryContext.getAge() < retrievedContext.getAgeMin()) ||
        (queryContext.getAge() > retrievedContext.getAgeMax())) ris+= 0.2;
    if ((queryContext.getDiastolicPressure() < retrievedContext.getDiastolicPressureMin()) ||
        (queryContext.getDiastolicPressure() > retrievedContext.getDiastolicPressureMax())) ris+= 0.2;
    if ((queryContext.getSystolicPressure() < retrievedContext.getSistolicPressureMin()) ||
        (queryContext.getSystolicPressure() > retrievedContext.getSistolicPressureMax())) ris+= 0.2;
    if ((queryContext.getSessionDuration() < retrievedContext.getSessionDurationMin()) ||
        (queryContext.getSessionDuration() > retrievedContext.getSessionDurationMax())) ris+= 0.2;
    if ((queryContext.getIntervention() == 1) && (retrievedContext.getIntervention() != 1)) ris+= 0.2;
    if ((retrievedContext.getIntervention() == 1) && (queryContext.getIntervention() != 1)) ris+= 0.2;

    return ris;
  }

  public RetrievedContext retrieveSimilarContext (int paziente, int sessione) {
    ArrayList repository = new ArrayList();
    RetrievedContext temp;
    double distance, maxDistance;
    int I, N;

    queryContext= new QueryContext();
    caseLoader.loadQueryContext(paziente, sessione, queryContext);
    abstractionLoader.loadAllRetrievedContexts(repository);

    retrievedContext= null;
    maxDistance= 2;
    N= repository.size();

    for (I= 0; I < N; I++) {
      temp= (RetrievedContext)repository.get(I);
      distance= this.getDistance(queryContext, temp);
      if (distance <= maxDistance) {
        retrievedContext= temp;
        distance= maxDistance;
      }
    }

    return retrievedContext;
  }
}

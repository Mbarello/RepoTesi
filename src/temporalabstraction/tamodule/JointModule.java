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

import temporalabstraction.symbol.*;
import temporalabstraction.instance.*;
import temporalabstraction.tamodule.checkInstances;

public class JointModule {
  SignalInstance instance;

  public JointModule(SignalInstance Instance) {
    instance= Instance;
  }

  public void generateJoints() {
    TrendInstance T;
    StateInstance S;
    JointInstance J;
    JointSymbol JSY;
    TimeInterval TI;
    int I, K;

    instance.jointInstances.clear();
    for (I= 0; I < instance.trendInstances.size(); I++) {
      T= instance.getTrendInstance(I);

      for (K= 0; K < instance.stateInstances.size(); K++) {
        S= instance.getStateInstance(K);

        if (S.getInterval().overlaps(T.getInterval(), 0) > 0) {
            // CONTROLLARE E INSERIRE IL JOINT SYMBOL !!! NON USARLO PER ADESSO
            TI = S.getInterval().intersect(T.getInterval());

            if ((T.getTrendSymbol().getSymbol().compareTo("UNKNOWN") == 0) ||
                (S.getStateSymbol().getSymbol().compareTo("UNKNOWN") == 0)) {
              JSY= new JointSymbol(T.getTrendSymbol().getSignal(), "UNKNOWN", T.getTrendSymbol(), S.getStateSymbol(), "UNKNOWN", 0, 0, 0);
              J= new JointInstance(JSY, TI);
              instance.addJointInstanceSorted(J);
            } else {
              JSY= new JointSymbol(T.getTrendSymbol().getSignal(), "OK", T.getTrendSymbol(), S.getStateSymbol(), "Good Joint", 0, 1, 0);
              J= new JointInstance(JSY, TI);
              instance.addJointInstanceSorted(J);
            }
          }
        }



/*
         instance.jointInstances.clear();
         for (I= 0; I < instance.trendInstances.size(); I++) {
           T= instance.getTrendInstance(I);

        while ((TI.length() == 0) && (K < instance.stateInstances.size())) {
          K++;
          S= instance.getStateInstance(K);
          TI = S.timeInterval.intersect(T.timeInterval);
        }
        while ((K < instance.stateInstances.size()) && (TI.length() != 0)) {
          // CONTROLLARE E INSERIRE IL JOINT SYMBOL !!! NON USARLO PER ADESSO

          if ((T.symbol.symbol.compareTo("UNKNOWN") == 0) ||
              (S.symbol.symbol.compareTo("UNKNOWN") == 0)) {
            JSY= new JointSymbol(T.symbol.signal, "UNKNOWN", T.symbol, S.symbol, "UNKNOWN", 0, 0, 0);
            J= new JointInstance(JSY, TI);
            instance.addJointInstanceSorted(J);
          } else {
            JSY= new JointSymbol(T.symbol.signal, "OK", T.symbol, S.symbol, "Good Joint", 0, 1, 0);
            J= new JointInstance(JSY, TI);
            instance.addJointInstanceSorted(J);
          }
          K++;
          if (K < instance.stateInstances.size()) {
            S= instance.getStateInstance(K);
            TI = S.timeInterval.intersect(T.timeInterval);
          }
        }
*/


    }
  }

  public void check() {
    int I;
    JointInstance J;
    checkInstances check= new checkInstances();
    check.check(instance.jointInstances, 0);

    for (I= 0; I < check.getNumOverlaps(); I++) {
      J= instance.getJointInstance(check.getFirstInstance(I));
      J.getSymbol().setSymbol("UNKNOWN");
      J.getSymbol().setDescription("UNKNOWN");

      J= instance.getJointInstance(check.getSecondInstance(I));
      J.getSymbol().setSymbol("UNKNOWN");
      J.getSymbol().setDescription("UNKNOWN");
    }

   //     uniformo e aggrego tutti gli <U, U> aventi lo stesso time interval
  }

  public void createJoints() {
    this.generateJoints();
//    this.check();
  }
}

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

import java.util.ArrayList;
import temporalabstraction.instance.TimeInterval;
import temporalabstraction.instance.SymbolInstance;

public class checkInstances {

  public class overlapStruct {
    int overlayType;
    int firstOverlap, secondOverlap;
    TimeInterval timeInterval;
    public overlapStruct() {
    }
  }

  ArrayList AI;
  ArrayList overlaps;
  double tolerance;
  overlapStruct currentOverlap;

  public checkInstances() {
    overlaps= new ArrayList();
    currentOverlap = new overlapStruct();
  }

  public void check (ArrayList Instances, double Tolerance) {
    SymbolInstance currentInstance, tempInstance;
    int N, I, K, R;

    AI= Instances;
    tolerance= Tolerance;
    overlaps.clear();
    N= AI.size();

    for (I=0; I < N; I++) {
      currentInstance = (SymbolInstance)AI.get(I);
      for (K= 0; K < I; K++) {
        tempInstance= (SymbolInstance)AI.get(K);
        R= currentInstance.getInterval().overlaps(tempInstance.getInterval(), tolerance);
        if (R > 0) {
          currentOverlap.overlayType= R;
          currentOverlap.firstOverlap= I;
          currentOverlap.secondOverlap= K;
          currentOverlap.timeInterval= currentInstance.getInterval().intersect(tempInstance.getInterval());
          overlaps.add(currentOverlap);
        }
      }
    }
  }

  public int getNumOverlaps () {
    return overlaps.size();
  }

  public int getOverlayType (int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return (currentOverlap.overlayType);
  }

  public int getFirstInstance (int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return (currentOverlap.firstOverlap);
  }

  public int getSecondInstance (int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return (currentOverlap.secondOverlap);
  }

  public long getFirstInterval (int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return (currentOverlap.timeInterval.getStart());
  }

  public long getSecondInterval (int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return (currentOverlap.timeInterval.getStart());
  }

  public TimeInterval getInterval(int I) {
    currentOverlap= (overlapStruct)overlaps.get(I);
    return currentOverlap.timeInterval;
  }
}

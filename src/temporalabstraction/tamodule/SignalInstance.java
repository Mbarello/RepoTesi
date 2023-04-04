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
import temporalabstraction.instance.*;

public class SignalInstance {
  int signal_id;
  ArrayList trendInstances, stationaryInstances, stateInstances, jointInstances;

  public SignalInstance(int signal) {
    signal_id= signal;
    trendInstances= new ArrayList();
    stationaryInstances= new ArrayList();
    stateInstances= new ArrayList();
    jointInstances= new ArrayList();
  }

  public void init () {
    trendInstances.clear();
    stationaryInstances.clear();
    stateInstances.clear();
    jointInstances.clear();
  }

  public void setSignal (int signal) {
    signal_id= signal;
  }

  public int getSignal() {
    return signal_id;
  }

  public void addTrendInstance (TrendInstance T) {
    trendInstances.add(T);
  }

  public void addTrendInstanceSorted (TrendInstance T) {
    TrendInstance Temp;
    int I, N;
    boolean stop;

    N= trendInstances.size();
    I= 0; stop= false;

    while ((!stop) && (I < N)) {
      Temp= (TrendInstance)trendInstances.get(I);
      if (Temp.getInterval().getStartStamp().after(T.getInterval().getStartStamp()))
        stop= true;
      else I++;
    }

    if (stop) trendInstances.add(I, T);
    else trendInstances.add(T);
  }

  public void addStationaryInstance (StationaryInstance T) {
    stationaryInstances.add(T);
  }

  public void addStationaryInstanceSorted (StationaryInstance T) {
    StationaryInstance Temp;
    int I, N;
    boolean stop;

    N= stationaryInstances.size();
    I= 0; stop= false;

    while ((!stop) && (I < N)) {
      Temp= (StationaryInstance)stationaryInstances.get(I);
      if (Temp.getInterval().getStartStamp().after(T.getInterval().getStartStamp()))
        stop= true;
      else I++;
    }

    if (stop) stationaryInstances.add(I, T);
    else stationaryInstances.add(T);
  }

  public void addStateInstance (StateInstance S) {
    stateInstances.add(S);
  }

  public void addStateInstanceSorted (StateInstance T) {
    StateInstance Temp;
    int I, N;
    boolean stop;

    N= stateInstances.size();
    I= 0; stop= false;

    while ((!stop) && (I < N)) {
      Temp= (StateInstance)stateInstances.get(I);
      if (Temp.getInterval().getStartStamp().after(T.getInterval().getStartStamp()))
        stop= true;
      else I++;
    }

    if (stop) stateInstances.add(I, T);
    else stateInstances.add(T);
  }

  public void addJointInstance (JointInstance J) {
    jointInstances.add(J);
  }

  public void addJointInstanceSorted (JointInstance T) {
    JointInstance Temp;
    int I, N;
    boolean stop;

    N= jointInstances.size();
    I= 0; stop= false;

    while ((!stop) && (I < N)) {
      Temp= (JointInstance)jointInstances.get(I);
      if (Temp.getInterval().getStartStamp().after(T.getInterval().getStartStamp()))
        stop= true;
      else I++;
    }

    if (stop) jointInstances.add(I, T);
    else jointInstances.add(T);
  }

  public TrendInstance getTrendInstance (int I) {
    return (TrendInstance)trendInstances.get(I);
  }

  public StationaryInstance getStationaryInstance (int I) {
    return (StationaryInstance)stationaryInstances.get(I);
  }

  public StateInstance getStateInstance (int I) {
    return (StateInstance)stateInstances.get(I);
  }

  public JointInstance getJointInstance (int I) {
    return (JointInstance)jointInstances.get(I);
  }

  public ArrayList getTrends () {
    return trendInstances;
  }

  public ArrayList getStationarities () {
    return stationaryInstances;
  }

  public ArrayList getStates () {
    return stateInstances;
  }

  public ArrayList getJoints () {
    return jointInstances;
  }
}

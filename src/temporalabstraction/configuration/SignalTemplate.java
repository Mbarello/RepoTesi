package temporalabstraction.configuration;

import temporalabstraction.expected.*;

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
 * @author Alessio Bottrighi
 * @version 1.1
 */

import java.util.ArrayList;

public class SignalTemplate {
  int signal_id;
  ArrayList expectedTrends, expectedStationaries, expectedStates, expectedJoints;

  public SignalTemplate(int signal) {
    signal_id= signal;
    expectedTrends= new ArrayList();
    expectedStationaries= new ArrayList();
    expectedStates= new ArrayList();
    expectedJoints= new ArrayList();
  }

  public void init () {
    expectedTrends.clear();
    expectedStationaries.clear();
    expectedStates.clear();
    expectedJoints.clear();
  }

  public void setSignal (int signal) {
    signal_id= signal;
  }

  public int getSignal() {
    return signal_id;
  }

  public void addTrend (ExpectedTrend T) {
    expectedTrends.add(T);
  }

  public void addStationary (ExpectedStationary T) {
    expectedStationaries.add(T);
  }

  public void addState (ExpectedState S) {
    expectedStates.add(S);
  }

  public void addJoint (ExpectedJoint J) {
    expectedJoints.add(J);
  }

  public ExpectedTrend getExpectedTrend (int I) {
    return (ExpectedTrend)expectedTrends.get(I);
  }

  public ExpectedStationary getExpectedStationary (int I) {
    return (ExpectedStationary)expectedStationaries.get(I);
  }

  public ExpectedState getExpectedState (int I) {
    return (ExpectedState)expectedStates.get(I);
  }

  public ExpectedJoint getExpectedJoint (int I) {
    return (ExpectedJoint)expectedJoints.get(I);
  }

  public boolean identify(int Signal) {
    return (this.signal_id == Signal);
  }

  public int getNumTrends() {
    return expectedTrends.size();
  }

  public int getNumStates() {
    return expectedStates.size();
  }

  public int getNumStationaries() {
    return expectedStationaries.size();
  }

  public int getNumJoints() {
    return expectedJoints.size();
  }

}

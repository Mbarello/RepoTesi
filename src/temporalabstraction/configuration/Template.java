package temporalabstraction.configuration;

import temporalabstraction.symbol.*;
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
 *
 * @version 1.1
 */
import java.util.ArrayList;

public class Template {
  ArrayList templates;

  public Template() {
    templates= new ArrayList();
  }

  public void initAll() {
    int i, n;
    SignalTemplate temp;

    n= templates.size();
    for (i= 0; i < n; i++) {
      temp= (SignalTemplate)templates.get(i);
      temp.init();
    }
  }

  public void init (int I) {
    SignalTemplate temp= (SignalTemplate)templates.get(I);
    temp.init();
  }

  public void addSignalTemplate (SignalTemplate ST) {
    templates.add(ST);
  }

  public SignalTemplate getSignalTemplate (int I) {
    return (SignalTemplate)templates.get(I);
  }

  public void setSignal (int Template, int Signal) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    temp.setSignal(Signal);
  }

  public int getSignal (int I) {
    SignalTemplate temp= (SignalTemplate)templates.get(I);
    return temp.getSignal();
  }

  public SignalTemplate getTemplateFromSignal (int Signal) {

   for (int i=0;i<templates.size();i++)
     if (((SignalTemplate)templates.get(i)).identify(Signal))
       return ((SignalTemplate)templates.get(i));
   return null;
  }


  public void addTrend (ExpectedTrend T, int I) {
    SignalTemplate temp= (SignalTemplate)templates.get(I);
    temp.addTrend(T);
  }

  public void addState (ExpectedState S, int I) {
    SignalTemplate temp= (SignalTemplate)templates.get(I);
    temp.addState(S);
  }

  public void addJoint (ExpectedJoint J, int I) {
    SignalTemplate temp= (SignalTemplate)templates.get(I);
    temp.addJoint(J);
  }

  public ExpectedTrend getExpectedTrend (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (ExpectedTrend)temp.getExpectedTrend(Position);
  }

  public ExpectedState getExpectedState (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (ExpectedState)temp.getExpectedState(Position);
  }

  public ExpectedJoint getExpectedJoint (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (ExpectedJoint)temp.getExpectedJoint(Position);
  }

  public TrendSymbol getTrendSymbol (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (TrendSymbol)temp.getExpectedTrend(Position).getSymbol();
  }

  public StateSymbol getStateSymbol (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (StateSymbol)temp.getExpectedState(Position).getSymbol();
  }

  public JointSymbol getJointSymbol (int Template, int Position) {
    SignalTemplate temp= (SignalTemplate)templates.get(Template);
    return (JointSymbol)temp.getExpectedJoint(Position).getSymbol();
  }
}

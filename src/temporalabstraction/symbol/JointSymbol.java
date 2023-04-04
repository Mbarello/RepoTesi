package temporalabstraction.symbol;

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
public class JointSymbol extends Symbol {
  TrendSymbol trendSymbol;
  StateSymbol stateSymbol;

  public JointSymbol() {
    super ();
    trendSymbol= new TrendSymbol();
    stateSymbol= new StateSymbol();
  }

  public JointSymbol(int Signal, String SSymbol,
                     TrendSymbol ttrendSymbol, StateSymbol sstateSymbol,
                     String Descr, float colorR, float colorG, float colorB) {
    super (Signal, SSymbol, Descr, colorR, colorG, colorB);
    trendSymbol= ttrendSymbol;
    stateSymbol= sstateSymbol;
  }

  public void setTrendSymbol (TrendSymbol ttrendSymbol) {
    trendSymbol= ttrendSymbol;
  }

  public void setStateSymbol (StateSymbol tstateSymbol) {
    stateSymbol= tstateSymbol;
  }

  public void setTrendSymbol (int Signal, String ssymbol) {
    trendSymbol= new TrendSymbol ();
    trendSymbol.setSignal (Signal);
    trendSymbol.setSymbol (ssymbol);
  }

  public void setStateSymbol (int Signal, String ssymbol) {
    stateSymbol= new StateSymbol ();
    stateSymbol.setSignal (Signal);
    stateSymbol.setSymbol (ssymbol);
  }

  public TrendSymbol getTrendSymbol () {
    return trendSymbol;
  }

  public StateSymbol getStateSymbol () {
    return stateSymbol;
  }

}

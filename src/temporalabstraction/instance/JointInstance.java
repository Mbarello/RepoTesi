package temporalabstraction.instance;

import temporalabstraction.symbol.JointSymbol;

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
public class JointInstance extends SymbolInstance {
  JointSymbol symbol;

  public JointInstance() {
    symbol= new JointSymbol();
    timeInterval= new TimeInterval();
  }

  public JointInstance(JointSymbol ssymbol, TimeInterval ttime) {
    symbol= ssymbol;
    timeInterval= ttime;
  }

  public void setJointInstance(JointSymbol ssymbol, TimeInterval ttime) {
    symbol= ssymbol;
    timeInterval= ttime;
  }

  public JointSymbol getJointSymbol() {
    return symbol;
  }

  public String toString() {
    String result = new String();

    result= "Joint Instance -  Symbol: " + this.symbol.getSymbol();
    result= result + " Trend: " + this.symbol.getTrendSymbol().getSymbol() + " State: " + this.symbol.getStateSymbol().getSymbol();
    result= result + " - Start: " + this.getInterval().getStartH() + ":" + this.getInterval().getStartM() + ":" + this.getInterval().getStartS();
    result= result + " - End: " + this.getInterval().getEndH() + ":" + this.getInterval().getEndM() + ":" + this.getInterval().getEndS();

    return result;
  }
}

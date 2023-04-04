package temporalabstraction.instance;

import temporalabstraction.symbol.Symbol;

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
public class SymbolInstance {
  Symbol symbol;
  TimeInterval timeInterval;

  public SymbolInstance() {
    symbol= new Symbol();
    timeInterval= new TimeInterval();
  }

  public SymbolInstance(Symbol ssymbol, TimeInterval ttime) {
    symbol= ssymbol;
    timeInterval= ttime;
  }

  public void setSymbol (Symbol ssymbol) {
    symbol= ssymbol;
  }

  public void setInterval (TimeInterval ttime) {
    timeInterval= ttime;
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public TimeInterval getInterval() {
    return timeInterval;
  }
}

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

import java.awt.Color;

public class TrendSymbol extends Symbol {
  public TrendSymbol() {
    super ();
  }

  public TrendSymbol(int Signal, String SSymbol, String Descr, float colorR, float colorG, float colorB) {
    super (Signal, SSymbol, Descr, colorR, colorG, colorB);
  }

  public TrendSymbol(int Signal, String SSymbol, String Descr, Color ccolor) {
    super (Signal, SSymbol, Descr, ccolor);
  }
}

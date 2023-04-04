package temporalabstraction.expected;

import temporalabstraction.symbol.*;

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

import java.sql.Timestamp;

public class ExpectedSymbol {
  Symbol symbol;
  String temporalUnit;
  Timestamp minimumDuration;
  Timestamp maximumDuration;

  public ExpectedSymbol() {
    symbol= new Symbol();
    temporalUnit= new String();
    minimumDuration= new Timestamp(0);
    maximumDuration= new Timestamp(0);
  }

  public ExpectedSymbol(Symbol ssymbol, String TemporalUnit, long min, long max) {
    symbol= ssymbol;
    temporalUnit= TemporalUnit;
    minimumDuration= new Timestamp(min);
    maximumDuration= new Timestamp(max);
  }

  public void setSymbol (Symbol ssymbol) {
    symbol= ssymbol;
  }

  public void setTemporalUnit (String TemporalUnit) {
    temporalUnit= TemporalUnit;
  }

  public void setMinDuration (long min) {
    minimumDuration.setTime(min);
  }

  public void setMaxDuration (long max) {
    maximumDuration.setTime(max);
  }

  public Symbol getSymbol () {
    return symbol;
  }

  public String getTemporalUnit  () {
    return temporalUnit;
  }

  public long getMinDuration () {
    return minimumDuration.getTime();
  }

  public long getMaxDuration () {
    return maximumDuration.getTime();
  }

}

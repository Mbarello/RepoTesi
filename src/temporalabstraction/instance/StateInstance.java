package temporalabstraction.instance;

import org.jdom.Element;
import java.util.ArrayList;
import temporalabstraction.symbol.StateSymbol;
import temporalabstraction.tamodule.IntervalInfo;

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
public class StateInstance extends SymbolInstance {
  StateSymbol symbol;
  IntervalInfo intervalInfo;

  public StateInstance() {
    symbol= new StateSymbol();
    timeInterval= new TimeInterval();
    intervalInfo= new IntervalInfo();
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public StateInstance(org.jdom.Element dati, StateSymbol ssymbol) {
    symbol= ssymbol;

    try {
      System.out.println("  Nuovo intervallo state:");
      System.out.println("  Lower bound");
      System.out.println("->"        +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("second").getIntValue());
      System.out.println("->"        +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("minute").getIntValue());
      System.out.println("->"        +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("hour").getIntValue());
      System.out.println("->"        +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("day").getIntValue());
      System.out.println("->"        +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("month").getIntValue());
      System.out.println("  Upper bound");
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("year").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("second").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("minute").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("hour").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("day").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("month").getIntValue());
      System.out.println("->2"        +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("year").getIntValue());

      timeInterval = new TimeInterval(
        (
      dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000
      +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000
      +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000),
//                +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("day").getIntValue()*60*60*24
//                +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("month").getIntValue()*60*60*24*30
//                +dati.getChild("Lower").getChild("DateTimeSecond").getAttribute("year").getIntValue()*60*60*24*30*365) ,
    (
      dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000
      +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000
      +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000)
//                +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("day").getIntValue()*60*60*24
//                +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("month").getIntValue()*60*60*24*30
//                +dati.getChild("Upper").getChild("DateTimeSecond").getAttribute("year").getIntValue()*60*60*24*30*365)
        );
    }
    catch (Exception e ) {
      e.printStackTrace();
    }

  }


  public StateInstance(StateSymbol ssymbol, TimeInterval ttime, IntervalInfo Info) {
    symbol= ssymbol;
    timeInterval= ttime;
    intervalInfo= Info;
  }

  public void setStateInstance(StateSymbol ssymbol, TimeInterval ttime, IntervalInfo Info) {
    symbol= ssymbol;
    timeInterval= ttime;
    intervalInfo= Info;
  }

  public IntervalInfo getIntervalInfo() {
    return intervalInfo;
  }

  private void jbInit() throws Exception {
  }

  public ArrayList controlMax(long max){
     ArrayList result = new ArrayList();

     StateInstance tmp = this;

     while (tmp.timeInterval.length() > max)
     {
     long end = tmp.timeInterval.getEnd();
     long newStart = tmp.timeInterval.getStart()+max;
     tmp.timeInterval.setTime(tmp.timeInterval.getStart(),newStart);
     result.add(tmp);
     tmp = new StateInstance(tmp.symbol, new TimeInterval(newStart,end),null);

     }

     result.add(tmp);

     return result;
  }

  public StateSymbol getStateSymbol() {
    return symbol;
  }

  public String toString() {
    String result = new String();

    result= "State Instance -  Symbol: " + this.symbol.getSymbol();
    result= result + " - Start: " + this.getInterval().getStartH() + ":" + this.getInterval().getStartM() + ":" + this.getInterval().getStartS();
    result= result + " - End: " + this.getInterval().getEndH() + ":" + this.getInterval().getEndM() + ":" + this.getInterval().getEndS();

    return result;
  }

}

/*package temporalabstraction.expected;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import temporalabstraction.symbol.StateSymbol;

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
/*
public class ExpectedState extends ExpectedSymbol {
  StateSymbol symbol;
  double lowerBound, upperBound;
  int localWindow, maximumTimeGap;

  public ExpectedState() {
    symbol= new StateSymbol();
    minimumDuration= new Timestamp(0);
    maximumDuration= new Timestamp(0);
    lowerBound= upperBound= 0.0;
    localWindow= maximumTimeGap= 0;
  }

  public ExpectedState(StateSymbol ssymbol, long min, long max,
                       double Lower, double Upper, int LocWind, int MaxTimeGap, String TemporalTimeUnit) {
    symbol= ssymbol;
    super.setTemporalUnit(TemporalTimeUnit);

    if (TemporalTimeUnit.compareTo("second") == 0) {
      min= min * 1000;
      max= max * 1000;
    }
    else if (TemporalTimeUnit.compareTo("minute") == 0) {
      min= min * 60000;
      max= max * 60000;
    }
    else if (TemporalTimeUnit.compareTo("hour") == 0) {
      min= min * 3600000;
      max= max * 3600000;
    }

    minimumDuration= new Timestamp(min);
    maximumDuration= new Timestamp(max);
    lowerBound= Lower;
    upperBound= Upper;
    localWindow= LocWind;
    maximumTimeGap= MaxTimeGap;
  }

  public void setParameters(StateSymbol ssymbol, long min, long max,
                            double Lower, double Upper,
                            int LocWind, int MaxTimeGap, String TemporalTimeUnit) {
    symbol= ssymbol;
    super.setTemporalUnit(TemporalTimeUnit);

    if (TemporalTimeUnit.compareTo("second") == 0) {
      min= min * 1000;
      max= max * 1000;
    }
    else if (TemporalTimeUnit.compareTo("minute") == 0) {
      min= min * 60000;
      max= max * 60000;
    }
    else if (TemporalTimeUnit.compareTo("hour") == 0) {
      min= min * 3600000;
      max= max * 3600000;
    }

    minimumDuration.setTime(min);
    maximumDuration.setTime(max);
    lowerBound= Lower;
    upperBound= Upper;
    localWindow= LocWind;
    maximumTimeGap= MaxTimeGap;
  }

  public double getLowerBound () {
    return lowerBound;
  }

  public double getUpperBound () {
    return upperBound;
  }

  public int getLocalWindow () {
    return localWindow;
  }

  public int getMaximumTimeGap () {
    return maximumTimeGap;
  }

  public org.jdom.Element getXML(org.jdom.Element data) {
    org.jdom.Element datats = new org.jdom.Element("DateTimeSecond");
    Calendar today = Calendar.getInstance();
    today.get(Calendar.DAY_OF_MONTH);

    ArrayList attribList2 = new ArrayList();
    attribList2 = new ArrayList();
    org.jdom.Attribute attrib2=new org.jdom.Attribute("year", new Integer(today.get(Calendar.YEAR)).toString());
    attribList2.add(attrib2);
    attrib2=new org.jdom.Attribute("month", new Integer(today.get(Calendar.MONTH)).toString());
    attribList2.add(attrib2);
    attrib2=new org.jdom.Attribute("day", new Integer(today.get(Calendar.DAY_OF_MONTH)).toString());
    attribList2.add(attrib2);
    attrib2=new org.jdom.Attribute("hour", new Integer(today.get(Calendar.HOUR_OF_DAY)).toString());
    attribList2.add(attrib2);
    attrib2=new org.jdom.Attribute("minute", new Integer(today.get(Calendar.MINUTE)).toString());
    attribList2.add(attrib2);
    attrib2=new org.jdom.Attribute("second", new Integer(today.get(Calendar.SECOND)).toString());
    attribList2.add(attrib2);
    datats.setAttributes((List)attribList2);

   org.jdom.Element output = new org.jdom.Element("Abstractions");
   org.jdom.Element abstraction = new org.jdom.Element("Abstraction");
   ArrayList attribList = new ArrayList();
   org.jdom.Attribute attrib=new org.jdom.Attribute("minDuration","0");
   attribList.add(attrib);
   abstraction.setAttributes((List)attribList);

   org.jdom.Element info = new org.jdom.Element("AbstractionInfo");
   info.addContent(new org.jdom.Element("Comment"));
   info.addContent(datats);
   abstraction.addContent(info);

   org.jdom.Element state = new org.jdom.Element("AbstractionState");

   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("temporalUnit",this.getTemporalUnit());
   attribList.add(attrib);
   attrib=new org.jdom.Attribute("maximumTimeGap",new Integer(maximumTimeGap).toString());
//      attrib=new org.jdom.Attribute("maximumTimeGap","10000");///-->>
   attribList.add(attrib);
   state.setAttributes((List)attribList);



   org.jdom.Element ts = new org.jdom.Element("ThresholdsList");
   org.jdom.Element tlower = new org.jdom.Element("Threshold");
   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("name","T01");
   attribList.add(attrib);
   attrib=new org.jdom.Attribute("max", new Double(lowerBound).toString());
   attribList.add(attrib);
   tlower.setAttributes((List)attribList);

   ts.addContent(tlower);

   org.jdom.Element tupper = new org.jdom.Element("Thresholds");
   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("name","T02");
   attribList.add(attrib);
   attrib=new org.jdom.Attribute("max", new Double(upperBound).toString());
//      attrib=new org.jdom.Attribute("max", ""); //-->>>> da togliere
   attribList.add(attrib);
   tupper.setAttributes((List)attribList);

   ts.addContent(tupper);

   org.jdom.Element tnone = new org.jdom.Element("Thresholds");
   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("name","T03");
   attribList.add(attrib);
   attrib=new org.jdom.Attribute("max", ""); //-->>>> da togliere
   attribList.add(attrib);
   tnone.setAttributes((List)attribList);

   ts.addContent(tnone);

   org.jdom.Element con = new org.jdom.Element("Conditions");
   org.jdom.Element step = new org.jdom.Element("Step");

   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("threshold","T02");
   attribList.add(attrib);
   step.setAttributes((List)attribList);
   con.addContent(step);


   org.jdom.Element ccon = new org.jdom.Element("CounterConditions");
   step = new org.jdom.Element("Step");

   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("threshold","T01");
   attribList.add(attrib);
   step.setAttributes((List)attribList);
   ccon.addContent(step);


   step = new org.jdom.Element("Step");
   attribList = new ArrayList();
   attrib=new org.jdom.Attribute("threshold","T03");
   attribList.add(attrib);
   step.setAttributes((List)attribList);
   ccon.addContent(step);

   state.addContent(ts);
   state.addContent(con);
   state.addContent(ccon);
   abstraction.addContent(state);
   output.addContent(abstraction);
    return output ;
  }

  public StateSymbol getStateSymbol() {
    return symbol;
  }

}
*/

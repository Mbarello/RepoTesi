package temporalabstraction.expected;

import temporalabstraction.symbol.TrendSymbol;

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
import java.util.ArrayList;
import java.util.List;

public class ExpectedTrend extends ExpectedSymbol {
  TrendSymbol symbol;
  String trend;
  double minimumRate, maximumRate;
  int localWindow;
  int maximumTimeGap;

  public ExpectedTrend() {
    symbol= new TrendSymbol();
    minimumDuration= new Timestamp(0);
    maximumDuration= new Timestamp(0);
    trend= new String();
    minimumRate= maximumRate= 0.0;
    localWindow= maximumTimeGap= 0;
  }

  public ExpectedTrend(TrendSymbol ssymbol, long min, long max, String Trend,
                       double MinRate, double MaxRate,
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

    minimumDuration= new Timestamp(min);
    maximumDuration= new Timestamp(max);
    trend= Trend;
    minimumRate= MinRate;
    maximumRate= MaxRate;
    localWindow= LocWind;
    maximumTimeGap= MaxTimeGap;
  }

  public void setParameters(TrendSymbol ssymbol, long min, long max,
                            String Trend, double MinRate, double MaxRate,
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
    trend= Trend;
    minimumRate= MinRate;
    maximumRate= MaxRate;
    localWindow= LocWind;
    maximumTimeGap= MaxTimeGap;
  }

  public String getTrend () {
    return trend;
  }

  public double getMinimumRate () {
    return minimumRate;
  }

  public double getMaximumRate () {
    return maximumRate;
  }

  public int getLocalWindow () {
    return localWindow;
  }

  public int getMaximumTimeGap () {
    return maximumTimeGap;
  }

  public org.jdom.Element getXML(org.jdom.Element data) {

    org.jdom.Element output = new org.jdom.Element("Abstractions");
    org.jdom.Element abstraction = new org.jdom.Element("Abstraction");

/*

   TrendSymbol symbol;
   String trend;
   double minimumRate, maximumRate;
   int localWindow;
   int maximumTimeGap;

 */

 ArrayList attribList = new ArrayList();
 org.jdom.Attribute attrib=new org.jdom.Attribute("minDuration", new Integer((int)minimumDuration.getTime()).toString());
 attribList.add(attrib);
 abstraction.setAttributes((List)attribList);

 org.jdom.Element info = new org.jdom.Element("AbstractionInfo");
 info.addContent(new org.jdom.Element("Comment"));
 info.addContent((org.jdom.Element) data.clone());
 abstraction.addContent(info);
 org.jdom.Element tr = new org.jdom.Element("AbstractionTrend");

 attribList = new ArrayList();
 attrib=new org.jdom.Attribute("temporalUnit",this.getTemporalUnit());
 attribList.add(attrib);
 attrib=new org.jdom.Attribute("maximumTimeGap",new Integer(maximumTimeGap).toString());
 attribList.add(attrib);
 attrib=new org.jdom.Attribute("trend",trend);
 attribList.add(attrib);
 attrib=new org.jdom.Attribute("minRate",new Double(minimumRate).toString());
 attribList.add(attrib);
 attrib=new org.jdom.Attribute("maxRate",new Double(maximumRate).toString());
 attribList.add(attrib);
 attrib=new org.jdom.Attribute("localWin",new Integer(localWindow).toString());
 attribList.add(attrib);
 tr.setAttributes((List)attribList);

 abstraction.addContent(tr);
 output.addContent(abstraction);



  return output ;
  }

  public TrendSymbol getTrendSymbol() {
    return symbol;
  }

}

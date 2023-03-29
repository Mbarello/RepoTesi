/*package temporalabstraction.expected;

import temporalabstraction.symbol.StationarySymbol;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExpectedStationary extends ExpectedSymbol {
    StationarySymbol symbol;
    double maxRate, maxOscillationMargin;
    int maximumTimeGap;

    public ExpectedStationary() {
      symbol= new StationarySymbol();
      minimumDuration= new Timestamp(0);
      maximumDuration= new Timestamp(0);
      maxRate= maxOscillationMargin= 0.0;
      maximumTimeGap= 0;
    }

    public ExpectedStationary(StationarySymbol ssymbol, long min, long max, double MaxRate,
                         double MaxOscMargin, int MaxTimeGap, String TemporalTimeUnit) {
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
      maxRate= MaxRate;
      maxOscillationMargin= MaxOscMargin;
      maximumTimeGap= MaxTimeGap;
    }

    public void setParameters(StationarySymbol ssymbol, long min, long max, double MaxRate,
                         double MaxOscMargin, int MaxTimeGap, String TemporalTimeUnit) {
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
      maxRate= MaxRate;
      maxOscillationMargin= MaxOscMargin;
      maximumTimeGap= MaxTimeGap;
    }

    public double getMaxRate () {
      return maxRate;
    }

    public double getMaxOscillationMargin () {
      return maxOscillationMargin;
    }

    public int getMaximumTimeGap () {
      return maximumTimeGap;
    }

    public org.jdom.Element getXML(org.jdom.Element data) {

      org.jdom.Element output = new org.jdom.Element("Abstractions");
      org.jdom.Element abstraction = new org.jdom.Element("Abstraction");

      ArrayList attribList = new ArrayList();
      org.jdom.Attribute attrib=new org.jdom.Attribute("minDuration", new Integer((int)minimumDuration.getTime()).toString());
      attribList.add(attrib);
      abstraction.setAttributes((List)attribList);

      org.jdom.Element info = new org.jdom.Element("AbstractionInfo");
      info.addContent(new org.jdom.Element("Comment"));
      info.addContent((org.jdom.Element) data.clone());
      abstraction.addContent(info);
      org.jdom.Element tr = new org.jdom.Element("AbstractionStationary");

      attribList = new ArrayList();
      attrib=new org.jdom.Attribute("temporalUnit",this.getTemporalUnit());
      attribList.add(attrib);
      attrib=new org.jdom.Attribute("maximumTimeGap",new Integer(maximumTimeGap).toString());
      attribList.add(attrib);
      attrib=new org.jdom.Attribute("maxRate",new Double(maxRate).toString());
      attribList.add(attrib);
      attrib=new org.jdom.Attribute("maxOscillationMargin",new Double(maxOscillationMargin).toString());
      attribList.add(attrib);
      tr.setAttributes((List)attribList);

      abstraction.addContent(tr);
      output.addContent(abstraction);

      return output ;
    }

    public StationarySymbol getStationarySymbol() {
      return symbol;
    }

  }
*/
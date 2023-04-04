package timeserie;

/**
 * Title:        Time Serie Definition
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author Giorgio Leonardi
 * @author Alessio Bottrighi
 * @version 1.0
 */
import org.jdom.Element;
import org.jdom.Attribute;
import java.util.ArrayList;
import java.util.List;

public class TPoint {
  private double value, stamp;

  public TPoint () {
    value= stamp= 0;
  }

  public TPoint (double x, double y) {
    value= y;
    stamp= x;
  }

  public TPoint (TPoint TP) {
    value= TP.getValue();
    stamp= TP.getStamp();
  }

  public void setPoint (double x, double y) {
    value= y;
    stamp= x;
  }

  public void setValue(double y) {
    value= y;
  }

  public void setStamp(double x) {
    stamp= x;
  }

  public double getValue() {
    return value;
  }

  public double getStamp() {
    return stamp;
  }

  public org.jdom.Element getXML() {

    org.jdom.Element output = new org.jdom.Element("NumericData");

    org.jdom.Element stampXml = new org.jdom.Element("DateTimeSecond");
    org.jdom.Element valueXml = new org.jdom.Element("Adimensional");

    ArrayList attribList = new ArrayList();
    attribList = new ArrayList();
    org.jdom.Attribute attrib=new org.jdom.Attribute("year", new Integer(2006+((int)(stamp/(3600*24*30*12)))).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("month", new Integer(1+((int)(stamp/(3600*24*30)))%12).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("day", new Integer(1+((int)(stamp/(3600*24)))%30).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("hour", new Integer(((int)(stamp/3600))%60).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("minute", new Integer(((int)(stamp/60))%60).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("second", new Integer((int)(stamp%60)).toString());
    attribList.add(attrib);
    stampXml.setAttributes((List)attribList);

    attribList = new ArrayList();
    attrib= new org.jdom.Attribute("value", new Float(this.value).toString());
    attribList.add(attrib);
    attrib= new org.jdom.Attribute("precision", "float");
    attribList.add(attrib);
    valueXml.setAttributes((List)attribList);

    output.addContent(stampXml);
    output.addContent(valueXml);

    return output ;
  }


}

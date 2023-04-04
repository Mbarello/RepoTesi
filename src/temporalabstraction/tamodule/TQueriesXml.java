package temporalabstraction.tamodule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hcklab.project.tempo.facade.TempoFacadeInterface;
import org.hcklab.project.tempo.facade.DefaultTempoFacade;
import java.util.Iterator;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import java.io.IOException;
import java.io.FileOutputStream;

import timeserie.TTimeSerie;
import temporalabstraction.configuration.*;
import cases.TCase;
import temporalabstraction.instance.*;
import temporalabstraction.symbol.*;
import temporalabstraction.expected.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Alessio Bottrighi
 * @version 1.1
 */
public class TQueriesXml {
//  private org.jdom.Element serieXml;
  private Template template;
  private TCase tcase;
  //private  int signal_id;
  private org.jdom.Element request_info;
  private org.jdom.Element datats;
  private TempoFacadeInterface facade;

  public TQueriesXml(TCase tc, Template t) {

    this.template  = t;
    this.tcase = tc;
//    this.serieXml = s.getXML();
//    this.signal_id= signal;
    this.facade = new DefaultTempoFacade();
    this.request_info = new org.jdom.Element("RequestInfo");
    org.jdom.Element user = new org.jdom.Element("user");
    user.addContent("admin");
    this.request_info.addContent(user);
    this.datats = new org.jdom.Element("DateTimeSecond");
    Calendar today = Calendar.getInstance();
    today.get(Calendar.DAY_OF_MONTH);

    ArrayList attribList = new ArrayList();
    attribList = new ArrayList();
    org.jdom.Attribute attrib=new org.jdom.Attribute("year", new Integer(today.get(Calendar.YEAR)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("month", new Integer(today.get(Calendar.MONTH)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("day", new Integer(today.get(Calendar.DAY_OF_MONTH)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("hour", new Integer(today.get(Calendar.HOUR_OF_DAY)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("minute", new Integer(today.get(Calendar.MINUTE)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("second", new Integer(today.get(Calendar.SECOND)).toString());
    attribList.add(attrib);
    datats.setAttributes((List)attribList);

    this.request_info.addContent(datats);
  }

  public void startNewXml() {
//    this.serieXml = s.getXML();
//    this.signal_id= signal;
    this.facade = new DefaultTempoFacade();
    this.request_info = new org.jdom.Element("RequestInfo");
    org.jdom.Element user = new org.jdom.Element("user");
    user.addContent("admin");
    this.request_info.addContent(user);
    this.datats = new org.jdom.Element("DateTimeSecond");
    Calendar today = Calendar.getInstance();
    today.get(Calendar.DAY_OF_MONTH);

    ArrayList attribList = new ArrayList();
    attribList = new ArrayList();
    org.jdom.Attribute attrib=new org.jdom.Attribute("year", new Integer(today.get(Calendar.YEAR)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("month", new Integer(today.get(Calendar.MONTH)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("day", new Integer(today.get(Calendar.DAY_OF_MONTH)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("hour", new Integer(today.get(Calendar.HOUR_OF_DAY)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("minute", new Integer(today.get(Calendar.MINUTE)).toString());
    attribList.add(attrib);
    attrib=new org.jdom.Attribute("second", new Integer(today.get(Calendar.SECOND)).toString());
    attribList.add(attrib);
    datats.setAttributes((List)attribList);

    this.request_info.addContent(datats);
  }

  public SignalInstance getSignalInstance(int signal)
  throws java.lang.Exception {

    int I;
    StationaryInstance statInst;
    StationarySymbol stSymbol;
    TrendInstance trendInst;

    SignalInstance output = new SignalInstance(this.template.getSignal(signal));
    System.out.println("Creata la serie di istanze vuota");

    TTimeSerie ts = this.tcase.getSerie(signal);
    org.jdom.Element serie = ts.getXML(datats);
    System.out.println("Creata la sequenza XML della TS da analizzare");

    SignalTemplate st = this.template.getSignalTemplate(signal);
    System.out.println("Recuperato il template per analizzare la TS");

    this.getStateInstance(output, serie, st);
    System.out.println("Eseguita l'analisi degli States");

    this.getTrendInstance(output, serie, st);
    System.out.println("Eseguita l'analisi dei Trends");

    this.getStationaryInstance(output, serie, st);
    System.out.println("Eseguita l'analisi delle Stazionarieta");


    ///
    /// INSERIRE QUI IL CODICE DI MERGE TRA TREND E STAZIONARIETA'
    ///

    for (I= 0; I < output.trendInstances.size(); I++) {
      trendInst= output.getTrendInstance(I);
      if (trendInst.getTrendSymbol().getDescription().compareTo("UNKNOWN") == 0) {
        output.trendInstances.remove(I);
      }
    }

    for (I= 0; I < output.stationaryInstances.size(); I++) {
      statInst= output.getStationaryInstance(I);
      if (statInst.getStationarySymbol().getDescription().compareTo("UNKNOWN") != 0) {

        stSymbol= statInst.getStationarySymbol();
        TrendSymbol trSymbol= new TrendSymbol(stSymbol.getSignal(),
                                              stSymbol.getSymbol(),
                                              stSymbol.getDescription(),
                                              stSymbol.getColor());
        TrendInstance temp= new TrendInstance(trSymbol,
                                              statInst.getInterval(),
                                              statInst.getIntervalInfo());
        output.addTrendInstanceSorted(temp);
      }
    }

    for (I= 1; I < output.trendInstances.size(); I++){
      long newStart =output.getTrendInstance(I-1).getInterval().getEnd();
      long newEnd = output.getTrendInstance(I).getInterval().getStart();
      if (newStart != newEnd ) {
        TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                            new TimeInterval(newStart,newEnd),
                                            null);
        output.addTrendInstanceSorted(s);
      }
    }

    TrendInstance primo= null;
    if (output.trendInstances.size() > 0) primo= (TrendInstance)output.trendInstances.get(0);
    if (primo != null) {
      long firstTimeStamp = (long)(ts.getTStamp (0) * 1000);
      if (firstTimeStamp < primo.getInterval().getStart()) {
        TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                            new TimeInterval(firstTimeStamp , primo.getInterval().getStart()),
                                            null);
        output.addTrendInstanceSorted(s);
      }
    }

    primo= null;
    if (output.trendInstances.size() > 0) primo= (TrendInstance)output.trendInstances.get(output.trendInstances.size() - 1);
    if (primo != null) {
      long lastTimeStamp = (long)(ts.getTStamp(ts.getNumValues() - 1) * 1000);
      if (lastTimeStamp > primo.getInterval().getEnd()) {
        TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                            new TimeInterval(primo.getInterval().getEnd(), lastTimeStamp),
                                            null);
        output.addTrendInstanceSorted(s);
      }
    }
    else if (ts.getNumValues() > 0) {
      long firstTimeStamp = (long)(ts.getTStamp(0) * 1000);
      long lastTimeStamp = (long)(ts.getTStamp(ts.getNumValues() - 1) * 1000);

      TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                          new TimeInterval(firstTimeStamp, lastTimeStamp),
                                          null);
      output.addTrendInstanceSorted(s);
    }



    ///
    /// INSERIRE QUI IL CODICE DI CONTROLLO PER RIEMPIRE LA SERIE DI STATES SE NESSUNO STATE E' STATO TROVATO
    ///

    if ((output.stateInstances.size() == 0) && (ts.getNumValues() > 0)) {
      long firstTimeStamp = (long)(ts.getTStamp(0) * 1000);
      long lastTimeStamp = (long)(ts.getTStamp(ts.getNumValues() - 1) * 1000);

      StateInstance s = new StateInstance(new StateSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                          new TimeInterval(firstTimeStamp, lastTimeStamp),
                                          null);
      output.addStateInstanceSorted(s);
    }

    return output;
  }

  public ArrayList getSignalsInstance()
  throws java.lang.Exception {
    ArrayList result = new ArrayList();

    for(int i= 0; i < this.tcase.getNumSignals(); i++){
      SignalInstance output = new SignalInstance(this.template.getSignal(i));///!!!!!???????????

      TTimeSerie ts = this.tcase.getSerie(i);
      org.jdom.Element serie = ts.getXML(datats);

      SignalTemplate st = this.template.getSignalTemplate(i);

      this.getStateInstance(output,serie, st);
      this.getTrendInstance(output,serie,st);
      this.getStationaryInstance(output,serie,st);

      result.add(output);
    }
    return result;
  }

  private void getStateInstance(SignalInstance output, org.jdom.Element serie, SignalTemplate st)
  throws java.lang.Exception {

    org.jdom.Document result = new org.jdom.Document();

    this.startNewXml();

    for(int i=0; i < st.getNumStates(); i++){
      org.jdom.Element tar = new org.jdom.Element("TemporalAbstractionRequest");
      tar.addContent((org.jdom.Element)this.request_info.clone());
      ExpectedState est= st.getExpectedState(i);
      org.jdom.Element t = est.getXML(datats);
      tar.addContent(t);
      //definisci elemento della query di stato

      //tar.addContent(abstractions);
      tar.addContent((org.jdom.Element)serie.clone());

      org.jdom.Document query = new org.jdom.Document();
      query.setRootElement(tar);

      try {
          //serializzo il documento in un file
          FileOutputStream out = new FileOutputStream(".\\Risposta\\q1State-"+i+".xml");
          XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
          serializer.output(query, out);
          out.flush();
          out.close();
      }
      catch(IOException e) {
        System.out.println(e);
      }

      result = this.facade.performTemporalAbstraction(query);
      System.out.println("<<<<<<<<<<<<<<<<<<----------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      //     StateInstance tmp = new StateInstance();
      // estrai dati da risposta
      //     org.jdom.Document  result= new Document();


      try {
          //serializzo il documento in un file
          FileOutputStream out = new FileOutputStream(".\\Risposta\\resu1t-q1State-"+i+".xml");
          XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
          serializer.output(result, out);
          out.flush();
          out.close();
      }
      catch(IOException e) {
        System.out.println(e);
      }


      List macronodi = result.getRootElement().getChild("Responses").getChild("Response").getChild("TimeIntervals").getChildren();
      Iterator  iterator = macronodi.iterator();

      while (iterator.hasNext()) {

        Element nodo = (Element)iterator.next();
        StateInstance etS = new StateInstance(nodo, st.getExpectedState(i).getStateSymbol());
        ArrayList states = etS.controlMax(st.getExpectedState(i).getMaxDuration());
        for (int j =0;j<states.size();j++)
          output.addStateInstanceSorted((StateInstance)states.get(j));
      }
    }

    try {
      List macronodi2= result.getRootElement().getChild("Responses").getChild("Response").getChild("Data").getChild("NumericDataSeries").getChildren();
      Iterator  iterator2 = macronodi2.iterator();
      Element nodo= null;
      if (iterator2.hasNext()) nodo= (Element)iterator2.next();
      StateInstance primo= null;
      if (output.stateInstances.size() > 0) primo= (StateInstance)output.stateInstances.get(0);
      if (primo != null) {
        long firstTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
                              nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
                              nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
        if (firstTimeStamp < primo.getInterval().getStart()) {
          StateInstance s = new StateInstance(new StateSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                              new TimeInterval(firstTimeStamp , primo.getInterval().getStart()),
                                              null);
          output.addStateInstanceSorted(s);
        }
      }

      for (int i =1;i<output.stateInstances.size() ;i++){
        long newStart = output.getStateInstance(i-1).getInterval().getEnd();
        long newEnd = output.getStateInstance(i).getInterval().getStart();
        if (newStart != newEnd ) {
          StateInstance s = new StateInstance(new StateSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                              new TimeInterval(newStart,newEnd),
                                              null);
          output.addStateInstanceSorted(s);
        }
      }

      while (iterator2.hasNext()) {
        nodo= (Element)iterator2.next();
      }
      primo= null;
      if (output.stateInstances.size() > 0) primo= (StateInstance)output.stateInstances.get(output.stateInstances.size() - 1);
      if (primo != null) {
        long lastTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
                              nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
                              nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
        if (lastTimeStamp > primo.getInterval().getEnd()) {
          StateInstance s = new StateInstance(new StateSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                              new TimeInterval(primo.getInterval().getEnd(), lastTimeStamp),
                                              null);
          output.addStateInstanceSorted(s);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

 }

 private void getTrendInstance(SignalInstance output, org.jdom.Element serie, SignalTemplate st)
 throws java.lang.Exception {
  org.jdom.Document result = new org.jdom.Document();
  org.jdom.Element tar = new org.jdom.Element("TemporalAbstractionRequest");

   this.startNewXml();
   tar.addContent(this.request_info);
   //tar.addContent(abstractions);


   for(int i= 0; i < st.getNumTrends(); i++){

     tar = new org.jdom.Element("TemporalAbstractionRequest");
     tar.addContent(st.getExpectedTrend(i).getXML(datats));
     //definisci elemento della query di stato
     tar.addContent((org.jdom.Element)serie.clone());

     org.jdom.Document query = new org.jdom.Document(tar);

     try {
         //serializzo il documento in un file
         FileOutputStream out = new FileOutputStream(".\\Risposta\\q1Trend-"+i+".xml");
         XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
         serializer.output(query, out);
         out.flush();
         out.close();
     }
     catch(IOException e) {
         System.out.println(e);
     }

     result = this.facade.performTemporalAbstraction(query);

     //TrendInstance tmp = new TrendInstance();
     //estrai dati da risposta
     //org.jdom.Document  result = new Document();

     try {
         //serializzo il documento in un file
         FileOutputStream out = new FileOutputStream(".\\Risposta\\resu1t-q1Trend-"+i+".xml");
         XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
         serializer.output(result, out);
         out.flush();
         out.close();
     }
     catch(IOException e) {
         System.out.println(e);
     }

     List macronodi = result.getRootElement().getChild("Responses").getChild("Response").getChild("TimeIntervals").getChildren();
     Iterator  iterator = macronodi.iterator();

     while (iterator.hasNext()) {
       Element nodo = (Element)iterator.next();
       TrendInstance etS = new TrendInstance(nodo, st.getExpectedTrend(i).getTrendSymbol());
       ArrayList trends = etS.controlMax(st.getExpectedTrend(i).getMaxDuration());
       for (int j =0;j<trends.size();j++)
       output.addTrendInstanceSorted((TrendInstance)trends.get(j));
       ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

     }
   }

   try {
     List macronodi2= result.getRootElement().getChild("Responses").getChild("Response").getChild("Data").getChild("NumericDataSeries").getChildren();
     Iterator  iterator2 = macronodi2.iterator();
     Element nodo= null;
     if (iterator2.hasNext()) nodo= (Element)iterator2.next();
     TrendInstance primo= null;
     if (output.trendInstances.size() > 0) primo= (TrendInstance)output.trendInstances.get(0);
     if (primo != null) {
       long firstTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
           nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
           nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
       if (firstTimeStamp < primo.getInterval().getStart()) {
         TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(firstTimeStamp , primo.getInterval().getStart()),
                                             null);
         output.addTrendInstanceSorted(s);
       }
     }

     for (int i= 1; i < output.trendInstances.size(); i++){
       long newStart = output.getTrendInstance(i-1).getInterval().getEnd();
       long newEnd = output.getTrendInstance(i).getInterval().getStart();
       if (newStart != newEnd ) {
         TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(newStart,newEnd),
                                             null);
         output.addTrendInstanceSorted(s);
       }
     }

     while (iterator2.hasNext()) {
       nodo= (Element)iterator2.next();
     }
     primo= null;
     if (output.trendInstances.size() > 0) primo= (TrendInstance)output.trendInstances.get(output.trendInstances.size() - 1);
     if (primo != null) {
       long lastTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
           nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
           nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
       if (lastTimeStamp > primo.getInterval().getEnd()) {
         TrendInstance s = new TrendInstance(new TrendSymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(primo.getInterval().getEnd(), lastTimeStamp),
                                             null);
         output.addTrendInstanceSorted(s);
       }
     }

   } catch (Exception e) {
     e.printStackTrace();
   }

 }


 private void getStationaryInstance(SignalInstance output, org.jdom.Element serie, SignalTemplate st)
 throws java.lang.Exception {

  org.jdom.Document result= new org.jdom.Document();
   org.jdom.Element tar = new org.jdom.Element("TemporalAbstractionRequest");
   this.startNewXml();
   tar.addContent(this.request_info);
   //tar.addContent(abstractions);


   for(int i= 0; i < st.getNumStationaries(); i++){

     tar = new org.jdom.Element("TemporalAbstractionRequest");
     tar.addContent(st.getExpectedStationary(i).getXML(datats));
     //definisci elemento della query di stato
     tar.addContent((org.jdom.Element)serie.clone());

     org.jdom.Document query = new org.jdom.Document(tar);

     try {
         //serializzo il documento in un file
         FileOutputStream out = new FileOutputStream(".\\Risposta\\q1Stationary-"+i+".xml");
         XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
         serializer.output(query, out);
         out.flush();
         out.close();
     }
     catch(IOException e) {
         System.out.println(e);
     }

     result = this.facade.performTemporalAbstraction(query);

     //TrendInstance tmp = new TrendInstance();
     //estrai dati da risposta
     //org.jdom.Document  result = new Document();

     try {
         //serializzo il documento in un file
         FileOutputStream out = new FileOutputStream(".\\Risposta\\resu1t-q1Stationary-"+i+".xml");
         XMLOutputter serializer = new XMLOutputter(" ", true, "ISO-8859-1");
         serializer.output(result, out);
         out.flush();
         out.close();
     }
     catch(IOException e) {
         System.out.println(e);
     }

     List macronodi = result.getRootElement().getChild("Responses").getChild("Response").getChild("TimeIntervals").getChildren();
      System.out.println("ELEMENTO: " + result.getRootElement().getChild("Responses").getChild("Response").getChild("TimeIntervals"));
     Iterator iterator = macronodi.iterator();

     while (iterator.hasNext()) {
       Element nodo = (Element)iterator.next();

       System.out.println("Nodo: " + nodo.getText());

       StationaryInstance etS = new StationaryInstance(nodo, st.getExpectedStationary(i).getStationarySymbol());
       ArrayList stationaries = etS.controlMax(st.getExpectedStationary(i).getMaxDuration());
       for (int j=0; j < stationaries.size(); j++)
       output.addStationaryInstanceSorted((StationaryInstance)stationaries.get(j));
       ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     }
   }

   try {
     List macronodi2= result.getRootElement().getChild("Responses").getChild("Response").getChild("Data").getChild("NumericDataSeries").getChildren();
     Iterator  iterator2 = macronodi2.iterator();
     Element nodo= null;
     if (iterator2.hasNext()) nodo= (Element)iterator2.next();
     StationaryInstance primo= null;
     if (output.stationaryInstances.size() > 0) primo= (StationaryInstance)output.stationaryInstances.get(0);

     if (primo != null) {
       long firstTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
           nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
           nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
       if (firstTimeStamp < primo.getInterval().getStart()) {
         StationaryInstance s = new StationaryInstance(new StationarySymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(firstTimeStamp , primo.getInterval().getStart()),
                                             null);
         output.addStationaryInstanceSorted(s);
       }
     }

     for (int i= 1; i < output.stationaryInstances.size(); i++){
       long newStart =((StationaryInstance)output.stationaryInstances.get(i-1)).getInterval().getEnd();
       long newEnd = ((StationaryInstance)output.stationaryInstances.get(i)).getInterval().getStart();
       if (newStart != newEnd ) {
         StationaryInstance s = new StationaryInstance(new StationarySymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(newStart,newEnd),
                                             null);
         output.addStationaryInstanceSorted(s);
       }
     }

     while (iterator2.hasNext()) {
       nodo= (Element)iterator2.next();
     }
     primo= null;
     if (output.stationaryInstances.size() > 0) primo= (StationaryInstance)output.stationaryInstances.get(output.stationaryInstances.size() - 1);
     if (primo != null) {
       long lastTimeStamp = nodo.getChild("DateTimeSecond").getAttribute("second").getIntValue()*1000 +
           nodo.getChild("DateTimeSecond").getAttribute("minute").getIntValue()*60000 +
           nodo.getChild("DateTimeSecond").getAttribute("hour").getIntValue()*3600000;
       if (lastTimeStamp > primo.getInterval().getEnd()) {
         StationaryInstance s = new StationaryInstance(new StationarySymbol(-1, "UNKNOWN", "UNKNOWN", 0, 0, 0),
                                             new TimeInterval(primo.getInterval().getEnd(), lastTimeStamp),
                                             null);
         output.addStationaryInstanceSorted(s);
       }
     }

   } catch (Exception e) {
     e.printStackTrace();
   }

 }


}

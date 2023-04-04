package loader;

import java.io.IOException;
import java.util.ArrayList;
import temporalabstraction.configuration.*;
import temporalabstraction.symbol.*;
import temporalabstraction.expected.*;

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
public class TemplateLoader {
  int[] segnali;
  DBConnection connection;

  public TemplateLoader(int[] Segnali, DBConnection conn) throws IOException {
    int I;
    connection= conn;

    segnali= new int[Segnali.length];
    for (I= 0; I < Segnali.length; I++) {
      segnali[I]= Segnali[I];
    }
  }

  public SignalTemplate loadSignalTemplateDirect (int signal, int template_id) throws java.sql.SQLException {
    SignalTemplate template= new SignalTemplate(signal);
    ExpectedTrend trend;
    TrendSymbol trendSymbol;
    ExpectedStationary stationary;
    StationarySymbol stationarySymbol;
    ExpectedState state;
    StateSymbol stateSymbol;
    ExpectedJoint joint;
    JointSymbol jointSymbol;

    template.init();
    String Query= "select * from template T inner join template_trend TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedtrend ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symboltrend ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      trend = new ExpectedTrend();
      trendSymbol= new TrendSymbol();
      trendSymbol.setSignal(signal);
      trendSymbol.setSymbol(connection.getRowValueString("symbol"));
      trendSymbol.setDescription(connection.getRowValueString("description"));
      trendSymbol.setColor(connection.getRowValueInt("colorr"),
                           connection.getRowValueInt("colorg"),
                           connection.getRowValueInt("colorb"));

      trend.setParameters(trendSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"), connection.getRowValueString("trend"),
          connection.getRowValueDouble("minrate"), connection.getRowValueDouble("maxrate"),
          connection.getRowValueInt("localwindow"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addTrend(trend);
    }


    Query= "select * from template T inner join template_stationary TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedstationary ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symbolstationary ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      stationary = new ExpectedStationary();
      stationarySymbol= new StationarySymbol();
      stationarySymbol.setSignal(signal);
      stationarySymbol.setSymbol(connection.getRowValueString("symbol"));
      stationarySymbol.setDescription(connection.getRowValueString("description"));
      stationarySymbol.setColor(connection.getRowValueInt("colorr"),
                                connection.getRowValueInt("colorg"),
                                connection.getRowValueInt("colorb"));

      stationary.setParameters(stationarySymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"), connection.getRowValueDouble("maxrate"),
          connection.getRowValueDouble("maxoscillationmargin"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addStationary (stationary);
    }


    Query= "select * from template T inner join template_state TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedstate ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symbolstate ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      state = new ExpectedState();
      stateSymbol= new StateSymbol();
      stateSymbol.setSignal(signal);
      stateSymbol.setSymbol(connection.getRowValueString("symbol"));
      stateSymbol.setDescription(connection.getRowValueString("description"));
      stateSymbol.setColor(connection.getRowValueInt("colorr"),
                           connection.getRowValueInt("colorg"),
                           connection.getRowValueInt("colorb"));

      state.setParameters(stateSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"),
          connection.getRowValueDouble("lowerbound"), connection.getRowValueDouble("upperbound"),
          connection.getRowValueInt("localwindow"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addState(state);
    }

    Query= "select * from template T inner join template_joint TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedjoint ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symboljoint ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      jointSymbol= new JointSymbol();
      jointSymbol.setTrendSymbol(signal, connection.getRowValueString("trendsymbol"));
      jointSymbol.setStateSymbol(signal, connection.getRowValueString("statesymbol"));
      jointSymbol.setDescription(connection.getRowValueString("description"));
      jointSymbol.setColor(connection.getRowValueInt("colorr"),
                           connection.getRowValueInt("colorg"),
                           connection.getRowValueInt("colorb"));

      joint = new ExpectedJoint(jointSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"));

      template.addJoint(joint);
    }

    return template;
  }

  public SignalTemplate loadSignalTemplateIndexed (int Signal, int template_id) throws java.sql.SQLException {
    int signal= segnali[Signal];
    SignalTemplate template= new SignalTemplate(signal);
    ExpectedTrend trend;
    TrendSymbol trendSymbol;
    ExpectedStationary stationary;
    StationarySymbol stationarySymbol;
    ExpectedState state;
    StateSymbol stateSymbol;
    ExpectedJoint joint;
    JointSymbol jointSymbol;
    float colorR, colorG, colorB;

    template.init();
    String Query= "select ET.*, ST.* from template T inner join template_trend TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedtrend ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symboltrend ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      trend = new ExpectedTrend();
      trendSymbol= new TrendSymbol();
      trendSymbol.setSignal(signal);
      trendSymbol.setSymbol(connection.getRowValueString("symbol"));
      trendSymbol.setDescription(connection.getRowValueString("description"));
      colorR= connection.getRowValueFloat("colorr");
      colorG= connection.getRowValueFloat("colorg");
      colorB= connection.getRowValueFloat("colorb");
      trendSymbol.setColor(colorR, colorG, colorB);

      trend.setParameters(trendSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"), connection.getRowValueString("trend"),
          connection.getRowValueDouble("minrate"), connection.getRowValueDouble("maxrate"),
          connection.getRowValueInt("localwindow"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addTrend(trend);
    }


    Query= "select ET.*, ST.* from template T inner join template_stationary TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedstationary ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symbolstationary ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      stationary = new ExpectedStationary();
      stationarySymbol= new StationarySymbol();
      stationarySymbol.setSignal(signal);
      stationarySymbol.setSymbol(connection.getRowValueString("symbol"));
      stationarySymbol.setDescription(connection.getRowValueString("description"));
      stationarySymbol.setColor(connection.getRowValueInt("colorr"),
                                connection.getRowValueInt("colorg"),
                                connection.getRowValueInt("colorb"));


      stationary.setParameters(stationarySymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"), connection.getRowValueDouble("maxrate"),
          connection.getRowValueDouble("maxoscillationmargin"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addStationary (stationary);
    }


    Query= "select ET.*, ST.* from template T inner join template_state TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedstate ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symbolstate ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      state = new ExpectedState();
      stateSymbol= new StateSymbol();
      stateSymbol.setSignal(signal);
      stateSymbol.setSymbol(connection.getRowValueString("symbol"));
      stateSymbol.setDescription(connection.getRowValueString("description"));
      stateSymbol.setColor(connection.getRowValueInt("colorr"),
                           connection.getRowValueInt("colorg"),
                           connection.getRowValueInt("colorb"));


      state.setParameters(stateSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"),
          connection.getRowValueDouble("lowerbound"), connection.getRowValueDouble("upperbound"),
          connection.getRowValueInt("localwindow"), connection.getRowValueInt("maximumtimegap"),
          connection.getRowValueString("temporalunit"));

      template.addState(state);
    }

    Query= "select ET.*, ST.* from template T inner join template_joint TR on ";
    Query= Query + "T.template_id=TR.template_id inner join expectedjoint ET on ";
    Query= Query + "(TR.signal=ET.signal and TR.symbol=ET.symbol) ";
    Query= Query + "inner join symboljoint ST on ";
    Query= Query + "(TR.signal=ST.signal and TR.symbol=ST.symbol) ";
    Query= Query + "where TR.signal = " + signal + " AND TR.template_id = " + template_id;

    connection.RunQuery(Query);

    while (connection.nextRecord() == true) {
      jointSymbol= new JointSymbol();
      jointSymbol.setTrendSymbol(signal, connection.getRowValueString("trendsymbol"));
      jointSymbol.setStateSymbol(signal, connection.getRowValueString("statesymbol"));
      jointSymbol.setDescription(connection.getRowValueString("description"));
      jointSymbol.setColor(connection.getRowValueInt("colorr"),
                           connection.getRowValueInt("colorg"),
                           connection.getRowValueInt("colorb"));

      joint = new ExpectedJoint(jointSymbol, connection.getRowValueInt("minduration"),
          connection.getRowValueInt("maxduration"));

      template.addJoint(joint);
    }

    return template;
  }

  public Template loadCompleteTemplate (int template_id) throws java.sql.SQLException {
    Template template= new Template();
    SignalTemplate signalTemplate;
    int signal;

    for (signal= 0; signal < segnali.length; signal++) {
      signalTemplate= loadSignalTemplateIndexed (signal, template_id);
      template.addSignalTemplate(signalTemplate);
    }

    return template;
  }

  public void loadRetrievedContext (int context_id, RetrievedContext Context) {
    this.connection.loadRetrievedContext(context_id, Context);
  }

  public void loadAllRetrievedContexts (ArrayList Contexts) {
    this.connection.loadAllRetrievedContexts(Contexts);
  }

}

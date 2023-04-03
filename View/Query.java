package View;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class Query {

    private LinkedHashMap<Float, Date> risultati;

    public static void main(String[] args) {
    }

    public void creaQuery(String trend, String minDuration, String maxTimeGap, String minRateField, String maxRateField, String localWindow, File file) throws XMLStreamException, IOException, TransformerException {
        StringWriter sw = new StringWriter();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(sw));
        xmlWriter.writeStartDocument();
        xmlWriter.writeStartElement("TemporalAbstractionRequest");

        // Abstractions
        xmlWriter.writeStartElement("Abstractions");
        xmlWriter.writeStartElement("Abstraction");
        xmlWriter.writeAttribute("minDuration", minDuration);

        // AbstractionInfo
        LocalDateTime data = LocalDateTime.now();
        xmlWriter.writeStartElement("AbstractionInfo");
        xmlWriter.writeStartElement("Comment");
        xmlWriter.writeEndElement(); // Comment
        xmlWriter.writeStartElement("DateTimeSecond");
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // DateTimeSecond
        xmlWriter.writeEndElement(); // AbstractionInfo

        // AbstractionTrend
        xmlWriter.writeStartElement("AbstractionTrend");
        xmlWriter.writeAttribute("temporalUnit", "minute");
        xmlWriter.writeAttribute("maximumTimeGap", maxTimeGap);
        switch (trend) {
            case "Fortemente Decrescente":
                xmlWriter.writeAttribute("trend", "Strong dec"); //??
                break;
            case "Decrescente":
                xmlWriter.writeAttribute("trend", "dec");
                break;
            case "Crescente":
                xmlWriter.writeAttribute("trend", "inc");
                break;
            case "Fortemente crescente":
                xmlWriter.writeAttribute("trend", "Strong inc"); //??
                break;
        }

        xmlWriter.writeAttribute("minRate", minRateField);
        xmlWriter.writeAttribute("maxRate", maxRateField);
        xmlWriter.writeAttribute("localWin", localWindow);
        xmlWriter.writeEndElement(); // AbstractionTrend

        xmlWriter.writeEndElement(); // Abstraction
        xmlWriter.writeEndElement(); // Abstractions

        // Series
        xmlWriter.writeStartElement("Series");
        xmlWriter.writeStartElement("Data");

        // DataInfo
        xmlWriter.writeStartElement("DataInfo");
        xmlWriter.writeAttribute("id", "admin-1090086105593");
        xmlWriter.writeStartElement("Comment");
        xmlWriter.writeCharacters("query");
        xmlWriter.writeEndElement(); // Comment
        xmlWriter.writeStartElement("DateTimeSecond");
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // DateTimeSecond
        xmlWriter.writeEndElement(); // DataInfo

        xmlWriter.writeStartElement("NumericDataSeries");
        this.risultati = ReadCSVFile.readFile(file);
        for (Date d : risultati.values()) {
            xmlWriter.writeStartElement("DateTimeSecond");
            xmlWriter.writeAttribute("year", String.valueOf(d.getYear()));
            xmlWriter.writeAttribute("month", String.valueOf(d.getMonth()));
            xmlWriter.writeAttribute("day", String.valueOf(d.getDay()));
            xmlWriter.writeAttribute("hour", String.valueOf(d.getHours()));
            xmlWriter.writeAttribute("minute", String.valueOf(d.getMinutes()));
            xmlWriter.writeAttribute("second", String.valueOf(d.getSeconds()));
            xmlWriter.writeEndElement();//DateTimeSecond
        }

        xmlWriter.writeEndElement(); // Data
        xmlWriter.writeEndElement(); // Series

        xmlWriter.writeEndElement(); // TemporalAbstractionRequest
        xmlWriter.writeEndDocument();

        xmlWriter.flush();
        xmlWriter.close();

        String xml = sw.toString();

        // generazione del file XML
        File fileXml = new File("temporal_abstraction_request.xml");
        FileWriter fw = new FileWriter(fileXml);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(xml);
        bw.close();
    }
    public static void creaQuery(String minDuration, String maxTimeGap, String minThreshold, String maxThreshold) throws XMLStreamException, IOException, TransformerException {
        StringWriter sw = new StringWriter();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(sw));
        xmlWriter.writeStartDocument();
        xmlWriter.writeStartElement("TemporalAbstractionRequest");

        xmlWriter.writeStartElement("RequestInfo");
        xmlWriter.writeStartElement("user");
        xmlWriter.writeAttribute("admin", "admin");
        LocalDateTime data = LocalDateTime.now();
        xmlWriter.writeStartElement("DateTimeSecond");
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // DateTimeSecond
        xmlWriter.writeEndElement();// RequestInfo
        // Abstractions
        xmlWriter.writeStartElement("Abstractions");
        xmlWriter.writeStartElement("Abstraction");
        xmlWriter.writeAttribute("minDuration", minDuration);

        // AbstractionInfo
        xmlWriter.writeStartElement("AbstractionInfo");
        xmlWriter.writeStartElement("Comment");
        xmlWriter.writeEndElement(); // Comment


        xmlWriter.writeEndElement(); // AbstractionInfo
        xmlWriter.writeStartElement("DateTimeSecond");
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // AbstractionInfo
        // AbstractionState
        xmlWriter.writeStartElement("AbstractionState");
        xmlWriter.writeAttribute("temporalUnit", "minute");
        xmlWriter.writeAttribute("maximumTimeGap", maxTimeGap);
        xmlWriter.writeStartElement("ThresholdList");
        xmlWriter.writeStartElement("Threshold");
        xmlWriter.writeAttribute( "T01", minThreshold);
        xmlWriter.writeEndElement();//Threshold
        xmlWriter.writeStartElement("Thresholds");
        xmlWriter.writeAttribute( "T02", maxThreshold);
        xmlWriter.writeEndElement();//Thresholds
        xmlWriter.writeStartElement("Thresholds");
        xmlWriter.writeAttribute( "T03", "");
        xmlWriter.writeEndElement();//Thresholds
        xmlWriter.writeEndElement();//ThresholdList


        xmlWriter.writeStartElement("Conditions");
        xmlWriter.writeStartElement("Step");
        xmlWriter.writeAttribute("threshold", "T02");
        xmlWriter.writeEndElement();//Step
        xmlWriter.writeEndElement();//Conditions
        xmlWriter.writeStartElement("CounterConditions");
        xmlWriter.writeStartElement("Step");
        xmlWriter.writeAttribute("threshold", "T01");
        xmlWriter.writeEndElement();//Step
        xmlWriter.writeStartElement("Step");
        xmlWriter.writeAttribute("threshold", "T01");
        xmlWriter.writeEndElement();//Step
        xmlWriter.writeEndElement(); // CounterConditions
        xmlWriter.writeEndElement(); // AbstractionState
        xmlWriter.writeEndElement(); // Abstraction
        xmlWriter.writeEndElement(); //Abstractions

        /*
        // Series
        xmlWriter.writeStartElement("Series");
        xmlWriter.writeStartElement("Data");

        // DataInfo
        xmlWriter.writeStartElement("DataInfo");
        xmlWriter.writeAttribute("id", "admin-1090086105593");
        xmlWriter.writeStartElement("Comment");
        xmlWriter.writeCharacters("query");
        xmlWriter.writeEndElement(); // Comment

        xmlWriter.writeEndElement(); // DataInfo

        xmlWriter.writeEndElement(); // Data
        xmlWriter.writeEndElement(); // Series
        */
        xmlWriter.writeEndElement(); // TemporalAbstractionRequest
        xmlWriter.writeEndDocument();

        xmlWriter.flush();
        xmlWriter.close();

        String xml = sw.toString();

        // generazione del file XML
        File file = new File("temporal_abstraction_request.xml");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(xml);
        bw.close();
    }






}



package View;

import java.io.*;
import java.time.LocalDateTime;
import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import javax.xml.transform.*;
import java.util.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class Query {
    private HashMap<LocalDateTime, Float> risultati;
    private static HashMap<LocalDateTime, Float> risultati1;

    public static void main(String[] args) {
    }

    public File creaQuery(String trend, String minDuration, String maxTimeGap, String minRateField, String maxRateField, String localWindow, File file, String maxOscillation) throws XMLStreamException, IOException, TransformerException {
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

        if (Objects.equals(trend, "Fortemente Decrescente") || Objects.equals(trend, "Decrescente") || Objects.equals(trend, "Crescente") || Objects.equals(trend, "Fortemente crescente")) {
            // AbstractionTrend
            xmlWriter.writeStartElement("AbstractionTrend");
            xmlWriter.writeAttribute("temporalUnit", "second");
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
        } else {
            xmlWriter.writeStartElement("AbstractionStationary");
            xmlWriter.writeAttribute("temporalUnit", "second");
            xmlWriter.writeAttribute("maximumTimeGap", maxTimeGap);
            xmlWriter.writeAttribute("maxRate", maxRateField);
            xmlWriter.writeAttribute("maxOscillationMargin", maxOscillation);
        }

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
        this.risultati = ReadCSVFile.ritornaData(file);
        for (LocalDateTime d : risultati.keySet()) {
            xmlWriter.writeStartElement("NumericaData");
            xmlWriter.writeStartElement("DateTimeSecond");
            xmlWriter.writeAttribute("year", String.valueOf(d.getYear()));
            xmlWriter.writeAttribute("month", String.valueOf(d.getMonthValue()));
            xmlWriter.writeAttribute("day", String.valueOf(d.getDayOfMonth()));
            xmlWriter.writeAttribute("hour", String.valueOf(d.getHour()));
            xmlWriter.writeAttribute("minute", String.valueOf(d.getMinute()));
            xmlWriter.writeAttribute("second", String.valueOf(d.getSecond()));
            xmlWriter.writeEndElement();//DateTimeSecond
            xmlWriter.writeStartElement("Adimensional");
            xmlWriter.writeAttribute("value", String.valueOf(risultati.get(d)));
            xmlWriter.writeAttribute("precision", "float");
            xmlWriter.writeEndElement();//Adimensional
            xmlWriter.writeEndElement(); //NumericaData

        }

        xmlWriter.writeEndElement(); // Data
        xmlWriter.writeEndElement(); // Series

        xmlWriter.writeEndElement(); // TemporalAbstractionRequest
        xmlWriter.writeEndDocument();

        xmlWriter.flush();
        xmlWriter.close();

        String xml = sw.toString();

        // generazione del file XML
        File fileXml = new File("temporal_abstraction_request_trend.xml");
        FileWriter fw = new FileWriter(fileXml);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(xml);
        bw.close();
        return fileXml;
    }
    public File creaQuery(String minDuration, String maxTimeGap, String minThreshold, String maxThreshold, File file) throws XMLStreamException, IOException, TransformerException {
        StringWriter sw = new StringWriter();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(sw));
        xmlWriter.writeStartDocument("ISO-8859-1", "1.0");
        xmlWriter.writeStartElement("TemporalAbstractionRequest"); //CHIUSO

        xmlWriter.writeStartElement("RequestInfo"); //CHIUSO

        xmlWriter.writeStartElement("user"); //CHIUSO
        xmlWriter.writeCharacters("admin");
        xmlWriter.writeEndElement();//user

        LocalDateTime data = LocalDateTime.now();
        xmlWriter.writeStartElement("DateTimeSecond"); //CHIUSO
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // DateTimeSecond
        xmlWriter.writeEndElement();// RequestInfo
        // Abstractions
        xmlWriter.writeStartElement("Abstractions"); //CHIUSO
        xmlWriter.writeStartElement("Abstraction"); //CHIUSO
        xmlWriter.writeAttribute("minDuration", minDuration);
        xmlWriter.writeStartElement("AbstractionInfo"); //CHIUSO
        // AbstractionInfo
        xmlWriter.writeStartElement("Comment"); //CHIUSO
        xmlWriter.writeEndElement(); // Comment


        xmlWriter.writeStartElement("DateTimeSecond"); //CHIUSO
        xmlWriter.writeAttribute("year", String.valueOf(data.getYear()));
        xmlWriter.writeAttribute("month", String.valueOf(data.getMonthValue()));
        xmlWriter.writeAttribute("day", String.valueOf(data.getDayOfMonth()));
        xmlWriter.writeAttribute("hour", String.valueOf(data.getHour()));
        xmlWriter.writeAttribute("minute", String.valueOf(data.getMinute()));
        xmlWriter.writeAttribute("second", String.valueOf(data.getSecond()));
        xmlWriter.writeEndElement(); // DateTimeSecond
        xmlWriter.writeEndElement(); // AbstractionInfo
        // AbstractionState
        xmlWriter.writeStartElement("AbstractionState"); //CHIUSO
        xmlWriter.writeAttribute("temporalUnit", "minute");
        xmlWriter.writeAttribute("maximumTimeGap", maxTimeGap);
        xmlWriter.writeStartElement("ThresholdsList"); //CHIUSO

        // write the T01 threshold element
        xmlWriter.writeStartElement("Threshold"); //CHIUSO
        xmlWriter.writeAttribute("name", "T01");
        xmlWriter.writeAttribute("max", String.valueOf(Float.parseFloat(minThreshold)));
        xmlWriter.writeEndElement(); //Threshold

        // write the T02 threshold element
        xmlWriter.writeStartElement("Thresholds"); //CHIUSO
        xmlWriter.writeAttribute("name", "T02");
        xmlWriter.writeAttribute("max",String.valueOf(Float.parseFloat(maxThreshold)));
        xmlWriter.writeEndElement(); //Threshold

        // write the T03 threshold element
        xmlWriter.writeStartElement("Thresholds"); //CHIUSO
        xmlWriter.writeAttribute("name", "T03");
        xmlWriter.writeAttribute("max", "");
        xmlWriter.writeEndElement(); //Thresholds
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

        risultati1 = ReadCSVFile.ritornaData(file);
        for (LocalDateTime d : risultati1.keySet()) {
            xmlWriter.writeStartElement("NumericData");
            xmlWriter.writeStartElement("DateTimeSecond");
            xmlWriter.writeAttribute("year", String.valueOf(d.getYear()));
            xmlWriter.writeAttribute("month", String.valueOf(d.getMonthValue()));
            xmlWriter.writeAttribute("day", String.valueOf(d.getDayOfMonth()));
            xmlWriter.writeAttribute("hour", String.valueOf(d.getHour()));
            xmlWriter.writeAttribute("minute", String.valueOf(d.getMinute()));
            xmlWriter.writeAttribute("second", String.valueOf(d.getSecond()));
            xmlWriter.writeEndElement();//DateTimeSecond
            xmlWriter.writeStartElement("Adimensional");
            xmlWriter.writeAttribute("value", String.valueOf(risultati1.get(d)));
            xmlWriter.writeAttribute("precision", "float");
            xmlWriter.writeEndElement();//Adimensional
            xmlWriter.writeEndElement(); //NumericaData
        }
        xmlWriter.writeEndElement();//NumericDataSeries
        xmlWriter.writeEndElement(); // Data
        xmlWriter.writeEndElement(); // Series

        xmlWriter.writeEndElement(); // TemporalAbstractionRequest
        xmlWriter.writeEndDocument();

        xmlWriter.flush();
        xmlWriter.close();

        String xml = sw.toString();

        // generazione del file XML
        File file1 = new File("temporal_abstraction_request_state.xml");
        FileWriter fw = new FileWriter(file1);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(xml);
        bw.close();

        return file1;
    }

}



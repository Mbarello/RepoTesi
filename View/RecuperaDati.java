package View;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class RecuperaDati {
    private final ArrayList<Intervallo> listaIntervalli = new ArrayList<>();

    public ArrayList<Intervallo> RecuperaDati(String pathname) {
        try {
            // Open XML file
            File file = new File(pathname + "risposta.xml");

            // Create DOM parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //Parse XML file
            Document doc = dBuilder.parse(file);

            //Find all TimeIntervals elements
            NodeList timeIntervalsList = doc.getElementsByTagName("TimeIntervals");

            //Iterate over each TimeIntervals element
            for (int i = 0; i < timeIntervalsList.getLength(); i++) {
                Node timeIntervalNode = timeIntervalsList.item(i);

                //Find all DateTimeInterval elements
                NodeList dateTimeIntervalList = timeIntervalNode.getChildNodes();
                for (int j = 0; j < dateTimeIntervalList.getLength(); j++) {
                    Node dateTimeIntervalNode = dateTimeIntervalList.item(j);

                    //Find all Lower and Upper elements
                    NodeList lowerList = dateTimeIntervalNode.getChildNodes();
                    Intervallo intervallo = new Intervallo();

                    for (int k = 0; k < lowerList.getLength(); k++) {
                        Node lowerNode = lowerList.item(k);
                        if (lowerNode.getNodeName().equals("Lower")) {

                            //Find DateTimeSecond element
                            Node dateTimeSecondNode = lowerNode.getFirstChild();
                            if (dateTimeSecondNode != null && dateTimeSecondNode.getNodeName().equals("DateTimeSecond")) {
                                NamedNodeMap attributi = dateTimeSecondNode.getAttributes();

                                String yearLower = attributi.getNamedItem("year").getNodeValue();
                                String monthLower = attributi.getNamedItem("month").getNodeValue();
                                String dayLower = attributi.getNamedItem("day").getNodeValue();
                                String hourLower = attributi.getNamedItem("hour").getNodeValue();
                                String minuteLower = attributi.getNamedItem("minute").getNodeValue();
                                String secondLower = attributi.getNamedItem("second").getNodeValue();
                                Integer monthLowerInt = Integer.parseInt(monthLower) + 1;
                                if (Integer.parseInt(monthLower) < 10) {
                                    monthLower = "0" + monthLowerInt;
                                }
                                if (Integer.parseInt(dayLower) < 10) {
                                    dayLower = "0" + dayLower;
                                }
                                if (Integer.parseInt(hourLower) < 10) {
                                    hourLower = "0" + hourLower;
                                }
                                if (Integer.parseInt(minuteLower) < 10) {
                                    minuteLower = "0" + minuteLower;
                                }
                                if (Integer.parseInt(secondLower) < 10) {
                                    secondLower = "0" + secondLower;
                                }
                                String dataLowerString = yearLower + "-" + monthLower + "-" + dayLower + " " + hourLower + ":" + minuteLower + ":" + secondLower;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime data = LocalDateTime.parse(dataLowerString, formatter);
                                intervallo.setInizio(data);
                            }
                        }
                    }


                    NodeList upperList = dateTimeIntervalNode.getChildNodes();
                    for (int k = 0; k < upperList.getLength(); k++) {
                        Node upperNode = upperList.item(k);
                        if (upperNode.getNodeName().equals("Upper")) {

                            //Find DateTimeSecond element
                            Node dateTimeSecondNode = upperNode.getFirstChild();
                            if (dateTimeSecondNode != null && dateTimeSecondNode.getNodeName().equals("DateTimeSecond")) {
                                NamedNodeMap attributi = dateTimeSecondNode.getAttributes();

                                String yearUpper = attributi.getNamedItem("year").getNodeValue();
                                String monthUpper = attributi.getNamedItem("month").getNodeValue();
                                String dayUpper = attributi.getNamedItem("day").getNodeValue();
                                String hourUpper = attributi.getNamedItem("hour").getNodeValue();
                                String minuteUpper = attributi.getNamedItem("minute").getNodeValue();
                                String secondUpper = attributi.getNamedItem("second").getNodeValue();
                                Integer monthUpperInt = Integer.parseInt(monthUpper) + 1;

                                if (monthUpperInt < 10) {
                                    monthUpper = "0" + monthUpperInt;
                                }
                                if (Integer.parseInt(dayUpper) < 10) {
                                    dayUpper = "0" + dayUpper;
                                }
                                if (Integer.parseInt(hourUpper) < 10) {
                                    hourUpper = "0" + hourUpper;
                                }
                                if (Integer.parseInt(minuteUpper) < 10) {
                                    minuteUpper = "0" + minuteUpper;
                                }
                                if (Integer.parseInt(secondUpper) < 10) {
                                    secondUpper = "0" + secondUpper;
                                }
                                String dataUpperString = yearUpper + "-" + monthUpper + "-" + dayUpper + " " + hourUpper + ":" + minuteUpper + ":" + secondUpper;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime data = LocalDateTime.parse(dataUpperString, formatter);
                                intervallo.setFine(data);
                            }
                            this.listaIntervalli.add(intervallo);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.listaIntervalli;
    }
}

/*package View;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class RecuperaDati {


        public void RecuperaDati {

            try {
                //crea un oggetto DocumentBuilderFactory
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                //crea un oggetto DocumentBuilder
                DocumentBuilder builder = factory.newDocumentBuilder();

                //parsa il file XML e crea un oggetto Document
                Document document = builder.parse(new File("file.xml"));

                //ottiene la lista di tutti gli elementi "NumericData" nel nodo "adimensional"
                NodeList nodeList = document.getElementsByTagName("adimensional").item(0).getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    //verifica se il nodo è un elemento "NumericData"
                    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("NumericData")) {
                        //recupera il valore numerico dal nodo "NumericData"
                        String numero = node.getTextContent();

                        //stampa il valore numerico recuperato
                        System.out.println("Numero: " + numero);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

 */

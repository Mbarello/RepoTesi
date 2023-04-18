package View;

import org.hcklab.project.tempo.facade.DefaultTempoFacade;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

public class QueryPerform {

    public void queryPerform(File xmlFile){
        try {
            String path = System.getProperty("user.dir") + "/risposta/";
            SAXBuilder saxBuilder = new SAXBuilder();
            Document jdomDoc = saxBuilder.build(xmlFile);

            DefaultTempoFacade tempoFacade = new DefaultTempoFacade();
            Document doc = tempoFacade.performTemporalAbstraction(jdomDoc);

            File file = new File(path + "risposta.xml");
            file.getParentFile().mkdirs();
            file.createNewFile();

            // Crea l'oggetto XMLOutputter per scrivere il contenuto del documento nel file
            XMLOutputter xmlOutput = new XMLOutputter();

            // Imposta il formato di output
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");

            // Scrivi il contenuto del documento nel file
            FileOutputStream fos = new FileOutputStream(file);
            xmlOutput.output(doc, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

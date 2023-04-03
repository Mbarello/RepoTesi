package View;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadCSVFile {

    public static LinkedHashMap<Float, LocalDateTime> readFile(File file) {

        LinkedHashMap<Float, LocalDateTime> risultati = new LinkedHashMap<>();
        String csvSplitBy = ",";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String timeString = data[0];
                String[] orario = timeString.split(" ");
                String ora = orario[1];

                try {
                    LocalTime time = LocalTime.parse(ora, formatter);
                    LocalDate today = LocalDate.now();
                    LocalDateTime dateTime = LocalDateTime.of(today, time);
                    risultati.put(Float.parseFloat(data[4]), dateTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sortHashMapByDateTime(risultati);
    }

    //Ordinamento dati file pdf (i dati non sono in ordine cronologico)
    public static LinkedHashMap<Float, LocalDateTime> sortHashMapByDateTime(HashMap<Float, LocalDateTime> map) {
        List<Map.Entry<Float, LocalDateTime>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Float, LocalDateTime>>() {
            public int compare(Map.Entry<Float, LocalDateTime> o1, Map.Entry<Float, LocalDateTime> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        LinkedHashMap<Float, LocalDateTime> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Float, LocalDateTime> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static HashMap<LocalDateTime,Float> ritornaData(File file) {
        HashMap<LocalDateTime,Float> mapData = new HashMap<>();
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                mapData.put(LocalDateTime.parse(data[0],formatter), Float.parseFloat(data[4]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapData;
    }
}
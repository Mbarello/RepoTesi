package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadCSVFile {

    public static LinkedHashMap<Float, Date> readFile(File file) {

        LinkedHashMap<Float, Date> risultati = new LinkedHashMap<>();
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String timeString = data[0];
                String[] orario = timeString.split(" ");
                String ora = orario[1];

                try {
                    Date date = dateFormat.parse(ora);
                    risultati.put(Float.parseFloat(data[4]), date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sortHashMapByDate(risultati);
    }
//Ordinamento dati file pdf (i dati non sono in ordine cronologico)
    public static LinkedHashMap<Float, Date> sortHashMapByDate(HashMap<Float, Date> map) {
        List<Map.Entry<Float, Date>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Float, Date>>() {
            public int compare(Map.Entry<Float, Date> o1, Map.Entry<Float, Date> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        LinkedHashMap<Float, Date> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Float, Date> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}

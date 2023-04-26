package View;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadCSVFile {

    public static LinkedHashMap<LocalDateTime,Float> readFile(File file) {

        LinkedHashMap<LocalDateTime,Float> risultati = new LinkedHashMap<>();
        ArrayList<Integer> cont=new ArrayList<Integer>();
        String csvSplitBy = ",";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;

            int i=0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String timeString = data[0];
                String[] orario = timeString.split(" ");
                String[] orario2 = orario[1].split("\\.");
                String ora = orario2[0];

                try {

                    LocalTime time = LocalTime.parse(ora, formatter);
                    LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(orario[0]), time);
                    if(!risultati.containsKey(dateTime)){
                        cont.add(1);
                    }
                    if(risultati.containsKey(dateTime)){
                        int p =posizioneChiave(risultati,dateTime);
                        //TROVO POSIZIONE CHIAVE
                        risultati.put(dateTime,Float.parseFloat(data[4])+risultati.get(dateTime)); //sommo i float
                        cont.set(p,cont.get(p)+1); //aggiorno il numero di volte che ho trovato una data
                    }
                    else{
                        risultati.put(dateTime,Float.parseFloat(data[4]));
                        i++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i=0;
            //faccio la media
            for(LocalDateTime l : risultati.keySet()){
                risultati.put(l,risultati.get(l)/cont.get(i));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sortHashMapByDateTime(risultati);

    }

    private static int posizioneChiave(LinkedHashMap<LocalDateTime, Float> risultati, LocalDateTime dateTime) {
        int p=0;
        int posizione=0;
        for (LocalDateTime l : risultati.keySet()){
            if(l.isEqual(dateTime)){
                posizione=p;
            }
            p++;
        }
        return posizione;
    }
/*
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
 */

    public static LinkedHashMap<LocalDateTime,Float> ritornaData(File file) {
        LinkedHashMap<LocalDateTime,Float> mapData = new LinkedHashMap<>();
        String csvSplitBy = ",";
        ArrayList<Integer> cont=new ArrayList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {

            String line;
            int i=0;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSplitBy);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String[] d =data[0].split("\\.");
                LocalDateTime Data=LocalDateTime.parse(d[0],formatter);

                if(!mapData.containsKey(Data)){
                    cont.add(1);
                }
                if(mapData.containsKey(Data)){
                    int p =posizioneChiave(mapData,Data);
                    //TROVO POSIZIONE CHIAVE
                    mapData.put(Data,Float.parseFloat(data[4])+mapData.get(Data)); //sommo i float
                    cont.set(p,cont.get(p)+1); //aggiorno il numero di volte che ho trovato una data
                }
                else{
                    mapData.put(Data,Float.parseFloat(data[4]));
                    i++;
                }


            }
            i=0;
            //faccio la media
            for(LocalDateTime l : mapData.keySet()){
                mapData.put(l,mapData.get(l)/cont.get(i));
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sortHashMapByDateTime(mapData);

    }

    public static LinkedHashMap<LocalDateTime, Float> sortHashMapByDateTime(HashMap<LocalDateTime, Float> map) {
        List<Map.Entry<LocalDateTime, Float>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<LocalDateTime, Float>>() {
            public int compare(Map.Entry<LocalDateTime, Float> o1, Map.Entry<LocalDateTime, Float> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        LinkedHashMap<LocalDateTime, Float> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<LocalDateTime, Float> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


}
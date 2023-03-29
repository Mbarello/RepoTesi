package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadCSVFile {

    public static HashMap readFile(File file) {

        HashMap risultati = new HashMap();
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
                    long milliseconds = calendar.getTimeInMillis() - date.getTime();
                    risultati.put(Float.parseFloat(data[4]), milliseconds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       return risultati;
    }
}
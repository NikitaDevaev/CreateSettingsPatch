package com.settings.patch.CreateSettingsPatch.generateModules;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

public class CsvWorker {

    private final String cvsSplitBy = ";";
    public void read(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line = "";
            while ((line = br.readLine())!= null) {
                String[] data = line.split(cvsSplitBy);
                System.out.println("Имя условия: " + data[0] + "; Описание: " + data[1] + "; Условие: " + data[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

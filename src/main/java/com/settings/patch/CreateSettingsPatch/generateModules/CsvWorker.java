package com.settings.patch.CreateSettingsPatch.generateModules;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.*;
import java.lang.reflect.Field;

// Не используется, но может пригодится
// TODO: паренести в fileWorker
public class CsvWorker {
    private String user;
    private final String cvsSplitBy = ";";

    public CsvWorker(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.user = auth.getName();
    }

    // для debug
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

    public void generateSettings(InputStream is, String tableName){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line = "";
            while ((line = br.readLine())!= null) {
                String[] data = line.split(cvsSplitBy);
                if(tableName.equals("YPMPF")){
                    Data.getYPMlist().get(user).add(new YPMPF(data[0], data[1], data[2], Mode.Добавление));
                }
                if(tableName.equals("YEFPF")){
                    // С применением рефлексии, можно передалать и YPMPF, но там нет столько полей
                    YEFPF yefpf = YEFPF.builder().mode(Mode.Добавление).build();
                    Field[] fields = yefpf.getClass().getFields();
                    int i = 0;
                    for(Field field : fields){
                        try {
                            field.set(yefpf,data[i]);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                    Data.getYEFlist().get(user).add(yefpf);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

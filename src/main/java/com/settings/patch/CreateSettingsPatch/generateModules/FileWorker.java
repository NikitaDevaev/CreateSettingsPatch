package com.settings.patch.CreateSettingsPatch.generateModules;

import lombok.Getter;

import java.io.*;
import java.util.Map;

public class FileWorker {

    // Константы
    public static final String defaultPatchToStaticFiles = "src/main/resources/static/";
    public static final String defaultPatchToTemplatesFiles = "src/main/resources/templates/";
    public static final String defaultPatchToResources = "src/main/resources/";

    public static String getTemplateDataFromFile(String pathToTemplateFile){
        StringBuilder str = new StringBuilder();
        File f = new File(pathToTemplateFile);
        try(FileReader fr = new FileReader(f)) {
            try(BufferedReader br = new BufferedReader(fr)){
                String tempStr = null;
                while ((tempStr = br.readLine())!= null){
                    str.append(tempStr + "\n");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new String(str);
    }

    public static void generateFinalFile(String pathToNewFile, String data, Map<String, String> paramReplace){
        for(Map.Entry<String, String> entry : paramReplace.entrySet()){
            data = data.replace(entry.getKey(), entry.getValue());
        }
        File file = new File(pathToNewFile);
        try {
            file.createNewFile();
            try(FileWriter fw = new FileWriter(file, false);) {
                fw.write(data);
                fw.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

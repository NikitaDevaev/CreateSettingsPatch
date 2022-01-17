package com.settings.patch.CreateSettingsPatch.generateModules;


import java.io.*;
import java.util.Map;



public class FileWorker {
    // Константы
    public static final String defaultPatchToStaticFiles = "/home/tomcat/tmp/static/";
    public static final String defaultPath = "/home/tomcat/tmp/";



    public static void createDir(String nameDirForProfile){
        File mkdir = new File(nameDirForProfile);
        if (!mkdir.exists()){
            mkdir.mkdirs();
        }
    }

    public static boolean deleteDirectory(File directoryToBeDeleted) {

        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

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
            try(FileWriter fw = new FileWriter(file, false)) {
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

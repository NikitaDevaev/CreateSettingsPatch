package com.settings.patch.CreateSettingsPatch.GenerateModule;


import com.settings.patch.CreateSettingsPatch.entities.BuildFile;
import com.settings.patch.CreateSettingsPatch.entities.Patch;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Generator {
    private Map<String, String> build = new HashMap<>();
    private BuildFile buildFile;
    private String mnemonic;
    private String numberPatch;
    @Getter
    private String patchForBuildFile;
    @Getter
    private String nameBuildFile;
    private Patch patch;

    public Generator(String task, String mnemonic, String numberPatch){
        this.buildFile = new BuildFile(task);
        this.mnemonic = mnemonic;
        this.numberPatch = numberPatch;
        this.patch = Patch.builder().build();
    }

    public void run(){
        generateBuildFile();
    }

   private void generateBuildFile(){
       String str = "";
       File f = new File("src/main/resources/static/TemplateBuildFile.txt");
       try(FileReader fr = new FileReader(f)) {
           try(BufferedReader br = new BufferedReader(fr)){
               str = br.readLine();
               str = str.replace("#SQL_FILE_NAME#", "R"+buildFile.getNumber()+"EXT");
               str = str.replace("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
           }catch (IOException ex){
               ex.printStackTrace();
           }
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       this.nameBuildFile = mnemonic + "#" + numberPatch + ".ext.gradle";
       this.patchForBuildFile = "src/main/resources/templates/"+this.nameBuildFile;
       File file = new File(patchForBuildFile);
       try {
           file.createNewFile();
           try {
               FileWriter fw = new FileWriter(file, false);
               fw.write(str);
               fw.flush();
               fw.close();
           }catch (IOException e){
               e.printStackTrace();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
}

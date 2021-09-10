package com.settings.patch.CreateSettingsPatch.generateModules;


import com.settings.patch.CreateSettingsPatch.entities.BuildFile;
import com.settings.patch.CreateSettingsPatch.entities.ReleaseFile;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Generator {
    private Map<String, String> build = new HashMap<>();
    // entities
    private BuildFile buildFile;
    private ReleaseFile releaseFile;

    private String mnemonic;
    private String numberPatch;

    @Getter
    private String patchForBuildFile;
    @Getter
    private String nameBuildFile;
    @Getter
    private String patchForReleaseFile;
    @Getter
    private String nameReleaseFile;


    public Generator(String fsd ,String task, String brd, String description, String mnemonic, String numberPatch, int rebuildLevel){
        this.buildFile = new BuildFile(task);
        this.mnemonic = mnemonic;
        this.numberPatch = numberPatch;
        this.releaseFile = ReleaseFile.builder().description(description).fsd(fsd).task(task).brd(brd).rebuildLevel(rebuildLevel).build();
    }

    public void run(){
        generateBuildFile();
        generateReleaseFile();
    }

    private void generateBuildFile(){
        String str = getTemplateDataFromFile("src/main/resources/static/TemplateBuildFile.txt");
        this.nameBuildFile = mnemonic + "#" + numberPatch + ".ext.gradle";
        this.patchForBuildFile = "src/main/resources/templates/"+this.nameBuildFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#SQL_FILE_NAME#","R"+buildFile.getNumber()+"EXT");
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
        }};
        generateFinalFile(this.patchForBuildFile,str,replaceData);
    }
    private void generateReleaseFile(){
        String str = getTemplateDataFromFile("src/main/resources/static/TemplateReleaseFile.txt");
        this.nameReleaseFile = mnemonic + "#" + numberPatch + ".gradle";
        this.patchForReleaseFile = "src/main/resources/templates/"+this.nameReleaseFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
            put("#PATCH_DESCRIPTION#", releaseFile.getDescription());
            put("#TASK_LINK#", "task {\t'" + releaseFile.getTask()+"'\t}");
            if(releaseFile.getFsd()==null){
                put("#FSD_LINK#", "fsd  {\t'" + releaseFile.getTask()+"'\t}");
            }else{
                put("#FSD_LINK#", "fsd  {\t'" + releaseFile.getFsd()+"'\t}");
            }
            if(releaseFile.getBrd()==null){
                put("#BRD_LINK#", "");
            }else{
                put("#BRD_LINK#", "brd  {\t'" + releaseFile.getBrd()+"'\t}");
            }
            if(releaseFile.getRebuildLevel() > 0){
                put("#REBUILD_LEVEL#", "setRebuildDatabaseLevel(" + releaseFile.getRebuildLevel() + ")");
            }else{
                put("#REBUILD_LEVEL#", "");
            }
        }};
        generateFinalFile(this.patchForReleaseFile,str,replaceData);
    }

    private String getTemplateDataFromFile(String pathToTemplateFile){
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
    private void generateFinalFile(String pathToNewFile, String data, Map<String, String> paramReplace){
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

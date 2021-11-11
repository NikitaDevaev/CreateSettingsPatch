package com.settings.patch.CreateSettingsPatch.generateModules;


import com.settings.patch.CreateSettingsPatch.entities.BuildFile;
import com.settings.patch.CreateSettingsPatch.entities.ReleaseFile;
import com.settings.patch.CreateSettingsPatch.entities.SqlFile;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import lombok.Getter;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Generator {



    // entities
    private BuildFile buildFile;
    private ReleaseFile releaseFile;
    private SqlFile sqlFile;

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
    @Getter
    private String patchForSqlFile;
    @Getter
    private String nameSqlFile;
    @Getter
    private String patchForZipFile;
    @Getter
    private String nameZipFile;


    public Generator(String fsd , String task, String brd, String tests, String description, String mnemonic,
                     String numberPatch, int rebuildLevel, Set<String> selectedTables){
        this.buildFile = new BuildFile(task);
        this.sqlFile = new SqlFile(selectedTables);
        this.mnemonic = mnemonic;
        this.numberPatch = numberPatch;
        this.releaseFile = ReleaseFile.builder().description(description).fsd(fsd).task(task).brd(brd).tests(tests).rebuildLevel(rebuildLevel).build();
    }

    public void run(){
        generateBuildFile();
        generateReleaseFile();
        generateSqlFile();
        createZipArchive();
    }

    private void generateBuildFile(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "TemplateBuildFile.txt");
        this.nameBuildFile = mnemonic + "#" + numberPatch + ".ext.gradle";
        this.patchForBuildFile = FileWorker.defaultPatchToTemplatesFiles+this.nameBuildFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#SQL_FILE_NAME#","R"+buildFile.getNumber()+"EXT");
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
        }};
        FileWorker.generateFinalFile(this.patchForBuildFile,str,replaceData);
    }
    private void generateReleaseFile(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "TemplateReleaseFile.txt");
        this.nameReleaseFile = mnemonic + "#" + numberPatch + ".gradle";
        this.patchForReleaseFile = FileWorker.defaultPatchToTemplatesFiles+this.nameReleaseFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
            put("#PATCH_DESCRIPTION#", releaseFile.getDescription());
            put("#TASK_LINK#", "task {\t'" + releaseFile.getTask()+"'\t}");
            put("#TESTS_LINK#", "tests {\t'" + releaseFile.getTests()+"'\t}");
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
            put("#BUMBER_TASK#", buildFile.getNumber());
        }};
        FileWorker.generateFinalFile(this.patchForReleaseFile,str,replaceData);
    }

    private void generateSqlFile(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "TemplateSqlFile.txt");
        this.nameSqlFile = "R"+buildFile.getNumber()+"EXT.SQL";
        this.patchForSqlFile =  FileWorker.defaultPatchToTemplatesFiles+this.nameSqlFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#NAME_GZ_FILES#",sqlFile.getStringWithNamesGzFiles());
            put("#INSERT_VALUES_INTO_YPM#", sqlFile.getInsertSqlForYPMPF());
            put("#INSERT_INTO_GYPF_FOR_YPM#", FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_GYPF/TemplateYPMPF.txt"));
        }};
        FileWorker.generateFinalFile(this.patchForSqlFile,str,replaceData);
    }

    public void createZipArchive(){
        this.nameZipFile = mnemonic + "#" +numberPatch +".zip";
        this.patchForZipFile = FileWorker.defaultPatchToResources + "ACOV/" + nameZipFile;
        List<String> patchToFilesInArchive = Arrays.asList(patchForBuildFile,patchForReleaseFile, patchForSqlFile);
        List<String> nameFilesInArchive = Arrays.asList(nameBuildFile, nameReleaseFile, nameSqlFile);
        if(patchToFilesInArchive.size()!=nameFilesInArchive.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(patchForZipFile)))
        {
            for(int i = 0; i < patchToFilesInArchive.size(); i++){
                try(FileInputStream fis = new FileInputStream(patchToFilesInArchive.get(i))) {
                    ZipEntry entry=new ZipEntry(nameFilesInArchive.get(i));
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.settings.patch.CreateSettingsPatch.generateModules;


import com.settings.patch.CreateSettingsPatch.entities.BuildFile;
import com.settings.patch.CreateSettingsPatch.entities.ReleaseFile;
import com.settings.patch.CreateSettingsPatch.entities.SqlFile;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    private String getProfile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public Generator(String fsd , String task, String brd, String tests, String description, String mnemonic,
                     String numberPatch, int rebuildLevel, Set<String> selectedTables){
        this.buildFile = new BuildFile(task);
        this.sqlFile = new SqlFile(selectedTables);
        this.mnemonic = mnemonic;
        this.numberPatch = numberPatch;
        this.releaseFile = ReleaseFile.builder().description(description).fsd(fsd).task(task).brd(brd).tests(tests).rebuildLevel(rebuildLevel).build();
    }

    public void run(){
        FileWorker.deleteDirectory(new File(FileWorker.defaultPath +getProfile()));
        FileWorker.createDir(FileWorker.defaultPath + getProfile());
        generateBuildFile();
        generateReleaseFile();
        generateSqlFile();
        createZipArchive();
    }

    private void generateBuildFile(){
        String str = FileWorker.getTemplateDataFromFile( FileWorker.defaultPatchToStaticFiles + "TemplateBuildFile.txt");
        this.nameBuildFile = mnemonic + "#" + numberPatch + ".ext.gradle";
        this.patchForBuildFile = FileWorker.defaultPath + getProfile() +"/"+this.nameBuildFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#SQL_FILE_NAME#","R"+buildFile.getNumber()+"EXT");
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
        }};
        FileWorker.generateFinalFile(this.patchForBuildFile,str,replaceData);
    }
    private void generateReleaseFile(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "TemplateReleaseFile.txt");
        this.nameReleaseFile = mnemonic + "#" + numberPatch + ".gradle";
        this.patchForReleaseFile =FileWorker.defaultPath + getProfile() +"/"+this.nameReleaseFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#RELEASE_VERSION#",numberPatch);
            put("#PACKAGE_NAME#","ROSS"+buildFile.getNumber());
            put("#PATCH_DESCRIPTION#", releaseFile.getDescription());
            put("#TASK_LINK#", "task {\tadd '" + releaseFile.getTask()+"'\t}");
            put("#TESTS_LINK#", "tests {\tadd '" + releaseFile.getTests()+"'\t}");
            if(releaseFile.getFsd()==null){
                put("#FSD_LINK#", "fsd  {\tadd '" + releaseFile.getTask()+"'\t}");
            }else{
                put("#FSD_LINK#", "fsd  {\tadd '" + releaseFile.getFsd()+"'\t}");
            }
            if(releaseFile.getBrd()==null){
                put("#BRD_LINK#", "");
            }else{
                put("#BRD_LINK#", "brd  {\tadd '" + releaseFile.getBrd()+"'\t}");
            }
            put("#BUMBER_TASK#", buildFile.getNumber());
        }};
        FileWorker.generateFinalFile(this.patchForReleaseFile,str,replaceData);
    }

    // TODO: переделать после изменения getInsertSqlForYEFPF
    private void generateSqlFile(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "TemplateSqlFile.txt");
        sqlFile.getInsertSqlForYEFPF(getProfile());
        this.nameSqlFile = "R"+buildFile.getNumber()+"EXT.SQL";
        this.patchForSqlFile = FileWorker.defaultPath + getProfile() +"/"+this.nameSqlFile;
        Map<String, String> replaceData = new HashMap<>(){{
            put("#NAME_GZ_FILES#",sqlFile.getStringWithNamesGzFiles());
            if(sqlFile.getSelectedTable().contains("YPMPF")){
                put("#INSERT_VALUES_INTO_YPM#", sqlFile.getInsertSqlForYPMPF(getProfile()));
                put("#INSERT_INTO_GYPF_FOR_YPM#", FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_GYPF/TemplateYPMPF.txt"));
            }else{
                put("#INSERT_VALUES_INTO_YPM#", "");
                put("#INSERT_INTO_GYPF_FOR_YPM#", "");
            }
            if(sqlFile.getSelectedTable().contains("YEFPF")){
                put("#INSERT_VALUES_INTO_YEF#", sqlFile.getInsertSqlForYEFPF(getProfile()));
                put("#INSERT_INTO_GYPF_FOR_YEF#", FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_GYPF/TemplateYEFPF.txt"));
            }else{
                put("#INSERT_VALUES_INTO_YEF#", "");
                put("#INSERT_INTO_GYPF_FOR_YEF#", "");
            }
            if(sqlFile.getSelectedTable().contains("YA5PF")){
                put("#INSERT_VALUES_INTO_YA5#", sqlFile.getInsertSqlForYEFPF(getProfile()));
                put("#INSERT_INTO_GYPF_FOR_YA5#", FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_GYPF/TemplateYA5PF.txt"));
            }else{
                put("#INSERT_VALUES_INTO_YA5#", "");
                put("#INSERT_INTO_GYPF_FOR_YA5#", "");
            }

        }};
        FileWorker.generateFinalFile(this.patchForSqlFile,str,replaceData);
    }

    public void createZipArchive(){
        this.nameZipFile = mnemonic + "#" +numberPatch +".zip";
        this.patchForZipFile = FileWorker.defaultPath + getProfile() + "/" + nameZipFile;
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

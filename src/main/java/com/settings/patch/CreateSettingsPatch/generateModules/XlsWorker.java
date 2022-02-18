package com.settings.patch.CreateSettingsPatch.generateModules;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import lombok.Getter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class XlsWorker {
    private String user;
    @Getter
    private String path;
    @Getter
    private String name;

    public XlsWorker(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("_yyyy.MM.dd_hh.mm.ss_");
        this.name = "Settings" + formatForDateNow.format(dateNow)+ ".xlsx";
        this.user = auth.getName();
        this.path = FileWorker.defaultPath +user +"/";
    }

    public void read(InputStream is){
        try(OPCPackage opcPackage = OPCPackage.open(is)) {
            XSSFWorkbook wb = new XSSFWorkbook(opcPackage);
            Iterator sheetIter = wb.sheetIterator();
            while (sheetIter.hasNext()){
                XSSFSheet sheet = (XSSFSheet) sheetIter.next();
                Iterator rowIter = sheet.rowIterator();
                if(rowIter.hasNext()){
                    // Пропускаем заголовок
                    rowIter.next();
                }
                while (rowIter.hasNext()) {
                    XSSFRow row = (XSSFRow) rowIter.next();
                    // TODO: тоже сделать с рефлексией и объеденить все в один метод, просто определять имена класса (вроде можно там так)
                    if(sheet.getSheetName().equals("YPMPF")){
                        Data.getYPMlist().get(user).add(new YPMPF(row.getCell(0).toString(),
                                row.getCell(1).toString(),row.getCell(2).toString(),
                                Mode.Добавление));
                    }else if(sheet.getSheetName().equals("YEFPF")){
                        YEFPF yefpf = YEFPF.builder().mode(Mode.Добавление).build();
                        Field[] fields = yefpf.getClass().getFields();
                        int i = 0;
                        for(Field field : fields){
                            try {
                                field.set(yefpf,row.getCell(i).toString());
                                i++;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        Data.getYEFlist().get(user).add(yefpf);
                    }else if(sheet.getSheetName().equals("YA5PF")){
                        YA5PF ya5PF = YA5PF.builder().mode(Mode.Добавление).build();
                        Field[] fields = ya5PF.getClass().getFields();
                        int i = 0;
                        for(Field field : fields){
                            try {
                                field.set(ya5PF,row.getCell(i).toString());
                                i++;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        Data.getYA5list().get(user).add(ya5PF);
                    }
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Set<String> tableName){
        FileWorker.deleteDirectory(new File(path));
        FileWorker.createDir(path);
        XSSFWorkbook workbook = new XSSFWorkbook();
        //TODO: Дубликация кода -> вынести
        for(String sheetName : tableName) {
            XSSFSheet sheet = workbook.createSheet(sheetName);
            int row_count = 0;
            if (sheetName.equals("YPMPF")) {
                YPMPF tepmYPM = new YPMPF();
                Row top_row = sheet.createRow(row_count++);
                int colom_number = 0;
                for (String field : tepmYPM.getFieldName()) {
                    Cell cell = top_row.createCell(colom_number++);
                    cell.setCellValue(field);
                }
                for (int i = 0; i < Data.getYPMlist().get(user).size(); i++) {
                    Row row = sheet.createRow(row_count++);
                    colom_number = 0;
                    for (String field : Data.getYPMlist().get(user).get(i).getData()) {
                        Cell cell = row.createCell(colom_number++);
                        cell.setCellValue(field);
                    }
                }
            } else if (sheetName.equals("YEFPF")) {
                YEFPF tempYEF = new YEFPF();
                int colom_number = 0;
                Row top_row = sheet.createRow(row_count++);
                for (String field : tempYEF.getFieldName()) {
                    Cell cell = top_row.createCell(colom_number++);
                    cell.setCellValue(field);
                }
                for (int i = 0; i < Data.getYEFlist().get(user).size(); i++) {
                    Row row = sheet.createRow(row_count++);
                    colom_number = 0;
                    // Что-то не помню что это (надо подебажить)
                    if(row_count==1){
                        for (String field : Data.getYPMlist().get(user).get(i).getFieldName()) {
                            Cell cell = row.createCell(colom_number++);
                            cell.setCellValue(field);
                        }
                        break;
                    }
                    for (String field : Data.getYEFlist().get(user).get(i).getData()) {
                        Cell cell = row.createCell(colom_number++);
                        cell.setCellValue(field);
                    }
                }
            }else if(sheetName.equals("YA5PF")){
                YA5PF tepmYA5 = new YA5PF();
                Row top_row = sheet.createRow(row_count++);
                int colom_number = 0;
                for (String field : tepmYA5.getFieldName()) {
                    Cell cell = top_row.createCell(colom_number++);
                    cell.setCellValue(field);
                }
                for (int i = 0; i < Data.getYA5list().get(user).size(); i++) {
                    Row row = sheet.createRow(row_count++);
                    colom_number = 0;
                    for (String field : Data.getYA5list().get(user).get(i).getData()) {
                        Cell cell = row.createCell(colom_number++);
                        cell.setCellValue(field);
                    }
                }
            }
        }
            try {
                FileOutputStream outputStream = new FileOutputStream(path + name);
                workbook.write(outputStream);
                workbook.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

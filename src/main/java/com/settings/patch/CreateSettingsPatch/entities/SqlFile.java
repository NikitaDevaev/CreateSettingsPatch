package com.settings.patch.CreateSettingsPatch.entities;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.settings.patch.CreateSettingsPatch.generateModules.FileWorker;

import java.util.List;
import java.util.Set;

public class SqlFile {
    private Set<String> selectedTable;
    private List<YPMPF> ypmpfList = Data.getList();

    public SqlFile(Set<String> selectedTable) {
        this.selectedTable = selectedTable;
        this.ypmpfList = ypmpfList;
    }

    public String getStringWithNamesGzFiles(){
        String result= "";
        for(String className : selectedTable){
            String template = "";
            switch (className){
                case "YPMPF":
                    template = "(" + YPMPF.NAMEGZFILE + ")";
                    break;
            }
            if(!template.equals("") && !result.equals("")){
                result = ",\n\t" + template;
            }else{
                result += template;
            }
        }
        return result;
    }

    public String getInsertSqlForYPMPF(){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_YPMPF/TemplateYPMPF.txt");
        String values = "";
        for(YPMPF ypmpf : ypmpfList){
            String temp = "(WSID, DEC(DAY(CURRENT_DATE),2,0), DEC(CURRENT_TIME,6,0),  2, '" +
                    (ypmpf.getMode() == Mode.Удаление ? "B" : "A") + "',\n\t'" +
                     ypmpf.getConditionName() +"', '" + resultSqlString(ypmpf.getDescription()) + "',\n\t'" +
                    resultSqlString(ypmpf.getCondition() + "', 'Y')");
            if(!values.equals("")){
                values += ",\n\t" + temp;
            }else{
                values += temp;
            }
        }
        str = str.replace("#INSERT_VALUES#", values);
        return str;
    }

    public String resultSqlString(String str){
        str = str.replaceAll("'", "''");
        int len = str.length();
        StringBuilder stringBuilder = null;
        while (len > 84){
            stringBuilder = new StringBuilder(str);
            stringBuilder.insert(84,"' ||\n\t'");
            len -= 84;
        }
        if(stringBuilder == null){
            return str;
        }
        return new String(stringBuilder);
    }
}

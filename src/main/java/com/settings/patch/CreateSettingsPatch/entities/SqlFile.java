package com.settings.patch.CreateSettingsPatch.entities;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.settings.patch.CreateSettingsPatch.generateModules.FileWorker;

import java.util.Set;

public class SqlFile {
    private Set<String> selectedTable;

    public SqlFile(Set<String> selectedTable) {
        this.selectedTable = selectedTable;
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

    public String getInsertSqlForYPMPF(String profile){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_YPMPF/TemplateYPMPF.txt");
        String values = "";
        int i = 1;
        for(YPMPF ypmpf : Data.getList().get(profile)){
            String temp = "(WSID, DEC(DAY(CURRENT_DATE),2,0), DEC(CURRENT_TIME,6,0), " + i +", '" +
                    (ypmpf.getMode() == Mode.Удаление ? "B" : "A") + "',\n\t'" +
                     ypmpf.getConditionName() +"', '" + resultSqlString(ypmpf.getDescription()) + "',\n\t'" +
                     resultSqlString(ypmpf.getCondition()) + "', 'Y')";
            if(!values.equals("")){
                values += ",\n\t" + temp;
            }else{
                values += temp;
            }
            i++;
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

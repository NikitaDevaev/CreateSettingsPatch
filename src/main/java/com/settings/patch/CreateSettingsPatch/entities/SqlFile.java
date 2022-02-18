package com.settings.patch.CreateSettingsPatch.entities;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.settings.patch.CreateSettingsPatch.generateModules.FileWorker;
import lombok.Getter;

import java.util.Set;

public class SqlFile {
    @Getter
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
                case "YEFPF":
                    template = "(" + YEFPF.NAMEGZFILE + ")";
                    break;
                case "YA5PF":
                    template = "(" + YA5PF.NAMEGZFILE + ")";
                    break;
            }
            if(!template.equals("") && !result.equals("")){
                result += ",\n" + template;
            }else{
                result += template;
            }
        }
        return result;
    }
    // TODO: Переделать в один метод getInsertSqlForYPMPF и т.д.
    public String getInsertSqlForYPMPF(String profile){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_YPMPF/TemplateYPMPF.txt");
        String values = "";
        int i = 1;
        for(YPMPF ypmpf : Data.getYPMlist().get(profile)){
            String temp = "(WSID, DEC(DAY(CURRENT_DATE),2,0), DEC(CURRENT_TIME,6,0), " + i +", '" +
                    (ypmpf.getMode() == Mode.Удаление ? "B" : "A") + "',\n\t'" +
                     ypmpf.getYPMCND() +"', '" + resultSqlString(ypmpf.getYEMDSC()) + "',\n\t'" +
                     resultSqlString(ypmpf.getYPMCCN()) + "', 'Y')";
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

    public String getInsertSqlForYEFPF(String profile){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_YEFPF/TemplateYEFPF.txt");
        String values = "";
        int i = 1;
        for(YEFPF yefpf : Data.getYEFlist().get(profile)){
            String temp = "(WSID, DEC(DAY(CURRENT_DATE),2,0), DEC(CURRENT_TIME,6,0), " + i +", '" +
                    (yefpf.getMode() == Mode.Удаление ? "B" : "A") + "',\n\t";
                    int j = 1;
                    for(String value : yefpf.getData()){
                        temp += "'" + value + "'";
                        if(j==61){
                            temp += ")";
                            break;
                        }
                        if(j%5==0){
                            temp+= ",\n\t";
                        }else{
                            temp += ", ";
                        }
                        j++;
                    }
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

    public String getInsertSqlForYA5PF(String profile){
        String str = FileWorker.getTemplateDataFromFile(FileWorker.defaultPatchToStaticFiles + "INSERT_YA5PF/TemplateYA5PF.txt");
        String values = "";
        int i = 1;
        for(YA5PF ya5pf : Data.getYA5list().get(profile)){
            String temp = "(WSID, DEC(DAY(CURRENT_DATE),2,0), DEC(CURRENT_TIME,6,0), " + i +", '" +
                    (ya5pf.getMode() == Mode.Удаление ? "B" : "A") + "',\n\t";
            int j = 1;
            for(String value : ya5pf.getData()){
                temp += "'" + value + "'";
                if(j==6){
                    temp += ")";
                    break;
                }else{
                    temp += ", ";
                }
                j++;
            }
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

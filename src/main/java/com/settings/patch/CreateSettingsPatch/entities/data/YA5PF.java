package com.settings.patch.CreateSettingsPatch.entities.data;

import com.settings.patch.CreateSettingsPatch.supportClass.Entity;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class YA5PF extends Entity {

    public YA5PF() {

    }

    public final static String NAMEGZFILE = "GZYA51";

    public String YA5MTN;
    public String YA5XPT;
    public String YA5FUN;
    public String YA5REC;
    public String YA5DLM;
    public String YA5UID;

    public Mode mode;

    public List<String> getData(){
        List<String> data = new ArrayList<>();
        Field[] fields = this.getClass().getFields();
        for(Field field : fields){
            if(field.getName().equals("mode") || field.getName().equals("NAMEGZFILE")){
                continue;
            }
            try {
                data.add((String) field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public List<String> getFieldName(){
        List<String> data = new ArrayList<>();
        Field[] fields = YA5PF.class.getFields();
        for(Field field : fields){
            if(field.getName().equals("mode") || field.getName().equals("NAMEGZFILE")){
                continue;
            }
            data.add(field.getName());
        }
        return data;
    }
}

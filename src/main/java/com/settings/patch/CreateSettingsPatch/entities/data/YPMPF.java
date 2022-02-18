package com.settings.patch.CreateSettingsPatch.entities.data;

import com.settings.patch.CreateSettingsPatch.supportClass.CountFields;
import com.settings.patch.CreateSettingsPatch.supportClass.Entity;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import com.settings.patch.CreateSettingsPatch.supportClass.QuotationMark;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.*;

@Data
@ToString
@CountFields(countFields = 3)
public class YPMPF extends Entity {

    public final static String NAMEGZFILE = "GZYPM1";

    private String YPMCND;
    @QuotationMark
    private String YEMDSC;
    private String YPMCCN;

    private Mode mode;

    public YPMPF() {

    }

    public YPMPF(String nameCondition, String description, String condition, Mode mode){
        this.YPMCND = nameCondition;
        this.YEMDSC = description;
        this.YPMCCN = condition;
        this.mode = mode;
    }

    public YPMPF(String nameCondition, String description, String condition) {
        this.YPMCND = nameCondition;
        this.YEMDSC = description;
        this.YPMCCN = condition;
    }

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
        data.add("YPMCCN");
        data.add("YPMDSC");
        data.add("YPMCND");
        return data;
    }
}


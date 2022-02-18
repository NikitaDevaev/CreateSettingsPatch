package com.settings.patch.CreateSettingsPatch.entities.data;

import com.settings.patch.CreateSettingsPatch.supportClass.Entity;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// AllArgsConstructor (from lombok) еще в бете, но работает корректно
// Можно сгенерировать конструктор, но тогда данный класс будет сильно нагружен

@ToString
@Data
@AllArgsConstructor
@Builder
public class YEFPF extends Entity {

    public final static String NAMEGZFILE = "GZYEF1";

    // Поля публичные для Рефлексии (если можно сделать приватными, то надо изменить)
    public String YEFMIN;
    public String YEFEXT;
    public String YEFTBI;
    public String YEFTBO;
    public String YEFCRA;
    public String YEFLFA;
    public String YEFCRE;
    public String YEFLFE;
    public String YEFALG;
    public String YEFPGM;
    public String YEFPRF;
    public String YEFPG1;
    public String YEFPG2;
    public String YEFDIN;
    public String YEFAIN;
    public String YEFQIN;
    public String YEFDOU;
    public String YEFAOU;
    public String YEFQOU;
    public String YEFDER;
    public String YEFQER;
    public String YEFMNG;
    public String YEFDLM;
    public String YEFHTP;
    public String YEFISI;
    public String YEFISO;
    public String YEFJBD;
    public String YEFMTR;
    public String YEFMTD;
    public String YEFSCR;
    public String YEFSCD;
    public String YEFSIR;
    public String YEFSID;
    public String YEFFCD;
    public String YEFBRN;
    public String YEFJBC;
    public String YEFPG3;
    public String YEFDNM;
    public String YEFMFM;
    public String YEFCTI;
    public String YEFCTO;
    public String YEFRCT;
    public String YEFPG4;
    public String YEFBSP;
    public String YEFPG5;
    public String YEFBTM;
    public String YEFPG6;
    public String YEFBSP2;
    public String YEFPG7;
    public String YEFBTM2;
    public String YEFMCA;
    public String YEFPG8;
    public String YEFMCT;
    public String YEFENC;
    public String YEFICTR;
    public String YEF503;
    public String YEFTIME;
    public String YEFICTO;
    public String YEFICTI;
    public String YEFPRC;
    public String YEFISW;

    public Mode mode;

    public YEFPF(){

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
        Field[] fields = YEFPF.class.getFields();
        for(Field field : fields){
            if(field.getName().equals("mode") || field.getName().equals("NAMEGZFILE")){
                continue;
            }
            data.add(field.getName());
        }
        return data;
    }
}

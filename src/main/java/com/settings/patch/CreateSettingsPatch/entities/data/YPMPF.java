package com.settings.patch.CreateSettingsPatch.entities.data;

import com.settings.patch.CreateSettingsPatch.entities.Mode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class YPMPF {

    public final static String NAMEGZFILE = "GZYPM1";

    private String conditionName;
    private String description;
    private String condition;

    private Mode mode;

    public YPMPF() {
    }

    public YPMPF(String nameCondition, String description, String condition) {
        this.conditionName = nameCondition;
        this.description = description;
        this.condition = condition;
    }
}


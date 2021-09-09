package com.settings.patch.CreateSettingsPatch.entities;

import lombok.Data;

@Data
public class YPMPF {
    private String conditionName;
    private String description;
    private String condition;

    public YPMPF() {
    }

    public YPMPF(String nameCondition, String description, String condition) {
        this.conditionName = nameCondition;
        this.description = description;
        this.condition = condition;
    }
}


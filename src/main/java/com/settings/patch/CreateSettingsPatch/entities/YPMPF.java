package com.settings.patch.CreateSettingsPatch.entities;


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

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}


package com.settings.patch.CreateSettingsPatch.entities;

import lombok.Getter;

public class BuildFile {
    @Getter
    private String number; //example: 5455

    public BuildFile(String task) {
        int start = task.indexOf("ROSS-");
        this.number = task.substring((start + 5), (start + 9));
    }
}

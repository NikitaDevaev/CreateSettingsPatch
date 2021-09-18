package com.settings.patch.CreateSettingsPatch.data;

import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<YPMPF> list = new ArrayList<>();

    public static List<YPMPF> getList() {
        return list;
    }
}

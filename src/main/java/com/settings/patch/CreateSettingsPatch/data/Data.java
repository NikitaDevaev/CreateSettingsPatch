package com.settings.patch.CreateSettingsPatch.data;

import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import lombok.Getter;

import java.util.*;

public class Data {
    @Getter
    private static Map<String, ArrayList<YPMPF>> list = new LinkedHashMap<>();
}

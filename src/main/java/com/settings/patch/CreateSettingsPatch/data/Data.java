package com.settings.patch.CreateSettingsPatch.data;

import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YH4PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import lombok.Getter;

import java.util.*;

public class Data {
    @Getter
    private static Map<String, ArrayList<YPMPF>> YPMlist = new LinkedHashMap<>();
    @Getter
    private static Map<String, ArrayList<YEFPF>> YEFlist = new LinkedHashMap<>();
    @Getter
    private static Map<String, ArrayList<YH4PF>> YH4list = new LinkedHashMap<>();
    @Getter
    private static Map<String, ArrayList<YA5PF>> YA5list = new LinkedHashMap<>();
}

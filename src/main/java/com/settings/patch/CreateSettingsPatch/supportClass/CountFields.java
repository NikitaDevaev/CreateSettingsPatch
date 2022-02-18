package com.settings.patch.CreateSettingsPatch.supportClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface CountFields {
    int countFields();
}

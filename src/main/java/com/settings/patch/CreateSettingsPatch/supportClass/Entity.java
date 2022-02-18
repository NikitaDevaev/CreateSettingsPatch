package com.settings.patch.CreateSettingsPatch.supportClass;

import java.util.List;

// Обязательные методы для сущностей
public abstract class Entity {
    public abstract List<String> getData();
    public abstract List<String> getFieldName();
}

package com.settings.patch.CreateSettingsPatch.data;

import com.settings.patch.CreateSettingsPatch.entities.User;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Users {
    List<User> users = new LinkedList<>();
}

package com.settings.patch.CreateSettingsPatch.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReleaseFile {
    private String mnemonicName; //example: NESS
    private String numberPatch; // example: 144
    private String fsd; // http:/...
    private String task; // http:/..
    private String brd; // http:/..
    private String description;
    private int rebuildLevel; // example: 3

}

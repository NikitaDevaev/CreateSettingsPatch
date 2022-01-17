package com.settings.patch.CreateSettingsPatch.view.YefView;

import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public class GridViewYef {
    @Getter
    private String profile;
    @Getter
    private Grid<YEFPF> gridYef = new Grid<>(YEFPF.class, false);
    private Button newSettingYef = new Button("New Settings", VaadinIcon.PLUS.create());
    public GridViewYef(String profile){
        this.profile = profile;
        gridYef.addColumn(YEFPF::getYEFMIN).setHeader("Метод ввода")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false).setFrozen(true);
        gridYef.addColumn(YEFPF::getYEFPGM).setHeader("Разбор сообщения")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
    }
}

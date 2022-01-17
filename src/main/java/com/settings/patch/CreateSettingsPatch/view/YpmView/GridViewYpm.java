package com.settings.patch.CreateSettingsPatch.view.YpmView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

// Синглтон класс
public class GridViewYpm {
    // View классы
    @Getter
    private String profile;
    DialogViewYpm dialogViewYpm;
    Grid<YPMPF> gridYpm = new Grid<>(YPMPF.class, false);
    Button newSettingYpm = new Button("New Settings", VaadinIcon.PLUS.create(), e->{
        dialogViewYpm.getDialog().open();
        dialogViewYpm.createDialogView(new YPMPF());
    });
    Button clearSettings = new Button("Clear All Settings", event -> {
       Data.getList().remove(getProfile());
    });

    public Grid<YPMPF> getGridYpm() {
        return this.gridYpm;
    }

    public Button getNewSettingYpm() {
        return this.newSettingYpm;
    }

    public GridViewYpm(String profile){
        this.profile = profile;
        this.dialogViewYpm = new DialogViewYpm(this);
        // Корректируем таблицу
        gridYpm.addColumn(YPMPF::getConditionName).setHeader("Условие")
                .setFlexGrow(0).setWidth("110px")
                .setResizable(false).setFrozen(true);
        gridYpm.addColumn(YPMPF::getDescription).setHeader("Описание")
                .setFlexGrow(0).setWidth("570px")
                .setResizable(false);
        gridYpm.addColumn(YPMPF::getCondition).setHeader("Выражение")
                .setAutoWidth(true);
        gridYpm.addColumn(YPMPF::getMode).setHeader("Mode");
        gridYpm.setItems(Data.getList().get(this.profile));
        gridYpm.asSingleSelect().addValueChangeListener(e->{
            dialogViewYpm.getDialog().open();
            dialogViewYpm.createDialogView(e.getValue());
        });
    }

}

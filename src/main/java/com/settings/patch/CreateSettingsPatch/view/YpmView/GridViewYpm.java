package com.settings.patch.CreateSettingsPatch.view.YpmView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;

// Синглтон класс
public class GridViewYpm {
    // View классы
    DialogViewYpm dialogViewYpm;
    Grid<YPMPF> gridYpm = new Grid<>(YPMPF.class, false);
    Button newSettingYpm = new Button("New Settings", VaadinIcon.PLUS.create(), e->{
        dialogViewYpm.getDialog().open();
        dialogViewYpm.createDialogView(new YPMPF());
    });

    public Grid<YPMPF> getGridYpm() {
        return this.gridYpm;
    }

    public Button getNewSettingYpm() {
        return this.newSettingYpm;
    }

    public GridViewYpm(){
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
        gridYpm.setItems(Data.getList());
        gridYpm.asSingleSelect().addValueChangeListener(e->{
            dialogViewYpm.getDialog().open();
            dialogViewYpm.createDialogView(e.getValue());

        });
    }

}

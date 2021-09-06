package com.settings.patch.CreateSettingsPatch.View.YpmView;

import com.settings.patch.CreateSettingsPatch.View.mainView;
import com.settings.patch.CreateSettingsPatch.entities.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;

// Синглтон класс
public class GridViewYpm {
    // View классы
    DialogViewYpm dialogViewYpm = new DialogViewYpm();
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
        // Корректируем таблицу
        gridYpm.addColumn(YPMPF::getConditionName).setHeader("Условие").setWidth("10px");
        gridYpm.addColumn(YPMPF::getDescription).setHeader("Описание");
        gridYpm.addColumn(YPMPF::getCondition).setHeader("Выражение");
        gridYpm.setItems(mainView.getList());
        gridYpm.asSingleSelect().addValueChangeListener(e->{
            dialogViewYpm.getDialog().open();
            dialogViewYpm.createDialogView(e.getValue());
        });
    }

}

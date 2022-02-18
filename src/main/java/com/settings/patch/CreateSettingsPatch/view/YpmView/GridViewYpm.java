package com.settings.patch.CreateSettingsPatch.view.YpmView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;


// TODO: когда удаляешь все данные вылетает ошибка в консоль (не влияет на работу) неплохо было бы исправить
public class GridViewYpm {
    // View классы
    @Getter
    private String profile;
    private DialogViewYpm dialogViewYpm;
    @Getter
    Grid<YPMPF> gridYpm = new Grid<>(YPMPF.class, false);
    @Getter
    Button newSettingYpm = new Button("New Settings", VaadinIcon.PLUS.create(), e->{
        dialogViewYpm.getDialog().open();
        dialogViewYpm.createDialogView(new YPMPF());
    });
    @Getter
    Button clearSettingsYPM = new Button("Clear", event -> {
       Data.getYPMlist().remove(profile);
       gridYpm.getDataProvider().refreshAll();
    });
    public GridViewYpm(String profile){
        this.profile = profile;
        this.dialogViewYpm = new DialogViewYpm(this);
        // Корректируем таблицу
        gridYpm.addColumn(YPMPF::getYPMCND).setHeader("Условие")
                .setFlexGrow(0).setWidth("110px")
                .setResizable(false).setFrozen(true);
        gridYpm.addColumn(YPMPF::getYEMDSC).setHeader("Описание")
                .setFlexGrow(0).setWidth("570px")
                .setResizable(false);
        gridYpm.addColumn(YPMPF::getYPMCCN).setHeader("Выражение")
                .setAutoWidth(true);
        gridYpm.addColumn(YPMPF::getMode).setHeader("Mode");
        gridYpm.setItems(Data.getYPMlist().get(this.profile));
        gridYpm.asSingleSelect().addValueChangeListener(e->{
            dialogViewYpm.getDialog().open();
            dialogViewYpm.createDialogView(e.getValue());
        });
    }

}

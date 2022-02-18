package com.settings.patch.CreateSettingsPatch.view.Ya5View;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public class GridViewYa5 {

    @Getter
    private DialogViewYa5 dialogViewYa5;
    @Getter
    private String profile;
    @Getter
    private Grid<YA5PF> gridYa5 = new Grid<>(YA5PF.class, false);
    @Getter
    private Button newSettingYa5 = new Button("New Settings", VaadinIcon.PLUS.create(), event -> {
        dialogViewYa5.getDialog().open();
        dialogViewYa5.createDialogView(new YA5PF());
    });

    @Getter
    private Button clearSettingYa5 = new Button("Clear", event -> {
        Data.getYA5list().remove(profile);
        gridYa5.getDataProvider().refreshAll();
    });

    public GridViewYa5(String profile){
        this.profile = profile;
        this.dialogViewYa5 = new DialogViewYa5(this);
        gridYa5.addColumn(YA5PF::getYA5MTN).setHeader("Метод ввода")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false).setFrozen(true);
        gridYa5.addColumn(YA5PF::getYA5XPT).setHeader("Вид обмена")
                .setFlexGrow(0).setWidth("90px")
                .setResizable(false);
        gridYa5.addColumn(YA5PF::getYA5FUN).setHeader("Имя функции")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYa5.addColumn(YA5PF::getYA5REC).setHeader("Имя поля")
                .setFlexGrow(0).setWidth("90px")
                .setResizable(false);
        gridYa5.addColumn(YA5PF::getYA5DLM).setHeader("Дата последнего изменения")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYa5.addColumn(YA5PF::getYA5UID).setHeader("UUID")
                .setFlexGrow(0).setWidth("90px")
                .setResizable(false);

        gridYa5.addColumn(YA5PF::getMode).setHeader("Mode");
        gridYa5.setItems(Data.getYA5list().get(this.profile));
        gridYa5.asSingleSelect().addValueChangeListener(e->{
            dialogViewYa5.getDialog().open();
            dialogViewYa5.createDialogView(e.getValue());
        });
        gridYa5.setVisible(false);
        newSettingYa5.setVisible(false);
        clearSettingYa5.setVisible(false);
    }
}

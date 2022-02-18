package com.settings.patch.CreateSettingsPatch.view.YefView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public class GridViewYef {
    @Getter
    private DialogViewYef dialogViewYef;
    @Getter
    private String profile;
    @Getter
    private Grid<YEFPF> gridYef = new Grid<>(YEFPF.class, false);
    @Getter
    private Button newSettingYef = new Button("New Settings", VaadinIcon.PLUS.create(),event -> {
        dialogViewYef.getDialog().open();
        dialogViewYef.createDialogView(new YEFPF());
    });
    @Getter
    private Button clearSettingYef = new Button("Clear", event -> {
        Data.getYEFlist().remove(profile);
        gridYef.getDataProvider().refreshAll();
    });
    public GridViewYef(String profile){
        this.profile = profile;
        this.dialogViewYef = new DialogViewYef(this);
        gridYef.addColumn(YEFPF::getYEFMIN).setHeader("Метод ввода")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false).setFrozen(true);
        gridYef.addColumn(YEFPF::getYEFEXT).setHeader("Вид обмена")
                .setFlexGrow(0).setWidth("90px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFTBI).setHeader("Таб. кодировки входящ.")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFTBO).setHeader("Таб. кодировки исход.")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFCRA).setHeader("Код Cr в ASCII")
                .setFlexGrow(0).setWidth("100px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFLFA).setHeader("Код Lf в ASCII")
                .setFlexGrow(0).setWidth("100px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFCRE).setHeader("Код Cr в EBCDIC")
                .setFlexGrow(0).setWidth("100px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFLFE).setHeader("Код Lr в ASCII")
                .setFlexGrow(0).setWidth("100px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFALG).setHeader("Алгоритм")
                .setFlexGrow(0).setWidth("90px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPGM).setHeader("Разбор сообщения")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPRF).setHeader("Префикс")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG1).setHeader("Reserved")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG2).setHeader("Reserved")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFDIN).setHeader("Каталог входящих")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFAIN).setHeader("Каталог арх.вход.")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFQIN).setHeader("Очередь входящих")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFDOU).setHeader("Каталог исходящих")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFAOU).setHeader("Очередь исходящих")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFQOU).setHeader("Очередь исходящих")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFDER).setHeader("Каталог не принятых")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFQER).setHeader("Очередь не принятых")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMNG).setHeader("Имя MQ manager")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFDLM).setHeader("Дата модификации")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFHTP).setHeader("Handler type")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFISI).setHeader("Start jobs IN*?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFISO).setHeader("Start jobs FS*?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFJBD).setHeader("Job description")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMTR).setHeader("Тип сообщ. R-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMNG).setHeader("Тип сообщ. D-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFSCR).setHeader("Скрипт R-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFSCD).setHeader("Скрипт D-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFSIR).setHeader("Скрипт исключения R-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFSID).setHeader("Скрипт исключения D-макет")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFFCD).setHeader("Код формата")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFBRN).setHeader("Филиал маршрутизации")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFJBC).setHeader("Number of jobs")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG3).setHeader("Обработчик вх. сообщ.")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFDNM).setHeader("Number of days")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMFM).setHeader("Message format")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFCTI).setHeader("Каталог контроля")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFCTO).setHeader("Каталог провереных")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFRCT).setHeader("Контр. реестр.?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG4).setHeader("Обработчик контроля р.")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFBSP).setHeader("Форм. БЭСП?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG5).setHeader("Обработчик БЭСП")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFBTM).setHeader("Задержка БЭСП")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG6).setHeader("Обработчик ФОС")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFBSP2).setHeader("Форм. БЭСП?2")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG7).setHeader("Обработчик БЭСП2")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFBTM2).setHeader("Задержка БЭСП2")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMCA).setHeader("Автореестр?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPG8).setHeader("Обработчик Автор-в")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFMCT).setHeader("Задержка АвтР")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFENC).setHeader("Шифрование?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFICTR).setHeader("Контроль входящих вне EQ?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEF503).setHeader("Формировать ED503?")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFTIME).setHeader("Граничн.время формирован. ED503 с будущей датой")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFICTO).setHeader("Каталог для контроля вне EQ")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFICTI).setHeader("Каталог с результом контр. вне EQ")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFPRC).setHeader("Рабочий каталог (для приема файлов)")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);
        gridYef.addColumn(YEFPF::getYEFISW).setHeader("Прием файлов SW (рабочий каталог)")
                .setFlexGrow(0).setWidth("170px")
                .setResizable(false);

        gridYef.addColumn(YEFPF::getMode).setHeader("Mode");
        gridYef.setItems(Data.getYEFlist().get(this.profile));
        gridYef.asSingleSelect().addValueChangeListener(e->{
            dialogViewYef.getDialog().open();
            dialogViewYef.createDialogView(e.getValue());
        });
        gridYef.setVisible(false);
        newSettingYef.setVisible(false);
        clearSettingYef.setVisible(false);
    }
}

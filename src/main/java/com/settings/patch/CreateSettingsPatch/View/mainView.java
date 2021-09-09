package com.settings.patch.CreateSettingsPatch.View;

import com.settings.patch.CreateSettingsPatch.GenerateModule.Generator;
import com.settings.patch.CreateSettingsPatch.View.YpmView.GridViewYpm;
import com.settings.patch.CreateSettingsPatch.entities.YPMPF;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Route
@Theme(variant = Lumo.DARK)
public class mainView extends VerticalLayout {

    private Generator generator;
    //Структуры
    private static List<YPMPF> list = new ArrayList<>();
    // View классы
    GridViewYpm gridViewYpm = new GridViewYpm();
    // Кнопки
    private final Button createPatch;
    DownloadDialog downloadDialog;
    private final CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();

    public mainView() {



        //-------Создание Input полей для заполнения информации о патче----------
        TextField mnemonic = new TextField();
        mnemonic.setLabel("Мнемоника патча");
        IntegerField version = new IntegerField();
        Div Prefix = new Div();
        Prefix.setText("#");
        version.setPrefixComponent(Prefix);
        version.setLabel("Версия патча");
        HorizontalLayout patchInfo = new HorizontalLayout(mnemonic,version);
        add(patchInfo);

        Accordion accordion = new Accordion();

        VerticalLayout personalInformationLayout = new VerticalLayout();
        TextField fsd = new TextField("Ссылка на fsd");
        fsd.setWidth("500px");
        TextField task = new TextField("Ссылка на задачу");
        task.setWidth("500px");
        TextField brd = new TextField("Ссылка на brd");
        brd.setWidth("500px");
        TextArea desc = new TextArea("Описание");
        desc.setWidth("500px");
        personalInformationLayout.add(fsd, task,brd, desc);
        accordion.add("Доп. инфо для патча", personalInformationLayout);
        add(accordion);

        //------------------------------------------------------------------------
        //-- Создание Check box для выбора в какие таблицы будут добавляться настройки--

        checkboxGroup.setLabel("Добавление настроек");
        checkboxGroup.setItems("YPMPF", "YEFPF");
        checkboxGroup.select("YPMPF");
        //Anchor anchor = new Anchor(streamResource, "download");

        //add(anchor);
        this.createPatch = new Button("Сгенерировать патч", VaadinIcon.DOWNLOAD.create());
        add(checkboxGroup, createPatch);
        //--------------------------------------------------------------------------
        //----------------- Таблицы ---------------------------------------
        add(gridViewYpm.getNewSettingYpm(), gridViewYpm.getGridYpm());
        //-----------------------------------------------------------------
        // Обработка
        createPatch.addClickListener(e -> {
            this.generator = new Generator(task.getValue(), mnemonic.getValue(), version.getValue().toString());
            generator.run();
            this.downloadDialog = new DownloadDialog(generator.getNameBuildFile(), generator.getPatchForBuildFile());
            downloadDialog.getDialog().open();
        });
        checkboxGroup.addSelectionListener(e ->{
            if(!e.getValue().contains("YPMPF")){
                gridViewYpm.getNewSettingYpm().setVisible(false);
                gridViewYpm.getGridYpm().setVisible(false);
            }else{
                gridViewYpm.getNewSettingYpm().setVisible(true);
                gridViewYpm.getGridYpm().setVisible(true);
            }
        });
    }
    public static List<YPMPF> getList() {
        return list;
    }

    public static void setList(List<YPMPF> list) {
       list = list;
    }



}
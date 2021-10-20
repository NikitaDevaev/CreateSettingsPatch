package com.settings.patch.CreateSettingsPatch.view;

import com.settings.patch.CreateSettingsPatch.generateModules.Generator;
import com.settings.patch.CreateSettingsPatch.view.YpmView.GridViewYpm;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("")
@Theme(variant = Lumo.DARK)
public class MainView extends VerticalLayout {

    private Generator generator;
    // View классы
    GridViewYpm gridViewYpm = new GridViewYpm();
    // Кнопки
    private final Button createPatch;
    DownloadDialog downloadDialog;
    private final CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();

    private AppLayout appLayout = new AppLayout();
    private Tabs menu = new Tabs();
    private H1 title = new H1("Патчер");

    private TextField mnemonic = new TextField();
    Div Prefix = new Div();
    IntegerField version = new IntegerField();

    private TextField fsd = new TextField("Ссылка на fsd");
    private TextField task = new TextField("Ссылка на задачу");
    private TextField brd = new TextField("Ссылка на brd");
    private TextArea desc = new TextArea("Описание");

    public MainView() {

        title.getStyle()
                .set("font-size", "20px")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");
        appLayout.addToNavbar(title, menu);
        add(appLayout);
        //-------Создание Input полей для заполнения информации о патче----------

        mnemonic.setLabel("Мнемоника патча");
        Prefix.setText("#");
        version.setPrefixComponent(Prefix);
        version.setLabel("Версия патча");
        HorizontalLayout patchInfo = new HorizontalLayout(mnemonic,version);
        add(patchInfo);

        Accordion accordion = new Accordion();

        VerticalLayout personalInformationLayout = new VerticalLayout();
        fsd.setWidth("500px");
        task.setWidth("500px");
        brd.setWidth("500px");
        desc.setWidth("500px");
        personalInformationLayout.add(fsd, task,brd, desc);
        accordion.add("Доп. инфо для патча", personalInformationLayout);
        add(accordion);

        //------------------------------------------------------------------------
        //-- Создание Check box для выбора в какие таблицы будут добавляться настройки--

        checkboxGroup.setLabel("Добавление настроек");
        checkboxGroup.setItems("YPMPF", "YEFPF");
        checkboxGroup.select("YPMPF");


        this.createPatch = new Button("Сгенерировать патч", VaadinIcon.DOWNLOAD.create());
        add(checkboxGroup, createPatch);
        //--------------------------------------------------------------------------
        //----------------- Таблицы ---------------------------------------
        add(gridViewYpm.getNewSettingYpm(), gridViewYpm.getGridYpm());
        //-----------------------------------------------------------------
        // Обработка
        createPatch.addClickListener(e -> {
            // Переделать бы конструктор на фабричный метод
            this.generator = new Generator(fsd.getValue(), task.getValue(),
                    brd.getValue(), desc.getValue(), mnemonic.getValue().toUpperCase(),
                    version.getValue().toString(), 3, checkboxGroup.getSelectedItems());
            generator.run();
            this.downloadDialog = new DownloadDialog(generator);
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
}
package com.settings.patch.CreateSettingsPatch.view;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.settings.patch.CreateSettingsPatch.generateModules.Generator;
import com.settings.patch.CreateSettingsPatch.generateModules.XlsWorker;
import com.settings.patch.CreateSettingsPatch.view.Ya5View.GridViewYa5;
import com.settings.patch.CreateSettingsPatch.view.xslxView.ExportDialog;
import com.settings.patch.CreateSettingsPatch.view.xslxView.ImportDialog;
import com.settings.patch.CreateSettingsPatch.view.YefView.GridViewYef;
import com.settings.patch.CreateSettingsPatch.view.YpmView.GridViewYpm;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


@Route("")
public class MainView extends VerticalLayout {

    // Логические Классы
    private Generator generator;
    private DownloadDialog downloadDialog;
    private ImportDialog uploadDialog;
    private XlsWorker xlsWorker =  new XlsWorker();

    // View классы
    private GridViewYef gridViewYef;
    private GridViewYpm gridViewYpm;
    private GridViewYa5 gridViewYa5;

    // Поля
    @Getter
    private TextField fsd = new TextField("Ссылка на fsd");
    private TextField task = new TextField("Ссылка на задачу");
    private TextField brd = new TextField("Ссылка на brd");
    private TextField tests = new TextField("Ссылка на тесты");
    private TextArea desc = new TextArea("Описание");
    private TextField mnemonic = new TextField();
    private IntegerField version = new IntegerField();

    // Кнопки
    private final Button createPatch;
    private Button user = new Button("Login out");
    private Button importButton = new Button("Import");
    private Button exportButton = new Button("Export");

    // Дополнительные компоненты
    private final CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
    private AppLayout appLayout = new AppLayout();
    private Tabs menu = new Tabs();
    private H1 title = new H1("Автогенерация поставок");
    private Div Prefix = new Div();
    private List<Grid> tableList = new ArrayList<>();
    private List<Button> buttonList = new ArrayList<>();
    private List<Button> clearList = new ArrayList<>();
    private ExportDialog exportDialog;

    // TODO: Перенести все элементы визуала в глобальную область
    public MainView() throws NullPointerException{
        // Воизбежание null pointer
        createData();
        // TODO: Создать класс под хранение шаблонов и работать с листом классов, а не кучей листов
        createTableList();
        createAddButtonList();
        createClearButtonList();

        Anchor anchor = new Anchor("/Patcher/login ", "");
        anchor.add(user);
        this.createPatch = new Button("Сгенерировать патч", VaadinIcon.DOWNLOAD.create());
        user.getStyle().set("border-radius","10px");
        createPatch.getStyle().set("border-radius","10px");
        importButton.getStyle().set("border-radius","10px");
        this.exportDialog = new ExportDialog();
        HorizontalLayout buttonLayout = new HorizontalLayout(createPatch, importButton, exportButton, anchor);
        buttonLayout.getStyle().set("flex-wrap", "wrap").set("position", "absolute").set("right", "var(--lumo-space-l)");
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        title.getStyle()
                .set("font-size", "20px")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");

        appLayout.addToNavbar(title, menu, buttonLayout);
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
        tests.setWidth("500px");
        desc.setWidth("500px");
        personalInformationLayout.add(fsd, task,brd,tests, desc);
        accordion.add("Доп. инфо для патча", personalInformationLayout);
        add(accordion);

        //------------------------------------------------------------------------
        //-- Создание Check box для выбора в какие таблицы будут добавляться настройки--

        checkboxGroup.setLabel("Добавление настроек");
        checkboxGroup.setItems("YPMPF", "YEFPF", "YA5PF");
        checkboxGroup.select("YPMPF");
        // TODO: необходима адаптивная верстка
        checkboxGroup.getStyle().set("flex-wrap", "wrap").
                set("position", "absolute").set("right", "300px").set("top", "60px");
        add(checkboxGroup);
        //--------------------------------------------------------------------------
        //----------------- Таблицы ---------------------------------------

        Tab ypmpfTable = new Tab("YPMPF");
        ypmpfTable.setClassName("YPMPF");
        Tab yefpfTable = new Tab("YEFPF");
        yefpfTable.setClassName("YEFPF");
        Tab ya5pfTable = new Tab("YA5PF");
        ya5pfTable.setClassName("YA5PF");
        Tabs tabs = new Tabs(ypmpfTable, yefpfTable, ya5pfTable);


        add(tabs);
        HorizontalLayout horizontalLayoutButtonYA5 = new HorizontalLayout(gridViewYa5.getNewSettingYa5(), gridViewYa5.getClearSettingYa5());
        HorizontalLayout horizontalLayoutButtonYEF = new HorizontalLayout(gridViewYef.getNewSettingYef(), gridViewYef.getClearSettingYef());
        HorizontalLayout horizontalLayoutButtonYPM = new HorizontalLayout(gridViewYpm.getNewSettingYpm(), gridViewYpm.getClearSettingsYPM());
        // TODO: Решить проблему со смещением
        // https://habr.com/ru/company/haulmont/blog/208388/ вроде решение изложено тут (посмотреть)
            add(horizontalLayoutButtonYPM, gridViewYpm.getGridYpm());
            add(horizontalLayoutButtonYEF, gridViewYef.getGridYef());
            add(horizontalLayoutButtonYA5, gridViewYa5.getGridYa5());

        //-----------------------------------------------------------------
        // Обработчики
        //-----------------------------------------------------------------

        importButton.addClickListener(e -> {
            this.uploadDialog = new ImportDialog(tableList);
            uploadDialog.getDialog().open();
        });

        tabs.addSelectedChangeListener(event -> {
            // TODO: переделать анализ (упростить с появлением класса)
            if(tableList.size() != buttonList.size()){
                System.out.println(tabs.getSelectedTab().getClassName());
                Notification.show("Программная ошибка")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }else{
                for(int i = 0; i < tableList.size(); i++){
                    if(i == tabs.getSelectedIndex()){
                        tableList.get(i).setVisible(true);
                        buttonList.get(i).setVisible(true);
                        clearList.get(i).setVisible(true);
                    }else{
                        tableList.get(i).setVisible(false);
                        buttonList.get(i).setVisible(false);
                        clearList.get(i).setVisible(false);
                    }
                }
            }
        });

        exportButton.addClickListener(event -> {
            xlsWorker.write(checkboxGroup.getSelectedItems());
            exportDialog.getDialog().open();
        });

        user.addClickListener(e->{
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        });

        createPatch.addClickListener(e -> {
            // Переделать бы конструктор на фабричный метод
            if(valid()) {
                this.generator = new Generator(fsd.getValue(), task.getValue(),
                        brd.getValue(), tests.getValue(), desc.getValue(), mnemonic.getValue().toUpperCase(),
                        version.getValue().toString(), 3, checkboxGroup.getSelectedItems());
                generator.run();
                this.downloadDialog = new DownloadDialog(generator);
                downloadDialog.getDialog().open();
            }
        });
        checkboxGroup.addSelectionListener(e ->{

        });
    }

    public void createData(){
        if(!Data.getYPMlist().containsKey(getProfile())){
            Data.getYPMlist().put(getProfile(), new ArrayList<YPMPF>());
        }
        if(!Data.getYEFlist().containsKey(getProfile())){
            Data.getYEFlist().put(getProfile(), new ArrayList<YEFPF>());
        }
        if(!Data.getYA5list().containsKey(getProfile())){
            Data.getYA5list().put(getProfile(), new ArrayList<YA5PF>());
        }
    }

    private void createTableList(){
        this.gridViewYef = new GridViewYef(getProfile());
        this.gridViewYpm = new GridViewYpm(getProfile());
        this.gridViewYa5 = new GridViewYa5(getProfile());
        tableList.add(gridViewYpm.getGridYpm());
        tableList.add(gridViewYef.getGridYef());
        tableList.add(gridViewYa5.getGridYa5());
    }
    private void createAddButtonList(){
        buttonList.add(gridViewYpm.getNewSettingYpm());
        buttonList.add(gridViewYef.getNewSettingYef());
        buttonList.add(gridViewYa5.getNewSettingYa5());
    }
    private void createClearButtonList(){
        clearList.add(gridViewYpm.getClearSettingsYPM());
        clearList.add(gridViewYef.getClearSettingYef());
        clearList.add(gridViewYa5.getClearSettingYa5());
    }

    private String getProfile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private boolean valid(){
      if(task.getValue().trim().equals("") && !task.getValue().contains("ROSS")){
          Notification.show("Не корректно заполнена сслыка на задачу").addThemeVariants(NotificationVariant.LUMO_ERROR);
          return false;
      }
      if(mnemonic.getValue().trim().equals("")){
          Notification.show("Не заполнена мнемоника").addThemeVariants(NotificationVariant.LUMO_ERROR);
          return false;
      }
      if(tests.getValue().trim().equals("")){
          Notification.show("Отсутствует ссылка на тесты").addThemeVariants(NotificationVariant.LUMO_ERROR);
          return false;
      }
      if(desc.getValue().trim().equals("")){
          Notification.show("Отсутствует описание для патча").addThemeVariants(NotificationVariant.LUMO_ERROR);
          return false;
      }
      return true;
    }
}
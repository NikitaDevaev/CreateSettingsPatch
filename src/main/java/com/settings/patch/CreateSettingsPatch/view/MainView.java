package com.settings.patch.CreateSettingsPatch.view;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.data.YPMPF;
import com.settings.patch.CreateSettingsPatch.generateModules.CsvWorker;
import com.settings.patch.CreateSettingsPatch.generateModules.FileWorker;
import com.settings.patch.CreateSettingsPatch.generateModules.Generator;
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
import com.vaadin.flow.component.html.Label;
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


@Route("")
public class MainView extends VerticalLayout {
    private Generator generator;
    // View классы
    private GridViewYef gridViewYef;
    private GridViewYpm gridViewYpm;
    // Кнопки
    private final Button createPatch;
    private CsvWorker csvWorker = new CsvWorker();
    private DownloadDialog downloadDialog;
    private final CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();

    private AppLayout appLayout = new AppLayout();
    private Tabs menu = new Tabs();
    private H1 title = new H1("Патчер");

    private TextField mnemonic = new TextField();
    Div Prefix = new Div();
    IntegerField version = new IntegerField();

    @Getter
    private TextField fsd = new TextField("Ссылка на fsd");
    private TextField task = new TextField("Ссылка на задачу");
    private TextField brd = new TextField("Ссылка на brd");
    private TextField tests = new TextField("Ссылка на тесты");
    private TextArea desc = new TextArea("Описание");

    private Button user = new Button("Login out");
    public MainView() throws NullPointerException{
        if(!Data.getList().containsKey(getProfile())){
            Data.getList().put(getProfile(), new ArrayList<YPMPF>());
        }
        this.gridViewYef = new GridViewYef(getProfile());
        this.gridViewYpm = new GridViewYpm(getProfile());
        Anchor anchor = new Anchor("/Patcher/login ", "");
        anchor.add(user);
        this.createPatch = new Button("Сгенерировать патч", VaadinIcon.DOWNLOAD.create());
        user.getStyle().set("border-radius","10px");
        createPatch.getStyle().set("border-radius","10px");
        HorizontalLayout buttonLayout = new HorizontalLayout(createPatch, anchor);
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
        checkboxGroup.setItems("YPMPF", "YEFPF");
        checkboxGroup.select("YPMPF");



        add(checkboxGroup);
        //--------------------------------------------------------------------------
        //----------------- Таблицы ---------------------------------------

        Tab ypmpfTable = new Tab("YPMPF");
        Tab yefpfTable = new Tab("YEFPF");
        Tabs tabs = new Tabs(ypmpfTable, yefpfTable);
        add(tabs);
        tabs.addAttachListener(event -> {
            if(tabs.getSelectedIndex() == 0){
                gridViewYpm.getNewSettingYpm().setVisible(false);
                gridViewYpm.getGridYpm().setVisible(false);
            }
        });


        add(gridViewYpm.getNewSettingYpm(), gridViewYpm.getGridYpm());

        //-----------------------------------------------------------------
        // Обработка
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
            if(!e.getValue().contains("YPMPF")){
                gridViewYpm.getNewSettingYpm().setVisible(false);
                gridViewYpm.getGridYpm().setVisible(false);
            }else{
                gridViewYpm.getNewSettingYpm().setVisible(true);
                gridViewYpm.getGridYpm().setVisible(true);
            }
        });
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
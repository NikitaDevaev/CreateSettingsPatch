package com.settings.patch.CreateSettingsPatch.view.YefView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;

public class DialogViewYef {

    private GridViewYef gridViewYef;
    @Getter
    private Dialog dialog;
    // Заголовок
    private H2 headline = new H2("Добавление новой настроки");
    private Paragraph paragraph = new Paragraph(
            "Настройка условия для YEFPF");
    // Поля
    private TextField YEFMIN = new TextField("Метод ввода");
    private TextField YEFEXT = new TextField("Вид обмена");
    private TextField YEFTBI = new TextField("Таблица кодировки входящиз");
    private TextField YEFTBO = new TextField("Таблица кодировки исходящих");
    private TextField YEFCRA = new TextField("Код Cr в ASCII");
    private TextField YEFLFA = new TextField("Код Lf в ASCII");
    private TextField YEFCRE = new TextField("Код Cr в EBCDIC");
    private TextField YEFLFE = new TextField("Код Lf в EBCDIC");
    private TextField YEFALG = new TextField("Алгоритм");
    private TextField YEFPGM = new TextField("Разбор сообщения");
    private TextField YEFPRF = new TextField("Префикс");
    private TextField YEFPG1 = new TextField("Reserved");
    private TextField YEFPG2 = new TextField("Reserved");
    private TextField YEFDIN = new TextField("Каталог входящих");
    private TextField YEFAIN = new TextField("Каталог арх.вход.");
    private TextField YEFQIN = new TextField("Очередь входящих");
    private TextField YEFDOU = new TextField("Каталог исходящих");
    private TextField YEFAOU = new TextField("Каталог арх.исход.");
    private TextField YEFQOU = new TextField("Очередь исходящих");
    private TextField YEFDER = new TextField("Каталог не принятых");
    private TextField YEFQER = new TextField("Очередь не принятых");
    private TextField YEFMNG = new TextField("Имя MQ manager");
    private TextField YEFDLM = new TextField("Дата модификации");
    private TextField YEFHTP = new TextField("Handler type");
    private TextField YEFISI = new TextField("Start jobs IN*?");
    private TextField YEFISO = new TextField("Start jobs FS*?");
    private TextField YEFJBD = new TextField("Job description");
    private TextField YEFMTR = new TextField("Тип сообщ. R-макет");
    private TextField YEFMTD = new TextField("Тип сообщ. D-макет");
    private TextField YEFSCR = new TextField("Скрипт R-макет");
    private TextField YEFSCD = new TextField("Скрипт D-макет");
    private TextField YEFSIR = new TextField("Скрипт исключения R-макет");
    private TextField YEFSID = new TextField("Скрипт исключения D-макет");
    private TextField YEFFCD = new TextField("Код формата");
    private TextField YEFBRN = new TextField("Филиал маршрутизации");
    private TextField YEFJBC = new TextField("Number of jobs");
    private TextField YEFPG3 = new TextField("Обработчик вх. сообщ.");
    private TextField YEFDNM = new TextField("Number of days");
    private TextField YEFMFM = new TextField("Message format");
    private TextField YEFCTI = new TextField("Каталог контроля");
    private TextField YEFCTO = new TextField("Каталог провереных");
    private TextField YEFRCT = new TextField("Контр. реестр.?");
    private TextField YEFPG4 = new TextField("Обработчик контроля р.");
    private TextField YEFBSP = new TextField("Форм. БЭСП?");
    private TextField YEFPG5 = new TextField("Обработчик БЭСП");
    private TextField YEFBTM = new TextField("Задержка БЭСП");
    private TextField YEFPG6 = new TextField("Обработчик ФОС");
    private TextField YEFBSP2 = new TextField("Форм. БЭСП?2");
    private TextField YEFPG7 = new TextField("Обработчик БЭСП2");
    private TextField YEFBTM2 = new TextField("Задержка БЭСП2");
    private TextField YEFMCA = new TextField("Автореестр?");
    private TextField YEFPG8 = new TextField("Обработчик Автор-в");
    private TextField YEFMCT = new TextField("Задержка АвтР");
    private TextField YEFENC = new TextField("Шифрование?");
    private TextField YEFICTR = new TextField("Контроль входящих вне EQ?");
    private TextField YEF503 = new TextField("Формировать ED503?");
    private TextField YEFTIME = new TextField("Граничн.время формирован. ED503 с будущей датой");
    private TextField YEFICTO = new TextField("Каталог для контроля вне EQ");
    private TextField YEFICTI = new TextField("Каталог с результом контр. вне EQ");
    private TextField YEFPRC = new TextField("Рабочий каталог (для приема файлов)");
    private TextField YEFISW = new TextField("Прием файлов SW (рабочий каталог)");
    // Кнопки
    private Button saveButton = new Button("Save",  VaadinIcon.CHECK.create());
    private  Button closeButton = new Button("Close");
    private  Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

    private ComboBox<Mode> mode = new ComboBox<>();

    // Связка полей
    // имена полей и имена переменных в классе связывания должны совподать (а то не будет работать =)) )
    private Binder<YEFPF> binder = new Binder<>(YEFPF.class);

    @Autowired
    public DialogViewYef(GridViewYef gridViewYef){
        this.gridViewYef = gridViewYef;
        // Создание экземпляра объекта диалогового окна
        this.dialog = new Dialog();
        mode.setItems(EnumSet.allOf(Mode.class));

        HorizontalLayout actions = new HorizontalLayout(saveButton, closeButton, deleteButton);
        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph, YEFMIN, YEFEXT, YEFTBI, YEFTBO, YEFCRA, YEFLFA, YEFCRE,
                YEFLFE, YEFALG, YEFPGM, YEFPRF, YEFPG1, YEFPG2, YEFDIN, YEFAIN, YEFQIN, YEFDOU, YEFAOU, YEFQOU, YEFDER, YEFQER, YEFMNG,
                YEFDLM, YEFHTP,YEFISI,YEFISO,YEFJBD,YEFMTR,YEFMTD,YEFSCR,YEFSCD,YEFSIR,YEFSID,YEFFCD,YEFBRN,YEFJBC,YEFPG3,YEFDNM,YEFMFM,
                YEFCTI,YEFCTO,YEFRCT,YEFPG4,YEFBSP,YEFPG5,YEFBTM,YEFPG6,YEFBSP2,YEFPG7,YEFBTM2,YEFMCA,YEFPG8,YEFMCT,YEFENC,YEFICTR,YEF503,
                YEFTIME,YEFICTO,YEFICTI,YEFPRC,YEFISW, mode, actions);
        binder.bindInstanceFields(this);

        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);

        dialog.add(dialogLayout);

        // Анализ действий
        closeButton.addClickListener(e -> dialog.close());
        saveButton.addClickListener(e -> {
            if(valid()){
                save();
                gridViewYef.getGridYef().getDataProvider().refreshAll();
            }
            });
        deleteButton.addClickListener(e ->{
            delete();
            gridViewYef.getGridYef().getDataProvider().refreshAll();
        });

    }
    public void createDialogView(YEFPF yefpf) {
        binder.bindInstanceFields(yefpf);
        binder.setBean(yefpf);
    }

   // TODO: вынести функционал в отдельный класс для использования во всех диалогах
    public void save(){
        final int it = Data.getYEFlist().get(gridViewYef.getProfile()).indexOf(binder.getBean());
        if(it == -1){
            Data.getYEFlist().get(gridViewYef.getProfile()).add(binder.getBean());
        }else{
            Data.getYEFlist().get(gridViewYef.getProfile()).set(it, binder.getBean());
        }
        dialog.close();
    }

    public void delete(){
        Data.getYEFlist().get(gridViewYef.getProfile()).remove(binder.getBean());
        dialog.close();
    }

    private boolean valid(){
        if(YEFMIN.getValue().trim().equals("")){
            Notification.show("Не заполнен метод ввода").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }
}

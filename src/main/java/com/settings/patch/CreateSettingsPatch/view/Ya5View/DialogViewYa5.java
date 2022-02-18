package com.settings.patch.CreateSettingsPatch.view.Ya5View;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.entities.Mode;
import com.settings.patch.CreateSettingsPatch.entities.data.YA5PF;
import com.settings.patch.CreateSettingsPatch.entities.data.YEFPF;
import com.settings.patch.CreateSettingsPatch.view.YefView.GridViewYef;
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

public class DialogViewYa5 {

    private GridViewYa5 gridViewYa5;
    @Getter
    private Dialog dialog;
    // Заголовок
    private H2 headline = new H2("Добавление новой настроки");
    private Paragraph paragraph = new Paragraph(
            "Настройка условия для YA5PF");
    // Поля
    private TextField YA5MTN = new TextField("Метод ввода");
    private TextField YA5XPT = new TextField("Путь");
    private TextField YA5FUN = new TextField("Имя функции");
    private TextField YA5REC = new TextField("Код поля");
    private TextField YA5DLM = new TextField("Дата последнего изменения");
    private TextField YA5UID = new TextField("UUID");

    // Кнопки
    private Button saveButton = new Button("Save",  VaadinIcon.CHECK.create());
    private  Button closeButton = new Button("Close");
    private  Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

    private ComboBox<Mode> mode = new ComboBox<>();

    // Связка полей
    // имена полей и имена переменных в классе связывания должны совподать (а то не будет работать =)) )
    private Binder<YA5PF> binder = new Binder<>(YA5PF.class);

    @Autowired
    DialogViewYa5(GridViewYa5 gridViewYa5){
        this.gridViewYa5 = gridViewYa5;
        // Создание экземпляра объекта диалогового окна
        this.dialog = new Dialog();
        mode.setItems(EnumSet.allOf(Mode.class));

        HorizontalLayout actions = new HorizontalLayout(saveButton, closeButton, deleteButton);
        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph, YA5MTN,YA5XPT,
                YA5FUN,YA5REC,YA5DLM,YA5UID,mode,actions);
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
                gridViewYa5.getGridYa5().getDataProvider().refreshAll();
            }
        });
        deleteButton.addClickListener(e ->{
            delete();
            gridViewYa5.getGridYa5().getDataProvider().refreshAll();
        });
    }

    public void createDialogView(YA5PF ya5pf) {
        binder.bindInstanceFields(ya5pf);
        binder.setBean(ya5pf);
    }

    // TODO: вынести функционал в отдельный класс для использования во всех диалогах
    public void save(){
        final int it = Data.getYA5list().get(gridViewYa5.getProfile()).indexOf(binder.getBean());
        if(it == -1){
            Data.getYA5list().get(gridViewYa5.getProfile()).add(binder.getBean());
        }else{
            Data.getYA5list().get(gridViewYa5.getProfile()).set(it, binder.getBean());
        }
        dialog.close();
    }

    public void delete(){
        Data.getYA5list().get(gridViewYa5.getProfile()).remove(binder.getBean());
        dialog.close();
    }

    private boolean valid(){
        if(YA5MTN.getValue().trim().equals("")){
            Notification.show("Не заполнен метод ввода").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

}

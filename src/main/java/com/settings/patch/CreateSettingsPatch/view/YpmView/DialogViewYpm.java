package com.settings.patch.CreateSettingsPatch.view.YpmView;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.settings.patch.CreateSettingsPatch.view.MainView;
import com.settings.patch.CreateSettingsPatch.entities.YPMPF;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

public class DialogViewYpm {

    private YPMPF ypmpf;
    private GridViewYpm gridViewYpm;
    private Dialog dialog;
    // Заголовок
    private H2 headline = new H2("Добавление новой настроки");
    private Paragraph paragraph = new Paragraph(
            "Настройка условия для YPMPF");
    // Поля
    private TextField conditionName = new TextField("Условие");
    private TextField description = new TextField("Описание");
    private TextArea condition = new TextArea("Выражение");
    // Кнопки
    private Button saveButton = new Button("Save",  VaadinIcon.CHECK.create());
    private  Button closeButton = new Button("Close");
    private  Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

    // Связка полей
    private Binder<YPMPF> binder = new Binder<>(YPMPF.class);

    @Autowired
    public DialogViewYpm(GridViewYpm gridViewYpm) {
        this.gridViewYpm = gridViewYpm;
        // Создание экземпляра объекта диалогового окна
        this.dialog = new Dialog();
        // Стили
          // Заголовок
        headline.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
          // Имя условия
        conditionName.setMaxLength(5);
        conditionName.setWidth("80px");
          // Описание
        description.setMaxLength(35);
          // Условие
        condition.setMaxLength(250);
        // Группировака
        HorizontalLayout actions = new HorizontalLayout(saveButton, closeButton, deleteButton);
        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph,conditionName,description,condition, actions);
        binder.bindInstanceFields(this);

        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);

        dialog.add(dialogLayout);

        // Анализ действий
        closeButton.addClickListener(e -> dialog.close());
        saveButton.addClickListener(e -> save());
        deleteButton.addClickListener(e ->delete());
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void createDialogView(YPMPF ypmpf){
        binder.bindInstanceFields(ypmpf);
        binder.setBean(ypmpf);
    }

    public void save(){
        final int it = Data.getList().indexOf(binder.getBean());
        if(it == -1){
            Data.getList().add(binder.getBean());
        }else{
            Data.getList().set(it, binder.getBean());
        }
        dialog.close();

    }

    public void delete(){
        Data.getList().remove(binder.getBean());
        dialog.close();
    }
}

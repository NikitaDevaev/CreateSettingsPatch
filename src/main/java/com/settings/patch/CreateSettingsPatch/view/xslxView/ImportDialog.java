package com.settings.patch.CreateSettingsPatch.view.xslxView;


import com.settings.patch.CreateSettingsPatch.generateModules.XlsWorker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import lombok.Getter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImportDialog {
    private List<Grid> tableList;
    private XlsWorker xlsWorker = new XlsWorker();
    @Getter
    private Dialog dialog;
    // Заголовок
    private H2 headline = new H2("Импорт настроек");
    private Paragraph paragraph = new Paragraph(
            "Добавление XLS файла");
    // Кнопки
    private Button okButton = new Button("OK");
    private Button closeButton = new Button("Close");

    public ImportDialog(List<Grid> tableList){

        this.tableList = new ArrayList<>(tableList);

        // Создание экземпляра объекта диалогового окна
        this.dialog = new Dialog();
        // Наводим красоту
        headline.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            System.out.println("тут работает");
            xlsWorker.read(buffer.getInputStream(fileName));
        });
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeButton, okButton);
        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph, upload, buttons);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);
        dialog.add(dialogLayout);

        // Обработчики
        closeButton.addClickListener(e -> dialog.close());
        okButton.addClickListener(event -> {
            for(Grid tempGrid: tableList){
                tempGrid.getDataProvider().refreshAll();
                dialog.close();
            }
        });
    }
}

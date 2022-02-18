package com.settings.patch.CreateSettingsPatch.view.xslxView;

import com.settings.patch.CreateSettingsPatch.generateModules.XlsWorker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.Getter;

import java.io.*;

import static com.settings.patch.CreateSettingsPatch.view.DownloadDialog.getStreamResource;

public class ExportDialog {
    private XlsWorker xlsWorker = new XlsWorker();
    @Getter
    private Dialog dialog;
    private Button closeButton = new Button("Отмена");
    private Anchor exportAnchor;
    private Button okButton = new Button("Да");

    private H2 headline = new H2("Экспорт настроек");
    private Paragraph paragraph = new Paragraph(
            "Хотите скачать настройки?");

    public ExportDialog(){
        // Создание экземпляра объекта диалогового окна
        this.dialog = new Dialog();
        // Наводим красоту
        headline.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        this.exportAnchor = new Anchor(getStreamResource(xlsWorker.getName(), xlsWorker.getPath()+xlsWorker.getName()),"");
        exportAnchor.add(okButton);
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeButton, exportAnchor);
        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph, buttons);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);
        dialog.add(dialogLayout);

        // Обработчики
        closeButton.addClickListener(e -> dialog.close());
    }

    public static StreamResource getStreamResource(String fileName, String pathFile){
        StreamResource streamResource = new StreamResource(
                fileName, () ->{
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            try(InputStream is = new FileInputStream(pathFile)){
                byteArrayOutputStream.write(is.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }
        );
        return streamResource;
    }
}

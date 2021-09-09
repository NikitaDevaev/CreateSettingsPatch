package com.settings.patch.CreateSettingsPatch.View;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.Data;
import lombok.Getter;

import java.io.*;
@Data
public class DownloadDialog {

    @Getter
    private Dialog dialog;
    @Getter
    private Anchor anchor;
    private Button close;

    public DownloadDialog(String nameBuildFile, String pathBuildFile){
        this.dialog = new Dialog();
        this.anchor = new Anchor(getStream(nameBuildFile,pathBuildFile),nameBuildFile);
        this.close = new Button("Close", e -> {dialog.close();});
        VerticalLayout verticalLayout = new VerticalLayout(anchor, close);
        dialog.add(verticalLayout);
    }

    public static StreamResource getStream(String fileName, String pathFile){
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

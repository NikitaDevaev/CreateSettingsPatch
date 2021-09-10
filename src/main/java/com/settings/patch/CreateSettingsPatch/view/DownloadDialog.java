package com.settings.patch.CreateSettingsPatch.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.Getter;

import java.io.*;
public class DownloadDialog {

    @Getter
    private Dialog dialog;
    @Getter
    private Anchor anchorBuildFile;
    @Getter
    private Anchor anchorReleaseFile;

    private Button downloadAll;
    private Button close;

    public DownloadDialog(String nameBuildFile, String pathBuildFile, String nameReleaseFile, String pathReleaseFile){
        this.dialog = new Dialog();
        this.anchorBuildFile = new Anchor(getStreamResource(nameBuildFile,pathBuildFile),nameBuildFile);
        this.anchorReleaseFile = new Anchor(getStreamResource(nameReleaseFile,pathReleaseFile),nameReleaseFile);
        this.close = new Button("Close", e -> {dialog.close();});
        this.downloadAll = new Button("Download All");
        HorizontalLayout hl = new HorizontalLayout(this.downloadAll, this.close);
        VerticalLayout verticalLayout = new VerticalLayout(anchorBuildFile, anchorReleaseFile, hl);
        dialog.add(verticalLayout);
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

package com.settings.patch.CreateSettingsPatch.view;

import com.settings.patch.CreateSettingsPatch.generateModules.Generator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.Getter;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloadDialog {

    @Getter
    private Dialog dialog;
    @Getter
    private Anchor anchorBuildFile;
    @Getter
    private Anchor anchorReleaseFile;
    @Getter
    private Anchor anchorDownloadAll;

    private Button buttonDownloadAll;
    private Button close;

    public DownloadDialog(Generator generator){
        this.dialog = new Dialog();
        this.anchorBuildFile = new Anchor(getStreamResource(generator.getNameBuildFile(),generator.getPatchForBuildFile()),generator.getNameBuildFile());
        this.anchorReleaseFile = new Anchor(getStreamResource(generator.getNameReleaseFile(),generator.getPatchForReleaseFile()),generator.getNameReleaseFile());

        // Странное решение...По хорошему переделать бы
        this.anchorDownloadAll = new Anchor(getStreamResource(generator.getNameZipFile(), generator.getPatchForZipFile()),"");
        this.buttonDownloadAll = new Button("Download All");
        anchorDownloadAll.add(buttonDownloadAll);

        this.close = new Button("Close", e -> {
            dialog.close();
        });
        HorizontalLayout hl = new HorizontalLayout(this.anchorDownloadAll ,this.close);

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

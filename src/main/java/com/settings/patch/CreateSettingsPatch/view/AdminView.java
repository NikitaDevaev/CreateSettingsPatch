package com.settings.patch.CreateSettingsPatch.view;

import com.settings.patch.CreateSettingsPatch.data.Data;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("/admin")
@Theme(variant = Lumo.DARK)
public class AdminView extends VerticalLayout {
    private H2 headline = new H2("Hello Admin");
    Button clearButton = new Button("Очистить");
    public AdminView(){
        add(headline, clearButton);
        clearButton.addClickListener(e->{
            Data.getList().clear();
        });
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.kitchen;

import com.codename1.properties.ListProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import static com.codename1.ui.layouts.BorderLayout.CENTER_BEHAVIOR_CENTER;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.util.HashSet;

//TODO when finish to add properties change the idx initilization.
//TODO add picture.
public class LayoutsDemo extends Demo {

    public final Property<Container, LayoutsDemo> layoutContainer = new Property<>("layoutContainer"); 
    public final ListProperty<Component, LayoutsDemo> colorLabelList = new ListProperty<>("colorLabelList");
    
    
    public final PropertyIndex idx = new PropertyIndex(this, "ThemesDemo", super.id, super.image, super.form, super.fatherForm);

    public LayoutsDemo(Form fatherForm, Resources resources){
        super.id.set("Layouts");
        super.image.set(resources.getImage("icon.png"));
        super.fatherForm.set(fatherForm);
        super.resource.set(resources);
        super.form.set(createForm(fatherForm, resources));
    }
    
    private Form createForm(Form fatherForm, Resources resources){
        Form themesForm = new Form("Themes", new BorderLayout());
        Toolbar tb = themesForm.getToolbar();
        
        //add back button
        FontImage backIcon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK,
                UIManager.getInstance().getComponentStyle("BackCommand"));
        tb.addCommandToLeftBar("",backIcon, e->{
            fatherForm.show();
        });
        
        //add info button
        FontImage infoIcon = FontImage.createMaterial(FontImage.MATERIAL_INFO,
                UIManager.getInstance().getComponentStyle("InfoCommand"));
        tb.addCommandToRightBar("",infoIcon, e->{
            Dialog.show("Information", "Layouts allow the UI of Codename One to adapt to the different resolutions and DPI's supported by " +
                        "the various OS's. This is just the tip of the iceberg. Layouts can be nested deeply and there are very" +
                          "complex layouts such as MiG, Group, GridBag etc. that aren't fully represented here...", "OK", null);
        });
        
        initContentPane(themesForm.getContentPane());
        
        return themesForm;
    }
    
    
    private void initContentPane(Container contentPane){
        
        Button flow = new Button("Flow Layout");
        flow.addActionListener(e-> {
            layoutContainer.get().setLayout(new FlowLayout());
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button flowCenter = new Button("Flow Center Layout");
        flowCenter.addActionListener(e-> {
            layoutContainer.get().setLayout(new FlowLayout(Component.CENTER));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button border = new Button("border Layout");
        border.addActionListener(e-> {
            layoutContainer.get().setLayout(new BorderLayout());
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button absoluteBorder = new Button("Absolute Border Layout");
        absoluteBorder.addActionListener(e-> {
            layoutContainer.get().setLayout(new BorderLayout(CENTER_BEHAVIOR_CENTER));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });

        Button boxX = new Button("Box X Layout");
        boxX.addActionListener(e-> {
            layoutContainer.get().setLayout(new BoxLayout(BoxLayout.X_AXIS));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
  
        Button boxY = new Button("Box Y Layout");
        boxY.addActionListener(e-> {
            layoutContainer.get().setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button grid = new Button("Grid Layout");
        grid.addActionListener(e-> {
            layoutContainer.get().setLayout(new GridLayout(1, 1));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button table = new Button("Table Layout");
        table.addActionListener(e-> {
            layoutContainer.get().setLayout(new TableLayout(2, 4));
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        Button layered = new Button("Layered Layout");
        layered.addActionListener(e-> {
            layoutContainer.get().setLayout(new LayeredLayout());
            layoutContainer.get().setShouldCalcPreferredSize(true);
            layoutContainer.get().animateLayout(1000);
        });
        
        
        Container buttonList = BoxLayout.encloseY(flow, flowCenter, border, absoluteBorder, boxX, boxY, grid, table, layered);
        
        
//        if (colorLabelList != null) {
//            colorLabelList.add(new Label("             ", "Costum1"));
//            colorLabelList.add(new Label("             ", "Costum2"));
//            colorLabelList.add(new Label("             ", "Costum3"));
//            colorLabelList.add(new Label("             ", "Costum4"));
//            colorLabelList.add(new Label("             ", "Costum5"));
//        }
//
//        Container colorsContainer = BoxLayout.encloseY(colorLabelList.get(0),
//                                                       colorLabelList.get(1),
//                                                       colorLabelList.get(2),
//                                                       colorLabelList.get(3),
//                                                       colorLabelList.get(4));

        Container colorsContainer = BoxLayout.encloseY(new Label(" ", "Costum1"),
                                                       new Label(" ", "Costum2"),
                                                       new Label(" ", "Costum3"),
                                                       new Label(" ", "Costum4"),
                                                       new Label(" ", "Costum5")
        );

        layoutContainer.set(colorsContainer);
        contentPane.add(BorderLayout.NORTH, colorsContainer);
        contentPane.add(BorderLayout.SOUTH, buttonList);
    }
    
    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
}

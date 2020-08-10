/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.kitchen;

import com.codename1.components.ScaleImageLabel;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author serge
 */
public abstract class Demo implements PropertyBusinessObject {
    public final Property<String, Demo> id = new Property<>("name"); 
    public final Property<Image, Demo> image = new Property<>("image");
    public final Property<Form, Demo> form = new Property<>("form");
    public final Property<Form, Demo> fatherForm = new Property<>("fatherForm");
    public final Property<Resources , Demo> resource = new Property<>("resources");
    
    
    public Component makeComponent(){
        ScaleImageLabel imageLabel = new ScaleImageLabel(image.get());
        //TODO remove that 300 300 hardcoded params.
        imageLabel.setIcon(imageLabel.getIcon().scaled(300, 300));
        Button button = new Button(id.get());
        button.addActionListener(e-> {
            form.get().show();
        });
        
        Container themeMainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        themeMainWindowComponent.setUIID("MainWindowComponent");
        return themeMainWindowComponent;
    }
}

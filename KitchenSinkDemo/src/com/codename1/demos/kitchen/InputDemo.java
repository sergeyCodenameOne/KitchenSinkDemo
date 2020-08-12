/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.demos.kitchen;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class InputDemo extends Demo{
    
    public InputDemo(Form parentForm){
        super.parentForm = parentForm;
        id = "Input";
        demoComponentImage = getGlobalResources().getImage("icon.png").scaled(CommonBehavior.IMAGE_WIDTH, CommonBehavior.IMAGE_HEIGHT);
    }
    
    public Component makeDemo(){
        ScaleImageLabel imageLabel = new ScaleImageLabel(demoComponentImage);
        Button button = new Button(id);
        button.addActionListener(e-> {
            createForm().show();
        });
        
        Container mainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        mainWindowComponent.setUIID("DemoComponent");
        return mainWindowComponent;
    }
    
    private Form createForm(){
        Form inputForm = new Form("Input", new BorderLayout());
        Toolbar toolBar = inputForm.getToolbar();
        
         //add back button
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->{
            parentForm.show();
        });
        
        //add info button
        toolBar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", "Demonstrates basic usage of input facilities, device orientation behavior as well as adapting the UI to tablets." +
                        " This demo shows off a typical form with user information, different keyboard types, ability to capture an " +
                        "avatar image and style it etc.", "OK", null);
        });
        // Set UIID for background image.
        Container contentPane = inputForm.getContentPane();
        contentPane.setUIID("InputDemoContentPane");
        
        TextModeLayout textLayout = new TextModeLayout(6, 1);
        Container textFields = new Container(textLayout);
        
        // Add some text fields to the page
        TextComponent name = new TextComponent().labelAndHint("Name");
        FontImage.setMaterialIcon(name.getField().getHintLabel(), FontImage.MATERIAL_PERSON);
        
        TextComponent email = new TextComponent().labelAndHint("E-mail").constraint(TextArea.EMAILADDR);
        FontImage.setMaterialIcon(email.getField().getHintLabel(), FontImage.MATERIAL_EMAIL);
        
        TextComponent password = new TextComponent().labelAndHint("Password").constraint(TextArea.PASSWORD);
        FontImage.setMaterialIcon(password.getField().getHintLabel(), FontImage.MATERIAL_LOCK);
        
        TextComponent bio = new TextComponent().labelAndHint("Bio").multiline(true);
        FontImage.setMaterialIcon(bio.getField().getHintLabel(), FontImage.MATERIAL_LIBRARY_BOOKS);
        
        PickerComponent birthday = PickerComponent.createDate(null).label("Birthday");
        
        textFields.add(name);
        textFields.add(birthday);
        textFields.add(email);
        textFields.add(password);
        textFields.add(bio);
        inputForm.setEditOnShow(name.getField());

        Button saveButton = new Button("save");
        saveButton.addActionListener(e ->{
            //TODO add clear all fields and and a toast bar;
        });
        
        Container textFieldsAndSaveButton = BorderLayout.south(saveButton);
        textFieldsAndSaveButton.add(BorderLayout.CENTER, textFields);
        
        //TODO move to css
        textFieldsAndSaveButton.getAllStyles().setMargin(100, 100, 100, 100);
        textFieldsAndSaveButton.getAllStyles().setBgTransparency(255);
        inputForm.add(BorderLayout.CENTER, textFieldsAndSaveButton);
        
        return inputForm;
    }

}

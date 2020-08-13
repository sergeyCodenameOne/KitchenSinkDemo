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

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.OnOffSwitch;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.Switch;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import com.codename1.ui.Button;
import com.codename1.ui.CN1Constants;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.TextModeLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import static java.lang.Thread.sleep;

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
        
        // Toolbar add back button
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->{
            parentForm.show();
        });
        
        // Toolbar add info button 
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
        
        Switch joinEmailSwitch = new Switch(); 
        Label joinEmailLabel = new Label("Join Mailing List");
        Container joinEmailList = BorderLayout.centerCenterEastWest(null, joinEmailSwitch, joinEmailLabel);
        
        textFields.add(name);
        textFields.add(birthday);
        textFields.add(email);
        textFields.add(password);
        textFields.add(bio);
        textFields.add(joinEmailList);
        inputForm.setEditOnShow(name.getField());

        Button saveButton = new Button("save");
        
        // Add validation to the save Button
        Validator saveValidation = new Validator();
        saveValidation.addConstraint(email, RegexConstraint.validEmail());
        saveValidation.addSubmitButtons(saveButton);
        
        saveButton.addActionListener(e ->{
            // Show saving status
            Status savingStatus = ToastBar.getInstance().createStatus();
            savingStatus.setMessage("Saving");
            savingStatus.setExpires(3000);
            savingStatus.setShowProgressIndicator(true);
            savingStatus.show();
            
            // Show saved 
            Status saved = ToastBar.getInstance().createStatus();
            saved.setMessage("Input was successfully saved");
            saved.showDelayed(4000);  
            saved.setExpires(2000);
            System.out.println(saved.getUiid());
            saved.show();
            
            name.getField().clear();
            email.getField().clear();
            bio.getField().clear();
            password.getField().clear();
        });
        
        Container textFieldsAndSaveButton = BorderLayout.south(saveButton);
        textFieldsAndSaveButton.add(BorderLayout.CENTER, textFields);
        
        textFieldsAndSaveButton.setUIID("InputDemoTextFieldsAndSaveButton");
        inputForm.add(BorderLayout.CENTER, textFieldsAndSaveButton);
        
        // Create button for the camera
        Image defaultImage = FontImage.createMaterial(FontImage.MATERIAL_CAMERA, "InputPicture", 8);
        Image circleMaskImage = getGlobalResources().getImage("circle-mask.png").scaled(Display.getInstance().convertToPixels(8),
                                                                                        Display.getInstance().convertToPixels(8));
//        //TODO fix the mask;
//        Object mask = circleMaskImage.createMask();
//        defaultImage.applyMask(mask);
        Button cameraButton = new Button("");
        cameraButton.getAllStyles().setPadding(0, 0, 0, 0);
        
        cameraButton.setIcon(defaultImage);
        cameraButton.addActionListener(e-> {
            if(Dialog.show("Camera or Gallery", "Would you like to use the camera or the gallery for the picture?", "Camera", "Gallery")) {
                try {
                    Image capturedImage = Image.createImage(Capture.capturePhoto(cameraButton.getHeight(), cameraButton.getWidth()));    
                    cameraButton.setIcon(capturedImage);
                } catch(IOException error) {
                    ToastBar.showErrorMessage("An error occured while loading the image");
                }
            } else {
                Display.getInstance().openGallery(ee -> {
                    if(ee.getSource() != null) {
                        try {
                            Image img = Image.createImage((String)ee.getSource()).scaled(cameraButton.getIcon().getWidth(), cameraButton.getIcon().getHeight());
                            cameraButton.setIcon(img);
                        } catch(IOException err) {
                            ToastBar.showErrorMessage("An error occured while loading the image: " + err);
                        }
                    }                    
                }, CN1Constants.GALLERY_IMAGE);
            }
            inputForm.revalidate();
        });
        
//            if(Dialog.show("Camera or Gallery", "Would you like to use the camera or choose picture from the gallery", "Camera", "Gallery")){
//                try{
//                    Image capturedImage = Image.createImage(Capture.capturePhoto(cameraButton.getWidth(), cameraButton.getHeight()));
//                    cameraButton.setIcon(capturedImage.applyMask(cameraButton.getIcon().createMask()));
//                }catch(IOException exception){
//                    ToastBar.showErrorMessage("An error occured while loading the image: " + exception.getMessage());
//                 }     
//            }else{
//                Display.getInstance().openGallery(response -> {
//                    if(response.getSource() != null) {
//                        try {
//                            Image img = Image.createImage((String)response.getSource()).fill(cameraButton.getWidth(), cameraButton.getHeight());
//                            cameraButton.setIcon(img.applyMask(cameraButton.getIcon().createMask()));
//                        } catch(IOException exception) {
//                            ToastBar.showErrorMessage("An error occured while loading the image: " + exception.getMessage());
//                        }
//                    }
//                }, CN1Constants.GALLERY_IMAGE);
//            }
//        });
        
        inputForm.getLayeredPane().addComponent(cameraButton);
        FlowLayout ll = (FlowLayout)(inputForm.getLayeredPane().getLayout());
        ll.setAlign(Component.CENTER);

        return inputForm;
    }

}

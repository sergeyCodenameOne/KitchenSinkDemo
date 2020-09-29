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
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.util.Resources.getGlobalResources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;

public class LabelsDemo extends Demo {
    
    public LabelsDemo(Form parentForm) {
        init("Labels", getGlobalResources().getImage("demo-labels.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("label.png"),
                                                                "Label",
                                                                "Allows displaying a single line of text and",
                                                                "icon (both optional) with different alignment options. This class is a base class for several "+ 
                                                                "components allowing them to declare alignment/icon appearance universally.", e->{                                                       
                                                                    showDemo("Label", createLabelDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("span-label.png"),
                                                                "Span Label",
                                                                "A multi line label component that can be",
                                                                "easily localized, this is simply based on a text area combined with a label.", e->{
                                                                   
                                                                    showDemo("SpanLabel", createSpanLabelDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("scale-image-label.png"),
                                                                "Scale Image Label",
                                                                "Label that simplifies the usage of scale to",
                                                                "fill/fit. This is effectively equivalent to just setting "+
                                                                "the style image on a label but more convenient for some special circumstances\n\nOne major difference is "+
                                                                "that preferred size equals the image in this case. The default UIID for this component is label", e->{
                                                                    showDemo("Scale image label", createScaleImageLabelDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("floating-hint.png"),
                                                                "Text Component",
                                                                "Text Component Encapsulates a text field",
                                                                "and label into a single component. This allows the UI to adapt for IOS/Android behavior differences and "+
                                                                "support features like floating hint when necessary.\n\nIt also includes platform specific error handling "+
                                                                "logic. It is highly recommended to use text component in the context of a TextModeLayout this allows "+
                                                                "the layout to implicitly adapt to the on-top mode and use a box layout Y mode for iOS and other platforms. "+
                                                                "This class supports several theme constants.", e->{
                                                                    
                                                                    showDemo("Text Component", createTextComponentContainer());
                                                                }));
        
        return demoContainer;
    }
    
    private Container createLabelDemo(){
        Container cnt1 = BoxLayout.encloseY(new Label("Text Label:", "DemoLabel"),
                new Label("label", "DemoLabel"));
        cnt1.setUIID("LabelContainer");

        Container cnt2 = BoxLayout.encloseY(new Label("Image Label:", "DemoLabel"),
                new Label(getGlobalResources().getImage("code-name-one-icon.png"), "DemoLabel"));
        cnt2.setUIID("LabelContainer");

        Container cnt3 = BoxLayout.encloseY(new Label("text and image Label:", "DemoLabel"),
                new Label("label", getGlobalResources().getImage("code-name-one-icon.png"), "DemoLabel"));
        cnt3.setUIID("LabelContainer");

        return BoxLayout.encloseY(cnt1, cnt2, cnt3);
    }
    
    private Container createSpanLabelDemo(){
        Container cnt1 = BoxLayout.encloseY(new Label("SpanLabel:", "DemoLabel"), 
                new SpanLabel("A multi line label component that can be easily localized, this is simply based on a text area combined with a label.", "DemoLabel"));
        cnt1.setUIID("LabelContainer");
        
        SpanLabel labelWithIconWest = new SpanLabel("A multi line label component that can be easily localized, this is simply based on a text area combined with a label.", "DemoLabel");
        labelWithIconWest.setMaterialIcon(FontImage.MATERIAL_INFO);
        labelWithIconWest.setIconUIID("DemoLabel");
        labelWithIconWest.setIconPosition("West");
        Container cnt2 = BoxLayout.encloseY(new Label("SpanLabel with icon (West):", "DemoLabel"), labelWithIconWest);
        cnt2.setUIID("LabelContainer");        
        
        SpanLabel labelWithIconNorth = new SpanLabel("A multi line label component that can be easily localized, this is simply based on a text area combined with a label.", "DemoLabel");
        labelWithIconNorth.setMaterialIcon(FontImage.MATERIAL_INFO);
        labelWithIconNorth.setIconUIID("DemoLabel");
        labelWithIconNorth.setIconPosition("North");
        Container cnt3 = BoxLayout.encloseY(new Label("SpanLabel with icon (North):", "DemoLabel"), labelWithIconNorth);
        cnt3.setUIID("LabelContainer");
        
        return BoxLayout.encloseY(cnt1, cnt2, cnt3);
    }
    
    private Container createScaleImageLabelDemo(){
        Container labelContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        labelContainer.add(new Label("Scale image label:", "DemoLabel"));
        ScaleImageLabel imageLabel = new ScaleImageLabel(getGlobalResources().getImage("scale-image-label.png")){
            @Override
            protected Dimension calcPreferredSize(){
                Dimension d = super.calcPreferredSize();
                d.setHeight(Display.getInstance().getDisplayHeight() / 7);
                return d;
            }
        };
        imageLabel.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        labelContainer.add(imageLabel);
        labelContainer.add(new Label("   "));
        labelContainer.add(new Label("3 Scale image labels", "DemoLabel"));
        labelContainer.add(new Label("(auto scaled to fit available space)", "GreyLabel"));
        
        Container threeImagesContainer = new Container(new GridLayout(1, 3));
        ScaleImageLabel label1 = new ScaleImageLabel(getGlobalResources().getImage("scale-image-label.png"));
        label1.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        label1.setUIID("DemoScaleImageLabel");

        ScaleImageLabel label2 = new ScaleImageLabel(getGlobalResources().getImage("scale-image-label.png"));
        label2.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        label2.setUIID("DemoScaleImageLabel");

        ScaleImageLabel label3 = new ScaleImageLabel(getGlobalResources().getImage("scale-image-label.png"));
        label3.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        label3.setUIID("DemoScaleImageLabel");

        threeImagesContainer.addAll(label1, label2, label3);                           
        labelContainer.add(threeImagesContainer);
        labelContainer.setUIID("LabelContainer");
                                                                                                                                  
        return BoxLayout.encloseY(labelContainer);
    }
    
    private Container createTextComponentContainer(){
        Container textContainer = new Container(new LayeredLayout());
        // Set UIID for background image.
        textContainer.setUIID("InputContainer");
        
        Container textFields = new Container(new TextModeLayout(6, 1));

        // Add some text fields to the page
        TextComponent name = new TextComponent().labelAndHint("Name");
        FontImage.setMaterialIcon(name.getField().getHintLabel(), FontImage.MATERIAL_PERSON);

        TextComponent email = new TextComponent().labelAndHint("E-mail").constraint(TextArea.EMAILADDR);
        FontImage.setMaterialIcon(email.getField().getHintLabel(), FontImage.MATERIAL_EMAIL);

        TextComponent password = new TextComponent().labelAndHint("Password").constraint(TextArea.PASSWORD);
        FontImage.setMaterialIcon(password.getField().getHintLabel(), FontImage.MATERIAL_LOCK);

        TextComponent bio = new TextComponent().labelAndHint("Bio").multiline(true);
        FontImage.setMaterialIcon(bio.getField().getHintLabel(), FontImage.MATERIAL_LIBRARY_BOOKS);

        textFields.add(name);
        textFields.add(email);
        textFields.add(password);
        textFields.add(bio);

        Button saveButton = new Button("Save", "InputSaveButton");

        // Add validation to the save Button
        Validator saveValidation = new Validator();
        saveValidation.addConstraint(email, RegexConstraint.validEmail());
        saveValidation.addSubmitButtons(saveButton);

        saveButton.addActionListener(ee ->{
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
            saved.show();

            name.getField().clear();
            email.getField().clear();
            bio.getField().clear();
            password.getField().clear();
        });

        Container textFieldsAndSaveButton = BorderLayout.south(saveButton);
        textFieldsAndSaveButton.setUIID("TextComponents");
        textFieldsAndSaveButton.add(BorderLayout.CENTER, textFields);
        
        return BorderLayout.center(textFieldsAndSaveButton);
    }
}

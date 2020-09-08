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

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.ImageIO;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.OutputStream;


public class ButtonsDemo extends Demo{

    public ButtonsDemo(Form parentForm) {
        init("Buttons", getGlobalResources().getImage("demo-buttons.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("buttons.png"),
                                                                "Buttons",
                                                                "Button is the base class for several UI",
                                                                "widgets allowing clickability. It has 3 States: rollover, pressed and the default state. Button can also "+
                                                                "have an ActionListernet that react when the button is clicked or handle actions via a Command.Button UIID by "+
                                                                "default.", e->{
                                                                    showDemo("Buttons", createButtonsDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("span-button.png"),
                                                                "Span Buttons",
                                                                "A complex button similar to MultiButton",
                                                                "that breaks lines automatically and looks like a regular button(more or less). Unlike the multi button the "+
                                                                "span buttons has the UIID style of a button.", e->{
                                                                    showDemo("Span Buttons", createSpanButtonsDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("multi-buttons.png"),
                                                                "Multi Buttons",
                                                                "A powerful button like component that",
                                                                "allows multiple rows/and an icon to be added every row/icon can have its own UIID.\n\nInternally the "+
                                                                "multi-button is a container with a lead component. Up to 4 rows are supported.", e->{
                                                                    showDemo("Multi Buttons", createMultiButtonsDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("scale-image-label.png"),
                                                                "Scale Image Button",
                                                                "Button that simplifies the usage of scale to",
                                                                "fill/fit. This is effectively equivalent to just setting the style image on a button but more convenient "+
                                                                "for some special circumstances.\n\nOne major difference is that preferred size equals the image in this case.", e->{
                                                                   showDemo("Scale Image Button", createScaleImageButton());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("floating-action-button.png"),
                                                                "Floating Action Button",
                                                                "Floating action buttons are a material design",
                                                                "element used to promote a special action in a form. They are represented as a floating circle with a "+
                                                                "flat icon floating above the UI typically in the bottom right area.", e->{
                                                                   showDemo("Floating Action Button", createFloatingActionButtonDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("share-button.png"),
                                                                "Share Button",
                                                                "The share button allows sharing a String",
                                                                "or an image either thru the defined sharing services or thru native OS sharing support. On Android & iOS the "+
                                                                "native sharing API is invoked for this class.\n\nThe code below demonstrates image sharing, notice that an "+
                                                                "image must be stored using the FileSystemStorage API and shouldn't use a different API like Storage.", e->{
                                                                    showDemo("Share Button", createShareButtonDemo());
                                                                }));
        return demoContainer;
    }
    
        
    private Container createButtonsDemo(){ 
        Button firstButton = new Button("Button", "ButtonDemo");
        firstButton.addActionListener(e-> ToastBar.showInfoMessage("first Button has pressed") );
        
        Button secondButton = new Button("", FontImage.MATERIAL_INFO, "ButtonDemo");
        secondButton.addActionListener(e-> ToastBar.showInfoMessage("second Button has pressed") );
        
        Button thirdButton = new Button("Button", FontImage.MATERIAL_INFO, "ButtonDemo");
        thirdButton.addActionListener(e-> ToastBar.showInfoMessage("third Button has pressed") );

        Container demoContainer =  BoxLayout.encloseY(new Label("button with text:", "ComponentDemoLabel"),
                                                        firstButton,
                                                        new Label("button with icon:", "ComponentDemoLabel"),
                                                        secondButton,
                                                        new Label("button with text and icon:", "ComponentDemoLabel"),
                                                        thirdButton);
        return demoContainer;
    }
    
    private Container createSpanButtonsDemo(){ 
        SpanButton button = new SpanButton("A complex button similar to MultiButton that breaks lines automatically and looks like "+
                                                "a regular button(more or less). Unlike the multi button the "+
                                                "span buttons has the UIID style of a button.", "WhiteText");
        button.setUIID("ButtonDemo");
        button.addActionListener(e-> ToastBar.showInfoMessage("Button has pressed") );
        return BoxLayout.encloseY( new Label("SpanButton:", "ComponentDemoLabel"), button);
    }
    
    private Container createMultiButtonsDemo(){ 
        MultiButton twoLinesNoIcon = new MultiButton("MultiButton");
        twoLinesNoIcon.setTextLine2("Line 2");
        
        Image emblem = FontImage.createMaterial(FontImage.MATERIAL_ARROW_RIGHT, UIManager.getInstance().getComponentStyle("MultiLineEmblem"));
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_INFO, UIManager.getInstance().getComponentStyle("MultiLineIcon"));

        MultiButton oneLineIconEmblem = new MultiButton("Icon + Emblem");
        oneLineIconEmblem.setIcon(icon);
        oneLineIconEmblem.setEmblem(emblem);
        MultiButton twoLinesIconEmblem = new MultiButton("Icon + Emblem");
        twoLinesIconEmblem.setIcon(icon);
        twoLinesIconEmblem.setEmblem(emblem);
        twoLinesIconEmblem.setTextLine2("Line 2");

        MultiButton twoLinesIconEmblemHorizontal = new MultiButton("Icon + Emblem");
        twoLinesIconEmblemHorizontal.setIcon(icon);
        twoLinesIconEmblemHorizontal.setEmblem(emblem);
        twoLinesIconEmblemHorizontal.setTextLine2("Line 2 Horizontal");
        twoLinesIconEmblemHorizontal.setHorizontalLayout(true);

        MultiButton twoLinesIconCheckBox = new MultiButton("CheckBox");
        twoLinesIconCheckBox.setIcon(icon);
        twoLinesIconCheckBox.setCheckBox(true);
        twoLinesIconCheckBox.setTextLine2("Line 2");

        MultiButton fourLinesIcon = new MultiButton("With Icon");
        fourLinesIcon.setIcon(icon);
        fourLinesIcon.setTextLine2("Line 2");
        fourLinesIcon.setTextLine3("Line 3");
        fourLinesIcon.setTextLine4("Line 4");

        Container demoContainer = BoxLayout.encloseY(oneLineIconEmblem,
                                                    twoLinesNoIcon,
                                                    twoLinesIconEmblem,
                                                    twoLinesIconEmblemHorizontal,
                                                    twoLinesIconCheckBox,
                                                    fourLinesIcon);
        demoContainer.setUIID("MultiButtonsContainer");
        return demoContainer;
    }
    
    private Container createShareButtonDemo(){
        ShareButton textShare = new ShareButton();
        textShare.setUIID("ButtonShareDemo");
        textShare.setTextToShare("Hello there");
        textShare.setText("share text");
        
        String imagePath = FileSystemStorage.getInstance().getAppHomePath() + "icon.png";
        Image imageToShare = getGlobalResources().getImage("codename-one-icon.png");
        
        try {
            OutputStream os = FileSystemStorage.getInstance().openOutputStream(imagePath);
            ImageIO.getImageIO().save(imageToShare, os, ImageIO.FORMAT_PNG, 1f);
            os.close();
        }
        catch (Exception error) {
            Log.e(error);
        }
        
        ShareButton imageShare = new ShareButton();
        imageShare.setUIID("ButtonShareDemo");
        imageShare.setText("share image");
        imageShare.setImageToShare(imagePath, "image/png");

        return BoxLayout.encloseY(textShare, imageShare);
    }
    
    private Container createScaleImageButton(){
        TableLayout tableLayout = new TableLayout(2, 2);
        Container demoContainer = new Container(tableLayout);
        
        Style s = UIManager.getInstance().getComponentStyle("ButtonDemo");
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_WARNING, s);
        ScaleImageLabel fillLabel = new ScaleImageLabel(icon);
        fillLabel.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        ScaleImageButton fillButton = new ScaleImageButton(icon);
        fillButton.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        demoContainer.add(tableLayout.createConstraint().widthPercentage(20), new ScaleImageButton(icon)).
                add(tableLayout.createConstraint().widthPercentage(80), new Label("<- 20% of the screen", "ComponentDemoLabel")).
                add(new SpanLabel("80% of the screen->", "ComponentDemoLabel")).
                add(new ScaleImageButton(icon)).
                add(fillLabel).
                add(new Label("<-image fill", "ComponentDemoLabel")).
                add(new Label("")).
                add(fillButton);
        return demoContainer;
    }
    
    private Container createFloatingActionButtonDemo(){
        Container demoContainer = new Container(new BorderLayout());
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.createSubFAB(FontImage.MATERIAL_PEOPLE, "");
        fab.createSubFAB(FontImage.MATERIAL_IMPORT_CONTACTS, "");
        return fab.bindFabToContainer(demoContainer);

    }
}

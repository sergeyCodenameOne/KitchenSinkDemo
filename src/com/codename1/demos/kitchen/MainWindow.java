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
import static com.codename1.ui.CN.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;


public class MainWindow {
    
    private Container tabletContentPane; 
    
    public Form buildForm(){
        Form mainWindow = new Form("Kitchen Sink", new BorderLayout());

        //create demos
        Demo[] demos = {new LayoutsDemo(mainWindow),
                        new InputDemo(mainWindow),
                        new ContactsDemo(mainWindow),
                        new VideoDemo(mainWindow),
                        new SalesDemo(mainWindow)
                        
        };
        
        if (isTablet()){
            Toolbar.setPermanentSideMenu(true);
            tabletContentPane = mainWindow.getContentPane();
            tabletContentPane.add(BorderLayout.CENTER, demos[0].createContentPane());
            initToolbarForTablet(mainWindow.getToolbar(), demos);
            return mainWindow;
            
        }else{
            mainWindow.setLayout(new GridLayout(3));
            initToolbarForPhone(mainWindow.getToolbar());

            for(Demo demo : demos){
                mainWindow.add(createDemoComponentForPhone(demo));
            }
            return mainWindow;
        }
    }
    
    private void initToolbarForPhone(Toolbar toolbar){
        toolbar.setUIID("MyToolbar");
        Label sideBarTitle = new Label("KitchenSink");

        Container sideBarTitleArea = BorderLayout.center(sideBarTitle);
        toolbar.addComponentToSideMenu(sideBarTitleArea);

        // add side bar components        
        toolbar.addMaterialCommandToSideMenu("CodeNameOne.com", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/")); 
        toolbar.addMaterialCommandToSideMenu("Getting Started", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/files/developer-guide.pdf"));
        toolbar.addMaterialCommandToSideMenu("JavaDoc (REFERENCE)", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/javadoc/"));
        toolbar.addMaterialCommandToSideMenu("Source Code", FontImage.MATERIAL_WEB, e -> execute("https://github.com/codenameone/KitchenSink"));

        if(isNativeShareSupported() && getAppstoreURL() != null) {
            toolbar.addMaterialCommandToSideMenu("Spread the Word!", FontImage.MATERIAL_SHARE, e -> {
                share("Check out the kitchen sink app from Codename One: " + getAppstoreURL(), null, null);
            });
        }
        toolbar.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {
                Dialog.show("About", "KitchenSink provides an overview of the core Codename One capaiblities. "
                + "Codename One allows Java developers to create native mobile applications that work everywhere!", "OK", null);
        });
    }
    
    private void initToolbarForTablet(Toolbar toolbar, Demo[] demos){
        Label sideBarTitle = new Label("KitchenSink");
                
        Container sideBarTitleArea = BorderLayout.center(sideBarTitle);
        toolbar.addComponentToSideMenu(sideBarTitleArea);
        
        for (Demo demo : demos){
            toolbar.addComponentToLeftSideMenu(createDemoComponentForTablet(demo));
        }
    }
    
    private Component createDemoComponentForTablet(Demo demo){
        Button demoComponent = new Button(demo.getDemoId());
        demoComponent.setUIID("TabletSideNavigationButton");
        Image demoImage = demo.getDemoImage().fill(CommonBehavior.getDemoImageWidthForTablet(), CommonBehavior.getDemoImageWidthForTablet());
        demoImage = demoImage.applyMask(CommonBehavior.getRoundMask(demoImage.getWidth()));
        demoComponent.setIcon(demoImage);
        demoComponent.addActionListener(e->{
            tabletContentPane.replace(tabletContentPane.getComponentAt(0), demo.createContentPane(), CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 200));
        });
        return demoComponent;
    }
    
    private Component createDemoComponentForPhone(Demo demo){
        ScaleImageLabel imageLabel = new ScaleImageLabel(demo.getDemoImage().scaled(CommonBehavior.getDemoImageWidthForPhone(), CommonBehavior.getDemoImageHeightForPhone()));
        Button button = new Button(demo.getDemoId());
        button.addActionListener(e-> createAndShowForm(demo));
        
        Container demoComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        demoComponent.setUIID("DemoComponent");
        
        return demoComponent;
    }
    
    private String getAppstoreURL() {
        if(getPlatformName().equals("ios")) {
            return "https://itunes.apple.com/us/app/kitchen-sink-codename-one/id635048865";
        }
        if(getPlatformName().equals("and")) {
            return "https://play.google.com/store/apps/details?id=com.codename1.demos.kitchen";
        }
        return null;
    }
    
    private void createAndShowForm(Demo demo){
        Form demoForm = new Form(demo.getDemoId(), new BorderLayout());
        Toolbar toolbar = demoForm.getToolbar();
        
        // Toolbar add info and back buttons.
        toolbar.setBackCommand("", e-> demo.getParentForm().show());
        toolbar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", demo.getDemoId(), "OK", null);
        });
        
        demoForm.add(BorderLayout.CENTER, demo.createContentPane());
        demoForm.show();
    }
}
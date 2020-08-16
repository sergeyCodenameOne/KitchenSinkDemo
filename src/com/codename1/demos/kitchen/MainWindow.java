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


import static com.codename1.ui.CN.execute;
import static com.codename1.ui.CN.getPlatformName;
import static com.codename1.ui.CN.isNativeShareSupported;
import static com.codename1.ui.CN.share;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;


public class MainWindow {
    
    public static Form buildForm(){
        Form mainWindow = new Form("Kitchen Sink", new GridLayout(3));
        mainWindow.getContentPane().setUIID("MainWindowContentPane");
        initToolBar(mainWindow.getToolbar());

        //create demos
        Demo layoutsDemo = new LayoutsDemo(mainWindow);   
        Demo inputDemo = new InputDemo(mainWindow);
        Demo contactsDemo = new ContactsDemo(mainWindow);
        
        //add demos components
        mainWindow.add(inputDemo.createDemo());
        mainWindow.add(layoutsDemo.createDemo());
        mainWindow.add(contactsDemo.createDemo());
        
        return mainWindow;
    }
    
    //TODO add search and reorder butten
    private static void initToolBar(Toolbar toolbar){
        toolbar.setUIID("MyToolbar");
        Label sideBarTitle = new Label("KitchenSink Demo");
        sideBarTitle.setUIID("SideBarTitle");
                
        Container sideBarTitleArea = BorderLayout.center(sideBarTitle);
        sideBarTitleArea.setUIID("SideBarTitleArea");
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
    
    private static String getAppstoreURL() {
        if(getPlatformName().equals("ios")) {
            return "https://itunes.apple.com/us/app/kitchen-sink-codename-one/id635048865";
        }
        if(getPlatformName().equals("and")) {
            return "https://play.google.com/store/apps/details?id=com.codename1.demos.kitchen";
        }
        return null;
    }
}
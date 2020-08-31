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

import com.codename1.components.MultiButton;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;


public class MainWindow {
       
    public Form buildForm(){
        Form mainWindow = new Form("Components", new GridLayout(7, 2));
        Container contentPane = mainWindow.getContentPane();
        contentPane.setUIID("MainWindowContainer");
        contentPane.setScrollableY(true);
        Toolbar tb = mainWindow.getToolbar();
        tb.setUIID("MainWindowToolbar");
        tb.getTitleComponent().setUIID("MainWindowTitle");

        //create demos
        Demo[] demos = {new ChartsDemo(mainWindow),
                        new AdvancedDemo(mainWindow),
                        new MapsDemo(mainWindow),
                        new LabelsDemo(mainWindow),
                        new ButtonsDemo(mainWindow),
                        new TogglesDemo(mainWindow),
                        new ToggleListDemo(mainWindow),
                        new SelectionDemo(mainWindow),
                        new TextFieldDemo(mainWindow),
                        new ContainersDemo(mainWindow),
                        new DialogDemo(mainWindow),
                        new ProgressDemo(mainWindow),
                        new ToolbarDemo(mainWindow),
                        new MediaDemo(mainWindow)
        };
        
        if (isTablet()){
            mainWindow.setLayout(new GridLayout(5, 3));
        }
        
        for(Demo demo : demos){
            mainWindow.add(createDemoComponent(demo));
        }
        
        return mainWindow;
    }
    
    private Component createDemoComponent(Demo demo){
        MultiButton demoComponent = new MultiButton(demo.getDemoId());
        demoComponent.setUIID("VideoComponent");
        demoComponent.setIcon(demo.getDemoImage().scaled(CommonBehavior.getDemoImageWidth(), CommonBehavior.getDemoImageHeight()));
        demoComponent.setIconPosition("North");
        demoComponent.addActionListener(e-> createAndShowForm(demo));
        demoComponent.setIconUIID("DemoComponentIcon");
        demoComponent.setUIIDLine1("MainWindowDemoName");
        return demoComponent;
    }
    
    private void createAndShowForm(Demo demo){
        Form demoForm = new Form(demo.getDemoId(), new BorderLayout());
        Toolbar toolbar = demoForm.getToolbar();
        toolbar.setUIID("DemoToolbar");
        toolbar.getTitleComponent().setUIID("DemoTitle");
        
        // Toolbar add source and back buttons.
        Style iconStyle = new Style();
        iconStyle.setFgColor(0x157EFB);
        Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, iconStyle),
                e-> demo.getParentForm().show());
        
        Command sourceCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_CODE, iconStyle),
                e->{});
        
        toolbar.addCommandToRightBar(sourceCommand);
        toolbar.addCommandToLeftBar(backCommand);
        demoForm.add(BorderLayout.CENTER, demo.createContentPane());
        demoForm.show();
    }
}
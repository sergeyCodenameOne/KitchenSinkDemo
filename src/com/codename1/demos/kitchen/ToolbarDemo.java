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

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class ToolbarDemo extends Demo{
    public ToolbarDemo(Form parentForm) {
        init("Toolbar", getGlobalResources().getImage("toolbar-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Form toolBarForm = new Form("", new FlowLayout(Component.CENTER));
        toolBarForm.getContentPane().setUIID("ComponentDemoContainer");
        Toolbar tb = toolBarForm.getToolbar();
        tb.setUIID("DemoToolbar");
        tb.getTitleComponent().setUIID("DemoTitle");
        
        // Toolbar add source and back buttons.
        Style commandStyle = UIManager.getInstance().getComponentStyle("DemoTitleCommand");
        Form lastForm = Display.getInstance().getCurrent();
        Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, commandStyle),
                e-> lastForm.showBack());
        
        Command sourceCommand = Command.create("", FontImage.create("{ }", commandStyle),
                e->{});
        
        tb.addCommandToRightBar(sourceCommand);
        tb.addCommandToLeftBar(backCommand);
       
        tb.setPermanentSideMenu(true);
                
        tb.addComponentToSideMenu(new Button("Home", FontImage.MATERIAL_HOME, ("ToolbarDemoButton")));
        tb.addComponentToSideMenu(new Button("Profile", FontImage.MATERIAL_SUPERVISED_USER_CIRCLE, ("ToolbarDemoButton")));
        tb.addComponentToSideMenu(new Button("Setting", FontImage.MATERIAL_SETTINGS, ("ToolbarDemoButton")));
        tb.addComponentToSideMenu(new Button("Logout", FontImage.MATERIAL_INPUT, ("ToolbarDemoButton")));
        
        Button searchButton = new Button("Show searchbar", ("ToolbarDemoButton"));
        searchButton.addActionListener(e->{
            tb.showSearchBar(ee->{
                String text = (String)ee.getSource();
                // Update the UI depending on the text.
            });
        });
        toolBarForm.add(searchButton);
        
        toolBarForm.show();
        return null;
    }
}

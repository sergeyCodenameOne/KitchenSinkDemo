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

import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;

/** 
 * This is the base class for all the demos.
 */
public abstract class Demo{
    
    private String id;
    private Image demoImage;
    private Form parentForm;
    private String sourceCode;
    
    protected void init(String id, Image demoImage, Form parentForm, String sourceCode){
        this.id = id;
        this.demoImage = demoImage;
        this.parentForm = parentForm;
        this.sourceCode = sourceCode;
    }
    
    protected String getSourceCode(){
        return sourceCode;
    }

    protected String getDemoId(){
        return id;
    }
   
    protected Image getDemoImage(){
        return demoImage;
    }
    
    protected Form getParentForm(){
        return parentForm;
    }
    
    abstract public Container createContentPane();

    protected void showDemo(String title, Component content){
        Form chartForm = new Form(title, new BorderLayout());
            content.setUIID("ComponentDemoContainer");
            Toolbar toolbar = chartForm.getToolbar();
            toolbar.setUIID("ComponentDemoToolbar");
            toolbar.getTitleComponent().setUIID("ComponentDemoTitle");
            
            Form lastForm = getCurrentForm();
            Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("DemoTitleCommand")),
                    e-> lastForm.showBack());
            
            toolbar.setBackCommand(backCommand);
            chartForm.add(BorderLayout.CENTER, content);
            chartForm.show();      
    }
    
    public static void adjustToTablet(Container cnt){
        // Create anonymous class and override the calcPreferredSize() function to fit execly half of the scree.
        Container leftSide = new Container(new BoxLayout(BoxLayout.Y_AXIS)){
            @Override
            protected Dimension calcPreferredSize() {
                Dimension dim = super.calcPreferredSize();
                dim.setWidth(Display.getInstance().getDisplayWidth() / 2);
                return dim;
            }
        };
        
        Container rightSide = new Container(new BoxLayout(BoxLayout.Y_AXIS)){
            @Override
            protected Dimension calcPreferredSize() {
                Dimension dim = super.calcPreferredSize();
                dim.setWidth(Display.getInstance().getDisplayWidth() / 2);
                return dim;
            }
            
        };
        int i = 0;
        for(Component currComponent : cnt.getChildrenAsList(true)){
            cnt.removeComponent(currComponent);
            if(i++ % 2 == 0){
                leftSide.add(currComponent);
            }else{
                rightSide.add(currComponent);
            }
        }
        cnt.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        cnt.addAll(leftSide, rightSide);
    }
}

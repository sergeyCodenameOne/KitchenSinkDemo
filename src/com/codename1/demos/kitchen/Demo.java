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

import com.codename1.demos.kitchen.charts.AbstractDemoChart;
import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;

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
    
    private void showChart(AbstractDemoChart demo) {
            Form chartForm = new Form(demo.getChartTitle(), new BorderLayout());
            Toolbar toolbar = chartForm.getToolbar();
            toolbar.setUIID("DemoToolbar");
            toolbar.getTitleComponent().setUIID("DemoTitle");
            
            Form lastForm = getCurrentForm();
            Style iconStyle = new Style();
            iconStyle.setFgColor(0x157EFB);
            Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, iconStyle),
                    e-> lastForm.showBack());
            
            toolbar.addCommandToLeftBar(backCommand);
            chartForm.add(BorderLayout.CENTER, demo.execute());
            chartForm.show();      
    }
    
    
    protected void showDemo(String title, Component content){
        Form chartForm = new Form(title, new BorderLayout());
            Toolbar toolbar = chartForm.getToolbar();
            toolbar.setUIID("DemoToolbar");
            toolbar.getTitleComponent().setUIID("DemoTitle");
            
            Form lastForm = getCurrentForm();
            Style iconStyle = new Style();
            iconStyle.setFgColor(0x157EFB);
            Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, iconStyle),
                    e-> lastForm.showBack());
            
            toolbar.addCommandToLeftBar(backCommand);
            chartForm.add(BorderLayout.CENTER, content);
            chartForm.show();      
    }
}

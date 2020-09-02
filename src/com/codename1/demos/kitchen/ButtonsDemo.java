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


import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;


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
                                                                    
                                                                    
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("span-button.png"),
                                                                "Span Buttons",
                                                                "A complex button similar to MultiButton",
                                                                "that breaks lines automatically and looks like a regular button(more or less). Unlike the multi button the "+
                                                                "span buttons has the UIID style of a button.", e->{
                                                                    
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("multi-buttons.png"),
                                                                "Multi Buttons",
                                                                "A powerful button like component that",
                                                                "allows multiple rows/and an icon to be added every row/icon can have its own UIID.\n\nInternally the "+
                                                                "multi-button is a container with a lead component. Up to 4 rows are supported.", e->{
                                                                    
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("scale-image-label.png"),
                                                                "Scale Image Button",
                                                                "Button that simplifies the usage of scale to",
                                                                "fill/fit. This is effectively equivalent to just setting the style image on a button but more convenient "+
                                                                "for some special circumstances.\n\nOne major difference is that preferred size equals the image in this case.", e->{
                                                                   
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("floating-action-button.png"),
                                                                "Floating Action Button",
                                                                "Floating action buttons are a material design",
                                                                "element used to promote a special action in a form. They are represented as a floating circle with a "+
                                                                "flat icon floating above the UI typically in the bottom right area.", e->{
                                                                   
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("share-button.png"),
                                                                "Share Button",
                                                                "The share button allows sharing a String",
                                                                "or an image either thru the defined sharing services or thru native OS sharing support. On Android & iOS the "+
                                                                "native sharing API is invoked for this class.\n\nThe code below demonstrates image sharing, notice that an "+
                                                                "image must be stored using the FileSystemStorage API and shouldn't use a different API like Storage.", e->{
                                                                    
                                                                }));
        
        return demoContainer;
    }
    
}

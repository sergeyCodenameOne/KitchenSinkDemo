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

import com.codename1.components.Switch;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class TogglesDemo extends Demo {
    
    public TogglesDemo(Form parentForm) {
        init("Toggles", getGlobalResources().getImage("toggles-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("check-box.png"),
                                                                "Checkbox",
                                                                "Checkbox is a button that can be selected",
                                                                "or deselected and display its state to the user. Check out RadioButton for a more exclusive selection "+
                                                                "approach. Both components support a toggle button mode using the Button.setToggle (Boolean) API.", e->{
                                                                    showDemo("Checkbox", createCheckboxDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("radio-button.png"),
                                                                "Radio Button",
                                                                "Checkbox is a button that can be selected",
                                                                "or deselected and display its state to the user. Check out RadioButton for a more exclusive selection "+
                                                                "approach. Both components support a toggle button mode using the Button.setToggle (Boolean) API.", e->{
                                                                    showDemo("Radio Button", createRadioButtonDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("switch.png"),
                                                                "Switch",
                                                                "Button is the base class for several UI",
                                                                "The on/off switch is a checkbox of sort (although it derives container) that represents its state as a switch "+
                                                                "when using the android native theme this implementation follows the Material Design Switch "+
                                                                "guidelines: https://material.io/guidelines/components/ selection-controls.html#selection-controls- radio-button",
                                                                e->{
                                                                    Switch s = new Switch();
                                                                    showDemo("Switch", BorderLayout.centerAbsolute(s));
                                                                }));
        
        return demoContainer;
    }
    private Container createCheckboxDemo(){
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_INFO, UIManager.getInstance().getComponentStyle("CheckBox"));
        CheckBox cb1 = new CheckBox("CheckBox No Icon");
        cb1.setSelected(true);
        CheckBox cb2 = new CheckBox("CheckBox With Icon", icon);
        CheckBox cb3 = new CheckBox("CheckBox Opposite True", icon);
        CheckBox cb4 = new CheckBox("CheckBox Opposite False", icon);
        cb3.setOppositeSide(true);
        cb4.setOppositeSide(false);
        
        return BoxLayout.encloseY(cb1, cb2, cb3, cb4);
    }
    
    private Container createRadioButtonDemo(){
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_INFO, UIManager.getInstance().getComponentStyle("RadioButton"));
        RadioButton rb1 = new RadioButton("Radio Button 1");
        RadioButton rb2 = new RadioButton("Radio Button 2");
        RadioButton rb3 = new RadioButton("Radio Button 3", icon);
        new ButtonGroup(rb1, rb2, rb3);
        rb2.setSelected(true);
        return BoxLayout.encloseY(rb1, rb2, rb3);
    }
}

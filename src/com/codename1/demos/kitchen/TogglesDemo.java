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

import static com.codename1.ui.CN.*;
import com.codename1.components.Switch;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
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
                                                                    showDemo("Switch", createSwitchDemo());
                                                                }));
        
        return demoContainer;
    }
    
    private Container createCheckboxDemo() {
        CheckBox cb1 = new CheckBox("Tomato");
        cb1.setUIID("DemoCheckBox");
        CheckBox cb2 = new CheckBox("Salad");
        cb2.setUIID("DemoCheckBox");
        CheckBox cb3 = new CheckBox("Onion");
        cb3.setUIID("DemoCheckBox");
        CheckBox cb4 = new CheckBox("Pickled Cucumber");
        cb4.setUIID("DemoCheckBox");
        CheckBox cb5 = new CheckBox("Mushrooms");
        cb5.setUIID("DemoCheckBox");
        CheckBox cb6 = new CheckBox("Cheese");
        cb6.setUIID("DemoCheckBox");
        CheckBox cb7 = new CheckBox("Egg");
        cb7.setUIID("DemoCheckBox");
        cb1.setSelected(true);
        cb2.setSelected(true);
        cb3.setSelected(true);
        cb4.setSelected(true);
        Container checkBoxContainer = BoxLayout.encloseY(cb1, cb2, cb3, cb4, cb5, cb6, cb7);
        Container demoContainer = BorderLayout.center(checkBoxContainer);
        Button completeOrder = new Button("Complete Order", "DemoCompleteOrder");
        completeOrder.addActionListener(e->{
            ToastBar.showInfoMessage("Your order is on the way");
        });
        demoContainer.add(BorderLayout.SOUTH, completeOrder);
        return demoContainer;
    }
    
    private Container createRadioButtonDemo(){
        ButtonGroup bg = new ButtonGroup();
        RadioButton rb1 = RadioButton.createToggle("Android", bg);
        RadioButton rb2 = RadioButton.createToggle("IOS", bg);
        RadioButton rb3 = RadioButton.createToggle("UWP", bg);
        RadioButton rb4 = RadioButton.createToggle("Mac Os Desktop", bg);
        RadioButton rb5 = RadioButton.createToggle("Windows Desktop", bg);
        RadioButton rb6 = RadioButton.createToggle("Javascript", bg);
  
        rb1.setUIID("DemoRadioButton");
        rb2.setUIID("DemoRadioButton");
        rb3.setUIID("DemoRadioButton");
        rb4.setUIID("DemoRadioButton");
        rb5.setUIID("DemoRadioButton");
        rb6.setUIID("DemoRadioButton");
        
        rb1.setSelected(true);
        Container radioButtonsContainer = BoxLayout.encloseY(new Label("select build:"), rb1, rb2, rb3, rb4, rb5, rb6);
        Container demoContainer = BorderLayout.center(radioButtonsContainer);
        Button applyButton = new Button("Send Build", "DemoRadioBuildButton");
        
        applyButton.addActionListener(e->{
            RadioButton selectedButton = bg.getSelected();
            ToastBar.showInfoMessage(selectedButton.getText() + " build was sent");
        });
        demoContainer.add(BorderLayout.SOUTH, applyButton);
        return demoContainer;
    }
    
    private Container createSwitchDemo(){
        Switch s = new Switch();
        s.setOn();
        if (isDarkMode()!= null && !isDarkMode()){
            s.setOff();
        }
        Container switchContainer = FlowLayout.encloseCenter(s);
        s.addChangeListener(ee->{
            if(s.isOn()){
                switchContainer.setUIID("BrightContainer");
            }else{
                switchContainer.setUIID("DarkContainer");
            }
            switchContainer.revalidate();
        });
        return switchContainer;
    }
}

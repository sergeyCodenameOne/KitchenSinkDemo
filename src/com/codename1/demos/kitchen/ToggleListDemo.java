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

import com.codename1.components.CheckBoxList;
import com.codename1.components.RadioButtonList;
import com.codename1.components.SwitchList;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import static com.codename1.ui.util.Resources.getGlobalResources;


public class ToggleListDemo extends Demo {
    
    public ToggleListDemo(Form parentForm) {
        init("Toggle List", getGlobalResources().getImage("toggle-list-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("switch-list.png"),
                                                                "Switch List (Flow)",
                                                                "A list of switches", e->{
                                                                    showDemo("Switch List (Flow)", createSwitchListDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("check-box-list.png"),
                                                                "Check Box List (Flow)",
                                                                "A list of Check Boxes", e->{  
                                                                    showDemo("Check Box List (Flow)", createCheckBoxListDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("radio-button-list.png"),
                                                                "RadioButton List (BoxLayout Y)",
                                                                "A list of Radio Buttons.", e->{
                                                                    showDemo("RadioButton List (BoxLayout Y)", createRadioButtonListDemo());
                                                                }));
        
        return demoContainer;
    }
    
    private Container createSwitchListDemo(){
        SwitchList switchList = new SwitchList(new DefaultListModel("Red", "Green", "Blue", "Indigo"));
        switchList.addActionListener(e->{
            String switchText = ((Label)e.getComponent().getParent().getComponentAt(0)).getText();
            ToastBar.showInfoMessage(switchText + " has pressed");

        });
        
        Button clearSelections = new Button("Clear");
        clearSelections.addActionListener(e -> {
            switchList.getMultiListModel().setSelectedIndices();
        });
        
        Button addOption = new Button("Add Option");
        addOption.addActionListener(e -> {
            TextField val = new TextField();
            Command res = Dialog.show("Enter Switch name", val, new Command("OK"));
            switchList.getMultiListModel().addItem(val.getText());
            switchList.revalidate();
        });
        
        Container cnt = BorderLayout.center(switchList);
        cnt.setUIID("SwitchListContainer");
        cnt.add(BorderLayout.SOUTH, BoxLayout.encloseX(addOption, clearSelections));
        return BorderLayout.center(cnt);
    }
    
    private Container createCheckBoxListDemo(){
        DefaultListModel model = new DefaultListModel("Red", "Green", "Blue", "Indigo");
        CheckBoxList list = new CheckBoxList(model);

        Button addOption = new Button("Add Option");
        addOption.addActionListener(e -> {
            TextField val = new TextField();
            Command res = Dialog.show("Enter Switch name", val, new Command("OK"));
            list.getMultiListModel().addItem(val.getText());
            list.revalidate();
        });
        
        Button confirm = new Button("confirm");
        confirm.addActionListener(e->{
            StringBuilder selectedItems = new StringBuilder();
            for (int i = 0; i < model.getSize(); i++) {
                if (((CheckBox)list.getComponentAt(i)).isSelected()){
                    selectedItems.append(model.getItemAt(i) + ", ");
                }
            }
            selectedItems.delete(selectedItems.length() - 2, selectedItems.length() - 1);
            ToastBar.showInfoMessage(selectedItems + " has selected");
        });
        Container checkBoxContainer = BorderLayout.center(list);
        checkBoxContainer.add(BorderLayout.SOUTH, BoxLayout.encloseX(addOption, confirm));
        checkBoxContainer.setUIID("CheckBoxContainer");
        return BorderLayout.center(checkBoxContainer);
    }
    
    private Container createRadioButtonListDemo(){
        DefaultListModel model = new DefaultListModel("Red", "Green", "Blue", "Indigo");
        RadioButtonList list = new RadioButtonList(model);
        list.setLayout(BoxLayout.y());
        
        Button addOption = new Button("Add Option");
        addOption.addActionListener(e -> {
            TextField val = new TextField();
            Command res = Dialog.show("Enter Switch name", val, new Command("OK"));
            list.getMultiListModel().addItem(val.getText());
            list.revalidate();
        });
        
        Button confirm = new Button("confirm");
        confirm.addActionListener(e->{
            int selectedIndex = list.getModel().getSelectedIndex();
            ToastBar.showInfoMessage(list.getModel().getItemAt(selectedIndex) + " was selected");
        });

        Container cnt = BorderLayout.center(list);
        cnt.add(BorderLayout.SOUTH, BoxLayout.encloseX(addOption, confirm));
        cnt.setUIID("RadioButtonsContainer");
        return BorderLayout.center(cnt);
    }
}

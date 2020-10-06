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
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;


public class ToggleListDemo extends Demo {
    
    public ToggleListDemo(Form parentForm) {
        init("Toggle List", getGlobalResources().getImage("toggle-list-demo.png"), parentForm,
                "https://github.com/sergeyCodenameOne/KitchenSinkDemo/blob/master/src/com/codename1/demos/kitchen/ToggleListDemo.java");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("check-box-list.png"),
                                                                "Check Box List",
                                                                "A list of Check Boxes", e->{  
                                                                    showDemo("Check Box List", createCheckBoxListDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("radio-button-list.png"),
                                                                "RadioButton List (BoxLayout Y)",
                                                                "A list of Radio Buttons.", e->{
                                                                    showDemo("RadioButton List (BoxLayout Y)", createRadioButtonListDemo());
                                                                }));
        
        return demoContainer;
    }
    
    private Container createCheckBoxListDemo(){
        DefaultListModel model = new DefaultListModel("Pasta", "Rice", "Bread", "Butter", "Milk", "Eggs", "Cheese", "Salt", "Pepper", "Honey");
        CheckBoxList list = new CheckBoxList(model);
        list.setScrollableY(true);
        list.setLayout(new BoxLayout(BoxLayout.Y_AXIS));  
        list.setShouldCalcPreferredSize(true);
        Button add = new Button("Add New", "AddNewButton");
        add.addActionListener(e->{
            TextComponent newItem = new TextComponent().label("New Item: ");
            Command ok = new Command("Ok");
            Command cancel = new Command("Cancel");
            
            if(Dialog.show("Enter Note", newItem, ok, cancel) == ok && newItem.getText().length() != 0){
                model.addItem(newItem.getText());
                list.revalidate();
            }
        });
        
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_SHARE, UIManager.getInstance().getComponentStyle("DemoButtonIcon"));
        ShareButton share = new ShareButton();
        share.setIcon(icon);
        share.setText("Share Groceries");
        share.setUIID("DemoButton");
        share.addActionListener(e->{
            StringBuilder sb = new StringBuilder();
            int[] selected = model.getSelectedIndices();
            for (int i : selected){
                sb.append(model.getItemAt(i));
                sb.append(", ");
            }
            if (selected.length > 0){
                sb.delete(sb.length() - 2, sb.length() - 1);
            }
            share.setTextToShare(sb.toString());
            
            int groceriesSize = model.getSize();
            for(int i = 0; i < groceriesSize; i++){
                CheckBox currItem = (CheckBox)list.getComponentAt(i);
                if(currItem.isSelected()){
                    currItem.setSelected(false);
                }
            }
        });
        
        Container buttonsContainer = FlowLayout.encloseCenter(share, add);
        buttonsContainer.setUIID("CompleteOrderContainer");
         
        Container checkBoxContainer = BorderLayout.center(list).
                                        add(BorderLayout.NORTH, new Label("Select groceries to share", "SelectGroceriesLabel")).
                                        add(BorderLayout.SOUTH, buttonsContainer);
        checkBoxContainer.setUIID("Wrapper");
        
        return BoxLayout.encloseY(checkBoxContainer);
    }
    
    private Container createRadioButtonListDemo(){
        SpanLabel question = new SpanLabel("Who is the first character in the series to be called \"King in the North\"?", "DemoLabel");
        Button answer = new Button("Answer", "DemoAnswerButton");
        
        DefaultListModel model = new DefaultListModel("Jon Snow", "Robb Stark", "Ned Stark", "Edmure Tully");
        RadioButtonList list = new RadioButtonList(model);
        list.setLayout(BoxLayout.y());   
        
        answer.addActionListener(e->{
            if (model.getSelectedIndex() == 1){
                ToastBar.showInfoMessage("Correct!");
            }
            else{
                ToastBar.showInfoMessage("Incorrect!!");
            }
        });

        Container demoContainer = BorderLayout.center(list).
                                    add(BorderLayout.NORTH, question).
                                    add(BorderLayout.SOUTH, answer);
        demoContainer.setUIID("Wrapper");
        
        return BoxLayout.encloseY(demoContainer);
    }
}

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

import com.codename1.components.ClearableTextField;
import com.codename1.components.ToastBar;
import com.codename1.io.CSVParser;
import com.codename1.io.Log;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextFieldDemo extends Demo{
    
    public TextFieldDemo(Form parentForm) {
        init("Text Field", getGlobalResources().getImage("text-field-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("text-field.png"),
                                                                "Text Field",
                                                                "A specialized version of TextArea with",
                                                                "some minor deviations from the original specifically: Blinking cursor is rendered on TextField only. "+
                                                                "com.codename1.ui.events.DataChangeList ener is only available in TextField.\n\nThis is crucial for "+
                                                                "character by character input event tracking setDoneListener(com. codename1.ui. events.ActionLister) "+
                                                                "is only available in Text Field Different UIID's (\"TextField\" vs. \"TextArea\").", 
                                                                e->{                                                                    
                                                                    showDemo("Text Field", createTextFieldDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("text-area.png"),
                                                                "Text Area",
                                                                "An optionally multi-line editable region that",
                                                                "can display text and allow a user to edit it. By default the text area will grow based on its content. "+
                                                                "TextArea is useful both for text input and for displaying multi-line data, it is used internally by "+
                                                                "components such as SpanLabel & SpanButton.\n\nTextArea & TextField are very similar, we discuss the main "+
                                                                "differences between the two here. In fact they are so similar that our sample code below was written for "+
                                                                "TextField but should be interchangeable with TextArea.", 
                                                                e->{
                                                                    showDemo("Text Area", createTextAreaDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("clearable-text-field.png"),
                                                                "Clearable Text Field",
                                                                "Wraps a text field so it will have an X to",
                                                                "clear its content on the right hand side.", 
                                                                e->{
                                                                    showDemo("Clearable Text Field", createClearableTextFieldDemo());
                                                                }));
 
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("auto-complete-text-field.png"),
                                                                "Auto Complete Text Field",
                                                                "An editable TextField with completion",
                                                                "suggestions that show up in a drop down menu while the user types in text. This class uses the \"TextField\" "+
                                                                "UIID by default as well as \"AutoCompletePopup\" & \"AutoCompleteList\" for the popup list details. The "+
                                                                "sample below shows the more trivial use case for this widget.", 
                                                                e->{
                                                                    showDemo("Browser", createAutoCompleteDemo());
                                                                }));
        return demoContainer;
    }
    
    private Container createTextFieldDemo(){
        Container textFields = new Container();
        TableLayout tl;
        if(Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
        }
        
        TextField firstName = new TextField("", "First Name", 20, TextArea.ANY);
        firstName.setUIID("DemoTextArea");
        TextField surname = new TextField("", "Surname", 20, TextArea.ANY);
        surname.setUIID("DemoTextArea");
        TextField email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        email.setUIID("DemoTextArea");
        TextField url = new TextField("", "URL", 20, TextArea.URL);
        url.setUIID("DemoTextArea");
        TextField phone = new TextField("", "Phone", 20, TextArea.PHONENUMBER);
        phone.setUIID("DemoTextArea");

        TextField num1 = new TextField("", "1234", 4, TextArea.NUMERIC);
        num1.setUIID("DemoTextArea");
        TextField num2 = new TextField("", "1234", 4, TextArea.NUMERIC);
        num2.setUIID("DemoTextArea");
        TextField num3 = new TextField("", "1234", 4, TextArea.NUMERIC);
        num3.setUIID("DemoTextArea");
        TextField num4 = new TextField("", "1234", 4, TextArea.NUMERIC);
        num4.setUIID("DemoTextArea");
        
        Button submit = new Button("Submit", "TextFieldsDemoButton");
        submit.addActionListener(e->{
            ToastBar.showInfoMessage("Your personal data was saved successfully");
        });
        
        tl.setGrowHorizontally(true);
        textFields.setLayout(tl);
        textFields.add(new Label("First Name", "DemoLabel")).
                add(firstName).
                add(new Label("Surname", "DemoLabel")).
                add(surname).
                add(new Label("E-Mail", "DemoLabel")).
                add(email).
                add(new Label("URL", "DemoLabel")).
                add(url).
                add(new Label("Phone", "DemoLabel")).
                add(phone).
                add(new Label("Credit Card", "DemoLabel")).
                add(GridLayout.encloseIn(4, num1, num2, num3, num4));
        
        Container demoContainer = BorderLayout.center(textFields);
        demoContainer.add(BorderLayout.SOUTH, submit);
        demoContainer.setUIID("Wrapper");
        return BoxLayout.encloseY(demoContainer);
    }
    
    private Container createTextAreaDemo(){
        Container textFields = new Container();
        TableLayout tl;
        if(Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
        }
        
        TextArea firstName = new TextArea(1, 20, TextArea.ANY);
        firstName.setUIID("DemoTextArea");
        TextArea surname = new TextArea(1, 20, TextArea.ANY);
        surname.setUIID("DemoTextArea");
        TextArea email = new TextArea(1, 20, TextArea.EMAILADDR);
        email.setUIID("DemoTextArea");
        TextArea url = new TextArea(1, 20, TextArea.URL);
        url.setUIID("DemoTextArea");
        TextArea phone = new TextArea(1, 20, TextArea.PHONENUMBER);
        phone.setUIID("DemoTextArea");

        TextArea num1 = new TextArea(1, 4, TextArea.NUMERIC);
        num1.setUIID("DemoTextArea");
        TextArea num2 = new TextArea(1, 4, TextArea.NUMERIC);
        num2.setUIID("DemoTextArea");
        TextArea num3 = new TextArea(1, 4, TextArea.NUMERIC);
        num3.setUIID("DemoTextArea");
        TextArea num4 = new TextArea(1, 4, TextArea.NUMERIC);
        num4.setUIID("DemoTextArea");
        
        Button submit = new Button("Submit", "TextFieldsDemoButton");
        submit.addActionListener(e->{
            ToastBar.showInfoMessage("Your personal data was saved successfully");
        });
        
        tl.setGrowHorizontally(true);
        textFields.setLayout(tl);
        textFields.add(new Label("First Name", "DemoLabel")).
                add(firstName).
                add(new Label("Surname", "DemoLabel")).
                add(surname).
                add(new Label("E-Mail", "DemoLabel")).
                add(email).
                add(new Label("URL", "DemoLabel")).
                add(url).
                add(new Label("Phone", "DemoLabel")).
                add(phone).
                add(new Label("Credit Card", "DemoLabel")).
                add(GridLayout.encloseIn(4, num1, num2, num3, num4));
        
        Container demoContainer = BorderLayout.center(textFields);
        demoContainer.add(BorderLayout.SOUTH, submit);
        demoContainer.setUIID("Wrapper");
        return BoxLayout.encloseY(demoContainer);
    }
    
    private Container createClearableTextFieldDemo(){
        TextField userName = new TextField("", "User Name", 20, TextArea.ANY);
        userName.setUIID("DemoTextArea");
        ClearableTextField clearableUserName = ClearableTextField.wrap(userName);
        
        TextField password = new TextField("", "Password", 20, TextArea.PASSWORD);
        password.setUIID("DemoTextArea");
        ClearableTextField clearablePassword = ClearableTextField.wrap(password);
        
        Container textFieldsContainer = BoxLayout.encloseY(new Label("Username:", "DemoLabel"),
                                  clearableUserName,
                                  new Label("Password:", "DemoLabel"),
                                  clearablePassword );
        
        Button loginButton = new Button("Login", "TextFieldsDemoButton");
        loginButton.addActionListener(e-> ToastBar.showInfoMessage("Username or Password are incorrect"));
        
        Container demoContainer = BorderLayout.center(textFieldsContainer);
        demoContainer.add(BorderLayout.SOUTH, loginButton);
        demoContainer.setUIID("Wrapper");
        return BoxLayout.encloseY(demoContainer);
    }
    
    private Container createAutoCompleteDemo(){
        CSVParser parser = new CSVParser();
        List<String> commonWords = new ArrayList();
        try(InputStream reader = Display.getInstance().getResourceAsStream(getClass(), "/common-words.csv")){
            String[][] data = parser.parse(reader);            
            for (String[] s : data){
                commonWords.add(s[0]);
            }
        } catch(IOException err) {
            Log.e(err);
        }
        
        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField ac = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if(text.length() == 0) {
                    options.removeAll();
                    return false;
                }
                List<String> matchedWords = searchWords(text, commonWords);
                options.removeAll();
                if(matchedWords == null || matchedWords.size() == 0) {
                    return true;
                }

                for(String s : matchedWords) {
                    options.addItem(s);
                }
                return true;
            }
        };
        ac.setUIID("DemoTextArea");
        DefaultListCellRenderer<Object> renderer = new DefaultListCellRenderer<>();
        renderer.setUIID("DemoLabel");
        renderer.setShowNumbers(false);
        ac.setCompletionRenderer(renderer);
        
        
        Container demoContainer = BoxLayout.encloseY(new Label("Search:", "DemoLabel"), ac);
        demoContainer.setUIID("Wrapper");
        return BoxLayout.encloseY(demoContainer);
    }    
    
    List<String> searchWords(String text, List<String> wordsList) {        
        List<String> matchedWords = new ArrayList<>();
        int count = 0;
        for (String word : wordsList){
            if (word.contains(text)){
                matchedWords.add(word);
                if(++count == 5){
                    return matchedWords;
                }
            }
        }
        return matchedWords;
    }
}

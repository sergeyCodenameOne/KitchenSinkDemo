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

/**
 *
 * @author serge
 */
public class DialogDemo extends Demo {
    
    public DialogDemo(Form parentForm) {
        init("Dialog", getGlobalResources().getImage("dialog-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("interaction-dialog.png"),
                                                                "Interaction Dialog",
                                                                "Unlike a regular dialog the interaction",
                                                                "dialog only looks like a dialog, it resides in the layered pane and can be used to implement features "+
                                                                "where interaction with the background form is still required. Since this code is designed for interaction "+
                                                                "all \"dialogs\" created thru there are modless and never block.",
                                                                e->{
                                                                    showDemo("Interaction Dialog", createInteractionDialogDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("dialog.png"),
                                                                "Dialog",
                                                                "A dialog is a form that occupies a part of",
                                                                "the screen and appears as a modal entity to the developer. Dialogs allow us to prompt users for information "+
                                                                "and rely on the information being available on the next line after the show method.\n\nModality indicates that "+
                                                                "a dialog will block the calling thread even if the calling thread is the EDT. Notice that a dialog will not "+
                                                                "release the block until dispose is called even if show() from another form is called! Events are still "+
                                                                "performed thanks to the Display.invokeAnd Block(java.lang.Runnable) capability of the Display class.\n\nTo "+
                                                                "determine the size of the dialog use the show method that accepts 4 integer values, notice that these "+
                                                                "values accept margin from the four sides than x,y, width and height values!\n\nIt's important to style a Dialog "+
                                                                "using getDialogStyle() or setDialogUIID(java.lang. String) methods rather than styling the dialog object "+
                                                                "directly.\n\nThe Dialog class also includes support for popup dialog which is a dialog type that is positioned "+
                                                                "text to a component or screen area and points an arrow at the location.",
                                                                e->{
                                                                    showDemo("Dialog", createDialogDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("sheet.png"),
                                                                "Sheet",
                                                                "A light-weight dialog that slides up from",
                                                                "the bottom of the screen on mobile devices. Sheets include a \"title\" bar, with a back/close button, a title "+
                                                                "label and a \"commands container\" (getCommandsContainer()) which allows you to insert your own custom "+
                                                                "components (usually buttons) in the upper right. Custom content should be placed inside the content pane "+
                                                                "which can be retrieved via getContentPane()\n\nUsage:\nThe general usage is to create new sheet instance "+
                                                                "(or subclass), then call show() to make it appear over the current form. If a different sheet that is "+
                                                                "currently being displayed, then calling show() will replace it.",
                                                                e->{
                                                                    showDemo("Sheet", createSheetDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("toast-bar.png"),
                                                                "ToastBar",
                                                                "An API to present status messages to the",
                                                                "user in an unobtrusive manner. This is useful if there are background tasks that need to display "+
                                                                "information to the user. E.g.p If a network request fails, of let the user know that \"Jobs are being "+
                                                                "synchronized\"",
                                                                e->{
                                                                    showDemo("ToastBar", createToastBarDemo());
                                                                }));
        return demoContainer;
    }
    
    private Container createInteractionDialogDemo(){
        return new Container();
    }
    
    private Container createDialogDemo(){
        return new Container();    
    }
    
    private Container createSheetDemo(){
        return new Container();
    }
    
    private Container createToastBarDemo(){
        return new Container();
    }
}

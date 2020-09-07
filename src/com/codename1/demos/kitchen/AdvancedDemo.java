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

import com.codename1.components.FileTree;
import com.codename1.components.FileTreeModel;
import com.codename1.components.SignatureComponent;
import com.codename1.demos.kitchen.ComponentDemos.ImageViewerDemo;
import com.codename1.ui.BrowserComponent;
import static com.codename1.ui.CN.isTablet;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

        
public class AdvancedDemo extends Demo{
    
    public AdvancedDemo(Form parentForm) {
        init("Advanced", getGlobalResources().getImage("advanced-icon.png"), parentForm, "");
    }
     
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        if (isTablet()){
            demoContainer.setLayout(new TableLayout(2, 2));
        }
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();

        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-browser.png"),
                                                                "Browser Component",
                                                                "The browser component is an interface",
                                                                "to an embeddable native platform browser on "+
                                                                "platforms that support embedding the native browser in place, if you need wide "+
                                                                "compatibility and flexibility you should check out the HTML Component which provides "+
                                                                "a lightweight 100% cross platform web component.\n\nThis component will only work on "+
                                                                "platforms that support embedding a native browser which exclude earlier versions of "+
                                                                "Blackberry devices and J2ME devices.\n\nIt's recommended that you place this component in "+
                                                                "a fixed position (none scrollable) on the screen without other focusable components to "+
                                                                "prevent confusion between focus authority and allow the component to scroll itself rather "+
                                                                "than CodenameOne making that decision for it.\n\nOn Android this component might show a native progress "+
                                                                "indicator dialog. You can disable that functionality using the call.", 
                                                                e->{
                                                                    BrowserComponent browser = new BrowserComponent();
                                                                    browser.setURL("https://www.codenameone.com/");
                                                                    showDemo("Browser", browser);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-singnature.png"),
                                                                "Signature Component",
                                                                "A component to allow a user to enter",
                                                                "their signature. This is just a button that, when pressed, will pop up a dialog where the user can draw "+
                                                                "their signature with their finger.\n\nThe user is given the option to save/reset/cancel the signature. On save, "+
                                                                "the signatureImamge property will be set with a full-size of the signature, and the icon on the button will "+
                                                                "show a thumbnail of the image.",
                                                                e->{
                                                                    Container cnt = new Container(BorderLayout.absolute());
                                                                    SignatureComponent sig = new SignatureComponent();
                                                                    sig.setUIID("WhiteContainer");
                                                                    cnt.addComponent(BorderLayout.CENTER, sig);
                                                                    showDemo("Signature", cnt);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-calendar.png"),
                                                                "Calendar",
                                                                "Date widget for selecting a date/time value.",
                                                                "To localize stings for month names use the values Calendar. Month using 3 first characters of the month name in "+
                                                                "the resource localization e.g. Calendar. Jan, Calendar.Feb etc …\n\nTo localize stings for day names use the values "+
                                                                "Calendar. Day in the resource localization e.g. \"Calendar.Sunday\", \"Calendar.Monday\" etc …\n\nNote that we "+
                                                                "recommend using the picker class which is superior when running on the device for most use cases.",
                                                                e->{
                                                                    Calendar cld = new Calendar();
                                                                    cld.setUIID("Calendar");
                                                                    showDemo("Calendar", cld);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-tree-file.png"),
                                                                "File Tree",
                                                                "Simple class showing off the file system as",
                                                                "a tree component.",
                                                                e->{
                                                                    FileTree xmlTree = new FileTree(new FileTreeModel(true));
                                                                    Container treeContainer = BorderLayout.center(xmlTree);
                                                                    treeContainer.setUIID("TreeContainer");
                                                                    showDemo("File Tree", treeContainer);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-image-viewer.png"),
                                                                "Image Viewer",
                                                                "Image Viewer allows zooming/panning an",
                                                                "image and potentially flicking between multiple images within a list of images",
                                                                e->{
                                                                    showDemo("Image Viewer", new ImageViewerDemo().createContentPane());
                                                                }));
        return demoContainer;
    }
}

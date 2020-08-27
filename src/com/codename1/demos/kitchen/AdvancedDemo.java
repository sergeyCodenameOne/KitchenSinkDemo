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

import static com.codename1.ui.CN.isTablet;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
        

public class AdvancedDemo extends Demo{
    
     public AdvancedDemo(Form parentForm) {
        init("Advanced", getGlobalResources().getImage("charts-demo-icon.png"), parentForm);
    }
     
     public Container createContentPane(){
         Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "VideoContainer");
         if (isTablet()){
            demoContainer.setLayout(new TableLayout(2, 2));
        }
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createAcordionComponent(getGlobalResources().getImage("advanced-browser.png"),
                                                                "Browser Component",
                                                                "The browser component is an interface",
                                                                "to an embeddable native platform browser on "+
                                                                "platforms that support embedding the native browser in place, if you need wide "+
                                                                "compatibility and flexibility you should check out the HTML Component which provides "+
                                                                "a lightweight 100% cross platform web component. This component will only work on "+
                                                                "platforms that support embedding a native browser which exclude earlier versions of "+
                                                                "Blackberry devices and J2ME devices. It's recommended that you place this component in "+
                                                                "a fixed position (none scrollable) on the screen without other focusable components to "+
                                                                "prevent confusion between focus authority and allow the component to scroll itself rather "+
                                                                "than CodenameOne making that decision for it. On Android this component might show a native progress "+
                                                                "indicator dialog. You can disable that functionality using the call."
                                                                ));
         
        return demoContainer;
     }
}

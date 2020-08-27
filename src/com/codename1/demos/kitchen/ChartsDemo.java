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
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class ChartsDemo extends Demo {

    public ChartsDemo(Form parentForm) {
        init("Charts", getGlobalResources().getImage("charts-demo-icon.png"), parentForm);
    }
    
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        if (isTablet()){
            demoContainer.setLayout(new TableLayout(2, 2));
        }
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-bar.png"),
                                                                "Bar Chart",
                                                                "The bar chart rendering class"
                                                                ));
        
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-bubble.png"),
                                                                "Bubble Chart",
                                                                "The bubble chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-combined-xy.png"),
                                                                "CombinedXY Chart",
                                                                "The combinedXY chart rendering class"));
        
        demoContainer.add(builder.createAcordionComponent(getGlobalResources().getImage("chart-cubic-line.png"),
                                                                "CunicLine Chart",
                                                                "The interpolated (cubic) line chart rendering",
                                                                " class"));

        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-donut.png"),
                                                                "Donut Chart",
                                                                "The donut chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-line.png"),
                                                                "Line Chart",
                                                                "The linechart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-pie.png"),
                                                                "Pie Chart",
                                                                "The pie chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-radar.png"),
                                                                "Radar Chart",
                                                                "The radar chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-round.png"),
                                                                "Round Chart",
                                                                "The round chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-scatter.png"),
                                                                "Scatter Chart",
                                                                "The scater chart rendering class"));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-time.png"),
                                                                "Time Chart",
                                                                "The Time chart rendering class"));
        
        return demoContainer;
    }
    
    
    
    
}

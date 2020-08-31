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


import com.codename1.demos.kitchen.charts.AbstractDemoChart;
import com.codename1.demos.kitchen.charts.AverageCubicTemperatureChart;
import com.codename1.demos.kitchen.charts.BudgetDoughnutChart;
import com.codename1.demos.kitchen.charts.BudgetPieChart;
import com.codename1.demos.kitchen.charts.CombinedTemperatureChart;
import com.codename1.demos.kitchen.charts.EmployeeChart;
import com.codename1.demos.kitchen.charts.ProjectStatusBubbleChart;
import com.codename1.demos.kitchen.charts.SalesBarChart;
import com.codename1.demos.kitchen.charts.ScatterChart;
import com.codename1.demos.kitchen.charts.SensorValuesChart;
import com.codename1.demos.kitchen.charts.TrigonometricFunctionsChart;
import com.codename1.demos.kitchen.charts.WeightDialChart;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class ChartsDemo extends Demo {
    
    public ChartsDemo(Form parentForm) {
        init("Charts", getGlobalResources().getImage("charts-demo-icon.png"), parentForm, "");
    }
    
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        if (isTablet()){
            demoContainer.setLayout(new TableLayout(2, 2));
        }
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-bar.png"),
                                                                "Bar Chart",
                                                                "The bar chart rendering class", e->{
                                                                    AbstractDemoChart chart = new SalesBarChart();
                                                                    showChart(chart);
                                                                }));
       
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-bubble.png"),
                                                                "Bubble Chart",
                                                                "The bubble chart rendering class", e-> {
                                                                    AbstractDemoChart chart = new ProjectStatusBubbleChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-combined-xy.png"),
                                                                "CombinedXY Chart",
                                                                "The combinedXY chart rendering class",e->{
                                                                    AbstractDemoChart chart = new CombinedTemperatureChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-cubic-line.png"),
                                                                "CunicLine Chart",
                                                                "The interpolated (cubic) line chart rendering",
                                                                " class", e->{
                                                                    AbstractDemoChart chart = new AverageCubicTemperatureChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-dial.png"),
                                                                "Dial Chart",
                                                                "The dial chart rendering class", e->{
                                                                    WeightDialChart chart = new WeightDialChart();
                                                                    showChart(chart);
                                                                }));

        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-donut.png"),
                                                                "Donut Chart",
                                                                "The donut chart rendering class", e->{
                                                                    BudgetDoughnutChart chart = new BudgetDoughnutChart();
                                                                    showChart(chart);

                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-line.png"),
                                                                "Line Chart",
                                                                "The linechart rendering class", e->{
                                                                    TrigonometricFunctionsChart chart = new TrigonometricFunctionsChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-pie.png"),
                                                                "Pie Chart",
                                                                "The pie chart rendering class", e->{
                                                                    BudgetPieChart chart = new BudgetPieChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-radar.png"),
                                                                "Radar Chart",
                                                                "The radar chart rendering class", e->{
                                                                    EmployeeChart chart = new EmployeeChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-scatter.png"),
                                                                "Scatter Chart",
                                                                "The scater chart rendering class", e->{
                                                                    ScatterChart chart = new ScatterChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("chart-time.png"),
                                                                "Time Chart",
                                                                "The Time chart rendering class", e->{
                                                                    SensorValuesChart chart = new SensorValuesChart();
                                                                    showChart(chart);
                                                                }));
        
        return demoContainer;
    }
            
    private void showChart(AbstractDemoChart demo) {
            Form chartForm = new Form(demo.getChartTitle(), new BorderLayout());
            Toolbar toolbar = chartForm.getToolbar();
            toolbar.setUIID("DemoToolbar");
            toolbar.getTitleComponent().setUIID("DemoTitle");
            
            Form lastForm = getCurrentForm();
            Style iconStyle = new Style();
            iconStyle.setFgColor(0x157EFB);
            Command backCommand = Command.create("", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, iconStyle),
                    e-> lastForm.showBack());
            
            toolbar.addCommandToLeftBar(backCommand);
            chartForm.add(BorderLayout.CENTER, demo.execute());
            chartForm.show();      
    }
}

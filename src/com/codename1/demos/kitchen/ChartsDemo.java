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
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class ChartsDemo extends Demo {
    private boolean drawOnMutableImages;


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
        
        ActionListener al = e->{
            System.out.println("change me i am here just for the debag");
        };
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-bar.png"),
                                                                "Bar Chart",
                                                                "The bar chart rendering class", e->{
                                                                    AbstractDemoChart chart = new SalesBarChart();
                                                                    showChart(chart);
                                                                }));
       
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-bubble.png"),
                                                                "Bubble Chart",
                                                                "The bubble chart rendering class", e-> {
                                                                    AbstractDemoChart chart = new ProjectStatusBubbleChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-combined-xy.png"),
                                                                "CombinedXY Chart",
                                                                "The combinedXY chart rendering class",e->{
                                                                    AbstractDemoChart chart = new CombinedTemperatureChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createAcordionComponent(getGlobalResources().getImage("chart-cubic-line.png"),
                                                                "CunicLine Chart",
                                                                "The interpolated (cubic) line chart rendering",
                                                                " class", e->{
                                                                    AbstractDemoChart chart = new AverageCubicTemperatureChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-dial.png"),
                                                                "Dial Chart",
                                                                "The dial chart rendering class", e->{
                                                                    WeightDialChart chart = new WeightDialChart();
                                                                    showChart(chart);
                                                                }));

        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-donut.png"),
                                                                "Donut Chart",
                                                                "The donut chart rendering class", e->{
                                                                    BudgetDoughnutChart chart = new BudgetDoughnutChart();
                                                                    showChart(chart);

                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-line.png"),
                                                                "Line Chart",
                                                                "The linechart rendering class", e->{
                                                                    TrigonometricFunctionsChart chart = new TrigonometricFunctionsChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-pie.png"),
                                                                "Pie Chart",
                                                                "The pie chart rendering class", e->{
                                                                    BudgetPieChart chart = new BudgetPieChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-radar.png"),
                                                                "Radar Chart",
                                                                "The radar chart rendering class", e->{
                                                                    EmployeeChart chart = new EmployeeChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-round.png"),
                                                                "Round Chart",
                                                                "The round chart rendering class", al));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-scatter.png"),
                                                                "Scatter Chart",
                                                                "The scater chart rendering class", e->{
                                                                    ScatterChart chart = new ScatterChart();
                                                                    showChart(chart);
                                                                }));
        
        demoContainer.add(builder.createRegularComponent(getGlobalResources().getImage("chart-time.png"),
                                                                "Time Chart",
                                                                "The Time chart rendering class", e->{
                                                                    SensorValuesChart chart = new SensorValuesChart();
                                                                    showChart(chart);
                                                                }));
        
        return demoContainer;
    }
    
    class ListOption {
        Class<AbstractDemoChart> chartClass;
        String name;
        
        ListOption(Class cls, String name){
            this.chartClass = cls;
            this.name = name;
        }
        
        public String toString(){
            return this.name;
        }
    }
    
            
    private void showChart(AbstractDemoChart demo) {

            demo.setDrawOnMutableImage(drawOnMutableImages);
            Form intent = wrap(demo.getChartTitle(), demo.execute());
            intent.getToolbar().getTitleComponent().setUIID("DemoTitle");
            if ( "".equals(intent.getTitle())){
                intent.setTitle(demo.getName());
            }
            Form lastForm = getCurrentForm();
            intent.getToolbar().setBackCommand("Menu", e -> lastForm.showBack());
            intent.getStyle().setBgColor(0x0);
            intent.getStyle().setBgTransparency(0xff);
            int numComponents = intent.getComponentCount();
            for (int i = 0; i < numComponents; i++) {
                intent.getComponentAt(i).getStyle().setBgColor(0x0);
                intent.getComponentAt(i).getStyle().setBgTransparency(0xff);
            }
            intent.show();
       
           
    }
    
    protected Form wrap(String title, Component c){
        c.getStyle().setBgColor(0xff0000);
        Form f = new Form(title);
        f.setLayout(new BorderLayout());
        if (drawOnMutableImages) {
            int dispW = Display.getInstance().getDisplayWidth();
            int dispH = Display.getInstance().getDisplayHeight();
            Image img = Image.createImage((int)(dispW * 0.8), (int)(dispH * 0.8), 0x0);
            Graphics g = img.getGraphics();
            c.setWidth((int)(dispW * 0.8));
            c.setHeight((int)(dispH * 0.8));
            c.paint(g);
            f.addComponent(BorderLayout.CENTER, new Label(img));
        } else {
          f.addComponent(BorderLayout.CENTER, c);
        }
        return f;
    }
    
}

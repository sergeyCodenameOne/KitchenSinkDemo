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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.convertToPixels;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class SalesDemo extends Demo {

    public SalesDemo(Form parentForm) {
        init("Sales", getGlobalResources().getImage("icon.png"), parentForm);
    }

    @Override
    public Component createDemo() {
        ScaleImageLabel imageLabel = new ScaleImageLabel(getDemoImage().scaled(CommonBehavior.getImageWidth(), CommonBehavior.getImageHeight()));
        Button button = new Button(getDemoId());
        button.addActionListener(e-> createAndShowForm());
        
        Container mainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        mainWindowComponent.setUIID("DemoComponent");
        return mainWindowComponent;
    }
    
    private void createAndShowForm(){
        Form SalesForm = new Form("Sales", new BorderLayout());

        Tabs tabs = new Tabs();

        tabs.addTab("Categories", FontImage.createMaterial(FontImage.MATERIAL_PIE_CHART, tabs.getUnselectedStyle()), createCategoriesContainer());
        tabs.addTab("Annual review", FontImage.createMaterial(FontImage.MATERIAL_SHOW_CHART, tabs.getUnselectedStyle()), createAnnualContainer());
        SalesForm.add(BorderLayout.CENTER, tabs);
        SalesForm.show();
    }
    
    private Container createCategoriesContainer(){
        
        Label firstHeadline = new Label("Category", "PieChartHeadline");
        Label secondHeadLine = new Label("Sales", "PieChartHeadline");
        
        TextField[][] textFieldsArray = createTextFields();
        TableLayout textFieldsLayout = new TableLayout(4, 2);
        Container textFields = new Container(textFieldsLayout).add(textFieldsLayout.createConstraint().widthPercentage(50), firstHeadline).
                                                               add(textFieldsLayout.createConstraint().widthPercentage(50), secondHeadLine).
                                                               add(textFieldsArray[0][0]).
                                                               add(textFieldsArray[0][1]).
                                                               add(textFieldsArray[1][0]).
                                                               add(textFieldsArray[1][1]).
                                                               add(textFieldsArray[2][0]).
                                                               add(textFieldsArray[2][1]);
        
        CategorySeries series = new CategorySeries("Sales");
        updatePieSeries(series, textFieldsArray);
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; j++) {
                textFieldsArray[i][j].addActionListener(e-> updatePieSeries(series, textFieldsArray));
            }
        }
        
        PieChart chart = new PieChart(series, CreatePieChartRenderer());
        
        ChartComponent chartComponent = new ChartComponent(chart){
            // Make an anonymous claas that overide calcPreferredSize to fit exactly a half of the screen.
            @Override
            protected Dimension calcPreferredSize(){
                
                int width = Display.getInstance().getDisplayWidth();
                int height = Display.getInstance().getDisplayHeight();
                return new Dimension(width, height / 2);
            }
        };
        
        Container categories = BorderLayout.north(chartComponent).add(BorderLayout.CENTER, textFields);
        return categories;
    }
    
    private Container createAnnualContainer(){
        return new Container();
    }
    
    private DefaultRenderer CreatePieChartRenderer(){
        DefaultRenderer renderer = new DefaultRenderer();
        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA};
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        renderer.setLabelsColor(0x000000);
        renderer.setShowLegend(false);
        renderer.setLabelsTextSize(convertToPixels(3));
        return renderer;
    }
    
    private TextField[][] createTextFields(){
        TextField[][] textFields = new TextField[3][2]; 
        
        textFields[0][0] = new TextField();
        textFields[0][0].setText("Products");
        textFields[0][0].setUIID("PieChartOddTextField");
        
        textFields[1][0] = new TextField();
        textFields[1][0].setText("Virtual");
        textFields[1][0].setUIID("PieChartEvenTextField");
        
        textFields[2][0] = new TextField();
        textFields[2][0].setText("Services");
        textFields[2][0].setUIID("PieChartOddTextField");
        
        
        textFields[0][1] = new TextField();
        textFields[0][1].setConstraint(TextArea.NUMERIC);
        textFields[0][1].setText("100.0");
        textFields[0][1].setUIID("PieChartOddTextField");
        
        textFields[1][1] = new TextField();
        textFields[1][1].setConstraint(TextArea.NUMERIC);
        textFields[1][1].setText("200.0");
        textFields[1][1].setUIID("PieChartEvenTextField");
        
        textFields[2][1] = new TextField();
        textFields[2][1].setConstraint(TextArea.NUMERIC);
        textFields[2][1].setText("300.0");
        textFields[2][1].setUIID("PieChartOddTextField");
        
        return textFields;
    }
    
    private void updatePieSeries(CategorySeries series, TextField[][] textFields){
        series.clear();
        for (int i = 0; i < 3; ++i){
            series.add(textFields[i][0].getText(), Double.valueOf(textFields[i][1].getText()));
            
        }
    }
}

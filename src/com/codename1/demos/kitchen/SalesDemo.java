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
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.convertToPixels;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableModel;
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

        // Toolbar add back button
        Toolbar toolBar = SalesForm.getToolbar();
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> getParentForm().show());
        
        // Toolbar add info button 
        toolBar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", "You can play videos either from remote or local sources very easily in Codename One, here we also " +
                        "show the ability to record a video that can play later.", "OK", null);
        });
        
        Tabs tabs = new Tabs();

        tabs.addTab("Categories", FontImage.createMaterial(FontImage.MATERIAL_PIE_CHART, tabs.getUnselectedStyle()), createCategoriesContainer());
        tabs.addTab("Annual review", FontImage.createMaterial(FontImage.MATERIAL_SHOW_CHART, tabs.getUnselectedStyle()), createAnnualContainer());
        SalesForm.add(BorderLayout.CENTER, tabs);
        SalesForm.show();
    }
    
    private Container createCategoriesContainer(){
        Object[][] dataCells = createDefaultCells();
        
        TableModel model = new DefaultTableModel(new String[] {"Category", "Sales"}, dataCells, true);
        Table dataTable = new Table(model) {
            @Override
            protected Component createCell(Object value, int row, int column, boolean editable) {
                
                Component cell = super.createCell(value, row, column, editable);
                if(row % 2 != 0) {
                    cell.getAllStyles().setBgColor(0xeeeeee);
                    cell.getAllStyles().setBgTransparency(255);
                }
                if (column == 1 && 0 <= row){
                    ((TextField)cell).setText(L10NManager.getInstance().formatCurrency(((Double)value).doubleValue()));
                    ((TextField)cell).addActionListener((e)->{
                        model.setValueAt(row, column, L10NManager.getInstance().parseCurrency(((TextField)cell).getText()));
                    });
                }
                return cell;
            }
            
            @Override
            protected TableLayout.Constraint createCellConstraint(Object value, int row, int column) {
                TableLayout.Constraint constraint =  super.createCellConstraint(value, row, column);
                constraint.setWidthPercentage(50);
                return constraint;
            }
        };
        dataTable.setDrawBorder(false);
        
        CategorySeries series = new CategorySeries("Sales");
        updatePieSeries(series, model);
        
        model.addDataChangeListener((rows, columns)-> updatePieSeries(series, model));
        
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
        
        Container categories = BorderLayout.north(chartComponent).add(BorderLayout.CENTER, dataTable);
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
    
    private Object[][] createDefaultCells(){
        Object[][] dataCells = new Object[][]{
            {"Products", new Double(10000)},
            {"Virtual", new Double(20000)},
            {"Services", new Double(30000)}
        };
            
        return dataCells;
    }
    
    private void updatePieSeries(CategorySeries series, TableModel dataModel){
        series.clear();
        for (int i = 0; i < dataModel.getRowCount(); ++i){
            String category = (String)dataModel.getValueAt(i, 0);
            Double value = (Double)dataModel.getValueAt(i, 1);
            series.add(category, value);
        }
    }
}

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
import static com.codename1.ui.FontImage.*;
import static com.codename1.ui.CN.*;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.LineChart;
import com.codename1.charts.views.PieChart;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableModel;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class SalesDemo extends Demo {

    public SalesDemo(Form parentForm) {
        init("Sales", getGlobalResources().getImage("icon.png"), parentForm,
                        "You can play videos either from remote or local sources very easily in Codename One, here we also " +
                        "show the ability to record a video that can play later.");
    }

    public Container createContentPane(){
        Tabs demoContainer = new Tabs();

        demoContainer.addTab("Categories", createMaterial(MATERIAL_PIE_CHART, demoContainer.getUnselectedStyle()), createCategoriesContainer());
        demoContainer.addTab("Annual review", createMaterial(MATERIAL_SHOW_CHART, demoContainer.getUnselectedStyle()), createAnnualContainer());
        return demoContainer;
    }
    
    private Container createCategoriesContainer(){
        Object[][] dataCells = createDefaultCategoryCells();
        
        TableModel model = new DefaultTableModel(new String[] {"Category", "Sales"}, dataCells, true){
            @Override
            public void setValueAt(int row, int column, Object o) {
                if (column == 1 && 0 <= row){
                    if (o instanceof String){
                        super.setValueAt(row, column, L10NManager.getInstance().parseCurrency(((String)o))); 
                    }
                }else {
                    super.setValueAt(row, column, o);                    
                }
            }
        };
        
        Table dataTable = new Table(model) {
            @Override
            protected Component createCell(Object value, int row, int column, boolean editable) {
                Component cell = super.createCell(value, row, column, editable);
                if(row == -1){
                    cell.setUIID("SalesTableHeader");
                }else if(row % 2 != 0) {
                    cell.setUIID("SalesTableOddRow");
                }else{
                    cell.setUIID("SalesTableEvenRow");
                }
                
                if (column == 1 && 0 <= row){
                    TextField tx = (TextField)cell;
                    tx.setConstraint(TextArea.DECIMAL);
                    tx.setText(L10NManager.getInstance().formatCurrency(((Double)value).doubleValue()));
                    tx.addActionListener((e)->{
                        model.setValueAt(row, column, tx.getText());
                        tx.setText(L10NManager.getInstance().formatCurrency((Double)model.getValueAt(row, column)));
                    });
                }
                return cell;
            }
            
            @Override
            protected TableLayout.Constraint createCellConstraint(Object value, int row, int column) {
                TableLayout.Constraint constraint = super.createCellConstraint(value, row, column);
                constraint.setWidthPercentage(50);
                return constraint;
            }
        };
        dataTable.setDrawBorder(false);
        
        CategorySeries series = new CategorySeries("Sales");
        
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
        
        updatePieSeries(series, model);
        model.addDataChangeListener((rows, columns)-> updatePieSeries(series, model));
        
        Container categories = BorderLayout.north(chartComponent).add(BorderLayout.CENTER, dataTable);
        return categories;
    }
    
    private Container createAnnualContainer(){
        Object dataCells[][] = createDefaultAnnualCells();
        
        TableModel model = new DefaultTableModel(new String[] {"Year", "Sales"}, dataCells, true){
            @Override
            public void setValueAt(int row, int column, Object o) {
                if (column == 1 && 0 <= row){
                    if (o instanceof String){
                        super.setValueAt(row, column, L10NManager.getInstance().parseCurrency(((String)o))); 
                    }
                }else {
                    super.setValueAt(row, column, o);                    
                }
            }
        };
        
        Table dataTable = new Table(model) {
            @Override
            protected Component createCell(Object value, int row, int column, boolean editable) {
                
                Component cell = super.createCell(value, row, column, editable);
                if(row == -1){
                    cell.setUIID("SalesTableHeader");
                }else if(row % 2 != 0) {
                    cell.setUIID("SalesTableOddRow");
                }else{
                    cell.setUIID("SalesTableEvenRow");
                }
                
                if (column == 1 && 0 <= row){
                    TextField tx = (TextField)cell;
                    tx.setConstraint(TextArea.DECIMAL);
                    tx.setText(L10NManager.getInstance().formatCurrency(((Double)value)));
                    tx.addActionListener((e)->{
                        model.setValueAt(row, column, tx.getText());
                        tx.setText(L10NManager.getInstance().formatCurrency((Double)model.getValueAt(row, column)));
                    });
                }else if (column == 0 && 0 <= row){
                    TextField tx = (TextField)cell;
                    tx.setConstraint(TextArea.DECIMAL);
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
        
        XYSeries annualSeries = new XYSeries("Sales");
        updateAnnualSeries(annualSeries, model);
        XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
        series.addSeries(annualSeries);
        
        XYMultipleSeriesRenderer renderer = createChartMultiRenderer(model);
        LineChart chart = new LineChart(series, renderer);
        ChartComponent chartComponent = new ChartComponent(chart){
            // Make an anonymous claas that overide calcPreferredSize to fit exactly a half of the screen.
            @Override
            protected Dimension calcPreferredSize(){
                
                int width = Display.getInstance().getDisplayWidth();
                int height = Display.getInstance().getDisplayHeight();
                return new Dimension(width, height / 2);
            }
        };
        
        model.addDataChangeListener((row, column)->{
                updateAnnualSeries(annualSeries, model);
                updateRendererMinMax(model, chart.getRenderer());
        });
        
        dataTable.setScrollableY(true);
        Container categories = BorderLayout.north(chartComponent).add(BorderLayout.CENTER, dataTable);
        return categories;
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
    
    private Object[][] createDefaultCategoryCells(){
        Object[][] dataCells = new Object[][]{
            {"Products", Double.valueOf(10000)},
            {"Virtual", Double.valueOf(20000)},
            {"Services", Double.valueOf(30000)}
        };
        return dataCells;
    }
    
    private Object[][] createDefaultAnnualCells(){
        Object[][] dataCells = new Object[][]{
            {2015, Double.valueOf(154143)},
            {2016, Double.valueOf(148591)},
            {2018, Double.valueOf(125123)},
            {2017, Double.valueOf(179525)},
            {2019, Double.valueOf(234130)},
            {2020, Double.valueOf(68123)}
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
    
    private void updateAnnualSeries(XYSeries annualSeries, TableModel dataTable){
        annualSeries.clear();
        for (int i = 0; i < dataTable.getRowCount(); ++i) {
            annualSeries.add(Double.valueOf((Integer)dataTable.getValueAt(i, 0)), (Double)dataTable.getValueAt(i, 1));
        }
    }
    
    private XYMultipleSeriesRenderer createChartMultiRenderer(TableModel tm) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(0x0060ff);
        r.setLineWidth(7);
        renderer.addSeriesRenderer(r);

        renderer.setLabelsColor(0xffffff);
        renderer.setBackgroundColor(0xeeeeee);
        renderer.setApplyBackgroundColor(true);
        renderer.setLabelsTextFont(Font.createTrueTypeFont("native:MainRegular", "native:MainRegular"));
        renderer.setLabelsTextSize(convertToPixels(3));
        renderer.setLabelsColor(0x000000);

        renderer.setZoomEnabled(false, false);
        renderer.setAxesColor(0x000000);

        renderer.setShowGrid(true);
        renderer.setXLabels(tm.getRowCount());
        renderer.setYLabels(tm.getRowCount());
        updateRendererMinMax(tm, renderer);
        return renderer;
    }
    
    private void updateRendererMinMax(TableModel tm, XYMultipleSeriesRenderer renderer) {
        double xmin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE;
        double ymin = Double.MAX_VALUE;
        double ymax = Double.MIN_VALUE;
        for(int i = 0 ; i < tm.getRowCount() ; ++i) {
            double currentX = (Integer)tm.getValueAt(i, 0);
            double currentY = (Double)tm.getValueAt(i, 1);
            xmin = Math.min(xmin, currentX);
            xmax = Math.max(xmax, currentX);
            ymin = Math.min(ymin, currentY);
            ymax = Math.max(ymax, currentY);
        }
        renderer.setXAxisMin(xmin);
        renderer.setXAxisMax(xmax);
        renderer.setYAxisMin(ymin);
        renderer.setYAxisMax(ymax);
    }
}

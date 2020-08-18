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

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import static com.codename1.ui.layouts.BorderLayout.CENTER_BEHAVIOR_CENTER;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.table.TableLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.ArrayList;
import java.util.List;

//TODO add picture.
public class LayoutsDemo extends Demo {
    
    private List<Component> colorLabelList;
    private Container colorsContainer;

    public LayoutsDemo(Form parentForm){
        init("Layouts", getGlobalResources().getImage("icon.png"), parentForm);
    }
    
    public Component createDemo(){
        ScaleImageLabel imageLabel = new ScaleImageLabel(getDemoImage().scaled(CommonBehavior.getImageWidth(), CommonBehavior.getImageHeight()));
        Button button = new Button(getDemoId());
        button.addActionListener(e-> {
            createForm().show();
        });
        
        Container mainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        mainWindowComponent.setUIID("DemoComponent");
        return mainWindowComponent;
    
    }
    
    private Form createForm(){
        Form themesForm = new Form(getDemoId(), new BorderLayout());
        Toolbar toolBar = themesForm.getToolbar();
        
        //add back button
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->{
            colorsContainer = null;
            colorLabelList = null;
            getParentForm().show();
        });
        
        //add info button
        toolBar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", "Layouts allow the UI of Codename One to adapt to the different resolutions and DPI's supported" +
                        " by the various OS's. This is just the tip of the iceberg. Layouts can be nested deeply and there are very " +
                        "complex layouts such as MiG, Group, GridBag etc. that aren't fully represented here...", "OK", null);
        });
        
        initContentPane(themesForm.getContentPane());      
        return themesForm;
    }
        
    private void initContentPane(Container contentPane){
        
        Button flow = new Button("Flow Layout");
        flow.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new FlowLayout());
            colorsContainer.animateLayout(1000);
        });
        
        Button flowCenter = new Button("Flow Center Layout");
        flowCenter.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new FlowLayout(Component.CENTER));
            colorsContainer.animateLayout(1000);
        });
        
        Button border = new Button("border Layout");
        border.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.removeAll();
            colorsContainer.setLayout(new BorderLayout());
            colorsContainer.add(BorderLayout.CENTER, colorLabelList.get(0)).
                            add(BorderLayout.WEST, colorLabelList.get(1)).
                            add(BorderLayout.EAST, colorLabelList.get(2)).
                            add(BorderLayout.NORTH, colorLabelList.get(3)).
                            add(BorderLayout.SOUTH, colorLabelList.get(4));
            colorsContainer.animateLayout(1000);
        });
        
        Button absoluteBorder = new Button("Absolute Border Layout");
        absoluteBorder.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.removeAll();
            colorsContainer.setLayout(new BorderLayout(CENTER_BEHAVIOR_CENTER));
            colorsContainer.add(BorderLayout.CENTER, colorLabelList.get(0)).
                            add(BorderLayout.WEST, colorLabelList.get(1)).
                            add(BorderLayout.EAST, colorLabelList.get(2)).
                            add(BorderLayout.NORTH, colorLabelList.get(3)).
                            add(BorderLayout.SOUTH, colorLabelList.get(4));
            colorsContainer.animateLayout(1000);
        });

        Button boxX = new Button("Box X Layout");
        boxX.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
            colorsContainer.animateLayout(1000);
        });
        
  
        Button boxY = new Button("Box Y Layout");
        boxY.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            colorsContainer.animateLayout(1000);
        });
        
        Button grid = new Button("Grid Layout");
        grid.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new GridLayout(1, 1));
            colorsContainer.animateLayout(1000);
        });
        
        Button simpleTable = new Button("Table Layout(simple)");
        simpleTable.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new TableLayout(3, 2));
            colorsContainer.removeAll();
            colorsContainer.addAll(colorLabelList.get(0),
                                   colorLabelList.get(1),
                                   colorLabelList.get(2),
                                   colorLabelList.get(3),
                                   colorLabelList.get(4));
            
            
            colorsContainer.animateLayout(1000);
        });
        
        Button complexTable = new Button("Table Layout(complex)");
        complexTable.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.removeAll();
            buildComplexTableUI(colorsContainer);
            colorsContainer.animateLayout(1000);
        });
        
        Button layered = new Button("Layered Layout");
        layered.addActionListener(e-> {
            resetMargin(colorsContainer);
            colorsContainer.setLayout(new LayeredLayout());
            
            // Increese the margin by 3 mm for every Component in the container for better
            //   visual effect of the LayeredLayout.
            setMarginForLayeredLayout(colorsContainer);
            colorsContainer.animateLayout(1000);
        });
        
        // Make a Button container 
        Container buttonList = BoxLayout.encloseY(flow,
                                                  flowCenter,
                                                  border,
                                                  absoluteBorder,
                                                  boxX,
                                                  boxY,
                                                  grid,
                                                  simpleTable,
                                                  complexTable,
                                                  layered);
        buttonList.setScrollableY(true);
  
        
        // Make some blank Labels with background colors from the CSS file.
        colorLabelList = new ArrayList<>();
        colorLabelList.clear();
        colorLabelList.add(new Label("                    ", "RedLabel"));
        colorLabelList.add(new Label("                    ", "BlueLabel"));
        colorLabelList.add(new Label("                    ", "GreenLabel"));
        colorLabelList.add(new Label("                    ", "YellowLabel"));
        colorLabelList.add(new Label("                    ", "CyanLabel"));

        // Make an anonymous claas that overide calcPreferredSize to fit exactly a half of the screen.
        // Altervatively you could use TableLayout instead of BorderLayout where i could explicitly define the height in percentages.
        //   or GridLayout that would divide the ContentPane by 2 for every Component within it. 
        colorsContainer = new Container (new BoxLayout(BoxLayout.Y_AXIS)){
            @Override
            protected Dimension calcPreferredSize(){
                
                int width = Display.getInstance().getDisplayWidth();
                int height = Display.getInstance().getDisplayHeight();
                return new Dimension(width, height / 2);
            }
        };
        colorsContainer.addAll( colorLabelList.get(0),
                                colorLabelList.get(1),
                                colorLabelList.get(2),
                                colorLabelList.get(3),
                                colorLabelList.get(4)
        );
        colorsContainer.setShouldCalcPreferredSize(true);
        
        contentPane.add(BorderLayout.NORTH, colorsContainer);
        contentPane.add(BorderLayout.CENTER, buttonList);
    }
    
    // Reset the margin for all the components inside the given container.
    private void resetMargin(Container colorsContainer){
        for(Component cmp : colorsContainer){
            cmp.getAllStyles().setMargin(0, 0, 0, 0);
        }
    }
    
    private void setMarginForLayeredLayout(Container colorsContainer){
        int margin = 0;
        for(Component cmp : colorsContainer){
            cmp.getAllStyles().setMargin(margin, margin, margin, margin);
            margin += Display.getInstance().convertToPixels(3);
        }
    }
    
    private void buildComplexTableUI(Container colorsContainer){
        TableLayout tl = new TableLayout(2, 3); 
        colorsContainer.setLayout(tl);
        colorsContainer.add(tl.createConstraint().widthPercentage(20),
                            colorLabelList.get(0));
        
        colorsContainer.add(tl.createConstraint().
                            horizontalSpan(2).heightPercentage(80).
                            verticalAlign(Component.CENTER).
                            horizontalAlign(Component.CENTER),
                                colorLabelList.get(1));

        colorsContainer.add(colorLabelList.get(2));

        colorsContainer.add(tl.createConstraint().
                            widthPercentage(60).
                            heightPercentage(20),
                                colorLabelList.get(3));

        colorsContainer.add(tl.createConstraint().
                            widthPercentage(20),
                                colorLabelList.get(4));
    }
}

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
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;


public class ContentBuilder {
    private static ContentBuilder instance = null;
    
    private ContentBuilder() {
    }
    
    public static ContentBuilder getInstance(){
        if (instance == null){
            instance = new ContentBuilder();
        }
        return instance;
    }
    
    public Component createComponent(Image image, String header, String firstLine, String body, ActionListener listener){
        Container demoContent = new AccordionComponent(image, header, firstLine, body, listener);
//        Accordion ac = new Accordion();
//        ScaleImageLabel contentImage = new ScaleImageLabel(image);
//        contentImage.addPointerDraggedListener(listener);
//        Label contentHeader = new Label(header, "DemoContentHeader");
//        Label contentFirstLine = new Label(firstLine, "DemoContentBody");
//        SpanLabel bodyy = new SpanLabel(body, "DemoContentBody");
//        ac.addContent(BoxLayout.encloseY(contentImage, contentHeader, contentFirstLine), bodyy);
//        ac.setUIID("DemoContentAccordion");
//        ac.addOnClickItemListener(listener);
        return demoContent;
    }
    
    public Component createComponent(Image image, String header, String firstLine, ActionListener listener){
        ScaleImageLabel contentImage = new ScaleImageLabel(image);
        contentImage.addPointerPressedListener(listener);
        contentImage.setUIID("DemoContentImage");
        Label contentHeader = new Label(header, "DemoContentHeader");
        Label contentFirstLine = new Label(firstLine, "DemoContentBody");
        
        Container demoContent = BoxLayout.encloseY(contentImage, contentHeader, contentFirstLine);
        contentImage.setWidth(demoContent.getWidth() - demoContent.getAllStyles().getPadding(Component.LEFT) - demoContent.getAllStyles().getPadding(Component.RIGHT) - contentImage.getAllStyles().getPadding(Component.LEFT) - contentImage.getAllStyles().getPadding(Component.RIGHT));
        demoContent.setUIID("DemoContentRegular");
        return demoContent;
    }
    
    private class AccordionComponent extends Container{
        private boolean isOpen = false;
        private Label firstLine;
        private SpanLabel body;
        private Image openedIcon;
        private Image closedIcon;
        

        private AccordionComponent(Image image, String header, String firstLine, String body, ActionListener listener) {
            super(new BorderLayout());   
            this.firstLine = new Label(firstLine, "DemoContentBody");
            this.body = new SpanLabel(body, "DemoContentBody");
            this.body.setHidden(true);
            
    
            setUIID("DemoContentAccordion");
            ScaleImageLabel contentImage = new ScaleImageLabel(image);
            contentImage.addPointerPressedListener(listener);
            contentImage.setUIID("DemoContentImage");
            Label contentHeader = new Label(header, "DemoContentHeader");

            Button showMoreButton = new Button("", FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "AccordionButton");
            Style buttonStyle = showMoreButton.getAllStyles();
            openedIcon = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_UP, buttonStyle);
            closedIcon = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, buttonStyle);
            showMoreButton.addActionListener(e->{
                if(isOpen){
                    isOpen = false;
                    showMoreButton.setIcon(closedIcon);
                    this.body.setHidden(true);
                    animateLayout(200);
                }else{
                    isOpen = true;
                    showMoreButton.setIcon(openedIcon);
                    this.body.setHidden(false);
                    animateLayout(200);
                }
            });

            add(BorderLayout.NORTH, contentImage);
            add(BorderLayout.WEST, contentHeader);
            add(BorderLayout.EAST, showMoreButton);
            add(BorderLayout.SOUTH, BoxLayout.encloseY(this.firstLine, this.body));
        }
    }
}

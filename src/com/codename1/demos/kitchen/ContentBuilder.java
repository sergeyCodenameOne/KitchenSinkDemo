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

import com.codename1.components.ScaleImageButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.ComponentSelector.select;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.List;


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
        return demoContent;
    }
    
    public Component createComponent(Image image, String header, String firstLine, ActionListener listener){
        ScaleImageButton contentImage = new ScaleImageButton(image){
            @Override
            protected Dimension calcPreferredSize() {
                
                Dimension preferredSize =  super.calcPreferredSize(); 
                preferredSize.setHeight(preferredSize.getHeight() * 4);
                return preferredSize;
            }
            
        };
        contentImage.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);

        contentImage.addActionListener(listener);
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
        private Button openClose;
        
        /**
         * Demo component that have more then one line of description.
         * 
         * @param image the image of the component.
         * @param header the header of the component. 
         * @param firstLine first line of description.
         * @param body the rest of the description.
         * @param listener add ActionListener to the image of the component.
         */
        private AccordionComponent(Image image, String header, String firstLine, String body, ActionListener listener) {
            super(new BorderLayout());   
            this.firstLine = new Label(firstLine, "DemoContentBody");
            this.body = new SpanLabel(body, "DemoContentBody");
            this.body.setHidden(true);
            
            setUIID("DemoContentAccordion");
            ScaleImageButton contentImage = new ScaleImageButton(image){
                @Override
                protected Dimension calcPreferredSize() {

                    Dimension preferredSize =  super.calcPreferredSize(); 
                    preferredSize.setHeight(preferredSize.getHeight() * 4);
                    return preferredSize;
                }

            };
            contentImage.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
            contentImage.addActionListener(listener);
            contentImage.setUIID("DemoContentImage");
            Label contentHeader = new Label(header, "DemoContentHeader");

            Style buttonStyle = UIManager.getInstance().getComponentStyle("AccordionButton");
            openedIcon = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_UP, buttonStyle);
            closedIcon = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, buttonStyle);
            openClose = new Button("", closedIcon, "AccordionButton");
            openClose.addActionListener(e->{
                if(isOpen){
                    close();
                }else{
                    open();
                }
            });

            add(BorderLayout.NORTH, contentImage);
            add(BorderLayout.WEST, contentHeader);
            add(BorderLayout.EAST, openClose);
            add(BorderLayout.SOUTH, BoxLayout.encloseY(this.firstLine, this.body));
        }
        
        public void open(){
            // Select all AccordionComponent objects to close them when we open another one. 
            List<Component> accordionList = select("DemoContentAccordion").asList();
            for(Component currComponent: accordionList){
                ((AccordionComponent)currComponent).close();
        }
                    
            if (!isOpen){
                isOpen = true;
                openClose.setIcon(openedIcon);
                body.setHidden(false);
                animateLayout(200);
            }
        }
        
        public void close(){
            if (isOpen){
                isOpen = false;
                openClose.setIcon(closedIcon);
                body.setHidden(true);
                animateLayout(200);
            }
        } 
    }
}

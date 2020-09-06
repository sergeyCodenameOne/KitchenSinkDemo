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


import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.googlemaps.MapContainer.MapObject;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.maps.Coord;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class MapsDemo extends Demo {
    
    boolean tapDisabled = false;
    MapObject sydney;
    
    
    public MapsDemo(Form parentForm) {
        init("Maps", getGlobalResources().getImage("demo-maps.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("map-google-component.png"),
                                                                "Google Map",
                                                                "Google Map class", e->{
                                                                    showDemo("Google Map", createGoogleMapComponent());
                                                                }));
        return demoContainer;
    }
    
    private Component createGoogleMapComponent(){
        final MapContainer cnt = new MapContainer("AIzaSyAvMh8_ihHqqAOGPN7hXPUdbSHEPA9XOhI");

        Button btnMoveCamera = new Button("Move Camera");
        btnMoveCamera.addActionListener(e->{
            cnt.setCameraPosition(new Coord(-33.867, 151.206));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        Button btnAddMarker = new Button("Add Marker");
        btnAddMarker.addActionListener(e->{

            cnt.setCameraPosition(new Coord(41.889, -87.622));
            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCameraPosition(),
                    "Hi marker",
                    "Optional long description",
                     evt -> {
                             ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                     }
            );

        });

        Button btnAddPath = new Button("Add Path");
        btnAddPath.addActionListener(e->{

            cnt.addPath(
                    cnt.getCameraPosition(),
                    new Coord(-33.866, 151.195), // Sydney
                    new Coord(-18.142, 178.431),  // Fiji
                    new Coord(21.291, -157.821),  // Hawaii
                    new Coord(37.423, -122.091)  // Mountain View
            );
        });

        Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e->{
            cnt.clearMapLayers();
        });

        cnt.addTapListener(e->{
            TextField enterName = new TextField();
            Container wrapper = BoxLayout.encloseY(new Label("Name:"), enterName);
            InteractionDialog dlg = new InteractionDialog("Add Marker");
            dlg.getContentPane().add(wrapper);
            enterName.setDoneListener(e2->{
                String txt = enterName.getText();
                cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(e.getX(), e.getY()),
                        enterName.getText(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+txt, FontImage.MATERIAL_PLACE);
                        }
                );
                dlg.dispose();
            });
            dlg.showPopupDialog(new Rectangle(e.getX(), e.getY(), 10, 10));
            enterName.startEditingAsync();
        });

        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt),
                BorderLayout.south(
                        FlowLayout.encloseBottom(btnMoveCamera, btnAddMarker, btnAddPath, btnClearAll)
                )
        );
        return root;
    }
}

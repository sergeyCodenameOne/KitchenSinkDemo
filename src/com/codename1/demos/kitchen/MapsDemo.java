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


import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.maps.MapComponent;
import com.codename1.maps.layers.PointLayer;
import com.codename1.maps.layers.PointsLayer;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.IOException;

public class MapsDemo extends Demo {
    
    public MapsDemo(Form parentForm) {
        init("Maps", getGlobalResources().getImage("demo-maps.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("map-component.png"),
                                                                "Map Component",
                                                                "The Map Component class", e->{
                                                                    
                                                                    showDemo("Map Component", createMapComponent());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("map-google-component.png"),
                                                                "Google Map",
                                                                "Google Map class", e->{
                                          
                                                                }));
        return demoContainer;
    }
    
    private Component createMapComponent(){
        
        MapComponent mc = new MapComponent();

        try {
            //get the current location from the Location API
            Location loc = LocationManager.getLocationManager().getCurrentLocation();

            Coord lastLocation = new Coord(loc.getLatitude(), loc.getLongtitude());
            Image i = getGlobalResources().getImage("maps-pin.png");
            PointsLayer pl = new PointsLayer();
            pl.setPointIcon(i);
            PointLayer p = new PointLayer(lastLocation, "You Are Here", i);
            p.setDisplayName(true);
            pl.addPoint(p);
            mc.addLayer(pl);
         } catch (IOException ex) {
            ex.printStackTrace();
         }
         mc.zoomToLayers();

        return mc;
    }
}

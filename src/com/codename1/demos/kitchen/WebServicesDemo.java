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
import com.codename1.components.ToastBar;
import com.codename1.io.rest.RequestBuilder;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.URLImage;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;



import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.List;
import java.util.Map;


public class WebServicesDemo extends Demo {
    private static final String WEBSERVICE_URL =  "https://www.codenameone.com/files/kitchensink/dogs/list.json";
    private EncodedImage placeholder;
    
    
    public WebServicesDemo(Form parentForm) {
        init("WebServices", getGlobalResources().getImage("icon.png"), parentForm,
                        "Webservices tutorial showing off a faux dog picture set and the ability to browse thru images from the " +
                        "network using ImageViewer.");
    }
    
    @Override
    public Container createContentPane() {
        if(placeholder == null) {
            Image tempPlaceHolder = getGlobalResources().getImage("blured-puppy.jpg");
            placeholder = EncodedImage.createFromImage(tempPlaceHolder, true);
        }
        InfiniteContainer webServicesContainer = new InfiniteContainer(10) {
            String nextURL = WEBSERVICE_URL;            
            
            @Override 
            public Component[] fetchComponents(int index, int amount) {
                // pull to refresh resets the position
                if(index == 0) {
                    nextURL = WEBSERVICE_URL;
                }
                // nextUrl is null when there is no more data to fetch.
                if(nextURL == null) {
                    return null;
                }
                
                // Request the data from the server.
                RequestBuilder rb = Rest.get(nextURL).acceptJson();
                Response<Map> resultData = rb.getAsJsonMap();
                if(resultData.getResponseCode() != 200) {
                    callSerially(() -> {
                        ToastBar.showErrorMessage("Error code from the server");
                    });
                    return null;
                }
                
                List itemList = (List)resultData.getResponseData().get("items");
                nextURL = (String)resultData.getResponseData().get("nextPage");
                
                if(itemList == null) {
                    return null;
                }
                
                int itemsCount = itemList.size();
                Component[] result = new Component[itemsCount];
                
                for(int i = 0; i < itemsCount; ++i) {
                    // Get all the necessary data.
                    Map<String, String> m = (Map<String, String>)itemList.get(i);
                    String title = m.get("title");
                    String url = m.get("url");
                    String fileName = url.substring(url.lastIndexOf("/") + 1);
                    URLImage ButtonImage = URLImage.createToStorage(placeholder, fileName, url, URLImage.RESIZE_SCALE_TO_FILL);
                    
                    // Build the components.
                    ScaleImageButton button;
                    if (isTablet()){
                        button = new ScaleImageButton(ButtonImage){
                            @Override
                            protected Dimension calcPreferredSize() {                        
                                Dimension dim = new Dimension(getDisplayWidth() / 4, getDisplayHeight() / 4);
                                return dim;
                            }
                        };
                    }else{
                        button = new ScaleImageButton(ButtonImage);
                    }
                    
                    button.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                    Label titleLabel = new Label(title, "WebServicesButtonTitle");
                    result[i] = LayeredLayout.encloseIn(button, 
                            BorderLayout.south(titleLabel));
                    
                }
                return result;
            }
        };
        
        if(isTablet()) {
            webServicesContainer.setLayout(GridLayout.autoFit());
        }
        
        return webServicesContainer;
    }
    
}

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

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Storage;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.IntProperty;
import com.codename1.properties.ListProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.URLImage;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Label;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.EventDispatcher;

import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
        
        Set pages = new HashSet();
        List<Item> allItemsList = new ArrayList();
        
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
                Response<Map> resultData = Rest.get(nextURL).acceptJson().getAsJsonMap();
                ItemList itemListData = new ItemList();
                itemListData.getPropertyIndex().populateFromMap(resultData.getResponseData());

                        
                if(resultData.getResponseCode() != 200) {
                    callSerially(() -> {
                        ToastBar.showErrorMessage("Error code from the server");
                    });
                    return null;
                }
       
                nextURL = itemListData.nextPage.get();
                String currPage = itemListData.page.get();
                int itemsCount = itemListData.elements.get();
                
                if(itemListData.items.asList() == null) {
                    return null;
                }
                
                List<Item> itemList = new ArrayList();
                for(Map currItemMap : itemListData.items.asList()){
                    Item currItem = new Item();
                    currItem.getPropertyIndex().populateFromMap(currItemMap);
                    itemList.add(currItem);
                }
                
                if (!pages.contains(currPage)){
                    pages.add(currPage);                
                    allItemsList.addAll(itemList);
                }
                                
                Component[] result = new Component[itemsCount];
                
                for(int i = 0; i < itemsCount; ++i) {
                    // Get all the necessary data.
                    Item currItem = itemList.get(i);

                    String title = currItem.title.get();
                    String url = currItem.url.get();
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
                    
                    button.addActionListener(e->{
                        int currButtonIndex = button.getParent().getParent().getComponentIndex(button.getParent());
                        Form viewerForm = new Form("", new BorderLayout());
                        viewerForm.getToolbar().setBackCommand("", ee-> button.getComponentForm().show());
                        ImageList model = new ImageList(allItemsList, currButtonIndex);
                        ImageViewer imageViewer = new ImageViewer(model.getItemAt(currButtonIndex));
                        imageViewer.setImageList(model);
                        
                        viewerForm.setTitle(title);
                        SpanLabel details = new SpanLabel(currItem.details.get(), "WebServicesDetails");
                        viewerForm.add(BorderLayout.SOUTH, details);
                        viewerForm.add(BorderLayout.CENTER, imageViewer);
                        
                        // Refresh the title and the details when switching between the images.
                        model.addSelectionListener((oldIndex, newIndex)->{
                            String currTitle = model.getTitle(newIndex) + " " + (newIndex + 1) + " of " + model.getSize();
                            String currDetails = model.getDetails(newIndex);
                            viewerForm.setTitle(currTitle);
                            details.setText(currDetails);
                            viewerForm.revalidate();
                        });
                        viewerForm.setTitle(title + " " + (currButtonIndex + 1) + " of " + model.getSize());
                        viewerForm.show();
                    });
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
    
    /**
     * Image model for the ImageViewer
     */
    private class ImageList implements ListModel<Image>{
        private List<Item> itemList;
        private int selection;
        private EventDispatcher selectionListeners = new EventDispatcher();

        public ImageList(List<Item> itemList, int selection) {
            this.itemList = itemList;
            this.selection = selection;
        }
        
        @Override
        public Image getItemAt(int index) {
            Item item = itemList.get(index);
            String url = item.url.get();
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            Image currImage;
            if (!existsInStorage(getAppHomePath() + fileName)){
                currImage = URLImage.createToStorage(placeholder, fileName, url, URLImage.RESIZE_SCALE_TO_FILL);
            }else{
                currImage = (Image)Storage.getInstance().readObject(fileName);
            }
            return currImage;
        }
        
        @Override
        public void addSelectionListener(SelectionListener l) {
            selectionListeners.addListener(l);
        }

        @Override
        public void removeSelectionListener(SelectionListener l) {
            selectionListeners.removeListener(l);
        }
        @Override
        public int getSelectedIndex() {
            return selection;
        }

        @Override
        public int getSize() {
            return itemList.size();
        }

        @Override
        public void setSelectedIndex(int index) {
            int oldIndex = selection;
            selection = index;
            selectionListeners.fireSelectionEvent(oldIndex, selection);
        }
        
        public String getTitle(int index){
            return itemList.get(index).title.get();
        }
        
        public String getDetails(int index){
            return itemList.get(index).details.get();
        }

        @Override
        public void removeDataChangedListener(DataChangedListener l) {
            
        }
        
        @Override
        public void addDataChangedListener(DataChangedListener l) {
            
        }
        
        @Override
        public void addItem(Image item) {
            
        }
        
        @Override
        public void removeItem(int index) {
            
        }
    }
    
    private class ItemList implements PropertyBusinessObject {
        public final Property<String, ItemList> title = new Property<>("title");
        public final IntProperty<ItemList> elements = new IntProperty<>("elements");
        public final Property<String, ItemList> copyright = new Property<>("copyright");
        public final Property<String, ItemList>  page = new Property<>("page");
        public final Property<String, ItemList> nextPage = new Property<>("nextPage");
        public final ListProperty<Map, ItemList> items = new ListProperty<>("items");
        
        private final PropertyIndex idx = new PropertyIndex(this, "Item", title, elements, copyright, page, nextPage, items);
        
        @Override 
        public PropertyIndex getPropertyIndex() {
            return idx;     
        }
    }
    
    private class Item implements PropertyBusinessObject{
        public final Property<String, ItemList> title = new Property<>("title");
        public final Property<String, ItemList> details = new Property<>("details");
        public final Property<String, ItemList> url = new Property<>("url");
        public final Property<String, ItemList> thumb = new Property<>("thumb");
        
        private final PropertyIndex idx = new PropertyIndex(this, "Item", title, details, url, thumb);
        
        @Override 
        public PropertyIndex getPropertyIndex() {
            return idx;     
        }
    }
    
}

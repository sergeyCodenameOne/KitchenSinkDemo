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

import static com.codename1.ui.CN.*;
import com.codename1.components.Accordion;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.SplitPane;
import com.codename1.components.ToastBar;
import com.codename1.demos.kitchen.charts.DemoCharts;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.IntProperty;
import com.codename1.properties.ListProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import static com.codename1.ui.FontImage.createMaterial;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ContainersDemo extends Demo{
    
    public ContainersDemo(Form parentForm) {
        init("Containers", getGlobalResources().getImage("containers-demo.png"), parentForm, "");
    }
     
    @Override
    public Container createContentPane() {
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("accordion.png"),
                                                                "Accordion",
                                                                "This Accordion ui pattern is a vertically",
                                                                "stacked list of items. Each items can be opened/closed to reveal more content similar to a Tree however "+
                                                                "unlike the the Tree the Accordion is designed to include containers or arbitrary components rather than model "+
                                                                "based data.\n\nThis makes the Accordion more convenient as a tool for folding/collapsing UI elements known in "+
                                                                "advance whereas a tree makes more sense as a tool to map data e.g filesystem structure, XML hierarchy etc.\n\n"+
                                                                "Note that the Accordion like many composite components in Codename One is scrollable by default which means "+
                                                                "you should use it within a non-scrollable hierarchy. If you wish to add it into a scrollable Container you "+
                                                                "should disable it's default scrollability using setScrollable(false).",
                                                                e->{
                                                                    showDemo("Accordion", createAccordionDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("infinite-container.png"),
                                                                "Infinite Container",
                                                                "This abstract Container can scroll",
                                                                "indefinitely (or at least until we run out of data). This class uses the "+
                                                                "InfiniteScrollAdapter to bring more data and the pull to refresh feature to "+
                                                                "refresh current displayed data.\n\nThe sample code shows the usage of the nestoria "+
                                                                "API to fill out an infinitely scrolling list.", 
                                                                e->{
                                                                    showDemo("Infinite Container", createInfiniteContainerDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("split-pane.png"),
                                                                "Split Pane",
                                                                "A split pane can either be horizontal or",
                                                                "vertical, and provides a draggable divider between two components. If the orientation is HORIZONTAL_SPLIT, "+
                                                                "then the child components will be laid out horizontally (side by side with a vertical bar as a divider). If "+
                                                                "the orientation is VERTICAL_SPLIT, then the components are laid out vertically. One above the other.\n\nThe bar "+
                                                                "divider bar includes to collapse and expand the divider also.", 
                                                                e->{
                                                                    showDemo("Split Pane", createSplitPaneDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("tabs.png"),
                                                                "Tabs",
                                                                "A component that lets the user switch",
                                                                "between a group if components by clicking on a tab with a given "+
                                                                "title and/or icon.\n\nTabs/components are added to a Tabs object by using the addTab and insertTab methods. "+
                                                                "A tab is represented by an index corresponding to the position it was added in, where the first tab has an "+
                                                                "index equal to 0 and the last tab has an index equal to the tab count minus 1. The Tabs uses a "+
                                                                "SingleSelectionModel to represent the set of tab indices and the currently selected index. If the tab "+
                                                                "count is greater that 0, then there will always be a selected index, which by default will be initialized "+
                                                                "to the first tab. If the tab count is 0, then the selected index will be -1. A simple Tabs looks like a "+
                                                                "bit like this.", 
                                                                e->{
                                                                    showDemo("Tabs", createTabsDemo());
                                                                }));
        
        return demoContainer;
    }
    
    private Container createAccordionDemo(){
        Accordion accordion = new Accordion();
        accordion.addContent("Item1", new SpanLabel("The quick brown fox jumps over the lazy dog\n"
                + "The quick brown fox jumps over the lazy dog"));
        accordion.addContent("Item2", new SpanLabel("The quick brown fox jumps over the lazy dog\n"
                + "The quick brown fox jumps over the lazy dog\n "
                + "The quick brown fox jumps over the lazy dog\n "
                + "The quick brown fox jumps over the lazy dog\n "
                + ""));

        accordion.addContent("Item3", BoxLayout.encloseY(new Label("Label"), new TextField(), new Button("Button"), new CheckBox("CheckBox")));
        return accordion;
    }
    
    private Container createInfiniteContainerDemo(){
        final String firstURL =  "https://www.codenameone.com/files/kitchensink/dogs/list.json";
        Image tempPlaceHolder = getGlobalResources().getImage("blured-puppy.jpg");
        EncodedImage placeholder;placeholder = EncodedImage.createFromImage(tempPlaceHolder, true);

        InfiniteContainer infiniteContainer = new InfiniteContainer(10) {
            String nextURL = firstURL;            

            @Override 
            public Component[] fetchComponents(int index, int amount) {
                // pull to refresh resets the position
                if(index == 0) {
                    nextURL = firstURL;
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
                    callSerially(()-> {
                        ToastBar.showErrorMessage("Error code from the server");
                    });
                    return null;
                }

                nextURL = itemListData.nextPage.get();
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
                Component[] result = new Component[itemsCount];

                for(int i = 0; i < itemsCount; ++i) {
                    // Get all the necessary data.
                    Item currItem = itemList.get(i);

                    String title = currItem.title.get();
                    String url = currItem.url.get();
                    String fileName = url.substring(url.lastIndexOf("/") + 1);
                    URLImage image = URLImage.createToStorage(placeholder, fileName, url, URLImage.RESIZE_SCALE_TO_FILL);

                    // Build the components.
                    ScaleImageLabel imageLabel = new ScaleImageLabel(image){
                        @Override
                        protected Dimension calcPreferredSize() {
                            Dimension dm = super.calcPreferredSize();
                            dm.setHeight(convertToPixels(30));
                            return dm;
                        }
                    };
                    imageLabel.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

                    result[i] = LayeredLayout.encloseIn(imageLabel, 
                            BorderLayout.south(new Label(title, "InfiniteComponentTitle")));
                }
                return result;
            }
        };
        return BorderLayout.center(infiniteContainer);
    }
    
    private Container createTabsDemo(){
        Tabs tabsContainer = new Tabs();
        tabsContainer.setUIID("DemoTabsContainer");
        tabsContainer.addTab("Categories", FontImage.MATERIAL_PIE_CHART, 3,
                DemoCharts.createCategoriesContainer());
        
        tabsContainer.addTab("Annual review", createMaterial(FontImage.MATERIAL_SHOW_CHART, UIManager.getInstance().getComponentStyle("Tab")),
                DemoCharts.createAnnualContainer());
        return BorderLayout.center(tabsContainer);
    }
    
    private Container createSplitPaneDemo(){
        Container redContainer = new Container();
        redContainer.setUIID("RedContainer");
        Container blueContainer = new Container();
        blueContainer.setUIID("BlueContainer");
        Container greenContainer = new Container();
        greenContainer.setUIID("GreenContainer");
         
        Container redBlueContainer = new SplitPane(new SplitPane.Settings().orientation(SplitPane.VERTICAL_SPLIT), redContainer, blueContainer);
        Container demoContainer = new SplitPane(new SplitPane.Settings(), redBlueContainer, greenContainer);
        demoContainer.setUIID("SplitPaneDemoContainer");
        return BorderLayout.center(demoContainer);
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

 

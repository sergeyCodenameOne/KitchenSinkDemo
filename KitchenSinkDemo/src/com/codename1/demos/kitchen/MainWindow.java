package com.codename1.demos.kitchen;

import com.codename1.ui.Button;
import static com.codename1.ui.CN.execute;
import static com.codename1.ui.CN.getPlatformName;
import static com.codename1.ui.CN.isNativeShareSupported;
import static com.codename1.ui.CN.share;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;


public class MainWindow {
    private static Form gridForm = null;
    private static Form boxForm = null;
    
    public static Form buildGridForm(Resources theme){
        Form gridMainWindow = new Form("Kitchen Sink", new GridLayout(3));
        initToolBar(gridMainWindow.getToolbar());
        
        
        //create demos
        Demo themeDemo = new ThemesDemo(gridMainWindow, theme);
        Demo layoutsDemo = new LayoutsDemo(gridMainWindow, theme);    
        
        //add demos components
        gridMainWindow.add(themeDemo.makeComponent());
        gridMainWindow.add(layoutsDemo.makeComponent());
        
        gridForm = gridMainWindow;
        return gridMainWindow;
    }
    
    public static Form buildBoxForm(Resources theme){
        
        Form boxMainWindow = new Form("Kitchen Sink", BoxLayout.y());
        initToolBar(boxMainWindow.getToolbar());
        // add component
        
        
        boxForm = boxMainWindow;
        return boxMainWindow;
    }
    
    //TODO add search and reorder butten
    private static void initToolBar(Toolbar toolbar){
        Label sideBarTitle = new Label("KitchenSink Demo");
        sideBarTitle.setUIID("SideBarTitle");
                
        Container sideBarTitleArea = BorderLayout.center(sideBarTitle);
        sideBarTitleArea.setUIID("SideBarTitleArea");
        toolbar.addComponentToSideMenu(sideBarTitleArea);

        // add side bar components        
        toolbar.addMaterialCommandToSideMenu("CodeNameOne.com", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/")); 
        toolbar.addMaterialCommandToSideMenu("Getting Started", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/files/developer-guide.pdf"));
        toolbar.addMaterialCommandToSideMenu("JavaDoc (REFERENCE)", FontImage.MATERIAL_WEB, e -> execute("https://www.codenameone.com/javadoc/"));
        toolbar.addMaterialCommandToSideMenu("Source Code", FontImage.MATERIAL_WEB, e -> execute("https://github.com/codenameone/KitchenSink"));

        if(isNativeShareSupported() && getAppstoreURL() != null) {
            toolbar.addMaterialCommandToSideMenu("Spread the Word!", FontImage.MATERIAL_SHARE, e -> {
                share("Check out the kitchen sink app from Codename One: " + getAppstoreURL(), null, null);
            });
        }
        toolbar.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {
                Dialog.show("About", "KitchenSink provides an overview of the core Codename One capaiblities. "
                + "Codename One allows Java developers to create native mobile applications that work everywhere!", "OK", null);
        });
    }
    
    
    private static String getAppstoreURL() {
        if(getPlatformName().equals("ios")) {
            return "https://itunes.apple.com/us/app/kitchen-sink-codename-one/id635048865";
        }
        if(getPlatformName().equals("and")) {
            return "https://play.google.com/store/apps/details?id=com.codename1.demos.kitchen";
        }
        return null;
    }
    
}
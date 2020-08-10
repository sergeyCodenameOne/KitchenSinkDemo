/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.kitchen;

import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.createStorageInputStream;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;


//TODO when finish to add properties change the idx initilization.
//TODO add picture.
public class ThemesDemo extends Demo{
  
    public final PropertyIndex idx = new PropertyIndex(this, "ThemesDemo", super.id, super.image, super.form, super.fatherForm);
    
    public ThemesDemo(Form fatherForm, Resources resources){
        super.id.set("Themes");
        super.image.set(resources.getImage("icon.png"));
        super.fatherForm.set(fatherForm);
        super.resource.set(resources);
        super.form.set(createForm(fatherForm, resources));
    }
     
    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
    
    public Form createForm(Form fatherForm, Resources resources){
        Form themesForm = new Form("Themes", new BoxLayout(BoxLayout.Y_AXIS));
        Toolbar tb = themesForm.getToolbar();
        
        //add back button
        FontImage backIcon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK,
                UIManager.getInstance().getComponentStyle("BackCommand"));
        tb.addCommandToLeftBar("",backIcon, e->{
            fatherForm.show();
        });
        
        //add info button
        FontImage infoIcon = FontImage.createMaterial(FontImage.MATERIAL_INFO,
                UIManager.getInstance().getComponentStyle("InfoCommand"));
        tb.addCommandToRightBar("",infoIcon, e->{
            Dialog.show("information", "Themes can be updated dynamically, layered and downloaded off of the internet. Pretty much everything" +
            "in Codename One can be updated via the themes" +
            "In this section we show all of the above with dynamic theme downloads, updates and layers.", "OK", null);
        });
        
        themesForm.add(makeTheme("Grape", "/grapeTheme", backIcon));
        
        return themesForm;
    }
    
    
    //TODO understand how it works and fix it. 
    private Component makeTheme(String name, String themeUrl, Image themeImage){
        Label imageLabel = new Label(themeImage);
        Button button = new Button(name);
//        button.addActionListener(e-> {
//            
//        
//            try(InputStream is = createStorageInputStream("Grape")){
//                Resources res = Resources.open(is);
//        
//            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
//            }
//            catch(IOException eASD){
//                System.out.println("asdasdasd");
//            }
//            
//            
//            
////            UIManager.getInstance().addThemeProps(resource.get().getTheme("/grapeTheme.res"));
//            form.get().refreshTheme();
//        });
        
        Container themeComponent = BoxLayout.encloseY(imageLabel, button);
        return themeComponent;
    }
}

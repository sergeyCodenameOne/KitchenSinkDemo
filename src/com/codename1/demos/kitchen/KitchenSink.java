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
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import static com.codename1.ui.Button.setButtonRippleEffectDefault;
import com.codename1.ui.CN;
import com.codename1.ui.Display;
import java.io.IOException;

public class KitchenSink {
    
    private Form current;
    private Resources theme;
    private boolean isDarkMode = false;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);
        
        theme = UIManager.initNamedTheme("/theme", "Theme");
        
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);
        
        setButtonRippleEffectDefault(false);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
     
        applyDarkMode();
        MainWindow mw = new MainWindow();
        mw.buildForm().show();
    }
    
    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }
    
    private void applyDarkMode(){
        new Thread() {
            public void run() { 
                while(true){
                    try{
                        // check every 0.5 seconds if the user set the dark mode.
                        sleep(500);
                    }catch(InterruptedException error){
                        Log.e(error);
                    }
                    if(isDarkMode){
                        if (CN.isDarkMode()!= null && !CN.isDarkMode()){
                            isDarkMode = false;
                            CN.setDarkMode(false);
                            try {
                                Resources theme = Resources.openLayered("/theme");
                                UIManager.getInstance().addThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
                            } catch(IOException e){
                                Log.e(e);
                            }
                            Display.getInstance().getCurrent().refreshTheme();
                        }
                    }else{
                        if (CN.isDarkMode()!= null && CN.isDarkMode()){
                            isDarkMode = true;
                            CN.setDarkMode(true);
                            try {
                                Resources darkTheme = Resources.openLayered("/dark-theme");
                                UIManager.getInstance().addThemeProps(darkTheme.getTheme(darkTheme.getThemeResourceNames()[0]));
                            } catch(IOException e){
                                Log.e(e);
                            }
                            Display.getInstance().getCurrent().refreshTheme();
                        }
                    }
                }
            }
        }.start();
    }
}

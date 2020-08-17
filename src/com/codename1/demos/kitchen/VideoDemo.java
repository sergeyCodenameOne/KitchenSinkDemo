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

import com.codename1.capture.Capture;
import com.codename1.components.MediaPlayer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.IOException;


public class VideoDemo extends Demo {
    private static final String CAPTURED_VIDEO_NAME = FileSystemStorage.getInstance().getAppHomePath() + "captured.mp4";
    
    public VideoDemo(Form parentForm) {
        init("Video", getGlobalResources().getImage("icon.png"), parentForm);
    }
    
    @Override
    public Component createDemo() {
        ScaleImageLabel imageLabel = new ScaleImageLabel(getDemoImage().scaled(CommonBehavior.getImageWidth(), CommonBehavior.getImageHeight()));
        Button button = new Button(getDemoId());
        button.addActionListener(e-> createAndShowForm());
        
        Container mainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        mainWindowComponent.setUIID("DemoComponent");
        return mainWindowComponent;
    }
    private void createAndShowForm(){
        Form videoDemoForm = new Form("Video", new BoxLayout(BoxLayout.Y_AXIS));
        
        // Toolbar add back button
        Toolbar toolBar = videoDemoForm.getToolbar();
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> getParentForm().show());
        
        // Toolbar add info button 
        toolBar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", "You can play videos either from remote or local sources very easily in Codename One, here we also " +
                        "show the ability to record a video that can play later.", "OK", null);
        });
        
        MultiButton playOfflineButton = new MultiButton("Play offline video");
        playOfflineButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_COLLECTION, playOfflineButton.getAllStyles()));
//        playOfflineButton.addActionListener();
        
        MultiButton playOnlineButton = new MultiButton("Play online video");
        playOnlineButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_COLLECTION, playOnlineButton.getAllStyles()));
//        playOnlineButton.addActionListener();
        
        MultiButton captureVideoButton = new MultiButton("Record Video");
        captureVideoButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_CALL, captureVideoButton.getAllStyles()));
        captureVideoButton.addActionListener(e->{
            String capturedVideo = Capture.captureVideo();
            if(capturedVideo != null){
                try{
                    Util.copy(openFileInputStream(capturedVideo), openFileOutputStream(CAPTURED_VIDEO_NAME));
                }catch(IOException err) {
                    Log.e(err);
                }
            }
        });
        
        MultiButton playCaptured = new MultiButton("Play last recorded Video");
        playCaptured.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_LABEL, playCaptured.getAllStyles()));
        playCaptured.addActionListener(e->{
            if (existsInFileSystem(CAPTURED_VIDEO_NAME)){
                playVideoOnNewForm(CAPTURED_VIDEO_NAME, videoDemoForm);
            }
            else{
                ToastBar.showErrorMessage("you should to capture video first");
            }
        });
        
        videoDemoForm.addAll(playOfflineButton, playOnlineButton, captureVideoButton, playCaptured);
        videoDemoForm.show();
    }
    private void playVideoOnNewForm(String fileURI, Form parentForm){
        Form videoForm = new Form("Video", new BorderLayout());
        videoForm.getToolbar().setBackCommand("", e->{
            parentForm.show();
        });
        scheduleBackgroundTask(() -> {
            try{
                Media capturedVideo = MediaManager.createMedia(fileURI, true);
                callSerially(()->{
                    if(capturedVideo != null){
                        capturedVideo.setNativePlayerMode(true);
                        capturedVideo.prepare();
                        videoForm.add(BorderLayout.CENTER, new MediaPlayer(capturedVideo));
                        videoForm.show();
                    }
                });
            }catch(IOException error){
                Log.e(error);
                ToastBar.showErrorMessage("Error loading video");
            }
            
        });
    }
}

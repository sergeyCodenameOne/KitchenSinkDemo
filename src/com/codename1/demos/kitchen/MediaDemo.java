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
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MediaPlayer;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import static com.codename1.io.Util.downloadUrlToFileSystemInBackground;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.IOException;


public class MediaDemo extends Demo {
    private static final String CAPTURED_VIDEO = FileSystemStorage.getInstance().getAppHomePath() + "captured.mp4";
    private static final String DOWNLOADED_VIDEO = FileSystemStorage.getInstance().getAppHomePath() + "hello-codenameone.mp4";
    
    public MediaDemo(Form parentForm) {
        init("media", getGlobalResources().getImage("media-demo-icon.png"), parentForm, "");
    }
    
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "VideoContainer");
      
        Style iconStyle = UIManager.getInstance().getComponentStyle("MediaIcon");
        Component downloadButton = createVideoComponent("Hello (Online)", "Download to FileSystem", FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, iconStyle),
                                        e-> {
                                            if (!existsInFileSystem(DOWNLOADED_VIDEO)){
                                                ToastBar.showMessage("Downloading", FontImage.MATERIAL_SYSTEM_UPDATE, 3000);
                                                downloadFile("https://www.codenameone.com/files/hello-codenameone.mp4");
                                            }
                                        });
        
        Component playOfflineButton = createVideoComponent("Hello (Offline)", "Play from FileSystem", FontImage.createMaterial(FontImage.MATERIAL_PLAY_CIRCLE_FILLED, iconStyle),
                                        e-> {
                                            if (existsInFileSystem(DOWNLOADED_VIDEO)){
                                                playVideoOnNewForm(DOWNLOADED_VIDEO, demoContainer.getComponentForm());
                                            }else{
                                                ToastBar.showErrorMessage("For playing the video in offline mode you should first to download the video");
                                            }
                                        });
                                            
        
        Component playOnlineButton = createVideoComponent("Hello (Online)", "Play thru http", FontImage.createMaterial(FontImage.MATERIAL_PLAY_CIRCLE_FILLED, iconStyle),
                                        e -> playVideoOnNewForm("https://www.codenameone.com/files/hello-codenameone.mp4", demoContainer.getComponentForm()));
        
        Component captureVideoButton = createVideoComponent("Capture", "Record video and save to FileSystem", FontImage.createMaterial(FontImage.MATERIAL_VIDEOCAM, iconStyle),
                                        e-> {
                                            String capturedVideo = Capture.captureVideo();
                                            if(capturedVideo != null){
                                                try{
                                                    Util.copy(openFileInputStream(capturedVideo), openFileOutputStream(CAPTURED_VIDEO));
                                                }catch(IOException err) {
                                                    Log.e(err);
                                                }
                                            }
                                        });
        
        Component playCaptured = createVideoComponent("Play", "Play captured video", FontImage.createMaterial(FontImage.MATERIAL_PLAY_CIRCLE_FILLED, iconStyle),
                                        e-> {
                                            if (existsInFileSystem(CAPTURED_VIDEO)){
                                                playVideoOnNewForm(CAPTURED_VIDEO, demoContainer.getComponentForm());
                                            }
                                            else{
                                                ToastBar.showErrorMessage("you should to capture video first");
                                            }
                                        });
  
        demoContainer.addAll(downloadButton, playOfflineButton, playOnlineButton, captureVideoButton, playCaptured);
        return demoContainer;
    }
    private void playVideoOnNewForm(String fileURI, Form parentForm){
        Form videoForm = new Form("Video", new BorderLayout());
        videoForm.add(CENTER, new InfiniteProgress());
        videoForm.show();
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
                        MediaPlayer mediaPlayer = new MediaPlayer(capturedVideo);
                        mediaPlayer.setAutoplay(true);
                        videoForm.removeAll();
                        videoForm.add(BorderLayout.CENTER, mediaPlayer);
                        videoForm.getContentPane().revalidate();
                    }
                });
            }catch(IOException error){
                Log.e(error);
                ToastBar.showErrorMessage("Error loading video");
            }
        });
    }
    
    private void downloadFile(String Url){
        
        downloadUrlToFileSystemInBackground(Url, DOWNLOADED_VIDEO, (e)-> {
            callSerially(()-> ToastBar.showInfoMessage("Your download has completed"));
        });
    }
    
    private Component createVideoComponent(String firstLine, String secondLine, Image icon, ActionListener actionListener){
        MultiButton videoComponent = new MultiButton(firstLine);
        videoComponent.setTextLine2(secondLine);
        videoComponent.setUIID("VideoComponent");
        videoComponent.setIcon(icon);
        videoComponent.setIconPosition("East");
        videoComponent.addActionListener(actionListener);
        videoComponent.setUIIDLine1("MediaComponentLine1");
        videoComponent.setUIIDLine2("MediaComponentLine2");
        return videoComponent;
    }
}

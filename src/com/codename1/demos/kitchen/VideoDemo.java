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
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.io.IOException;


public class VideoDemo extends Demo {
    private static final String CAPTURED_VIDEO = FileSystemStorage.getInstance().getAppHomePath() + "captured.mp4";
    private static final String DOWNLOADED_VIDEO = FileSystemStorage.getInstance().getAppHomePath() + "hello-codenameone.mp4";
    
    public VideoDemo(Form parentForm) {
        init("Video", getGlobalResources().getImage("icon.png"), parentForm, 
                                                "You can play videos either from remote or local sources very easily in Codename One, here we also " +
                                                "show the ability to record a video that can play later.");
    }
    
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      
        MultiButton downloadButton = new MultiButton("Download for offline mode");
        downloadButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SYSTEM_UPDATE, downloadButton.getAllStyles()));
        downloadButton.addActionListener(e->{
            if (!existsInFileSystem(DOWNLOADED_VIDEO)){
                ToastBar.showMessage("Downloading", FontImage.MATERIAL_SYSTEM_UPDATE, 3000);
                downloadFile("https://www.codenameone.com/files/hello-codenameone.mp4");
            }

        });
        
        MultiButton playOfflineButton = new MultiButton("Play offline video");
        playOfflineButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_COLLECTION, playOfflineButton.getAllStyles()));
        playOfflineButton.addActionListener(e->{
            if (existsInFileSystem(DOWNLOADED_VIDEO)){
                playVideoOnNewForm(DOWNLOADED_VIDEO, demoContainer.getComponentForm());
            }else{
                ToastBar.showErrorMessage("For playing the video in offline mode you should first to download the video");
            }
        });
        
        MultiButton playOnlineButton = new MultiButton("Play online video");
        playOnlineButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_COLLECTION, playOnlineButton.getAllStyles()));
        playOnlineButton.addActionListener(e -> playVideoOnNewForm("https://www.codenameone.com/files/hello-codenameone.mp4", demoContainer.getComponentForm()));
        
        MultiButton captureVideoButton = new MultiButton("Record Video");
        captureVideoButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_CALL, captureVideoButton.getAllStyles()));
        captureVideoButton.addActionListener(e->{
            String capturedVideo = Capture.captureVideo();
            if(capturedVideo != null){
                try{
                    Util.copy(openFileInputStream(capturedVideo), openFileOutputStream(CAPTURED_VIDEO));
                }catch(IOException err) {
                    Log.e(err);
                }
            }
        });
        
        MultiButton playCaptured = new MultiButton("Play last recorded Video");
        playCaptured.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIDEO_LABEL, playCaptured.getAllStyles()));
        playCaptured.addActionListener(e->{
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
}

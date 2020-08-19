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

import static com.codename1.ui.util.Resources.getGlobalResources;
import static com.codename1.contacts.ContactsManager.deleteContact;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ShareButton;
import com.codename1.contacts.Contact;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

public class ContactsDemo extends Demo{

    public ContactsDemo(Form parentForm) {
        init("Contacts", getGlobalResources().getImage("icon.png"), parentForm,
                                "A list of contacts is a very common use case for developers, we tried to make this list as realistic as possible allowing" +
                                " you to dial, email, share and even delete contacts. Notice that some platforms might not support contacts access (e.g. " +
                                "JavaScript) in which case we fallback to fake contacts.");
    }

    public Container createContentPane(){
        Container demoContainer = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        
        // getAllContacts can take long time so we add infiniteProgress to modify user experience.
        demoContainer.add(BorderLayout.CENTER, new InfiniteProgress());

        // Add new Contact button.
        FloatingActionButton addNewButton = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        Container LayeredDemoContainer = addNewButton.bindFabToContainer(demoContainer, Component.RIGHT, Component.BOTTOM);
        
        // Create new background Thread that will get all the contacts.
        scheduleBackgroundTask(()->{
            Contact contacts[] = Display.getInstance().getAllContacts(true, true, false, true, false, false);
                
            // Return to the EDT for edit the UI (the UI should be edited only within the EDT).
            callSerially(()->{
                demoContainer.removeAll();
                demoContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                demoContainer.setScrollableY(true);
                for (Contact currentContact : contacts){
                    demoContainer.add(createContactComponent(currentContact));
                    demoContainer.revalidate();
                }
            });
        }); 
        return LayeredDemoContainer;
    }
    
    private Component createContactComponent(Contact contact){
        MultiButton contactInfo = new MultiButton(contact.getDisplayName());
        contactInfo.setUIID("ContactMainInfo");
        Image contactImage = contact.getPhoto();
        
        // Set default avatar for contacts without avatar picture.
        if (contactImage == null){
            contactImage = getGlobalResources().getImage("default-contact-pic.jpg");
        }
        contactImage = contactImage.fill(CN.convertToPixels(10), CN.convertToPixels(10));
        contactImage = contactImage.applyMask(CommonBehavior.getRoundMask(contactImage.getHeight()));
        contactInfo.setIcon(contactImage);
        
        // Add call button. 
        Button call = new Button(FontImage.MATERIAL_CALL, 6f, "ContactsGreenButton");
        call.addActionListener(e-> dial(contact.getPrimaryPhoneNumber()));
        
        // Add info button.
        Button info = new Button(FontImage.MATERIAL_INFO, 6f, "ContactsBlueButton");
        info.addActionListener(e->{
            Dialog infoDialog = new Dialog(contact.getDisplayName(), new BoxLayout(BoxLayout.Y_AXIS));
            infoDialog.add(new Label("Phone: " + contact.getPrimaryPhoneNumber()));
            if (contact.getPrimaryEmail() != null){
                infoDialog.add(new Label("Email: " + contact.getPrimaryEmail()));
            }
            if (contact.getBirthday() != 0){
                infoDialog.add(new Label("Birthday: " + contact.getBirthday()));   
            }
            infoDialog.setDisposeWhenPointerOutOfBounds(true);
            infoDialog.show();
        });

        // Add share button.
        ShareButton share = new ShareButton();
        share.setUIID("ContactsGreenButton");
        share.setMaterialIcon(FontImage.MATERIAL_SHARE, 6f);  // Change the size of the icon.
        share.setTextToShare(contact.getDisplayName() + " phone: " + contact.getPrimaryPhoneNumber());
        
        // Add delete Button.
        Button delete = new Button(FontImage.MATERIAL_DELETE, 6f, "ContactsRedButton");
     
        Container callInfoShare = BoxLayout.encloseX(call, share, info);
        SwipeableContainer contactComponent = new SwipeableContainer(callInfoShare, delete, contactInfo);
        
        // Add action listener to delete button so it will delete the contacts from the mobile contats and from the app contacts. 
        delete.addActionListener(e->{
            if (Dialog.show("Delete", "This will delete the contact permanently!\nAre you sure?", "Delete", "Cancel")){
                if (contact.getId()!= null){
                    deleteContact(contact.getId());
                    contactComponent.remove();
                    Display.getInstance().getCurrent().revalidate();
                }
            }
        });
        return contactComponent;
    }
}

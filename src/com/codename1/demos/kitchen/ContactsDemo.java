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
import static com.codename1.ui.CN.dial;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.contacts.Contact;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

public class ContactsDemo extends Demo{

    public ContactsDemo(Form parentForm) {
        init("Contacts", getGlobalResources().getImage("icon.png"), parentForm);
    }

    
    @Override
    public Component createDemo() {
        ScaleImageLabel imageLabel = new ScaleImageLabel(getDemoImage().scaled(CommonBehavior.getImageWidth(), CommonBehavior.getImageHeight()));
        Button button = new Button(getDemoId());
        button.addActionListener(e-> createForm().show());
        
        Container mainWindowComponent = BoxLayout.encloseY(imageLabel, 
                                                                button);
        mainWindowComponent.setUIID("DemoComponent");
        return mainWindowComponent;
    }

    private Form createForm(){
        Form contactsForm = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        Toolbar toolBar = contactsForm.getToolbar();
        
        // Toolbar add back button
        toolBar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->{
            getParentForm().show();
        });
        
        // Toolbar add info button 
        toolBar.addMaterialCommandToRightBar("", FontImage.MATERIAL_INFO, e->{
            Dialog.show("Information", "A list of contacts is a very common use case for developers, we tried to make this list as realistic as possible allowing" +
                        " you to dial, email, share and even delete contacts. Notice that some platforms might not support contacts access (e.g. " +
                        "JavaScript) in which case we fallback to fake contacts.", "OK", null);
        });
        
        Contact contacts[] = Display.getInstance().getAllContacts(true, true, false, true, false, false);
        for (Contact currentContact : contacts){
            contactsForm.add(createContactComponent(currentContact));
        }
        
        // Add new Contact button.
        FloatingActionButton addNew = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD, "AddNewContactButton");
        addNew.bindFabToContainer(contactsForm.getContentPane(), Component.RIGHT, Component.BOTTOM);
        
        return contactsForm;
    }
    
    private Component createContactComponent(Contact contact){
        MultiButton button = new MultiButton(contact.getDisplayName());
        Image contactImage = contact.getPhoto();
        if (contactImage == null){
            contactImage = getGlobalResources().getImage("default-contact-pic.jpg");
        }
        contactImage = contactImage.fill(CN.convertToPixels(10), CN.convertToPixels(10));
        contactImage = contactImage.applyMask(CommonBehavior.getRoundMask(contactImage.getHeight()));
        button.setIcon(contactImage);
        
        // Add call button. 
        Button call = new Button(FontImage.MATERIAL_CALL, 6f, "ContactsButton");
        call.addActionListener(e-> dial(contact.getPrimaryPhoneNumber()));
        
        // Add info button.
        Button info = new Button(FontImage.MATERIAL_INFO, 6f, "ContactsButton");
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
        share.setMaterialIcon(FontImage.MATERIAL_SHARE, 6f);  // Change the size of the icon.
        share.setTextToShare(contact.getDisplayName() + " phone: " + contact.getPrimaryPhoneNumber());
        
        // Add delete Button.
        Button delete = new Button(FontImage.MATERIAL_DELETE, 6f, "ContactsButton");
     
        Container callInfoShare = FlowLayout.encloseCenter(call, info, share);
        SwipeableContainer cantactComponent = new SwipeableContainer(callInfoShare, delete, button);
        
        // Add action listener to delete button so it will delete the contacts from the mobile contats and from the app contacts. 
        delete.addActionListener(e->{
            if (Dialog.show("Delete", "This will delete the contact permanently!\nAre you sure?", "Delete", "Cancel")){
                if (contact.getId()!= null){
                
                }
                deleteContact(contact.getId());
                cantactComponent.remove();
                Display.getInstance().getCurrent().revalidate();
            }
        });
        return cantactComponent;
    }
}

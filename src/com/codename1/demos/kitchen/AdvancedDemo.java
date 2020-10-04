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

import com.codename1.components.FileTree;
import com.codename1.components.FileTreeModel;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SignatureComponent;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.demos.kitchen.ComponentDemos.ImageViewerDemo;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import static com.codename1.ui.CN.invokeAndBlock;
import com.codename1.ui.Calendar;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextComponent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

        
public class AdvancedDemo extends Demo{
    private HashMap<String, List<String>> allNotes = new HashMap<>();
    
    
    public AdvancedDemo(Form parentForm) {
        init("Advanced", getGlobalResources().getImage("advanced-icon.png"), parentForm, "");
    }
    
    public Container createContentPane(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS), "DemoContainer");
        demoContainer.setScrollableY(true);
        ContentBuilder builder = ContentBuilder.getInstance();

        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-browser.png"),
                                                                "Browser Component",
                                                                "The browser component is an interface",
                                                                "to an embeddable native platform browser on "+
                                                                "platforms that support embedding the native browser in place, if you need wide "+
                                                                "compatibility and flexibility you should check out the HTML Component which provides "+
                                                                "a lightweight 100% cross platform web component.\n\nThis component will only work on "+
                                                                "platforms that support embedding a native browser which exclude earlier versions of "+
                                                                "Blackberry devices and J2ME devices.\n\nIt's recommended that you place this component in "+
                                                                "a fixed position (none scrollable) on the screen without other focusable components to "+
                                                                "prevent confusion between focus authority and allow the component to scroll itself rather "+
                                                                "than CodenameOne making that decision for it.\n\nOn Android this component might show a native progress "+
                                                                "indicator dialog. You can disable that functionality using the call.", 
                                                                e->{
                                                                    BrowserComponent browser = new BrowserComponent();
                                                                    browser.setURL("https://www.codenameone.com/");
                                                                    showDemo("Browser", browser);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-singnature.png"),
                                                                "Signature Component",
                                                                "A component to allow a user to enter",
                                                                "their signature. This is just a button that, when pressed, will pop up a dialog where the user can draw "+
                                                                "their signature with their finger.\n\nThe user is given the option to save/reset/cancel the signature. On save, "+
                                                                "the signatureImamge property will be set with a full-size of the signature, and the icon on the button will "+
                                                                "show a thumbnail of the image.",
                                                                e->{
                                                                    showDemo("Signature", createSignatureDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-calendar.png"),
                                                                "Calendar",
                                                                "Date widget for selecting a date/time value.",
                                                                "To localize stings for month names use the values Calendar. Month using 3 first characters of the month name in "+
                                                                "the resource localization e.g. Calendar. Jan, Calendar.Feb etc …\n\nTo localize stings for day names use the values "+
                                                                "Calendar. Day in the resource localization e.g. \"Calendar.Sunday\", \"Calendar.Monday\" etc …\n\nNote that we "+
                                                                "recommend using the picker class which is superior when running on the device for most use cases.",
                                                                e-> {
                                                                    showDemo("Calendar", createCalendarDemo());
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-tree-file.png"),
                                                                "File Tree",
                                                                "Simple class showing off the file system as",
                                                                "a tree component.",
                                                                e-> {
                                                                    FileTree xmlTree = new FileTree(new FileTreeModel(true));
                                                                    Container treeContainer = BorderLayout.center(xmlTree);
                                                                    int height = Display.getInstance().convertToPixels(4);
                                                                    int width = Display.getInstance().convertToPixels(4);
                                                                    xmlTree.setFolderIcon(getGlobalResources().getImage("close-folder.png").scaled(width, height));
                                                                    xmlTree.setFolderOpenIcon(getGlobalResources().getImage("open-folder.png").scaled(width, height));
                                                                    xmlTree.setNodeIcon(getGlobalResources().getImage("file.png").scaled(width, height));
                                                                    
                                                                    // Refresh the root image.
                                                                    xmlTree.refreshNode(((Container)xmlTree.getComponentAt(0)).getComponentAt(0));
                                                                    showDemo("File Tree", treeContainer);
                                                                }));
        
        demoContainer.add(builder.createComponent(getGlobalResources().getImage("advanced-image-viewer.png"),
                                                                "Image Viewer",
                                                                "Image Viewer allows zooming/panning an",
                                                                "image and potentially flicking between multiple images within a list of images",
                                                                e->{
                                                                    Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                                                                    invokeAndBlock(()-> {
                                                                        Container demo = new ImageViewerDemo().createContentPane();
                                                                        CN.callSerially(()-> {
                                                                            ip.dispose();
                                                                            showDemo("Image Viewer", demo);
                                                                        });
                                                                    });
                                                                }));
        return demoContainer;
    }
    
    private Container createSignatureDemo(){
        Container demoContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Container costSummury = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        costSummury.setUIID("CostSummary");
        costSummury.add(new Label("Cost Summary", "CostSummaryLabel")).
                add(BorderLayout.centerEastWest(null, new Label("$30.00", "SignatureCost"),  new Label("Subtotal",  "SignatureLabel"))).
                add(BorderLayout.centerEastWest(null, new Label("$5", "SignatureCost"), new Label("Shipping", "SignatureLabel"))).
                add(BorderLayout.centerEastWest(null, new Label("$3.00", "SignatureCost"), new Label("Estimated Tax ", "SignatureLabel"))).
                add(new Label(" ", "Separator")).
                add(BorderLayout.centerEastWest(null, new Label("$38.00", "SignatureTotalCost"), new Label("Total", "SignatureLabel")));
        
        ScaleImageLabel creditCard = new ScaleImageLabel(getGlobalResources().getImage("credit-card.png")){
            @Override
            protected Dimension calcPreferredSize() {
                Dimension d = super.calcPreferredSize();
                d.setWidth(Display.getInstance().getDisplayWidth());
                d.setHeight(d.getWidth() / 2);
                return d;
            }
        };
        creditCard.setUIID("CreditCard");
        
        SignatureComponent sig = new SignatureComponent();
        
        Button confirmAndPay = new Button("Confirm & Pay", "SignatureConfirm");
        confirmAndPay.addActionListener(e->{
            if(sig.getSignatureImage() == null){
                ToastBar.showInfoMessage("you need to sign");
            }else {
                ToastBar.showInfoMessage("purchase was successfully completed");
            }
        });
        
        Button clear = new Button("Clear", "SignatureClear");
        clear.addActionListener(e->{
            sig.setSignatureImage(null);
        });
        
        Container confirmContainer = new Container(new BorderLayout());
        confirmContainer.setUIID("ConfirmContainer");
        confirmContainer.add(BorderLayout.NORTH, sig).
                add(BorderLayout.SOUTH, FlowLayout.encloseCenter(clear, confirmAndPay));
                
        demoContainer.addAll(costSummury, creditCard, confirmContainer);
        demoContainer.setScrollableY(true);
        return demoContainer;   
    }
    
    private Container createCalendarDemo(){
        Container notes = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        notes.add(createNote("Empty", null, notes));
        notes.setScrollableY(true);
        
        Calendar cld = new Calendar();
        cld.setSelectedDaysUIID("CalendarSelected");
        System.out.println(cld.getSelectedDaysUIID());
        cld.addActionListener((e)->{
            notes.removeAll();          
            List<String> currentNotes = allNotes.get(cld.getDate().toString());
            if(currentNotes == null){
                currentNotes = new ArrayList<>();
                allNotes.put(cld.getDate().toString(), currentNotes);
            }
            int notesCount = currentNotes.size();
            if(notesCount == 0){
                notes.add(createNote("Empty", currentNotes, notes));
            }else{
                for(String note : currentNotes){
                    notes.add(createNote(note, currentNotes, notes));
                }
            }
        });
                
        Button addNote = new Button("+ Create Memo", "DemoButton");
        addNote.addActionListener(e->{
            List<String> currentNotes = allNotes.get(cld.getDate().toString());
            if(currentNotes == null){
                currentNotes = new ArrayList<>();
                allNotes.put(cld.getDate().toString(), currentNotes);
            }
            
            TextComponent currNote = new TextComponent().labelAndHint("Note");
            Command ok = new Command("Ok");
            Command cancel = new Command("Cancel");
            
            if (Dialog.show("Enter Note", currNote, ok, cancel) == ok && currNote.getText().length() != 0){
                if(currentNotes.size() == 0){
                    notes.removeAll();
                }
                currentNotes.add(currNote.getText());
                notes.add(createNote(currNote.getText(), currentNotes, notes));
                
                notes.revalidate();
            }
        });
        
        Container demoContainer = BorderLayout.north(cld).
                                    add(BorderLayout.CENTER, notes).
                                    add(BorderLayout.SOUTH, FlowLayout.encloseRight(addNote));
        
        return demoContainer;
    }
    
    private Component createNote(String noteText, List<String> currNotes, Container notes){
        Button deleteButton = new Button("", FontImage.createMaterial(FontImage.MATERIAL_DELETE, UIManager.getInstance().getComponentStyle("DeleteButton")), "DeleteButton");
        SpanLabel noteTextLabel = new SpanLabel(noteText, "Note");
        SwipeableContainer note = new SwipeableContainer(deleteButton, noteTextLabel);

        deleteButton.addActionListener(e->{
            notes.removeComponent(note);
            notes.revalidate();
            if(currNotes != null){
                currNotes.remove(noteText);
            }
        });
        
        return note;
    }
}

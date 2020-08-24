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

import com.codename1.ui.CN;
import static com.codename1.ui.CN.convertToPixels;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.layouts.BorderLayout;
import static com.codename1.ui.util.Resources.getGlobalResources;
import java.util.Date;

/**
 *
 * @author serge
 */
public class ClockDemo extends Demo{

    public ClockDemo(Form parentForm) {
        init("Video", getGlobalResources().getImage("icon.png"), parentForm, 
                                                "This section shows off low level graphics in Codename One and drawing of shapes, it also demonstrates the " +
                                                "flexibility of the image class. You can learn more about this code within the developer guide in the graphics chapter");
    }
     
    public Container createContentPane(){
        AnalogClock clock = new AnalogClock();
        return BorderLayout.center(clock);
    }
    
    private class AnalogClock extends Component {
        private Date currentTime = new Date();
        private double padding;
        private double radius;
        private double centerX;
        private double centerY;
        private int shortTickLen;
        private int medTickLen;
        private int longTickLen;
        private int tickColor;

        @Override
        public void paintBackground(Graphics g) {
            initParameters();
            drawTicks(g);
            drawNumbers(g);
        }
        
        private void initParameters(){
            // Padding for the clock
            padding = convertToPixels(2);

            // Clock radius
            radius = Math.min(getWidth(), getHeight()) / 2 - padding;

            // Center point.
            centerX = getX() + getWidth() / 2;
            centerY = getY() + getHeight() / 2;

            // Tick Styles
            shortTickLen = 10;  // at 1-minute intervals
            medTickLen = 30;  // at 5-minute intervals
            longTickLen = 50; // at 15-minute intervals

            tickColor = 0xCCCCCC;
        }
        
        
        private void drawTicks(Graphics g){
            Stroke tickStroke = new Stroke(2f, Stroke.CAP_BUTT, Stroke.JOIN_ROUND, 1f);
            GeneralPath ticksPath = new GeneralPath();
            
            // Draw a tick for each "second" (1 through 60)
            for (int i = 1; i<= 60; i++){
                
                // default tick length is short
                int len = shortTickLen;
                
                if (i % 15 == 0){
                    // Longest tick at 15-minute intervals.
                    len = longTickLen;
                } else if (i % 5 == 0){
                    // Medium ticks at 5-minute intervals.
                    len = medTickLen;
                }else{
                    // Short ticks every minute.
                    len = shortTickLen;
                }

                double di = (double)i; // tick num as double for easier math.

                // Get the angle from 12 O'Clock to this tick (radians)
                double angleFrom12 = di / 60.0 * 2.0 * Math.PI;

                // Get the angle from 3 O'Clock to this tick
                    // Note: 3 O'Clock corresponds with zero angle in unit circle
                    // Makes it easier to do the math.
                double angleFrom3 = Math.PI / 2.0 - angleFrom12;

                // Move to the outer edge of the circle at correct position
                // for this tick.
                ticksPath.moveTo(
                        (float)(centerX + Math.cos(angleFrom3) * radius),
                        (float)(centerY - Math.sin(angleFrom3) * radius)
                );

                // Draw line inward along radius for length of tick mark.
                ticksPath.lineTo(
                        (float)(centerX + Math.cos(angleFrom3) * (radius - len)),
                        (float)(centerY - Math.sin(angleFrom3) * (radius - len))
                );
            }
            
            // Draw the ticks.
            g.setColor(tickColor);
            g.drawShape(ticksPath, tickStroke);
        }
        
        private void drawNumbers(Graphics g){
            Coordinate[] coordinates = new Coordinate[12];
            
            // Calculate all the numbers coordinates.
            for (int i = 1; i <= 12; i++){
                // Calculate the string width and height so we can center it properly
                String hourString = ((Integer)i).toString();
                int charWidth = g.getFont().stringWidth(hourString);
                int charHeight = g.getFont().getHeight();

                double di = (double)i;  // number as double for easier math

                // Calculate the position along the edge of the clock where the number should
                // be drawn.
                // Get the angle from 12 O'Clock to this tick (radians).
                double angleFrom12 = di / 12.0 * 2.0 * Math.PI;

                // Get the angle from 3 O'Clock to this tick
                    // Note: 3 O'Clock corresponds with zero angle in unit circle
                    // Makes it easier to do the math.
                double angleFrom3 = Math.PI / 2.0 - angleFrom12;

                int extraRange = convertToPixels(2);
                
                // Get diff between number position and clock center
                int tx = (int)(Math.cos(angleFrom3) * (radius - longTickLen - extraRange));
                int ty = (int)(-Math.sin(angleFrom3) * (radius - longTickLen - extraRange));

                coordinates[i - 1] = new Coordinate(tx + (int)centerX - charWidth / 2, ty + (int)centerY - charHeight / 2);
            }
            
            // Draw all the numbrs.
            for (int i = 1; i <= 12; i++) {
                String hourString = ((Integer)i).toString();
                g.drawString(hourString, coordinates[i - 1].x, coordinates[i - 1].y);
            }
        }
    }
    
    private class Coordinate {
        private int x;
        private int y;

        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
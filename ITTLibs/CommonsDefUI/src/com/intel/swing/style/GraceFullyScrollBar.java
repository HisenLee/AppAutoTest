/*
 * Copyright 2015 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
 package com.intel.swing.style;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class GraceFullyScrollBar extends MetalScrollBarUI {
    private Image imageThumb;
    public GraceFullyScrollBar() {
        try {
            imageThumb = new ImageIcon(GraceFullyScrollBar.class.getResource("thumb.png")).getImage();
        } catch (Exception e){}
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
    	g.translate(thumbBounds.x, thumbBounds.y);
        g.drawRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/(imageThumb.getWidth(null)+0.5),(double)thumbBounds.height/imageThumb.getHeight(null));
        ((Graphics2D)g).drawImage(imageThumb, transform, null);
        g.translate( -thumbBounds.x, -thumbBounds.y );
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {        
//        g.translate(trackBounds.x, trackBounds.y);
//        ((Graphics2D)g).drawImage(imageTrack,AffineTransform.getScaleInstance(imageTrack.getWidth(null),(double)trackBounds.height/imageTrack.getHeight(null)),null);
//        g.translate( -trackBounds.x, -trackBounds.y );
    	super.paintTrack(g, c, trackBounds);
    }

  }
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
 package com.cats.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class SwingUtil {
    private static AffineTransform atf = new AffineTransform();

    private static FontRenderContext frc = new FontRenderContext(atf, true,
            true);

    public static int getStringHeight(String str, Font font) {
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getHeight();

    }

    public static int getStringWidth(String str, Font font) {
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getWidth();
    }

    /**
     * 将形如“#FFFFFF”的颜色转换成Color
     * 
     * @param hex
     * @return
     */
    public static Color getColorFromHex(String hex) {
        if (hex == null || hex.length() != 7) {
            try {
                throw new Exception("不能转换这种类型的颜色");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        int r = Integer.valueOf(hex.substring(1, 3), 16);
        int g = Integer.valueOf(hex.substring(3, 5), 16);
        int b = Integer.valueOf(hex.substring(5), 16);
        return new Color(r, g, b);
    }
    
    public static void centerWindow(int width, int height, Component ui)
	{
		ui.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().width - width)/2),
    			(int)((Toolkit.getDefaultToolkit().getScreenSize().height - height)/2));
	}

	public static Image genImageResource(Class<?> cls,
            String icon)
    {
		Image img = new ImageIcon(cls.getResource(icon)).getImage();
		return img;
    }
	
	public static void initGracefulStyle()
	{
		initGracefulStyle(new Font("Segoe UI", Font.PLAIN, 12));
	}
	
	public static void initGracefulYaHeiStyle()
	{
		initGracefulStyle(new Font("Microsoft YaHei", Font.PLAIN, 12));
	}
	
	private static void initGracefulStyle(Font font)
	{
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		Object key = null;
		Object value = null;
		while (keys.hasMoreElements())
		{
			key = keys.nextElement();
			value = UIManager.get(key);
			if (key instanceof String)
			{
				if (((String) key).endsWith(".background"))
				{
					UIManager.put(key, Color.white);
				}
			}
			if (value instanceof Font)
			{
				UIManager.put(key, font);
			}
		}
		UIManager.put("Button.focusPainted", false);
		UIManager.put("MenuItem.selectionBackground",IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN);
		UIManager.getDefaults().put("TableHeader.cellBorder", BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	
	public static void initNimbusStyle()
	{
		try
        {
	        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
        } catch (ClassNotFoundException e)
        {
	        e.printStackTrace();
        } catch (InstantiationException e)
        {
	        e.printStackTrace();
        } catch (IllegalAccessException e)
        {
	        e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e)
        {
	        e.printStackTrace();
        } catch(Exception e)
		{
        	e.printStackTrace();
		}
	}
}
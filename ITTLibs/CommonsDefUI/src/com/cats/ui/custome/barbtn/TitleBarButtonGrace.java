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
 package com.cats.ui.custome.barbtn;

import javax.swing.ImageIcon;

import com.cats.ui.custome.iconbtn.IconJButton;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public class TitleBarButtonGrace extends IconJButton
{
	
    private static final long serialVersionUID = 1L;

	public TitleBarButtonGrace(BarIconType iconType)
    {
		ImageIcon normalIcon = new ImageIcon(TitleBarButtonGrace.class.getResource("/com/cats/ui/custome/barbtn/icon_"+iconType.getName()+".png"));
		ImageIcon enterIcon = new ImageIcon(TitleBarButtonGrace.class.getResource("/com/cats/ui/custome/barbtn/icon_"+iconType.getName()+"_enter.png"));
		ImageIcon pressIcon = new ImageIcon(TitleBarButtonGrace.class.getResource("/com/cats/ui/custome/barbtn/icon_"+iconType.getName()+".png"));
	    this.initIconButton(normalIcon, enterIcon, pressIcon);
    }
}

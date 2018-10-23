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
 package com.cats.ui.alertdialog;


/**
 * @author xblia
 * 2015年7月3日
 */
public interface IComstomeAlertDefine
{
    /**
     * Button id and name define example
     * new Object[][]
     * {
     *      {128, "button1"},
            {256, "button2"}
     * }
     *--128 and 256 must be Math.pow(2, x) customer define minimum 128
     * @return
     */
    Object[][] defineAlertDlgBtn();
    
    /**
     * Define Dialog icon info example
     * new Object[][] = 
     * {
     *      {3, TestFrame.class, "iconstest.png"},
     *  };
     *  --3 is TypeId, minimum 2, TestFrame.class is class file in resource folder, iconstest.png is resource name
     * @return
     */
    Object[][] defineAlertType();
}

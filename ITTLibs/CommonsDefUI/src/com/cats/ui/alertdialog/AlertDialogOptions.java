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
 * @author xblia 2015年7月3日
 */
public interface AlertDialogOptions
{
    int OPTION_OK = 1;
    int OPTION_CANCEL = 2;
    int OPTION_YES = 4;
    int OPTION_NO = 8;
    int OPTION_IGNORE = 16;
    int OPTION_IGNOREALL = 32;
    int OPTION_TERMINAL = 64;

    Object[][] BTN_DEFINE =
    {
        { OPTION_OK,            "OK" },
        { OPTION_CANCEL,        "CANCEL" },
        { OPTION_YES,           "YES" },
        { OPTION_NO,            "NO" },
        { OPTION_IGNORE,        "Ignore" },
        { OPTION_IGNOREALL,     "Ignore All" },
        { OPTION_TERMINAL,      "Terminate" }, 
    };
}

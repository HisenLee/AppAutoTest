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
 package com.cats.version;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.cats.version.utils.Utils;

/**
 * @author xblia2 Jun 8, 2015
 * 2016-01-25, Add by xblia for save XML data function.
 */
public class VersionCfgParseAndSave
{
	protected static final String FILE_NAME = "version_cfg.xml";

	public List<VersionInfo> getVersionInfo()
	{
		return getVersionInfo(Utils.getWorkSpace() + File.separator + FILE_NAME);
	}
	
	public List<VersionInfo> getVersionInfo(String fullPath)
	{
		SAXBuilder builder = new SAXBuilder();
		List<VersionInfo> infos = new ArrayList<VersionInfo>();
		try
		{
			Document doc = builder.build(new File(fullPath));
			Element root = doc.getRootElement();
			List<Element> softEles = root.getChildren("software");
			for (Element softEle : softEles)
			{
				String appName = softEle.getAttribute("name").getValue();
				String versionCode = softEle.getChildText("latest-version-code");
				String versionName = softEle.getChildText("latest-version");
				String versionPath = softEle.getChildText("latest-version-abspath");
				String startupName = softEle.getChildText("latest-version-startup");
				
				Element detailEles = softEle.getChild("latest-version-detail");
				List<Element> detailItemEles = detailEles.getChildren("item");
				List<VersionInfoDetail> details = new ArrayList<VersionInfoDetail>();
				for (Element detailItem : detailItemEles)
				{
					String title = detailItem.getAttributeValue("name");
					List<Element> detailEleList = detailItem.getChildren("detail");
					List<String> detailList = new ArrayList<String>();
					for (Element detailEle : detailEleList)
					{
						String strDetail = detailEle.getText();
						detailList.add(strDetail);
					}
					details.add(new VersionInfoDetail(title, detailList));
				}
				
				Element ignoreEles = softEle.getChild("ignore-files");
				List<String> ignoreFiles = new ArrayList<String>();
				if(ignoreEles != null)
				{
					List<Element> ignoreItems = ignoreEles.getChildren("item");
					for (Element ignoreItem : ignoreItems)
					{
						ignoreFiles.add(ignoreItem.getText());
					}
				}
				
				VersionInfo versionInfo = new VersionInfo();
				versionInfo.setAppName(appName);
				versionInfo.setVersion(versionName);
				versionInfo.setStartupName(startupName);
				versionInfo.setVersionCode(Integer.parseInt(versionCode));
				versionInfo.setPath(versionPath);
				versionInfo.setDetails(details);
				versionInfo.setIgnoreFiles(ignoreFiles);
				infos.add(versionInfo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return infos;
	}
	
	public boolean saveVersionInfo(List<VersionInfo> infos, String fullPath)
	{
		try
		{
			Document doc = new Document();
			Element root = new Element("software-group");
			for (VersionInfo info : infos)
			{
				Element softEle = new Element("software");
				softEle.setAttribute("name", info.getAppName());
				Element versionCodeEle = new Element("latest-version-code");
				Element versionNameEle = new Element("latest-version");
				Element versionPathEle = new Element("latest-version-abspath");
				Element startupNameEle = new Element("latest-version-startup");
				
				versionCodeEle.setText(String.valueOf(info.getVersionCode()));
				versionNameEle.setText(info.getVersion());
				versionPathEle.setText(info.getPath());
				startupNameEle.setText(info.getStartupName());
				softEle.addContent(versionCodeEle);
				softEle.addContent(versionNameEle);
				softEle.addContent(versionPathEle);
				softEle.addContent(startupNameEle);
				
				List<VersionInfoDetail> details = info.getDetails();
				if(null != details)
				{
					Element detailEles = new Element("latest-version-detail");
					for (VersionInfoDetail verDetail : details)
					{
						Element itemElem = new Element("item");
						itemElem.setAttribute("name", verDetail.getTitle());
						
						List<String> detailList = verDetail.getDetail();
						for (String detailInfo : detailList)
						{
							Element detailEle = new Element("detail");
							detailEle.setText(detailInfo);
							itemElem.addContent(detailEle);
						}
						detailEles.addContent(itemElem);
					}
					softEle.addContent(detailEles);
				}
				
				List<String> ignoreFiles = info.getIgnoreFiles();
				if(ignoreFiles != null)
				{
					Element ignoreEles = new Element("ignore-files");
					for (String ignoreInfo : ignoreFiles)
					{
						Element ignoreItemEle = new Element("item");
						ignoreItemEle.setText(ignoreInfo);
						ignoreEles.addContent(ignoreItemEle);
					}
					softEle.addContent(ignoreEles);
				}
				root.addContent(softEle);
			}
			doc.setRootElement(root);
			
			//Save to xml file
			XMLOutputter xmlOut = null;
			FileOutputStream fos = null;
            try { 
            	fos = new FileOutputStream(fullPath);
            	xmlOut = new XMLOutputter(Format.getPrettyFormat());
            	xmlOut.output(doc, fos);  
            }catch(Exception e)
            {
            	e.printStackTrace();
            }finally
            {
            	if(null != fos)
            	{
            		try
                    {
	                    fos.close();
                    } catch (Exception e)
                    {
	                    e.printStackTrace();
                    }
            	}
            }
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
package com.testserver.util;

/*
 * 此class只是测试，不用于自动化脚本
 * 
 * */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.intel.cats.test.log.ILog;

public class PngCheck implements Runnable
{
	public static void main(String[] args)
	{
		Thread t = new Thread(new PngCheck());
		t.start();
	}

	@SuppressWarnings("unused")
    @Override
	public void run()
	{
		// TODO Auto-generated method stub
		String path = "/home/ytf/Downloads/APKencryptionsolutionvendors/nexus5/tmp/03ff77b922350a6b/03ff77b922350a6b_2014_6_30_兼职猫（校招版）.apk_launch.png";
		boolean isblack = true;
		try
		{
			BufferedImage soucrceImage = ImageIO.read(new File(path));
			int width = soucrceImage.getWidth();
			int height = soucrceImage.getHeight();
			int pixels[][] = new int[width / 2][height / 2];
			int firstcolor = -1;
			boolean isfirst = true;
			for (int i = width / 4; i < width / 4 * 3; i++)
			{
				for (int j = height / 4; j < height / 4 * 3; j++)
				{

					if (isfirst)
					{
						firstcolor = soucrceImage.getRGB(i, j) & 0xff;
						isfirst = false;
					} else
					{
						int color = soucrceImage.getRGB(i, j) & 0xff;
						if (color != firstcolor)
						{
							isblack = false;
							break;
						}
						firstcolor = color;
					}

				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isblack)
			ILog.getLog().logMain("this is pic is black");
		else
			ILog.getLog().logMain("this is pic is not black");

	}

}

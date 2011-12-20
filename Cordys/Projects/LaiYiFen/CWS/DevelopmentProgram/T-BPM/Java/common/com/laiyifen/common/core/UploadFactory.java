/**
 * @Project:AppToolkit C3
 * @FileName:UploadFactory.java  2008-4-18
 * Copyright 2007 Cordys Info BV. All rights reserved.
 */
package com.laiyifen.common.core;

import com.laiyifen.common.attachment.delete.DBDeleteManager;
import com.laiyifen.common.attachment.delete.DeleteInterface;
import com.laiyifen.common.attachment.download.DBDownloadManager;
import com.laiyifen.common.attachment.download.DownloadInterface;
import com.laiyifen.common.attachment.download.FileDownloadManager;
import com.laiyifen.common.attachment.upload.DBUploadManager;
import com.laiyifen.common.attachment.upload.FileUploadManager;
import com.laiyifen.common.attachment.upload.UploadInterface;
import com.laiyifen.common.util.LaiYiFenProperties;


public class UploadFactory
{	
	private static String implType = LaiYiFenProperties.getUploadImplementation();
	
	private static UploadInterface uploadManager;

	private static DownloadInterface downloadManager;
	
	private static DeleteInterface deleteManager;
	
	static
	{
		if (implType.equals("file"))
		{
			try
			{
				uploadManager = new FileUploadManager();
				downloadManager = new FileDownloadManager();
			} catch (Exception e)
			{
				throw new RuntimeException("Exception error happens when initialized uploading program:" + e.getMessage());
			}
		}
		else
		{
            try
            {
                uploadManager = new DBUploadManager();
                downloadManager = new DBDownloadManager();
                deleteManager = new DBDeleteManager();
            } catch (Exception e)
            {
                throw new RuntimeException("Exception error happens when initialized uploading program:" + e.getMessage());
            }			
		}
		
	}
	
	private UploadFactory()
	{
		
	}
	
	
	public static UploadInterface getUploadManager()
	{
		return uploadManager;
	}
	
	public static DownloadInterface getDownloadManager()
	{
		return downloadManager;
	}
	
	public static DeleteInterface getDeleteManager()
	{
		return deleteManager;
	}
	
}

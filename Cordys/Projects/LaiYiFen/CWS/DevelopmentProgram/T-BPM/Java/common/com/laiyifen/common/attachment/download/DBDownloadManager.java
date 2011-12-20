package com.laiyifen.common.attachment.download;

import java.io.File;

import com.laiyifen.common.exceptions.LYFException;
import com.laiyifen.common.util.FileUtil;
import com.laiyifen.common.util.LaiYiFenProperties;


public class DBDownloadManager implements DownloadInterface{
	
	private static final String tempFileDorPath = LaiYiFenProperties.getDownloadFile()+File.separator;
	
	private static final String downLoadPath = LaiYiFenProperties.getDownloadPath()+File.separator;
	
	private static final String storageFolderPath = LaiYiFenProperties.getUploadFileStorage();
	
	
	public DBDownloadManager() throws LYFException {
	}
	
	public String downloadFile(String guid) throws LYFException{
		String  guidArray =guid.substring(0,guid.replaceAll("\\\\", "/").lastIndexOf("/"));
		File dataFile = new File(tempFileDorPath + guidArray);
		if (!dataFile.exists()) {
			dataFile.mkdirs();
		}
        
		File tmpFile = new File(tempFileDorPath + guid);
    
		File srcFile = new File(storageFolderPath + File.separator + guid);
		if (!srcFile.exists())
			throw new LYFException("Down error:there is no file named:"+guid);
		try {
			FileUtil.copy(srcFile, tmpFile);
		} catch (Exception e) {
			throw new LYFException("Copy files to download cache error:" + e.getMessage());
		}
		return downLoadPath+guid;
	}
}

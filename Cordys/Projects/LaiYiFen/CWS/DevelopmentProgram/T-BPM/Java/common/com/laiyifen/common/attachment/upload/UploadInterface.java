package com.laiyifen.common.attachment.upload;

import com.laiyifen.common.exceptions.LYFException;


public interface UploadInterface {
	public String uploadFile(String fileFullName, String fileContent,String directory,String fileType) throws LYFException;
}

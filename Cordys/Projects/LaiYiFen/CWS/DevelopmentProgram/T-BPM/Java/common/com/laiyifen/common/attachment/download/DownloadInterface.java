package com.laiyifen.common.attachment.download;

import com.laiyifen.common.exceptions.LYFException;

public interface DownloadInterface {
	public String downloadFile(String id) throws LYFException;
}

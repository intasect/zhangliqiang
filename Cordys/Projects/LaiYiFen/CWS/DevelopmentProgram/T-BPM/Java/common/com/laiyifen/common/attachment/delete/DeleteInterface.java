package com.laiyifen.common.attachment.delete;

import com.laiyifen.common.exceptions.LYFException;

public interface DeleteInterface {
	public void deleteFile(String guid) throws LYFException;
}

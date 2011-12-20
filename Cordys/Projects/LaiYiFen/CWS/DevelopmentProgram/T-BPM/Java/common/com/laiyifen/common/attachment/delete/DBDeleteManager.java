package com.laiyifen.common.attachment.delete;

import java.io.File;

import com.cordys.cpc.bsf.busobject.DMLStatement;
import com.laiyifen.common.exceptions.LYFException;
import com.laiyifen.common.util.LaiYiFenProperties;

/*
 * @Author Jiang
 */
public class DBDeleteManager implements DeleteInterface {

	private static final String storageFolderPath = LaiYiFenProperties
			.getUploadFileStorage();

	public DBDeleteManager() throws LYFException {
	}

	/*
	 * 删除文件 @param guid 文件的URL.
	 * 
	 * @see com.laiyifen.common.attachment.delete.DeleteInterface#deleteFile(java.lang.String)
	 */
	public void deleteFile(String guid) throws LYFException {
		try {
			String filePath = storageFolderPath + File.separator + guid;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			if (!myDelFile.exists())
				throw new LYFException(
						"Delete the error:there is no file named:" + guid);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				DMLStatement dml = new DMLStatement(
						"DELETE FROM ATTACHMENT WHERE FILEURL='"+guid+"'");
				dml.execute();
			} catch (Exception ex) {
				throw new LYFException("删除数据库不成功" + ex);
			}
		}
	}
}

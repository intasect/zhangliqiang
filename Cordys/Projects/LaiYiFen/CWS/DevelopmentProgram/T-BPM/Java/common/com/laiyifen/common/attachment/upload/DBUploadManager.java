package com.laiyifen.common.attachment.upload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.exceptions.LYFException;
import sun.misc.BASE64Decoder;
import com.laiyifen.common.util.LaiYiFenProperties;

/**
 * This class is derived from the Cordys documentation example for Uploader.
 */
public class DBUploadManager implements UploadInterface {

	private File uploadRecordFile;
	String storagePath =com.laiyifen.common.core.User.getUserInfo().getDeptName();
	
	private static String storageFolderPath = LaiYiFenProperties.getUploadFileStorage();
	String dateFolderPath=null;
	String dbFolderPath=null;
	String fullName=null;
	public DBUploadManager() throws LYFException {
	}
	/**
	 * This method uploads a file to a given server location.
	 * 
	 * @param fileFullName :
	 *            Name of the file to be created on the server.
	 * @param fileContent :
	 *            Contents to be set in the file in base64 format.
	 * @param directory :
	 * @param fileType :
	 * @return : A String which contains status information about the upload
	 */
	public String uploadFile(String fileFullName, String fileContent,
			String directory, String fileType) {
		FileOutputStream fout = null;
		String dateFile= (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
		dateFolderPath=storageFolderPath+File.separator+storagePath+File.separator+dateFile;
		dbFolderPath=storagePath+File.separator+dateFile+File.separator;
		uploadRecordFile = new File(dateFolderPath);
		if (!uploadRecordFile.exists()) {
			uploadRecordFile.mkdirs();
		}
		try {
			File uploaderFile = new File(fileFullName);
			boolean exists = uploaderFile.exists();
			if (exists) {
				// If the file exists, try to rename it to .old, to make
				// a temporary backup of the existing version:
				String fileNameWithDotOld = fileFullName + ".old";
				File uploaderFileWithDotOld = new File(fileNameWithDotOld);
				boolean success = renameFile(uploaderFileWithDotOld,
						uploaderFile);
				// If renaming doesn't work, then replace the file without
				// making
				// the .old temporary backup:
				if (!success) {
					uploaderFile.delete();
					uploaderFile = new File(fileFullName);
				} 
			}
			//long currentTimeLong=System.currentTimeMillis();
			//dateFolderPath += File.separator + currentTimeLong + "_";
			// String storagePath = EIBProperties.getInstallDir()+
			// "\\Web\\Temp\\" + System.currentTimeMillis() + "_";
			String fileName = fileFullName.substring(fileFullName
					.lastIndexOf(System.getProperty("file.separator")) + 1);
		    fullName=System.currentTimeMillis()+"_"+fileName;
			// Decode the contents and then write them to the file:
			BASE64Decoder dec = new BASE64Decoder();
			byte[] fileInfo = dec.decodeBuffer(fileContent);
			fout = new FileOutputStream(dateFolderPath+File.separator +fullName);
			fout.write(fileInfo);
		} catch (IOException e) {
			// This error message is checked at loadRepository.js. Check this js
			// file before changing anything in the message here:
			throw new RuntimeException("Error while uploading the file."+e.getMessage());
		} finally {
			// The fout.close() is here to increase the chance that the file is
			// always closed at the end of the process.
			try {
				if (fout != null)
					fout.close();
			} catch (IOException e) {
				// This error message is checked at loadRepository.js. Check
				// this js file before changing anything in the message here:
				throw new RuntimeException("Error while closing the file at the server."+e.getMessage());
			}
		}
		return dbFolderPath+fullName;
	}

	/**
	 * Main method to tests the process. Prints the status of the upload to
	 * system.out
	 * 
	 * @param args :
	 *            can be left empty
	 */
	/*
	 * public static void main(String args[]) { String uploadstatus =
	 * uploadFile("D:\\test.log" , "SGVsbG8gLCBJIGFtIGhlcmUgISEh" );
	 * System.out.println("Upload Status : " + uploadstatus); }
	 */
	/**
	 * This method Renames a the file
	 * 
	 * @param newFileName :
	 *            File Name to which the file should be renamed
	 * @param oldFileName :
	 *            File Name which is to be renamed
	 * @return : whether or not rename was successful
	 */
	public static boolean renameFile(File newFileName, File oldFileName) {
		boolean isRenamed = oldFileName.renameTo(newFileName);
		// It might be that the newFileName already exists, so renaming won't
		// work. In this case, delete the file with name equal to newFileName
		// and try to rename the old file again:
		if (!isRenamed) {
			newFileName.delete();
			isRenamed = oldFileName.renameTo(newFileName);
		}
		return isRenamed;
	}
    /*
	public static void insertFileDB(String fileFullName, String directory,String fileType) {
		String criteriaValue = "<new>"+
                       "<File_Repository>"+
                       "<FileName>"+"fileFullName"+"</FileName>"+
                       "<Directory>"+directory+"</Directory>"+
                       "<FileType>"+fileType+"</FileType>"+
                       "</File_Repository>"+
                       "</new>";
		String[] paramNames = new String[] {"tuple"};
		Object[] paramValues = new Object[] {criteriaValue};
		SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(),"http://schemas.cordys.com/business",
				"UpdateFile_Repository",paramNames,paramValues);
		int result = sro.execute();
		String temp = Node.writeToString(result, true);
		System.out.println(temp);
	}
	*/
	public static void insertFileDB(String fileFullName, String directory,String fileType,String content) {
		ATTACHMENT fileRepository = new ATTACHMENT();
		fileRepository.setFILENAME(fileFullName);
		fileRepository.setFILEURL(directory);
		fileRepository.setFILETYPE(fileType);
		/*fileRepository.setDEPT_CODE(deptCd);
		fileRepository.setDEPTMENT_NAME(deptNm);
		fileRepository.setFORM_ID(formId);
		fileRepository.setINSTANCE_ID(instanceId);
		fileRepository.setOPERATOR_CODE(userId);
		fileRepository.setOPERATOR_NAME(userNm);*/
		fileRepository.setDEPT_CODE("1001");
		fileRepository.setDEPTMENT_NAME("Sales");
		fileRepository.setFORM_ID("10010110");
		fileRepository.setINSTANCE_ID("sd12323-kkl911-7913");
		fileRepository.setOPERATOR_CODE("10023012");
		fileRepository.setOPERATOR_NAME("LEE");
		
		//fileRepository.setFileContent(content);
		fileRepository.insert();
	}
}

/**
 * @Project:LYF
 * @FileName:FileUtil.java  2011-7-17
 * Copyright 2011 Cordys Info BV. All rights reserved.
 */
package com.laiyifen.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class FileUtil
{
	/**
	 * To suppress default construction for noninstantiability
	 */
	private FileUtil()
	{
		
	}
	
	/**
	 * get bytes content of file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException
	{
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE)
		{
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length)
		{
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/**
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void copy(File src, File dst) throws IOException
	{
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * 删除文件、文件夹下的所有文件
	 * 
	 * @param fRoot
	 * @throws IOException
	 */
	public static void deleteRecursively(File fRoot) throws IOException
	{
		if (fRoot.isFile())
			if (!fRoot.delete())
				throw new IOException((new StringBuilder()).append("Unable to delete file '")
						.append(fRoot.getAbsolutePath()).append("'").toString());
			else
				return;
		File faFiles[] = fRoot.listFiles();
		for (int iIndex = 0; iIndex < faFiles.length; iIndex++)
		{
			File fFile = faFiles[iIndex];
			deleteRecursively(fFile);
		}

	}

	/**
	 * 创建文件所在的文件夹
	 * @param fFile
	 * @throws IOException
	 */
	public static void createFileDirectories(File fFile) throws IOException
	{
		File fParent = fFile.getParentFile();
		if (fParent.exists())
			return;
		if (!fParent.mkdirs())
			throw new IOException((new StringBuilder()).append("Unable to create the directory '")
					.append(fParent.getAbsolutePath()).append("'").toString());
		else
			return;
	}
	
   
    /**
     * 删除指定目录下的所有过时的文件,
     * @param absolutDirPath
     * @param keepTime
     */
    public static void deleteFile(String absolutDirPath, long keepTime)
    {
        
        File dir = new File(absolutDirPath);
        if (!dir.exists())
            return;
        
        FileFilter fileFilter = new FileFilter()
        {
            public boolean accept(File file)
            {
                return file.isFile();
            }
        };
        
        File[] files = dir.listFiles(fileFilter);
        for (int j = 0; j < files.length; j++)
        {
            if (files[j].lastModified() + keepTime < System.currentTimeMillis())
            {
                files[j].delete();
            }
        }
    }
    
static java.text.SimpleDateFormat format = null;
	
	public static Date CalculatorDate(int x) throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -x);
		String dateString = format.format(cal.getTime());
		Date date = null;
		if (!("").equals(dateString) || null != dateString) {
			date = format.parse(dateString);
		}
		return date;
	}

	public static void refreshFileList(String path) throws ParseException,
			IOException {
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.exists()) {
				refreshFileList(files[i].getName());
				String fileName = files[i].getName();

				if (!"".equals(fileName) || null != fileName) {
					Date date = format.parse(fileName);
					boolean flag = dataCompare(date,CalculatorDate(LaiYiFenProperties.getRemovedDays()));
					if (flag) {
						delAll(file);
					}
				}
			}
		}
	}

	public static boolean dataCompare(Date date1, Date date2) {
		boolean dateComPareFlag = true;
		if (date2.compareTo(date1) != 1) {
			dateComPareFlag = false;
		}
		return dateComPareFlag;
	}

	public static void delAll(File f) throws IOException {
		if (!f.exists())
			throw new IOException("Specified directory does not exist:"
					+ f.getName());
		boolean rslt = true;
		if (!(rslt = f.delete())) {

			File subs[] = f.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory())
					delAll(subs[i]);
				rslt = subs[i].delete();
			}
			rslt = f.delete();
		}
		if (!rslt) {
			throw new IOException("It can't delete it:" + f.getName());
		}
	}

}

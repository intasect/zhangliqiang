/**
 * @Project:LYF
 * @FileName:GUIDUtil.java  2011-7-20
 * Copyright 2011 Cordys Info BV. All rights reserved.
 */
package com.laiyifen.common.usersync;

import java.util.UUID;

public class GUIDUtil
{
	/**
	 * To suppress default construction for noninstantiability
	 */
	private GUIDUtil()
	{
		
	}
	public static String getGUIDString()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}

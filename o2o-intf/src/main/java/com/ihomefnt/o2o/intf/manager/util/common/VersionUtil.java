/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月23日
 * Description:VersionUtil.java 
 */
package com.ihomefnt.o2o.intf.manager.util.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhang
 */
public class VersionUtil {
	private static final Logger LOG = LoggerFactory.getLogger(VersionUtil.class);

	/**
	 * 判断版本大小
	 * 
	 * @param client
	 *            第一个版本号
	 * @param server
	 *            第二个版本号
	 * @return true:第一个版本号小于第二个版本号 <br/>
	 * 		   false:第一个版本号大于等于第二个版本号<br/>
	 */
	public static boolean mustUpdate(String client, String server) {
		if (StringUtils.isBlank(client) || StringUtils.isBlank(server)) {
			return false;
		}
		String[] clients = client.split("\\.");
		String[] servers = server.split("\\.");
		if (clients.length != servers.length) {
			return false;
		}

		for (int i = 0; i < clients.length; i++) {
			int current = Integer.parseInt(clients[i].trim());
			int latest = Integer.parseInt(servers[i].trim());
			if (current < latest) {
				return true;
			} else if (current > latest) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 版本比对 0：失败；1：当前版本低于比对版本；2：当前版本等于比对版本;3：当前版本高于比对版本
	 * @param currentVersion
	 * @param compareVersion
	 * @return
	 */
	public static Integer versionCompare(String currentVersion, String compareVersion) {
		if (StringUtils.isBlank(currentVersion) || StringUtils.isBlank(compareVersion)) {
			return 0;
		}
		String[] currentVersions = currentVersion.split("\\.");
		String[] compareVersions = compareVersion.split("\\.");
		if (currentVersions.length != compareVersions.length) {
			return 0;
		}

		for (int i = 0; i < currentVersions.length; i++) {
			int current = Integer.parseInt(currentVersions[i].trim());
			int compare = Integer.parseInt(compareVersions[i].trim());
			if (current < compare) {
				return 1;
			} else if (current > compare) {
				return 3;
			}
		}
		return 2;
	}

	/**
	 * bundle版本比较
	 * 指定模块需要大于某版本
	 * @param bundleT bundle android是list ios是String 如：["screen_559_14"]或"screen_559_14"
	 * @param moduleName 模块名称如：screen
	 * @param bundleVersion bundle版本号如：14
	 * @return bundleVersions为空 返回ture
	 *         具体版本号大于等于bundleVersion 返回true
	 *         具体版本号小于bundleVersion false
	 */
	public static boolean bundleMustUpdate(Object bundleT, String moduleName, Integer bundleVersion) {
		List<String> bundleVersions = new ArrayList();
		try {
			if (bundleT instanceof List) {
				for (Object o : (List<String>) bundleT) {
					bundleVersions.add(String.class.cast(o));
				}
			}else if(bundleT instanceof String){
				String o1 = (String) bundleT;
				if(StringUtils.isNotBlank(o1)){
					bundleVersions = Arrays.asList(o1.split(","));
				}
			}
		}catch (Exception e){
			LOG.error("bundleMustUpdate error",e);
			return true;
		}
		if(CollectionUtils.isNotEmpty(bundleVersions)){
			try {
				final boolean[] b = {true};
				bundleVersions.forEach(versionStr->{
					if(versionStr.indexOf(moduleName)>-1){
						Integer bundleVersionCurrent = Integer.parseInt(versionStr.substring(versionStr.lastIndexOf("_")+1));
						b[0] = bundleVersionCurrent >= bundleVersion;
					}
				});
				return b[0];
			}catch (Exception e){
				LOG.error("bundleMustUpdate error",e);
				return true;
			}
		}
		return true;
	}

//	public static void main(String[] args) {
//		List<String> bundleVersions = Arrays.asList("decoration_559_9","life_559_11");
//		String kk="decoration_559_11,life_559_11";
//		System.out.println(bundleMustUpdate(bundleVersions,"decoration",10));
//	}



}

package com.ihomefnt.o2o.intf.domain.emchat.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangxiao on 2015-11-30.
 */
public class PropertiesUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);


	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
					"RestAPIConfig.properties");

			p.load(inputStream);

		} catch (IOException e) {
			LOG.error("getProperties Exception ", e);
		}

		return p;
	}

}

package com.ihomefnt.o2o.service.manager.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

	@Value("${wpf.home.url}")
	private String WPF_HOME_URL;

	@Value("${icon.url}")
	private String ICON_URL;

	@Value("${icon.image.url}")
	private String ICON_IMG_URL;

	// 已废弃
	@Value("${ihome.api.settlement.recharge}")
	private String settlementRecharge;

	@Value("${lechange.api.device.list}")
	private String lechangeDeviceList;

	// 更新乐橙设备图片
	@Value("${lechange.api.update.image}")
	private String lechangeUpdateImage;

	// 更新乐橙直播图片
	@Value("${lechange.api.device.list.cameraSn}")
	private String lechangeLiveImage;

	@Value("${lechange.api.base.show}")
	private String lechangeBaseShow;

	@Value("${env.open.tag.show}")
	private String openTagShow;

	@Value("${hard.skip.url}")
	private String hardSkipUrl;

	@Value("${soft.skip.url}")
	private String softSkipUrl;

	@Value("${subject.skip.url}")
	private String subjectSkipUrl;

	@Value("${wpf.case.url}")
	private String wpfCaseUrl;

	@Value("${home.carnival.url}")
	private String homeCarnivalUrl;

	@Value("${dna.share.url}")
	private String dnaShareUrl;

	@NacosValue(value = "${mop.host}", autoRefreshed = true)
	private String mopHost;

	public String getMopHost() {
		return mopHost;
	}

	public void setMopHost(String mopHost) {
		this.mopHost = mopHost;
	}

	public String getWPF_HOME_URL() {
		return WPF_HOME_URL;
	}

	public void setWPF_HOME_URL(String wPF_HOME_URL) {
		WPF_HOME_URL = wPF_HOME_URL;
	}

	public String getICON_URL() {
		return ICON_URL;
	}

	public void setICON_URL(String iCON_URL) {
		ICON_URL = iCON_URL;
	}

	public String getICON_IMG_URL() {
		return ICON_IMG_URL;
	}

	public void setICON_IMG_URL(String iCON_IMG_URL) {
		ICON_IMG_URL = iCON_IMG_URL;
	}

	public String getSettlementRecharge() {
		return settlementRecharge;
	}

	public void setSettlementRecharge(String settlementRecharge) {
		this.settlementRecharge = settlementRecharge;
	}

	public String getLechangeDeviceList() {
		return lechangeDeviceList;
	}

	public void setLechangeDeviceList(String lechangeDeviceList) {
		this.lechangeDeviceList = lechangeDeviceList;
	}

	public String getLechangeUpdateImage() {
		return lechangeUpdateImage;
	}

	public void setLechangeUpdateImage(String lechangeUpdateImage) {
		this.lechangeUpdateImage = lechangeUpdateImage;
	}

	public String getLechangeLiveImage() {
		return lechangeLiveImage;
	}

	public void setLechangeLiveImage(String lechangeLiveImage) {
		this.lechangeLiveImage = lechangeLiveImage;
	}

	public String getLechangeBaseShow() {
		return lechangeBaseShow;
	}

	public void setLechangeBaseShow(String lechangeBaseShow) {
		this.lechangeBaseShow = lechangeBaseShow;
	}

	public String getHardSkipUrl() {
		return hardSkipUrl;
	}

	public void setHardSkipUrl(String hardSkipUrl) {
		this.hardSkipUrl = hardSkipUrl;
	}

	public String getSoftSkipUrl() {
		return softSkipUrl;
	}

	public void setSoftSkipUrl(String softSkipUrl) {
		this.softSkipUrl = softSkipUrl;
	}

	public String getSubjectSkipUrl() {
		return subjectSkipUrl;
	}

	public void setSubjectSkipUrl(String subjectSkipUrl) {
		this.subjectSkipUrl = subjectSkipUrl;
	}

	public String getWpfCaseUrl() {
		return wpfCaseUrl;
	}

	public void setWpfCaseUrl(String wpfCaseUrl) {
		this.wpfCaseUrl = wpfCaseUrl;
	}

	public String getOpenTagShow() {
		return openTagShow;
	}

	public void setOpenTagShow(String openTagShow) {
		this.openTagShow = openTagShow;
	}

	public String getHomeCarnivalUrl() {
		return homeCarnivalUrl;
	}

	public void setHomeCarnivalUrl(String homeCarnivalUrl) {
		this.homeCarnivalUrl = homeCarnivalUrl;
	}

	public String getDnaShareUrl() {
		return dnaShareUrl;
	}

	public void setDnaShareUrl(String dnaShareUrl) {
		this.dnaShareUrl = dnaShareUrl;
	}

}

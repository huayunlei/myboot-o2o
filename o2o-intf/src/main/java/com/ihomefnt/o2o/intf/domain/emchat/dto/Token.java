package com.ihomefnt.o2o.intf.domain.emchat.dto;

import lombok.EqualsAndHashCode;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by wangxiao on 2015-11-30.
 */
@EqualsAndHashCode
public class Token {
	private String accessToken;
	private Long expiredAt;

	public Token() {
	}

	public Token(String accessToken, Long expiredAt) {
		this.accessToken = accessToken;
		this.expiredAt = expiredAt;
	}

	public static void applyAuthentication(HttpEntityEnclosingRequestBase httpMethodEntity, Credential credentail) {
		applyAuthentication(httpMethodEntity, credentail.getToken());
	}

	public static void applyAuthentication(HttpEntityEnclosingRequestBase httpMethodEntity, Token token) {
		httpMethodEntity.addHeader("Authorization", "Bearer " + token.toString());
	}

	public static void applyAuthentication(HttpRequestBase httpMethodEntity, Credential credentail) {
		applyAuthentication(httpMethodEntity, credentail.getToken());
	}

	public static void applyAuthentication(HttpRequestBase httpMethodEntity, Token token) {
		httpMethodEntity.addHeader("Authorization", "Bearer " + token.toString());
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expiredAt;
	}

	@Override
	public String toString() {
		return accessToken;
	}
}

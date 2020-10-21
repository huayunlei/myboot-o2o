package com.ihomefnt.o2o.intf.domain.emchat.dto;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.EqualsAndHashCode;

import java.net.URL;

/**
 * Created by wangxiao on 2015-11-30.
 */
@EqualsAndHashCode
public abstract class Credential {

	protected String grantType;
	protected String tokenKey1;
	protected String tokenKey2;

	protected JsonNodeFactory factory = new JsonNodeFactory(false);

	protected Token token;

	protected abstract URL getUrl();

	protected abstract GrantType getGrantType();

	public static enum GrantType {
		CLIENT_CREDENTIALS
	}

	public Credential() {
	}

	public Credential(String tokenKey1, String tokenKey2) {
		this.tokenKey1 = tokenKey1;
		this.tokenKey2 = tokenKey2;
	}

	public Credential(Token token) {
		this.token = token;
	}

	public abstract Token getToken();

	@Override
	public String toString() {
		return "Credential [grantType=" + grantType + ", tokenKey1=" + tokenKey1 + ", tokenKey2=" + tokenKey2
				+ ", token=" + token + "]";
	}
}

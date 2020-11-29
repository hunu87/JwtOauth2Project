package com.kiwoom.demo.manage.jwt.domain;

public class Jwt {
	
	private String accessToken;
	private String refreshToken;
	
	public Jwt(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	@Override
	public String toString() {
		return "Jwt [accessToken=" + accessToken + ". refreshToken=" + refreshToken + "]";
	}
}

package com.kiwoom.demo.manage.jwt.enums.exception;

import com.kiwoom.demo.manage.exception.enums.base.EnumBaseException;

public enum EnumSecurityException implements EnumBaseException {
	
	NONE					( "NONE" 					,"NONE"),
	BadCredentialsException	( "BadCredentialsException"	,"the credentials are invalid"),
	ExpiredJwtException		( "ExpiredJwtException"		,"Exception indicating that a JWT was accepted after it expired and must be rejected."),
	UnsupportedJwtException	( "UnsupportedJwtException"	,"when receiving a JWT in a particular format/configuration that does not match the format expected"),
	MalformedJwtException	( "MalformedJwtException"	,"Exception indicating that a JWT was not correctly constructed and should be rejected"),
	SignatureException		( "SignatureException"		,"Exception indicating that either calculating a signature or verifying an existing signature of a JWT failed."),
	IllegalArgumentException( "IllegalArgumentException","Thrown to indicate that a method has been passed an illegal or inappropriate argument");
	
	private final String code;
	private final String desc;
	
	private EnumSecurityException(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
	
}

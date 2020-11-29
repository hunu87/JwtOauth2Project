package com.kiwoom.demo.manage.exception.base;

import com.kiwoom.demo.manage.exception.enums.base.EnumBaseException;

public class CommonException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Exception e;
	private EnumBaseException enumBaseException;
	private String message;
		
	public CommonException(Exception e, EnumBaseException enumBaseException) {
		super();
		this.e = e;
		this.enumBaseException = enumBaseException;
	}

	public CommonException(Exception e, EnumBaseException enumBaseException, String message) {
		super();
		this.e = e;
		this.enumBaseException = enumBaseException;
		this.message = message;
	}

	public Exception getE() {
		return e;
	}

	public EnumBaseException getEnumBaseException() {
		return enumBaseException;
	}

	public String getMessage() {
		return message;
	}
}

/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hlc.utility.excel;

/**
 * Excel处理异常.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ExcelException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2058401770643787186L;

	/**
	 * Instantiates a new excel exception.
	 */
	public ExcelException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new excel exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new excel exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ExcelException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new excel exception.
	 *
	 * @param message the message
	 */
	public ExcelException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new excel exception.
	 *
	 * @param cause the cause
	 */
	public ExcelException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

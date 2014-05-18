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
package org.hlc.utility.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类型处理器.
 *
 * @author huanglicong
 * @version V1.0
 */
public class DateTypeHandler implements TypeHandler<Date> {

	// TODO 需要支持多种格式
	private final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/** {@inheritDoc} */
	@Override
	public String typeToString(Date obj) {
		return FULL_FORMAT.format(obj);
	}

	/** {@inheritDoc} */
	@Override
	public Date stringToType(String source) {
		try {
			return FULL_FORMAT.parse(source);
		} catch (ParseException e) {
			throw new TypeBindException("Error parsing the date string," + source, e);
		}
	}

}

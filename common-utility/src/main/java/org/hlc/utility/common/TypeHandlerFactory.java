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

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 类型处理器工厂，支持用户自定义.
 *
 * @author huanglicong
 * @version V1.0
 */
public class TypeHandlerFactory {

	/** The Constant typeRegistered. */
	@SuppressWarnings("rawtypes")
	public static final Map<String, TypeHandler> typeRegistered = new LinkedHashMap<String, TypeHandler>();

	static {
		typeRegistered.put("null", new NullTypeHandler());
		typeRegistered.put(Date.class.getName(), new DateTypeHandler());
		typeRegistered.put(Class.class.getName(), new ClassTypeHandler());
		typeRegistered.put(String.class.getName(), new StringTypeHandler());
		typeRegistered.put(Byte.class.getName(), new ByteTypeHandler());
		typeRegistered.put(Short.class.getName(), new ShortTypeHandler());
		typeRegistered.put(Integer.class.getName(), new IntegerTypeHandler());
		typeRegistered.put(Long.class.getName(), new LongTypeHandler());
		typeRegistered.put(Double.class.getName(), new DoubleTypeHandler());
		typeRegistered.put(BigDecimal.class.getName(), new BigDecimalTypeHandler());

		typeRegistered.put(byte.class.getName(), new ByteTypeHandler());
		typeRegistered.put(short.class.getName(), new ShortTypeHandler());
		typeRegistered.put(int.class.getName(), new IntegerTypeHandler());
		typeRegistered.put(long.class.getName(), new LongTypeHandler());
		typeRegistered.put(double.class.getName(), new DoubleTypeHandler());
	}

	/**
	 * Adds the type handler.
	 *
	 * @param type the type
	 * @param handler the handler
	 */
	@SuppressWarnings("rawtypes")
	public static void addTypeHandler(Class<?> type, TypeHandler handler) {
		typeRegistered.put(type.getName(), handler);
	}

	/**
	 * Gets the handler.
	 *
	 * @param <E> the element type
	 * @param classes the classes
	 * @return the handler
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> TypeHandler<E> getHandler(Class<E> classes) {
		String typeName = (classes == null ? "null" : classes.getName());
		TypeHandler handler = typeRegistered.get(typeName);
		if (handler != null) {
			return handler;
		}
		throw new IllegalArgumentException("Don't support " + classes.getName() + " type processing.");
	}

	/**
	 * Gets the handler.
	 *
	 * @param <E> the element type
	 * @param classes the classes
	 * @return the handler
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> TypeHandler<E> getHandler(String classes) {
		String typeName = (classes == null || "".equals(classes.trim()) ? "null" : classes);
		TypeHandler handler = typeRegistered.get(typeName);
		if (handler != null) {
			return handler;
		}
		throw new IllegalArgumentException("Don't support " + classes + " type processing.");
	}

}

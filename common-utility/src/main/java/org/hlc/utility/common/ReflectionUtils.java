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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ReflectionUtils {

	/**
	 * 获取指定前缀的方法.
	 *
	 * @param field
	 * @param targetCalss
	 * @param method
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@SuppressWarnings("rawtypes")
	public static Method getMethod(Field field, Class<?> targetCalss, BeanMethod method, Class[] params) throws NoSuchMethodException, SecurityException {
		String fieldname = field.getName();
		StringBuffer methodName = new StringBuffer(method.toString());
		methodName.append(fieldname.substring(0, 1).toUpperCase());
		methodName.append(fieldname.substring(1));
		return targetCalss.getMethod(methodName.toString(), params);
	}

	/**
	 * 获取指定字段的getter方法.
	 *
	 * @param field
	 * @param targetCalss
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getValueMethod(Field field, Class<?> targetCalss) throws NoSuchMethodException, SecurityException {
		Method temp = getMethod(field, targetCalss, BeanMethod.GETTER, new Class[] {});
		if (temp == null) {
			temp = getMethod(field, targetCalss, BeanMethod.IS, new Class[] {});
		}
		return temp;
	}

	/**
	 * 获取指定字段的setter方法.
	 *
	 * @param field
	 * @param targetCalss
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method setValueMethod(Field field, Class<?> targetCalss) throws NoSuchMethodException, SecurityException {
		return getMethod(field, targetCalss, BeanMethod.SETTER, new Class[] { field.getType() });
	}

	/**
	 * 获取对象指定字段的值.
	 *
	 * @param field
	 * @param target
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Object getValue(Field field, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method method = getValueMethod(field, target.getClass());
		return method.invoke(target);
	}

	/**
	 * 设置对象指定字段的值.
	 *
	 * @param field
	 * @param target
	 * @param value
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void setValue(Field field, Object target, Object value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = setValueMethod(field, target.getClass());
		method.invoke(target, value);
	}

}

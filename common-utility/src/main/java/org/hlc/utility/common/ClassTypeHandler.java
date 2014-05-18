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

/**
 * 类类型处理器.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ClassTypeHandler implements TypeHandler<Class<?>> {

	/** {@inheritDoc} */
	@Override
	public String typeToString(Class<?> obj) {
		return obj.getName();
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> stringToType(String source) {
		try {
			return Class.forName(source);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Class type handler error!", e);
		}
	}

}

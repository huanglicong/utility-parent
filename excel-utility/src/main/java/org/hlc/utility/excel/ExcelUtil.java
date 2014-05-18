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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * 定义Excel常用处理方法.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ExcelUtil {

	/** The util instance. */
	protected static ExcelUtil utilInstance;

	/** The excel output handler. */
	private ExcelOutputHandler excelOutputHandler;

	/** The excel input handler. */
	private ExcelInputHandler excelInputHandler;

	/**
	 * Instantiates a new excel util.
	 */
	public ExcelUtil() {
		this.excelOutputHandler = new ExcelOutputHandler();
		this.excelInputHandler = new ExcelInputHandler();
	}

	/**
	 * 获取ExcelUtil工具实例.
	 *
	 * @return single instance of ExcelUtil
	 */
	public static ExcelUtil getInstance() {
		if (utilInstance == null) {
			// 初始化工具类
			utilInstance = new ExcelUtil();
		}
		return utilInstance;
	}

	/**
	 * 将Excel列表转换成数据列表,只支持Excel表格中一种格式，支持多个工作单元.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param in the in
	 * @return the list
	 */
	public <T> List<T> importExcel(Class<T> type, InputStream in) {
		return excelInputHandler.importExcel(type, in);
	}

	/**
	 * 将Excel列表转换成数据列表,只支持Excel表格中一种格式，支持多个工作单元.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param in the in
	 * @return the list
	 */
	public <T> List<T> importExcel2007(Class<T> type, InputStream in) {
		return excelInputHandler.importExcel2007(type, in);
	}

	/**
	 * 讲数据对象集合导出到Excel表格当中改方法支持导入到一个工作单元中.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param dataSet the data set
	 * @param out the out
	 */
	public <T> void exportExcel(Class<T> type, Collection<T> dataSet, OutputStream out) {
		excelOutputHandler.exportExcel(type, dataSet, out);
	}

}

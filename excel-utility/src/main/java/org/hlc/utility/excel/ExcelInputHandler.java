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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hlc.utility.common.ReflectionUtils;
import org.hlc.utility.common.TypeHandler;
import org.hlc.utility.common.TypeHandlerFactory;
import org.hlc.utility.excel.annotation.Excel;
import org.hlc.utility.excel.annotation.ExcelColumn;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ExcelInputHandler {

	/**
	 * Import excel.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param in the in
	 * @return the list
	 */
	@SuppressWarnings("rawtypes")
	public <T> List<T> importExcel(Class<T> type, InputStream in) {

		Excel excelAnn = type.getAnnotation(Excel.class);
		if (excelAnn == null) {
			throw new ExcelException("The Class <" + type + "> did not Excel");
		}

		List<T> list = new ArrayList<T>();

		Map<String, Method> mapping = Maps.newLinkedHashMap();
		Map<String, TypeHandler> converters = new HashMap<String, TypeHandler>();

		try {
			// Step1 解析数据对象
			Field fileds[] = type.getDeclaredFields();
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];
				ExcelColumn column = field.getAnnotation(ExcelColumn.class);
				if (column != null) {
					Method setMethod = ReflectionUtils.setValueMethod(field, type);
					mapping.put(column.name(), setMethod);
					if (column.converter() != TypeHandler.class) {
						converters.put(setMethod.getName().toString(), column.converter().newInstance());
					} else {
						converters.put(setMethod.getName().toString(), TypeHandlerFactory.getHandler(field.getType()));
					}
				}
			}

			T temp = null;
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}

				// 解析Sheet
				List<Method> methods = Lists.newArrayList();
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}

					// 第一行获取标题
					if (rowNum == 0) {
						for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
							String title = hssfRow.getCell(cellNum).getStringCellValue();
							Method me = mapping.get(title);
							if (me == null) {
								continue;
							}
							methods.add(me);
						}
						continue;
					}

					temp = type.newInstance();
					for (int cellNum = 0; cellNum < methods.size(); cellNum++) {
						HSSFCell xh = hssfRow.getCell(cellNum);
						if (xh == null) {
							continue;
						}
						Method m = methods.get(cellNum);
						TypeHandler handler = converters.get(m.getName());
						if (handler == null) {
							continue;
						}
						String value = xh.getStringCellValue();
						if (StringUtils.isEmpty(value)) {
							continue;
						}
						Object val = null;
						try {
							val = handler.stringToType(value);
						} catch (Exception e) {
							throw new ExcelException("第" + (numSheet + 1) + "个工作表，第" + (rowNum + 1) + "行，第" + (cellNum + 1) + "列的内容" + value + "格式不正确");
						}
						methods.get(cellNum).invoke(temp, val);
					}
					list.add(temp);
				}
			}
		} catch (Exception e) {
			throw new ExcelException("Excel processing error！", e);
		}
		return list;
	}

	/**
	 * Import excel.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param in the in
	 * @return the list
	 */
	@SuppressWarnings("rawtypes")
	public <T> List<T> importExcel2007(Class<T> type, InputStream in) {

		Excel excelAnn = type.getAnnotation(Excel.class);
		if (excelAnn == null) {
			throw new ExcelException("The Class <" + type + "> did not Excel");
		}

		List<T> list = new ArrayList<T>();

		Map<String, Method> mapping = Maps.newLinkedHashMap();
		Map<String, TypeHandler> converters = new HashMap<String, TypeHandler>();

		try {
			// Step1 解析数据对象
			Field fileds[] = type.getDeclaredFields();
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];
				ExcelColumn column = field.getAnnotation(ExcelColumn.class);
				if (column != null) {
					Method setMethod = ReflectionUtils.setValueMethod(field, type);
					mapping.put(column.name(), setMethod);
					if (column.converter() != TypeHandler.class) {
						converters.put(setMethod.getName().toString(), column.converter().newInstance());
					} else {
						converters.put(setMethod.getName().toString(), TypeHandlerFactory.getHandler(field.getType()));
					}
				}
			}

			T temp = null;
			XSSFWorkbook hssfWorkbook = new XSSFWorkbook(in);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}

				// 解析Sheet
				List<Method> methods = Lists.newArrayList();
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

					XSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}

					// 第一行获取标题
					if (rowNum == 0) {
						for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
							String title = hssfRow.getCell(cellNum).getStringCellValue();
							Method me = mapping.get(title);
							if (me == null) {
								continue;
							}
							methods.add(me);
						}
						continue;
					}

					temp = type.newInstance();
					for (int cellNum = 0; cellNum < methods.size(); cellNum++) {
						XSSFCell xh = hssfRow.getCell(cellNum);
						if (xh == null) {
							continue;
						}
						Method m = methods.get(cellNum);
						TypeHandler handler = converters.get(m.getName());
						if (handler == null) {
							continue;
						}
						xh.setCellType(Cell.CELL_TYPE_STRING);
						String value = xh.getStringCellValue();
						if (StringUtils.isEmpty(value)) {
							continue;
						}
						Object val = null;
						try {
							val = handler.stringToType(value);
						} catch (Exception e) {
							throw new ExcelException("第" + (numSheet + 1) + "个工作表，第" + (rowNum + 1) + "行，第" + (cellNum + 1) + "列的内容" + value + "格式不正确");
						}
						methods.get(cellNum).invoke(temp, val);
					}
					list.add(temp);
				}
			}
		} catch (Exception e) {
			throw new ExcelException("Excel processing error！", e);
		}
		return list;
	}
}

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

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hlc.utility.common.ReflectionUtils;
import org.hlc.utility.common.TypeHandler;
import org.hlc.utility.common.TypeHandlerFactory;
import org.hlc.utility.excel.annotation.Excel;
import org.hlc.utility.excel.annotation.ExcelColumn;

/**
 * 输出Excel.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ExcelOutputHandler {

	/**
	 * Export excel.
	 *
	 * @param type the type
	 * @param dataSet the data set
	 * @param out the out
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportExcel(Class<?> type, Collection<?> dataSet, OutputStream out) {

		Excel excelAnn = type.getAnnotation(Excel.class);
		if (excelAnn == null) {
			throw new ExcelException("The Class <" + type + "> did not Excel");
		}
		if (dataSet == null || dataSet.size() == 0) {
			dataSet = new ArrayList();
		}
		String title = excelAnn.value();
		if (StringUtils.isEmpty(title)) {
			title = "Sheet";
		}
		List<String> exportFieldTitle = new ArrayList<String>();
		List<Integer> exportFieldWidth = new ArrayList<Integer>();
		List<Method> methodObj = new ArrayList<Method>();
		Map<String, TypeHandler> converters = new HashMap<String, TypeHandler>();

		try {

			// Step1 解析数据对象
			Field fileds[] = type.getDeclaredFields();
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];
				ExcelColumn column = field.getAnnotation(ExcelColumn.class);
				if (column != null) {
					exportFieldTitle.add(column.value());
					exportFieldWidth.add(column.width());
					Method getMethod = ReflectionUtils.getValueMethod(field, type);
					methodObj.add(getMethod);
					if (column.converter() != TypeHandler.class) {
						converters.put(getMethod.getName().toString(), column.converter().newInstance());
					} else {
						converters.put(getMethod.getName().toString(), TypeHandlerFactory.getHandler(field.getType()));
					}
				}
			}

			// Step2 导出Excel
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet(title);
			int index = 0;
			Row row = sheet.createRow(index);
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
				cell.setCellValue(text);
			}
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			Iterator<?> its = dataSet.iterator();
			while (its.hasNext()) {
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = getMethod.invoke(t, new Object[] {});
					if (value == null) {
						cell.setCellValue("");
					}
					TypeHandler conveter = converters.get(getMethod.getName());
					if (conveter == null) {
						continue;
					}
					cell.setCellValue(conveter.typeToString(value));
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			throw new ExcelException("Excel processing error！", e);
		}
	}
}

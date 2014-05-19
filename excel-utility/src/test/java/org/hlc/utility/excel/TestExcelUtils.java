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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * The Class TestExcelUtils.
 *
 * @author huanglicong
 * @version V1.0
 */
public class TestExcelUtils {

	/**
	 * Test import excel.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testImportExcel() throws IOException {

		String filename = "/Users/huanglicong/Desktop/test.xls";
		FileInputStream in = new FileInputStream(filename);

		List<Person> list = new ArrayList<Person>();
		if (filename.contains(".xlsx")) {
			list = ExcelUtil.getInstance().importExcel2007(Person.class, in);
		} else {
			list = ExcelUtil.getInstance().importExcel(Person.class, in);
		}
		int i = 1;
		for (Person temp : list) {
			System.out.println("[" + i + "]" + temp.toString());
			i++;
		}
		in.close();
	}

	/**
	 * Test export excel.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testExportExcel() throws IOException {

		Date now = new Date();
		List<Person> list = new ArrayList<Person>();
		for (int i = 1; i <= 20; i++) {
			list.add(new Person(510000200 + i, "张三" + i, "男", 20 + i, now));
		}

		FileOutputStream out = new FileOutputStream("/Users/huanglicong/Desktop/test.xls");
		ExcelUtil.getInstance().exportExcel(Person.class, list, out);
		out.close();

	}

	public static void main(String[] args) throws IOException {
		TestExcelUtils tt = new TestExcelUtils();
		// tt.testExportExcel();
		tt.testImportExcel();
	}

}

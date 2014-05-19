package org.hlc.utility.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.hlc.utility.common.DateTypeHandler;
import org.hlc.utility.excel.annotation.Excel;
import org.hlc.utility.excel.annotation.ExcelColumn;

@Excel("人员")
public class Person {

	@ExcelColumn("编号")
	private long no;
	@ExcelColumn("姓名")
	private String name;
	@ExcelColumn("性别")
	private String sex;
	@ExcelColumn("年龄")
	private int age;
	@ExcelColumn(value = "生日", converter = DateTypeHandler.class)
	private Date birthday;

	@ExcelColumn("X1")
	private byte x1 = 1;
	@ExcelColumn("X2")
	private short x2 = 2;
	@ExcelColumn("X3")
	private BigDecimal x3 = new BigDecimal(2000.00);

	public Person() {
		super();
	}

	public Person(long no, String name, String sex, int age, Date birthday) {
		super();
		this.no = no;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public byte getX1() {
		return x1;
	}

	public void setX1(byte x1) {
		this.x1 = x1;
	}

	public short getX2() {
		return x2;
	}

	public void setX2(short x2) {
		this.x2 = x2;
	}

	public BigDecimal getX3() {
		return x3;
	}

	public void setX3(BigDecimal x3) {
		this.x3 = x3;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [no=");
		builder.append(no);
		builder.append(", name=");
		builder.append(name);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", age=");
		builder.append(age);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", x1=");
		builder.append(x1);
		builder.append(", x2=");
		builder.append(x2);
		builder.append(", x3=");
		builder.append(x3);
		builder.append("]");
		return builder.toString();
	}

}

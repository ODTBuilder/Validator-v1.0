/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.type.qa20.header;

/**
 * NGIField 정보를 담고있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:39:49
 */
public class NDAField {

	String originFieldName;
	String fieldName;
	String type;
	String size;
	String decimal;
	boolean isUnique;
	boolean isNotNull;

	public NDAField() {
		super();
	}

	/**
	 * NGIField 생성자
	 * 
	 * @param fieldName
	 * @param type
	 * @param size
	 * @param decimal
	 * @param isUnique
	 */
	public NDAField(String originFieldName, String fieldName, String type, String size, String decimal,
			boolean isUnique, boolean isNotNull) {
		super();
		this.originFieldName = originFieldName;
		this.fieldName = fieldName;
		this.type = type;
		this.size = size;
		this.decimal = decimal;
		this.isUnique = isUnique;
		this.isNotNull = isNotNull;
	}

	public NDAField(String fieldName, String type, String size, String decimal, boolean isUnique, boolean isNotNull) {
		super();
		this.fieldName = fieldName;
		this.type = type;
		this.size = size;
		this.decimal = decimal;
		this.isUnique = isUnique;
		this.isNotNull = isNotNull;
	}

	public NDAField(String fieldName, String type, String size, String decimal, boolean isUnique) {
		super();
		this.fieldName = fieldName;
		this.type = type;
		this.size = size;
		this.decimal = decimal;
		this.isUnique = isUnique;
	}

	public String getOriginFieldName() {
		return originFieldName;
	}

	public void setOriginFieldName(String originFieldName) {
		this.originFieldName = originFieldName;
	}

	/**
	 * fieldName getter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:01 @return
	 * String @throws
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * fieldName setter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:04 @param
	 * fieldName void @throws
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * type getter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:05 @return
	 * String @throws
	 */
	public String getType() {
		return type;
	}

	/**
	 * type setter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:16 @param type
	 * void @throws
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * size getter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:14 @return
	 * String @throws
	 */
	public String getSize() {
		return size;
	}

	/**
	 * size setter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:12 @param size
	 * void @throws
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * decimal getter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:08 @return
	 * String @throws
	 */
	public String getDecimal() {
		return decimal;
	}

	/**
	 * decimal setter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:25 @param decimal
	 * void @throws
	 */
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}

	/**
	 * isUnique getter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:23 @return
	 * boolean @throws
	 */
	public boolean isUnique() {
		return isUnique;
	}

	/**
	 * isUnique setter @author DY.Oh @Date 2017. 3. 11. 오후 2:40:19 @param
	 * isUnique void @throws
	 */
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

}
